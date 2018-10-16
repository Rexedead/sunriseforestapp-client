package pro.sunriseforest.sunriseforestapp_client.models;

public class Task {
    private int mId;
    private String mTextTask;
    private boolean mIsBooked;
    private String mDate;



    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getDate() {
        return mDate;
    }

    public String getTextTask() {
        return mTextTask;
    }

    public void setTextTask(String textTask) {
        mTextTask = textTask;
    }

    public boolean isBooked() {
        return mIsBooked;
    }

    public void setBooked(boolean booked) {
        mIsBooked = booked;
    }

    public Task copy(){
        return new Task(this.mId, this.mTextTask, this.mDate, this.mIsBooked);
    }

    public Task(int id, String textTask) {
        mId = id;
        mTextTask = textTask;
        mIsBooked = false;
    }
    public Task(int id, String textTask, String date, boolean isBooked) {
        mId = id;
        mTextTask = textTask;
        mDate = date;
        mIsBooked = isBooked;
    }

}