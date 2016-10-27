package commanutil.utl;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.zhenbeiju.commanutil.R;

import commanutil.base.BaseApplication;


public class NotificationUtil {
    private NotificationManager notificationManager;
    private Context mContext;
    private static NotificationUtil notificationUtil;

    public static NotificationUtil getInstance() {
        if (notificationUtil == null) {
            notificationUtil = new NotificationUtil();
            notificationUtil.notificationManager = (NotificationManager) BaseApplication.context.getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return notificationUtil;
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void showMsg(String title, String msg, int id, int drawableid, PendingIntent pendingIntent) {
        if (drawableid == 0) {
            drawableid = R.mipmap.notify_normal;
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(BaseApplication.context).setDefaults(Notification.DEFAULT_ALL);
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
     * @param drawableId
     * @param pendingIntent
     */
    public void showError(String title, String msg, int id, int drawableId, PendingIntent pendingIntent) {
        if (drawableId == 0) {
            drawableId = R.mipmap.notify_error;
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(BaseApplication.context).setDefaults(Notification.DEFAULT_ALL);
        builder.setContentTitle(title).setContentText(msg).setSmallIcon(drawableId);
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
        NotificationCompat.Builder builder = new NotificationCompat.Builder(BaseApplication.context).
                setContentTitle(title).setContentText(title + msg).setSmallIcon(drawableid);
        builder.setContentIntent(pendingIntent);
        builder.setVibrate(new long[]{100, 100, 200, 100,});
        builder.setTicker(msg);
        Notification notification = builder.build();
        notificationManager.notify(id, notification);
    }

    public void showProgress(String title, String msg, int drawableid, int id, int progress, PendingIntent pendingIntent) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(BaseApplication.context);
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
