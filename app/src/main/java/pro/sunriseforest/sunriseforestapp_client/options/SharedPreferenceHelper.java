package pro.sunriseforest.sunriseforestapp_client.options;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.StringRes;

import pro.sunriseforest.sunriseforestapp_client.R;

public class SharedPreferenceHelper {

    private static final String SHARED_PREF_NAME = "sunriseforestapp-client";
    private SharedPreferences mSharedPreferences;

    public SharedPreferenceHelper(Context context){
        mSharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }
}
