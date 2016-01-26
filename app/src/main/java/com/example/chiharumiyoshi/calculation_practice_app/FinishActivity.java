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

    int correct, timeKind;
    long time, seconds, minutes, subSeconds;
    int question_numbers, calculationKind, timesInADay;
    float timeTimes, highestTime = 0;
    TextView correctText, timeText, timeTimesText, highestTimeText;
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

        //View
        timeTimesText = (TextView)findViewById(R.id.textView17);
        correctText = (TextView)findViewById(R.id.correct_t);
        highestTimeText = (TextView)findViewById(R.id.textView18);
        listView = (ListView)findViewById(R.id.listView);

        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        question_numbers = prefs.getInt(SettingsActivity.KEY_QUESTION_NUMBER, 10);
        correct = getIntent().getIntExtra("correct", 0);
        time = getIntent().getLongExtra("timeChronometer", 0);
        timeKind = getIntent().getIntExtra("timeKind", 0);

        if(timeKind == 0){
            correctText.setText(correct + "/" + question_numbers + "回");
        }else if(timeKind == 1){
            correctText.setText(correct + "回");
            question_numbers = correct;
        }

        seconds = time / 1000;
        subSeconds = time % 1000;
        minutes = seconds / 60;
        seconds = seconds - minutes * 60;
        timeText = (TextView)findViewById(R.id.textView2);
        timeText.setText("時間　" + minutes + "分" + seconds + "秒" + subSeconds);
        calculationKind = getIntent().getIntExtra(KEY_CALCULATION_KIND,0);

        if(correct == 0){
            timeTimes = 0;
        }else{
            timeTimes = (float)time / 1000 / (float)correct;
        }
        timeTimesText.setText("1問　" + timeTimes + "秒");

        if(timeKind == 1){
            if(calculationKind == 0){

                highestTime = prefs.getFloat("highestTimeAddition", 0);
                if(highestTime > timeTimes || highestTime == 0){
                    highestTime = timeTimes;
                    prefs.edit()
                            .putFloat("highestTimeAddition",  timeTimes)
                            .apply();
                }
            }else if(calculationKind == 1){

                highestTime = prefs.getFloat("highestTimeSubtraction", 0);
                if(highestTime > timeTimes || highestTime == 0){
                    highestTime = timeTimes;
                    prefs.edit()
                            .putFloat("highestTimeSubtraction",  timeTimes)
                            .apply();
                }
            }else if(calculationKind == 2){

                highestTime = prefs.getFloat("highestTimeMultiplication", 0);
                if(highestTime > timeTimes || highestTime == 0){
                    highestTime = timeTimes;
                    prefs.edit()
                            .putFloat("highestTimeMultiplication",  timeTimes)
                            .apply();
                }
            }else if(calculationKind == 3){

                highestTime = prefs.getFloat("highestTimeMultiplication", 0);
                if(highestTime > timeTimes || highestTime == 0){
                    highestTime = timeTimes;
                    prefs.edit()
                            .putFloat("highestTimeMultiplication", timeTimes)
                            .apply();
                }
            }

            highestTimeText.setText("最高記録　1問　" + highestTime + "秒");
        }

        timesInADay = prefs.getInt(StartActivity.KEY_TIMES_IN_A_DAY,0);
        timesInADay += 1;
        prefs.edit()
                .putInt(StartActivity.KEY_TIMES_IN_A_DAY, timesInADay)
                .apply();

        for(int i = 1; i < question_numbers; i++){
            search(i);
        }

        listView.setAdapter(arrayAdapter);
    }

    public void search(int question_numberValue){

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1);

        Cursor cursor = null;

        try{

            cursor = database.query(MySQLiteOpenHelper.TABLE_NAME, new String[]{"question_number", "number1", "number2", "correct_answer", "answer", "result"}, "question_number = ?", new String[]{String.valueOf(question_numberValue)}, null, null, null);

            int indexQuestionNumber = cursor.getColumnIndex("question_number");
            int indexNumber1 = cursor.getColumnIndex("number1");
            int indexNumber2 = cursor.getColumnIndex("number2");
            int indexCorrect_answer = cursor.getColumnIndex("correct_answer");
            int indexAnswer = cursor.getColumnIndex("answer");

            Log.e("out_serts", " " + indexQuestionNumber + " " + indexNumber1 + " " + indexNumber2 + " " + indexCorrect_answer + " " + indexAnswer);

            while(cursor.moveToNext()) {
                arrayAdapter.add("第" + indexQuestionNumber + "問：" + indexNumber1 + "+" + indexNumber2 + "　解答：" + indexCorrect_answer + "　あなたの答え：" + indexAnswer);
            }

        }finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public void restart(View v) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.setClass(FinishActivity.this, CalculationActivity.class);
        intent.putExtra(KEY_CALCULATION_KIND, calculationKind);
        startActivity(intent);
    }

    public void home(View v){
        Intent intent = new Intent();
        intent.setClass(FinishActivity.this, StartActivity.class);
        startActivity(intent);
    }
}
