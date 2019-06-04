package pro.sunriseforest.sunriseforestapp_client.presenters;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import pro.sunriseforest.sunriseforestapp_client.SunriseForestApp;
import pro.sunriseforest.sunriseforestapp_client.models.Task;
import pro.sunriseforest.sunriseforestapp_client.models.User;
import pro.sunriseforest.sunriseforestapp_client.net.ApiFactory;
import pro.sunriseforest.sunriseforestapp_client.net.AsyncNetTransformer;
import pro.sunriseforest.sunriseforestapp_client.settings.SharedPreferenceHelper;
import pro.sunriseforest.sunriseforestapp_client.ui.fragments.TaskFragment;

public class TaskPresenter extends BasePresenter<TaskFragment> {

    /* */
    private static final TaskPresenter ourInstance = new TaskPresenter();

    private SharedPreferenceHelper mSharedPreferenceHelper;
    private boolean mIsLoading = false;

    public static TaskPresenter getInstance() {
        return ourInstance;
    }


    private Task mTask;
    private List<Task> mChangedTasks = new ArrayList<>();
//    private boolean mIsDescriptionTaskChanged = false;
    private boolean mIsViewUpdating = false;

    private TaskPresenter() {
        mSharedPreferenceHelper = new SharedPreferenceHelper(SunriseForestApp.getAppContext());
    }

    public void setTask(Task task) {
        log("setTask()");
        mTask = task;
    }

    public void clickedSaveButton() {
        log("clickedSaveButton()");

        Task task = getChangedTask();
        if(task == null) tryUpdateView();
        updateDescriptionTask(task);
    }

    public void clickedBookButton() {
        log("clickedBookButton()");
        bookTask(mView.getTaskId());
    }

    private boolean canChangeTask() {
        log("canChangeTask()");
        return getUserRole().equals("manager") && !mTask.isDone();
    }

    public void refresh(){
        refreshDescriptionTask();
    }

    private void refreshDescriptionTask() {
        log("updateDescriptionTask()");

        startLoading();
        ApiFactory
                .getSunriseForestService()
                .getTask(
                        mTask.getTaskID()
                )
                .compose(new AsyncNetTransformer<>())
                .subscribe(
                        tsk -> {
                            mTask = tsk;
                            deleteChanges();
                            DeskPresenter.getInstance().updateTask(mTask);
                            stopLoading();
                        },
                        throwable -> {
                            handleNetworkError(throwable);
                            stopLoading();
                        },
                        this::tryUpdateView
                );
    }

    private void updateDescriptionTask(Task task) {
        log("updateDescriptionTask()");
        startLoading();
        ApiFactory
                .getSunriseForestService()
                .updDescription(
                        task.getTaskID(),
                        task
                )
                .compose(new AsyncNetTransformer<>())
                .subscribe(
                        tsk -> {
                            mTask = tsk;
                            deleteChanges();
                            DeskPresenter.getInstance().updateTask(mTask);
                            if(mView !=null) mView.showToast("Изменения сохранены");
                            stopLoading();
                        },
                        throwable -> {
                            handleNetworkError(throwable);
                            stopLoading();
                        },
                        this::tryUpdateView
                        );
    }

    private void deleteChanges(){
        for (int i = 0; i < mChangedTasks.size(); i++) {
            if(mChangedTasks.get(i).getTaskID().equals(mTask.getTaskID())){
                mChangedTasks.remove(i);
                break;
            }
        }
    }


