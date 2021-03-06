package com.example.reminder;

import android.app.Activity;
import android.content.Context;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager.WakeLock;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.UUID;

public class ReminderDialogFragment extends Fragment {
	private static final Uri ALARM_URI = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
	private static final Uri RINGTONE_URI = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
	private static final String TAG = "ReminderDialogFragment";
	private static ReminderAdapter sAdapter;
	private static ArrayList<Reminder> doReminders;
	public static boolean sDialogIsCreated = false;
	private static Activity sActivity;
	public static boolean onPaused = false;
	private WakeLock mWakeLock;
	private Vibrator mVibrator;
	private Ringtone mRingtone;
	private boolean hasVibrate;
	private final long[] pattern = { 0, 500, 1000 };
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
		sActivity = getActivity();
		hasVibrate = false;
		/*Window window = sActivity.getWindow();
		sActivity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
				| WindowManager.LayoutParams.FLAG_FULLSCREEN
	            | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD 
	            | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
	            | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
	            | WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON);*/
		mRingtone = RingtoneManager.getRingtone(sActivity, ALARM_URI);
		mVibrator = (Vibrator)getActivity().getSystemService(Context.VIBRATOR_SERVICE);
		playAlarmSound();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		sDialogIsCreated = true;
		Log.d(TAG, "onCreate");
		doReminders = getDoReminders();
		sAdapter = new ReminderAdapter(getActivity(), doReminders, true);
		View view = getActivity().getLayoutInflater().inflate(
				R.layout.dialog_reminder, null);
		ListView reminderListView = (ListView) view
				.findViewById(R.id.dialog_remindersListView);
		reminderListView.setAdapter(sAdapter);
		/*
		 * ArrayList<Reminder> mReminders = ReminderLab.get(
		 * getApplicationContext()).getReminders(); for (Reminder r :
		 * mReminders) { if (r.isDoRemind()) { doReminders.add(r); } }
		 */
		Button stopButton = (Button) view.findViewById(R.id.stopButton);
		Button snoozeButton = (Button) view.findViewById(R.id.snoozeButton);
		stopButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				stopPlaying();
				for (Reminder r : doReminders) {
					r.setDoRemind(false);
					r.setupFlag();
				}
				ReminderLab.get(sActivity).saveReminders();
				finishFragment();
			}
		});

		snoozeButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				stopPlaying();
				// TODO Auto-generated method stub
				ReminderLab reminderLab = ReminderLab.get(sActivity);
				LinkedHashSet<UUID> snoozedIdList = reminderLab.getSnoozedIdList();
				
				boolean hasSnoozed = false;
				for (Reminder r : doReminders) {
					r.setDoRemind(false);
					// r.setSnooze(true);
					if(snoozedIdList.add(r.getId()))
					{
						// ++SnoozeReceiver.alarmID;
						new SnoozeReceiver().startAlarm(getActivity(),
								SnoozeReceiver.class, (int)r.getId().getMostSignificantBits());
					hasSnoozed = true;
					}else
					{
						Toast.makeText(getActivity(), "Reminder " + "(" + r.getTitle() +")" +" is already snoozed", Toast.LENGTH_SHORT).show();

					}
					if(hasSnoozed)
					{
						Toast.makeText(getActivity(),
	        					"snoozing after " + ReminderSettings.get(getActivity()).getSnoozeTime() + " minutes",
	        					Toast.LENGTH_SHORT).show();
					}

				}
				reminderLab.saveSnoozedID(snoozedIdList);
				finishFragment();
			}

		});

		return view;
	}

	private void finishFragment() {
		// ReminderLab.get(sActivity).saveReminders();
		sDialogIsCreated = false;
		getActivity().finish();
	}
	private void playAlarmSound()
	{
		hasVibrate = true;
		mRingtone.play();
		mVibrator.vibrate(pattern,0);
	/*	new Thread(new Runnable() {
			@Override
			public void run() {
				while (hasVibrate)
				{
					mVibrator.vibrate(pattern,5);
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});*/
	}
	  private void stopPlaying() {
          if (mRingtone.isPlaying()) {
        	  mRingtone.stop();
         }
		  mVibrator.cancel();
      }
	  public boolean isCreated()
	  {
		  boolean isCreated = false;
		  Fragment mFragment = getFragmentManager().findFragmentById(R.id.fragmentContainer);
		  if (mFragment != null && mFragment instanceof ReminderDialogFragment)
		  {
			  isCreated = true;
		  }
		      return isCreated;
	  }

	public static void dataChanged() {
		doReminders = getDoReminders();
		sActivity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				sAdapter.clear();
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
					sAdapter.addAll(doReminders);
				}
				else
				{
					for(Reminder r : doReminders)
					{
						sAdapter.add(r);
					}

				}
			}
		});

	}

	private static ArrayList<Reminder> getDoReminders() {
		ArrayList<Reminder> doReminders = new ArrayList<Reminder>();
		ArrayList<Reminder> mReminders = ReminderLab.get(sActivity)
				.getReminders();
		int ss = 0;
		for (Reminder r : mReminders) {
			if (r.isDoRemind()) {
				doReminders.add(r);
				ss++;
			}
		}
		Log.d(TAG, "doReminders :" + ss);
		return doReminders;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		Log.d(TAG, "onDestroyFragment");
		super.onDestroy();
		stopPlaying();
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
	}
}
