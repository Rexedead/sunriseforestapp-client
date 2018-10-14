package pro.sunriseforest.sunriseforestapp_client.options;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.util.Log;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import pro.sunriseforest.sunriseforestapp_client.R;
import pro.sunriseforest.sunriseforestapp_client.models.Task;
import pro.sunriseforest.sunriseforestapp_client.models.Token;

public class SharedPreferenceHelper {

    private static final String SHARED_PREF_NAME = "sunriseforestapp-client";
    private static final String TOKEN_TAG = "TOKEN_TAG";
    private static final String TOKENS_TAG = "TOKENS_TAG";


    private SharedPreferences mSharedPreferences;

    public SharedPreferenceHelper(Context context){
        mSharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    public @Nullable Token getToken(){
        Token token;
        String jsonToken = mSharedPreferences.getString(TOKEN_TAG, "");
        if(jsonToken.length() == 0){
            return null;
        }
        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<Token> jsonAdapter = moshi.adapter(Token.class);
        try {
            token = jsonAdapter.fromJson(jsonToken);
        } catch (IOException e) {
            Log.e("SharedPreferenceHelper", "не удалось перевести json в Token");
            return null;
        }
        return token;

    }

    public void saveToken(Token token){
        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<Token> jsonAdapter = moshi.adapter(Token.class);
        String jsonToken = jsonAdapter.toJson(token);
        mSharedPreferences.edit()
                .putString(TOKEN_TAG, jsonToken)
                .apply();
    }

    public void _saveTokenForServerHelper(Token token){
        List<Token> list = new ArrayList<>();

        Moshi moshi = new Moshi.Builder().build();
        Type type = Types.newParameterizedType(List.class, Token.class);
        JsonAdapter<List> jsonAdapter = moshi.adapter(type);
        String tokensJson = mSharedPreferences.getString(TOKENS_TAG, "");

        if(tokensJson.length() == 0){
            list.add(token);
            String json = jsonAdapter.toJson(list);
            mSharedPreferences.edit().putString(TOKENS_TAG, json).apply();
            return;
        }

        try {
            list = jsonAdapter.fromJson(tokensJson);
        } catch (IOException e) {
            e.printStackTrace();
        }

        list.add(token);
        String json = jsonAdapter.toJson(list);
        mSharedPreferences.edit().putString(TOKENS_TAG, json).apply();

    }
    public List<Token> _getTokensForServerHelper(){
        String json;

        json = mSharedPreferences.getString(TOKENS_TAG, "");
        if(json.equals("")){
            return new ArrayList<>();
        }


        Moshi moshi = new Moshi.Builder().build();
        Type type = Types.newParameterizedType(List.class, Token.class);
        JsonAdapter<List> jsonAdapter = moshi.adapter(type);
        List<Token> tokens = null;
        try {
            tokens = jsonAdapter.fromJson(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tokens;
    }
}
