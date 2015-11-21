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

    public static final String KEY_QUESTION_NUMBER = "question_number";
    public static final String KEY_QUESTION_TIME = "question_time";
    public static final String KEY_MINUS = "minus";
    public static final String KEY_ERASER_COLOR = "eraser_color";

    boolean minus;
    TextView questionNumbersText, questionTimeText;
    ImageView eraserImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        final CheckBox minus_c = new CheckBox(this);
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        minus = prefs.getBoolean(KEY_MINUS, false);
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
                        .putBoolean(KEY_MINUS, minus)
                        .apply();
            }
        });

        questionNumbersText = (TextView)findViewById(R.id.question_numbers_t);

        // Load from prefs
        int number = prefs.getInt(KEY_QUESTION_NUMBER, 10);
        questionNumbersText.setText("問題数：" + number + "問");
        eraserImageView = (ImageView)findViewById(R.id.imageView10);

        int defaultTime = prefs.getInt(KEY_QUESTION_TIME, 0);
        questionTimeText = (TextView)findViewById(R.id.textView19);
        questionTimeText.setText(defaultTime);

        int default_eraser_color = prefs.getInt(KEY_ERASER_COLOR, 0) + 1;
        int image_res = getResources().getIdentifier("delete_button_" + default_eraser_color, "drawable", getPackageName());
        eraserImageView.setImageResource(image_res);
    }

    public void question_numbers(View view){
        AlertDialog.Builder adb1 = new AlertDialog.Builder(this);
        adb1.setTitle("問題数");
        final String[] question_numbers_t = getResources().getStringArray(R.array.question_numbers);
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        int number = prefs.getInt(KEY_QUESTION_NUMBER, 10);
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
                        .putInt(KEY_QUESTION_NUMBER, question_numbers)
                        .apply();
                questionNumbersText.setText("問題数：" + question_numbers + "問");
                dialog.dismiss();
            }
        });
        adb1.show();
    }

    public void question_time(View view){
        AlertDialog.Builder adb1 = new AlertDialog.Builder(this);
        adb1.setTitle("時間");
        final String[] question_numbers_t = getResources().getStringArray(R.array.question_time);
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        int defaultTime = prefs.getInt(KEY_QUESTION_TIME, 10);
        int selected_index = 0;

        if (defaultTime == 30){
            selected_index = 0;
        }else if(defaultTime == 60){
            selected_index = 1;
        }else if(defaultTime == 90){
            selected_index = 2;
        }else if(defaultTime == 120){
            selected_index = 3;
        }else if(defaultTime == 300){
            selected_index = 4;
        }

        adb1.setSingleChoiceItems(question_numbers_t, selected_index, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int question_time = 10;
                if (which == 0) {
                    question_time = 30;
                } else if (which == 1) {
                    question_time = 60;
                } else if (which == 2) {
                    question_time = 90;
                } else if (which == 3) {
                    question_time = 120;
                } else if (which == 4) {
                    question_time = 300;
                }
                prefs.edit()
                        .putInt(KEY_QUESTION_TIME, question_time)
                        .apply();
                questionNumbersText.setText("時間：" + question_time + "問");
                dialog.dismiss();
            }
        });
        adb1.show();
    }

    public void eraser_color(View v){
        AlertDialog.Builder adb2 = new AlertDialog.Builder(this);
        adb2.setTitle("消しゴムの色");
        String[] eraser_color = getResources().getStringArray(R.array.eraser_color);
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        int default_eraser_color = prefs.getInt(KEY_ERASER_COLOR, 1) - 1;
        adb2.setSingleChoiceItems(eraser_color, default_eraser_color, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                which = which + 1;
                prefs.edit()
                        .putInt(KEY_ERASER_COLOR,which)
                        .apply();

                int image_res = getResources().getIdentifier("delete_button_" + which, "drawable", getPackageName());
                eraserImageView.setImageResource(image_res);
                dialog.dismiss();
            }
        });
        adb2.show();
    }
}
