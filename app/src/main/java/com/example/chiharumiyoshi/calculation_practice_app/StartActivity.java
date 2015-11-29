package com.example.chiharumiyoshi.calculation_practice_app;

import android.app.AlertDialog;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.Calendar;

public class StartActivity extends AppCompatActivity {

    int lastYear, lastMonth, lastDay, lastDate,year,month,day,date, timesInADay, continuousDays, calculationKind;
    TextView timesInADayText, continuousDayTimesText, titleText;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        titleText = (TextView)findViewById(R.id.textView4);


        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        timesInADay = prefs.getInt("timesInADay",0);
        continuousDays = prefs.getInt("continuousDays",0);
        lastYear = prefs.getInt("lastYear", 0);
        lastMonth = prefs.getInt("lastMonth", 0);
        lastDay = prefs.getInt("lastDay", 0);
        lastDate = prefs.getInt("lastDate",0);


        titleText.setTypeface(Typeface.SERIF, Typeface.BOLD);
        timesInADayText = (TextView)findViewById(R.id.textView9);
        continuousDayTimesText = (TextView)findViewById(R.id.textView10);

        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
        date = day + month * 100 + year * 10000;
        if(lastDate == date){
            timesInADay = timesInADay + 1;
        }else{
            timesInADay = 1;
        }
        if(date != lastDate + 1){
            continuousDays = 0;
        }
        timesInADayText.setText("今日(" + year + "年" + month + "月" + day + "日)、" + timesInADay + "回目のプレイです。");
        if(continuousDays > 0){
            continuousDayTimesText.setText(continuousDays + "日連続プレイです。");
        }else if(continuousDays == 0){
            if (lastDate != 0){
                continuousDayTimesText.setText("前回のプレイは" + lastYear + "年" + lastMonth + "月" + lastDay + "日です。");
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
                .putInt("timesInADay", timesInADay)
                .apply();
        prefs.edit()
                .putInt("continuousDays", continuousDays)
                .apply();
        prefs.edit()
                .putInt("lastDate",date)
                .apply();
    }

    public void settings(View v){
        Intent intent = new Intent();
        intent.setClass(StartActivity.this, SettingsActivity.class);
        startActivity(intent);
    }

    public void calculate(View view){

        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        calculationKind = prefs.getInt("calculationKind", 0);
        calculationKind = 1;
        prefs.edit()
                .putInt("calculationKind", calculationKind)
                .apply();

        LayoutInflater inflater = (LayoutInflater)this.getSystemService(LAYOUT_INFLATER_SERVICE);
        final View adbLayout = inflater.inflate(R.layout.calculate_dialog, null);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v){
                int id = v.getId();
                if(id == R.id.calculate_addition){
                    Intent intent = new Intent();
                    intent.setClass(StartActivity.this, AdditionActivity.class);
                    startActivity(intent);
                }else if(id == R.id.calculate_subtraction){
                    Intent intent = new Intent();
                    intent.setClass(StartActivity.this, SubtractionActivity.class);
                    startActivity(intent);
                }else if(id == R.id.calculate_maltiplication){
                    Intent intent = new Intent();
                    intent.setClass(StartActivity.this, MultiplicationActivity.class);
                    startActivity(intent);
                }else if(id == R.id.calculate_division){
                    Intent intent = new Intent();
                    intent.setClass(StartActivity.this, DivisionActivity.class);
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
                    intent.setClass(StartActivity.this, AdditionSecondActivity.class);
                    startActivity(intent);
                }else if(id == R.id.calculate_subtraction){
                    Intent intent = new Intent();
                    intent.setClass(StartActivity.this, SubtractionActivity.class);
                    startActivity(intent);
                }else if(id == R.id.calculate_maltiplication){
                    Intent intent = new Intent();
                    intent.setClass(StartActivity.this, MultiplicationActivity.class);
                    startActivity(intent);
                }else if(id == R.id.calculate_division){
                    Intent intent = new Intent();
                    intent.setClass(StartActivity.this, DivisionActivity.class);
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
}
