package com.example.danesh_yar;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class test extends AppCompatActivity {

    private Button testbtn;
    NotificationManager notifManager;
    String offerChannelId = "Offers";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        final NotificationCompat.Builder NotifBuilder = new NotificationCompat.Builder(this,offerChannelId)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("بروزرسانی")
                .setContentText("یک نسخه جدید از برنامه آماده دریافت است");

        testbtn=findViewById(R.id.testbtn);

        final NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(this);
        testbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    notificationManagerCompat.notify(100,NotifBuilder.build());
                }
                catch (Exception e){
                    openDialog(e.getMessage(),"");
                }

            }
        });
        for (int i=0;i<10;i++){
            NotifBuilder.setContentText("12");
        }
    }
    private void createNotifChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            String offerChannelName = "Shop offers";
            String offerChannelDescription= "Best offers for customers";
            int offerChannelImportance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel notifChannel = new NotificationChannel(offerChannelId, offerChannelName, offerChannelImportance);
            notifChannel.setDescription(offerChannelDescription);
            notifChannel.enableVibration(true);
            notifChannel.enableLights(true);
            notifChannel.setLightColor(Color.GRAY);

            notifManager.createNotificationChannel(notifChannel);
        }

    }
    public void openDialog(String messge,String title){
        Messagebox msg=new Messagebox(messge,title);
        msg.show(getSupportFragmentManager(),"examplemessge");
    }
    public void simpleNotification() {

        NotificationCompat.Builder sNotifBuilder = new NotificationCompat.Builder(this, offerChannelId)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("بروزرسانی")
                .setContentText("یک نسخه جدید از برنامه آماده دریافت است")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        notifManager.notify(1, sNotifBuilder.build());

    }
}
