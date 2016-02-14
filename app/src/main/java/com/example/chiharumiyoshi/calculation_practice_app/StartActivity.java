package com.example.chiharumiyoshi.calculation_practice_app;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
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
import android.widget.TextView;

import java.util.Calendar;

public class StartActivity extends AppCompatActivity {

    int lastYear, lastMonth, lastDay, lastDate, year, month, day, date, timesInADay, continuousDays;
    TextView timesInADayText, continuousDayTimesText, titleText;
    AlertDialog dialog;
    boolean newYear, updateAlert;

    public static final String KEY_TIMES_IN_A_DAY = "TimesInADay";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        /*
        To Do

        -データベースの削除。
        -日付の記録（データベース）。
        -画面サイズによって文字の大きさを変える。
        -戻るボタンの処理。
        説明文
        アクションバー（上と下）。
        百ます計算。
         */

        titleText = (TextView)findViewById(R.id.titleText);
        timesInADayText = (TextView)findViewById(R.id.textView9);
        continuousDayTimesText = (TextView)findViewById(R.id.textView10);

        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        timesInADay = prefs.getInt(KEY_TIMES_IN_A_DAY, 0);
        continuousDays = prefs.getInt("continuousDays",0);
        lastYear = prefs.getInt("lastYear", 0);
        lastMonth = prefs.getInt("lastMonth", 0);
        lastDay = prefs.getInt("lastDay", 0);
        lastDate = prefs.getInt("lastDate", 0);
        newYear = prefs.getBoolean("newYear", false);
        updateAlert = prefs.getBoolean("update_alert", false);

        Display display = getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        titleText.setTypeface(Typeface.SERIF, Typeface.BOLD);
        titleText.setTextSize((float) width / (float) 12);
        timesInADayText.setTextSize((float) width / (float) 30);
        continuousDayTimesText.setTextSize((float)width / (float)30);

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
            alertDialog.setMessage("間違えた問題を重点的に出す機能を追加しました。");
            alertDialog.setPositiveButton("了解！", null);
            alertDialog.show();
            prefs.edit()
                    .putBoolean("update_alert", true)
                    .apply();
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

    public void calculate(View view){

        LayoutInflater inflater = (LayoutInflater)this.getSystemService(LAYOUT_INFLATER_SERVICE);
        final View adbLayout = inflater.inflate(R.layout.calculate_dialog, null);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v){
                int id = v.getId();
                if(id == R.id.calculate_addition){
                    Intent intent = new Intent();
                    intent.setClass(StartActivity.this, CalculationActivity.class);
                    intent.putExtra(FinishActivity.KEY_CALCULATION_KIND, 0);
                    intent.putExtra("timeKind", 0);
                    startActivity(intent);
                }else if(id == R.id.calculate_subtraction){
                    Intent intent = new Intent();
                    intent.setClass(StartActivity.this, CalculationActivity.class);
                    intent.putExtra(FinishActivity.KEY_CALCULATION_KIND, 1);
                    intent.putExtra("timeKind", 0);
                    startActivity(intent);
                }else if(id == R.id.calculate_maltiplication){
                    Intent intent = new Intent();
                    intent.setClass(StartActivity.this, CalculationActivity.class);
                    intent.putExtra(FinishActivity.KEY_CALCULATION_KIND, 2);
                    intent.putExtra("timeKind", 0);
                    startActivity(intent);
                }else if(id == R.id.calculate_division){
                    Intent intent = new Intent();
                    intent.setClass(StartActivity.this, CalculationActivity.class);
                    intent.putExtra(FinishActivity.KEY_CALCULATION_KIND, 3);
                    intent.putExtra("timeKind", 0);
                    startActivity(intent);
                }
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

    public void calculate_much(View view){

        LayoutInflater inflater = (LayoutInflater)this.getSystemService(LAYOUT_INFLATER_SERVICE);
        final View adbLayout = inflater.inflate(R.layout.calculate_dialog, null);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v){
                int id = v.getId();
                if(id == R.id.calculate_addition){
                    Intent intent = new Intent();
                    intent.setClass(StartActivity.this, CalculationActivity.class);
                    intent.putExtra(FinishActivity.KEY_CALCULATION_KIND, 0);
                    intent.putExtra("timeKind", 1);
                    startActivity(intent);
                }else if(id == R.id.calculate_subtraction){
                    Intent intent = new Intent();
                    intent.setClass(StartActivity.this, CalculationActivity.class);
                    intent.putExtra(FinishActivity.KEY_CALCULATION_KIND, 1);
                    intent.putExtra("timeKind", 1);
                    startActivity(intent);
                }else if(id == R.id.calculate_maltiplication){
                    Intent intent = new Intent();
                    intent.setClass(StartActivity.this, CalculationActivity.class);
                    intent.putExtra(FinishActivity.KEY_CALCULATION_KIND, 2);
                    intent.putExtra("timeKind", 1);
                    startActivity(intent);
                }else if(id == R.id.calculate_division){
                    Intent intent = new Intent();
                    intent.setClass(StartActivity.this, CalculationActivity.class);
                    intent.putExtra(FinishActivity.KEY_CALCULATION_KIND, 3);
                    intent.putExtra("timeKind", 1);
                    startActivity(intent);
                }
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