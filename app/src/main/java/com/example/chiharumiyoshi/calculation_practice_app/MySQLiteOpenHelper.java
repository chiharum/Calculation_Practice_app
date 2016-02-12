package com.example.chiharumiyoshi.calculation_practice_app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    static final String DATABASE = "calculation_practice_app_data.database";
    static final int DATABASE_VERSION = 1;
    static final String QUESTIONS_TABLE_NAME = "questions";
    static final String RECORD_TABLE_NAME = "records";
    static final String FORWARD_TABLE_NAME = "forward";

    public MySQLiteOpenHelper(Context context){

        super(context, DATABASE, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase database){
        database.execSQL("create table " + QUESTIONS_TABLE_NAME + " (_id integer primary key autoincrement not null, question_number integer not null, number1 integer not null, number2 integer not null, correct_answer integer not null, answer integer)");
        database.execSQL("create table " + RECORD_TABLE_NAME + " (_id integer primary key autoincrement not null, date integer not null, calculation_kind text not null, correct_rate integer not null, time_per_a_question integer not null)");
        database.execSQL("create table " + FORWARD_TABLE_NAME + " (_id integer primary key autoincrement not null, calculation_kind integer not null, number1 integer not null, number2 integer not null, correct_time integer not null, total_time integer not null");
    }

    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion){

    }
}
