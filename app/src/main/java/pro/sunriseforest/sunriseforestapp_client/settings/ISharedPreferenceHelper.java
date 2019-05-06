package pro.sunriseforest.sunriseforestapp_client.settings;

import pro.sunriseforest.sunriseforestapp_client.models.User;

public interface ISharedPreferenceHelper {

    User getUser();
    String getToken();
    void saveUser(User user);
    void removeUser();
    Settings getSettings();
    void updateSettings(SharedPreferenceHelper.OnUpdatedSettings update);

}
