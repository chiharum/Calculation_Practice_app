package com.example.chiharumiyoshi.calculation_practice_app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    static final String DATABASE = "calculation_practice_app_data.database";
    static final int DATABASE_VERSION = 1;
    static final String TABLE_NAME = "questions";

    public MySQLiteOpenHelper(Context context){

        super(context, DATABASE, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase database){
        database.execSQL("create table " + TABLE_NAME + " (_id integer primary key autoincrement not null, question_number integer not null, number1 integer not null, number2 integer not null, correct_answer integer not null, answer integer)");
    }

    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion){

    }
}
