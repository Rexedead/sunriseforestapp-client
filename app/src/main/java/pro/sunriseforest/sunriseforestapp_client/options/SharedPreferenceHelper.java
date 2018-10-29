package pro.sunriseforest.sunriseforestapp_client.options;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.util.Log;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import pro.sunriseforest.sunriseforestapp_client.models.User;

public class SharedPreferenceHelper {

    private static final String SHARED_PREF_NAME = "sunriseforestapp-client";
    private static final String TOKEN_TAG = "TOKEN_TAG";
    private static final String TOKENS_TAG = "TOKENS_TAG";


    private SharedPreferences mSharedPreferences;

    public SharedPreferenceHelper(Context context){
        mSharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    public @Nullable
    User getToken(){
        User user;
        String jsonToken = mSharedPreferences.getString(TOKEN_TAG, "");
        if(jsonToken.length() == 0){
            return null;
        }
        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<User> jsonAdapter = moshi.adapter(User.class);
        try {
            user = jsonAdapter.fromJson(jsonToken);
        } catch (IOException e) {
            Log.e("SharedPreferenceHelper", "не удалось перевести json в User");
            return null;
        }
        return user;

    }

    public void saveToken(User user){
        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<User> jsonAdapter = moshi.adapter(User.class);
        String jsonToken = jsonAdapter.toJson(user);
        mSharedPreferences.edit()
                .putString(TOKEN_TAG, jsonToken)
                .apply();
    }




    public void _saveTokenForServerHelper(User user){
        List<User> list = new ArrayList<>();

        Moshi moshi = new Moshi.Builder().build();
        Type type = Types.newParameterizedType(List.class, User.class);
        JsonAdapter<List> jsonAdapter = moshi.adapter(type);
        String tokensJson = mSharedPreferences.getString(TOKENS_TAG, "");

        if(tokensJson.length() == 0){
            list.add(user);
            String json = jsonAdapter.toJson(list);
            mSharedPreferences.edit().putString(TOKENS_TAG, json).apply();
            return;
        }

        try {
            list = jsonAdapter.fromJson(tokensJson);
        } catch (IOException e) {
            e.printStackTrace();
        }

        list.add(user);
        String json = jsonAdapter.toJson(list);
        mSharedPreferences.edit().putString(TOKENS_TAG, json).apply();

    }
    public List<User> _getTokensForServerHelper(){
        String json;

        json = mSharedPreferences.getString(TOKENS_TAG, "");
        if(json.equals("")){
            return new ArrayList<>();
        }


        Moshi moshi = new Moshi.Builder().build();
        Type type = Types.newParameterizedType(List.class, User.class);
        JsonAdapter<List> jsonAdapter = moshi.adapter(type);
        List<User> users = null;
        try {
            users = jsonAdapter.fromJson(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }
}
