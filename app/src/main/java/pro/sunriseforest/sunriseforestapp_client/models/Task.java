package pro.sunriseforest.sunriseforestapp_client.models;

import com.google.gson.annotations.SerializedName;

   /*
    default 101
    changed 102
    done 103
    */

public class Task {

    @SerializedName("sr_task_contractor")
    private Contractor mContractor;

    @SerializedName("sr_task_client")
    private Client mClient;

    @SerializedName("sr_task_id")
    private String mTaskID;

    @SerializedName("sr_task_description")
    private String mTaskDescription;

    @SerializedName("sr_task_creation_date")
    private String mCreationDate;

    @SerializedName("sr_task_deadline")
    private String mDeadlineDate;

    @SerializedName("sr_task_start_date")
    private String mStartDate;

     @SerializedName("sr_task_status")
    private byte mStatus;

    @SerializedName("sr_task_reward")
    private int mReward;


    public Task(String mTaskDescription, String mCreationDate, String mDeadlineDate, String mStartDate, byte mStatus, int mReward) {
        this.mTaskDescription = mTaskDescription;
        this.mCreationDate = mCreationDate;
        this.mDeadlineDate = mDeadlineDate;
        this.mStartDate = mStartDate;
        this.mStatus = mStatus;
        this.mReward = mReward;
    }




    public int getReward() {
        return mReward;
    }

    public void setReward(int reward) {
        mReward = reward;
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

    public void setContractor(Contractor contractor) {
        mContractor = contractor;
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

    public String getmStartDate() {
        return mStartDate;
    }

    public String getTaskID() {
        return mTaskID;
    }

    public void setTaskID(String mTaskID) {
        this.mTaskID = mTaskID;
    }

}