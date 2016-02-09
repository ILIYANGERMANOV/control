package custom.receivers;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import custom.extras.MyDebugger;
import custom.extras.utils.FileUtils;
import custom.extras.utils.SharedPrefUtils;

public class DownloadCompletedBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        long myDownloadId = SharedPrefUtils.readMyDownloadId(context);
        Bundle extras = intent.getExtras();
        DownloadManager.Query q = new DownloadManager.Query();
        Long downloaded_id = extras.getLong(DownloadManager.EXTRA_DOWNLOAD_ID);
        if (myDownloadId == downloaded_id) { // so it is my file that has been completed
            q.setFilterById(downloaded_id);
            DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            Cursor c = manager.query(q);
            if (c.moveToFirst()) {
                int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
                if (status == DownloadManager.STATUS_SUCCESSFUL) {
                    // do any thing here
                    MyDebugger.log("hurray install update");
                    FileUtils.installApk(context);
                }
            }
            c.close();
        }
    }
}
