package com.example.danesh_yar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Button aboutbtn=findViewById(R.id.about);
        aboutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog("برنامه نویس : علی منصوری","About");
            }
        });
        Button programbtn=findViewById(R.id.ProgramBtn);
        programbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWin_program();
            }
        });
        Button Start1Btn=findViewById(R.id.Start1Btn);
        Button Start2Btn=findViewById(R.id.Start2Btn);
        Calendar calendar = Calendar.getInstance();
        final int day = calendar.get(Calendar.DAY_OF_WEEK);
        Start1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type1="";
                switch (day) {
                    case Calendar.SATURDAY:
                        type1="C";
                        break;
                    case Calendar.SUNDAY:
                        type1="Q";
                        break;
                    case Calendar.MONDAY:
                        type1="E";
                        break;
                    case Calendar.TUESDAY:
                        type1="H";
                        break;
                    case Calendar.WEDNESDAY:
                        type1="F";
                        break;
                    case Calendar.THURSDAY:
                        type1="S";
                        break;
                    case Calendar.FRIDAY:
                        type1="2";
                        break;
                }
                openinfo(type1);
            }
        });
        Start2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type1="";
                switch (day) {
                    case Calendar.SATURDAY:
                        type1="A";
                        break;
                    case Calendar.SUNDAY:
                        type1="D";
                        break;
                    case Calendar.MONDAY:
                        type1="E";
                        break;
                    case Calendar.TUESDAY:
                        type1="H";
                        break;
                    case Calendar.WEDNESDAY:
                        type1="F";
                        break;
                    case Calendar.THURSDAY:
                        type1="S";
                        break;
                    case Calendar.FRIDAY:
                        type1="2";
                        break;
                }
                openinfo(type1);
            }
        });
        Button ReadBtn=findViewById(R.id.ReadBtn);
        ReadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openinfo("K");
            }
        });
        Button SetLessonBtn=findViewById(R.id.SetLessonBtn);
        SetLessonBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSetLesson();
            }
        });
    }
    public void openWin_program(){
        Intent intent=new Intent(this, Win_Program.class);
        startActivity(intent);
    }
    public void openDialog(String Message,String Title){
        Messagebox msg=new Messagebox(Message,Title);
        msg.show(getSupportFragmentManager(),"examplemessge");
    }
    public void openinfo(String Type){
        Intent intent=new Intent(this, Info.class);
        intent.putExtra("TYPE",Type);
        startActivity(intent);
    }
    public void openSetLesson(){
        Intent intent=new Intent(this, SetLesson.class);
        startActivity(intent);
    }

}
