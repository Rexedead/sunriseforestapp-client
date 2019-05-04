package pro.sunriseforest.sunriseforestapp_client.presenters;

import pro.sunriseforest.sunriseforestapp_client.SunriseForestApp;
import pro.sunriseforest.sunriseforestapp_client.models.Task;
import pro.sunriseforest.sunriseforestapp_client.net.ApiFactory;
import pro.sunriseforest.sunriseforestapp_client.net.AsyncNetTransformer;
import pro.sunriseforest.sunriseforestapp_client.options.SharedPreferenceHelper;
import pro.sunriseforest.sunriseforestapp_client.ui.NavigationManager;
import pro.sunriseforest.sunriseforestapp_client.ui.fragments.TaskFragment;

public class TaskPresenter extends BasePresenter<TaskFragment> {



    private static final TaskPresenter ourInstance = new TaskPresenter();
    private static final String TAG = "TaskPresenter";

    private SharedPreferenceHelper mSharedPreferenceHelper;
    public static TaskPresenter getInstance() {
        return ourInstance;
    }

    private boolean taskChanged = false;
    private Task mTask;
    private NavigationManager mNavigationManager;

    private TaskPresenter() {
        mNavigationManager = NavigationManager.getInstance();
        mSharedPreferenceHelper = new SharedPreferenceHelper(SunriseForestApp.getAppContext());
    }

    public void setTask(Task task){
        mTask = task;
    }

    public void clickedSaveButton(Task task){
        log("clickedSaveButton()");
        //...
        mView.showToast("*клик по сохранялке*");
        //если не передавать таску из фрагмента, получаем таск.ид = нулл, после второго сохранения
        mTask = task;
        mTask.setTaskDescription(mView.getDescription());
        mTask.setStartDate(mView.getTaskStartDate());
        mTask.setDeadlineDate(mView.getTaskEndDate());
        mTask.setReward(Integer.parseInt(mView.getReward()));
        saveTask(mTask);
        mView.saveButtonIsVisible(false);
        taskChanged = false;
    }


    public void clickedBookButton() {
        log("clickedBookButton()");
        bookTask(mTask.getTaskID());
        mView.showToast("*Задача забронирована*");
    }


    private boolean canChangeTask(){
        log("canChangeTask()");
        //...
        return true;
    }

    private void saveTask(Task task){
        log("saveTask()");
        //todo...реализуй пока без обновы на сервере. обнови только на клиенте. коммент по итогу не стирай
            ApiFactory
                    .getSunriseForestService()
                    .updDescription(
                           task.getTaskID(),
                            task
                            )
                    .compose(new AsyncNetTransformer<>())
                    .subscribe(this::setTask,this::handleNetworkError);
        }



        private void bookTask(String t_id){
        log("bookTask()");
            ApiFactory
                    .getSunriseForestService()
                    .taskReservation(
                            t_id,
                            mSharedPreferenceHelper.getUser()
                            )
                    .compose(new AsyncNetTransformer<>())
                    .subscribe(this::setTask,this::handleNetworkError);
        }


    @Override
    public void bindView(TaskFragment view) {
        super.bindView(view);

        view.showTask(mTask);

        boolean yes = canChangeTask();
        mView.setEnabledEditTexts(yes);
        mView.saveButtonIsVisible(false);

    }

    @Override
    protected String getTAG() {
        return TAG;
    }


}
