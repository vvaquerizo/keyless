package es.upm.vvaquerizo.keyless;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;

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
        Intent intent = new Intent(context, DoorDetailsActivity.class);
        intent.putExtra("door_id", doorData.id - 1);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, doorData.id, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        String message = String.format(context.getResources().getString(R.string.code_updated_info),doorData.name);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"1")
                .setSmallIcon(R.drawable.ic_stat_name)
                .setContentTitle(context.getResources().getString(R.string.code_updated))
                .setContentText(message)
                .setLargeIcon(BitmapFactory.decodeByteArray(doorData.image,0,doorData.image.length))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message));
        notificationManager.notify(doorData.id,builder.build());
    }
}
