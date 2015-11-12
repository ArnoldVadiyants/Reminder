package com.example.reminder;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

public class ReminderIntentJSONSerializer {
	private Context mContext;
	private String mFilename;

	public ReminderIntentJSONSerializer(Context c, String f) {
		mContext = c;
		mFilename = f;
	}
	
	public ArrayList<Reminder> loadReminders() throws IOException, JSONException {
		ArrayList<Reminder> reminders = new ArrayList<Reminder>();
		BufferedReader reader = null;
		try {
		// �������� � ������ ����� � StringBuilder
		InputStream in = mContext.openFileInput(mFilename);
		reader = new BufferedReader(new InputStreamReader(in));
		StringBuilder jsonString = new StringBuilder();
		String line = null;
		while ((line = reader.readLine()) != null) {
		// Line breaks are omitted and irrelevant
		jsonString.append(line);
		}
		// ������ JSON � �������������� JSONTokener
		JSONArray array = (JSONArray) new JSONTokener(jsonString.toString())
		.nextValue();
		for (int i = 0; i < array.length(); i++) {
			reminders.add(new Reminder(array.getJSONObject(i)));
			}
			} catch (FileNotFoundException e) {
			// ���������� ��� ������ "� ����"; �� ��������� ��������
			} finally {
			if (reader != null)
			reader.close();
			}
			return reminders;
			}

	public void saveReminders(ArrayList<Reminder> reminders)
			throws JSONException, IOException {
		// ���������� ������� � JSON
		JSONArray array = new JSONArray();
		
		for (Reminder r : reminders)
		{
			if((r.getTitle().trim()).equals(""))
			{
				r.setTitle(Reminder.EMPTY_TITLE);
			}
			array.put(r.toJSON());
		}
		// ������ ����� �� ����
		Writer writer = null;
		try {
			OutputStream out = mContext.openFileOutput(mFilename,
					Context.MODE_PRIVATE);
			writer = new OutputStreamWriter(out);
			writer.write(array.toString());
		} finally {
			if (writer != null)
				writer.close();
		}
	}
	public JSONArray loadSnoozedRemindersID () throws IOException, JSONException {
	
		BufferedReader reader = null;
		JSONArray array = new JSONArray();
		try {
		// �������� � ������ ����� � StringBuilder
		InputStream in = mContext.openFileInput(mFilename);
		reader = new BufferedReader(new InputStreamReader(in));
		StringBuilder jsonString = new StringBuilder();
		String line = null;
		while ((line = reader.readLine()) != null) {
		// Line breaks are omitted and irrelevant
		jsonString.append(line);
		}
		// ������ JSON � �������������� JSONTokener
		array = (JSONArray) new JSONTokener(jsonString.toString())
		.nextValue();
		
			} catch (FileNotFoundException e) {
			// ���������� ��� ������ "� ����"; �� ��������� ��������
			} finally {
			if (reader != null)
			reader.close();
			}
			return array;
			}
	public void saveSnoozedRemindersID(JSONArray array)
			throws JSONException, IOException {
		Writer writer = null;
		try {
			OutputStream out = mContext.openFileOutput(mFilename,
					Context.MODE_PRIVATE);
			writer = new OutputStreamWriter(out);
			writer.write(array.toString());
		} finally {
			if (writer != null)
				writer.close();
		}
	}
	public JSONObject loadSettings() throws IOException, JSONException
	{
		
		BufferedReader reader = null;
		JSONObject json = new JSONObject();
		try {
		// �������� � ������ ����� � StringBuilder
		InputStream in = mContext.openFileInput(mFilename);
		reader = new BufferedReader(new InputStreamReader(in));
		String jsonString = new String();
		String line = null;
		if((line = reader.readLine()) != null) {
	
		jsonString = line;
		}
		 json =  new JSONObject(jsonString);//  (JSONArray) new JSONTokener(jsonString.toString())
		//.nextValue();
		
			} catch (FileNotFoundException e) {
			// ���������� ��� ������ "� ����"; �� ��������� ��������
			} finally {
			if (reader != null)
			reader.close();
			}
		return json;
		
	}
	public void saveSettings(JSONObject json)
			throws JSONException, IOException {
		Writer writer = null;
		try {
			OutputStream out = mContext.openFileOutput(mFilename,
					Context.MODE_PRIVATE);
			writer = new OutputStreamWriter(out);
			writer.write(json.toString());
		} finally {
			if (writer != null)
				writer.close();
		}
	}
}