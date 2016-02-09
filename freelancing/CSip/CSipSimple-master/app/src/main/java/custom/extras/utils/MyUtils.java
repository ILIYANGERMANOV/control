package custom.extras.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import custom.extras.MyDebugger;
import custom.extras.values.Constants;
import custom.extras.values.Preferences;

public class MyUtils {

    public static void incrementPasswordTries(Context context) {
        int tries = getPasswordTries(context);
        setPasswordTries(context, ++tries);
    }

    public static void resetPasswordTries(Context context) {
        setPasswordTries(context, 0);
    }


    public static int getPasswordTries(Context context) {
        return SharedPrefUtils.readPasswordTries(context);
    }

    private static void setPasswordTries(Context context, int value) {
        SharedPrefUtils.saveToSharePref(context, Preferences.PASSWORD_TRIES_PREFERENCE, value);
    }

    public static String parseContactName(String remoteContactFull) {
        //TODO: need refactor (RENAME vars)
        String[] split = remoteContactFull.split("<");
        if (split.length == 2) {
            return parseFrom(split[1]);
        } else {
            return parseFrom(remoteContactFull);
        }
    }

    private static String parseFrom(String from) {
        String[] fromSplit = from.split("@");
        if (fromSplit.length > 0) {
            String[] sipContactNameSplit = fromSplit[0].split(":");
            if (sipContactNameSplit.length > 1) {
                String parsedName = sipContactNameSplit[1];
                if (!parsedName.trim().equals(Constants.OPERATOR_UNKNOWN)) {
                    return parsedName;
                } else {
                    return Constants.OPERATOR;
                }
            } else {
                return Constants.ERROR;
            }
        } else {
            return Constants.ERROR;
        }
    }

    public static int dpToPixels(Context context, int dp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static int getAppVersionCode(Context context) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            MyDebugger.log("exception while getting appVersionCode", e.getMessage());
            e.printStackTrace();
        }
        int appVersion = -1;
        if (packageInfo != null) {
            appVersion = packageInfo.versionCode;
        }
        return appVersion;
    }
}
