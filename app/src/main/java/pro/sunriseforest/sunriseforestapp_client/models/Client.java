package pro.sunriseforest.sunriseforestapp_client.models;

import com.google.gson.annotations.SerializedName;

public class Client {

    @SerializedName("id")
    private int mId;

    @SerializedName("name")
    private String mName;

    @SerializedName("phone")
    private long mPhoneNumber;

    public Client(int id, String name, long phoneNumber) {
        mId = id;
        mName = name;
        mPhoneNumber = phoneNumber;
    }

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public long getPhoneNumber() {
        return mPhoneNumber;
    }
}
