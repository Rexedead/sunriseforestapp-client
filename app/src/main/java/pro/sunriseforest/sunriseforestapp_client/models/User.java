package pro.sunriseforest.sunriseforestapp_client.models;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("sr_user_id")
    private int mId;

    @SerializedName("sr_user_password")
    private String mPassword;

    @SerializedName("sr_user_phone")
    private String mPhoneNumber;

    @SerializedName("sr_user_mail")
    private String mMail;

    @SerializedName("sr_user_role")
    private String mRole;

    @SerializedName("sr_user_token")
    private String mToken;

    @SerializedName("sr_user_name")
    private String mName;

    @SerializedName("sr_user_info")
    private String mInfo;



    public User(String mPassword, String mPhoneNumber, String mMail, String mName) {
        this.mPassword = mPassword;
        this.mPhoneNumber = mPhoneNumber;
        this.mMail = mMail;
        this.mName = mName;
    }

    public String getmName() {
        return mName;
    }

    public String getmMail() {
        return mMail;
    }

    public int getmId() {
        return mId;
    }

    public String getmPassword() {
        return mPassword;
    }

    public String getmPhoneNumber() {
        return mPhoneNumber;
    }

    public String getmRole() {
        return mRole;
    }

    public String getmToken() {
        return mToken;
    }

    public String getmInfo() {
        return mInfo;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public void setmPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public void setmPhoneNumber(String mPhoneNumber) {
        this.mPhoneNumber = mPhoneNumber;
    }

    public void setmMail(String mMail) {
        this.mMail = mMail;
    }

    public void setmRole(String mRole) {
        this.mRole = mRole;
    }

    public void setmToken(String mToken) {
        this.mToken = mToken;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public void setmInfo(String mInfo) {
        this.mInfo = mInfo;
    }
}
