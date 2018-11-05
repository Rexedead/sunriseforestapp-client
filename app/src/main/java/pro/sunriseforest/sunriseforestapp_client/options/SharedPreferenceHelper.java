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

import pro.sunriseforest.sunriseforestapp_client.models.Contractor;

public class SharedPreferenceHelper {

    private static final String SHARED_PREF_NAME = "sunriseforestapp-client";
    private static final String TOKEN_TAG = "TOKEN_TAG";
    private static final String MY_CONTRACTOR_TAG = "MY_CONTRACTOR_TAG";


    private SharedPreferences mSharedPreferences;

    public SharedPreferenceHelper(Context context){
        mSharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    public @Nullable
    Contractor getMyConractor(){
        Contractor contractor;
        String jsonToken = mSharedPreferences.getString(MY_CONTRACTOR_TAG, "");
        if(jsonToken.length() == 0){
            return null;
        }
        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<Contractor> jsonAdapter = moshi.adapter(Contractor.class);
        try {
            contractor = jsonAdapter.fromJson(jsonToken);
        } catch (IOException e) {
            Log.e("SharedPreferenceHelper", "не удалось перевести json в Contractor");
            return null;
        }
        return contractor;

    }

    public @Nullable String getToken(){
        String token = mSharedPreferences.getString(TOKEN_TAG, "");
        if(token.length() == 0){
            return null;
        }
        return token;
    }

    public void saveToken(String token){

        mSharedPreferences.edit()
                .putString(TOKEN_TAG, token)
                .apply();
    }



    public void saveContractor(Contractor contractor){
        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<Contractor> jsonAdapter = moshi.adapter(Contractor.class);
        String jsonContractor = jsonAdapter.toJson(contractor);
        mSharedPreferences.edit()
                .putString(MY_CONTRACTOR_TAG, jsonContractor)
                .apply();
    }


    public void removeContactor(){
        mSharedPreferences.edit()
                .putString(MY_CONTRACTOR_TAG, null)
                .apply();
    }

}
