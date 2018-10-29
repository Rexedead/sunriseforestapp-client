package pro.sunriseforest.sunriseforestapp_client.models;

public class User {

    private int mTokenId;
    private String mPassword;
    private  String mLogin;

    public User(String login, String password, int tokenId ) {
        mTokenId = tokenId;
        mPassword = password;
        mLogin = login;
    }

    public String getPassword() {
        return mPassword;
    }

    public String getLogin() {
        return mLogin;
    }

    public int getTokenId() {
        return mTokenId;
    }

    public User copy(){
        return new User(mLogin,mPassword ,mTokenId );
    }

}
