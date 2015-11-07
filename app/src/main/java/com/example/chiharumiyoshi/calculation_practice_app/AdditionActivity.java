package com.example.chiharumiyoshi.calculation_practice_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class AdditionActivity extends AppCompatActivity {

    TextView number1, number2, answer, correct_t, remain_t;
    ImageView eraser_image, correct_img, incorrect_img;
    int n1, n2, a, ca, correct, times, question_number, eraser_color, remain;
    long start_time, end_time, total_time, stop_realtime;
    ProgressBar progressBar;
    Chronometer time;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addition);

        eraser_image = (ImageView) findViewById(R.id.imageView);
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        question_number = prefs.getInt(SettingsActivity.KEY_QUESTION_NUMBER, 10);
        eraser_color = prefs.getInt(SettingsActivity.KEY_ERASER_COLOR, 1);
        int image = getResources().getIdentifier("delete_button_" + eraser_color, "drawable", getPackageName());
        eraser_image.setImageResource(image);
        number1 = (TextView) findViewById(R.id.number1);
        number2 = (TextView) findViewById(R.id.number2);
        answer = (TextView) findViewById(R.id.answer);
        correct_t = (TextView) findViewById(R.id.correct);
        correct_t.setText("0" + "問");
        remain_t = (TextView) findViewById(R.id.remain);
        remain_t.setText("のこり　" + question_number + "問");
        remain = question_number;
        new_question();
        correct = 0;
        times = 0;
        correct_img = (ImageView) findViewById(R.id.correct_img);
        correct_img.setVisibility(View.GONE);
        incorrect_img = (ImageView) findViewById(R.id.incorrect_img);
        incorrect_img.setVisibility(View.GONE);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setMax(question_number);
        progressBar.setProgress(0);
        total_time = 0;
        time = (Chronometer) findViewById(R.id.chronometer);
        time.setBase(android.os.SystemClock.elapsedRealtime());
        time.start();
        start_time = System.currentTimeMillis();
    }

    public void new_question() {
        a = 0;
        ca = 0;
        answer.setText("");
        n1 = (int) (Math.random() * 98) + 1;
        number1.setText(String.valueOf(n1));
        n2 = (int) (Math.random() * 98) + 1;
        number2.setText(String.valueOf(n2));
        ca = n1 + n2;
    }

    public void finish() {
        end_time = System.currentTimeMillis();
        time.stop();
        total_time = end_time - start_time;
        Intent intent = new Intent();
        intent.putExtra("correct", correct);
        intent.putExtra("time", total_time);
        intent.putExtra("last_activity", 1);
        intent.setAction(Intent.ACTION_MAIN);
        intent.setClass(AdditionActivity.this, FinishActivity.class);
        startActivity(intent);
    }

    public void click1(View v) {
        if (a == 0) {
            a = 1;
        } else {
            a = 1 + a * 10;
        }
        answer.setText(String.valueOf(a));
    }

    public void click2(View v) {
        if (a == 0) {
            a = 2;
        } else {
            a = 2 + a * 10;
        }
        answer.setText(String.valueOf(a));
    }

    public void click3(View v) {
        if (a == 0) {
            a = 3;
        } else {
            a = 3 + a * 10;
        }
        answer.setText(String.valueOf(a));
    }

    public void click4(View v) {
        if (a == 0) {
            a = 4;
        } else {
            a = 4 + a * 10;
        }
        answer.setText(String.valueOf(a));
    }

    public void click5(View v) {
        if (a == 0) {
            a = 5;
        } else {
            a = 5 + a * 10;
        }
        answer.setText(String.valueOf(a));
    }

    public void click6(View v) {
        if (a == 0) {
            a = 6;
        } else {
            a = 6 + a * 10;
        }
        answer.setText(String.valueOf(a));
    }

    public void click7(View v) {
        if (a == 0) {
            a = 7;
        } else {
            a = 7 + a * 10;
        }
        answer.setText(String.valueOf(a));
    }

    public void click8(View v) {
        if (a == 0) {
            a = 8;
        } else {
            a = 8 + a * 10;
        }
        answer.setText(String.valueOf(a));
    }

    public void click9(View v) {
        if (a == 0) {
            a = 9;
        } else {
            a = 9 + a * 10;
        }
        answer.setText(String.valueOf(a));
    }

    public void click0(View v) {
        if (a == 0) {
            a = 0;
        } else {
            a = a * 10;
        }
        answer.setText(String.valueOf(a));
    }

    public void erase(View v) {
        if (a < 10) {
            a = 0;
            answer.setText("");
        } else {
            a = a / 10;
            answer.setText(String.valueOf(a));
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
                    Intent intent = new Intent();
                    intent.setClass(AdditionActivity.this, AdditionActivity.class);
                    startActivity(intent);
                } else if (id == R.id.imageView4) {
                    dialog.hide();
                    time.setBase(android.os.SystemClock.elapsedRealtime() + stop_realtime);
                    time.start();
                    start_time = System.currentTimeMillis();
                    end_time = 0;
                } else if (id == R.id.imageView5) {
                    Intent intent = new Intent();
                    intent.putExtra("correct", correct);
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
        times = times + 1;

        if (a == ca) {
            correct = correct + 1;

            if (times == question_number) {
                finish();
            }

            correct_img.setVisibility(View.VISIBLE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    correct_img.setVisibility(View.GONE);
                    new_question();
                }
            }, 1000);
        } else {
            if (times == question_number) {
                finish();
            }
            incorrect_img.setVisibility(View.VISIBLE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    incorrect_img.setVisibility(View.GONE);
                    new_question();
                }
            }, 1000);
        }

        correct_t.setText(correct + "問");
        remain = remain - 1;
        if (remain == question_number - times) {
            remain_t.setText("のこり　" + remain + "問");
        } else {
            remain_t.setText("残り問題数の計算に失敗しました。");
        }
        progressBar.setProgress(times);
    }
}
