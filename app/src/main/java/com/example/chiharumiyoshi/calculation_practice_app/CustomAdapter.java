package com.example.chiharumiyoshi.calculation_practice_app;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.preference.PreferenceManager;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<Item> {

    LayoutInflater layoutInflater;

    final SharedPreferences prefs  = PreferenceManager.getDefaultSharedPreferences(getContext());
    int width = prefs.getInt("width", 0);
    int height = prefs.getInt("height", 0);

    public CustomAdapter(Context context, int resource, List<Item> items){

        super(context, resource, items);
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(int position, View convertView, ViewGroup parent){

        final ViewHolder viewHolder;

        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.result_list, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        Item item = getItem(position);

        if(item != null){
            if(item.isRight){
                viewHolder.resultIconImage.setImageResource(R.drawable.correct);
            }else{
                viewHolder.resultIconImage.setImageResource(R.drawable.incorrect);
            }
            viewHolder.questionNumberTextView.setText(item.questionNumber);
            viewHolder.questionTextView.setText(item.question);
            viewHolder.correctAnswerTextView.setText(item.correctAnswer);
            viewHolder.answerTextView.setText(item.answer);
            viewHolder.resultIconImage.setMaxWidth(width / 7);
            viewHolder.resultIconImage.setMaxHeight(height / 7);
            viewHolder.resultIconImage.setMinimumWidth(width / 7);
            viewHolder.resultIconImage.setMinimumHeight(height / 7);
        }

        return convertView;
    }

    private class ViewHolder{
        ImageView resultIconImage;
        TextView questionNumberTextView;
        TextView questionTextView;
        TextView correctAnswerTextView;
        TextView answerTextView;
        public ViewHolder(View view) {
            resultIconImage = (ImageView) view.findViewById(R.id.resultImage);
            questionNumberTextView = (TextView) view.findViewById(R.id.resultQuestionNumberText);
            questionTextView = (TextView) view.findViewById(R.id.resultQuestionText);
            correctAnswerTextView = (TextView) view.findViewById(R.id.resultCorrectAnswerText);
            answerTextView = (TextView) view.findViewById(R.id.resultAnswerText);
        }
    }
}
