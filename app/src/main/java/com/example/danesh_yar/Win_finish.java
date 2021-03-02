package com.example.danesh_yar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class Win_finish extends AppCompatActivity {

    private DBLessonsHelper db;
    public String name;
    public int num;
    public int M_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win_finish);

        name = getIntent().getStringExtra("LessonName");
        num = Integer.valueOf(
                getIntent().getStringExtra("Number"));

        db=new DBLessonsHelper(this);

        Button aboutbtn=findViewById(R.id.ChangeBtn);

        aboutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText num1=findViewById(R.id.Editm_num);
                int m_num=Integer.valueOf(num1.getText().toString().trim());
                if (m_num > num) {
                    openDialog("بیشتر از تعداد صفحات خوندی!!!","بیشتر");
                }
                else if (m_num==num){
                    if (db.UpdateLessonInfo(String.valueOf(m_num),NowDate(),"1",name)==true){
                        openDialog("باریکلا کل کتابو خوندی","");
                        close();
                    }
                    else {
                        openDialog("یه مشکلی تو برنامه هست","");
                    }
                }
                else {
                    if (db.UpdateLessonInfo(String.valueOf(m_num),NowDate(),"0",name)==true){
                        openDialog("تبریک میگم","");
                        close();
                    }
                    else {
                        openDialog("یه مشکلی تو برنامه هست","");
                    }
                }
            }

            private void close() {
                this.close();
            }
        });

    }
    public void openDialog(String message,String title){
        Messagebox msg=new Messagebox(message,title);
        msg.show(getSupportFragmentManager(),"examplemessge");
    }
    public String NowDate(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.SHORT);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR);
        int minut = calendar.get(Calendar.MINUTE);
        int am = calendar.get(Calendar.AM);
        month++;
        String DateTime=
                String.valueOf(year)
                        +"/"+String.valueOf(month)
                        +"/"+String.valueOf(day)
                        +" "+String.valueOf(hour)
                        +":"+String.valueOf(minut)+" ";
        if (am==1){
            DateTime+="PM";
        }else {
            DateTime+="AM";
        }
        return DateTime;
    }
}
