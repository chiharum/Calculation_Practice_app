package com.example.chiharumiyoshi.calculation_practice_app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Point;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;

public class StartActivity extends AppCompatActivity {

    int lastYear, lastMonth, lastDay, lastDate, year, month, day, date, timesInADay, continuousDays, width, height, previousVersion;
    boolean newYear, updateAlert, kindAddition, kindSubtraction, kindMultiplication, kindDivision, additionNumberOne, additionNumberTwo, additionNumberThree, subtractionNumberOne, subtractionNumberTwo, subtractionNumberThree, multiplicationNumberOne, multiplicationNumberTwo, multiplicationNumberThree, divisionNumberOne, divisionNumberTwo, divisionNumberThree, timeQuestionNumbers, timeQuestionTime, informationOk;
    TextView timesInADayText, continuousDayTimesText, titleText;
    Button calculateButton1, calculateButton2, calculateButton3, calculateButton4;
    AlertDialog dialog;

    public static final String KEY_TIMES_IN_A_DAY = "TimesInADay";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        titleText = (TextView)findViewById(R.id.titleText);
        timesInADayText = (TextView)findViewById(R.id.textView9);
        continuousDayTimesText = (TextView)findViewById(R.id.textView10);
        calculateButton1 = (Button)findViewById(R.id.calculate_addition);
        calculateButton2 = (Button)findViewById(R.id.calculate_subtraction);
        calculateButton3 = (Button)findViewById(R.id.calculate_maltiplication);
        calculateButton4 = (Button)findViewById(R.id.calculate_division);

        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        timesInADay = prefs.getInt(KEY_TIMES_IN_A_DAY, 0);
        continuousDays = prefs.getInt("continuousDays",0);
        lastYear = prefs.getInt("lastYear", 0);
        lastMonth = prefs.getInt("lastMonth", 0);
        lastDay = prefs.getInt("lastDay", 0);
        lastDate = prefs.getInt("lastDate", 0);
        newYear = prefs.getBoolean("newYear", false);
        updateAlert = prefs.getBoolean("update_alert_19", false);
        previousVersion = prefs.getInt("previousVersion", 19);

        prefs.edit().putInt("previousVersion", 19).apply();

        kindAddition = true;
        kindSubtraction = false;
        kindMultiplication = false;
        kindDivision = false;
        additionNumberOne = true;
        additionNumberTwo = false;
        additionNumberThree = false;
        subtractionNumberOne = false;
        subtractionNumberTwo = false;
        subtractionNumberThree = false;
        multiplicationNumberOne = false;
        multiplicationNumberTwo = false;
        multiplicationNumberThree = false;
        divisionNumberOne = false;
        divisionNumberTwo = false;
        divisionNumberThree = false;
        timeQuestionNumbers = true;
        timeQuestionTime = false;
        informationOk = true;

        Display display = getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        width = point.x;
        height = point.y;

        prefs.edit()
                .putInt("width", width)
                .apply();
        prefs.edit()
                .putInt("height", height)
                .apply();

        titleText.setTextSize((float) width / (float) 12);
        timesInADayText.setTextSize((float) width / (float) 30);
        continuousDayTimesText.setTextSize((float) width / (float) 30);
        titleText.setTypeface(Typeface.SERIF, Typeface.BOLD);

        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
        date = day + month * 100 + year * 10000;

        if(lastDate != date) {
            timesInADay = 0;
        }

        if(date != lastDate + 1){
            continuousDays = 0;
        }

        //timesInADayText.setText("今日(" + year + "年" + month + "月" + day + "日)、" + timesInADay + "回目のプレイです。");
        if(continuousDays > 0){
            continuousDayTimesText.setText(continuousDays + "日連続プレイです。");
        }else if(continuousDays == 0){
            if (lastDate != 0){
                //continuousDayTimesText.setText("前回のプレイは" + lastYear + "年" + lastMonth + "月" + lastDay + "日です。");
            }else if (lastDate == 0){
                continuousDayTimesText.setText("");
            }
        }

        prefs.edit()
                .putInt("lastYear",year)
                .apply();
        prefs.edit()
                .putInt("lastMonth",month)
                .apply();
        prefs.edit()
                .putInt("lastDay",day)
                .apply();
        prefs.edit()
                .putInt(KEY_TIMES_IN_A_DAY, timesInADay)
                .apply();
        prefs.edit()
                .putInt("continuousDays", continuousDays)
                .apply();
        prefs.edit()
                .putInt("lastDate", date)
                .apply();
        prefs.edit()
                .putBoolean("newYear", newYear)
                .apply();

