package pro.sunriseforest.sunriseforestapp_client.settings;

import com.squareup.moshi.Json;

public class Settings {

    @Json(name = "notifications_are_works")
    private boolean mNotificationsAreWorks;

    public Settings(boolean notificationsAreWorks) {
        mNotificationsAreWorks = notificationsAreWorks;
    }

    public boolean isNotificationsAreWorks() {
        return mNotificationsAreWorks;
    }

    public void setNotificationsAreWorks(boolean notificationsAreWorks) {
        mNotificationsAreWorks = notificationsAreWorks;
    }

    public boolean turnNotifications() {
        return mNotificationsAreWorks = !mNotificationsAreWorks;
    }
}
