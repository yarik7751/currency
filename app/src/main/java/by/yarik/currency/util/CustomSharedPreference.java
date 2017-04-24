package by.yarik.currency.util;

import android.content.Context;
import android.content.SharedPreferences;

public class CustomSharedPreference {

    public static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(Const.SHARED_PREFERENCES_TITLE, Context.MODE_PRIVATE);
    }

    public static void setSelectCurrencies(Context context, String data) {
        getSharedPreferences(context).edit().putString(Const.SP_SELECT_CURRENCIES, data).commit();
    }

    public static String getSelectCurrencies(Context context) {
        return getSharedPreferences(context).getString(Const.SP_SELECT_CURRENCIES, "");
    }
}
