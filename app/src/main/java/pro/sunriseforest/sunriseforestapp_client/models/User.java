package pro.sunriseforest.sunriseforestapp_client.models;

import android.support.annotation.NonNull;

import com.squareup.moshi.Json;

public class User {
    @Json(name = "sr_user_id")
    private String mId;

    @Json(name = "sr_user_name")
    private String mName;

    @Json(name = "sr_user_password")
    private String mPassword;

    @Json(name = "sr_user_phone")
    private String mPhoneNumber;

    @Json(name = "sr_user_email")
    private String mEmail;

    @Json(name = "sr_user_phone_auth")
    private String mPhoneAuthNumber;

    @Json(name = "sr_user_email_auth")
    private String mEmailAuth;

    @Json(name = "sr_user_role")
    private String mRole;

    @Json(name = "sr_user_token")
    private String mToken;


    @Json(name = "sr_user_info")
    private Object mInfo;

    @Json(name = "sr_user_reward")
    private int mRewardSum;

    @Json(name = "sr_user_tasks_taken")
    private int mTasksTakenCount;

    @Json(name = "sr_user_tasks_complete")
    private int mTasksCompleteCount;

    public User(String email, String password) {
        mPassword = password;
        mEmail = email;
    }

    public User(String mName, String mPassword, String mPhoneNumber, String mEmail, String mRole) {
        this.mName = mName;
        this.mPassword = mPassword;
        this.mPhoneNumber = mPhoneNumber;
        this.mEmail = mEmail;
        this.mRole = mRole;
        this.mEmailAuth = mEmail;
        this.mPhoneAuthNumber = mPhoneNumber;
    }

    public String getEmail() {
        return mEmail;
    }

    public String getId() {
        return mId;
    }

    public String getName() {
        return mName;
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


    public void setId(String id) {
        mId = id;
    }

    public void setName(String name) {
        mName = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        mPhoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public void setInfo(String mInfo) {
        this.mInfo = mInfo;
    }


    public int getRewardSum() {
        return mRewardSum;
    }

    public void setRewardSum(int mRewardSum) {
        this.mRewardSum = mRewardSum;
    }

    public int getTasksTakenCount() {
        return mTasksTakenCount;
    }

    public int getTasksCompleteCount() {
        return mTasksCompleteCount;
    }

    public void toSalary(int reward){
        mRewardSum+= reward;
    }
    public void newTaskBooked(){
        mTasksTakenCount++;
    }
    public void taskCanceled(){
        mTasksTakenCount--;
    }
    public void taskDone(){
        mTasksCompleteCount++;
    }


    public void setTasksTakenCount(int mTasksCount) {
        this.mTasksTakenCount = mTasksCount;
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
