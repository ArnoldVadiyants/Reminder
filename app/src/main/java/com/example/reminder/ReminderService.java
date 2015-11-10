package com.example.reminder;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

public class ReminderService extends IntentService {
	private static final String TAG = "ReminderService";
	private static final int REMINDER_INTERVAL = 1000; // �������
	public static final String PREF_IS_ALARM_ON = "isAlarmOn";

	public ReminderService() {
		super(TAG);

		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		// while(true)
		// // {
		ArrayList<Reminder> reminders = ReminderLab
				.get(getApplicationContext()).getReminders();

		// ArrayList<Reminder> activeReminders = new ArrayList<Reminder>();
		/*
		 * for (Reminder r : reminders) { if (r.isActive()) {
		 * activeReminders.add(r); } }
		 */
		/*
		 * if (activeReminders.size() == 0) { return; }
		 */
		// activeReminders = new ReminderSorting().sortByDate(activeReminders);
		Date date = new Date();
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.SECOND, 0);
		date = calendar.getTime();
		Log.d(TAG, date.toString());
		boolean haveDoReminders = false;
		for (final Reminder r : reminders) {
			if (r.isActive()) {
				Log.d(TAG, ""+ (int)r.getId().getMostSignificantBits());
				if (date.compareTo(r.getDate()) >= 0) {
					Log.d(TAG, "*** " + "true" + " ***");
					r.setDoRemind(true);
					r.setActive(false);
					ReminderLab.get(getApplicationContext()).saveReminders();
				} /*else if (date.compareTo(r.getDate()) > 0) {
					final Date d = date;
					new Thread(new Runnable() {
						
						@Override
						public void run() {
							do
							{
								r.setupFlag();
							}
							while(r.isActive() && d.compareTo(r.getDate()) > 0);	
						}
					});
					ReminderLab.get(getApplicationContext()).saveReminders();
				}*/
			}
			if (r.isDoRemind()) {
				haveDoReminders = true;
			}
		}
	//	}
		if (haveDoReminders) {
			createDialog(intent);
		}
		
	}
	private void snooze(final Reminder reminder)
	{
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
				reminder.setDoRemind(true);
				ReminderLab.get(getApplicationContext()).saveReminders();
				
			}
		}).start();
	}
	private void createDialog(Intent intent) {
	
	
		
		if (ServiceDialog.sIsCreated) {
			Log.d(TAG, "is created - true");
			ReminderDialogFragment.dataChanged();
		} else {
			Log.d(TAG, "is created - false");
			
			intent = new Intent("android.intent.action.MAIN");
			intent.setClass(this, ServiceDialog.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
			ReminderLab.get(getApplicationContext()).saveReminders();
		}
	}

	public static void setServiceAlarm(Context context, boolean isOn) {

		Intent intent = new Intent(context, ReminderService.class);
		PendingIntent pi = PendingIntent.getService(context, 0, intent,
				0);
		AlarmManager alarmManager = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		if (isOn) {
			alarmManager.setRepeating(AlarmManager.RTC,
					System.currentTimeMillis(), REMINDER_INTERVAL, pi);
		} else {
			alarmManager.cancel(pi);
			pi.cancel();
		}
		PreferenceManager.getDefaultSharedPreferences(context).edit()
				.putBoolean(ReminderService.PREF_IS_ALARM_ON, isOn).commit();
	}

	private void createNotification(String msg) {

		Resources r = getResources();
		// Intent intent = new Intent(this, NotifyService.class);
		// intent.putExtra("do_action", "notifyCall");
		// PendingIntent pi = PendingIntent.getService(this, 0, intent, 0);
		long[] pattern = { 1000, 1000 };
		Notification notification = new NotificationCompat.Builder(this)
				.setTicker(r.getString(R.string.app_name))
				.setSmallIcon(android.R.drawable.alert_light_frame)
				.setContentTitle(r.getString(R.string.app_name))
				.setVibrate(pattern)
				// .setLights(1000, 1000, 10000)
				.setContentText(msg)
				// .setContentIntent(pi)
				.setAutoCancel(true).setOngoing(false).build();

		NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		mNotificationManager.notify(0, notification);
	}

	private void destroyNotification() {
		NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		mNotificationManager.cancel(0);
	}

	public static boolean isServiceAlarmOn(Context context) {
		Bundle bundle = new Bundle();
		// bundle.put
		Intent i = new Intent(context, ReminderService.class);
		PendingIntent pi = PendingIntent.getService(context, 0, i,
				PendingIntent.FLAG_NO_CREATE);
		return pi != null;


	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

}
