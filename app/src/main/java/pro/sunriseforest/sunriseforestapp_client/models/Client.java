package pro.sunriseforest.sunriseforestapp_client.models;


import com.squareup.moshi.Json;

public class Client {

    @Json(name = "_id")
    private int mId;

    @Json(name = "sr_client_name")
    private String mName;

    @Json(name = "sr_client_phone")
    private String mPhoneNumber;

    @Json(name = "sr_client_email")
    private String mEmail;


    public Client(String name, String phoneNumber) {
        mName = name;
        mPhoneNumber = phoneNumber;
    }

    public String getEmail() {
        return mEmail;
    }

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }
}
