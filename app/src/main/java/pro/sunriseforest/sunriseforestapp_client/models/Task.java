package pro.sunriseforest.sunriseforestapp_client.models;

import java.util.ArrayList;
import java.util.List;

public class Task {

    private int mId;
    private String mTextTask;
    private boolean mIsBooked;
    private String mDate;
    private String mName;
    private List<Comment> mCommentList;
    private String mAddress;
    private String mDeadline;
    private int mReward;


    public Task(int id,
                String textTask,
                boolean isBooked,
                String date,
                String name,
                List<Comment> commentList,
                String address,
                String deadLine,
                int reward) {
        mId = id;
        mTextTask = textTask;
        mIsBooked = isBooked;
        mDate = date;
        mName = name;
        mDeadline = deadLine;
        mCommentList = commentList;
        mAddress = address;
        mReward = reward;
    }


    public String getDeadline() {
        return mDeadline;
    }

    public void setDeadline(String deadline) {
        mDeadline = deadline;
    }

    public Task(int id, String textTask, String date, String name, String deadLine, String address, int reward) {
        mId = id;
        mTextTask = textTask;
        mDate = date;
        mName = name;
        mAddress = address;
        mDeadline = deadLine;

        mCommentList = new ArrayList<>();
        mIsBooked = false;
        mReward = reward;


    }

    public int getReward() {
        return mReward;
    }

    public void setReward(int reward) {
        mReward = reward;
    }

    public void addComment(Comment comment){
        mCommentList.add(comment);
    }

    public String getName() {
        return mName;
    }

    public List<Comment> getCommentList() {
        return mCommentList;
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
        List<Comment> copyComments = new ArrayList<>();
        for(Comment com: mCommentList){
            copyComments.add(com.copy());
        }

        return new Task(mId, mTextTask, mIsBooked,
                mDate, mName, copyComments,mAddress, mDeadline, mReward);
    }


}