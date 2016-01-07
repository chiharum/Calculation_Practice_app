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
    long time, seconds, minutes, subSeconds;
    int question_numbers, calculationKind, timesInADay;
    float timeTimes, highestTime = 0;
    TextView correctText, timeText, timeTimesText, highestTimeText;

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
