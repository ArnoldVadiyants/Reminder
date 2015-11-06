package com.example.reminder;

import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class ChoiceReplyFragment extends DialogFragment{
	private int replyTime[] = {0,0,0,0,0,0};
	public static final String EXTRA_REPLY_TIME = "com.example.reminder.android.reminderintent.reply_time";
@Override
public Dialog onCreateDialog(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	 replyTime = (int[]) getArguments().getIntArray(EXTRA_REPLY_TIME);
	View v = getActivity().getLayoutInflater().inflate(
			R.layout.other_choice_reply, null);
	final TextView minutesTextView = (TextView) v
			.findViewById(R.id.minutesTextView);
	minutesTextView.setText(getResources().getString(R.string.minutes) + " " + replyTime[0]);
	SeekBar minutesSeekBar = (SeekBar) v
			.findViewById(R.id.minutesSeekBar);
	minutesSeekBar.setProgress(replyTime[0]);
	minutesSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			replyTime[0] = seekBar.getProgress();
		}
		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
		}
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			// TODO Auto-generated method stub
			minutesTextView.setText(getResources().getString(R.string.minutes) + " " + progress);
		}
	});
	final TextView hoursTextView = (TextView) v
			.findViewById(R.id.hoursTextView);
	hoursTextView.setText(getResources().getString(R.string.hours) + " " + replyTime[1]);
	SeekBar hoursSeekBar = (SeekBar) v
			.findViewById(R.id.hoursSeekBar);
	hoursSeekBar.setProgress(replyTime[1]);
	hoursSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			replyTime[1] = seekBar.getProgress();
		}
		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
		}
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			// TODO Auto-generated method stub
			hoursTextView.setText(getResources().getString(R.string.hours) + " " + progress);
		}
	});
	final TextView daysTextView = (TextView) v
			.findViewById(R.id.daysTextView);
	daysTextView.setText(getResources().getString(R.string.days) + " " + replyTime[2]);
	SeekBar daysSeekBar = (SeekBar) v
			.findViewById(R.id.daysSeekBar);
	daysSeekBar.setProgress(replyTime[2]);
	daysSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			replyTime[2] = seekBar.getProgress();
		}
		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
		}
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			// TODO Auto-generated method stub
			daysTextView.setText(getResources().getString(R.string.days) + " " + progress);
		}
	});
	final TextView weeksTextView = (TextView) v
			.findViewById(R.id.weeksTextView);
	weeksTextView.setText(getResources().getString(R.string.weeks) + " " + replyTime[3]);
	SeekBar weeksSeekBar = (SeekBar) v
			.findViewById(R.id.weeksSeekBar);
	weeksSeekBar.setProgress(replyTime[3]);
	weeksSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			replyTime[3] = seekBar.getProgress();
		}
		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
		}
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			// TODO Auto-generated method stub
			weeksTextView.setText(getResources().getString(R.string.weeks) + " " + progress);
		}
	});
	final TextView monthsTextView = (TextView) v
			.findViewById(R.id.monthsTextView);
	monthsTextView.setText(getResources().getString(R.string.months) + " " + replyTime[4]);
	SeekBar monthsSeekBar = (SeekBar) v
			.findViewById(R.id.monthsSeekBar);
	monthsSeekBar.setProgress(replyTime[4]);
	monthsSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			replyTime[4] = seekBar.getProgress();
		}
		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
		}
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			// TODO Auto-generated method stub
			monthsTextView.setText(getResources().getString(R.string.months) + " " + progress);
		}
	});
	final TextView yearsTextView = (TextView) v
			.findViewById(R.id.yearsTextView);
	yearsTextView.setText(getResources().getString(R.string.years) + " " + replyTime[5]);
	SeekBar yearsSeekBar = (SeekBar) v
			.findViewById(R.id.yearsSeekBar);
	yearsSeekBar.setProgress(replyTime[5]);
	yearsSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			replyTime[5] = seekBar.getProgress();
		}
		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
		}
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			// TODO Auto-generated method stub
			yearsTextView.setText(getResources().getString(R.string.years) + " " + progress);
		}
	});
	

	
	return new AlertDialog.Builder(getActivity()).setView(v)
			.setTitle("Create other reply variant")
			.setPositiveButton(android.R.string.ok, new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					sendResult(Activity.RESULT_OK);
				}
			}).create();
}
public static ChoiceReplyFragment newInstance(int array[]) {
	Bundle args = new Bundle();
	args.putIntArray(EXTRA_REPLY_TIME, array);
	ChoiceReplyFragment fragment = new ChoiceReplyFragment();
	fragment.setArguments(args);
	return fragment;
}
private void sendResult(int resultCode) {
	if (getTargetFragment() == null)
		return;
	Intent i = new Intent();
	i.putExtra(EXTRA_REPLY_TIME,replyTime);
	getTargetFragment().onActivityResult(1,
			resultCode, i);
}
}
