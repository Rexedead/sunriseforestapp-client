package pro.sunriseforest.sunriseforestapp_client.presenters;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import pro.sunriseforest.sunriseforestapp_client.SunriseForestApp;
import pro.sunriseforest.sunriseforestapp_client.models.User;
import pro.sunriseforest.sunriseforestapp_client.net.ApiFactory;
import pro.sunriseforest.sunriseforestapp_client.net.AsyncNetTransformer;
import pro.sunriseforest.sunriseforestapp_client.options.SharedPreferenceHelper;
import pro.sunriseforest.sunriseforestapp_client.ui.NavigationManager;
import pro.sunriseforest.sunriseforestapp_client.ui.fragments.LoginFragment;
import retrofit2.HttpException;

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
        //хз, нужно ли их тут добавлять, даже не понял с чего они могу взяться
        //    else if (e instanceof HttpRetryException){
        //        logError("HttpRetryException");
        //        return;
        //    }
        //    else if (e instanceof InterruptedByTimeoutException){
        //        logError("InterruptedByTimeoutException");
        //        return;
        //    }

    if (e instanceof ConnectException){
        showError("Отсутствует подключение к интернету");
        logError(LoginPresenter.getInstance().getTAG()+" : "+ e.getMessage());
        return;
    }else if (e instanceof SocketTimeoutException){
        showError("На сервере проблема, попробуйте еще раз через пару минут");
        logError(LoginPresenter.getInstance().getTAG()+" : "+ e.getMessage());
        return;
        }
    else if (((HttpException)e).code() == 400){
        showError("Неверный запрос или запрещенные символы");
        logError(LoginPresenter.getInstance().getTAG()+" : "+ e.getMessage());
    }
    else if (((HttpException)e).code() == 401){
        showError("Неверное имя пользователя или пароль");
        logError(LoginPresenter.getInstance().getTAG()+" : "+ e.getMessage());
    }
    else if (((HttpException)e).code() == 403){
        showError("Пользователь заблокирован/Нет доступа");
        logError(LoginPresenter.getInstance().getTAG()+" : "+ e.getMessage());
    }
    else if (((HttpException)e).code() == 404){
        showError("Запрос на авторизацию по неверной ссылке");
        logError(LoginPresenter.getInstance().getTAG()+" : "+ e.getMessage());
    }
    else if (((HttpException)e).code() == 408){
        showError("Сервер перегружен, попробуйте войти через 5 минут");
        logError(LoginPresenter.getInstance().getTAG()+" : "+ e.getMessage());
    }
    else if (((HttpException)e).code() == 429){
        showError("Слишком много попыток входа. Попробуйте еще раз через 5 минут");
        logError(LoginPresenter.getInstance().getTAG()+" : "+ e.getMessage());
    }
    else if (((HttpException)e).code() == 500){
        showError("Ошибка сервера 500. Попробуйте еще раз через 5 минут");
        logError(LoginPresenter.getInstance().getTAG()+" : "+ e.getMessage());
    }
    else if (((HttpException)e).code() == 502){
        showError("Ошибочный ответ от базы. Попробуйте еще раз через 5 минут");
        logError(LoginPresenter.getInstance().getTAG()+" : "+ e.getMessage());
    }
    else if (((HttpException)e).code() == 504){
        showError("База перегружена. Попробуйте еще раз через 5 минут");
        logError(LoginPresenter.getInstance().getTAG()+" : "+ e.getMessage());
    }
    else
        showError("Передать админам: "+ e.getMessage());
        logError(LoginPresenter.getInstance().getTAG()+" : "+ e.getMessage());
    }


    @Override
    protected String getTAG() {
        return "login_presenter";
    }
}
