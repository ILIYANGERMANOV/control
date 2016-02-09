package custom.services;

import android.app.IntentService;
import android.content.Intent;

import java.util.Date;

import custom.extras.MyDebugger;
import custom.extras.utils.AlarmUtils;
import custom.extras.utils.DateUtils;
import custom.extras.utils.SharedPrefUtils;
import custom.extras.values.Constants;
import custom.receivers.BootWakefulBroadcastReceiver;

public class ResetAlarmService extends IntentService {
    //!NOTE because the service was started by WakefulBroadcastReceiver, the wake lock must be released
    // by calling completeWakefulIntent().

    public ResetAlarmService() {
        super("ResetAlarmService"); //name used only for debugging
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Date nextUpdateDate;
        String nextUpdateDateString = SharedPrefUtils.readNextUpdateDate(this);
        if (!nextUpdateDateString.equals(Constants.NO_DATE)) {
            nextUpdateDate = DateUtils.parseDateFromSQLiteFormat(nextUpdateDateString);
        } else {
            nextUpdateDate = new Date();
            MyDebugger.log("ResetAlarmService(): scheduled from current date");
        }
        AlarmUtils.setAlarmForUpdate(this, nextUpdateDate);
        BootWakefulBroadcastReceiver.completeWakefulIntent(intent);//releases the wake lock
    }
}
