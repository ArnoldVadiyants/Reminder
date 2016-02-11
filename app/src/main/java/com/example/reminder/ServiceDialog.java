package com.example.reminder;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v4.app.Fragment;
import android.view.Window;
import android.view.WindowManager;

public class ServiceDialog extends SingleFragmentActivity

{
	public static boolean sIsCreated = false;
	private static final String TAG = "ServiceDialog";
	private PowerManager.WakeLock mWakeLock;
@TargetApi(11)
@Override
public void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
/*	 KeyguardManager kgm = (KeyguardManager)getSystemService(Context.KEYGUARD_SERVICE);
	    boolean isKeyguardUp = kgm.inKeyguardRestrictedInputMode();
	    KeyguardLock kgl = kgm.newKeyguardLock("Your Activity/Service name");

	    if(isKeyguardUp){
	    kgl.disableKeyguard();
	    isKeyguardUp = false;
	    }
	*/
	 
	Window window = getWindow();
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
			| WindowManager.LayoutParams.FLAG_FULLSCREEN
            | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD 
            | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
            | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
	super.onCreate(savedInstanceState);
	if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
		setFinishOnTouchOutside(false);
	}


}
	@Override
	protected Fragment createFragment() {
		// TODO Auto-generated method stub
	
		return new ReminderDialogFragment();
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		
		return;
	}
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		sIsCreated = true;
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		sIsCreated = false;
	}
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		sIsCreated = true;
	}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		sIsCreated = false;
	}
	
}
