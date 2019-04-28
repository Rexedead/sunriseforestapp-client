package pro.sunriseforest.sunriseforestapp_client.presenters;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import pro.sunriseforest.sunriseforestapp_client.SunriseForestApp;
import pro.sunriseforest.sunriseforestapp_client.models.Task;
import pro.sunriseforest.sunriseforestapp_client.net.ApiFactory;
import pro.sunriseforest.sunriseforestapp_client.net.AsyncNetTransformer;
import pro.sunriseforest.sunriseforestapp_client.net.ErrorMassageManager;
import pro.sunriseforest.sunriseforestapp_client.options.SharedPreferenceHelper;
import pro.sunriseforest.sunriseforestapp_client.ui.NavigationManager;
import pro.sunriseforest.sunriseforestapp_client.ui.fragments.TaskFragment;
import retrofit2.HttpException;

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

    public void clickedSaveButton(){
        log("clickedSaveButton()");
        //...
        mView.showToast("*клик по сохранялке*");
        mTask.setTaskDescription(mView.getDescriptionEditText());
        mTask.setStartDate(mView.getTaskStartDateEditText());
        mTask.setDeadlineDate(mView.getTaskEndDateEditText());
        mTask.setReward(Integer.parseInt(mView.getRewardEditText()));
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
                            task.getTaskDescription(),
                            task.getStartDate(),
                            task.getDeadlineDate(),
                            task.getReward()
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
        mView.addListenersForEditText();
        mView.saveButtonIsVisible(false);

    }

    @Override
    protected String getTAG() {
        return TAG;
    }

    private void handleNetworkError(Throwable e) {
        if (e instanceof ConnectException) {
            mView.showToast("Отсутствует подключение к интернету");
        } else if (e instanceof SocketTimeoutException) {
            mView.showToast("На сервере проблема, попробуйте еще раз через пару минут");
        } else if (e instanceof HttpException) {
            mView.showToast(ErrorMassageManager.WhatIsMyError(((HttpException) e).code(),TAG));
        }
        logError(e.getMessage());
    }


}
