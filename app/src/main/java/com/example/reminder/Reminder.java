package com.example.reminder;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class Reminder {
	
	public static final String EMPTY_TITLE = "No title";
	private static final String TAG = "Reminder";
	private static final String JSON_ID = "id";
	private static final String JSON_TITLE = "title";
	private static final String JSON_ACTIVE = "active";
	private static final String JSON_DATE = "date";
	private static final String JSON_FLAG = "flag";
	private static final String JSON_SNOOZE = "snooze";
	private static final String JSON_DO_REMIND = "do_remind";
	private static final String JSON_REPLY_TIME_MIN = "reply_min";
	private static final String JSON_REPLY_TIME_HOUR = "reply_hour";
	private static final String JSON_REPLY_TIME_DAY = "reply_day";
	private static final String JSON_REPLY_TIME_WEEK = "reply_week";
	private static final String JSON_REPLY_TIME_MONTH = "reply_month";
	private static final String JSON_REPLY_TIME_YEARS = "reply_years";
	
	private static final int FLAG_NONE = 0;
	private static final int FLAG_EVERY_MINUTE = 1;
	private static final int FLAG_EVERY_HOUR = 2;
	private static final int FLAG_EVERY_DAY = 3;
	private static final int FLAG_EVERY_WEEK = 4;
	private static final int FLAG_EVERY_MONTH = 5;
	private static final int FLAG_EVERY_YEAR = 6;
	private static final int FLAG_OTHER = 7;
	private int mCalendarFields[] = { 0, Calendar.MINUTE, Calendar.HOUR_OF_DAY,
			Calendar.DAY_OF_MONTH, Calendar.WEEK_OF_MONTH, Calendar.MONTH,
			Calendar.YEAR, FLAG_OTHER };
	private int mReplyTime[];
	private UUID mId;
	private String mTitle;
	private Date mDate;
	private boolean mIsActive;
	private boolean mDoRemind;
	private boolean mIsSnoozed;
	
	public boolean isSnoozed() {
		return mIsSnoozed;
	}

	public void setSnooze(boolean snooze) {
		mIsSnoozed = snooze;
	}

	private int mFlag;
	

	public Reminder() {
		// TODO Auto-generated constructor stub
		mId = UUID.randomUUID();
		mDate = newDate();
		mFlag = FLAG_NONE;
		mIsActive = true;
		mReplyTime = new int[6];
		mDoRemind = false;
		mIsSnoozed = false;
		mTitle = "";
	}
	
	public Date newDate()
	{
		Calendar calendar = GregorianCalendar.getInstance();
		Date date = new Date();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR_OF_DAY, 1);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.SECOND, 0);
		date = calendar.getTime();
		return date;
		
	}
	public Date newDate(Date date)
	{
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.SECOND, 0);
		date = calendar.getTime();
		return date;
	}

	public void setReplyTime(int[] replyTime) {
	mReplyTime = replyTime;
	}

	public int[] getReplyTime() {
		return mReplyTime;
	}

	public int getFlag() {
		return mFlag;
	}

	public void setFlag(int flag) {
		this.mFlag = flag;
	}

	public void setupFlag() 
	{
		
	//	new Thread(new Runnable() {//
			
		//	@Override
		//	public void run() {///
				Calendar calendar = GregorianCalendar.getInstance();
				calendar.setTime(mDate);
				if (mCalendarFields[mFlag] == 0) {
		mIsActive = false;
				} else if (mCalendarFields[mFlag] == 7) {
					for (int i = 0; i < 6; i++) {
						calendar.add(mCalendarFields[i + 1], mReplyTime[i]);
						/*
						 * calendar.add(Calendar.HOUR_OF_DAY, 1);
						 * calendar.add(Calendar.DAY_OF_MONTH, 1);
						 * calendar.add(Calendar.WEEK_OF_MONTH, 1);
						 * calendar.add(Calendar.MONTH, 1); 
						 * calendar.add(Calendar.YEAR, 1);
						 */
					}
					Date date = calendar.getTime();
					if(!mDate.equals(date))
					{
						mDate = date;
						mIsActive = true;
					}
					Log.d(TAG, mDate.toString() + "   " + " Add OTHER");
				} else {
					calendar.add(mCalendarFields[mFlag], 1);
					mDate = calendar.getTime();
					mIsActive = true;
				}
				///	}
				///});
		
	
		Date date = newDate(new Date());
		 if ((date.compareTo(mDate) >= 0) &&(mIsActive == true)) {
			 setupFlag();
			 Log.d(TAG, "SETUP FLAG");
		 }
	}
	
	public Reminder(JSONObject json) throws JSONException {
		mReplyTime = new int[6];
		mId = UUID.fromString(json.getString(JSON_ID));
		mTitle = json.getString(JSON_TITLE);
		mIsActive = json.getBoolean(JSON_ACTIVE);
		mDoRemind = json.getBoolean(JSON_DO_REMIND);
		mDate = new Date(json.getLong(JSON_DATE));
		mFlag = json.getInt(JSON_FLAG);
		mIsSnoozed = json.getBoolean(JSON_SNOOZE);
		mReplyTime[0] = json.getInt(JSON_REPLY_TIME_MIN);
		mReplyTime[1] = json.getInt(JSON_REPLY_TIME_HOUR);
		mReplyTime[2] = json.getInt(JSON_REPLY_TIME_DAY);
		mReplyTime[3] = json.getInt(JSON_REPLY_TIME_WEEK);
		mReplyTime[4] = json.getInt(JSON_REPLY_TIME_MONTH);
		mReplyTime[5] = json.getInt(JSON_REPLY_TIME_YEARS);
	
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return mTitle;
	}

	public boolean isDoRemind() {
		return mDoRemind;
	}

	public void setDoRemind(boolean doRemind) {
		mDoRemind = doRemind;
	}

	public Date getDate() {
		return mDate;
	}

	public void setDate(Date date) {
		mDate = date;
	}

	public boolean isActive() {
		return mIsActive;
	}

	public void setActive(boolean active) {
		mIsActive = active;
	}
	public String getTitle() {
		return mTitle;
	}

	public void setTitle(String title) {
			mTitle = title;
	}

	public UUID getId() {
		return mId;
	}

	public JSONObject toJSON() throws JSONException {
		JSONObject json = new JSONObject();
		json.put(JSON_ID, mId.toString());
		json.put(JSON_TITLE, mTitle);
		json.put(JSON_ACTIVE, mIsActive);
		json.put(JSON_DO_REMIND, mDoRemind);
		json.put(JSON_SNOOZE, mIsSnoozed);
		json.put(JSON_DATE, mDate.getTime());
		json.put(JSON_FLAG, mFlag);
		json.put(JSON_REPLY_TIME_MIN, mReplyTime[0]);
		json.put(JSON_REPLY_TIME_HOUR, mReplyTime[1]);
		json.put(JSON_REPLY_TIME_DAY, mReplyTime[2]);
		json.put(JSON_REPLY_TIME_WEEK, mReplyTime[3]);
		json.put(JSON_REPLY_TIME_MONTH, mReplyTime[4]);
		json.put(JSON_REPLY_TIME_YEARS, mReplyTime[5]);
		
		return json;
	}
}
