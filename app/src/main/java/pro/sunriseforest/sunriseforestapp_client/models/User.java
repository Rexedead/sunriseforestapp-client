package pro.sunriseforest.sunriseforestapp_client.models;

import android.support.annotation.NonNull;

import com.squareup.moshi.Json;

public class User {
    @Json(name = "sr_user_id")
    private String mId;

    @Json(name = "sr_user_password")
    private String mPassword;

    @Json(name = "sr_user_phone_auth")
    private String mPhoneNumber;

    @Json(name = "sr_user_email_auth")
    private String mEmail;

    @Json(name = "sr_user_role")
    private String mRole;

    @Json(name = "sr_user_token")
    private String mToken;


    @Json(name = "sr_user_info")
    private Object mInfo;


    public User(String email, String password) {
        mPassword = password;
        mEmail = email;
    }

    public User(String mPassword, String mPhoneNumber, String mEmail, String mRole) {
        this.mPassword = mPassword;
        this.mPhoneNumber = mPhoneNumber;
        this.mEmail = mEmail;
        this.mRole = mRole;
    }



    public String getEmail() {
        return mEmail;
    }

    public String getId() {
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

    public void setId(String mId) {
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


    @NonNull
    @Override
    public String toString() {
        return "User{" +
                "mId='" + mId + '\'' +
                ", mPassword='" + mPassword + '\'' +
                ", mPhoneNumber='" + mPhoneNumber + '\'' +
                ", mEmail='" + mEmail + '\'' +
                ", mRole='" + mRole + '\'' +
                ", mToken='" + mToken + '\'' +
                '}';
    }
}
