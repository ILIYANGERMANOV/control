package custom.extras.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Date;

import custom.services.UpdateService;

public class AlarmUtils {
    public static final int UPDATE_ALARM_ID = 1;

    public static void setAlarmForUpdate(Context context, Date date) {
        Intent intent = new Intent(context, UpdateService.class);

        PendingIntent pendingIntent = PendingIntent.getService(context, UPDATE_ALARM_ID,
                intent, PendingIntent.FLAG_CANCEL_CURRENT); //FLAG_CANCEL_CURRENT, cuz when updating date it must cancel previous intent

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, date.getTime(), pendingIntent);//RTC_WAKEUP so UpdateService will be executed even when the phone is locked
    }
}
