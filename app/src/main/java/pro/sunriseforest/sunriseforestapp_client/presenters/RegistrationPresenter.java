package pro.sunriseforest.sunriseforestapp_client.presenters;

import android.text.TextUtils;

import pro.sunriseforest.sunriseforestapp_client.SunriseForestApp;
import pro.sunriseforest.sunriseforestapp_client.models.User;
import pro.sunriseforest.sunriseforestapp_client.net.ApiFactory;
import pro.sunriseforest.sunriseforestapp_client.net.AsyncNetTransformer;
import pro.sunriseforest.sunriseforestapp_client.settings.SharedPreferenceHelper;
import pro.sunriseforest.sunriseforestapp_client.ui.NavigationManager;
import pro.sunriseforest.sunriseforestapp_client.ui.fragments.RegistrationFragment;
import pro.sunriseforest.sunriseforestapp_client.utils.InputCheckUtils;

public class RegistrationPresenter extends BasePresenter<RegistrationFragment> {

    private static final RegistrationPresenter ourInstance = new RegistrationPresenter();
    public static RegistrationPresenter getInstance() {
        return ourInstance;
    }

    private NavigationManager mNavigationManager;
    private SharedPreferenceHelper mSharedPreferenceHelper;

    private RegistrationPresenter() {
        mNavigationManager = NavigationManager.getInstance();
        mSharedPreferenceHelper = new SharedPreferenceHelper(SunriseForestApp.getAppContext());
    }

    @Override
    public String createTAG() {
        return "RegistrationPresenter";
    }

    public void selectedRegistration(final User user) {
        log(String.format("selectedRegistration( User ): user = %s", user));


        if(!check(user)) return;


        ApiFactory
                .getSunriseForestService()
                .userRegistration(user)
                .compose(new AsyncNetTransformer<>())
                .subscribe(this::saveUser,
                        this::handleNetworkError,
                        this::showTasks
                );


    }

    private boolean check(User user){

        String email = user.getEmail();
        String name = user.getName();
        String pn = user.getPhoneNumber();

        if(TextUtils.isEmpty(email)){
            getView().showError("Введите почту");
            return false;
        }
        if(TextUtils.isEmpty(user.getPassword())){
            getView().showError("Введите пароль");
            return false;
        }

        if(TextUtils.isEmpty(name)){
            getView().showError("Введите имя");
            return false;
        }

        if(TextUtils.isEmpty(pn)){
            getView().showError("Введите номер телефона");
            return false;
        }

        if(InputCheckUtils.checkEmail(email) != InputCheckUtils.CORRECTLY){
            getView().showError("Адрес электронной почты введен неверно");
            return false;
        }

        return true;

    }

    private void saveUser(User user) {
        log(String.format("saveUser(User user = %s)", user));
        mSharedPreferenceHelper.saveUser(user);

    }

    private void showTasks() {
        log("showTasks()");
        mNavigationManager.fromRegistrationToDesk();


    }


}
