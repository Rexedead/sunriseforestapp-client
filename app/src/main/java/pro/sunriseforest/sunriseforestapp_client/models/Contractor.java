package pro.sunriseforest.sunriseforestapp_client.models;

import com.google.gson.annotations.SerializedName;

public class Contractor {
    @SerializedName("id")
    private int mId;

    @SerializedName("password")
    private String mPassword;

    @SerializedName("login")
    private  String mLogin;

    @SerializedName("name")
    private String mName;

    @SerializedName("phoneNumber")
    private long mPhoneNumber;

    public Contractor(String password, String login, String name, long phoneNumber) {
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

    public long getPhoneNumber() {
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
