package com.example.reminder;

import java.util.ArrayList;
import java.util.UUID;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

public class ReminderPagerActivity extends FragmentActivity {
private ViewPager mViewPager;
private ArrayList<Reminder> mReminders;

@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		mViewPager = new ViewPager(this);
		mViewPager.setId(R.id.viewPager);
		setContentView(mViewPager);
		
		mReminders = ReminderLab.get(this).getReminders();
		
		FragmentManager fm = getSupportFragmentManager();
		setTitle(mReminders.get(0).getTitle());
		mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
			
			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return mReminders.size();
			}
			
			@Override
			public Fragment getItem(int arg0) {
				// TODO Auto-generated method stub
				Reminder reminder = mReminders.get(arg0);
				return ReminderFragment.newInstance(reminder.getId());
			}
		});

		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				Reminder reminder = mReminders.get(arg0);
				if(reminder.getTitle() != null)
				{
					setTitle(reminder.getTitle());
				}
			}
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {}
			@Override
			public void onPageScrollStateChanged(int arg0) {}
		});
		
		UUID reminderId = (UUID) getIntent().getSerializableExtra(
				ReminderFragment.EXTRA_REMINDER_ID);
		for (int i = 0; i < mReminders.size(); i++) {
			if (mReminders.get(i).getId().equals(reminderId)) {
				mViewPager.setCurrentItem(i);
				break;
			}
		}
		
	}
@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		ReminderLab.get(this).saveReminders();
	}
}
