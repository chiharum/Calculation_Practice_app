package com.example.chiharumiyoshi.calculation_practice_app;

import android.content.ContentValues;
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

    int correct, timeKind, questionTimes, calculationKind, timesInADay;
    long time, seconds, minutes, subSeconds;
    float timePerAQuestion, highestTime, correctRate;
    TextView correctTimesText, timeText, timesPerASecondText, highestTimeText;
    ArrayAdapter arrayAdapter;
    ListView listView;

    public static final String KEY_CALCULATION_KIND = "CalculationKind";
    public static final String KEY_TOTAL_QUESTION_TIMES = "totalQuestionTimes";
    public static final String KEY_TOTAL_CORRECT_TIMES = "totalCorrectTimes";
    public static final String KEY_TOTALS = "totals";

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
        timesInADay = prefs.getInt(StartActivity.KEY_TIMES_IN_A_DAY, 0);

        correct = getIntent().getIntExtra("correct", 0);
        questionTimes = getIntent().getIntExtra("question_times", 0);
        calculationKind = getIntent().getIntExtra("calculation_kind", 0);
        timeKind = getIntent().getIntExtra("timeKind", 0);
        time = getIntent().getLongExtra("time", 0);

        if(correct == 0){
            correctRate = 0;
        }else{
            correctRate = (float)correct / (float)questionTimes * 100;
        }

        prefs.edit()
                .putInt(KEY_TOTALS, prefs.getInt(KEY_TOTALS, 0) + 1)
                .apply();

        if(timeKind == 0){
            correctTimesText.setText(correct + "/" + questionTimes + "回");
        }else if(timeKind == 1){
            correctTimesText.setText(correct + "回");
            questionTimes = correct;
        }

        prefs.edit()
                .putInt(KEY_TOTAL_QUESTION_TIMES, prefs.getInt(KEY_TOTAL_QUESTION_TIMES, 0) + questionTimes)
                .apply();
        prefs.edit()
                .putInt(KEY_TOTAL_CORRECT_TIMES, prefs.getInt(KEY_TOTAL_CORRECT_TIMES, 0) + correct)
                .apply();

        seconds = time / 1000;
        subSeconds = time % 1000;
        minutes = seconds / 60;
        seconds = seconds - minutes * 60;
        timeText.setText("時間　" + minutes + "分" + seconds + "秒" + subSeconds);

        if(correct == 0){
            timePerAQuestion = 0;
        }else{
            timePerAQuestion = (float)time / 1000 / (float)correct;
        }
        timesPerASecondText.setText("1問　" + timePerAQuestion + "秒");

        if(calculationKind == 0){

            highestTime = prefs.getFloat("highestTimeAddition", 0);
            if(highestTime > timePerAQuestion || highestTime == 0){
                highestTime = timePerAQuestion;
                prefs.edit()
                        .putFloat("highestTimeAddition", timePerAQuestion)
                        .apply();
            }
        }else if(calculationKind == 1){

            highestTime = prefs.getFloat("highestTimeSubtraction", 0);
            if(highestTime > timePerAQuestion || highestTime == 0){
                highestTime = timePerAQuestion;
                prefs.edit()
                        .putFloat("highestTimeSubtraction", timePerAQuestion)
                        .apply();
            }
        }else if(calculationKind == 2){

            highestTime = prefs.getFloat("highestTimeMultiplication", 0);
            if(highestTime > timePerAQuestion || highestTime == 0){
                highestTime = timePerAQuestion;
                prefs.edit()
                        .putFloat("highestTimeMultiplication", timePerAQuestion)
                        .apply();
            }
        }else if(calculationKind == 3){

            highestTime = prefs.getFloat("highestTimeMultiplication", 0);
            if(highestTime > timePerAQuestion || highestTime == 0){
                highestTime = timePerAQuestion;
                prefs.edit()
                        .putFloat("highestTimeMultiplication", timePerAQuestion)
                        .apply();
            }
        }
        highestTimeText.setText("最高記録　1問　" + highestTime + "秒");

        if(calculationKind == 0){
            insert("足し算", (int)correctRate, (int)timePerAQuestion);
        }else if(calculationKind == 1){
            insert("引き算", (int)correctRate, (int)timePerAQuestion);
        }else if(calculationKind == 2){
            insert("かけ算", (int)correctRate, (int)timePerAQuestion);
        }else if(calculationKind == 3){
            insert("割り算", (int)correctRate, (int)timePerAQuestion);
        }

        timesInADay += 1;
        prefs.edit()
                .putInt(StartActivity.KEY_TIMES_IN_A_DAY, timesInADay)
                .apply();

        for(int i = 1; i <= questionTimes; i++){

            arrayAdapter.add(search(i));

            database.delete(MySQLiteOpenHelper.QUESTIONS_TABLE_NAME, "_id = " + i, null);
        }

        listView.setAdapter(arrayAdapter);
    }

    public String search(int question_numberValue){

        Cursor cursor = null;
        String result = "";

        try{

            cursor = database.query(MySQLiteOpenHelper.QUESTIONS_TABLE_NAME, new String[]{"question_number", "number1", "number2", "correct_answer", "answer"}, "question_number = ?", new String[]{String.valueOf(question_numberValue)}, null, null, null);

            int indexQuestionNumber = cursor.getColumnIndex("question_number");
            int indexNumber1 = cursor.getColumnIndex("number1");
            int indexNumber2 = cursor.getColumnIndex("number2");
            int indexCorrect_answer = cursor.getColumnIndex("correct_answer");
            int indexAnswer = cursor.getColumnIndex("answer");

//            Log.e("out_serts", question_numberValue + " " + indexQuestionNumber + " " + indexNumber1 + " " + indexNumber2 + " " + indexCorrect_answer + " " + indexAnswer);

            while(cursor.moveToNext()) {

                if (calculationKind == 0){
                    result = "第" + cursor.getInt(indexQuestionNumber) + "問：" + cursor.getInt(indexNumber1) + "+" + cursor.getInt(indexNumber2) + " 解答：" + cursor.getInt(indexCorrect_answer) + "　あなたの答え：" + cursor.getInt(indexAnswer);
                }else if(calculationKind == 1){
                    result = "第" + cursor.getInt(indexQuestionNumber) + "問：" + cursor.getInt(indexNumber1) + "-" + cursor.getInt(indexNumber2) + " 解答：" + cursor.getInt(indexCorrect_answer) + "　あなたの答え：" + cursor.getInt(indexAnswer);
                }else if(calculationKind == 2){
                    result = "第" + cursor.getInt(indexQuestionNumber) + "問：" + cursor.getInt(indexNumber1) + "×" + cursor.getInt(indexNumber2) + " 解答：" + cursor.getInt(indexCorrect_answer) + "　あなたの答え：" + cursor.getInt(indexAnswer);
                }else if(calculationKind == 3){
                    result = "第" + cursor.getInt(indexQuestionNumber) + "問：" + cursor.getInt(indexNumber1) + "÷" + cursor.getInt(indexNumber2) + " 解答：" + cursor.getInt(indexCorrect_answer) + "　あなたの答え：" + cursor.getInt(indexAnswer);
                }
            }

        }finally {
            if (cursor != null) {

                cursor.close();
            }
        }

        return result;
    }

    public void insert(String calculationKind, int correctRate, int timePerAQuestion){

        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        int date = prefs.getInt("lastDate", 0);

        ContentValues values = new ContentValues();
        values.put("date", date);
        values.put("calculation_kind", calculationKind);
        values.put("correct_rate", correctRate);
        values.put("time_per_a_question", timePerAQuestion);

        database.insert(MySQLiteOpenHelper.RECORD_TABLE_NAME, null, values);
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
