package pro.sunriseforest.sunriseforestapp_client.models;

import com.squareup.moshi.Json;

   /*
    default 101
    changed 102
    done 103
    */

public class Task {

//    @Json(name = "sr_contractor_id")
//    private int mContractor;

    @Json(name = "sr_contractor_name")
    private String mContractorName;

    @Json(name = "sr_contractor_phone")
    private String mContractorPhone;

    @Json(name = "sr_task_client")
    private Client mClient;

    @Json(name = "_id")
    private String mTaskID;

    @Json(name = "sr_task_description")
    private String mTaskDescription;

    @Json(name = "sr_task_creation_date")
    private String mCreationDate;

    @Json(name = "sr_task_end_date")
    private String mDeadlineDate;

    @Json(name = "sr_task_start_date")
    private String mStartDate;

    @Json(name = "sr_task_status")
    private byte mStatus;

    @Json(name = "sr_task_reward")
    private int mReward;


    public Task(String mTaskDescription, String mCreationDate, String mStartDate,
                String mDeadlineDate, byte mStatus, int mReward, String mContractorName, String mContractorPhone) {
        this.mTaskDescription = mTaskDescription;
        this.mCreationDate = mCreationDate;
        this.mDeadlineDate = mDeadlineDate;
        this.mStartDate = mStartDate;
        this.mStatus = mStatus;
        this.mReward = mReward;
        this.mContractorName = mContractorName;
        this.mContractorPhone = mContractorPhone;
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

    public String getContractorName() {
        return mContractorName;
    }

    public void setContractorName(String mContractorName) {
        this.mContractorName = mContractorName;
    }

    public String getmContractorPhone() {
        return mContractorPhone;
    }

    public void setmContractorPhone(String mContractorPhone) {
        this.mContractorPhone = mContractorPhone;
    }

    public Client getClient() {
        return mClient;
    }

    public String getDeadlineDate() {
        return mDeadlineDate;
    }

    public String getStartDate() {
        return mStartDate;
    }

    public String getTaskID() {
        return mTaskID;
    }

    public void setTaskID(String mTaskID) {
        this.mTaskID = mTaskID;
    }

}