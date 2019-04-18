package pro.sunriseforest.sunriseforestapp_client.presenters;

import pro.sunriseforest.sunriseforestapp_client.SunriseForestApp;
import pro.sunriseforest.sunriseforestapp_client.models.User;
import pro.sunriseforest.sunriseforestapp_client.net.ApiFactory;
import pro.sunriseforest.sunriseforestapp_client.net.AsyncNetTransformer;
import pro.sunriseforest.sunriseforestapp_client.options.SharedPreferenceHelper;
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

    private void saveUser(User user){
        log(String.format("saveUser(User user = %s)", user));
        mSharedPreferenceHelper.saveUser(user);

    }
    private void showTasks(){
        log("showTasks()");
        mNavigationManager.fromLoginToDesk();


    }

    private void showError(String msg){
        log(String.format("showError(String msg = %s)", msg));
        if(!viewIsNullAndLog("showError")){
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


    @Override
    protected String getTAG() {
        return "login_presenter";
    }
}
