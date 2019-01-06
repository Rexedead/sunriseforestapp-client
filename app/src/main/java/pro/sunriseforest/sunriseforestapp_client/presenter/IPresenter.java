package pro.sunriseforest.sunriseforestapp_client.presenter;

import pro.sunriseforest.sunriseforestapp_client.models.Task;
import pro.sunriseforest.sunriseforestapp_client.models.User;

public interface IPresenter {

    void startApp();
    void selectedLogin(User user);
    void selectedRegistration(User user);

    void exitProfile();
    void editProfile(User user);
    void getProfileAdditionalInfo(String id);

    void addTask(Task task);
    void taskReservation();
    void editTask();

    void clickedBack();
}
