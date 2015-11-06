package com.example.reminder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Toast;
	
public class ReminderFragment extends Fragment {
	public static final String EXTRA_REMINDER_ID =
			"com.example.reminder.android.reminderintent.reminder_id";
	
	private static final String TAG = "ReminderFragment";
	private static final String DIALOG_DATE = "date";
	private static final String DIALOG_REPLY = "reply";
	private static final int REQUEST_DATE = 0;
	private static final int REQUEST_REPLY_TIME = 1;
	public static final String DATE_TIME_FORMAT = "d MMM yyyy  kk:mm";
	private String[] flags_list;
	private Reminder mReminder;
	private EditText mTitleField;
	private Button mDateButton;
	private CheckBox mRemindCheckBox;
	private Button mFlagButton;
	public ReminderFragment() {
		// TODO Auto-generated constructor stub
		Log.d(TAG, "************************");
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		UUID reminderId = (UUID)getArguments().getSerializable(EXTRA_REMINDER_ID);
				mReminder = ReminderLab.get(getActivity()).getReminder(reminderId);
			 flags_list = getResources().getStringArray(R.array.flags_list);
				setRetainInstance(true);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (resultCode != Activity.RESULT_OK) return;
		if (requestCode == REQUEST_DATE) {
			Date date = (Date)data
			.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
			mReminder.setDate(date);
			updateDate();
			}
		
		if (requestCode == REQUEST_REPLY_TIME) {
			
			mReminder.setReplyTime((int[])data.getIntArrayExtra(ChoiceReplyFragment.EXTRA_REPLY_TIME));
			//updateDate();
			}
	}
	@TargetApi(11)
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View v = inflater.inflate(R.layout.fragment_reminder, container, false);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			if (NavUtils.getParentActivityName(getActivity()) != null) {
				getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
				}
		}

		mTitleField = (EditText) v.findViewById(R.id.reminder_title);
		mTitleField.setText(mReminder.getTitle());
		mTitleField.addTextChangedListener(new TextWatcher() {
			public void onTextChanged(CharSequence c, int start, int before,
					int count) {
				mReminder.setTitle(c.toString());
			}

			public void beforeTextChanged(CharSequence c, int start, int count,
					int after) {
				
			}

			public void afterTextChanged(Editable c) {
				
			}
		});
		mDateButton = (Button) v.findViewById(R.id.reminder_date);
		updateDate();
		mDateButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				FragmentManager fm = getActivity().getSupportFragmentManager();
				DatePickerFragment dialog = DatePickerFragment
						.newInstance(mReminder.getDate());
				dialog.setTargetFragment(ReminderFragment.this, 0);
				dialog.show(fm,DIALOG_DATE);
				
			}
		});
		mFlagButton = (Button) v.findViewById(R.id.sortButton);
		updateFlag();
		mFlagButton.setOnClickListener( new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				   
				   
				   AlertDialog.Builder choicesBuilder = new AlertDialog.Builder(getActivity());
				   choicesBuilder.setTitle(R.string.reply_reminder);
				   choicesBuilder.setItems(R.array.flags_list,new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int item) {
						if(item == 7)
						{
						//	FragmentManager fm = getActivity().getSupportFragmentManager();
						//	DatePickerFragment dialog = DatePickerFragment
									
						//	dialog.setTargetFragment(ReminderFragment.this, 0);
						//	dialog.show(fm,DIALOG_DATE);
							FragmentManager fm = getActivity().getSupportFragmentManager();
							ChoiceReplyFragment choiceDialog =ChoiceReplyFragment.newInstance(mReminder.getReplyTime());
							choiceDialog.setTargetFragment(ReminderFragment.this, REQUEST_DATE);
							choiceDialog.show(fm, DIALOG_REPLY);
						}
							mReminder.setFlag(item);
							updateFlag();
						
					}// onClick  
				   }// choicesBuilder.setItems
				   );
				   AlertDialog choicesDialog = choicesBuilder.create();
				   choicesDialog.show();
			}
		});

		mRemindCheckBox = (CheckBox) v.findViewById(R.id.reminder_reminded);
		mRemindCheckBox.setChecked(mReminder.isActive());
		mRemindCheckBox.setOnCheckedChangeListener( new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						// TODO Auto-generated method stub
						Animation animation = null;

						if (new Date().compareTo(mReminder.getDate()) >= 0) {
							animation = AnimationUtils.loadAnimation(
									getActivity(), R.anim.translate_animation);
							Toast.makeText(
									getActivity(),
									"this time is less than the current time",
									Toast.LENGTH_SHORT).show();
							mReminder.setActive(false);
							mRemindCheckBox.setChecked(false);
						} else {
							animation = AnimationUtils.loadAnimation(
									getActivity(), R.anim.skale_animation);
							mReminder.setActive(isChecked);
						}
						mRemindCheckBox.startAnimation(animation);
					}
				});

		return v;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:
			if (NavUtils.getParentActivityName(getActivity()) != null) {
				NavUtils.navigateUpFromSameTask(getActivity());
			
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public static ReminderFragment newInstance(UUID reminderId) {
		Bundle args = new Bundle();
		args.putSerializable(EXTRA_REMINDER_ID, reminderId);
		ReminderFragment fragment = new ReminderFragment();
		fragment.setArguments(args);
		return fragment;
		}
	public void updateDate() {
		 SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_TIME_FORMAT); 
	        String dateForButton = dateFormat.format(mReminder.getDate()); 
	        mDateButton.setText(dateForButton);
		}
	public void updateFlag() {
	        mFlagButton.setText("Reply: " + flags_list[mReminder.getFlag()]);
		}
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	
	}

}
