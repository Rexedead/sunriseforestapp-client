package pro.sunriseforest.sunriseforestapp_client.presenters;

import pro.sunriseforest.sunriseforestapp_client.SunriseForestApp;
import pro.sunriseforest.sunriseforestapp_client.models.Task;
import pro.sunriseforest.sunriseforestapp_client.net.ApiFactory;
import pro.sunriseforest.sunriseforestapp_client.net.AsyncNetTransformer;
import pro.sunriseforest.sunriseforestapp_client.settings.SharedPreferenceHelper;
import pro.sunriseforest.sunriseforestapp_client.ui.NavigationManager;
import pro.sunriseforest.sunriseforestapp_client.ui.fragments.TaskFragment;

public class TaskPresenter extends BasePresenter<TaskFragment> {


    private static final TaskPresenter ourInstance = new TaskPresenter();

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

    public void setTask(Task task) {
        mTask = task;
    }

    //забираем данные из фрагмента, и заносим их в mTask
    public void clickedSaveButton() {
        log("clickedSaveButton()");
        mTask.setTaskID(mView.getTaskId());
        mTask.setTaskDescription(mView.getDescription());
        mTask.setStartDate(mView.getTaskStartDate());
        mTask.setDeadlineDate(mView.getTaskEndDate());
        mTask.setReward(Integer.parseInt(mView.getReward()));
        mTask.setContractorName(mView.getContractorName());
        mTask.setContractorPhone(mView.getContractorPhone());
        mTask.setClientName(mView.getClientName());
        mTask.setClientPhone(mView.getClientPhone());
        saveTask(mTask);

    }

    public void clickedBookButton() {
        log("clickedBookButton()");
        bookTask(mView.getTaskId());
    }


    private boolean canChangeTask() {
        log("canChangeTask()");
        return getUserRole().equals("manager") && !(mTask.getStatus() == 103);
    }

    private void saveTask(Task task) {
        log("saveTask()");
        //todo...реализуй пока без обновы на сервере. обнови только на клиенте. коммент по итогу не стирай = DeskPresenter.getInstance().update();
        ApiFactory
                .getSunriseForestService()
                .updDescription(
                        task.getTaskID(),
                        task
                )
                .compose(new AsyncNetTransformer<>())
                .subscribe(this::setTask, this::handleNetworkError, this::hideButtonAfterSave);
    }


    private void bookTask(String t_id) {
        log("bookTask()");
        ApiFactory
                .getSunriseForestService()
                .taskReservation(
                        t_id,
                        mSharedPreferenceHelper.getUser()
                )
                .compose(new AsyncNetTransformer<>())
                .subscribe(this::setTask, this::handleNetworkError, this::showTaskActions);
    }

    //отображение при нажатии на резерв
    //после нажатия на бронь, через subscribe on complete - отображаем кнопки действия таска
    private void showTaskActions() {
        log("showTaskActions()");
        mView.bookButtonIsVisible(false);
        mView.taskActionsIsVisible(true);
        mView.setTaskContractor(mSharedPreferenceHelper.getUser().getName(),
                mSharedPreferenceHelper.getUser().getPhoneNumber());
        mView.clientIsVisible(true);
        mView.showToast("*Задача забронирована*");
    }

    //отображение при создании фрагмента
    private void displayTaskActionsForUser() {
        log("displayTaskActionsForUser()");
        boolean isManager = getUserRole().equals("manager");
        if (mTask.getStatus() == 102 &&
                (mTask.getContractorId().equals(mSharedPreferenceHelper.getUser().getId()) || isManager)) {
            mView.bookButtonIsVisible(false);
            mView.taskActionsIsVisible(true);
            mView.clientIsVisible(true);
            if(isManager){
                mView.setTextOnCompleteTaskButton("Завершить задачу (от Менеджера)");
                mView.setTextOnCancelTaskButton("Отменить бронирование (от Менеджера)");
            }
        }
        else if (mTask.getStatus() == 101) {
            mView.bookButtonIsVisible(true);
            mView.taskActionsIsVisible(false);
            mView.clientIsVisible(isManager);
        } else {
            mView.bookButtonIsVisible(false);
            mView.taskActionsIsVisible(false);
            mView.clientIsVisible(isManager || mTask.getContractorId().equals(mSharedPreferenceHelper.getUser().getId()));
        }
    }

    public void clickedCompleteButton() {
        log("clickedCompleteButton()");
        ApiFactory
                .getSunriseForestService()
                .updComplete(
                        mView.getTaskId()
                )
                .compose(new AsyncNetTransformer<>())
                .subscribe(this::setTask, this::handleNetworkError, this::hideButtonsAfterComplete);
    }


    public void clickedCancelButton() {
        log("clickedCancelButton()");

        ApiFactory
                .getSunriseForestService()
                .updCancel(
                        mView.getTaskId()
                )
                .compose(new AsyncNetTransformer<>())
                .subscribe(this::setTask, this::handleNetworkError, this::hideButtonsAfterCancel);

    }

    private String getUserRole() {
        return mSharedPreferenceHelper.getUser().getRole();
    }

    @Override
    public void bindView(TaskFragment view) {
        super.bindView(view);

        mView.setTask(
                mTask.getTaskID(),
                mTask.getTaskDescription(),
                mTask.getStartDate(),
                mTask.getDeadlineDate(),
                mTask.getReward(),
                mTask.getContractorName(),
                mTask.getContractorPhone(),
                mTask.getClientName(),
                mTask.getClientPhone());

        boolean yes = canChangeTask();
        mView.setEnabledEditTexts(yes);
        mView.saveButtonIsVisible(false);
        //логика отображения отмены и завершения
        displayTaskActionsForUser();
    }

    @Override
    protected String createTAG() {
        return "TaskPresenter";
    }


    private void hideButtonAfterSave() {
        log("hideButtonAfterSave");
//        DeskPresenter.getInstance().update();
        mView.saveButtonIsVisible(false);
        taskChanged = false;
        mView.showToast("Сохранено");
    }

    private void hideButtonsAfterComplete() {
        log("hideButtonsAfterComplete");
//        DeskPresenter.getInstance().update();
        mView.taskActionsIsVisible(false);
        mView.showToast("Задача отмечена завершенной");
    }

    private void hideButtonsAfterCancel() {
        log("hideButtonsAfterCancel");
//        DeskPresenter.getInstance().update();
        mView.taskActionsIsVisible(false);
        mView.showToast("Бронирование задачи отменено");
    }
}
