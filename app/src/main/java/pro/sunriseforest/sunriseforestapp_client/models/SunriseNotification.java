package pro.sunriseforest.sunriseforestapp_client.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.squareup.moshi.Json;


import io.reactivex.annotations.Nullable;

public class SunriseNotification  implements Parcelable{

    @Json(name = "sr_notification_msg")
    private String mMessage;

    @Json(name = "sr_notification_headline")
    private String mHeadline;

    @Json(name = "sr_notification_type")
    private int mType;

    @Json(name = "sr_notification_data")
    private String data;

    public SunriseNotification(String message, String headline, int type, String data) {
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

    public @Nullable String getData() {
        return data;
    }


    protected SunriseNotification(Parcel in) {
        mMessage = in.readString();
        mHeadline = in.readString();
        mType = in.readInt();
        data = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mMessage);
        dest.writeString(mHeadline);
        dest.writeInt(mType);
        dest.writeString(data);
    }


    public static final Parcelable.Creator<SunriseNotification> CREATOR = new Parcelable.Creator<SunriseNotification>() {
        @Override
        public SunriseNotification createFromParcel(Parcel in) {
            return new SunriseNotification(in);
        }

        @Override
        public SunriseNotification[] newArray(int size) {
            return new SunriseNotification[size];
        }
    };



}
