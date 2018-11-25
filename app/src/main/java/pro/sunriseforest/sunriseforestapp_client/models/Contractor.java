package pro.sunriseforest.sunriseforestapp_client.models;

import com.google.gson.annotations.SerializedName;

public class Contractor {
    @SerializedName("sr_user_id")
    private int mId;

    @SerializedName("sr_user_email")
    private  String mEmail;

    @SerializedName("sr_user_name")
    private String mName;

    @SerializedName("sr_user_phone")
    private String mPhoneNumber;

    public Contractor(String email, String name, String phoneNumber) {

        mEmail = email;
        mName = name;
        mPhoneNumber = phoneNumber;
    }

    public Contractor(String email, int id) {
        mId = id;
        mEmail = email;
    }

    public String getName() {
        return mName;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }


    public String getEmail() {
        return mEmail;
    }

    public int getId() {
        return mId;
    }

    public Contractor copy(){
        return new Contractor(mEmail, mId);
    }

}