        if(!updateAlert){
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("アップデート情報");
            alertDialog.setMessage("デザインの変更をしました。\n計算練習をするには、「計算練習」ボタンを押して、計算練習の条件を決定してください。");
            alertDialog.setPositiveButton("了解！", null);
            alertDialog.show();
            prefs.edit().putBoolean("update_alert_19", true).apply();
        }

        if(lastYear != 0 && lastYear != year){
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("あけましておめでとうございます");
            alertDialog.setMessage("今年もよろしくおねがいします");
            alertDialog.setPositiveButton("OK", null);
            alertDialog.show();
        }
    }

    public void settings(View v){
        Intent intent = new Intent();
        intent.setClass(StartActivity.this, SettingsActivity.class);
        startActivity(intent);
    }

    public void history(View v){
        Intent intent = new Intent();
        intent.setClass(StartActivity.this, RecordActivity.class);
        startActivity(intent);
    }

    public void help(View view){
        Intent intent = new Intent();
        intent.setClass(StartActivity.this, ExplanationActivity.class);
        startActivity(intent);
    }

    public void calculate(View view){

        kindAddition = true;
        kindSubtraction = false;
        kindMultiplication = false;
        kindDivision = false;
        additionNumberOne = true;
        additionNumberTwo = false;
        additionNumberThree = false;
        subtractionNumberOne = false;
        subtractionNumberTwo = false;
        subtractionNumberThree = false;
        multiplicationNumberOne = false;
        multiplicationNumberTwo = false;
        multiplicationNumberThree = false;
        divisionNumberOne = false;
        divisionNumberTwo = false;
        divisionNumberThree = false;
        timeQuestionNumbers = true;
        timeQuestionTime = false;
        informationOk = true;

        LayoutInflater inflater = (LayoutInflater)this.getSystemService(LAYOUT_INFLATER_SERVICE);
        final View adbLayout = inflater.inflate(R.layout.calculation_chooser_dialog_layout, null);

        pushed(adbLayout.findViewById(R.id.button9), kindAddition);
        pushed(adbLayout.findViewById(R.id.additionNumberOne), additionNumberOne);
        pushed(adbLayout.findViewById(R.id.button19), timeQuestionNumbers);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v){
                int id = v.getId();
                if(id == R.id.button9){

                    kindAddition = !kindAddition;
                    pushed(adbLayout.findViewById(R.id.button9), kindAddition);
                }else if(id == R.id.button10){

                    kindSubtraction = !kindSubtraction;
                    pushed(adbLayout.findViewById(R.id.button10), kindSubtraction);
                }else if(id == R.id.button11){

                    kindMultiplication = !kindMultiplication;
                    pushed(adbLayout.findViewById(R.id.button11), kindMultiplication);
                }else if(id == R.id.button12){

                    kindDivision = !kindDivision;
                    pushed(adbLayout.findViewById(R.id.button12), kindDivision);
                }else if(id == R.id.additionNumberOne){

                    additionNumberOne = !additionNumberOne;
                    pushed(adbLayout.findViewById(R.id.additionNumberOne), additionNumberOne);
                    if(additionNumberTwo){
                        additionNumberTwo = false;
                        pushed(adbLayout.findViewById(R.id.additionNumberTwo), additionNumberTwo);
                        if(additionNumberThree){
                            additionNumberThree = false;
                            pushed(adbLayout.findViewById(R.id.additionNumberThree), additionNumberThree);
                        }
                    }
                }else if(id == R.id.additionNumberTwo){

                    additionNumberTwo = !additionNumberTwo;
                    pushed(adbLayout.findViewById(R.id.additionNumberTwo), additionNumberTwo);
                    if(additionNumberTwo){
                        additionNumberOne = true;
                        pushed(adbLayout.findViewById(R.id.additionNumberOne), additionNumberOne);
                    }else if(additionNumberThree){
                        additionNumberThree = false;
                        pushed(adbLayout.findViewById(R.id.additionNumberThree), additionNumberThree);
                    }
                }else if(id == R.id.additionNumberThree){

                    additionNumberThree = !additionNumberThree;
                    pushed(adbLayout.findViewById(R.id.additionNumberThree), additionNumberThree);
                    if(additionNumberThree){
                        additionNumberOne = true;
                        pushed(adbLayout.findViewById(R.id.additionNumberOne), additionNumberOne);
                        additionNumberTwo = true;
                        pushed(adbLayout.findViewById(R.id.additionNumberTwo), additionNumberTwo);
                    }
                }else if(id == R.id.subtractionNumberOne){

                    subtractionNumberOne = !subtractionNumberOne;
                    pushed(adbLayout.findViewById(R.id.subtractionNumberOne), subtractionNumberOne);
                    if(subtractionNumberTwo){
                        subtractionNumberTwo = false;
                        pushed(adbLayout.findViewById(R.id.subtractionNumberTwo), subtractionNumberTwo);
                        if(subtractionNumberThree){
                            subtractionNumberThree = false;
                            pushed(adbLayout.findViewById(R.id.subtractionNumberThree), subtractionNumberThree);
                        }
                    }
                }else if(id == R.id.subtractionNumberTwo){

                    subtractionNumberTwo = !subtractionNumberTwo;
                    pushed(adbLayout.findViewById(R.id.subtractionNumberTwo), subtractionNumberTwo);
                    if (subtractionNumberTwo) {
                        subtractionNumberOne = true;
                        pushed(adbLayout.findViewById(R.id.subtractionNumberOne), subtractionNumberOne);
                    }else if(subtractionNumberThree){
                        subtractionNumberThree = false;
                        pushed(adbLayout.findViewById(R.id.subtractionNumberThree), subtractionNumberThree);
                    }
                }else if(id == R.id.subtractionNumberThree){

                    subtractionNumberThree = !subtractionNumberThree;
                    pushed(adbLayout.findViewById(R.id.subtractionNumberThree), subtractionNumberThree);
                    if(subtractionNumberThree){
                        subtractionNumberTwo = true;
                        pushed(adbLayout.findViewById(R.id.subtractionNumberTwo), subtractionNumberTwo);
                        subtractionNumberOne = true;
                        pushed(adbLayout.findViewById(R.id.subtractionNumberOne), subtractionNumberOne);
                    }
                }else if(id == R.id.multiplicationNumberOne){

                    multiplicationNumberOne = !multiplicationNumberOne;
                    pushed(adbLayout.findViewById(R.id.multiplicationNumberOne), multiplicationNumberOne);
                    if(multiplicationNumberTwo){
                        multiplicationNumberTwo = false;
                        pushed(adbLayout.findViewById(R.id.multiplicationNumberTwo), multiplicationNumberTwo);
                        if(multiplicationNumberThree){
                            multiplicationNumberThree = false;
                            pushed(adbLayout.findViewById(R.id.multiplicationNumberThree), multiplicationNumberThree);
                        }
                    }
                }else if(id == R.id.multiplicationNumberTwo){

                    multiplicationNumberTwo = !multiplicationNumberTwo;
                    pushed(adbLayout.findViewById(R.id.multiplicationNumberTwo), multiplicationNumberTwo);
                    if(multiplicationNumberTwo){
                        multiplicationNumberOne = true;
                        pushed(adbLayout.findViewById(R.id.multiplicationNumberOne), multiplicationNumberOne);
                    }else if(multiplicationNumberThree){
                        multiplicationNumberThree = false;
                        pushed(adbLayout.findViewById(R.id.multiplicationNumberThree), multiplicationNumberThree);
                    }
                }else if(id == R.id.multiplicationNumberThree){

                    multiplicationNumberThree = !multiplicationNumberThree;
                    pushed(adbLayout.findViewById(R.id.multiplicationNumberThree), multiplicationNumberThree);
                    if(multiplicationNumberThree){
                        multiplicationNumberTwo = true;
                        pushed(adbLayout.findViewById(R.id.multiplicationNumberTwo), multiplicationNumberTwo);
                        multiplicationNumberOne = true;
                        pushed(adbLayout.findViewById(R.id.multiplicationNumberOne), multiplicationNumberOne);
                    }
                }else if(id == R.id.divisionNumberOne){

                    divisionNumberOne = !divisionNumberOne;
                    pushed(adbLayout.findViewById(R.id.divisionNumberOne), divisionNumberOne);
                    if(divisionNumberTwo){
                        divisionNumberTwo = false;
                        pushed(adbLayout.findViewById(R.id.divisionNumberTwo), divisionNumberTwo);
                        if(divisionNumberThree){
                            divisionNumberThree = false;
                            pushed(adbLayout.findViewById(R.id.divisionNumberThree), divisionNumberThree);
                        }
                    }
                }else if(id == R.id.divisionNumberTwo){

                    divisionNumberTwo = !divisionNumberTwo;
                    pushed(adbLayout.findViewById(R.id.divisionNumberTwo), divisionNumberTwo);
                    if(divisionNumberTwo){
                        divisionNumberOne = true;
                        pushed(adbLayout.findViewById(R.id.divisionNumberOne), divisionNumberOne);
                    }else if(divisionNumberThree){
                        divisionNumberThree = false;
                        pushed(adbLayout.findViewById(R.id.divisionNumberThree), divisionNumberThree);
                    }
                }else if(id == R.id.divisionNumberThree){

                    divisionNumberThree = !divisionNumberThree;
                    pushed(adbLayout.findViewById(R.id.divisionNumberThree), divisionNumberThree);
                    if(divisionNumberThree){
                        divisionNumberTwo = true;
                        pushed(adbLayout.findViewById(R.id.divisionNumberTwo), divisionNumberTwo);
                        divisionNumberOne = true;
                        pushed(adbLayout.findViewById(R.id.divisionNumberOne), divisionNumberOne);
                    }
                }else if(id == R.id.button19){

                    timeQuestionNumbers = true;
                    pushed(adbLayout.findViewById(R.id.button19), timeQuestionNumbers);
                    timeQuestionTime = false;
                    pushed(adbLayout.findViewById(R.id.button20), timeQuestionTime);
                }else if(id == R.id.button20){

                    timeQuestionTime = true;
                    pushed(adbLayout.findViewById(R.id.button20), timeQuestionTime);
                    timeQuestionNumbers = false;
                    pushed(adbLayout.findViewById(R.id.button19), timeQuestionNumbers);
                }
            }
        };

        adbLayout.findViewById(R.id.button9).setOnClickListener(listener);
        adbLayout.findViewById(R.id.button10).setOnClickListener(listener);
        adbLayout.findViewById(R.id.button11).setOnClickListener(listener);
        adbLayout.findViewById(R.id.button12).setOnClickListener(listener);
        adbLayout.findViewById(R.id.additionNumberOne).setOnClickListener(listener);
        adbLayout.findViewById(R.id.additionNumberTwo).setOnClickListener(listener);
        adbLayout.findViewById(R.id.additionNumberThree).setOnClickListener(listener);
        adbLayout.findViewById(R.id.subtractionNumberOne).setOnClickListener(listener);
        adbLayout.findViewById(R.id.subtractionNumberTwo).setOnClickListener(listener);
        adbLayout.findViewById(R.id.subtractionNumberThree).setOnClickListener(listener);
        adbLayout.findViewById(R.id.multiplicationNumberOne).setOnClickListener(listener);
        adbLayout.findViewById(R.id.multiplicationNumberTwo).setOnClickListener(listener);
        adbLayout.findViewById(R.id.multiplicationNumberThree).setOnClickListener(listener);
        adbLayout.findViewById(R.id.divisionNumberOne).setOnClickListener(listener);
        adbLayout.findViewById(R.id.divisionNumberTwo).setOnClickListener(listener);
        adbLayout.findViewById(R.id.divisionNumberThree).setOnClickListener(listener);
        adbLayout.findViewById(R.id.button19).setOnClickListener(listener);
        adbLayout.findViewById(R.id.button20).setOnClickListener(listener);

        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setView(adbLayout);
        adb.setPositiveButton(getString(R.string.go), new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int which){

                if(!kindAddition && !kindSubtraction && !kindMultiplication && !kindDivision){

                    errorDialog("計算方法を決定してください。");
                    informationOk = false;

                }

                if(kindAddition){

                    if(!additionNumberOne && !additionNumberTwo && !additionNumberThree){
                        errorDialog("ケタ数を決定してください。");
                        informationOk = false;
                    }else{
                        informationOk = true;
                    }
                }

                if(kindSubtraction){

                    if(!subtractionNumberOne && !subtractionNumberTwo && !subtractionNumberThree){
                        errorDialog("ケタ数を決定してください。");
                        informationOk = false;
                    }else{
                        informationOk = true;
                    }
                }

                if(kindMultiplication){

                    if(!multiplicationNumberOne && !multiplicationNumberTwo && !multiplicationNumberThree){
                        errorDialog("ケタ数を決定してください。");
                        informationOk = false;
                    }else{
                        informationOk = true;
                    }
                }

                if(kindDivision){

                    if(!divisionNumberOne && !divisionNumberTwo && !divisionNumberThree){
                        errorDialog("ケタ数を決定してください。");
                        informationOk = false;
                    }else{
                        informationOk = true;
                    }
                }

                if(informationOk){
                    go();
                }
            }
        });
        dialog = adb.show();
    }

    public void errorDialog(String text){

        AlertDialog.Builder errorDialog = new AlertDialog.Builder(this);
        errorDialog.setTitle("設定情報不足です！");
        errorDialog.setMessage(text);
        errorDialog.setPositiveButton(getText(R.string.ok), null);
        dialog = errorDialog.show();
    }

    public void pushed(View view, boolean state){
        if(state){
            view.setBackgroundResource(R.drawable.small_button_selected);
        }else{
            view.setBackgroundResource(R.drawable.small_button);
        }
    }

    public void go(){
        Intent intent = new Intent();
        intent.setClass(StartActivity.this, CalculationActivity.class);
        if(timeQuestionNumbers){
            intent.putExtra("timeKind", 0);
        }else{
            intent.putExtra("timeKind", 1);
        }
        intent.putExtra("addition", kindAddition);
        intent.putExtra("subtraction", kindSubtraction);
        intent.putExtra("multiplication", kindMultiplication);
        intent.putExtra("division", kindDivision);
        intent.putExtra("additionNumberOne", additionNumberOne);
        intent.putExtra("additionNumberTwo", additionNumberTwo);
        intent.putExtra("additionNumberThree", additionNumberThree);
        intent.putExtra("subtractionNumberOne", subtractionNumberOne);
        intent.putExtra("subtractionNumberTwo", subtractionNumberTwo);
        intent.putExtra("subtractionNumberThree", subtractionNumberThree);
        intent.putExtra("multiplicationNumberOne", multiplicationNumberOne);
        intent.putExtra("multiplicationNumberTwo", multiplicationNumberTwo);
        intent.putExtra("multiplicationNumberThree", multiplicationNumberThree);
        intent.putExtra("divisionNumberOne", divisionNumberOne);
        intent.putExtra("divisionNumberTwo", divisionNumberTwo);
        intent.putExtra("divisionNumberThree", divisionNumberThree);
        startActivity(intent);
    }

    public void play_classic(View view){

        kindAddition = true;
        kindSubtraction = false;
        kindMultiplication = false;
        kindDivision = false;
        additionNumberOne = true;
        additionNumberTwo = false;
        additionNumberThree = false;
        subtractionNumberOne = false;
        subtractionNumberTwo = false;
        subtractionNumberThree = false;
        multiplicationNumberOne = false;
        multiplicationNumberTwo = false;
        multiplicationNumberThree = false;
        divisionNumberOne = false;
        divisionNumberTwo = false;
        divisionNumberThree = false;
        timeQuestionNumbers = true;
        timeQuestionTime = false;
        informationOk = true;

        LayoutInflater inflater = (LayoutInflater)this.getSystemService(LAYOUT_INFLATER_SERVICE);
        final View adbLayout = inflater.inflate(R.layout.calculate_dialog, null);

        View.OnClickListener listener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                int id = v.getId();
                if (id == R.id.calculate_addition) {
                    kindAddition = true;
                    additionNumberOne = true;
                    additionNumberTwo = true;
                } else if (id == R.id.calculate_subtraction) {
                    kindSubtraction = true;
                    subtractionNumberOne = true;
                    subtractionNumberTwo = true;
                } else if (id == R.id.calculate_maltiplication) {
                    kindMultiplication = true;
                    multiplicationNumberOne = true;
                    multiplicationNumberTwo = true;
                } else if (id == R.id.calculate_division) {
                    kindDivision = true;
                    divisionNumberOne = true;
                    divisionNumberTwo = true;
                }

                timeQuestionNumbers = true;
                go();
            }
        };

        adbLayout.findViewById(R.id.calculate_addition).setOnClickListener(listener);
        adbLayout.findViewById(R.id.calculate_subtraction).setOnClickListener(listener);
        adbLayout.findViewById(R.id.calculate_maltiplication).setOnClickListener(listener);
        adbLayout.findViewById(R.id.calculate_division).setOnClickListener(listener);

        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setView(adbLayout);
        dialog = adb.show();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK){

            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setMessage("アプリを終了します。");
            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    quit();
                }
            });
            alertDialog.setNegativeButton("キャンセル", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            alertDialog.show();

            return super.onKeyDown(keyCode, event);
        }else{
            return false;
        }
    }

    public void quit(){
        this.moveTaskToBack(true);
    }
}