package pro.sunriseforest.sunriseforestapp_client.presenters;


import java.util.ArrayList;
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
    private boolean mLoading = false;


    private SharedPreferenceHelper mSharedPreferenceHelper;


    private DeskPresenter() {
        mNavigationManager = NavigationManager.getInstance();
        mSharedPreferenceHelper = new SharedPreferenceHelper(SunriseForestApp.getAppContext());
    }


    private List<Task> mTasks = new ArrayList<>();


    @Override
    public void bindView(DeskFragment view) {
        super.bindView(view);

        if(isManager()){
            view.showFab();
        }else{
            view.hideFab();
        }

        if(isFirst()){
            update();
            return;
        }
        tryUpdateView();

    }


    private void tryUpdateView() {
        if(mView!= null){
            mView.showTasks(mTasks);
            if(mLoading) mView.showLoading();
            else mView.hideLoading();
        }
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

    private void update() {
        log("update()");

        String token = mSharedPreferenceHelper.getToken();
        mLoading = true;
        if(mView!=null) mView.showLoading();

        loadTasks(token).subscribe(
                tasks -> {
                    mTasks = tasks;
                    mLoading = false;
                    tryUpdateView();
                },
                throwable -> {
                    handleNetworkError(throwable);
                    mLoading = false;
                    tryUpdateView();
                }
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



    void addTask(Task task){
        log("addTask( task = %s)", task);
        mTasks.add(task);
    }

    private boolean isManager(){
        return Objects.requireNonNull(mSharedPreferenceHelper.getUser()).getRole().equals("manager");
    }

    @Override
    public String createTAG() {
        return "DeskPresenter";
    }

    public void refresh() {
        update();
    }
}
