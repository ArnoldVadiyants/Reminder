package com.example.reminder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;


public class StartupReceiver extends BroadcastReceiver {
	private static final String TAG = "StartupReceiver";

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.i(TAG, "Received broadcast intent: " + intent.getAction());
		
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		boolean isOn = prefs.getBoolean(ReminderService.PREF_IS_ALARM_ON, false);
		ReminderService.setServiceAlarm(context, isOn);	
	}

}
