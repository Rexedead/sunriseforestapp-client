package pro.sunriseforest.sunriseforestapp_client.settings;

import com.squareup.moshi.Json;

public class Settings {

    @Json(name = "notifications_are_enabled")
    private boolean mNotificationsAreEnabled;

    public Settings(boolean notificationsAreEnabled) {
        mNotificationsAreEnabled = notificationsAreEnabled;
    }

    public boolean isNotificationsAreEnabled() {
        return mNotificationsAreEnabled;
    }

    public void setNotificationsAreEnabled(boolean notificationsAreEnabled) {
        mNotificationsAreEnabled = notificationsAreEnabled;
    }
}
