package es.upm.vvaquerizo.keyless;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import androidx.core.app.NotificationCompat;

public class NotificationsManager {
    static private NotificationsManager notificationsManager = null;
    private NotificationManager notificationManager;
    private Context context;

    public static NotificationsManager getInstance(Context context) {
        if (notificationsManager == null) {
            notificationsManager = new NotificationsManager(context);
        }
        return notificationsManager;
    }

    private NotificationsManager(Context context) {
        this.context = context;
        initializeNotificationsManager();
    }

    private void initializeNotificationsManager() {
        createChannel();
    }

    private void createChannel() {
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        final String channelId = "1";
        final CharSequence channelName = context.getResources().getString(R.string.channel_name);
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel notificationChannel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notificationChannel = new NotificationChannel(channelId, channelName, importance);
            notificationChannel.enableLights(true);
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    public void sendNotificationUpdatedCode(DoorData doorData) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"1")
                .setSmallIcon(R.drawable.ic_stat_name)
                .setContentTitle("Código actualizado")
                .setContentText("El código de la puerta " + doorData.name + " se ha actualizado")
                .setLargeIcon(BitmapFactory.decodeByteArray(doorData.image,0,doorData.image.length));
        notificationManager.notify(1,builder.build());
    }
}
