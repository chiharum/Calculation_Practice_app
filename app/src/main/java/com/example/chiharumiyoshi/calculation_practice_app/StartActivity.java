package com.example.chiharumiyoshi.calculation_practice_app;

import android.app.ActionBar;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;

import java.util.Calendar;

public class StartActivity extends AppCompatActivity {

    int last_year,last_month,last_day,last_date,year,month,day,date,times_in_day, continuousDay;
    TextView times_in_day_t,continuous_day_times_t, title_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        title_text = (TextView)findViewById(R.id.textView4);
        title_text.setTypeface(Typeface.SERIF,Typeface.BOLD);
        times_in_day_t = (TextView)findViewById(R.id.textView9);
        continuous_day_times_t = (TextView)findViewById(R.id.textView10);
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        times_in_day = prefs.getInt("times_day",0);
        continuousDay = prefs.getInt("continuousDay",0);
        last_year = prefs.getInt("last_year", 0);
        last_month = prefs.getInt("last_month", 0);
        last_day = prefs.getInt("last_day", 0);
        last_date = prefs.getInt("last_date",0);
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
        date = day + month * 100 + year * 10000;
        if(last_date == date){
            times_in_day = times_in_day + 1;
        }else{
            times_in_day = 1;
        }
        if(date != last_date + 1){
            continuousDay = 0;
        }
        times_in_day_t.setText("今日(" + year + "年" + month + "月" + day + "日)、" + times_in_day + "回目のプレイです。");
        if(continuousDay > 0){
            continuous_day_times_t.setText(continuousDay + "日連続プレイです。");
        }else if(continuousDay == 0){
            if (last_date != 0){
                continuous_day_times_t.setText("前回のプレイは" + last_year + "年" + last_month + "月" + last_day + "日です。");
            }else if (last_date == 0){
                continuous_day_times_t.setText("");
            }
        }
        prefs.edit()
                .putInt("last_year",year)
                .apply();
        prefs.edit()
                .putInt("last_month",month)
                .apply();
        prefs.edit()
                .putInt("last_day",day)
                .apply();
        prefs.edit()
                .putInt("times_day",times_in_day)
                .apply();
        prefs.edit()
                .putInt("continuousDay", continuousDay)
                .apply();
        prefs.edit()
                .putInt("last_date",date)
                .apply();
    }

    public void settings(View v){
        Intent intent = new Intent();
        intent.setClass(StartActivity.this, SettingsActivity.class);
        startActivity(intent);
    }

    public void addition(View v){
        Intent intent = new Intent(StartActivity.this,AdditionActivity.class);
        startActivity(intent);
    }

    public void subtraction(View v){
        Intent intent = new Intent(StartActivity.this,SubtractionActivity.class);
        startActivity(intent);
    }

    public void multiplication(View v){
        Intent intent = new Intent(StartActivity.this,MaltiplicationActivity.class);
        startActivity(intent);
    }

    public void division(View v){
        Intent intent = new Intent(StartActivity.this,DivisionActivity.class);
        startActivity(intent);
    }

    public void setting(View v){
        Intent intent = new Intent(StartActivity.this,SettingsActivity.class);
        startActivity(intent);
    }
}
