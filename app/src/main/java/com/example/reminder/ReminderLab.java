package com.example.reminder;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.UUID;

public class ReminderLab {

	
	private static final String TAG = "ReminderLab";
	private static final String REMINDERS_FILENAME = "reminders.json";
	private static final String SNOOZED_FILENAME = "snoozed.json";
	private static final String JSON_SNOOZED = "snoozed_reminders";
	private ArrayList<Reminder> mReminders;
	private ReminderIntentJSONSerializer mSerializer;
	private static ReminderLab sReminderLab;
	private Context mAppContext;
	//public static boolean newConstructor = true;

	private ReminderLab(Context appContext) {
		mAppContext = appContext;
		mSerializer = new ReminderIntentJSONSerializer(mAppContext, REMINDERS_FILENAME);
		try {
			mReminders = mSerializer.loadReminders();
			Log.d(TAG, "loading reminders: ");
			} catch (Exception e) {
			mReminders = new ArrayList<Reminder>();
			Log.e(TAG, "Error loading reminders: ", e);
			}
		
	}

	public static ReminderLab get(Context c) {
		if (sReminderLab == null) {
			sReminderLab = new ReminderLab(c.getApplicationContext());
		}
	//	else newConstructor = false;
		return sReminderLab;
	}

	public void addReminder(Reminder r) {
		mReminders.add(r);
		sortReminders();
	}
	public void deleteReminder(Reminder r) {
		mReminders.remove(r);
		LinkedHashSet<UUID> snoozedIdList = getSnoozedIdList();
		if(snoozedIdList.remove(r.getId()))
			{
			new SnoozeReceiver().stopAlarm(mAppContext, SnoozeReceiver.class, (int)r.getId().getMostSignificantBits());
			saveSnoozedID(snoozedIdList);
			}
		sortReminders();
		}
	public ArrayList<Reminder> getReminders() {

		return mReminders;
	}

	public Reminder getReminder(UUID id) {
		for (Reminder r : mReminders) {
			Log.d(TAG, "UUID **" + r.getId()+"**" + id + "**");
			if (r.getId().equals(id))
			{
				return r;	
			}
				
			
		}
		return null;
	}
	public boolean saveReminders() {
		try {
			sortReminders();
		mSerializer.saveReminders(mReminders);
		Log.d(TAG, "reminders saved to file");
		return true;
		} catch (Exception e) {
		Log.e(TAG, "Error saving reminders: ", e);
		return false;
		}
		}
	public void sortReminders()
	{
		Collections.sort(mReminders, new DateSortComparator() {
		});
	}

	public LinkedHashSet<UUID> getSnoozedIdList()
	{
		ReminderIntentJSONSerializer serializer = new ReminderIntentJSONSerializer(mAppContext, SNOOZED_FILENAME);
		LinkedHashSet<UUID> snoozedIdList = new LinkedHashSet<UUID>();
		try {
			JSONArray array = serializer.loadSnoozedRemindersID();
			for (int i = 0; i < array.length(); i++) {
				snoozedIdList.add(UUID.fromString((array.getJSONObject(i)).getString(JSON_SNOOZED)));
			}
			Log.d(TAG, "loading reminders: ");
			} catch (Exception e) {
			Log.e(TAG, "Error loading reminders: ", e);
			}
		return snoozedIdList;
		
	}
	public boolean saveSnoozedID(LinkedHashSet<UUID>snoozedIdList) 
	{
		ReminderIntentJSONSerializer serializer = new ReminderIntentJSONSerializer(mAppContext, SNOOZED_FILENAME);
		JSONArray array = new JSONArray();
		Iterator<UUID> iterator = snoozedIdList.iterator();
		try
		{
			while(!snoozedIdList.isEmpty())
			{
				JSONObject json = new JSONObject();
				json.put(JSON_SNOOZED, iterator.next().toString());
				array.put(json);
				iterator.remove();				
			}
		}catch(JSONException e)
		{	
		}
		
		try {
		serializer.saveSnoozedRemindersID(array);
		Log.d(TAG, "snoozedIdList saved to file");
		return true;
		} catch (Exception e) {
		Log.e(TAG, "Error saving snoozedIdList: ", e);
		return false;
		}
	}
}
