package com.example.chiharumiyoshi.calculation_practice_app;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class CalculationActivity extends AppCompatActivity {

    int number1, number2, userAnswer, correctAnswer, correctTimes, times, questionTimes, eraserColor, remainTimes, calculationKind, timeKind, indexCorrect_times, indexTotal_time, reviewId, reviewRandom, totalForwardAddition,totalForwardAdditionSubtraction, totalForwardMultiplication, totalForwardDivision, numberNumbersAmount;
    long startedTime, endedTime, totalTime, stopRealTime, questionTime, remainTime;
    boolean minus, forward, review, imageSkipped, addition, subtraction, multiplication, division, additionNumberOne, additionNumberTwo, additionNumberThree, subtractionNumberOne, subtractionNumberTwo, subtractionNumberThree, multiplicationNumberOne, multiplicationNumberTwo, multiplicationNumberThree, divisionNumberOne, divisionNumberTwo, divisionNumberThree;
    String[] calculationKinds;
    ArrayList<Integer> number1Records, number2Records, correctAnswerRecords, answerRecords;
    TextView number1Text, number2Text, answerText, correctTimesText, remainText, flagText;
    ImageView eraserImage, judgeImage;
    ProgressBar progressBar;
    Chronometer timeChronometer;
    AlertDialog dialog;
    Button nextButton;
    SharedPreferences prefs;

    static final String TOTAL_FORWARD_ADDITION = "totalForwardAddition";
    static final String TOTAL_FORWARD_SUBTRACTION = "totalForwardDivision";
    static final String TOTAL_FORWARD_MULTIPLICATION = "totalForwardMultiplication";
    static final String TOTAL_FORWARD_DIVISION = "totalForwardDivision";

    MySQLiteOpenHelper mySQLiteOpenHelper;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculation);

        mySQLiteOpenHelper = new MySQLiteOpenHelper(getApplicationContext());
        database = mySQLiteOpenHelper.getWritableDatabase();

        // View
        eraserImage = (ImageView) findViewById(R.id.imageView);
        remainText = (TextView) findViewById(R.id.remain);
        timeChronometer = (Chronometer) findViewById(R.id.chronometer);
        number1Text = (TextView) findViewById(R.id.number1);
        number2Text = (TextView) findViewById(R.id.number2);
        answerText = (TextView) findViewById(R.id.answer);
        correctTimesText = (TextView) findViewById(R.id.correct);
        flagText = (TextView)findViewById(R.id.flagText);
        judgeImage = (ImageView)findViewById(R.id.judgeImage);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        nextButton = (Button)findViewById(R.id.button);

        //data
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        eraserColor = prefs.getInt(SettingsActivity.KEY_ERASER_COLOR_SETTINGS, 1);
        minus = prefs.getBoolean(SettingsActivity.KEY_MINUS_SETTINGS, false);
        forward = prefs.getBoolean(SettingsActivity.KEY_FORWARD_SETTINGS, false);
        totalForwardAddition = prefs.getInt(TOTAL_FORWARD_ADDITION, 0);
        totalForwardAdditionSubtraction = prefs.getInt(TOTAL_FORWARD_SUBTRACTION, 0);
        totalForwardMultiplication = prefs.getInt(TOTAL_FORWARD_MULTIPLICATION, 0);
        totalForwardDivision = prefs.getInt(TOTAL_FORWARD_DIVISION, 0);
        timeKind = getIntent().getIntExtra("timeKind", 0);
        addition = getIntent().getBooleanExtra("addition", true);
        subtraction = getIntent().getBooleanExtra("subtraction", true);
        multiplication = getIntent().getBooleanExtra("multiplication", true);
        division = getIntent().getBooleanExtra("division", true);
        additionNumberOne = getIntent().getBooleanExtra("additionNumberOne", true);
        additionNumberTwo = getIntent().getBooleanExtra("additionNumberTwo", true);
        additionNumberThree = getIntent().getBooleanExtra("additionNumberThree", true);
        subtractionNumberOne = getIntent().getBooleanExtra("subtractionNumberOne", true);
        subtractionNumberTwo = getIntent().getBooleanExtra("subtractionNumberTwo", true);
        subtractionNumberThree = getIntent().getBooleanExtra("subtractionNumberThree", true);
        multiplicationNumberOne = getIntent().getBooleanExtra("multiplicationNumberOne", true);
        multiplicationNumberTwo = getIntent().getBooleanExtra("multiplicationNumberTwo", true);
        multiplicationNumberThree = getIntent().getBooleanExtra("multiplicationNumberThree", true);
        divisionNumberOne = getIntent().getBooleanExtra("divisionNumberOne", true);
        divisionNumberTwo = getIntent().getBooleanExtra("divisionNumberTwo", true);
        divisionNumberThree = getIntent().getBooleanExtra("divisionNumberThree", true);

        int width = prefs.getInt("width", 500);
        nextButton.setTextSize((float)width / (float)25.6);

        review = false;
        number1Records = new ArrayList<Integer>();
        number2Records = new ArrayList<Integer>();
        answerRecords = new ArrayList<Integer>();
        correctAnswerRecords = new ArrayList<Integer>();
        calculationKinds = new String[4];

        if(timeKind == 0){

            questionTimes = prefs.getInt(SettingsActivity.KEY_QUESTION_TIMES_SETTINGS, 10);

            remainTimes = questionTimes;

            remainText.setText(questionTimes + "問");

            progressBar.setMax(questionTimes);
            progressBar.setProgress(0);

        }else if(timeKind == 1){

            questionTime = prefs.getLong(SettingsActivity.KEY_QUESTION_TIME_SETTINGS, 30);

            remainTime = questionTime;
            remainText.setText(remainTime + "秒");

            progressBar.setMax((int) questionTime);
            progressBar.setProgress((int) questionTime);

            CountDownTimer countDownTimer = new CountDownTimer(remainTime * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    remainTime = remainTime - 1;
                    remainText.setText(remainTime + "秒");

                    progressBar.setProgress((int)remainTime);
                }

                @Override
                public void onFinish() {
                    finish();
                }
            };

            countDownTimer.start();
        }

        int image = getResources().getIdentifier("delete_button_" + eraserColor, "drawable", getPackageName());
        eraserImage.setImageResource(image);

        correctTimesText.setText("0" + "問");

        newQuestion();
        correctTimes = 0;
        times = 0;

        judgeImage.setVisibility(View.GONE);

        totalTime = 0;

        startedTime = System.currentTimeMillis();

        timeChronometer.setBase(android.os.SystemClock.elapsedRealtime());
        timeChronometer.start();
    }

    public void newQuestion() {

        setCalculationKind();

        if(calculationKind == 0){

            reviewRandom = (int)(Math.random() * 3);

            if(forward && reviewRandom == 0 && totalForwardAddition > 10){

                review = true;
                reviewId = (int)(Math.random() * totalForwardAddition + 1);
                searchForward(reviewId);
            }else{

                if(additionNumberOne && additionNumberTwo && additionNumberThree){
                    numberNumbersAmount = (int)(Math.random() * 3 + 1);
                }else if(additionNumberOne && additionNumberTwo){
                    numberNumbersAmount = (int)(Math.random() * 2 + 1);
                }else if(additionNumberOne){
                    numberNumbersAmount = (int)(Math.random() * 1 + 1);
                }

                if(numberNumbersAmount == 1){
                    number1 = (int) (Math.random() * 9) + 1;
                }else if(numberNumbersAmount == 2){
                    number1 = (int) (Math.random() * 98) + 1;
                }else if(numberNumbersAmount == 3){
                    number1 = (int) (Math.random() * 998) + 1;
                }

                if(additionNumberOne && additionNumberTwo && additionNumberThree){
                    numberNumbersAmount = (int)(Math.random() * 3 + 1);
                }else if(additionNumberOne && additionNumberTwo){
                    numberNumbersAmount = (int)(Math.random() * 2 + 1);
                }else if(additionNumberOne){
                    numberNumbersAmount = (int)(Math.random() * 1 + 1);
                }

                if(numberNumbersAmount == 1){
                    number2 = (int) (Math.random() * 9) + 1;
                }else if(numberNumbersAmount == 2){
                    number2 = (int) (Math.random() * 98) + 1;
                }else if(numberNumbersAmount == 3){
                    number2 = (int) (Math.random() * 998) + 1;
                }
            }

            correctAnswer = number1 + number2;

            flagText.setText("+");

        }else if(calculationKind == 1){

            reviewRandom = (int)(Math.random() * 3);

            if(forward && reviewRandom == 0 && totalForwardAdditionSubtraction > 10){

                review = true;
                reviewId = (int)(Math.random() * totalForwardAdditionSubtraction + 1);
                searchForward(reviewId);
            }else{

                if(subtractionNumberOne && subtractionNumberTwo && subtractionNumberThree){
                    numberNumbersAmount = (int)(Math.random() * 3 + 1);
                }else if(subtractionNumberOne && subtractionNumberTwo){
                    numberNumbersAmount = (int)(Math.random() * 2 + 1);
                }else if(subtractionNumberThree){
                    numberNumbersAmount = (int)(Math.random() * 1 + 1);
                }

                if(numberNumbersAmount == 1){
                    number1 = (int) (Math.random() * 9) + 1;
                }else if(numberNumbersAmount == 2){
                    number1 = (int) (Math.random() * 98) + 1;
                }else if(numberNumbersAmount == 3){
                    number1 = (int) (Math.random() * 998) + 1;
                }

                if(subtractionNumberOne && subtractionNumberTwo && subtractionNumberThree){
                    numberNumbersAmount = (int)(Math.random() * 3 + 1);
                }else if(subtractionNumberOne && subtractionNumberTwo){
                    numberNumbersAmount = (int)(Math.random() * 2 + 1);
                }else if(subtractionNumberThree){
                    numberNumbersAmount = (int)(Math.random() * 1 + 1);
                }

                if(numberNumbersAmount == 1){
                    number2 = (int) (Math.random() * 9) + 1;
                }else if(numberNumbersAmount == 2){
                    number2 = (int) (Math.random() * 98) + 1;
                }else if(numberNumbersAmount == 3){
                    number2 = (int) (Math.random() * 998) + 1;
                }
            }

            if (!minus) {
                if (number1 < number2){
                    userAnswer = number1;
                    number1 = number2;
                    number2 = userAnswer;
                }
            }
            correctAnswer = number1 - number2;

            flagText.setText("-");

        }else if(calculationKind == 2){

            reviewRandom = (int)(Math.random() * 3);

            if(forward && reviewRandom == 0 && totalForwardMultiplication > 10){

                review = true;
                reviewId = (int)(Math.random() * totalForwardMultiplication + 1);
                searchForward(reviewId);
                Log.i("review", "reviewed");
            }else{

                if(multiplicationNumberOne && multiplicationNumberTwo && multiplicationNumberThree){
                    numberNumbersAmount = (int)(Math.random() * 3 + 1);
                }else if(multiplicationNumberOne && multiplicationNumberTwo){
                    numberNumbersAmount = (int)(Math.random() * 2 + 1);
                }else if(multiplicationNumberThree){
                    numberNumbersAmount = (int)(Math.random() * 1 + 1);
                }

                if(numberNumbersAmount == 1){
                    number1 = (int) (Math.random() * 9) + 1;
                }else if(numberNumbersAmount == 2){
                    number1 = (int) (Math.random() * 98) + 1;
                }else if(numberNumbersAmount == 3){
                    number1 = (int) (Math.random() * 998) + 1;
                }

                if(multiplicationNumberOne && multiplicationNumberTwo && multiplicationNumberThree){
                    numberNumbersAmount = (int)(Math.random() * 3 + 1);
                }else if(multiplicationNumberOne && multiplicationNumberTwo){
                    numberNumbersAmount = (int)(Math.random() * 2 + 1);
                }else if(multiplicationNumberThree){
                    numberNumbersAmount = (int)(Math.random() * 1 + 1);
                }

                if(numberNumbersAmount == 1){
                    number2 = (int) (Math.random() * 9) + 1;
                }else if(numberNumbersAmount == 2){
                    number2 = (int) (Math.random() * 98) + 1;
                }else if(numberNumbersAmount == 3){
                    number2 = (int) (Math.random() * 998) + 1;
                }
            }

            correctAnswer = number1 * number2;

            flagText.setText("×");

        }else if(calculationKind == 3){

            reviewRandom = (int)(Math.random() * 3);

            if(forward && reviewRandom == 0 && totalForwardDivision > 10){

                review = true;
                reviewId = (int)(Math.random() * totalForwardDivision + 1);
                searchForward(reviewId);
                Log.i("review", "reviewed");
            }else{

                chooseDivision();

                if(divisionNumberThree){
                    while(number1 * number2 >= 1000){
                        chooseDivision();
                    }
                }else if(divisionNumberTwo){
                    while(number1 * number2 >= 100){
                        chooseDivision();
                    }
                }else if(divisionNumberOne){
                    while(number1 * number2 >= 10){
                        chooseDivision();
                    }
                }
            }

            correctAnswer = number1;
            number1 = number1 * number2;

            flagText.setText("÷");
        }

        userAnswer = 0;
        answerText.setText("");
        number1Text.setText(String.valueOf(number1));
        number2Text.setText(String.valueOf(number2));
    }

    public void chooseDivision(){

        if(divisionNumberOne && divisionNumberTwo && divisionNumberThree){
            numberNumbersAmount = (int)(Math.random() * 3 + 1);
        }else if(divisionNumberOne && divisionNumberTwo){
            numberNumbersAmount = (int)(Math.random() * 2 + 1);
        }else if(divisionNumberOne){
            numberNumbersAmount = (int)(Math.random() * 1 + 1);
        }

        if(numberNumbersAmount == 1){
            number2 = (int) (Math.random() * 9) + 1;
        }else if(numberNumbersAmount == 2){
            number2 = (int) (Math.random() * 98) + 1;
        }else if(numberNumbersAmount == 3){
            number2 = (int) (Math.random() * 998) + 1;
        }

        number1 = (int) (Math.random() * 99) + 1;
    }

    public void searchForward(int id){

        if(calculationKind == 0){
            Cursor cursor = null;

            try{
                cursor = database.query(MySQLiteOpenHelper.FORWARD_ADDITION_TABLE, new String[]{"id", "number1", "number2", "correct_times", "total_times"}, "id = ?", new String[]{String.valueOf(id)}, null, null, null);

                int indexNumber1 = cursor.getColumnIndex("number1");
                int indexNumber2 = cursor.getColumnIndex("number2");
                indexCorrect_times = cursor.getColumnIndex("correct_times");
                indexTotal_time = cursor.getColumnIndex("total_time");

                while(cursor.moveToNext()){
                    number2 = cursor.getInt(indexNumber2);
                    number1 = cursor.getInt(indexNumber1);
                }
            }finally {
                if(cursor != null){
                    cursor.close();
                }
            }
        }else if(calculationKind == 1){
            Cursor cursor = null;

            try{
                cursor = database.query(MySQLiteOpenHelper.FORWARD_SUBTRACTION_TABLE, new String[]{"id", "number1", "number2", "correct_times", "total_times"}, "id = ?", new String[]{String.valueOf(id)}, null, null, null);

                int indexNumber1 = cursor.getColumnIndex("number1");
                int indexNumber2 = cursor.getColumnIndex("number2");
                indexCorrect_times = cursor.getColumnIndex("correct_times");
                indexTotal_time = cursor.getColumnIndex("total_time");

                while(cursor.moveToNext()){
                    number2 = cursor.getInt(indexNumber2);
                    number1 = cursor.getInt(indexNumber1);
                }
            }finally {
                if(cursor != null){
                    cursor.close();
                }
            }
        }else if(calculationKind == 3){
            Cursor cursor = null;

            try{
                cursor = database.query(MySQLiteOpenHelper.FORWARD_MULTIPLICATION_TABLE, new String[]{"id", "number1", "number2", "correct_times", "total_times"}, "id = ?", new String[]{String.valueOf(id)}, null, null, null);

                int indexNumber1 = cursor.getColumnIndex("number1");
                int indexNumber2 = cursor.getColumnIndex("number2");
                indexCorrect_times = cursor.getColumnIndex("correct_times");
                indexTotal_time = cursor.getColumnIndex("total_time");

                while(cursor.moveToNext()){
                    number2 = cursor.getInt(indexNumber2);
                    number1 = cursor.getInt(indexNumber1);
                }
            }finally {
                if(cursor != null){
                    cursor.close();
                }
            }
        }else if(calculationKind == 4){
            Cursor cursor = null;

            try{
                cursor = database.query(MySQLiteOpenHelper.FORWARD_DIVISION_TABLE, new String[]{"id", "number1", "number2", "correct_times", "total_times"}, "id = ?", new String[]{String.valueOf(id)}, null, null, null);

                int indexNumber1 = cursor.getColumnIndex("number1");
                int indexNumber2 = cursor.getColumnIndex("number2");
                indexCorrect_times = cursor.getColumnIndex("correct_times");
                indexTotal_time = cursor.getColumnIndex("total_time");

                while(cursor.moveToNext()){
                    number2 = cursor.getInt(indexNumber2);
                    number1 = cursor.getInt(indexNumber1);
                }
            }finally {
                if(cursor != null){
                    cursor.close();
                }
            }
        }
    }

    public void finish() {
        endedTime = System.currentTimeMillis();
        timeChronometer.stop();
        totalTime = endedTime - startedTime;
        Intent intent = new Intent();
        intent.putExtra("correct", correctTimes);
        intent.putExtra("calculation_kind", calculationKind);
        intent.putExtra("number1Records", number1Records);
        intent.putExtra("number2Records", number2Records);
        intent.putExtra("correctAnswerRecords", correctAnswerRecords);
        intent.putExtra("answerRecords", answerRecords);

        if(timeKind == 0){

            intent.putExtra("question_times", questionTimes);
            intent.putExtra("time", totalTime);
            intent.putExtra("timeKind", 0);
        }else if(timeKind == 1){

            intent.putExtra("question_times", times);
            intent.putExtra("time", questionTime * 1000);
            intent.putExtra("timeKind", 1);
        }

        intent.setAction(Intent.ACTION_MAIN);
        intent.setClass(CalculationActivity.this, FinishActivity.class);
        startActivity(intent);
    }

    public void insert_forward(int calculation_kind, int number1, int number2){

        ContentValues values = new ContentValues();
        values.put("calculation_kind", calculation_kind);
        values.put("number1", number1);
        values.put("number2", number2);

        database.insert(MySQLiteOpenHelper.FORWARD_MULTIPLICATION_TABLE, null, values);
    }

    public void setCalculationKind(){

        calculationKind = (int)(Math.random() * 4);

        if(calculationKind == 0){
            if(!addition){
                setCalculationKind();
            }
        }else if(calculationKind == 1){

            if(!subtraction){
                setCalculationKind();
            }
        }else if(calculationKind == 2){

            if(!multiplication){
                setCalculationKind();
            }
        }else if(calculationKind == 3){

            if(!division){
                setCalculationKind();
            }
        }
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
        endedTime = System.currentTimeMillis();
        stopRealTime = timeChronometer.getBase() - SystemClock.elapsedRealtime();
        timeChronometer.stop();
        totalTime = endedTime - startedTime + totalTime;
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
                    intent.putExtra(FinishActivity.KEY_CALCULATION_KIND, calculationKind);
                    startActivity(intent);
                } else if (id == R.id.imageView4) {
                    // resume
                    dialog.hide();
                    timeChronometer.setBase(android.os.SystemClock.elapsedRealtime() + stopRealTime);
                    timeChronometer.start();
                    startedTime = System.currentTimeMillis();
                    endedTime = 0;
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
        adb.setCancelable(false);
        dialog = adb.show();
    }

    public void next(View view) {

        times = times + 1;

        number1Records.add(number1);
        number2Records.add(number2);
        correctAnswerRecords.add(correctAnswer);
        answerRecords.add(userAnswer);

        if (userAnswer == correctAnswer) {

            correctTimes = correctTimes + 1;

            if(timeKind == 0){

                if (times == questionTimes) {
                    finish();
                }
            }

            if(calculationKind == 0 && review){

                indexTotal_time += 1;
                indexCorrect_times += 1;
                database.execSQL("update " + MySQLiteOpenHelper.FORWARD_ADDITION_TABLE + " set total_times = '" + indexTotal_time + "' where id = " + reviewId);
                database.execSQL("update " + MySQLiteOpenHelper.FORWARD_ADDITION_TABLE + " set correct_times = '" + indexCorrect_times + "' where id = " + reviewId);
            }else if(calculationKind == 2 && review){

                indexTotal_time += 1;
                indexCorrect_times += 1;
                database.execSQL("update " + MySQLiteOpenHelper.FORWARD_SUBTRACTION_TABLE + " set total_times = '" + indexTotal_time + "' where id = " + reviewId);
                database.execSQL("update " + MySQLiteOpenHelper.FORWARD_SUBTRACTION_TABLE + " set correct_times = '" + indexCorrect_times + "' where id = " + reviewId);
            }else if(calculationKind == 3 && review){

                indexTotal_time += 1;
                indexCorrect_times += 1;
                database.execSQL("update " + MySQLiteOpenHelper.FORWARD_MULTIPLICATION_TABLE + " set total_times = '" + indexTotal_time + "' where id = " + reviewId);
                database.execSQL("update " + MySQLiteOpenHelper.FORWARD_MULTIPLICATION_TABLE + " set correct_times = '" + indexCorrect_times + "' where id = " + reviewId);
            }else if(calculationKind == 4 && review){

                indexTotal_time += 1;
                indexCorrect_times += 1;
                database.execSQL("update " + MySQLiteOpenHelper.FORWARD_DIVISION_TABLE + " set total_times = '" + indexTotal_time + "' where id = " + reviewId);
                database.execSQL("update " + MySQLiteOpenHelper.FORWARD_DIVISION_TABLE + " set correct_times = '" + indexCorrect_times + "' where id = " + reviewId);
            }

            judgeImage.setImageResource(R.drawable.correct);
            judgeImage.setVisibility(View.VISIBLE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    judgeImage.setVisibility(View.INVISIBLE);

                    if(!imageSkipped){
                        newQuestion();
                    }else{
                        imageSkipped = false;
                    }
                }
            }, 1000);
        } else {

            if(timeKind == 0){
                if (times == questionTimes) {
                    finish();
                }
            }

            if(calculationKind == 2){
                insert_forward(calculationKind, number1, number2);

                Log.i("update", "updated");

                prefs.edit()
                        .putInt(TOTAL_FORWARD_MULTIPLICATION, totalForwardMultiplication + 1)
                        .apply();

                if(review){
                    indexTotal_time += 1;
                    database.execSQL("update " + MySQLiteOpenHelper.FORWARD_MULTIPLICATION_TABLE + " set total_times = '" + indexTotal_time + "' where id = " + reviewId);
                    database.execSQL("update " + MySQLiteOpenHelper.FORWARD_MULTIPLICATION_TABLE + " set correct_times = '" + indexCorrect_times + "' where id = " + reviewId);
                }
            }

            judgeImage.setImageResource(R.drawable.incorrect);
            judgeImage.setVisibility(View.VISIBLE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    judgeImage.setVisibility(View.INVISIBLE);

                    if(!imageSkipped){
                        newQuestion();
                    }else{
                        imageSkipped = false;
                    }
                }
            }, 1000);
        }

        correctTimesText.setText(correctTimes + "問");

        if(timeKind == 0){

            remainTimes = remainTimes - 1;
            remainText.setText(remainTimes + "問");
            progressBar.setProgress(times);
        }
    }

    public void skipImage(View view){
        imageSkipped = true;
        judgeImage.setVisibility(View.INVISIBLE);
        newQuestion();
    }
}