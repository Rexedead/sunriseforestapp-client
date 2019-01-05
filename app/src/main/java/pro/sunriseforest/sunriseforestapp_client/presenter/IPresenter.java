package pro.sunriseforest.sunriseforestapp_client.presenter;

import pro.sunriseforest.sunriseforestapp_client.models.User;

public interface IPresenter {

    void startApp();
    void selectedLogin(User user);
    void selectedRegistration(User user);

    void exitProfile();
    void clickedBack();

}
