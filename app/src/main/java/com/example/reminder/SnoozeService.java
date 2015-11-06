package com.example.reminder;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

public class SnoozeService extends Service {
	public static final String EXTRA_REMINDER_ID = "com.example.reminder.android.reminderintent.reminder_id";
	private static final String TAG = "SnoozeService";
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Log.d(TAG,"******onCreate******");
	    @SuppressWarnings("deprecation")
			Notification notification = new Notification(0, "Reminder",
			        System.currentTimeMillis());
				Intent notificationIntent = new Intent(this, ServiceDialog.class);
				PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
				notification.setLatestEventInfo(this, getText(R.string.app_name),
				       "Reminder", pendingIntent);
				
		startForeground(1, notification);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		Log.d(TAG,"******onStart******");
		UUID reminderID = (UUID)intent.getSerializableExtra(EXTRA_REMINDER_ID);
		Reminder r = ReminderLab.get(getApplicationContext()).getReminder(reminderID);

		
		
		
		wakeUp(startId, reminderID);
	//	return super.onStartCommand(intent, flags, startId);
		return START_STICKY;
	}
	
	private void wakeUp(final int startId, final UUID reminderID) {
		new Thread(new Runnable() {
			public void run() {
				try {
					Log.d(TAG, "****** try ******");
					TimeUnit.SECONDS.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Log.d(TAG, "****** catch ******");
				}
				Log.d(TAG, "****** setDoRemind ******");
				ReminderLab.get(getApplicationContext())
						.getReminder(reminderID).setDoRemind(true);
				ReminderLab.get(getApplicationContext()).saveReminders();
				stopSelf(startId);
			}
		}).start();
	}

	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	@Override
	public void onTaskRemoved(Intent rootIntent) {
		// TODO Auto-generated method stub
		super.onTaskRemoved(rootIntent);
		Log.d(TAG, "****** REMOVED ******");
	
	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.d(TAG,"****** onDestroy ******");
	}
}
