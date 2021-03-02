package com.example.danesh_yar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBLessonsHelper extends SQLiteOpenHelper {

    public static final String db_name="mydb";
    public static final String table_name="Lessons";

    public static final String col_name="name";
    public static final String col_num="num";
    public static final String col_m_num="m_num";
    public static final String col_date="date";
    public static final String col_is_read="is_read";
    public static final String col_type="type";

    public static final int db_version=2;

    public DBLessonsHelper(@Nullable Context context) {
        super(context, table_name, null, db_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="CREATE TABLE Lessons (" +
                "    id      INTEGER  PRIMARY KEY AUTOINCREMENT UNIQUE," +
                "    name    VARCHAR  UNIQUE," +
                "    num     INTEGER," +
                "    m_num   INTEGER," +
                "    date    DATETIME," +
                "    is_read BOOLEAN," +
                "    type    CHAR" +
                ");";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*String sql="DROP TABLE IF EXISTS "+table_name;
        db.execSQL(sql);
        onCreate(db);*/
    }
    //get function{{
    //get lesson by type
    public Cursor getLessionsByType(char type)
    {
        SQLiteDatabase db=getReadableDatabase();
        String sql = "SELECT * FROM "+table_name+" WHERE(type='" + type + "')";
        return db.rawQuery(sql,null);
    }
    //get first unread lesson
    /*public Cursor getU_ReadLessons()
    {
        SQLiteDatabase db=getReadableDatabase();
        String sql = "SELECT * FROM " + table_name + " WHERE(is_read=False)";
        return db.rawQuery(sql,null);
    }*/
    //get all lesson
    public Cursor getLessons()
    {
        SQLiteDatabase db2=getReadableDatabase();
        String sql = "SELECT * FROM Lessons;" ;
        return db2.rawQuery(sql,null);
    }
    ///}}
    //set function{{
    public Boolean UpdateLessonInfo(String m_num,String date,String is_read,String OriginalName){
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("UPDATE "+table_name+" set m_num = "+m_num+" ,date = '"+date+"',is_read = "+is_read+" where(name = '"+OriginalName+"') ;");
        return true;
    }

    public boolean updater(String ftype,String type){
        SQLiteDatabase db1=this.getWritableDatabase();
        db1.execSQL("UPDATE Lessons set type = '"+type+"' where(num = "+ftype+") ;");
        return true;
    }
    //}}
    //delete{{
    public Boolean deleteLesson(String num){
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DELETE FROM "+table_name+" where( num = '"+num+"') ;");
        return true;
    }
    //}}
    //isnert function{{
    public boolean addLesson(String name,String num,String type){
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(col_name,name);
        values.put(col_num,num);
        values.put(col_m_num,"0");
        values.put(col_date,"");
        values.put(col_is_read,false);
        values.put(col_type,type);
        if (db.insert(table_name,null,values)==-1)
            return false;
        return true;
    }



    //}}
}
