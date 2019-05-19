package pro.sunriseforest.sunriseforestapp_client.models;

import com.squareup.moshi.Json;

import io.reactivex.annotations.Nullable;

public class SunriseNotification {

    @Json(name = "sr_notification_msg")
    private String mMessage;

    @Json(name = "sr_notification_headline")
    private String mHeadline;

    @Json(name = "sr_notification_type")
    private int mType;
    
    @Json(name = "sr_notification_data")
    private Object data;

    public SunriseNotification(String message, String headline, int type, Object data) {
        mMessage = message;
        mHeadline = headline;
        mType = type;
        this.data = data;
    }

    public SunriseNotification(String message, String headline, int type) {
        mMessage = message;
        mHeadline = headline;
        mType = type;
    }

    public SunriseNotification(String headline) {
        mHeadline = headline;
    }

    public String getMessage() {
        return mMessage;
    }

    public String getHeadline() {
        return mHeadline;
    }

    public int getType() {
        return mType;
    }

    public @Nullable Object getData() {
        return data;
    }


}
