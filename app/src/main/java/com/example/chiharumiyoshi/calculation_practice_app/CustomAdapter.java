package com.example.chiharumiyoshi.calculation_practice_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<Item> {

    LayoutInflater layoutInflater;

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
