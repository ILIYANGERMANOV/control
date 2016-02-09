package custom.services;

import android.app.IntentService;
import android.content.Intent;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.csipsimple.ui.incall.InCallActivity;

import java.util.Date;
import java.util.concurrent.ExecutionException;

import custom.extras.MyDebugger;
import custom.extras.utils.AlarmUtils;
import custom.extras.utils.DateUtils;
import custom.extras.utils.FileUtils;
import custom.extras.utils.MyUtils;
import custom.extras.utils.SharedPrefUtils;
import custom.extras.values.Constants;
import custom.extras.values.Preferences;

public class UpdateService extends IntentService {
    public UpdateService() {
        super("UpdateService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        //check for update
        MyDebugger.log("Update service executing");
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String versionUrl = Constants.SERVER_IP + Constants.VERSION_API;
        String downloadUrl = Constants.SERVER_IP + Constants.DOWNLOAD_API;

        RequestFuture<String> future = RequestFuture.newFuture();
        StringRequest getVersionRequest = new StringRequest(Request.Method.GET, versionUrl, future, future);
        requestQueue.add(getVersionRequest);

        try {
            String version = future.get(); // this will block (forever)

            int appVersion = MyUtils.getAppVersionCode(this);
            MyDebugger.log("server version", version);
            MyDebugger.log("app version", appVersion);
            if (Integer.parseInt(version) > appVersion) {
                MyDebugger.log("updating scheduled");
                if (!InCallActivity.IS_IN_CALL) {
                    //user is not talking atm, we can proceed updating
                    FileUtils.downloadFileFromUrl(this, downloadUrl);
                }
            }

        } catch (InterruptedException | ExecutionException e) {
            MyDebugger.log("Exception while getting version", e.getMessage());
        }

        //schedule next update check
        Date nextUpdateCheckDate = DateUtils.getNextDateToCheckForUpdate();
        SharedPrefUtils.saveToSharePref(this, Preferences.NEXT_UPDATE_DATE_PREFERENCE,
                DateUtils.formatDateInSQLiteFormat(nextUpdateCheckDate));
        AlarmUtils.setAlarmForUpdate(this, nextUpdateCheckDate);
    }
}
