package pro.sunriseforest.sunriseforestapp_client.models;

import com.google.gson.annotations.SerializedName;

public class Task {

    private int mId;

    @SerializedName("contractor")
    private Contractor mContractor;

    @SerializedName("client")
    private Client mClient;

    @SerializedName("taskDescription")
    private String mTaskDescription;

    @SerializedName("creationDate")
    private String mCreationDate;

    @SerializedName("deadlineDate")
    private String mDeadlineDate;

    @SerializedName("startDate")
    private String mStartDate;

    /*
    Размещено 101
    Взято 102
    Изменено 103
    Отменено 104
    Выполнено 105
    */

    @SerializedName("status")
    private byte mStatus;

    @SerializedName("reward")
    private String mReward;


    public Task(int id,
                String taskDescription,
                byte status,
                String creationDate,
                String reward) {
        mId = id;
        mTaskDescription = taskDescription;
        mStatus = status;
        mCreationDate = creationDate;
        mReward = reward;
    }


    public String getReward() {
        return mReward;
    }

    public void setReward(String reward) {
        mReward = reward;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getCreationDate() {
        return mCreationDate;
    }

    public String getTaskDescription() {
        return mTaskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        mTaskDescription = taskDescription;
    }

    public byte getStatus() {
        return mStatus;
    }

    public Contractor getContractor() {
        return mContractor;
    }

    public Client getClient() {
        return mClient;
    }

    public String getDeadlineDate() {
        return mDeadlineDate;
    }

    public Task copy(){

        return new Task(mId, mTaskDescription, mStatus,
                mCreationDate, mReward);
    }


}