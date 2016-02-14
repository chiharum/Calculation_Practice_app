package com.example.chiharumiyoshi.calculation_practice_app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    static final String DATABASE = "calculation_practice_app_data.database";
    static final int DATABASE_VERSION = 1;
    static final String QUESTIONS_TABLE = "questions";
    static final String RECORD_TABLE = "records";
    static final String FORWARD_ADDITION_TABLE = "forward_addition";
    static final String FORWARD_SUBTRACTION_TABLE = "forward_subtraction";
    static final String FORWARD_MULTIPLICATION_TABLE = "forward_multiplication";
    static final String FORWARD_DIVISION_TABLE = "forward_division";

    public MySQLiteOpenHelper(Context context){

        super(context, DATABASE, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase database){
        database.execSQL("create table " + QUESTIONS_TABLE + " (id integer primary key autoincrement not null, question_number integer not null, number1 integer not null, number2 integer not null, correct_answer integer not null, answer integer)");
        database.execSQL("create table " + RECORD_TABLE + " (id integer primary key autoincrement not null, date integer not null, calculation_kind text not null, correct_rate integer not null, time_per_a_question integer not null)");
        database.execSQL("create table " + FORWARD_ADDITION_TABLE + " (id integer primary key autoincrement not null, number1 integer not null, number2 integer not null, correct_times integer, total_times integer)");
        database.execSQL("create table " + FORWARD_SUBTRACTION_TABLE + " (id integer primary key autoincrement not null, number1 integer not null, number2 integer not null, correct_times integer, total_times integer)");
        database.execSQL("create table " + FORWARD_MULTIPLICATION_TABLE + " (id integer primary key autoincrement not null, number1 integer not null, number2 integer not null, correct_times integer, total_times integer)");
        database.execSQL("create table " + FORWARD_DIVISION_TABLE + " (id integer primary key autoincrement not null, number1 integer not null, number2 integer not null, correct_times integer, total_times integer)");
    }

    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion){

    }
}
