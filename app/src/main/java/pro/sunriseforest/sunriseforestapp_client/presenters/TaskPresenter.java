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

    public void setTask(Task task) {
        mTask = task;
    }

    public void clickedSaveButton(Task task) {
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


    private boolean canChangeTask() {
        log("canChangeTask()");
        return getUserRole().equals("manager");
    }

    private void saveTask(Task task) {
        log("saveTask()");
        //todo...реализуй пока без обновы на сервере. обнови только на клиенте. коммент по итогу не стирай
        ApiFactory
                .getSunriseForestService()
                .updDescription(
                        task.getTaskID(),
                        task
                )
                .compose(new AsyncNetTransformer<>())
                .subscribe(this::setTask, this::handleNetworkError);
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
                .subscribe(this::setTask, this::handleNetworkError);
    }

    //отображение при нажатии на резерв
    //после нажатия на бронь, через subscribe on next - отображаем кнопки действия таска
    //не понял, нужно ли нам .subscribe(this::setTask) в методе bookTask
    private void showTaskActions() {
        log("showTaskActions()");
        mView.bookButtonIsVisible(false);
        mView.taskActionsIsVisible(true);
    }

    //отображение при создании фрагмента
    public void displayTaskActionsForUser() {
        log("displayTaskActionsForUser()");
        if (mTask.getStatus() == 102 &&
                (mTask.getContractorId().equals(mSharedPreferenceHelper.getUser().getId())
                        || getUserRole().equals("manager"))) {
            mView.bookButtonIsVisible(false);
            mView.taskActionsIsVisible(true);
        } else if (mTask.getStatus() == 101) {
            mView.bookButtonIsVisible(true);
            mView.taskActionsIsVisible(false);
        } else {
            mView.bookButtonIsVisible(false);
            mView.taskActionsIsVisible(false);
        }
    }

    public void clickedCompleteButton() {
        log("clickedCompleteButton()");
        ApiFactory
                .getSunriseForestService()
                .updComplete(
                        mTask.getTaskID(),
                        mTask
                )
                .compose(new AsyncNetTransformer<>())
                .subscribe(this::setTask, this::handleNetworkError);

        //todo скрывать, если от сервака норм ответ
        mView.taskActionsIsVisible(false);
        getView().showToast("Задача отмечена завершенной");
    }


    public void clickedCancelButton() {
        log("clickedCancelButton()");

        ApiFactory
                .getSunriseForestService()
                .updCancel(
                        mTask.getTaskID(),
                        mTask
                )
                .compose(new AsyncNetTransformer<>())
                .subscribe(this::setTask, this::handleNetworkError);

        //todo скрывать, если от сервака норм ответ
        mView.taskActionsIsVisible(false);
        getView().showToast("Задача отменена");

    }

    public String getUserRole(){
        return mSharedPreferenceHelper.getUser().getRole();
    }


    @Override
    public void bindView(TaskFragment view) {
        super.bindView(view);

        view.showTask(mTask);

        boolean yes = canChangeTask();
        mView.setEnabledEditTexts(yes);
        mView.saveButtonIsVisible(false);

        //логика отображения отмены и завершения
        //почему если пихнуть в oncreate - null pointer?
        displayTaskActionsForUser();
    }

    @Override
    protected String getTAG() {
        return TAG;
    }


}