    private void tryUpdateView() {
        log("tryUpdateView()");

        if(mView == null) return;

        boolean iAmManager = getUserRole().equals("manager");
        boolean isMyTask = isMyTask();

        if(mIsLoading) mView.showLoading();
        else mView.hideLoading();

        Task task = getChangedTask();
        //start updating view
        mIsViewUpdating = true;
        if(task != null){
            mView.showSaveViews();
            mView.showTask(task);
        }
        else {
            mView.hideSaveViews();
            mView.showTask(mTask);
        }

        if(iAmManager){
            mView.hideBookViews();
            mView.hideActionsTaskViews();
            mView.showClientViews();
            if(mTask.isFree()){
                mView.hideContractorViews();
            }
            if(mTask.isBooked() || mTask.isDone()){
                mView.showContractorViews();
            }
        }else{
            if(isMyTask){
                mView.hideContractorViews();
                mView.hideBookViews();
                if(mTask.isBooked()){
                    mView.showActionsTaskViews();
                    mView.showClientViews();
                }
                if(mTask.isDone()){
                    mView.hideActionsTaskViews();
                    mView.showClientViews();
                }
            }else{
                mView.hideActionsTaskViews();
                mView.hideClientViews();
                if(mTask.isFree()){
                    mView.hideContractorViews();
                    mView.showBookViews();
                }
                if(mTask.isBooked()|| mTask.isDone()){
                    mView.showContractorViews();
                    mView.hideBookViews();
                }
            }
        }
        //finish updating
        mIsViewUpdating = false;
    }


    private Task getChangedTask(){
        for (int i = 0; i < mChangedTasks.size(); i++) {
            if(mChangedTasks.get(i).getTaskID().equals(mTask.getTaskID())) {
                return mChangedTasks.get(i);
            }
        }
        return null;
    }


    private boolean isMyTask() {
        if(mTask.getContractorId()==null) return false;
        return mTask.getContractorId()
                .equals(Objects.requireNonNull(mSharedPreferenceHelper.getUser()).getId());
    }


    @Override
    public void unBindView() {
        mIsViewUpdating = false;
        super.unBindView();
    }

    private void bookTask(String taskId) {
        log("bookTask()");
        startLoading();
        ApiFactory
                .getSunriseForestService()
                .taskReservation(
                        taskId,
                        mSharedPreferenceHelper.getUser()
                )
                .compose(new AsyncNetTransformer<>())
                .subscribe(
                        tsk->{
                            mTask = tsk;
                            book(tsk);
                            if(mView !=null) mView.showToast("Вы успешно забронировали");
                            stopLoading();
                        },
                        throwable -> {
                            handleNetworkError(throwable);
                            stopLoading();
                        },
                        this::tryUpdateView);
    }

    private void book(Task task) {
        planeNotification(task);
        DeskPresenter.getInstance().updateTask(task);
        mSharedPreferenceHelper.updateUser(User::newTaskBooded);
    }

    private void planeNotification(Task task) {
        //todo
    }

    public void clickedCompleteButton() {
        log("clickedCompleteButton()");
        startLoading();
        ApiFactory
                .getSunriseForestService()
                .updComplete(
                        mView.getTaskId()
                )
                .compose(new AsyncNetTransformer<>())
                .subscribe(
                        tsk -> {
                            mTask = tsk;
                            complete(tsk);
                            if(mView !=null) mView.showToast("Mission completed");
                            stopLoading();
                        },
                        throwable -> {
                            handleNetworkError(throwable);
                            stopLoading();
                        },
                        this::tryUpdateView);
    }

    private void complete(Task task) {
        DeskPresenter.getInstance().updateTask(task);
        mSharedPreferenceHelper.updateUser(user -> {
            user.taskDone();
            user.toSalary(task.getReward());
        });
    }


    public void clickedCancelTaskButton() {
        log("clickedCancelTaskButton()");
        startLoading();
        ApiFactory
                .getSunriseForestService()
                .updCancel(
                        mView.getTaskId()
                )
                .compose(new AsyncNetTransformer<>())
                .subscribe(
                        tsk ->{
                            mTask = tsk;
                            cancel(tsk);
                            stopLoading();
                            if(mView !=null) mView.showToast("Вы отменили задание");
                        },
                        throwable -> {
                            handleNetworkError(throwable);
                            stopLoading();
                        },this::tryUpdateView);

    }

    private void cancel(Task task) {
        DeskPresenter.getInstance().updateTask(task);
        mSharedPreferenceHelper.updateUser(User::taskCanceled);
    }

    private String getUserRole() {
        return Objects.requireNonNull(mSharedPreferenceHelper.getUser()).getRole();
    }

    @Override
    public void bindView(TaskFragment view) {
        super.bindView(view);

        boolean yes = canChangeTask();
        mView.setEnabledEditTexts(yes);

        tryUpdateView();

    }

