package pro.sunriseforest.sunriseforestapp_client.presenters;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import pro.sunriseforest.sunriseforestapp_client.SunriseForestApp;
import pro.sunriseforest.sunriseforestapp_client.models.User;
import pro.sunriseforest.sunriseforestapp_client.net.ApiFactory;
import pro.sunriseforest.sunriseforestapp_client.net.AsyncNetTransformer;
import pro.sunriseforest.sunriseforestapp_client.options.SharedPreferenceHelper;
import pro.sunriseforest.sunriseforestapp_client.ui.NavigationManager;
import pro.sunriseforest.sunriseforestapp_client.ui.fragments.RegistrationFragment;
import retrofit2.HttpException;

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
        if (e instanceof ConnectException) {
            showError("Отсутствует подключение к интернету");
            logError(RegistrationPresenter.getInstance().getTAG() + " : " + e.getMessage());
            return;
        } else if (e instanceof SocketTimeoutException) {
            showError("На сервере проблема, попробуйте еще раз через пару минут");
            logError(RegistrationPresenter.getInstance().getTAG() + " : " + e.getMessage());
            return;
        } else if (((HttpException) e).code() == 400) {
            showError("Неверный запрос или запрещенные символы");
            logError(RegistrationPresenter.getInstance().getTAG() + " : " + e.getMessage());
        } else if (((HttpException) e).code() == 401) {
            showError("401 при регистрации");
            logError(RegistrationPresenter.getInstance().getTAG() + " : " + e.getMessage());
        } else if (((HttpException) e).code() == 403) {
            showError("403 при регистрации");
            logError(RegistrationPresenter.getInstance().getTAG() + " : " + e.getMessage());
        } else if (((HttpException) e).code() == 404) {
            showError("Запрос на регистрацию по неверной ссылке");
            logError(RegistrationPresenter.getInstance().getTAG() + " : " + e.getMessage());
        } else if (((HttpException) e).code() == 408) {
            showError("Сервер перегружен, попробуйте войти через 5 минут");
            logError(RegistrationPresenter.getInstance().getTAG() + " : " + e.getMessage());
        } else if (((HttpException) e).code() == 429) {
            showError("Слишком много запросов. Попробуйте еще раз через 5 минут");
            logError(RegistrationPresenter.getInstance().getTAG() + " : " + e.getMessage());
        } else if (((HttpException) e).code() == 500) {
            showError("Ошибка сервера 500. Попробуйте еще раз через 5 минут");
            logError(RegistrationPresenter.getInstance().getTAG() + " : " + e.getMessage());
        } else if (((HttpException) e).code() == 502) {
            showError("Ошибочный ответ от базы. Попробуйте еще раз через 5 минут");
            logError(RegistrationPresenter.getInstance().getTAG() + " : " + e.getMessage());
        } else if (((HttpException) e).code() == 504) {
            showError("База перегружена. Попробуйте еще раз через 5 минут");
            logError(RegistrationPresenter.getInstance().getTAG() + " : " + e.getMessage());
        } else
            showError("Передать админам: " + e.getMessage());
        logError(RegistrationPresenter.getInstance().getTAG() + " : " + e.getMessage());

    }


}
