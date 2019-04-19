package pro.sunriseforest.sunriseforestapp_client.ui;

import java.util.List;

import pro.sunriseforest.sunriseforestapp_client.models.Task;

public interface IView {

    void showLoginScreen();
    void showRegistrationScreen();
    void showDeskScreen();
    void showTasks(List<Task> tasks);
    void showTask();
    void hideBottomNavigation();
    void showBottomNavigation();
    void showProfile();
    void showNewTask();
    void showError(String msg);
    void showLoading();
    void showInfoMessage(String msg);

}
