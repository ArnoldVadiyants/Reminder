package com.example.reminder;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class
		DatePickerFragment extends DialogFragment {
	public static final String EXTRA_DATE = "com.example.reminder.android.reminderintent.date";
	private Date mDate;
	private Calendar calendar;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		mDate = (Date) getArguments().getSerializable(EXTRA_DATE);
	 	// �������� ������� Calendar ��� ��������� ����, ������ � ���
		calendar = Calendar.getInstance();
		calendar.setTime(mDate);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		//calendar.se
		// TODO Auto-generated method stub
		View v = getActivity().getLayoutInflater().inflate(
				R.layout.dialog_date, null);

		DatePicker datePicker = (DatePicker) v
				.findViewById(R.id.dialog_date_datePicker);
		datePicker.init(year, month, day, new OnDateChangedListener() {
			public void onDateChanged(DatePicker view, int year, int month,
									  int day) {
				calendar.set(Calendar.YEAR, year);
				calendar.set(Calendar.MONTH, month);
				calendar.set(Calendar.DAY_OF_MONTH, day);
				updateDate();
				
			/*	// �������������� ����, ������ � ��� � ������ Date
				mDate = new GregorianCalendar(year, month, day).getTime();
				calendar.setTime(mDate);
				// ���������� ��������� ��� ����������
				// ���������� �������� ��� ��������
				getArguments().putSerializable(EXTRA_DATE, mDate);*/
			}
		});

		TimePicker timePicker = (TimePicker) v
				.findViewById(R.id.dialog_time_timePicker);
		timePicker.setCurrentHour(hour);
		timePicker.setCurrentMinute(minute);
		timePicker.setIs24HourView(true);
		timePicker.setOnTimeChangedListener(new OnTimeChangedListener() {
			@Override
			public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
				// TODO Auto-generated method stub
				calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
				calendar.set(Calendar.MINUTE, minute);
				calendar.set(Calendar.MILLISECOND, 0);
				calendar.set(Calendar.SECOND, 0);
				updateDate();
			}
		});
		Button okButton = (Button)v.findViewById(R.id.dialog_date_okButton);
		okButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (new Date().compareTo(mDate) >= 0) {

					Toast.makeText(
							getActivity(),
							"This time is less than the current time",
							Toast.LENGTH_SHORT).show();

					//sendResult(Activity.RESULT_CANCELED);
				} else {
					getTime();
					sendResult(Activity.RESULT_OK);
					dismiss();
				}
			}
		});

		return new AlertDialog.Builder(getActivity()).setView(v)
				.setTitle(R.string.date_time_picker_title)
		/*.setPositiveButton(android.R.string.ok, new OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub
									if (new Date().compareTo(mDate) >= 0) {

										Toast.makeText(
												getActivity(),
												"this time is less than the current time",
												Toast.LENGTH_SHORT).show();
										return;
										//sendResult(Activity.RESULT_CANCELED);
									} else {
										getTime();
										sendResult(Activity.RESULT_OK);
									}
								}
							}

					)*/.

					create();

				}

	public static DatePickerFragment newInstance(Date date) {
		Bundle args = new Bundle();
		args.putSerializable(EXTRA_DATE, date);
		DatePickerFragment fragment = new DatePickerFragment();
		fragment.setArguments(args);
		return fragment;
	}

	private void sendResult(int resultCode) {
		if (getTargetFragment() == null)
			return;
		Intent i = new Intent();
		i.putExtra(EXTRA_DATE, mDate);
		getTargetFragment().onActivityResult(getTargetRequestCode(),
				resultCode, i);
	}
	private void updateDate()
	{
		mDate = calendar.getTime();
		getArguments().putSerializable(EXTRA_DATE, mDate);
	}

	private void getTime()
	{
		Date d = new Date();
		long l = (mDate.getTime() - d.getTime())/1000;
		long minutes = l/60 + 1;
		long hours = minutes/60;
		long days = hours/24;
		long months = days/30;
		long years = months/12;
		if(years ==0)
		{

			if(months ==0) {
				if(days ==0)
				{
					if(hours ==0)
					{

							Toast.makeText(
									getActivity(),
									"Remind for " + minutes + " minutes from now.",
									Toast.LENGTH_SHORT).show();

					}
					else
					{
						Toast.makeText(
								getActivity(),
								"Remind for " + hours + " hours and "+(minutes - hours*60) + " minutes from now.",
								Toast.LENGTH_SHORT).show();
					}
				}
				else
				{
					Toast.makeText(
							getActivity(),
							"Remind for " + days + " days and "+(hours - days*24) + " hours from now.",
							Toast.LENGTH_SHORT).show();
				}
			}
			else
			{
				Toast.makeText(
						getActivity(),
						"Remind for " + months + " months from now.",
						Toast.LENGTH_SHORT).show();
			}


		}
		else
		{
			Toast.makeText(
					getActivity(),
					"Remind for " + years + " years from now.",
					Toast.LENGTH_SHORT).show();
		}
	/*	int arrayTime[] = getArrayTime();
		int minute = arrayTime[0];
		int hour = arrayTime[1];
		int day = arrayTime[2];
		int month = arrayTime[3];
		int year = arrayTime[4];

		if(hour == 0)
		{
			Toast.makeText(
					getActivity(),
					"Remind for " + minute + " minutes from now.",
					Toast.LENGTH_SHORT).show();
		}
		else
		{
			if(day == 0)
			{
				Toast.makeText(
						getActivity(),
						"Remind for "+ hour+" hours and " + minute + " minutes from now.",
						Toast.LENGTH_SHORT).show();
			}
			else
			{
				if(month == 0)
				{
					Toast.makeText(
							getActivity(),
							"Remind for "+ day +" days, "+ hour+" hours and " + minute + " minutes from now.",
							Toast.LENGTH_SHORT).show();
				}
				else
				{
					if(year == 0)
					{
						Toast.makeText(
								getActivity(),
								"Remind for "+ month +" months, "+ day +" days, "+ hour+" hours and " + minute + " minutes from now.",
								Toast.LENGTH_SHORT).show();
					}
					else
					{
						Toast.makeText(
								getActivity(),
								"Remind for "+ year +" years, "+ month +" months, "+ day +" days, "+ hour+" hours and " + minute + " minutes from now.",
								Toast.LENGTH_SHORT).show();
					}
				}
			}
		}*/


	}
	private int[]getArrayTime()
	{
		int[] time = new int[5];
		final int i_min = 0,i_hour = 1,i_day = 2, i_month = 3, i_year = 4;
		for(int i =0; i<time.length; i++)
		{
			time[i] = 0;
		}
		Calendar c2 = calendar;
		//Date d2 = mDate;
		Calendar c = GregorianCalendar.getInstance();
		Date d = new Date();
		c.setTime(d);
		time[i_min] = c2.get(Calendar.MINUTE) - c.get(Calendar.MINUTE);
		///minutes
		if(time[i_min] < 0)
		{
			time[i_min] = 60 + time[i_min];
			int tmp = c2.get(Calendar.HOUR_OF_DAY) - 1;
			if(tmp>=0)
			{
				c2.set(Calendar.HOUR_OF_DAY, tmp);
			}
			else
			{
				return time;
			}
		}
		///hours
		time[i_hour] = c2.get(Calendar.HOUR_OF_DAY)- c.get(Calendar.HOUR_OF_DAY);
		if(time[i_hour] < 0)
		{
			time[i_hour] = 24 + time[i_hour];
			int tmp = c2.get(Calendar.DAY_OF_MONTH) - 1;
			if(tmp>=0)
			{
				c2.set(Calendar.DAY_OF_MONTH, tmp);
			}
			else
			{
				return time;
			}
		}
		///days
		time[i_day] = c2.get(Calendar.DAY_OF_MONTH)- c.get(Calendar.DAY_OF_MONTH);
		if(time[i_day] < 0)
		{
			int daysInMonth = 31;
			int mnth =c2.get(Calendar.MONTH);
			if(mnth==1)
			{
				if((c2.get(Calendar.YEAR)%4 == 0 && c2.get(Calendar.YEAR)%100 != 0)||c2.get(Calendar.YEAR)%400 == 0)
				{
					daysInMonth = 29;
				}
				else
				{
					daysInMonth = 28;
				}
			}
			else if(mnth==3 ||mnth==5 ||mnth==8 ||mnth==10)
			{
				daysInMonth = 30;
			}
			time[i_day] = daysInMonth + time[i_day];
			int tmp = c2.get(Calendar.MONTH) - 1;
			if(tmp>=0)
			{
				c2.set(Calendar.MONTH, tmp);
			}
			else
			{
				return time;
			}
		}
		///month
		time[i_month] = c2.get(Calendar.MONTH)- c.get(Calendar.MONTH);
		if(time[i_month] < 0)
		{
			time[i_month] = 12 + time[i_month];
			int tmp = c2.get(Calendar.YEAR) - 1;
			if(tmp>=0)
			{
				c2.set(Calendar.YEAR, tmp);
			}
			else
			{
				return time;
			}
		}
		///year
		time[i_year] = c2.get(Calendar.YEAR)- c.get(Calendar.YEAR);
		if(time[i_year] < 0)
		{
			time[i_year] = 0;

			return time;
		}
		return time;
	}
}
