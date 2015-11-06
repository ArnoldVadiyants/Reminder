package com.example.reminder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class ReminderAdapter extends ArrayAdapter<Reminder> {
	Context c;
	public ReminderAdapter(Context context, ArrayList<Reminder> reminders) {
		
		
		super(context, 0, reminders);
		c = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) c.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
			convertView =  inflater.inflate(
					R.layout.list_item_reminder, null);
		}
		Reminder r = getItem(position);
		TextView titleTextView = (TextView) convertView
				.findViewById(R.id.reminder_list_item_titleTextView);
		titleTextView.setText(r.getTitle());
		TextView dateTextView = (TextView)convertView.findViewById(R.id.reminder_list_item_dateTextView); 
		SimpleDateFormat dateFormat = new SimpleDateFormat(ReminderFragment.DATE_TIME_FORMAT); 
        String dateForButton = dateFormat.format(r.getDate()); 
		dateTextView.setText(dateForButton);
		CheckBox solvedCheckBox =
		(CheckBox)convertView.findViewById(R.id.reminder_list_item_remindedCheckBox);
		solvedCheckBox.setChecked(r.isActive());
		return convertView;
		
	}
	@Override
	public void notifyDataSetChanged() {
		// TODO Auto-generated method stub
		super.notifyDataSetChanged();
	}
}