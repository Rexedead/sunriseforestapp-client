package pro.sunriseforest.sunriseforestapp_client.models;

import java.util.ArrayList;
import java.util.List;

public class Task {

    private int mId;
    private String mTextTask;
    private boolean mIsBooked;
    private String mDate;
    private String mName;
    private String mAddress;
    private int mReward;


    public Task(int id,
                String textTask,
                boolean isBooked,
                String date,
                String name,

                String address,
                int reward) {
        mId = id;
        mTextTask = textTask;
        mIsBooked = isBooked;
        mDate = date;
        mName = name;
        mAddress = address;
        mReward = reward;
    }




    public Task(int id, String textTask, String date, String name, String address, int reward) {
        mId = id;
        mTextTask = textTask;
        mDate = date;
        mName = name;
        mAddress = address;

        mIsBooked = false;
        mReward = reward;


    }

    public int getReward() {
        return mReward;
    }

    public void setReward(int reward) {
        mReward = reward;
    }


    public String getName() {
        return mName;
    }


    public String getAddress() {
        return mAddress;
    }

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


        return new Task(mId, mTextTask, mIsBooked,
                mDate, mName,mAddress, mReward);
    }


}