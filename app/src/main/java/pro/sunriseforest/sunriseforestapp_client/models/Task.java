package pro.sunriseforest.sunriseforestapp_client.models;


import com.squareup.moshi.Json;

   /*
    default 101
    changed 102
    done 103
    */

public class Task {

    @Json(name = "sr_contractor_id")
    private String mContractorId;

    @Json(name = "sr_contractor_name")
    private String mContractorName;

    @Json(name = "sr_contractor_phone")
    private String mContractorPhone;

    @Json(name = "sr_client_phone")
    private String mClientPhone;

    @Json(name = "sr_client_name")
    private String mClientName;

    @Json(name = "sr_task_id")
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

    public Task(){}


    public Task(String contractorId, String contractorName, String contractorPhone,
                String clientPhone, String clientName, String taskID,
                String taskDescription, String creationDate, String deadlineDate,
                String startDate, byte status, int reward) {

        mContractorId = contractorId;
        mContractorName = contractorName;
        mContractorPhone = contractorPhone;
        mClientPhone = clientPhone;
        mClientName = clientName;
        mTaskID = taskID;
        mTaskDescription = taskDescription;
        mCreationDate = creationDate;
        mDeadlineDate = deadlineDate;
        mStartDate = startDate;
        mStatus = status;
        mReward = reward;
    }

    public String getTaskID() {
        return mTaskID;
    }
    public int getReward() {
        return mReward;
    }

    public String getCreationDate() {
        return mCreationDate;
    }

    public String getTaskDescription() {
        return mTaskDescription;
    }

    public byte getStatus() {
        return mStatus;
    }

    public String getContractorName() {
        return mContractorName;
    }

    public String getContractorPhone() {
        return mContractorPhone;
    }

    public String getDeadlineDate() {
        return mDeadlineDate;
    }

    public String getStartDate() {
        return mStartDate;
    }

    public String getClientPhone() {
        return mClientPhone;
    }

    public String getClientName() {
        return mClientName;
    }
    public String getContractorId() {
        return mContractorId;
    }


    public void setStatus(byte status) {
        mStatus = status;
    }

    public void setCreationDate(String creationDate) {
        mCreationDate = creationDate;
    }

    public void setTaskDescription(String taskDescription) {
        mTaskDescription = taskDescription;
    }

    public void setDeadlineDate(String mDeadlineDate) {
        this.mDeadlineDate = mDeadlineDate;
    }

    public void setReward(int reward) {
        mReward = reward;
    }

    public void setStartDate(String mStartDate) {
        this.mStartDate = mStartDate;
    }

    public void setContractorPhone(String mContractorPhone) {
        this.mContractorPhone = mContractorPhone;
    }

    public void setContractorName(String mContractorName) {
        this.mContractorName = mContractorName;
    }

    public void setClientPhone(String mClientPhone) {
        this.mClientPhone = mClientPhone;
    }

    public void setClientName(String mClientName) {
        this.mClientName = mClientName;
    }

    public void setTaskID(String mTaskID) {
        this.mTaskID = mTaskID;
    }

    public boolean isFree(){
        return mStatus == (byte)101;
    }
    public boolean isDone(){
        return mStatus == (byte)103;
    }

    public boolean isBooked(){
        return mStatus == (byte)102;
    }

   public Task getCopy(){
    return new Task(mContractorId, mContractorName, mContractorPhone, mClientPhone, mClientName,
            mTaskID, mTaskDescription, mCreationDate, mDeadlineDate, mStartDate, mStatus, mReward);
    }
}