package pro.sunriseforest.sunriseforestapp_client.options;

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

import pro.sunriseforest.sunriseforestapp_client.models.Contractor;
import pro.sunriseforest.sunriseforestapp_client.models.User;

public class SharedPreferenceHelper {

    private static final String SHARED_PREF_NAME = "sunriseforestapp-client";
    private static final String TOKEN_TAG = "TOKEN_TAG";
    private static final String MY_USER_TAG = "MY_USER_TAG";


    private SharedPreferences mSharedPreferences;

    private Moshi mMoshi = new Moshi.Builder().build();

    public SharedPreferenceHelper(Context context){
        mSharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    public @Nullable
    User getMyUser(){
        User user;
        String jsonToken = mSharedPreferences.getString(MY_USER_TAG, "");
        if(jsonToken.length() == 0){
            return null;
        }
        JsonAdapter<User> jsonAdapter = mMoshi.adapter(User.class);
        try {
            user = jsonAdapter.fromJson(jsonToken);
        } catch (IOException e) {
            Log.e("SharedPreferenceHelper", "не удалось перевести json в User");
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
                .putString(TOKEN_TAG, null)
                .apply();
    }

    public void saveUser(@NonNull User user){

        String token = user.getToken();
        saveToken(token);

        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<User> jsonAdapter = moshi.adapter(User.class);

        String jsonUser = jsonAdapter.toJson(user);
        mSharedPreferences.edit()
                .putString(MY_USER_TAG, jsonUser)
                .apply();
    }


    public void removeUser(){
        mSharedPreferences.edit()
                .putString(MY_USER_TAG, null)
                .apply();

        removeToken();
    }

}
