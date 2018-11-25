package pro.sunriseforest.sunriseforestapp_client.models;

import com.google.gson.annotations.SerializedName;

public class Client {

    @SerializedName("_id")
    private int mId;

    @SerializedName("sr_client_name")
    private String mName;

    @SerializedName("sr_client_phone")
    private String mPhoneNumber;

    @SerializedName("sr_client_email")
    private String mEmail;



    public Client(int id, String name, String phoneNumber) {
        mId = id;
        mName = name;
        mPhoneNumber = phoneNumber;
    }

    public String getEmail() {
        return mEmail;
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
