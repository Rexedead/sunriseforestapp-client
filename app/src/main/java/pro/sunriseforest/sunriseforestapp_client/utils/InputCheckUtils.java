package pro.sunriseforest.sunriseforestapp_client.utils;

import android.text.TextUtils;

public class InputCheckUtils {

    public static final int CORRECTLY = 1;
    public static final int INCORRECTLY = 2;

    public static int checkEmail(String email){
        if(TextUtils.isEmpty(email)) return INCORRECTLY;

        return email.split("@").length == 2 ? CORRECTLY : INCORRECTLY;
    }


}
