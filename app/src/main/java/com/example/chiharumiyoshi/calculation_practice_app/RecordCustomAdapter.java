package com.example.chiharumiyoshi.calculation_practice_app;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class RecordCustomAdapter extends ArrayAdapter<RecordItem> {

    LayoutInflater layoutInflater;

    final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());

    public RecordCustomAdapter(Context context, int resource, List<RecordItem> items) {
        super(context, resource, items);
        layoutInflater =  (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.record_list, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        RecordItem item = getItem(position);

        if (item != null) {
            int width = prefs.getInt("width", 500);

            viewHolder.dateTextView.setText(item.dateText);
            viewHolder.detailTextView.setText(item.detail);
            viewHolder.dateTextView.setTextSize(width / 24);
            viewHolder.detailTextView.setTextSize(width / 27);
        }

        return convertView;
    }

    private class ViewHolder{
        TextView dateTextView;
        TextView detailTextView;
        public ViewHolder(View view) {
            dateTextView = (TextView) view.findViewById(R.id.dateText);
            detailTextView = (TextView) view.findViewById(R.id.detail);
        }
    }
}
