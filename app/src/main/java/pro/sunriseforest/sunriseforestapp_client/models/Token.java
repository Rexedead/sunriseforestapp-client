package pro.sunriseforest.sunriseforestapp_client.models;

public class Token {

    private int mTokenId;
    private String mPassword;
    private  String mLogin;

    public Token(String login, String password, int tokenId ) {
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

}
