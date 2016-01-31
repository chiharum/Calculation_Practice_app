package com.example.chiharumiyoshi.calculation_practice_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {

    public static final String KEY_QUESTION_TIMES_SETTINGS = "questionTimesSettings";
    public static final String KEY_QUESTION_TIME_SETTINGS = "questionTimeSettings";
    public static final String KEY_MINUS_SETTINGS = "minus";
    public static final String KEY_ERASER_COLOR_SETTINGS = "eraser_color";

    boolean minus;
    TextView questionNumbersText;
    TextView questionTimeText;
    ImageView eraserImageView;
    long questionTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        final CheckBox minus_c = new CheckBox(this);
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        minus = prefs.getBoolean(KEY_MINUS_SETTINGS, false);
        if(minus){
            minus_c.setChecked(true);
        }else{
            minus_c.setChecked(false);
        }
        minus_c.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                minus = minus_c.isChecked();
                prefs.edit()
                        .putBoolean(KEY_MINUS_SETTINGS, minus)
                        .apply();
            }
        });

        questionNumbersText = (TextView)findViewById(R.id.question_numbers_t);

        int number = prefs.getInt(KEY_QUESTION_TIMES_SETTINGS, 10);
        questionNumbersText.setText("問題数：" + number + "問");
        eraserImageView = (ImageView)findViewById(R.id.imageView10);

        questionTime = prefs.getLong(KEY_QUESTION_TIME_SETTINGS, 30);
        questionTimeText = (TextView)findViewById(R.id.textView19);
        questionTimeText.setText("時間：" + questionTime + "秒");

        int default_eraser_color = prefs.getInt(KEY_ERASER_COLOR_SETTINGS, 0) + 1;
        int image_res = getResources().getIdentifier("delete_button_" + default_eraser_color, "drawable", getPackageName());
        eraserImageView.setImageResource(image_res);
    }

    //問題数
    public void question_numbers(View view){
        AlertDialog.Builder adb1 = new AlertDialog.Builder(this);
        adb1.setTitle("問題数");
        final String[] question_numbers_t = getResources().getStringArray(R.array.question_numbers);
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        int number = prefs.getInt(KEY_QUESTION_TIMES_SETTINGS, 10);
        int selected_index = 0;

        if (number == 10){
            selected_index = 0;
        }else if(number == 15){
            selected_index = 1;
        }else if(number == 20){
            selected_index = 2;
        }else if(number == 100){
            selected_index = 3;
        }

        adb1.setSingleChoiceItems(question_numbers_t, selected_index, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int question_numbers = 10;
                if (which == 0) {
                    question_numbers = 10;
                } else if (which == 1) {
                    question_numbers = 15;
                } else if (which == 2) {
                    question_numbers = 20;
                } else if (which == 3) {
                    question_numbers = 100;
                }
                prefs.edit()
                        .putInt(KEY_QUESTION_TIMES_SETTINGS, question_numbers)
                        .apply();
                questionNumbersText.setText("問題数：" + question_numbers + "問");
                dialog.dismiss();
            }
        });
        adb1.show();
    }

    //問題の時間
    public void question_time(View view){
        AlertDialog.Builder adb1 = new AlertDialog.Builder(this);
        adb1.setTitle("時間");
        final String[] questionNumbersText = getResources().getStringArray(R.array.question_time);
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        int selectedIndex = 0;

        if (questionTime == 30){
            selectedIndex = 0;
        }else if(questionTime == 60){
            selectedIndex = 1;
        }else if(questionTime == 90){
            selectedIndex = 2;
        }else if(questionTime == 120){
            selectedIndex = 3;
        }else if(questionTime == 300){
            selectedIndex = 4;
        }

        adb1.setSingleChoiceItems(questionNumbersText, selectedIndex, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                questionTime = 10;
                if (which == 0) {
                    questionTime = 30;
                } else if (which == 1) {
                    questionTime = 60;
                } else if (which == 2) {
                    questionTime = 90;
                } else if (which == 3) {
                    questionTime = 120;
                } else if (which == 4) {
                    questionTime = 300;
                }
                prefs.edit()
                        .putLong(KEY_QUESTION_TIME_SETTINGS, questionTime)
                        .apply();
                questionTimeText.setText("時間：" + questionTime + "秒");
                dialog.dismiss();
            }
        });
        adb1.show();
    }

    //消しゴムの色
    public void eraser_color(View v){
        AlertDialog.Builder adb2 = new AlertDialog.Builder(this);
        adb2.setTitle("消しゴムの色");
        String[] eraser_color = getResources().getStringArray(R.array.eraser_color);
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        int default_eraser_color = prefs.getInt(KEY_ERASER_COLOR_SETTINGS, 1) - 1;
        adb2.setSingleChoiceItems(eraser_color, default_eraser_color, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                which = which + 1;
                prefs.edit()
                        .putInt(KEY_ERASER_COLOR_SETTINGS,which)
                        .apply();

                int image_res = getResources().getIdentifier("delete_button_" + which, "drawable", getPackageName());
                eraserImageView.setImageResource(image_res);
                dialog.dismiss();
            }
        });
        adb2.show();
    }
}
