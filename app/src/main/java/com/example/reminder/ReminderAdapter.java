package com.example.reminder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ReminderAdapter extends ArrayAdapter<Reminder> {
	Context c;
	boolean mInvisibleCBox = false;

	public ReminderAdapter(Context context, ArrayList<Reminder> reminders, boolean invisibleCBox) {


		super(context, 0, reminders);
		mInvisibleCBox = invisibleCBox;
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
		TextView replyTextView = (TextView)convertView.findViewById(R.id.reminder_list_item_replyTextView);
		String[] flags_list = c.getResources().getStringArray(R.array.flags_list);
		replyTextView.setText(flags_list[r.getFlag()]);
		ImageView imageView = (ImageView)convertView.findViewById(R.id.snozeImageView);
		if(ReminderLab.get(c).getSnoozedIdList().contains(r.getId()))
		{
			imageView.setImageResource(R.drawable.snooze_reminder);
			imageView.setVisibility(View.VISIBLE);
		}
		else if(r.isActive() && r.isToday())
		{
			imageView.setImageResource(R.mipmap.today_reminder2);
			imageView.setVisibility(View.VISIBLE);
		}
		else
		{
			imageView.setVisibility(View.GONE);
		}
		CheckBox solvedCheckBox =
		(CheckBox)convertView.findViewById(R.id.reminder_list_item_remindedCheckBox);

		solvedCheckBox.setChecked(r.isActive());

		if(mInvisibleCBox)
		{
			solvedCheckBox.setVisibility(View.GONE);
		}
		return convertView;
		
	}
	@Override
	public void notifyDataSetChanged() {
		// TODO Auto-generated method stub
		super.notifyDataSetChanged();
	}
}