package pro.sunriseforest.sunriseforestapp_client.presenters;



import android.text.TextUtils;


import pro.sunriseforest.sunriseforestapp_client.SunriseForestApp;
import pro.sunriseforest.sunriseforestapp_client.models.User;
import pro.sunriseforest.sunriseforestapp_client.net.ApiFactory;
import pro.sunriseforest.sunriseforestapp_client.net.AsyncNetTransformer;
import pro.sunriseforest.sunriseforestapp_client.settings.SharedPreferenceHelper;
import pro.sunriseforest.sunriseforestapp_client.ui.NavigationManager;
import pro.sunriseforest.sunriseforestapp_client.ui.fragments.LoginFragment;

public class LoginPresenter extends BasePresenter<LoginFragment>{
    private static final LoginPresenter ourInstance = new LoginPresenter();
    public static LoginPresenter getInstance() {
        return ourInstance;
    }

    private NavigationManager mNavigationManager;
    private SharedPreferenceHelper mSharedPreferenceHelper;

    private LoginPresenter() {
        mNavigationManager = NavigationManager.getInstance();
        mSharedPreferenceHelper = new SharedPreferenceHelper(SunriseForestApp.getAppContext());
        
    }

    public void selectedLogin(User user){
        log(String.format("selectedLogin(user=%s)", user));
        
        String email = user.getEmail();
        String password = user.getPassword();

        if(TextUtils.isEmpty(email)){
            getView().showError("Введите вашу почту ");
            return;
        }

        if(TextUtils.isEmpty(password)){
            getView().showError("Введите пароль");
            return;
        }

        if(!checkEmail(email)){
            getView().showError("Неверная почта");
            return;
        }

        ApiFactory
                .getSunriseForestService()
                .userLoginByEmail(email, password)
                .compose(new AsyncNetTransformer<>())
                .subscribe(this::saveUser,
                        this::handleNetworkError,
                        this::showTasks
                        );

    }



    public void selectedGoToRegistration(){
        mNavigationManager.fromLoginToRegistration();
    }



    private boolean checkEmail(String email){
       return email.split("@").length == 2;
    }

    private void saveUser(User user){
        log(String.format("saveUser(User user = %s)", user));
        mSharedPreferenceHelper.saveUser(user);

    }
    private void showTasks(){
        log("showTasks()");
        mNavigationManager.fromLoginToDesk();


    }


    @Override
    protected String createTAG() {
        return "LoginPresenter";
    }
}
