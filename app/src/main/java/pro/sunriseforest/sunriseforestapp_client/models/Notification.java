package pro.sunriseforest.sunriseforestapp_client.models;

import com.squareup.moshi.Json;

import io.reactivex.annotations.Nullable;

public class Notification {

    @Json(name = "sr_message")
    private String mMessage;

    @Json(name = "sr_headline")
    private String mHeadline;

    @Json(name = "sr_type")
    private int mType;
    
    @Json(name = "sr_data")
    private Object data;

    public Notification(String message, String headline, int type, Object data) {
        mMessage = message;
        mHeadline = headline;
        mType = type;
        this.data = data;
    }

    public Notification(String message, String headline, int type) {
        mMessage = message;
        mHeadline = headline;
        mType = type;
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
