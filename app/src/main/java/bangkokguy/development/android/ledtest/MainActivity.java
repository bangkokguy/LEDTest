package bangkokguy.development.android.ledtest;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    static final String TAG = MainActivity.class.getSimpleName();

    NotificationManagerCompat notificationManagerCompat;
    NotificationCompat.Builder mBuilder;
    Notification noti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mBuilder = new NotificationCompat.Builder(this, "channel")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("textTitle")
                .setContentText("textContent")
                .setStyle(new NotificationCompat.BigTextStyle()
                .bigText("Much longer text that cannot fit one line..."))
                .setOngoing(true)
                .setLights(Color.argb(255,0,255,0), 0xffff, 0x0000)
                .setSound(getNotificationSoundUri(this))
                .setChannelId("a")
                .setPriority(NotificationCompat.PRIORITY_MAX);

            notificationManagerCompat =
                    NotificationManagerCompat.from(this);

            noti = mBuilder.build();

            /*noti.flags = 1;
            noti.ledOffMS = 0x00;
            noti.ledOnMS =  0xff;
            noti.ledARGB =  0xffffffff;
            noti.color = Color.RED;*/
            notificationManagerCompat.notify(1, noti);

        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
                    Log.d(TAG, Intent.ACTION_SCREEN_OFF);

                    notificationManagerCompat.notify(1, noti);
                } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
                    Log.d(TAG, Intent.ACTION_SCREEN_ON);
                }
            }
        }, intentFilter);

    }

    private static Uri getNotificationSoundUri(Context context) {
        int resID = R.raw.chafing;
        return resourceToUri(context, resID);
    }

    private static Uri resourceToUri(Context context, int resID) {
        return Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
                context.getResources().getResourcePackageName(resID) + '/' +
                context.getResources().getResourceTypeName(resID) + '/' +
                context.getResources().getResourceEntryName(resID));
    }
}
