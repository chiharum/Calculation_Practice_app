package com.example.chiharumiyoshi.calculation_practice_app;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class RecordActivity extends AppCompatActivity {

    int totalTimes, totalCorrectTimes, totals;
    float correctRate;
    TextView correctRateText;
    ArrayAdapter arrayAdapter;
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
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        totalTimes = prefs.getInt(FinishActivity.KEY_TOTAL_QUESTION_TIMES, 0);
        totalCorrectTimes = prefs.getInt(FinishActivity.KEY_TOTAL_CORRECT_TIMES, 0);
        totals = prefs.getInt(FinishActivity.KEY_TOTALS, 0);

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1);

        if(totalTimes == 0){
            correctRateText.setText("0%");
        }else{
            correctRate = (float)totalCorrectTimes / (float) totalTimes * 100;
            correctRateText.setText((int)correctRate + "%");
        }

        for(int a = 0; a < totals; a++){
            arrayAdapter.add(search(totals - a));
        }
        recordList.setAdapter(arrayAdapter);
    }

    public String search(int idNum){

        Cursor cursor = null;
        String result = "";

        try{

            cursor = database.query(MySQLiteOpenHelper.RECORD_TABLE_NAME, new String[]{"_id","calculation_kind", "date", "correct_rate", "time_per_a_question"}, "_id = ?", new String[]{String.valueOf(idNum)}, null, null, null);

            int indexDate = cursor.getColumnIndex("date");
            int indexCalculationKind = cursor.getColumnIndex("calculation_kind");
            int indexCorrect_rate = cursor.getColumnIndex("correct_rate");
            int indexTime_per_a_question = cursor.getColumnIndex("time_per_a_question");

            while(cursor.moveToNext()){
                int date = cursor.getInt(indexDate);
                String calculationKind = cursor.getString(indexCalculationKind);
                int correctRate = cursor.getInt(indexCorrect_rate);
                int timePerAQuestion = cursor.getInt(indexTime_per_a_question);

                int year = date / 10000;
                int month = date / 100 - year * 100;
                int day = date - year * 10000 - month * 100;

                result = year + "年" + month + "月" + day + "日" + "\n" + calculationKind + " 正答率：" + correctRate + "% " + "1問：" + timePerAQuestion + "秒";
            }
        }finally {
            if(cursor != null){
                cursor.close();
            }
        }

        return result;
    }
}
