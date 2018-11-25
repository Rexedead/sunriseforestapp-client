package pro.sunriseforest.sunriseforestapp_client.models;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("_id")
    private int mId;

    @SerializedName("sr_user_password")
    private String mPassword;

    @SerializedName("sr_user_phone_auth")
    private String mPhoneNumber;

    @SerializedName("sr_user_email_auth")
    private String mEmail;

    @SerializedName("sr_user_role")
    private String mRole;

    @SerializedName("sr_user_token")
    private String mToken;


    @SerializedName("sr_user_info")
    private Object mInfo;



    public User(String mPassword, String mPhoneNumber, String mMail) {
        this.mPassword = mPassword;
        this.mPhoneNumber = mPhoneNumber;
        this.mEmail = mMail;

    }



    public String getEmail() {
        return mEmail;
    }

    public int getId() {
        return mId;
    }

    public String getPassword() {
        return mPassword;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public String getRole() {
        return mRole;
    }

    public String getToken() {
        return mToken;
    }

    public Object getInfo() {
        return mInfo;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public void setPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public void setPhoneNumber(String mPhoneNumber) {
        this.mPhoneNumber = mPhoneNumber;
    }

    public void setEmail(String mMail) {
        this.mEmail = mMail;
    }

    public void setRole(String mRole) {
        this.mRole = mRole;
    }

    public void setToken(String mToken) {
        this.mToken = mToken;
    }


    public void setInfo(String mInfo) {
        this.mInfo = mInfo;
    }
}
