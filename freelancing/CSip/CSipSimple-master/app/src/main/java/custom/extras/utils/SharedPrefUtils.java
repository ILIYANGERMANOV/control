package custom.extras.utils;

import android.content.Context;
import android.content.SharedPreferences;

import custom.extras.values.Constants;
import custom.extras.values.Preferences;

public class SharedPrefUtils {
    public static long readMyDownloadId(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(Preferences.PREFERENCES_FILE, Context.MODE_PRIVATE);
        return sharedPref.getLong(Preferences.MY_DOWNLOAD_ID, Constants.NO_DOWNLOAD_ID);
    }

    public static String readNextUpdateDate(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(Preferences.PREFERENCES_FILE, Context.MODE_PRIVATE);
        return sharedPref.getString(Preferences.NEXT_UPDATE_DATE_PREFERENCE, Constants.NO_DATE);
    }

    public static int readPasswordTries(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(Preferences.PREFERENCES_FILE, Context.MODE_PRIVATE);
        return sharedPref.getInt(Preferences.PASSWORD_TRIES_PREFERENCE, 0);
    }

    public static void saveToSharePref(Context context, String key, String value) {
        SharedPreferences.Editor editor = getSharedPrefEditor(context);
        editor.putString(key, value);
        editor.commit();
    }

    public static void saveToSharePref(Context context, String key, int value) {
        SharedPreferences.Editor editor = getSharedPrefEditor(context);
        editor.putInt(key, value);
        editor.commit();
    }

    public static void saveToSharePref(Context context, String key, long value) {
        SharedPreferences.Editor editor = getSharedPrefEditor(context);
        editor.putLong(key, value);
        editor.commit();
    }

    private static SharedPreferences.Editor getSharedPrefEditor(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(Preferences.PREFERENCES_FILE, Context.MODE_PRIVATE);
        return sharedPref.edit();
    }
}
