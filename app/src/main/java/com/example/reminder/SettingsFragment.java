package com.example.reminder;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.ToggleButton;

public class SettingsFragment extends Fragment {
	private TextView snoozeTextView;
	private SeekBar snoozeSeekBar;
	private Button sortButton;
	private ToggleButton sortToggleButton;
	private ReminderSettings mSettings;
	private static final int MIN_PROGRESS = 1;
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mSettings = ReminderSettings.get(getActivity());
		setRetainInstance(true);
		getActivity().setTitle(R.string.settings);
		
	}
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		View view = getActivity().getLayoutInflater().inflate(
				R.layout.settings_fragment, null);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			if (NavUtils.getParentActivityName(getActivity()) != null) {
				getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
				}
		}
		/*
		 * ArrayList<Reminder> mReminders = ReminderLab.get(
		 * getApplicationContext()).getReminders(); for (Reminder r :
		 * mReminders) { if (r.isDoRemind()) { doReminders.add(r); } }
		 */
	 snoozeTextView = (TextView) view.findViewById(R.id.snoozeTextView);
	 updateSnoozeTime();
		 snoozeSeekBar = (SeekBar) view.findViewById(R.id.snoozeSeekBar);
		snoozeSeekBar.setProgress(mSettings.getSnoozeTime() - MIN_PROGRESS);
		snoozeSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
			
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				mSettings.setSnoozeTime(progress + MIN_PROGRESS);
				updateSnoozeTime();
			}
		});



	
		
return view;
	}
	private void updateSnoozeTime()
	{
		snoozeTextView.setText(mSettings.getSnoozeTime() + " " + "minutes");
	}


	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		ReminderSettings.get(getActivity()).saveSettings();
	}
}
