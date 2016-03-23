package com.example.chiharumiyoshi.calculation_practice_app;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class FinishActivity extends AppCompatActivity {

    int correct, timeKind, questionTimes, calculationKind, timesInADay;
    long time, seconds, minutes, subSeconds;
    float timePerAQuestion, highestTime, correctRate;
    ArrayList<Integer> number1Records, number2Records, correctAnswerRecords, answerRecords;
    TextView correctTimesText, timeText, timesPerASecondText, highestTimeText, resultQuestionNumberText, resultQuestionText, resultCorrectAnswerText, resultAnswerText;
    ArrayAdapter arrayAdapter;
    ListView listView;
    ImageView resultImage;
    LinearLayout resultLayout, detailLayout;

    Animation animation1, animation2;

    public static final String KEY_CALCULATION_KIND = "CalculationKind";
    public static final String KEY_TOTAL_QUESTION_TIMES = "totalQuestionTimes";
    public static final String KEY_TOTAL_CORRECT_TIMES = "totalCorrectTimes";
    public static final String KEY_TOTALS = "totals";

    MySQLiteOpenHelper mySQLiteOpenHelper;
    SQLiteDatabase database;

    List<Item> items;
    CustomAdapter customAdapter;

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
        detailLayout = (LinearLayout)findViewById(R.id.detailLayout);
        resultLayout = (LinearLayout)findViewById(R.id.resultLayout);
        resultImage = (ImageView)findViewById(R.id.resultImage);

        animation1 = AnimationUtils.loadAnimation(this, R.anim.below_to_stage_animation);
        animation2 = AnimationUtils.loadAnimation(this, R.anim.stage_to_below_animation);

        number1Records = new ArrayList<>();
        number2Records = new ArrayList<>();
        answerRecords = new ArrayList<>();
        correctAnswerRecords = new ArrayList<>();

        //data
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        timesInADay = prefs.getInt(StartActivity.KEY_TIMES_IN_A_DAY, 0);

        correct = getIntent().getIntExtra("correct", 0);
        questionTimes = getIntent().getIntExtra("question_times", 0);
        calculationKind = getIntent().getIntExtra("calculation_kind", 0);
        timeKind = getIntent().getIntExtra("timeKind", 0);
        time = getIntent().getLongExtra("time", 0);
        number1Records = getIntent().getIntegerArrayListExtra("number1Records");
        number2Records = getIntent().getIntegerArrayListExtra("number2Records");
        correctAnswerRecords = getIntent().getIntegerArrayListExtra("correctAnswerRecords");
        answerRecords = getIntent().getIntegerArrayListExtra("answerRecords");

        detailLayout.setVisibility(View.INVISIBLE);

        if(correct == 0){
            correctRate = 0;
        }else{
            correctRate = (float)correct / (float)questionTimes * 100;
        }

        prefs.edit()
                .putInt(KEY_TOTALS, prefs.getInt(KEY_TOTALS, 0) + 1)
                .apply();

        if(timeKind == 0){
            correctTimesText.setText("正解数：" + correct + "/" + questionTimes + "回\n（" + (int)correctRate +  "%）");
        }else if(timeKind == 1){
            correctTimesText.setText("正解数：" + correct + "回");
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
        timesPerASecondText.setText("1問：" + timePerAQuestion + "秒");

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

        items = new ArrayList<>();

        for(int i = 0; i < questionTimes; i++){

            String text = "";

            if(calculationKind == 0){
                text = number1Records.get(i) + "+" + number2Records.get(i);
            }else if(calculationKind == 2){
                text = number1Records.get(i) + "-" + number2Records.get(i);
            }else if(calculationKind == 3){
                text = number1Records.get(i) + "×" + number2Records.get(i);
            }else if(calculationKind == 4){
                text = number1Records.get(i) + "÷" + number2Records.get(i);
            }

            Item item;
            item = new Item(i + 1, answerRecords.get(i), correctAnswerRecords.get(i), text);

            items.add(item);
        }

        customAdapter = new CustomAdapter(this, R.layout.result_list, items);
        listView.setAdapter(customAdapter);
    }

    public void insert(String calculationKind, int correctRate, int timePerAQuestion){

        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        int date = prefs.getInt("lastDate", 0);

        ContentValues values = new ContentValues();
        values.put("date", date);
        values.put("calculation_kind", calculationKind);
        values.put("correct_rate", correctRate);
        values.put("time_per_a_question", timePerAQuestion);

        database.insert(MySQLiteOpenHelper.RECORD_TABLE, null, values);
    }

    public void detail(View view){
        animation1.setFillAfter(true);
        animation1.setFillEnabled(true);
        detailLayout.startAnimation(animation1);
        resultLayout.setVisibility(View.INVISIBLE);
    }

    public void hideDetail(View view){
        detailLayout.startAnimation(animation2);
        detailLayout.setVisibility(View.INVISIBLE);
        resultLayout.setVisibility(View.VISIBLE);
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

    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK){

            Intent intent = new Intent();
            intent.setClass(FinishActivity.this, StartActivity.class);
            startActivity(intent);

            return super.onKeyDown(keyCode, event);
        }else{
            return false;
        }
    }
}
