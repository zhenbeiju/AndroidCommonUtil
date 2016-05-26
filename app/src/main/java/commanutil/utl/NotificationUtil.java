package commanutil.utl;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.zhenbeiju.commanutil.R;
import commanutil.base.MyApplication;


public class NotificationUtil {
    private NotificationManager notificationManager;
    private Context mContext;
    private static NotificationUtil notificationUtil;

    public static NotificationUtil getInstance() {
        if (notificationUtil == null) {
            notificationUtil = new NotificationUtil();
            notificationUtil.notificationManager = (NotificationManager) MyApplication.context.getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return notificationUtil;
    }

//    /**
//     * @param drawableId
//     * @param flag
//     */
//    public void sendNotification(int drawableId, int flag, String title, String eventInfo, PendingIntent pendintIntent, int id) {
//        Notification notification = new Notification(drawableId, title, System.currentTimeMillis());
//        notification.setLatestEventInfo(mContext, title, eventInfo, pendintIntent);
//        notificationManager.notify(id, notification);
//    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void showMsg(String title, String msg, int id, int drawableid, PendingIntent pendingIntent) {
        if (drawableid == 0) {
            drawableid = R.mipmap.notify_normal;
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(MyApplication.context).setDefaults(Notification.DEFAULT_ALL);
        builder.setContentTitle(title).setContentText(msg).setSmallIcon(drawableid);
        builder.setContentIntent(pendingIntent);
        builder.setTicker(msg);
        Notification notification = builder.build();
        notificationManager.notify(id, notification);
    }
    /**
     * @param title
     * @param msg
     * @param id
     * @param drawableid
     * @param pendingIntent
     */
    public void showError(String title, String msg, int id, int drawableid, PendingIntent pendingIntent) {
        if (drawableid == 0) {
            drawableid = R.mipmap.notify_error;
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(MyApplication.context).setDefaults(Notification.DEFAULT_ALL);
        builder.setContentTitle(title).setContentText(msg).setSmallIcon(drawableid);
        builder.setContentIntent(pendingIntent);
        builder.setVibrate(new long[]{100, 500, 600, 500,});
        builder.setTicker(msg);
        Notification notification = builder.build();
        notificationManager.notify(id, notification);
    }

    public void showWarning(String title, String msg, int id, int drawableid, PendingIntent pendingIntent) {
        LogManager.e("show warning " + title + "|" + msg);
        if (drawableid == 0) {
            drawableid = R.mipmap.notify_warning;
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(MyApplication.context).
                setContentTitle(title).setContentText(title + msg).setSmallIcon(drawableid);
        builder.setContentIntent(pendingIntent);
        builder.setVibrate(new long[]{100, 100, 200, 100,});
        builder.setTicker(msg);
        Notification notification = builder.build();
        notificationManager.notify(id, notification);
    }

    public void showProgress(String title, String msg, int drawableid, int id, int progress, PendingIntent pendingIntent) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(MyApplication.context);
        mBuilder.setContentTitle(title)
                .setContentText(msg)
                .setSmallIcon(drawableid);
        mBuilder.setProgress(100, progress, false);
        mBuilder.setContentIntent(pendingIntent);
        Notification notification = mBuilder.build();
        notificationManager.notify(id, notification);
    }


    public void cancelNotity(int notifyID) {
        notificationManager.cancel(notifyID);
    }

}