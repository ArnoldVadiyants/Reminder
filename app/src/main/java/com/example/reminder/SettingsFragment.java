package com.example.reminder;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
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
		 sortButton = (Button)view.findViewById(R.id.sortButton);
		updateSort();
		 sortToggleButton = (ToggleButton)view.findViewById(R.id.sortToggleButton);
		 updateSortUp();
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
		sortButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				   AlertDialog.Builder sortingBuilder = new AlertDialog.Builder(getActivity());
				   sortingBuilder.setTitle(R.string.sort);
				   sortingBuilder.setItems(R.array.sort_list,new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int item) {
						
						mSettings.setSortValue(item);
							updateSort();
							sortToggleButton.setChecked(false);
							sortToggleButton.setChecked(true);
							
					}// onClick  
				   }// choicesBuilder.setItems
				   );
				   AlertDialog sortingDialog = sortingBuilder.create();
				   sortingDialog.show();	
			}
		});
		sortToggleButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				mSettings.setSortUp(isChecked);
				updateSortUp();
			}
		});
	
		
return view;
	}
	private void updateSnoozeTime()
	{
		snoozeTextView.setText(mSettings.getSnoozeTime() + " " + "minutes");
	}
	private void updateSort()
	{
		String[]sortArray = getResources().getStringArray(R.array.sort_list);
		 sortButton.setText("Sort By " + sortArray[mSettings.getSortValue()] );
	}
	private void updateSortUp()
	{
		switch (mSettings.getSortValue()) {
		case ReminderSettings.SORT_NONE:
			sortToggleButton.setVisibility(View.GONE);
			break;
		case ReminderSettings.SORT_DATE:
			sortToggleButton.setVisibility(View.VISIBLE);
			if(mSettings.isSortUp())
			{
				sortToggleButton.setTextOn(getResources().getString(R.string.sort_new_old));
			}
			else
			{
				sortToggleButton.setTextOff(getResources().getString(R.string.sort_old_new));
			}
			break;
		case ReminderSettings.SORT_NAME:
			sortToggleButton.setVisibility(View.VISIBLE);
			if(mSettings.isSortUp())
			{
				sortToggleButton.setTextOn(getResources().getString(R.string.sort_A_Z));
			}
			else
			{
				sortToggleButton.setTextOff(getResources().getString(R.string.sort_Z_A));
			}
			break;
		default:
			break;
		}
	}
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		ReminderSettings.get(getActivity()).saveSettings();
	}
}
