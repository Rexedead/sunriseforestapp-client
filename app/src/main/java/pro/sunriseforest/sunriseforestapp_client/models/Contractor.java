package pro.sunriseforest.sunriseforestapp_client.models;

import com.google.gson.annotations.SerializedName;

public class Contractor {
    @SerializedName("sr_contractor_id")
    private int mId;

    @SerializedName("sr_contractor_password")
    private String mPassword;

    @SerializedName("sr_contractor_login")
    private  String mLogin;

    @SerializedName("sr_contractor_name")
    private String mName;

    @SerializedName("sr_contractor_phone")
    private String mPhoneNumber;

    public Contractor(String password, String login, String name, String phoneNumber) {
        mPassword = password;
        mLogin = login;
        mName = name;
        mPhoneNumber = phoneNumber;
    }

    public Contractor(String login, String password, int id) {
        mId = id;
        mPassword = password;
        mLogin = login;
    }

    public String getName() {
        return mName;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public String getPassword() {
        return mPassword;
    }

    public String getLogin() {
        return mLogin;
    }

    public int getId() {
        return mId;
    }

    public Contractor copy(){
        return new Contractor(mLogin,mPassword , mId);
    }

}
