package pro.sunriseforest.sunriseforestapp_client.presenters;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.List;

import pro.sunriseforest.sunriseforestapp_client.SunriseForestApp;
import pro.sunriseforest.sunriseforestapp_client.models.Task;
import pro.sunriseforest.sunriseforestapp_client.net.ApiFactory;
import pro.sunriseforest.sunriseforestapp_client.net.AsyncNetTransformer;
import pro.sunriseforest.sunriseforestapp_client.options.SharedPreferenceHelper;
import pro.sunriseforest.sunriseforestapp_client.ui.NavigationManager;
import pro.sunriseforest.sunriseforestapp_client.ui.fragments.DeskFragment;
import retrofit2.HttpException;
import rx.Observable;


public class DeskPresenter extends BasePresenter<DeskFragment> {

    public static String TAG = "DeskPresenter";


    private static final DeskPresenter ourInstance = new DeskPresenter();

    public static DeskPresenter getInstance() {
        return ourInstance;
    }

    private NavigationManager mNavigationManager;
    private SharedPreferenceHelper mSharedPreferenceHelper;

    private DeskPresenter() {
        mNavigationManager = NavigationManager.getInstance();
        mSharedPreferenceHelper = new SharedPreferenceHelper(SunriseForestApp.getAppContext());
    }


    private List<Task> mTasks;


    private void showTasks(List<Task> tasks) {
        if (!viewIsNullAndLog("showTasks()")) {
            mView.showTasks(tasks);
        }
    }

    private void showTasks() {

        String token = mSharedPreferenceHelper.getToken();

        loadTasks(token)
                .subscribe(
                        this::showTasks,
                        this::handleNetworkError
                );
    }

    private void showTask(int position) {
        log("showTask(position = %s)", position);
        TaskPresenter.getInstance().setTask(mTasks.get(position));
        mNavigationManager.fromDeskToTask();
    }

    public void clickedNewTask() {
        log("clickedNewTask()");
        showNewTask();
    }

    public void clickedSelectedTask(int position) {
        log("clickedSelectedTask(position = %s)", position);
        showTask(position);

    }

    public void update() {
        log("update()");

        String token = mSharedPreferenceHelper.getToken();

        loadTasks(token)
                .subscribe(
                        tasks -> {
                            mTasks = tasks;
                            showTasks(tasks);
                        },
                        this::handleNetworkError
                );

    }


    private Observable<List<Task>> loadTasks(String token) {
        log("loadTasks(token = %s)", token);
        return ApiFactory
                .getSunriseForestService()
                .getTasks(token)
                .compose(new AsyncNetTransformer<>());

    }

    private void showNewTask() {
        log("showNewTask()");

        mNavigationManager.fromDeskToNewTask();
    }

    private void showError(String msg) {
        log(String.format("showError(String msg = %s)", msg));

        if (!viewIsNullAndLog("showError()")) {
            getView().showError(msg);
        }

    }

    private void handleNetworkError(Throwable e) {
        if (e instanceof ConnectException) {
            showError("Отсутствует подключение к интернету");
            logError(DeskPresenter.getInstance().getTAG() + " : " + e.getMessage());
            return;
        } else if (e instanceof SocketTimeoutException) {
            showError("На сервере проблема, попробуйте еще раз через пару минут");
            logError(DeskPresenter.getInstance().getTAG() + " : " + e.getMessage());
            return;
        } else if (((HttpException) e).code() == 400) {
            showError("Неверный запрос или запрещенные символы");
            logError(DeskPresenter.getInstance().getTAG() + " : " + e.getMessage());
        } else if (((HttpException) e).code() == 401) {
            showError("Неавторизованный запрос, попробуйте перезайти");
            logError(DeskPresenter.getInstance().getTAG() + " : " + e.getMessage());
        } else if (((HttpException) e).code() == 403) {
            showError("Пользователь заблокирован/Нет доступа");
            logError(DeskPresenter.getInstance().getTAG() + " : " + e.getMessage());
        } else if (((HttpException) e).code() == 404) {
            showError("Запрос на задачи по неверной ссылке");
            logError(DeskPresenter.getInstance().getTAG() + " : " + e.getMessage());
        } else if (((HttpException) e).code() == 408) {
            showError("Сервер перегружен, попробуйте войти через 5 минут");
            logError(DeskPresenter.getInstance().getTAG() + " : " + e.getMessage());
        } else if (((HttpException) e).code() == 429) {
            showError("Слишком много запросов. Попробуйте еще раз через 5 минут");
            logError(DeskPresenter.getInstance().getTAG() + " : " + e.getMessage());
        } else if (((HttpException) e).code() == 500) {
            showError("Ошибка сервера 500. Попробуйте еще раз через 5 минут");
            logError(DeskPresenter.getInstance().getTAG() + " : " + e.getMessage());
        } else if (((HttpException) e).code() == 502) {
            showError("Ошибочный ответ от базы. Попробуйте еще раз через 5 минут");
            logError(DeskPresenter.getInstance().getTAG() + " : " + e.getMessage());
        } else if (((HttpException) e).code() == 504) {
            showError("База перегружена. Попробуйте еще раз через 5 минут");
            logError(DeskPresenter.getInstance().getTAG() + " : " + e.getMessage());
        } else
            showError("Передать админам: " + e.getMessage());
        logError(DeskPresenter.getInstance().getTAG() + " : " + e.getMessage());



}




    @Override
    public String getTAG() {
        return TAG;
    }
}
