package com.example.danesh_yar;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBpreviewHelper extends SQLiteOpenHelper {

    public static final String db_name="mydb";
    public static final String table_name="preview";

    public static final String col_name="name";
    public static final String col_date="date";

    public static final int db_version=1;

    public DBpreviewHelper(@Nullable Context context) {
        super(context, db_name, null, db_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="CREATE TABLE preview (\n" +
                "    name VARCHAR  PRIMARY KEY UNIQUE," +
                "    date DATETIME" +
                ");";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql="DROP TABLE IF EXISTS "+table_name;
        db.execSQL(sql);
        onCreate(db);
    }
}
