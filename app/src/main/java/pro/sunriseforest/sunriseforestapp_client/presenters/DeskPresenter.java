package pro.sunriseforest.sunriseforestapp_client.presenters;



import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import pro.sunriseforest.sunriseforestapp_client.SunriseForestApp;
import pro.sunriseforest.sunriseforestapp_client.models.SunriseNotification;
import pro.sunriseforest.sunriseforestapp_client.models.Task;
import pro.sunriseforest.sunriseforestapp_client.net.ApiFactory;
import pro.sunriseforest.sunriseforestapp_client.net.AsyncNetTransformer;
import pro.sunriseforest.sunriseforestapp_client.settings.SharedPreferenceHelper;
import pro.sunriseforest.sunriseforestapp_client.ui.NavigationManager;
import pro.sunriseforest.sunriseforestapp_client.ui.fragments.DeskFragment;
import pro.sunriseforest.sunriseforestapp_client.utils.TasksUtils;
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

        if(iAmManager()){
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

    @Override
    public void cameNewNotifications(List<SunriseNotification> notifications) {
        log("cameNewNotifications() ");
        update();
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
        if(mView!=null)
            mView.showLoading();

        loadTasks(token)
                .flatMap(Observable::from)
                .filter(this::isVisibleTaskByRole)
                .toSortedList((t1,t2)->TasksUtils.getComparatorTasks().compare(t1,t2))
                .subscribe(
                tasks -> mTasks = tasks,

                throwable -> {
                    handleNetworkError(throwable);
                    mLoading = false;
                    tryUpdateView();
                },
                ()->{
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
        sortTasks(mTasks);
    }

    private boolean iAmManager(){
        return Objects.requireNonNull(mSharedPreferenceHelper.getUser()).getRole().equals("manager");
    }

    @Override
    public String createTAG() {
        return "DeskPresenter";
    }

    public void refresh() {
        update();
    }

    private void sortTasks(List<Task> tasks){
        Collections.sort(tasks, TasksUtils.getComparatorTasks());
    }


    void updateTask(Task tsk) {
        int idx = -1;
        for (int i = 0; i < mTasks.size(); i++) {
            if(mTasks.get(i).getTaskID().equals(tsk.getTaskID())){
                idx = i;
                break;
            }
        }
        if(idx != -1){
            mTasks.remove(idx);
            mTasks.add(idx, tsk);
        }
        sortTasks(mTasks);
    }

    private boolean isVisibleTaskByRole(Task t){
        if(mSharedPreferenceHelper.getUser()==null) return false;
        if (iAmManager()) {
            return t.isFree()||t.isBooked()||t.isDone();
        }
        return t.isFree()||(t.isBooked() && t.getContractorId().equals(
                mSharedPreferenceHelper.getUser().getId()));
    }
}
