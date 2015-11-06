package com.example.reminder;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public class SettingsFragmentAcitivity extends SingleFragmentActivity {
@Override
public void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);

}
	@Override
	protected Fragment createFragment() {
		// TODO Auto-generated method stub
		return new SettingsFragment();
	}

}
