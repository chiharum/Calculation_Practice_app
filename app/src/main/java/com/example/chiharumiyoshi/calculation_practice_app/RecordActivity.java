package com.example.chiharumiyoshi.calculation_practice_app;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RecordActivity extends AppCompatActivity {

    int totalTimes, totalCorrectTimes, totals;
    float correctRate;
    List<RecordItem> recordItems;
    RecordCustomAdapter recordCustomAdapter;
    TextView correctRateText;
    ListView recordList;

    MySQLiteOpenHelper mySQLiteOpenHelper;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        mySQLiteOpenHelper = new MySQLiteOpenHelper(getApplicationContext());
        database = mySQLiteOpenHelper.getWritableDatabase();

        //View
        correctRateText = (TextView)findViewById(R.id.correctRateText);
        recordList = (ListView)findViewById(R.id.recordList);

        //data
        recordItems = new ArrayList<>();
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        totalTimes = prefs.getInt(FinishActivity.KEY_TOTAL_QUESTION_TIMES, 0);
        totalCorrectTimes = prefs.getInt(FinishActivity.KEY_TOTAL_CORRECT_TIMES, 0);
        totals = prefs.getInt(FinishActivity.KEY_TOTALS, 0);

        if(totalTimes == 0){
            correctRateText.setText("0%");
        }else{
            correctRate = (float)totalCorrectTimes / (float) totalTimes * 100;
            correctRateText.setText((int)correctRate + "%");
        }

        for(int a = 0; a < totals; a++){
            RecordItem item = new RecordItem(searchDate(totals - a), search(totals - a));
            recordItems.add(item);
        }

        recordCustomAdapter = new RecordCustomAdapter(this, R.layout.record_list, recordItems);
        recordList.setAdapter(recordCustomAdapter);
    }

    public String searchDate(int idNum){

        Cursor cursor = null;
        String result = "";

        try{

            cursor = database.query(MySQLiteOpenHelper.RECORD_TABLE, new String[]{"id", "date"}, "id = ?", new String[]{String.valueOf(idNum)}, null, null, null);

            int indexDate = cursor.getColumnIndex("date");

            while(cursor.moveToNext()){
                int date = cursor.getInt(indexDate);

                int year = date / 10000;
                int month = date / 100 - year * 100;
                int day = date - year * 10000 - month * 100;

                result = year + "年" + month + "月" + day + "日";
            }
        }finally {
            if(cursor != null){
                cursor.close();
            }
        }

        return result;
    }

    public String search(int idNum){

        Cursor cursor = null;
        String result = "";

        try{

            cursor = database.query(MySQLiteOpenHelper.RECORD_TABLE, new String[]{"id","calculation_kind", "correct_rate", "time_per_a_question"}, "id = ?", new String[]{String.valueOf(idNum)}, null, null, null);

            int indexCalculationKind = cursor.getColumnIndex("calculation_kind");
            int indexCorrect_rate = cursor.getColumnIndex("correct_rate");
            int indexTime_per_a_question = cursor.getColumnIndex("time_per_a_question");

            while(cursor.moveToNext()){
                String calculationKind = cursor.getString(indexCalculationKind);
                int correctRate = cursor.getInt(indexCorrect_rate);
                int timePerAQuestion = cursor.getInt(indexTime_per_a_question);

                result = calculationKind + " 正答率：" + correctRate + "% " + "1問：" + timePerAQuestion + "秒";
            }
        }finally {
            if(cursor != null){
                cursor.close();
            }
        }

        return result;
    }
}
