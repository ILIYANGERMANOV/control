package custom.receivers;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

import custom.services.ResetAlarmService;

public class BootWakefulBroadcastReceiver extends WakefulBroadcastReceiver {
    //!NOTE use WakefulBroadcastReceiver instead of normal receiver, cuz there is a little chance
    //where ResetAlarmsService won't be started

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equalsIgnoreCase(Intent.ACTION_BOOT_COMPLETED)) {
            //boot completed, start resetAlarmService
            Intent resetAlarmServiceIntent = new Intent(context, ResetAlarmService.class);
            startWakefulService(context, resetAlarmServiceIntent); //startWakefulService() guarantees that service will start
        }
    }
}
