package com.example.chiharumiyoshi.calculation_practice_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;

public class FinishActivity extends AppCompatActivity {

    int correct, calculationKind;
    long time, seconds, minutes, subSeconds, highestTime, highestTimeAddition, highestTimeSubtraction, highestTimeMultiplication, time_times, highestTimeMinutes, highestTimeSeconds, highestTimeSubSeconds, highestTimeDivision;
    TextView correctText, timeText, timeTimesText, highestTimeText;
    int question_numbers,last_activity, timesInADay;

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

        correct = getIntent().getIntExtra("correctTimes", 0);
        time = getIntent().getLongExtra("time", 0);
        calculationKind = getIntent().getIntExtra("calculationKind", 0);

        if(calculationKind == 1){
            correctText.setText(correct + "/" + question_numbers + "回");
        }else if(calculationKind == 2){
            correctText.setText(correct + "回");
        }

        seconds = time / 1000;
        subSeconds = time % 1000;
        minutes = seconds / 60;
        seconds = seconds - minutes * 60;
        timeText = (TextView)findViewById(R.id.textView2);
        timeText.setText("時間　" + minutes + "分" + seconds + "秒" + subSeconds);
        last_activity = getIntent().getIntExtra("last_activity",1);

        if(correct == 0){
            time_times = 0;
        }else{
            time_times = seconds / correct;
        }
        subSeconds = seconds % correct;
        timeTimesText.setText("1問　" + time_times + "秒" + subSeconds);

        if(calculationKind == 1){
            if(last_activity == 1){
                highestTimeAddition = prefs.getLong("highestTimeAddition", 0);
                if(highestTimeAddition > time / question_numbers || highestTimeAddition == 0){
                    highestTimeAddition = time / question_numbers;
                    prefs.edit()
                            .putLong("highestTimeAddition", highestTimeAddition)
                            .apply();
                }
                highestTime = highestTimeAddition;
            }else if(last_activity == 2){
                highestTimeSubtraction = prefs.getLong("highestTimeSubtraction", 0);
                if(highestTimeSubtraction > time / question_numbers || highestTimeAddition == 0){
                    highestTimeSubtraction = time / question_numbers;
                    prefs.edit()
                            .putLong("highestTimeSubtraction", highestTimeSubtraction)
                            .apply();
                }
                highestTime = highestTimeSubtraction;
            }else if(last_activity == 3){
                highestTimeMultiplication = prefs.getLong("highestTimeMultiplication", 0);
                if(highestTimeMultiplication > time / question_numbers || highestTimeAddition == 0){
                    highestTimeMultiplication = time / question_numbers;
                    prefs.edit()
                            .putLong("highestTimeMultiplication", highestTimeMultiplication)
                            .apply();
                }
                highestTime = highestTimeMultiplication;
            }else if(last_activity == 4){
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

        timesInADay = prefs.getInt("TimesInADay",0);
        timesInADay = timesInADay + 1;
    }

    public void restart(View v){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        if (last_activity == 1){
            intent.setClass(FinishActivity.this, AdditionActivity.class);
        }else if (last_activity == 2){
            intent.setClass(FinishActivity.this, SubtractionActivity.class);
        }else if(last_activity == 3){
            intent.setClass(FinishActivity.this, MultiplicationActivity.class);
        }else if(last_activity == 4){
            intent.setClass(FinishActivity.this, DivisionActivity.class);
        }
        startActivity(intent);
    }

    public void home(View v){
        Intent intent = new Intent();
        intent.putExtra("last_activity",1);
        intent.setClass(FinishActivity.this, StartActivity.class);
        startActivity(intent);
    }
}
