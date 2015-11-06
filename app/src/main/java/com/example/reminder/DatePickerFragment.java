package com.example.reminder;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;
import android.widget.Toast;

public class DatePickerFragment extends DialogFragment {
	public static final String EXTRA_DATE = "com.example.reminder.android.reminderintent.date";
	private Date mDate;
	private Calendar calendar;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		mDate = (Date) getArguments().getSerializable(EXTRA_DATE);
	 	// создание объекта Calendar для получения года, месяца и дня
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
				calendar.set(Calendar.YEAR,year);
				calendar.set(Calendar.MONTH, month);
				calendar.set(Calendar.DAY_OF_MONTH, day);
				updateDate();
				
			/*	// Преобразование года, месяца и дня в объект Date
				mDate = new GregorianCalendar(year, month, day).getTime();
				calendar.setTime(mDate);
				// обновление аргумента для сохранения
				// выбранного значения при повороте
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

		return new AlertDialog.Builder(getActivity()).setView(v)
				.setTitle(R.string.date_time_picker_title)
				.setPositiveButton(android.R.string.ok, new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						if(new Date().compareTo(mDate)>=0 )
						{
							
							Toast.makeText(
									getActivity(),
									"this time is less than the current time",
									Toast.LENGTH_SHORT).show();
							sendResult(Activity.RESULT_CANCELED);
						}
						else sendResult(Activity.RESULT_OK);
					}
				}).create();

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
}
