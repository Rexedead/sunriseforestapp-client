package pro.sunriseforest.sunriseforestapp_client.models;

public class Ad {
    private int mId;
    private String mTextAd;
    private boolean mIsBooked;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getTextAd() {
        return mTextAd;
    }

    public void setTextAd(String textAd) {
        mTextAd = textAd;
    }

    public boolean isBooked() {
        return mIsBooked;
    }

    public void setBooked(boolean booked) {
        mIsBooked = booked;
    }

    public Ad(int id, String textAd) {
        mId = id;
        mTextAd = textAd;
        mIsBooked = false;
    }
}
