package com.example.chiharumiyoshi.calculation_practice_app;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class CalculationActivity extends AppCompatActivity {

    TextView number1Text, number2Text, answerText, correctTimesText, remainText, flagText;
    ImageView eraserImage, correctImage, incorrectImage;
    int number1, number2, userAnswer, correctAnswer, correctTimes, times, questionTimes, eraserColor, remainTimes, calculationKind, timeKind;
    long startTime, endTime, totalTime, stopRealTime, questionTime, remainTime;
    boolean minus;
    ProgressBar progressBar;
    Chronometer time;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculation);

        // Viewの関連付け
        eraserImage = (ImageView) findViewById(R.id.imageView);
        remainText = (TextView) findViewById(R.id.remain);
        time = (Chronometer) findViewById(R.id.chronometer);

        number1Text = (TextView) findViewById(R.id.number1);
        number2Text = (TextView) findViewById(R.id.number2);
        answerText = (TextView) findViewById(R.id.answer);
        correctTimesText = (TextView) findViewById(R.id.correct);
        flagText = (TextView)findViewById(R.id.flagText);

        correctImage = (ImageView) findViewById(R.id.correct_image);
        incorrectImage = (ImageView) findViewById(R.id.incorrect_image);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        //値の取得
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        timeKind = getIntent().getIntExtra("timeKind", 0);

        eraserColor = prefs.getInt(SettingsActivity.KEY_ERASER_COLOR, 1);
        minus = prefs.getBoolean(SettingsActivity.KEY_MINUS, false);


        if(timeKind == 0){

            questionTimes = prefs.getInt(SettingsActivity.KEY_QUESTION_NUMBER, 10);

            remainTimes = questionTimes;

            remainText.setText(questionTimes + "問");

            progressBar.setMax(questionTimes);
            progressBar.setProgress(0);
        }else if(timeKind == 1){

            questionTime = prefs.getInt(SettingsActivity.KEY_QUESTION_TIME, 0);

            remainTime = questionTime;
            remainText.setText(remainTime + "秒");

            progressBar.setMax((int)questionTime);

            CountDownTimer countDownTimer = new CountDownTimer(remainTime * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    remainTime = remainTime - 1;
                    remainText.setText(remainTime + "秒");

                    progressBar.setProgress((int)remainTime);
                }

                @Override
                public void onFinish() {
                    endTime = System.currentTimeMillis();
                    time.stop();
                    Intent intent = new Intent();
                    intent.putExtra("correct", correctTimes);
                    intent.putExtra("question_numbers", times);
                    intent.putExtra("time", questionTime * 1000);
                    intent.putExtra("calculationKind", calculationKind);
                    intent.putExtra("timeKind", 1);
                    intent.setAction(Intent.ACTION_MAIN);
                    intent.setClass(CalculationActivity.this, FinishActivity.class);
                    startActivity(intent);
                }
            };

            countDownTimer.start();
        }

        int image = getResources().getIdentifier("delete_button_" + eraserColor, "drawable", getPackageName());
        eraserImage.setImageResource(image);

        correctTimesText.setText("0" + "問");

        calculationKind = getIntent().getIntExtra("timeKind", 0);
        if(calculationKind == 0){
            flagText.setText("+");
        }else if(calculationKind == 1){
            flagText.setText("-");
        }else if(calculationKind == 2){
            flagText.setText("×");
        }else if(calculationKind == 3){
            flagText.setText("÷");
        }

        new_question();
        correctTimes = 0;
        times = 0;

        correctImage.setVisibility(View.GONE);
        incorrectImage.setVisibility(View.GONE);

        totalTime = 0;

        startTime = System.currentTimeMillis();

        time.setBase(android.os.SystemClock.elapsedRealtime());
        time.start();
    }

    public void new_question() {

        if(calculationKind == 0){

            number1 = (int) (Math.random() * 98) + 1;
            number2 = (int) (Math.random() * 98) + 1;
            correctAnswer = number1 + number2;
        }else if(calculationKind == 1){

            number1 = (int) (Math.random() * 98) + 1;
            number2 = (int) (Math.random() * 98) + 1;
            if (!minus) {
                if (number1 < number2){
                    userAnswer = number1;
                    number1 = number2;
                    number2 = userAnswer;
                }
            }
            correctAnswer = number1 - number2;
        }else if(calculationKind == 2){

            userAnswer = 0;
            correctAnswer = 0;
            answerText.setText("");
            number1 = (int) (Math.random() * 9) + 1;
            number2 = (int) (Math.random() * 9) + 1;
            correctAnswer = number1 * number2;
        }else if(calculationKind == 3){

            userAnswer = 0;
            correctAnswer = 0;
            answerText.setText("");
            number2 = (int) (Math.random() * 9) + 1;
            number1 = (int) (Math.random() * 9) + 1;
            correctAnswer = number1;
            number1 = number1 * number2;
        }

        userAnswer = 0;
        answerText.setText("");
        number1Text.setText(String.valueOf(number1));
        number2Text.setText(String.valueOf(number2));
    }

    public void finish() {
        endTime = System.currentTimeMillis();
        time.stop();
        totalTime = endTime - startTime;
        Intent intent = new Intent();
        intent.putExtra("correct", correctTimes);
        intent.putExtra("calculationKind", calculationKind);
        intent.putExtra("time", totalTime);
        intent.putExtra("timeKind", 0);
        intent.putExtra("question_numbers", questionTimes);

        intent.setAction(Intent.ACTION_MAIN);
        intent.setClass(CalculationActivity.this, FinishActivity.class);
        startActivity(intent);
    }

    public void click1(View v) {
        if (userAnswer == 0) {
            userAnswer = 1;
        } else {
            userAnswer = 1 + userAnswer * 10;
        }
        answerText.setText(String.valueOf(userAnswer));
    }

    public void click2(View v) {
        if (userAnswer == 0) {
            userAnswer = 2;
        } else {
            userAnswer = 2 + userAnswer * 10;
        }
        answerText.setText(String.valueOf(userAnswer));
    }

    public void click3(View v) {
        if (userAnswer == 0) {
            userAnswer = 3;
        } else {
            userAnswer = 3 + userAnswer * 10;
        }
        answerText.setText(String.valueOf(userAnswer));
    }

    public void click4(View v) {
        if (userAnswer == 0) {
            userAnswer = 4;
        } else {
            userAnswer = 4 + userAnswer * 10;
        }
        answerText.setText(String.valueOf(userAnswer));
    }

    public void click5(View v) {
        if (userAnswer == 0) {
            userAnswer = 5;
        } else {
            userAnswer = 5 + userAnswer * 10;
        }
        answerText.setText(String.valueOf(userAnswer));
    }

    public void click6(View v) {
        if (userAnswer == 0) {
            userAnswer = 6;
        } else {
            userAnswer = 6 + userAnswer * 10;
        }
        answerText.setText(String.valueOf(userAnswer));
    }

    public void click7(View v) {
        if (userAnswer == 0) {
            userAnswer = 7;
        } else {
            userAnswer = 7 + userAnswer * 10;
        }
        answerText.setText(String.valueOf(userAnswer));
    }

    public void click8(View v) {
        if (userAnswer == 0) {
            userAnswer = 8;
        } else {
            userAnswer = 8 + userAnswer * 10;
        }
        answerText.setText(String.valueOf(userAnswer));
    }

    public void click9(View v) {
        if (userAnswer == 0) {
            userAnswer = 9;
        } else {
            userAnswer = 9 + userAnswer * 10;
        }
        answerText.setText(String.valueOf(userAnswer));
    }

    public void click0(View v) {
        if (userAnswer == 0) {
            userAnswer = 0;
        } else {
            userAnswer = userAnswer * 10;
        }
        answerText.setText(String.valueOf(userAnswer));
    }

    public void erase(View v) {
        if (userAnswer < 10) {
            userAnswer = 0;
            answerText.setText("");
        } else {
            userAnswer = userAnswer / 10;
            answerText.setText(String.valueOf(userAnswer));
        }
    }

    public void pause(View view) {
        endTime = System.currentTimeMillis();
        stopRealTime = time.getBase() - SystemClock.elapsedRealtime();
        time.stop();
        totalTime = endTime - startTime + totalTime;
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        final View layout = inflater.inflate(R.layout.pause_dialog, (ViewGroup) findViewById(R.id.pause));

        /* layout.findViewById(R.id.imageView3).setOnClickListener(listener);の前にlistenerを宣言。 */
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // idを取得して動作を宣言。
                int id = v.getId();
                if (id == R.id.imageView3) {
                    // restart
                    Intent intent = new Intent();
                    intent.setClass(CalculationActivity.this, CalculationActivity.class);
                    intent.putExtra("timeKind", calculationKind);
                    startActivity(intent);
                } else if (id == R.id.imageView4) {
                    // resume
                    dialog.hide();
                    time.setBase(android.os.SystemClock.elapsedRealtime() + stopRealTime);
                    time.start();
                    startTime = System.currentTimeMillis();
                    endTime = 0;
                } else if (id == R.id.imageView5) {
                    // go to home
                    Intent intent = new Intent();
                    intent.setClass(CalculationActivity.this, StartActivity.class);
                    startActivity(intent);
                }
            }
        };

        /* OnClickListenerを設定。listenerのなかでimageViewをさがす。 */
        layout.findViewById(R.id.imageView3).setOnClickListener(listener);
        layout.findViewById(R.id.imageView4).setOnClickListener(listener);
        layout.findViewById(R.id.imageView5).setOnClickListener(listener);

        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setTitle("Pausing");
        adb.setView(layout);
        dialog = adb.show();
    }

    public void next(View view) {
        times = times + 1;

        if (userAnswer == correctAnswer) {

            correctTimes = correctTimes + 1;

            if(timeKind == 0){

                if (times == questionTimes) {
                    finish();
                }
            }

            correctImage.setVisibility(View.VISIBLE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    correctImage.setVisibility(View.GONE);
                    new_question();
                }
            }, 1000);
        } else {

            if(timeKind == 0){

                if (times == questionTimes) {
                    finish();
                }
            }

            incorrectImage.setVisibility(View.VISIBLE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    incorrectImage.setVisibility(View.GONE);
                    new_question();
                }
            }, 1000);
        }

        correctTimesText.setText(correctTimes + "問");

        if(timeKind == 0){

            remainTimes = remainTimes - 1;
            if (remainTimes == questionTimes - times) {
                remainText.setText(remainTimes + "問");
            } else {
                remainText.setText("エラー");
            }
            progressBar.setProgress(times);
        }
    }
}