package pro.sunriseforest.sunriseforestapp_client.presenters;

import pro.sunriseforest.sunriseforestapp_client.models.Task;
import pro.sunriseforest.sunriseforestapp_client.ui.NavigationManager;
import pro.sunriseforest.sunriseforestapp_client.ui.fragments.TaskFragment;

public class TaskPresenter extends BasePresenter<TaskFragment> {



    private static final TaskPresenter ourInstance = new TaskPresenter();

    public static TaskPresenter getInstance() {
        return ourInstance;
    }

    private boolean taskChanged = false;
    private Task mTask;
    private NavigationManager mNavigationManager;
//    private SharedPreferenceHelper mSharedPreferenceHelper;

    private TaskPresenter() {
        mNavigationManager = NavigationManager.getInstance();
//        mSharedPreferenceHelper = new SharedPreferenceHelper(SunriseForestApp.getAppContext());
    }

    public void setTask(Task task){
        mTask = task;
    }

    public void clickedSaveButton(){
        log("clickedSaveButton()");
        //...
        mView.showToast("*клик по сохранялке*");
        saveTask();
        taskChanged = false;


    }
    private boolean canChangeTask(){
        log("canChangeTask()");
        //...
        return false;
    }

    private void saveTask(){
        log("saveTask()");
        //...реализуй пока без обновы на сервере. обнови только на клиенте. коммент по итогу не стирай
    }


    @Override
    public void bindView(TaskFragment view) {
        super.bindView(view);
        view.showTask(mTask);

        boolean yes = canChangeTask();
        mView.setEnabledEditTexts(yes);


    }

    @Override
    protected String getTAG() {
        return "TaskPresenter";
    }



}
