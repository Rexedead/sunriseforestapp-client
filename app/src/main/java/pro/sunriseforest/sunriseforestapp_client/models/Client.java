package pro.sunriseforest.sunriseforestapp_client.models;

import com.google.gson.annotations.SerializedName;

public class Client {

    @SerializedName("sr_client_id")
    private int mId;

    @SerializedName("sr_client_name")
    private String mName;

    @SerializedName("sr_client_phone")
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
