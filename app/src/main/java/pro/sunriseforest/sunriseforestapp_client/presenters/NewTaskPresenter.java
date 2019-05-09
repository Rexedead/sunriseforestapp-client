package pro.sunriseforest.sunriseforestapp_client.presenters;



import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import pro.sunriseforest.sunriseforestapp_client.SunriseForestApp;
import pro.sunriseforest.sunriseforestapp_client.models.Task;
import pro.sunriseforest.sunriseforestapp_client.net.ApiFactory;
import pro.sunriseforest.sunriseforestapp_client.net.AsyncNetTransformer;
import pro.sunriseforest.sunriseforestapp_client.settings.SharedPreferenceHelper;
import pro.sunriseforest.sunriseforestapp_client.ui.NavigationManager;
import pro.sunriseforest.sunriseforestapp_client.ui.fragments.NewTaskFragment;


public class NewTaskPresenter extends BasePresenter<NewTaskFragment> {

    private static final String TAG = "NewTaskPresenter";
    private static final int TASK_IS_OK = 1;
    private static final int DESCRIPTION_TASK_IS_EMPTY = 2;
    private static final int END_DATE_TASK_IS_NOT_SELECTED = 3;
    private static final int START_DATE_TASK_IS_NOT_SELECTED = 4;
    private static final int INPUT_ERROR = 101;

    private static final NewTaskPresenter ourInstance = new NewTaskPresenter();


    private NavigationManager mNavigationManager;
    private SharedPreferenceHelper mSharedPreferenceHelper;

    private Calendar mStartTaskDate = Calendar.getInstance();
    private Calendar mEndTaskDate = Calendar.getInstance();

    public static NewTaskPresenter getInstance() {
        return ourInstance;
    }


    private NewTaskPresenter() {
        mNavigationManager = NavigationManager.getInstance();
        mSharedPreferenceHelper = new SharedPreferenceHelper(SunriseForestApp.getAppContext());


    }

    public void setStartDate(int y, int m, int d){
        log("setStartDate( y = %s, m = %s, d = %s)", y, m, d);
        setDate(y, m, d, mStartTaskDate);
        updateStartDateEdit();
    }

    public void setEndDate(int y, int m, int d){
        log("setEndDate( y = %s, m = %s, d = %s)", y, m, d);
        setDate(y, m, d, mEndTaskDate);
        updateEndDateEdit();

    }

    public void clickedTaskStartDate(){
        log("clickedTaskStartDate()");

        getView().showStartDatePickerDialog(
                mStartTaskDate.get(Calendar.YEAR),
                mStartTaskDate.get(Calendar.MONTH),
                mStartTaskDate.get(Calendar.DAY_OF_MONTH));

    }

    public void clickedAddNewTask(Task task){
        int code = checkTask(task);
        if(code == TASK_IS_OK){
            addTask(task);
        }else{
            showInputError(code);
        }
    }
    public void clickedTaskEndDate(){
        log("clickedTaskEndDate()");
        getView().showEndDatePickerDialog(
                mEndTaskDate.get(Calendar.YEAR),
                mEndTaskDate.get(Calendar.MONTH),
                mEndTaskDate.get(Calendar.DAY_OF_MONTH));

    }

    private void addTask(Task task){
        String token = mSharedPreferenceHelper.getToken();
        String managerId = mSharedPreferenceHelper.getUser().getId();

        ApiFactory.getSunriseForestService()
                .addTask(task, token, managerId)
                .compose(new AsyncNetTransformer<>())
                .subscribe(this::saveTask,
                this::handleNetworkError,
                this::taskIsAdded
        );
    }

    private String getDate(long mils){
        String myFormat = "dd.MM.yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        return sdf.format(mils);
    }
    private void setDate(int y, int m, int d, Calendar calendar){
        calendar.set(Calendar.YEAR, y);
        calendar.set(Calendar.MONTH, m);
        calendar.set(Calendar.DAY_OF_MONTH, d);
    }


    private void updateEndDateEdit(){
        log("updateEndDateEdit()");
        String date = getDate(mEndTaskDate.getTimeInMillis());
        getView().updateEndDateEdit(date);
    }

    private void updateStartDateEdit(){
        log("updateStartDateEdit()");
        String date = getDate(mStartTaskDate.getTimeInMillis());
        getView().updateStartDateEdit(date);
    }

    private int checkTask(Task task){

        if(nullOrEmpty(task.getTaskDescription()))
            return DESCRIPTION_TASK_IS_EMPTY;
        else if(nullOrEmpty(task.getStartDate()))
            return START_DATE_TASK_IS_NOT_SELECTED;
        else if(nullOrEmpty(task.getDeadlineDate()))
            return END_DATE_TASK_IS_NOT_SELECTED;

        return TASK_IS_OK;
    }
    private void showInputError(int code){
        //...
        getView().showError("неверно заполнены поля");
    }

    private void taskIsAdded(){
        log("taskIsAdded");

        getView().showToast("Новый таск добавлен");

        mNavigationManager.back();
    }

    private void saveTask(Task task){
        log("saveTask( task = %s)", task);
        DeskPresenter.getInstance().addTask(task);
    }

    private void showError(String msg){
        log(String.format("showError(String msg = %s)", msg));
        if(!viewIsNullAndLog("showError")){
            getView().showError(msg);
        }

    }


    private boolean nullOrEmpty(String str){
        return str == null || str.length() == 0;
    }

    @Override
    public String getTAG() {
        return TAG;
    }
}


