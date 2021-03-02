package com.example.danesh_yar;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Win_Program extends AppCompatActivity {

    private DBLessonsHelper db;
    private ListView lst;
    private TableLayout tb;
    private TableRow tr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win__program);



        db=new DBLessonsHelper(this);

        tb = (TableLayout) findViewById(R.id.LessonTable);

        findViewById(R.id.AddBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddLesson();
            }
        });

        try {
            showLessons();
        }
        catch (Exception e){
            openDialog(e.getMessage());
        }
    }
    public void openAddLesson(){
        Intent intent=new Intent(this, AddLesson.class);
        startActivity(intent);
    }
    public void openDialog(String messge){
        Messagebox msg=new Messagebox(messge,"");
        msg.show(getSupportFragmentManager(),"examplemessge");
    }
    public void showLessons(){

        Cursor cursor=db.getLessons();

        String name;
        String num;
        String m_num;
        String date;
        int is_read;
        String type;

        int n=0;

        tr=new TableRow(this);

        if (cursor.moveToFirst()){
            do {
                tr = new TableRow(this);
                tr.setLayoutParams(new TableLayout.LayoutParams( TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

                //type{
                type=cursor.getString(cursor.getColumnIndex(DBLessonsHelper.col_type));
                TextView txttype = new TextView(this);
                switch (type){
                    case ("C"):
                        txttype.setText("فنی");
                        break;
                    case ("Q"):
                        txttype.setText("آموزشی");
                        break;
                    case ("E"):
                        txttype.setText("انگلیسی");
                        break;
                    case ("H"):
                        txttype.setText("هندسه");
                        break;
                    case ("F"):
                        txttype.setText("فیزیک");
                        break;
                    case ("S"):
                        txttype.setText("شیمی");
                        break;
                    case ("A"):
                        txttype.setText("عربی");
                    break;
                    case ("D"):
                        txttype.setText("دینی");
                        break;
                    case ("K"):
                        txttype.setText("کتاب");
                        break;
                    default:
                        txttype.setText("ارور");
                        break;
                }

                ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) findViewById(R.id.viewtype).getLayoutParams();
                txttype.setLayoutParams(params);
                txttype.setGravity(Gravity.CENTER);
                txttype.setBackgroundColor(Color.WHITE);
                tr.addView(txttype);
                //}}type
                //is_read{{
                is_read=
                        Integer.valueOf(
                                cursor.getString(
                                        cursor.getColumnIndex(
                                                DBLessonsHelper.col_is_read
                                        )));
                TextView txtread = new TextView(this);

                //txtread.setText(String.valueOf((Integer.valueOf(is_read))));
                if (is_read==1){
                    txtread.setText("*");
                }
                else {
                    txtread.setText(" ");
                }
                params = (ViewGroup.LayoutParams) findViewById(R.id.viewis_read).getLayoutParams();
                txtread.setLayoutParams(params);
                txtread.setGravity(Gravity.CENTER);
                txtread.setBackgroundColor(Color.WHITE);
                tr.addView(txtread);
                //}}is_read
                //date{{
                date=cursor.getString(cursor.getColumnIndex(DBLessonsHelper.col_date));
                TextView txtdate = new TextView(this);
                txtdate.setText(date);
                params = (ViewGroup.LayoutParams) findViewById(R.id.viewdate).getLayoutParams();
                txtdate.setLayoutParams(params);
                txtdate.setGravity(Gravity.CENTER);
                txtdate.setBackgroundColor(Color.WHITE);
                tr.addView(txtdate);
                //}}date
                //m_num{{
                m_num=cursor.getString(cursor.getColumnIndex(DBLessonsHelper.col_m_num));
                TextView txtm_num = new TextView(this);
                txtm_num.setText(m_num);
                params = (ViewGroup.LayoutParams) findViewById(R.id.viewm_num).getLayoutParams();
                txtm_num.setLayoutParams(params);
                txtm_num.setGravity(Gravity.CENTER);
                txtm_num.setBackgroundColor(Color.WHITE);
                tr.addView(txtm_num);
                //}}m_num
                //num{{
                num=cursor.getString(cursor.getColumnIndex(DBLessonsHelper.col_num));
                TextView txtnum = new TextView(this);
                txtnum.setText(num);
                params = (ViewGroup.LayoutParams) findViewById(R.id.viewnum).getLayoutParams();
                txtnum.setLayoutParams(params);
                txtnum.setGravity(Gravity.CENTER);
                txtnum.setBackgroundColor(Color.WHITE);
                tr.addView(txtnum);
                //}}num
                //name{{
                name=cursor.getString(cursor.getColumnIndex(DBLessonsHelper.col_name));
                TextView txtname = new TextView(this);
                txtname.setText(name);
                params = (ViewGroup.LayoutParams) findViewById(R.id.viewname).getLayoutParams();
                txtname.setLayoutParams(params);
                txtname.setGravity(Gravity.CENTER);
                txtname.setBackgroundColor(Color.WHITE);
                tr.addView(txtname);
                //}}name
                tb.removeView(tr);

                tb.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

            }while (cursor.moveToNext());
        }

    }
}
