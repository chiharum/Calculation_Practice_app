package com.example.chiharumiyoshi.calculation_practice_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;

public class FinishActivity extends AppCompatActivity {

    int correct, timeKind;
    long time, seconds, minutes, subSeconds, highestTime, highestTimeAddition, highestTimeSubtraction, highestTimeMultiplication, highestTimeMinutes, highestTimeSeconds, highestTimeSubSeconds, highestTimeDivision;
    TextView correctText, timeText, timeTimesText, highestTimeText;
    int question_numbers, calculationKind, timesInADay;
    float timeTimes;

    public static final String KEY_CALCULATION_KIND = "CalculationKind";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);

        //View
        timeTimesText = (TextView)findViewById(R.id.textView17);
        correctText = (TextView)findViewById(R.id.correct_t);
        highestTimeText = (TextView)findViewById(R.id.textView18);


        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        question_numbers = prefs.getInt(SettingsActivity.KEY_QUESTION_NUMBER, 10);

        correct = getIntent().getIntExtra("correct", 0);
        time = getIntent().getLongExtra("time", 0);
        timeKind = getIntent().getIntExtra("timeKind", 0);

        if(timeKind == 1){
            correctText.setText(correct + "/" + question_numbers + "回");
        }else if(timeKind == 2){
            correctText.setText(correct + "回");
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
            if(calculationKind == 1){

                highestTimeAddition = prefs.getLong("highestTimeAddition", 0);
                if(highestTimeAddition > time / question_numbers || highestTimeAddition == 0){
                    highestTimeAddition = time / question_numbers;
                    prefs.edit()
                            .putLong("highestTimeAddition", highestTimeAddition)
                            .apply();
                }
                highestTime = highestTimeAddition;
            }else if(calculationKind == 2){

                highestTimeSubtraction = prefs.getLong("highestTimeSubtraction", 0);
                if(highestTimeSubtraction > time / question_numbers || highestTimeAddition == 0){
                    highestTimeSubtraction = time / question_numbers;
                    prefs.edit()
                            .putLong("highestTimeSubtraction", highestTimeSubtraction)
                            .apply();
                }
                highestTime = highestTimeSubtraction;
            }else if(calculationKind == 3){

                highestTimeMultiplication = prefs.getLong("highestTimeMultiplication", 0);
                if(highestTimeMultiplication > time / question_numbers || highestTimeAddition == 0){
                    highestTimeMultiplication = time / question_numbers;
                    prefs.edit()
                            .putLong("highestTimeMultiplication", highestTimeMultiplication)
                            .apply();
                }
                highestTime = highestTimeMultiplication;
            }else if(calculationKind == 4){

                highestTimeDivision = prefs.getLong("highestTimeMultiplication", 0);
                if(highestTimeDivision > time / question_numbers || highestTimeDivision == 0){
                    highestTimeDivision = time / question_numbers;
                    prefs.edit()
                            .putLong("highestTimeMultiplication", highestTimeDivision)
                            .apply();
                }
                highestTime = highestTimeDivision;
            }

            highestTimeSeconds = highestTime / 1000;
            highestTimeSubSeconds = highestTime - highestTimeSeconds * 1000;
            highestTimeMinutes = highestTimeSeconds / 60;
            highestTimeSeconds = highestTimeSeconds - highestTimeMinutes * 60;
            highestTimeText.setText("最高記録　1問　" + highestTimeSeconds + "秒" + highestTimeSubSeconds);
        }

        timesInADay = prefs.getInt(StartActivity.KEY_TIMES_IN_A_DAY,0);
        timesInADay += 1;
        prefs.edit()
                .putInt(StartActivity.KEY_TIMES_IN_A_DAY, timesInADay)
                .apply();
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
