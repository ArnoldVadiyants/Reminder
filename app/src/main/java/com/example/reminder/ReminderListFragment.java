package com.example.reminder;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;

import java.util.ArrayList;

public class ReminderListFragment extends ListFragment {
	private static final String TAG = "ReminderListFragment";
	private ArrayList<Reminder> mReminders;
	private boolean mSubtitleVisible;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		getActivity().setTitle(R.string.reminders_title);
		mReminders = ReminderLab.get(getActivity()).getReminders();
		ReminderAdapter adapter = new ReminderAdapter(getActivity(),mReminders, false);
		setListAdapter(adapter);
		setRetainInstance(true);
		mSubtitleVisible = false;
		Log.d(TAG, "onCreate");
	//	getActivity().startService(new Intent(getActivity(), ReminderService.class));
		ReminderService.setServiceAlarm(getActivity(), true);
		
	}

	@TargetApi(11)
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//View v = super.onCreateView(inflater, container, savedInstanceState);
		View v = inflater.inflate(R.layout.reminder_list_fagment, null);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			if (mSubtitleVisible) {
				getActivity().getActionBar().setSubtitle(R.string.subtitle);
			}
		}
		
		ListView listView = (ListView)v.findViewById(android.R.id.list);
		
		
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
// ����������� ���� ��� Froyo � Gingerbread
			registerForContextMenu(listView);
			} else {
			// ����������� ������ �������� ��� Honeycomb � ����
			listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
			listView.setMultiChoiceModeListener(new MultiChoiceModeListener() {
				
				@Override
				public boolean onPrepareActionMode(ActionMode mode, Menu menu) {return false;}
				@Override
				public void onDestroyActionMode(ActionMode mode) {}
				@Override
				public boolean onCreateActionMode(ActionMode mode, Menu menu) {
					MenuInflater inflater = mode.getMenuInflater();
					inflater.inflate(R.menu.reminder_list_item_context, menu);
					return true;
				}
				
				@Override
				public boolean onActionItemClicked(ActionMode mode,
						MenuItem item) {
					switch (item.getItemId()) {
					case R.id.menu_item_delete_reminder:
							ReminderAdapter adapter = (ReminderAdapter) getListAdapter();
						ReminderLab reminderLab = ReminderLab.get(getActivity());
						for (int i = adapter.getCount() - 1; i >= 0; i--) {
							if (getListView().isItemChecked(i)) {
								reminderLab.deleteReminder(adapter.getItem(i));
							}
						}
						mode.finish();
						adapter.notifyDataSetChanged();
						return true;
					default:
						return false;
					}

				}

				@Override
				public void onItemCheckedStateChanged(ActionMode mode, int position,
						long id, boolean checked) {
					// TODO Auto-generated method stub
					
				}
			});
			}
		
		return v;
	}
@Override
public void setEmptyText(CharSequence text) {
	// TODO Auto-generated method stub
	super.setEmptyText(text);
}
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.fragment_reminder_list, menu);
		/*MenuItem showSubtitle = menu.findItem(R.id.menu_item_show_subtitle);
		if (mSubtitleVisible && showSubtitle != null) {
		showSubtitle.setTitle(R.string.hide_subtitle);
		}*/
	}
	
	@TargetApi(11)
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.menu_item_new_remind:
			Reminder reminder = new Reminder();
			ReminderLab.get(getActivity()).addReminder(reminder);
			Intent i = new Intent(getActivity(), ReminderPagerActivity.class);
			i.putExtra(ReminderFragment.EXTRA_REMINDER_ID, reminder.getId());
			startActivityForResult(i, 0);
			return true;
		case R.id.menu_item_settings:
			Intent intent = new Intent(getActivity(), SettingsFragmentAcitivity.class);
			startActivity(intent);
			return true;
	/*	case R.id.menu_item_show_subtitle:
			if (getActivity().getActionBar().getSubtitle() == null) {
				getActivity().getActionBar().setSubtitle(R.string.subtitle);
				mSubtitleVisible = true;
				item.setTitle(R.string.hide_subtitle);
				} else {
				getActivity().getActionBar().setSubtitle(null);
				mSubtitleVisible = false;
				item.setTitle(R.string.show_subtitle);
				}
			return true;*/
			default:
			return super.onOptionsItemSelected(item);
		}
	}
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		getActivity().getMenuInflater().inflate(
				R.menu.reminder_list_item_context, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();
		int position = info.position;
		ReminderAdapter adapter = (ReminderAdapter) getListAdapter();
		Reminder reminder = adapter.getItem(position);
		switch (item.getItemId()) {
		case R.id.menu_item_delete_reminder:
			ReminderLab.get(getActivity()).deleteReminder(reminder);
			adapter.notifyDataSetChanged();
			return true;
		}
		return super.onContextItemSelected(item);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		Reminder reminder = ((ReminderAdapter) getListAdapter()).getItem(position);
		
		Intent intent  = new Intent(getActivity(), ReminderPagerActivity.class);
		intent.putExtra(ReminderFragment.EXTRA_REMINDER_ID, reminder.getId());
		startActivity(intent);

	}	

	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.d(TAG, "onResume");
		ReminderLab.get(getActivity()).sortReminders();
				((ReminderAdapter) getListAdapter()).notifyDataSetChanged();
	}
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		ReminderLab.get(getActivity()).saveReminders();
	}
	
}
