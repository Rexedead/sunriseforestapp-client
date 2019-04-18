package pro.sunriseforest.sunriseforestapp_client.presenters;

import pro.sunriseforest.sunriseforestapp_client.SunriseForestApp;
import pro.sunriseforest.sunriseforestapp_client.models.User;
import pro.sunriseforest.sunriseforestapp_client.net.ApiFactory;
import pro.sunriseforest.sunriseforestapp_client.net.AsyncNetTransformer;
import pro.sunriseforest.sunriseforestapp_client.options.SharedPreferenceHelper;
import pro.sunriseforest.sunriseforestapp_client.ui.NavigationManager;
import pro.sunriseforest.sunriseforestapp_client.ui.fragments.RegistrationFragment;

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
    public String getTAG() {
        return "RegistrationPresenter";
    }

    public void selectedRegistration(final User user){
        log(String.format("selectedRegistration"));

        ApiFactory
                .getSunriseForestService()
                .userRegistration(user)
                .compose(new AsyncNetTransformer<>())
                .subscribe(this::saveUser,
                        this::handleNetworkError,
                        this::showTasks
                );



    }
    private void saveUser(User user){
        log(String.format("saveUser(User user = %s)", user));
        mSharedPreferenceHelper.saveUser(user);

    }
    private void showTasks(){
        log("showTasks()");
        mNavigationManager.fromRegistrationToDesk();


    }

    private void showError(String msg){
        log(String.format("showError(String msg = %s)", msg));
        if(getView() == null){
            log("showError() : view is null");
        }else{
            getView().showError(msg);
        }
    }

    private void handleNetworkError(Throwable e){
        //TODO этот метод дергается в observer.onError(Throwable e) в случае если произошла
        // какая-то ошибка и не получилось загрузить с сети User. ошибка может быть доступа к сети и
        // мы не можем отправить запрос (в таком случае пользователя нужно оповестить, что
        // нужно проверить подключение сети), а может ошибка сети и мы взависимости от кода ошибки
        // сообщим пользователю в чем проблема.
        // Задача: в зависимости от типа ошибки (исключения) оповести пользователя правильным сообщением об ошибки.
        // ошибки логировать в logerror()
        showError("хз чо случилось и чо делать, можешь удалить приложение если что-то не нравится");

    }


}