    @Override
    protected String createTAG() {
        return "TaskPresenter";
    }


    public void changedStartDate(int year, int month, int dayOfMonth) {
        log("changedStartDate(y = %s, m = %s, d = %s)", year,month,dayOfMonth);
        Locale locale = Locale.getDefault();
        String date = String.format(locale,"%02d.%02d.%d", dayOfMonth, month+1, year);
        if(mView != null) mView.setTaskStartDate(date);
    }

    public void changedEndDate(int year, int month, int dayOfMonth) {
        log("changedEndDate(y = %s, m = %s, d = %s)", year,month,dayOfMonth);
        Locale locale = Locale.getDefault();
        String date = String.format(locale, "%02d.%02d.%d", dayOfMonth, month+1, year);
        if(mView != null) mView.setTaskEndDate(date);
    }

    public void clickedEndDate() {
        log("clickedEndDate()");

        Task task = getChangedTask();
        if(task == null) task = mTask;
        String endDateString = task.getDeadlineDate();
        String[] endDate = TextUtils.split(endDateString, "\\.");

        int y = Integer.parseInt(endDate[2]);
        int m = Integer.parseInt(endDate[1]);
        int d = Integer.parseInt(endDate[0]);
//        int m = Integer.parseInt(String.valueOf(endDate[1].startsWith("0")? endDate[1].charAt(1) : endDate[1])); // )))0))0
//        int d = Integer.parseInt(String.valueOf(endDate[0].startsWith("0")? endDate[0].charAt(1) : endDate[0]));
        mView.showEndDatePickerDialog(y, m ,d);
    }

    public void clickedStartDate() {
        log("clickedStartDate()");
        Task task = getChangedTask();
        if(task == null) task = mTask;
        String startDateString = task.getStartDate();
        String[] startDate = TextUtils.split(startDateString, "\\.");

        int y = Integer.parseInt(startDate[2]);
        int m = Integer.parseInt(startDate[1]);
        int d = Integer.parseInt(startDate[0]);
//        int m = Integer.parseInt(String.valueOf(startDate[1].startsWith("0")? startDate[1].charAt(1) : startDate[1])); // )))0))0
//        int d = Integer.parseInt(String.valueOf(startDate[0].startsWith("0")? startDate[0].charAt(1) : startDate[0]));
        mView.showStartDatePickerDialog(y, m, d);
    }

    private void updateChangedTask(){
        for (int i = 0; i < mChangedTasks.size(); i++) {
            if(mChangedTasks.get(i).getTaskID().equals(mTask.getTaskID())){
                setDescriptionFromUI(mChangedTasks.get(i));
                return;
            }
        }
        // if changedTask is not in mChangedTasks then create it
        Task changedTask = mTask.getCopy();
        setDescriptionFromUI(changedTask);
        mChangedTasks.add(changedTask);
    }
    private void setDescriptionFromUI(Task task){

        task.setTaskID(mView.getTaskId());
        task.setTaskDescription(mView.getDescription());
        task.setStartDate(mView.getTaskStartDate());
        task.setDeadlineDate(mView.getTaskEndDate());

        task.setReward(
                TextUtils.isEmpty(mView.getReward()) ?
                        0 : Integer.parseInt(mView.getReward()));

        task.setContractorName(mView.getContractorName());
        task.setContractorPhone(mView.getContractorPhone());
        task.setClientName(mView.getClientName());
        task.setClientPhone(mView.getClientPhone());

    }

    public void descriptionTaskIsChanged() {
        log("descriptionTaskIsChanged()");
        if(!mIsViewUpdating){
            updateChangedTask();
            mView.showSaveViews();

        }
    }

    private void startLoading(){
        mIsLoading = true;
        if(mView != null) mView.showLoading();
    }
    private void stopLoading(){
        mIsLoading = false;
        if(mView != null) mView.hideLoading();
    }

    public void clickedCancelChangesButton() {
        log("clickedCancelChangesButton()");
        deleteChanges();
        tryUpdateView();
    }

    public void clearChangedTasks(){
        mChangedTasks.clear();
    }

}
