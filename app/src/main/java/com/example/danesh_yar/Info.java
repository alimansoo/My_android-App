package com.example.danesh_yar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.example.danesh_yar.Services.NotificationActionService;
import com.example.danesh_yar.Services.onClearFromRecivedService;

import static android.app.NotificationManager.IMPORTANCE_LOW;

public class Info extends AppCompatActivity implements Playable {


    int LAUNCH_SECOND_ACTIVITY = 1;

    private DBLessonsHelper db;
    Button okbtn;

    public static final String ACTION_PLAY_PAUSE="Play_Pause_Acion";
    public static final String ACTION_STOP="Stop_Acion";

    private static final long START_TIME_IN_MILIN=600000;

    private final int Final_second = 30;
    private final int Final_minut = 0;

    int sans = 1;
    int minut = Final_minut;
    int second = Final_second;

    NotificationManager NotificationManager;

    private CountDownTimer countDownTimer;

    private long mtimeleftinMilits=START_TIME_IN_MILIN;

    NotificationCompat.Builder NotificationBuilder;

    public String LessonName="";
    public String LessonNumber="";

    boolean Timer_Running = false;
    boolean Timer_Create = false;

    final int NotificationID = 0;

    Intent Intent_Play_pause;
    PendingIntent PendingIntentPlay_Pause;

    Intent Intent_Stop;
    PendingIntent PendingIntentStop;

    char type2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        db=new DBLessonsHelper(this);
        TextView txt = findViewById(R.id.Lesson_name);
        okbtn = findViewById(R.id.OkBtn);

        type2= getIntent().getStringExtra("TYPE").charAt(0);

        if (type2 == '2') {
            txt.setText("تعطیله");
            findViewById(R.id.textView6)
                    .setVisibility(View.INVISIBLE);
            findViewById(R.id.textView8)
                    .setVisibility(View.INVISIBLE);
            findViewById(R.id.textView12)
                    .setVisibility(View.INVISIBLE);
            findViewById(R.id.textView10)
                    .setVisibility(View.INVISIBLE);
            findViewById(R.id.bekhon)
                    .setVisibility(View.INVISIBLE);
            findViewById(R.id.textView7)
                    .setVisibility(View.INVISIBLE);
            findViewById(R.id.OkBtn)
                    .setVisibility(View.INVISIBLE);
        } else {
            try{
                String[] data = showLesson(type2);
                LessonName=data[0];
                LessonNumber=data[1];
                if (LessonName.equals("")) {
                    openDialog("درسی وجود نداره!!");
                    findViewById(R.id.OkBtn).setVisibility(View.INVISIBLE);
                }
                txt.setText(LessonName);
            }
            catch (Exception e){
                openDialog("درسی وجود نداره!!");
                findViewById(R.id.OkBtn)
                        .setVisibility(View.INVISIBLE);
            }
        }

        okbtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                try{
                    if (!Timer_Create){
                        Timer_Create = true;
                        Timer_Running = true;
                        createNotifChannel();
                        InitializeIntents();
                        showNotification(getTime(),R.drawable.ic_play,"وایسا");
                        Create_Start_Timer();
                    }
                    else {
                        Start_Pause_Timer();
                    }
                }
                catch (Exception e){
                    openDialog(e.getMessage());
                }
            }
        });

        //1

    }
    public String[] showLesson(char type){
        String [] name=new String[2];
        int is_read;
        Cursor cursor=db.getLessionsByType(type);
        if (cursor.moveToFirst()){
            String [] users=new String[cursor.getCount()];
            do {
                name[0]=cursor.getString(cursor.getColumnIndex(DBLessonsHelper.col_name));
                name[1]=cursor.getString(cursor.getColumnIndex(DBLessonsHelper.col_num));
                is_read=Integer.valueOf(
                        cursor.getString(cursor.getColumnIndex(DBLessonsHelper.col_is_read))
                );
                if (is_read==0)
                    break;
            }while (cursor.moveToNext());
        }
        return name;
    }

    public void openSleep(){
        Intent intent=new Intent(this, Sleep.class);
        startActivityForResult(intent,LAUNCH_SECOND_ACTIVITY);
    }

    public void openWin_finish(){
        Intent intent=new Intent(this, Win_finish.class);
        intent.putExtra("LessonName",LessonName);
        intent.putExtra("Number",LessonNumber);
        startActivity(intent);
    }

    public void openDialog(String messge){
        Messagebox msg=new Messagebox(messge,"");
        msg.show(getSupportFragmentManager(),"alarm_message");
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void createNotifChannel() {

        NotificationManager = getSystemService(NotificationManager.class);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("10",
                    "name",
                    IMPORTANCE_LOW);
            channel.setDescription("DESCRIPTION");
            NotificationManager.createNotificationChannel(channel);
            openDialog("in the channel");
        }


    }

    private void InitializeIntents(){

        Intent_Play_pause= new Intent(getApplicationContext(), NotificationActionService.class)
                .setAction(ACTION_PLAY_PAUSE);
        PendingIntentPlay_Pause = PendingIntent.getBroadcast(getApplicationContext(),0,
                Intent_Play_pause,PendingIntent.FLAG_UPDATE_CURRENT);

        Intent_Stop = new Intent(getApplicationContext(), NotificationActionService.class)
                .setAction(ACTION_STOP);
        PendingIntentStop = PendingIntent.getBroadcast(getApplicationContext(),0,
                Intent_Stop,PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private void showNotification(String time,int play_pause_icon,String play_pause_text) {

        NotificationBuilder = new NotificationCompat.Builder(getApplicationContext(), "YOUR_CHANNEL_ID")
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(time)
                .setContentText("نصب و راه اندازی")
                .setLocalOnly(true)
                .setShowWhen(false)
                //.setOngoing(true)
                .setLargeIcon(BitmapFactory.decodeResource(getApplication().getResources(),
                        R.drawable.logo))
                .setOnlyAlertOnce(true)
                .addAction(play_pause_icon,play_pause_text,PendingIntentPlay_Pause)
                .addAction(R.drawable.ic_stop,"تمام",PendingIntentStop)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setAutoCancel(true);


        NotificationManager.notify(NotificationID, NotificationBuilder.build());

        registerReceiver(broadcastReceiver, new IntentFilter("TIMER"));
        startService(new Intent(getBaseContext(), onClearFromRecivedService.class));
    }

    private void Create_Start_Timer(){
        countDownTimer=new CountDownTimer(mtimeleftinMilits,1000) {
            @SuppressLint("DefaultLocale")
            @Override
            public void onTick(long millisUntilFinished) {
                if (second==0){
                    if (minut == 0){
                        Reset_Timer();
                        if (sans == 1)
                            openSleep();
                        else
                            openWin_finish();
                    }
                    else{
                        minut--;
                        second=59;
                    }

                }
                else {
                    second--;
                }
                NotificationBuilder.setContentTitle(getTime());
                NotificationManager.notify(0, NotificationBuilder.build());
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }

    @SuppressLint("DefaultLocale")
    private String getTime() {
        return String.format("%02d:%02d",minut,second);
    }

    final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getExtras().getString("ActionName");
            switch (action) {
                case ACTION_PLAY_PAUSE:
                    Start_Pause_Timer();
                    break;
                case ACTION_STOP:
                    Stop_Timer();
                    openWin_finish();
                    break;
            }
        }
    };

    //Timer Methods
    @Override
    public void Start_Pause_Timer() {
        if (!Timer_Running){
            countDownTimer.start();
            showNotification(getTime(),R.drawable.ic_play,"وایسا");
            Timer_Running = true;
        }
        else {
            countDownTimer.cancel();
            showNotification(getTime(),R.drawable.ic_pause,"ادامه");
            Timer_Running = false;
        }
    }

    @Override
    public void Stop_Timer() {
        countDownTimer.cancel();
        NotificationManager.cancel(NotificationID);
    }

    @Override
    public void Reset_Timer() {
        Timer_Running = false;
        second = Final_second;
        minut = Final_minut;
        countDownTimer.cancel();
        NotificationManager.cancel(NotificationID);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LAUNCH_SECOND_ACTIVITY) {
            if(resultCode == Activity.RESULT_OK){
                String result;
                result=data.getStringExtra("result");
                sans++;
                Timer_Running = true;
                showNotification(getTime(),R.drawable.ic_play,"وایسا");
                Create_Start_Timer();
            }
            if (resultCode == Activity.RESULT_CANCELED)
                openDialog("not result from the sleep activity!");
        }
    }
}
