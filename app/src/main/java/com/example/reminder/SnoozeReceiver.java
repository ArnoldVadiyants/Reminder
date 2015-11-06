
package com.example.reminder;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.NoSuchElementException;
import java.util.UUID;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;


public class SnoozeReceiver extends BroadcastReceiver {

	private  final String TAG = " SnoozeReceiver";
 //       private  boolean alarmStatus = false;
        private  Context context = null;
        private  Class originalClass = null;
        private  AlarmManager alarmManager = null;
       
    //    private  final int notificationIDOD = 0;
    //    private  final int notificationIDRQ = 1;
       
        public  String ID = null;
        public  String IDRQ = null;
       
        @Override
        public void onReceive(Context context,Intent intent)
        {
        	ReminderLab reminderLab = ReminderLab.get(context);
        	LinkedHashSet<UUID>snoozedIdList = reminderLab.getSnoozedIdList();
        	/*ArrayList<Reminder> reminders = ReminderLab
    				.get(context).getReminders();
        	for (Reminder r : reminders) {
    			if(r.isSnoozed())
    			{
    				Log.d(TAG, "****** isSnoozed *******");
    				snooze(r);
    				r.setSnooze(false);
    			}
        	}*/
        	Log.d(TAG, "****** Snooze Recieve *******");
        	Iterator<UUID> iterator = snoozedIdList.iterator();
        	if(!iterator.hasNext())
        	{
        		return;
        	}
        	else
        	{
        		UUID reminderId = null;
        		
        		try
        		{
        			
        		    reminderId = iterator.next();
        			Log.d(TAG, "UUID22: " + reminderId.toString());
        			 (reminderLab.getReminder(reminderId)).setDoRemind(true);
        		}
        		catch(NullPointerException exception)
        		{
        			Reminder reminder = new Reminder();
        			Date date = reminder.newDate(new Date());
        			reminder.setDate(date);
        			reminder.setTitle("NULL Reminder");
        			reminder.setDoRemind(true);
        			reminderLab.addReminder(reminder);
        			Log.d(TAG, "UUID22: " + reminderId.toString());
        			Toast.makeText(context,
        					"Error: snoozed reminder don't exist",
        					Toast.LENGTH_LONG).show();
        		}
        		catch (NoSuchElementException e) {
					// TODO: handle exception
        			e.printStackTrace();
        			Toast.makeText(context,
        					"Error: snoozed reminder don't exist",
        					Toast.LENGTH_LONG).show();
				}
        		
        	
        		iterator.remove();
        		reminderLab.saveReminders();
        		reminderLab.saveSnoozedID(snoozedIdList);
        	}
    
        	
        }
        
     /*   private void snooze(final Reminder reminder)
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
    				ReminderLab.get(context).saveReminders();
    				
    			}
    		}).start();
    	}*/
        
   /*     public  boolean alarmStatus()
        {
                return SnoozeReceiver.alarmStatus;
        }*/
       
        public  void startAlarm(Context context,Class caller, int alarmID)
        {
        	Log.d(TAG, "****** startAlarm *******");
             //   final int intervalTimeSeconds = 1;
                Intent intentAlarm;
                PendingIntent pIntent;
               
              /*  if(alarmStatus == true)
                        return;
                if(SnoozeReceiver.context == null)*/
                        this.context = context;
               
               /* if(SnoozeReceiver.originalClass == null)*/
                        this.originalClass = caller;
                     // ++alarmID;
               
                try
                {
                	Log.d(TAG, "****** Snooze try *******");
                   /*     if(SnoozeReceiver.alarmManager == null)*/
                               alarmManager = (AlarmManager)this.context.getSystemService(Context.ALARM_SERVICE);
                       
                        intentAlarm = new Intent(this.context, this.originalClass);
               //         intentAlarm.putExtra(ReminderFragment.EXTRA_REMINDER_ID, reminderId);
                       
                        pIntent = PendingIntent.getBroadcast(this.context, alarmID, intentAlarm,
                                PendingIntent.FLAG_UPDATE_CURRENT);
               Log.d(TAG, "*****currentTime " +alarmID);
                       
                        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                        		 SystemClock.elapsedRealtime() +
                                ReminderSettings.get(context).getSnoozeTime()* 60 * 1000,pIntent);
                      //  SnoozeReceiver.alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),
                          //              intervalTimeSeconds * 1000, pIntent);
                    //    SnoozeReceiver.alarmManager.se
                }
                catch(Exception e)
                {
                	Log.d(TAG, "****** Snooze catch *******");
                        e.printStackTrace();
                }
         //       alarmStatus = true;    
        }
        public  void stopAlarm(Context context, Class caller, int alarmID)
        {
                Intent intentAlarm;
                PendingIntent pIntent;
               
              /*  if( alarmStatus == false)
                        return;
                if(context == null)*/
                        this.context = context;
               /* if(SnoozeReceiver.originalClass == null)*/
                       this.originalClass = caller;
               
                try
                {
                        intentAlarm = new Intent(this.context, this.originalClass);
                       
                        pIntent = PendingIntent.getBroadcast(this.context, alarmID, intentAlarm,
                                        PendingIntent.FLAG_UPDATE_CURRENT);
                       Log.d(TAG, "currentTime" +alarmID);
                        if(this.alarmManager == null)
                                this.alarmManager = (AlarmManager)this.context.getSystemService(Context.ALARM_SERVICE);
                       
                        this.alarmManager.cancel(pIntent);
                }
                catch(Exception e)
                {
                        e.printStackTrace();
                }
           //     alarmStatus = false;
        }
}
