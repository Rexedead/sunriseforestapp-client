package pro.sunriseforest.sunriseforestapp_client.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import pro.sunriseforest.sunriseforestapp_client.models.SunriseNotification;
import pro.sunriseforest.sunriseforestapp_client.models.User;

public class SharedPreferenceHelper implements ISharedPreferenceHelper{
public static final String TAG = "%%%/SharedPrefHelper";
    private static final String SHARED_PREF_NAME = "pro.sunriseforest.sunriseforestapp-client";
    private static final String TOKEN_TAG = "pro.sunriseforest.sunriseforestapp_client_token_tag";
    private static final String MY_USER = "pro.sunriseforest.sunriseforestapp_client_my_user";
    private static final String SETTINGS_TAG = "pro.sunriseforest.sunriseforestapp_client_settings_tag";

    private static final String NOTIFICATIONS_TAG = "pro.sunriseforest.sunriseforestapp_client_notifications_tag";



    private SharedPreferences mSharedPreferences;

    private Moshi mMoshi = new Moshi.Builder().build();

    public static SharedPreferenceHelper getInstance(Context context){
        return new SharedPreferenceHelper(context);
    }


    public SharedPreferenceHelper(Context context){
        mSharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        if(getSettings() == null)
            setDefaultSettings();

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

    private void saveSettings(@NonNull Settings settings) {
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

    public void updateSettings(OnUpdatedSettings update){
        Settings settings = getSettings();
        update.update(settings);
        saveSettings(settings);
    }

    public void setDefaultSettings(){
        Settings defaultSettings = new Settings(true);

        saveSettings(defaultSettings);
    }

     public interface OnUpdatedSettings{
        void update(Settings settings);
    }

    //notifications

    public List<SunriseNotification> getNotifications(){
        List<SunriseNotification> notifications;
        String jsonNotifications = mSharedPreferences.getString(NOTIFICATIONS_TAG, "");
        if(jsonNotifications.length() == 0){
            return null;
        }
        Type type = Types.newParameterizedType(List.class, SunriseNotification.class);
        JsonAdapter<List> jsonAdapter = mMoshi.adapter(type);
        try {
            notifications = jsonAdapter.fromJson(jsonNotifications);
        } catch (IOException e) {
            Log.e(TAG, "не удалось перевести json в Settings");
            return null;
        }


        return notifications;
    }

    public void saveNotifications(List<SunriseNotification> notifications){

        Type type = Types.newParameterizedType(List.class, SunriseNotification.class);
        JsonAdapter<List> jsonAdapter = mMoshi.adapter(type);

        String jsonNotifications = jsonAdapter.toJson(notifications);
        mSharedPreferences.edit()
                .putString(NOTIFICATIONS_TAG, jsonNotifications)
                .apply();
    }

}
