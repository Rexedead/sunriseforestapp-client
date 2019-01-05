package pro.sunriseforest.sunriseforestapp_client.options;

import pro.sunriseforest.sunriseforestapp_client.models.User;

public interface ISharedPreferenceHelper {

    User getUser();
    String getToken();
    void saveUser(User user);
    void removeUser();

}
