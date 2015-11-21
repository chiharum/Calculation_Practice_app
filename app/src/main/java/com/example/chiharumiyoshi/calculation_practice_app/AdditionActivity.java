package com.example.chiharumiyoshi.calculation_practice_app;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class AdditionActivity extends AppCompatActivity {

    TextView number1Text, number2Text, answerText, correctTimesText, remainTimesText;
    ImageView eraserImage, correctImage, incorrectImage;
    int number1, number2, answer, correctAnswerNumber, correctTimes, timesNumber, question_number, question_time, eraser_color, remain, calculationKind;
    long start_time, end_time, total_time, stop_realtime, remain_time;
    ProgressBar progressBar;
    Chronometer time;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addition);

        eraserImage = (ImageView) findViewById(R.id.imageView);

        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        calculationKind = prefs.getInt("calculationKind", 0);

        remainTimesText = (TextView) findViewById(R.id.remain);

        if(calculationKind == 1){
            question_number = prefs.getInt(SettingsActivity.KEY_QUESTION_NUMBER, 10);
            remain = question_number;

            remainTimesText.setText(question_number + "問");

            progressBar = (ProgressBar) findViewById(R.id.progressBar);
            progressBar.setMax(question_number);
            progressBar.setProgress(0);

        }else if(calculationKind == 2){
            question_time = prefs.getInt(SettingsActivity.KEY_QUESTION_TIME, 30);

            remainTimesText.setText(remain_time + "秒");

            CountDownTimer countDownTimer = new CountDownTimer(remain_time * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    remain_time = remain_time - 1;
                    remainTimesText.setText(remain_time + "秒");
                }

                @Override
                public void onFinish() {
                    end_time = System.currentTimeMillis();
                    time.stop();
                    Intent intent = new Intent();
                    intent.putExtra("correct", correctTimes);
                    intent.putExtra("question_numbers", timesNumber);
                    intent.putExtra("time", remain_time);
                    intent.putExtra("last_activity", 1);
                    intent.setAction(Intent.ACTION_MAIN);
                    intent.setClass(AdditionActivity.this, FinishActivity.class);
                    startActivity(intent);
                }
            };

            countDownTimer.start();
        }

        eraser_color = prefs.getInt(SettingsActivity.KEY_ERASER_COLOR, 1);
        int image = getResources().getIdentifier("delete_button_" + eraser_color, "drawable", getPackageName());
        eraserImage.setImageResource(image);

        number1Text = (TextView) findViewById(R.id.number1);
        number2Text = (TextView) findViewById(R.id.number2);
        answerText = (TextView) findViewById(R.id.answer);
        correctTimesText = (TextView) findViewById(R.id.correct);
        correctTimesText.setText("0" + "問");

        new_question();
        correctTimes = 0;
        timesNumber = 0;

        correctImage = (ImageView) findViewById(R.id.correct_img);
        correctImage.setVisibility(View.GONE);
        incorrectImage = (ImageView) findViewById(R.id.incorrect_img);
        incorrectImage.setVisibility(View.GONE);

        total_time = 0;
        time = (Chronometer) findViewById(R.id.chronometer);
        time.setBase(android.os.SystemClock.elapsedRealtime());
        time.start();
        start_time = System.currentTimeMillis();
    }

    public void new_question() {
        answer = 0;
        correctAnswerNumber = 0;
        answerText.setText("");
        number1 = (int) (Math.random() * 98) + 1;
        number1Text.setText(String.valueOf(number1));
        number2 = (int) (Math.random() * 98) + 1;
        number2Text.setText(String.valueOf(number2));
        correctAnswerNumber = number1 + number2;
    }

    public void finish() {
        end_time = System.currentTimeMillis();
        time.stop();
        total_time = end_time - start_time;
        Intent intent = new Intent();
        intent.putExtra("correct", correctTimes);
        intent.putExtra("last_activity", 1);

        if(calculationKind == 1){
            intent.putExtra("time", total_time);
        }

        intent.setAction(Intent.ACTION_MAIN);
        intent.setClass(AdditionActivity.this, FinishActivity.class);
        startActivity(intent);
    }

    public void click1(View v) {
        if (answer == 0) {
            answer = 1;
        } else {
            answer = 1 + answer * 10;
        }
        answerText.setText(String.valueOf(answer));
    }

    public void click2(View v) {
        if (answer == 0) {
            answer = 2;
        } else {
            answer = 2 + answer * 10;
        }
        answerText.setText(String.valueOf(answer));
    }

    public void click3(View v) {
        if (answer == 0) {
            answer = 3;
        } else {
            answer = 3 + answer * 10;
        }
        answerText.setText(String.valueOf(answer));
    }

    public void click4(View v) {
        if (answer == 0) {
            answer = 4;
        } else {
            answer = 4 + answer * 10;
        }
        answerText.setText(String.valueOf(answer));
    }

    public void click5(View v) {
        if (answer == 0) {
            answer = 5;
        } else {
            answer = 5 + answer * 10;
        }
        answerText.setText(String.valueOf(answer));
    }

    public void click6(View v) {
        if (answer == 0) {
            answer = 6;
        } else {
            answer = 6 + answer * 10;
        }
        answerText.setText(String.valueOf(answer));
    }

    public void click7(View v) {
        if (answer == 0) {
            answer = 7;
        } else {
            answer = 7 + answer * 10;
        }
        answerText.setText(String.valueOf(answer));
    }

    public void click8(View v) {
        if (answer == 0) {
            answer = 8;
        } else {
            answer = 8 + answer * 10;
        }
        answerText.setText(String.valueOf(answer));
    }

    public void click9(View v) {
        if (answer == 0) {
            answer = 9;
        } else {
            answer = 9 + answer * 10;
        }
        answerText.setText(String.valueOf(answer));
    }

    public void click0(View v) {
        if (answer == 0) {
            answer = 0;
        } else {
            answer = answer * 10;
        }
        answerText.setText(String.valueOf(answer));
    }

    public void erase(View v) {
        if (answer < 10) {
            answer = 0;
            answerText.setText("");
        } else {
            answer = answer / 10;
            answerText.setText(String.valueOf(answer));
        }
    }

    public void pause(View view) {
        end_time = System.currentTimeMillis();
        stop_realtime = time.getBase() - SystemClock.elapsedRealtime();
        time.stop();
        total_time = end_time - start_time + total_time;
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
                    intent.setClass(AdditionActivity.this, AdditionActivity.class);
                    startActivity(intent);
                } else if (id == R.id.imageView4) {
                    // resume
                    dialog.hide();
                    time.setBase(android.os.SystemClock.elapsedRealtime() + stop_realtime);
                    time.start();
                    start_time = System.currentTimeMillis();
                    end_time = 0;
                } else if (id == R.id.imageView5) {
                    // go to home
                    Intent intent = new Intent();
                    intent.putExtra("correctTimes", correctTimes);
                    intent.setClass(AdditionActivity.this, StartActivity.class);
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

    public void next(View v) {
        timesNumber = timesNumber + 1;

        if (answer == correctAnswerNumber) {
            correctTimes = correctTimes + 1;

            if (calculationKind == 1){
                if (timesNumber == question_number) {
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

            if (calculationKind == 1){
                if (timesNumber == question_number) {
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

        if(calculationKind == 1){
            remain = remain - 1;
            if (remain == question_number - timesNumber) {
                remainTimesText.setText(remain + "問");
            } else {
                remainTimesText.setText("エラー");
            }
            progressBar.setProgress(timesNumber);
        }
    }
}
