package com.example.proj2;

import android.app.Notification;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.RemoteViews;

public class MainActivity extends AppCompatActivity {

    private int clickCounter = 0;
    private int clickToastCounter = 0;
    private NotificationManager mNotifyMgr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createNotificationChannel();
    }

    private String totalClicks() {
        return "total clicks so far: " + ++clickCounter;
    }

    private String totalToastClicks() {
        return "total clicks so far: " + ++clickToastCounter;
    }

    public void createNotificationChannel() {
        NotificationChannel channel = new NotificationChannel("Channel_ID", "name", NotificationManager.IMPORTANCE_DEFAULT);
        channel.setDescription("description");
        mNotifyMgr = getSystemService(NotificationManager.class);
        mNotifyMgr.createNotificationChannel(channel);
    }

    private void showNotification(Notification.Builder builder) {
        mNotifyMgr.notify(clickCounter, builder.build());
//        mNotifyMgr.notify(0, builder.build());
    }

    private Notification.Builder getBuilder(String title, String text) {
        return new Notification.Builder(this, "Channel_ID")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(text);
    }

    public void showBasicNotification(View view) {
        showNotification(getBuilder("basic notification", totalClicks()));
    }

    public void showCustomNotification(View view){
        String title = "custom notifiacation";
        String text = totalClicks();
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.custom_notification);
        remoteViews.setTextViewText(R.id.nTitle, title);
        remoteViews.setTextViewText(R.id.nTitle, text);
        remoteViews.setImageViewResource(R.id.imageLeft, android.R.drawable.presence_video_busy );
        remoteViews.setImageViewResource(R.id.imageRight, android.R.drawable.btn_star_big_on );

        // Create a Notification Builder with the custom content
        Notification.Builder builder = getBuilder(title, text);
        builder.setCustomContentView(remoteViews); // for regular-sized notifications
        builder.setCustomBigContentView(remoteViews); // for expanded/big notifications

        // Show the notification with the custom layout
        showNotification(builder);
    }

    public void showBasicToastNotification(View view) {
        String text = totalToastClicks();
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
    public void showCustomToastNotification(View view) {
        // Inflate the custom layout
        View customToastView = getLayoutInflater().inflate(R.layout.custom_toast_notification, null);

        // Customize the custom toast view
        ImageView icon = customToastView.findViewById(R.id.toastIcon);
        TextView text = customToastView.findViewById(R.id.toastText);
        icon.setImageResource(android.R.drawable.presence_video_busy); // Set your custom icon
        text.setText(totalToastClicks()); // Set the text

        // Create and show the custom toast
        Toast customToast = new Toast(this);
        customToast.setDuration(Toast.LENGTH_SHORT);
        customToast.setView(customToastView);
        customToast.show();
    }





}
