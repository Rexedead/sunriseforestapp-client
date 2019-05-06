package pro.sunriseforest.sunriseforestapp_client.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import java.io.IOException;
import pro.sunriseforest.sunriseforestapp_client.models.User;

public class SharedPreferenceHelper implements ISharedPreferenceHelper{
public static final String TAG = "%%%/SharedPrefHelper";
    private static final String SHARED_PREF_NAME = "pro.sunriseforest.sunriseforestapp-client";
    private static final String TOKEN_TAG = "pro.sunriseforest.sunriseforestapp_client_token_tag";
    private static final String MY_USER = "pro.sunriseforest.sunriseforestapp_client_my_user";
    private static final String SETTINGS_TAG = "pro.sunriseforest.sunriseforestapp_client_settings_tag";

    private SharedPreferences mSharedPreferences;

    private Moshi mMoshi = new Moshi.Builder().build();

    public SharedPreferenceHelper(Context context){
        mSharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    public @Nullable
    User getUser(){
        User user;
        String jsonUser = mSharedPreferences.getString(MY_USER, "");
        if(jsonUser.length() == 0){
            return null;
        }
        JsonAdapter<User> jsonAdapter = mMoshi.adapter(User.class);
        try {
            user = jsonAdapter.fromJson(jsonUser);
        } catch (IOException e) {
            Log.e(TAG, "не удалось перевести json в User");
            return null;
        }
        return user;

    }

    public @Nullable String getToken(){

        return  mSharedPreferences.getString(TOKEN_TAG, "");
    }

    private void saveToken(String token){

        mSharedPreferences.edit()
                .putString(TOKEN_TAG, token)
                .apply();
    }

    private void removeToken(){
        mSharedPreferences.edit()
                .remove(TOKEN_TAG)
                .apply();
    }

    public void saveUser(@NonNull User user){

        String token = user.getToken();
        saveToken(token);

        JsonAdapter<User> jsonAdapter = mMoshi.adapter(User.class);

        String jsonUser = jsonAdapter.toJson(user);
        mSharedPreferences.edit()
                .putString(MY_USER, jsonUser)
                .apply();
    }

    public void removeUser(){
        mSharedPreferences.edit()
        .remove(MY_USER)
        .apply();
        removeToken();
    }

    @Override
    public void saveSettings(@NonNull Settings settings) {
        JsonAdapter<Settings> jsonAdapter = mMoshi.adapter(Settings.class);

        String jsonSettings = jsonAdapter.toJson(settings);
        mSharedPreferences.edit()
                .putString(SETTINGS_TAG, jsonSettings)
                .apply();

    }

    public Settings getSettings(){
        Settings settings;
        String jsonSettings = mSharedPreferences.getString(SETTINGS_TAG, "");
        if(jsonSettings.length() == 0){
            return null;
        }
        JsonAdapter<Settings> jsonAdapter = mMoshi.adapter(Settings.class);
        try {
            settings = jsonAdapter.fromJson(jsonSettings);
        } catch (IOException e) {
            Log.e(TAG, "не удалось перевести json в Settings");
            return null;
        }
        return settings;
    }

}
