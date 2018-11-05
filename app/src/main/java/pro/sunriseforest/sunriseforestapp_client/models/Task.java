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

    @SerializedName("isBooked")
    private boolean mIsBooked;

    @SerializedName("reward")
    private int mReward;


    public Task(int id,
                String taskDescription,
                boolean isBooked,
                String creationDate,

                int reward) {
        mId = id;
        mTaskDescription = taskDescription;
        mIsBooked = isBooked;
        mCreationDate = creationDate;
        mReward = reward;
    }

    public Task(int id, String taskDescription, String creationDate, String name, String address, int reward) {
        mId = id;
        mTaskDescription = taskDescription;
        mCreationDate = creationDate;

        mIsBooked = false;
        mReward = reward;
    }

    public int getReward() {
        return mReward;
    }

    public void setReward(int reward) {
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

    public boolean isBooked() {
        return mIsBooked;
    }

    public void setBooked(boolean booked) {
        mIsBooked = booked;
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

        return new Task(mId, mTaskDescription, mIsBooked,
                mCreationDate, mReward);
    }


}