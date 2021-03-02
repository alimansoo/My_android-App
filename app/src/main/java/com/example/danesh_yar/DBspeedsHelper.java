package com.example.danesh_yar;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBspeedsHelper extends SQLiteOpenHelper {

    public static final String db_name="mydb";
    public static final String table_name="speeds";

    public static final String col_type="type";
    public static final String col_speed="speed";

    public static final int db_version=1;

    public DBspeedsHelper(@Nullable Context context) {
        super(context, table_name, null, db_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="CREATE TABLE speeds (" +
                "    type  CHAR    PRIMARY KEY," +
                "    speed INTEGER" +
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
