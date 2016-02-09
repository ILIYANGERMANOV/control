package custom.extras.utils;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;

import java.io.File;

import custom.extras.MyDebugger;
import custom.extras.values.Preferences;

public class FileUtils {
    public static final String UPDATE_APK_NAME = "csip_update.apk";
    public static final String UPDATE_APK_FILE_PATH =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).
                    getAbsolutePath() + "/" + UPDATE_APK_NAME;

    public static void downloadFileFromUrl(Context context, String url) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setTitle("CSipSimple update");
        request.setDescription("CSipSimple is downloading update.");
        // in order for this if to run, you must use the android 3.2 to compile your app
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        }

        deleteFile(UPDATE_APK_FILE_PATH); //delete old apk file
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, UPDATE_APK_NAME);

        // get download service and enqueue file
        DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        SharedPrefUtils.saveToSharePref(context, Preferences.MY_DOWNLOAD_ID, manager.enqueue(request));
    }

    public static void installApk(Context context) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(UPDATE_APK_FILE_PATH)), "application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


    public static boolean deleteFile(String filePath) {
        return deleteFile(new File(filePath));
    }

    public static boolean deleteFile(File fileToDelete) {
        if (fileToDelete.exists()) {
            //file exists, try to delete it
            if (!fileToDelete.delete()) {
                //deletion has failed
                MyDebugger.log("deleteFile()", "failed to delete file");
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

}
