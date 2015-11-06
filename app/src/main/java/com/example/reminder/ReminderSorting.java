package com.example.reminder;

import java.util.ArrayList;
import java.util.Arrays;

import android.util.Log;

public class ReminderSorting {
	private static final String TAG = "ReminderSorting";
	private static final String DATE_TIME_FORMAT = "dd.MM.yyyy HH:mm:ss";

	public ReminderSorting() {
		// TODO Auto-generated constructor stub
	}

	public ArrayList<Reminder> sortByDate(ArrayList<Reminder> reminders) {
		// SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
		// int s= new DateComparator().compare(
		// dateFormat.format(reminders.get(0).getDate()),
		// dateFormat.format(reminders.get(1).getDate()));
		// Log.d(TAG, "***** "+ s + " *****");
		reminders.trimToSize();
		int size = reminders.size();
		if(size == 0)
		{
			return null;
		}
		DateSortReminder [] dArray = new DateSortReminder[size];
		
		int s =0;
		for(Reminder r: reminders)
		{
			dArray[s] = new DateSortReminder(r);
			s++;
		}
		Arrays.sort(dArray); 
		
		ArrayList<Reminder> reminders2 = new ArrayList<Reminder>();
		for (int i = 0; i < dArray.length; i++) {
			reminders2.add(i, dArray[i].getReminder());
		}
		/*
		 * int size = reminders.size(); for (int i = 0; i < size; i++) for (int
		 * j = i + 1; j < size; j++) if (reminders.get(i).getDate()
		 * .compareTo(reminders.get(j).getDate()) < 0) { Reminder tmp =
		 * reminders.get(i); reminders.add(i, reminders.get(j));
		 * reminders.add(j, tmp); }
		 */
	//	Log.d(TAG, "***** "+ reminders2.get(0).toString()+" and " + reminders2.get(1).toString()+ " *****");
		return reminders2;
	}
	private class DateSortReminder implements Comparable {
		private Reminder reminder;
		public Reminder getReminder() {
			return reminder;
		}
	    public DateSortReminder(Reminder reminder) {
	    this.reminder = reminder;	
	    }

	    @Override
	    public int compareTo(Object o) {
	        //сортировка 
	        DateSortReminder compared = (DateSortReminder) o;
	       
	        return (reminder.getDate().compareTo(compared.getReminder().getDate()));
	    }
	    
	}
}
