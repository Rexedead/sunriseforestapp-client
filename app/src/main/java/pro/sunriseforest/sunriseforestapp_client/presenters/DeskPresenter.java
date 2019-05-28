package pro.sunriseforest.sunriseforestapp_client.presenters;

import java.util.List;
import java.util.Objects;

import pro.sunriseforest.sunriseforestapp_client.SunriseForestApp;
import pro.sunriseforest.sunriseforestapp_client.models.Task;
import pro.sunriseforest.sunriseforestapp_client.net.ApiFactory;
import pro.sunriseforest.sunriseforestapp_client.net.AsyncNetTransformer;
import pro.sunriseforest.sunriseforestapp_client.settings.SharedPreferenceHelper;
import pro.sunriseforest.sunriseforestapp_client.ui.NavigationManager;
import pro.sunriseforest.sunriseforestapp_client.ui.fragments.DeskFragment;
import rx.Observable;


public class DeskPresenter extends BasePresenter<DeskFragment> {


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


    public void addTask(Task task){
        log("addTask( task = %s)", task);
        mTasks.add(task);
    }

    public boolean isManager(){
        return Objects.requireNonNull(mSharedPreferenceHelper.getUser()).getRole().equals("manager");
    }

    @Override
    public String createTAG() {
        return "DeskPresenter";
    }
}
