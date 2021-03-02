package com.example.danesh_yar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Sleep extends AppCompatActivity {

    private static final long START_TIME_IN_MILIN=600000;
    private long mtimeleftinMilits=START_TIME_IN_MILIN;
    TextView Time;
    int minut = 2;
    int second = 0;

    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep);

        Time = findViewById(R.id.sleep_time);

        Create_Start_Timer();
    }

    private void Create_Start_Timer(){
        countDownTimer=new CountDownTimer(mtimeleftinMilits,100) {
            @SuppressLint("DefaultLocale")
            @Override
            public void onTick(long millisUntilFinished) {
                if (second==0){
                    if (minut == 0){
                        Stop_Timer();
                    }
                    else{
                        minut--;
                        second=59;
                    }

                }
                else {
                    second--;
                }
                Time.setText(getTime());
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

    private void Stop_Timer() {
        countDownTimer.cancel();
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result",5);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }
}
