package pro.sunriseforest.sunriseforestapp_client.models;

import com.google.gson.annotations.SerializedName;

public class Client {

    @SerializedName("id")
    private int mId;

    @SerializedName("name")
    private String mName;

    @SerializedName("phone")
    private String mPhoneNumber;

    public Client(int id, String name, String phoneNumber) {
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

    public String getPhoneNumber() {
        return mPhoneNumber;
    }
}
