package com.example.reminder;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

public class ReminderSettings {
	private static final String JSON_SNOOZE_TIME = "snooze_time";
	private static final String JSON_SORT_VALUE = "sort_value";
	private static final String JSON_SORT_UP = "sort_up";
	private static final String TAG = "ReminderSettings";
	private static final String FILENAME = "settings.json";
	public static final int SORT_NONE = 0;
	public static final int SORT_DATE = 1;
	public static final int SORT_NAME = 2;
	
	private int mSnoozeTime;
	

	private  int mSortValue;
	private boolean mSortUp;
	private ReminderIntentJSONSerializer mSerializer;
	private static ReminderSettings sReminderSettings;
	private Context mAppContext;

	private ReminderSettings(Context appContext) {
		mAppContext = appContext;
		mSerializer = new ReminderIntentJSONSerializer(mAppContext, FILENAME);
		try {
			JSONObject json = mSerializer.loadSettings();
			mSnoozeTime = json.getInt(JSON_SNOOZE_TIME);
			mSortValue = json.getInt(JSON_SORT_VALUE);
			mSortUp = json.getBoolean(JSON_SORT_UP);
			// Log.d(TAG, "loading settings: ");
		} catch (Exception e) {
			mSnoozeTime = 1;
			mSortValue = SORT_NONE;
			mSortUp = true;
			// Log.e(TAG, "Error loading reminders: ", e);
		}

	}
	public int getSortValue() {
		return mSortValue;
	}

	public void setSortValue(int sortValue) {
		mSortValue = sortValue;
	}

	public boolean isSortUp() {
		return mSortUp;
	}

	public void setSortUp(boolean sortUp) {
		mSortUp = sortUp;
	}
	public int getSnoozeTime() {
		return mSnoozeTime;
	}

	public void setSnoozeTime(int snoozeTime) {
		mSnoozeTime = snoozeTime;
	}

	public static ReminderSettings get(Context c) {
		if (sReminderSettings == null) {
			sReminderSettings = new ReminderSettings(c.getApplicationContext());
		}
		// else newConstructor = false;
		return sReminderSettings;
	}

	public boolean saveSettings() {
		try {
			JSONObject json = new JSONObject();
			json.put(JSON_SNOOZE_TIME, mSnoozeTime);
			json.put(JSON_SORT_VALUE, mSortValue);
			json.put(JSON_SORT_UP, mSortUp);
			mSerializer.saveSettings(json);
			Log.d(TAG, "settings saved to file");
			return true;
		} catch (Exception e) {
			Log.e(TAG, "Error saving settings: ", e);
			return false;
		}
	}
}
