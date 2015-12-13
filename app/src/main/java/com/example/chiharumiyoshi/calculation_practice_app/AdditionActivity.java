package com.example.chiharumiyoshi.calculation_practice_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
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

    TextView number1Text, number2Text, answerText, correctTimesText, remainText;
    ImageView eraserImage, correctImage, incorrectImage;
    int number1, number2, answer, correctAnswerNumber, correctTimes, timesNumber, questionNumber, eraserColor, remain, calculationKind;
    long startTime, endTime, totalTime, stopRealtime, remainTime, questionTime;
    ProgressBar progressBar;
    Chronometer time;
    AlertDialog dialog;

//    private SQLiteDatabase database;
//    private static String DATABASE_NAME = "questions.db";
//    private static String DATABASE_TABLE = "questions";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addition);

        // Viewの関連付け
        eraserImage = (ImageView) findViewById(R.id.imageView);
        remainText = (TextView) findViewById(R.id.remain);
        time = (Chronometer) findViewById(R.id.chronometer);

        number1Text = (TextView) findViewById(R.id.number1);
        number2Text = (TextView) findViewById(R.id.number2);
        answerText = (TextView) findViewById(R.id.answer);
        correctTimesText = (TextView) findViewById(R.id.correct);

        correctImage = (ImageView) findViewById(R.id.correct_img);
        incorrectImage = (ImageView) findViewById(R.id.incorrect_img);


//        DatabaseHelper databaseHelper = new DatabaseHelper(this);
//        database = databaseHelper.getWritableDatabase();


        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        eraserColor = prefs.getInt(SettingsActivity.KEY_ERASER_COLOR, 1);


        questionNumber = prefs.getInt(SettingsActivity.KEY_QUESTION_NUMBER, 10);

        remain = questionNumber;

        remainText.setText(questionNumber + "問");

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setMax(questionNumber);
        progressBar.setProgress(0);

        int image = getResources().getIdentifier("delete_button_" + eraserColor, "drawable", getPackageName());
        eraserImage.setImageResource(image);

        correctTimesText.setText("0" + "問");

        new_question();
        correctTimes = 0;
        timesNumber = 0;

        correctImage.setVisibility(View.GONE);
        incorrectImage.setVisibility(View.GONE);

        totalTime = 0;

        startTime = System.currentTimeMillis();

        time.setBase(android.os.SystemClock.elapsedRealtime());
        time.start();
    }

//    private void writeDatabase(String info) throws Exception {
//        ContentValues values = new ContentValues();
//        values.put("id", "0");
//        values.put("info", info);
//        int columnNumber = database.update(DATABASE_TABLE, values, null, null);
//        if (columnNumber == 0) database.insert(DATABASE_TABLE,"", values);
//    }
//
//    private static class DatabaseHelper extends SQLiteOpenHelper {
//        public DatabaseHelper(Context context){
//            super(context, DATABASE_NAME, null, 1);
//        }
//
//        @Override
//        public void onCreate(SQLiteDatabase database){
//            database.execSQL("create table if not exists " + DATABASE_NAME + "(id tet primary key, info text)");
//        }
//
//        @Override
//        public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
//            database.execSQL("drop table if exists " + DATABASE_TABLE);
//            onCreate(database);
//        }
//    }

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
        endTime = System.currentTimeMillis();
        time.stop();
        totalTime = endTime - startTime;
        Intent intent = new Intent();
        intent.putExtra("correct", correctTimes);
        intent.putExtra("last_activity", 1);
        intent.putExtra("calculationKind", 1);

        if(calculationKind == 1){
            intent.putExtra("time", totalTime);
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
        endTime = System.currentTimeMillis();
        stopRealtime = time.getBase() - SystemClock.elapsedRealtime();
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
                    intent.setClass(AdditionActivity.this, AdditionActivity.class);
                    startActivity(intent);
                } else if (id == R.id.imageView4) {
                    // resume
                    dialog.hide();
                    time.setBase(android.os.SystemClock.elapsedRealtime() + stopRealtime);
                    time.start();
                    startTime = System.currentTimeMillis();
                    endTime = 0;
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
                if (timesNumber == questionNumber) {
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
                if (timesNumber == questionNumber) {
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
            if (remain == questionNumber - timesNumber) {
                remainText.setText(remain + "問");
            } else {
                remainText.setText("エラー");
            }
            progressBar.setProgress(timesNumber);
        }
    }
}