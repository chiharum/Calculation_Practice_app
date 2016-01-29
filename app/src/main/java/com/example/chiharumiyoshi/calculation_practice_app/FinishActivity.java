package com.example.chiharumiyoshi.calculation_practice_app;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class FinishActivity extends AppCompatActivity {

    int correct, timeKind, question_numbers, calculationKind, timesInADay;
    long time, seconds, minutes, subSeconds;
    float timesPerASecond, highestTime = 0;
    TextView correctTimesText, timeText, timesPerASecondText, highestTimeText;
    ArrayAdapter arrayAdapter;
    ListView listView;

    public static final String KEY_CALCULATION_KIND = "CalculationKind";

    MySQLiteOpenHelper mySQLiteOpenHelper;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);

        mySQLiteOpenHelper = new MySQLiteOpenHelper(getApplicationContext());
        database = mySQLiteOpenHelper.getWritableDatabase();

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1);

        //View
        timesPerASecondText = (TextView)findViewById(R.id.textView17);
        correctTimesText = (TextView)findViewById(R.id.correct_t);
        highestTimeText = (TextView)findViewById(R.id.textView18);
        timeText = (TextView)findViewById(R.id.textView2);
        listView = (ListView)findViewById(R.id.listView);


        //data
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        question_numbers = prefs.getInt(SettingsActivity.KEY_QUESTION_NUMBER, 10);
        timesInADay = prefs.getInt(StartActivity.KEY_TIMES_IN_A_DAY, 0);
        correct = getIntent().getIntExtra("correct", 0);
        calculationKind = getIntent().getIntExtra(KEY_CALCULATION_KIND, 0);
        timeKind = getIntent().getIntExtra("timeKind", 0);
        time = getIntent().getLongExtra("timeChronometer", 0);

        Log.e("time", "correct = " + correct + " timeChronometer = " + time + " timeKind = " + timeKind);

        if(timeKind == 0){
            correctTimesText.setText(correct + "/" + question_numbers + "回");
        }else if(timeKind == 1){
            correctTimesText.setText(correct + "回");
            question_numbers = correct;
        }

        seconds = time / 1000;
        subSeconds = time % 1000;
        minutes = seconds / 60;
        seconds = seconds - minutes * 60;
        timeText.setText("時間　" + minutes + "分" + seconds + "秒" + subSeconds);

        if(correct == 0){
            timesPerASecond = 0;
        }else{
            timesPerASecond = (float)time / 1000 / (float)correct;
        }
        timesPerASecondText.setText("1問　" + timesPerASecond + "秒");

        if(calculationKind == 0){

            highestTime = prefs.getFloat("highestTimeAddition", 0);
            if(highestTime > timesPerASecond || highestTime == 0){
                highestTime = timesPerASecond;
                prefs.edit()
                        .putFloat("highestTimeAddition", timesPerASecond)
                        .apply();
            }
        }else if(calculationKind == 1){

            highestTime = prefs.getFloat("highestTimeSubtraction", 0);
            if(highestTime > timesPerASecond || highestTime == 0){
                highestTime = timesPerASecond;
                prefs.edit()
                        .putFloat("highestTimeSubtraction", timesPerASecond)
                        .apply();
            }
        }else if(calculationKind == 2){

            highestTime = prefs.getFloat("highestTimeMultiplication", 0);
            if(highestTime > timesPerASecond || highestTime == 0){
                highestTime = timesPerASecond;
                prefs.edit()
                        .putFloat("highestTimeMultiplication", timesPerASecond)
                        .apply();
            }
        }else if(calculationKind == 3){

            highestTime = prefs.getFloat("highestTimeMultiplication", 0);
            if(highestTime > timesPerASecond || highestTime == 0){
                highestTime = timesPerASecond;
                prefs.edit()
                        .putFloat("highestTimeMultiplication", timesPerASecond)
                        .apply();
            }
        }
        highestTimeText.setText("最高記録　1問　" + highestTime + "秒");

        timesInADay += 1;
        prefs.edit()
                .putInt(StartActivity.KEY_TIMES_IN_A_DAY, timesInADay)
                .apply();

        for(int i = 1; i <= question_numbers; i++){
            arrayAdapter.add(search(i));
        }

        listView.setAdapter(arrayAdapter);
    }

    public String search(int question_numberValue){

        Cursor cursor = null;
        String result = "";

        try{

            cursor = database.query(MySQLiteOpenHelper.TABLE_NAME, new String[]{"question_number", "number1", "number2", "correct_answer", "answer"}, "question_number = ?", new String[]{String.valueOf(question_numberValue)}, null, null, null);

            int indexQuestionNumber = cursor.getColumnIndex("question_number");
            int indexNumber1 = cursor.getColumnIndex("number1");
            int indexNumber2 = cursor.getColumnIndex("number2");
            int indexCorrect_answer = cursor.getColumnIndex("correct_answer");
            int indexAnswer = cursor.getColumnIndex("answer");

            Log.e("out_serts", question_numberValue + " " + indexQuestionNumber + " " + indexNumber1 + " " + indexNumber2 + " " + indexCorrect_answer + " " + indexAnswer);

            while(cursor.moveToNext()) {
                result = "第" + question_numberValue + "問：" + indexNumber1 + "+" + indexNumber2 + "　解答：" + indexCorrect_answer + "　あなたの答え：" + indexAnswer;
            }

        }finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return result;
    }

    public void restart(View v) {

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.setClass(FinishActivity.this, CalculationActivity.class);
        intent.putExtra(KEY_CALCULATION_KIND, calculationKind);
        intent.putExtra("timeKind", timeKind);
        startActivity(intent);
    }

    public void home(View v){

        Intent intent = new Intent();
        intent.setClass(FinishActivity.this, StartActivity.class);
        startActivity(intent);
    }
}
