package com.example.chiharumiyoshi.calculation_practice_app;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class DivisionActivity extends AppCompatActivity {

    TextView number1Text, number2Text, answerText, correctText, remainText;
    ImageView eraserImage, correctImage, incorrectImage;
    int number1, number2, answer, cerrectAnswer, correctTimes, times, question_number, eraser_color, remain;
    long startTimeL, endTimeL, totalTimeL, stopRealtimeL;
    ProgressBar progressBar;
    Chronometer time;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_division);

        //View
        eraserImage = (ImageView) findViewById(R.id.imageView);

        number1Text = (TextView) findViewById(R.id.number1);
        number2Text = (TextView) findViewById(R.id.number2);
        answerText = (TextView) findViewById(R.id.answer);
        correctText = (TextView) findViewById(R.id.correct);

        remainText = (TextView) findViewById(R.id.remain);

        correctImage = (ImageView) findViewById(R.id.correct_img);
        incorrectImage = (ImageView) findViewById(R.id.incorrect_img);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        time = (Chronometer) findViewById(R.id.chronometer);


        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        question_number = prefs.getInt(SettingsActivity.KEY_QUESTION_NUMBER, 10);
        eraser_color = prefs.getInt(SettingsActivity.KEY_ERASER_COLOR, 1);

        int image = getResources().getIdentifier("delete_button_" + eraser_color, "drawable", getPackageName());
        eraserImage.setImageResource(image);

        correctText.setText("0" + "問");

        remainText.setText("のこり　" + question_number + "問");
        remain = question_number;

        new_question();

        correctTimes = 0;
        times = 0;
        correctImage.setVisibility(View.GONE);
        incorrectImage.setVisibility(View.GONE);

        progressBar.setMax(question_number);
        progressBar.setProgress(0);
        totalTimeL = 0;

        time.setBase(android.os.SystemClock.elapsedRealtime());
        time.start();
        startTimeL = System.currentTimeMillis();
    }

    public void new_question() {
        answer = 0;
        cerrectAnswer = 0;
        answerText.setText("");
        number2 = (int) (Math.random() * 9) + 1;
        number2Text.setText(String.valueOf(number2));
        number1 = (int) (Math.random() * 9) + 1;
        cerrectAnswer = number1;
        number1 = number1 * number2;
        number1Text.setText(String.valueOf(number1));
    }

    public void finish() {
        endTimeL = System.currentTimeMillis();
        time.stop();
        totalTimeL = endTimeL - startTimeL;
        Intent intent = new Intent();
        intent.putExtra("correctTimes", correctTimes);
        intent.putExtra("time", totalTimeL);
        intent.putExtra("last_activity", 4);
        intent.putExtra("calculationKind", 1);

        intent.setAction(Intent.ACTION_MAIN);
        intent.setClass(DivisionActivity.this, FinishActivity.class);
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
        endTimeL = System.currentTimeMillis();
        stopRealtimeL = time.getBase() - SystemClock.elapsedRealtime();
        time.stop();
        totalTimeL = endTimeL - startTimeL + totalTimeL;
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        final View layout = inflater.inflate(R.layout.pause_dialog, (ViewGroup) findViewById(R.id.pause));

        /* layout.findViewById(R.id.imageView3).setOnClickListener(listener);の前にlistenerを宣言。 */
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // idを取得して動作を宣言。
                int id = v.getId();
                if (id == R.id.imageView3) {
                    Intent intent = new Intent();
                    intent.setClass(DivisionActivity.this, DivisionActivity.class);
                    startActivity(intent);
                } else if (id == R.id.imageView4) {
                    dialog.hide();
                    time.setBase(android.os.SystemClock.elapsedRealtime() + stopRealtimeL);
                    time.start();
                    startTimeL = System.currentTimeMillis();
                    endTimeL = 0;
                } else if (id == R.id.imageView5) {
                    Intent intent = new Intent();
                    intent.putExtra("correctTimes", correctTimes);
                    intent.setClass(DivisionActivity.this, StartActivity.class);
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
        times = times + 1;

        if (answer == cerrectAnswer) {
            correctTimes = correctTimes + 1;

            if (times == question_number) {
                finish();
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
            if (times == question_number) {
                finish();
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

        correctText.setText(correctTimes + "問");
        remain = remain - 1;
        if (remain == question_number - times) {
            remainText.setText("のこり　" + remain + "問");
        } else {
            remainText.setText("残り問題数の計算に失敗しました。");
        }
        progressBar.setProgress(times);
    }
}
