package com.example.danesh_yar;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class AddLesson extends AppCompatActivity {

    private DBLessonsHelper db;
    private EditText editname;
    private EditText editnum;
    private Spinner edittype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lesson);

        db=new DBLessonsHelper(this);
        editname=findViewById(R.id.insertName);
        editnum=findViewById(R.id.insertNum);
        edittype=findViewById(R.id.insertType);

        findViewById(R.id.SaveLessonBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=editname.getText().toString().trim();
                String num=editnum.getText().toString().trim();
                int index_type=edittype.getSelectedItemPosition();
                String type="";
                if (name.length() > 0&&
                        num.length() > 0&&
                        index_type!=0){
                    switch (index_type){
                        case 1:
                            type="C";
                            break;
                        case 2:
                            type="Q";
                            break;
                        case 3:
                            type="E";
                            break;
                        case 4:
                            type="H";
                            break;
                        case 5:
                            type="F";
                            break;
                        case 6:
                            type="S";
                            break;
                        case 7:
                            type="A";
                            break;
                        case 8:
                            type="D";
                            break;
                        case 9:
                            type="K";
                            break;
                    }
                    if (db.addLesson(name,num,type)){
                        openDialog("اضافه شد");
                        close();
                    }
                    else {
                        openDialog("اضافه نشد!!");
                    }
                }
                else {
                    openDialog("همه فیلدارو باید پر کنی!!!");
                }
            }
        });
    }
    public void close(){
        this.finish();
    }
    public void openDialog(String message){
        Messagebox msg=new Messagebox(message,"");
        msg.show(getSupportFragmentManager(),"examplemessge");
    }
}
