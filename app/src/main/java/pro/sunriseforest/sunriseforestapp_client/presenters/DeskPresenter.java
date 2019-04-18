package pro.sunriseforest.sunriseforestapp_client.presenters;

import java.util.List;

import pro.sunriseforest.sunriseforestapp_client.SunriseForestApp;
import pro.sunriseforest.sunriseforestapp_client.models.Task;
import pro.sunriseforest.sunriseforestapp_client.net.ApiFactory;
import pro.sunriseforest.sunriseforestapp_client.net.AsyncNetTransformer;
import pro.sunriseforest.sunriseforestapp_client.options.SharedPreferenceHelper;
import pro.sunriseforest.sunriseforestapp_client.ui.NavigationManager;
import pro.sunriseforest.sunriseforestapp_client.ui.fragments.DeskFragment;
import rx.Observable;


public class DeskPresenter extends BasePresenter<DeskFragment>{

    public static String TAG ="DeskPresenter";


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


//    public void initTaskDeskActivity() {
//
////        String token = mPreferenceHelper.getMyUser().getToken();
////        Call<List<Task>> call = ApiFactory.getSunriseForestService().getTasks(token);
////
////        call.enqueue(new Callback<List<Task>>() {
////            @Override
////            public void onResponse(Call<List<Task>> call, Response<List<Task>> resp) {
////                int code = resp.code();
////
////                if(code == 200){
//////                    mDataBaseHelper.cacheTasks(resp.body());
////                    List<Task> tasks = resp.body();
////                    mView.showListTask(tasks);
////                }else {
////                    Log.e("DeskPresenter", resp.message());
////                }
////            }
////
////            @Override
////            public void onFailure(Call<List<Task>> call, Throwable t) {
////                Log.e("DeskPresenter", t.getMessage());
////            }
////        });
//
//
//    }


    private void showTasks(List<Task> tasks){
        if(!viewIsNullAndLog("showTasks()")){
            mView.showTasks(tasks);
        }
        //...
    }

    private void showTasks(){

        String token = mSharedPreferenceHelper.getToken();

        loadTasks(token)
                .subscribe(
                        this::showTasks,
                        this::handleNetworkError
                );
    }


    public void clickedSelectedTask(int position) {
       log("clickedSelectedTask(position = %s)", position);
       if(!viewIsNullAndLog("clickedSelectedTask()")){
           mView.showToast("*клик на таск номер "+ position +"*");
       }
    }


//    public void fabAction(){
//       mView.createNewTask();
//    }

    public void update() {

        String token = mSharedPreferenceHelper.getToken();

        loadTasks(token)
                .subscribe(
                        this::showTasks,
                        this::handleNetworkError
                         );

    }




    private Observable<List<Task>> loadTasks(String token){
        return ApiFactory
                .getSunriseForestService()
                .getTasks(token)
                .compose(new AsyncNetTransformer<>());

    }

    private void showError(String msg){
        log(String.format("showError(String msg = %s)", msg));

        if(!viewIsNullAndLog("showError()")){
            getView().showError(msg);
        }

    }

    private void handleNetworkError(Throwable e){
        //TODO этот метод дергается в observer.onError(Throwable e) в случае если произошла
        // какая-то ошибка и не получилось загрузить с сети User. ошибка может быть доступа к сети и
        // мы не можем отправить запрос (в таком случае пользователя нужно оповестить, что
        // нужно проверить подключение сети), а может ошибка сети и мы взависимости от кода ошибки
        // сообщим пользователю в чем проблема.
        // Задача: в зависимости от типа ошибки (исключения) оповести пользователя правильным сообщением об ошибки.
        // ошибки логировать в logerror()
        showError("хз чо случилось и чо делать, можешь удалить приложение если что-то не нравится");

    }




    @Override
    public String getTAG() {
        return TAG;
    }
}
