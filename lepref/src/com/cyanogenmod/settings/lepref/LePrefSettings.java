/*
 *  LeEco Extras Settings Module for Resurrection Remix ROMs
 *  Made by @andr68rus 2017
 */

package com.cyanogenmod.settings.lepref;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.preference.SwitchPreference;
import android.provider.Settings;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import android.util.Log;
import android.os.SystemProperties;
import java.io.*;
import android.widget.Toast;  
import android.preference.ListPreference;

public class LePrefSettings extends PreferenceActivity implements OnPreferenceChangeListener {
	private static final boolean DEBUG = true;
	private static final String TAG = "LePref";
	private static final String ENABLE_QC_KEY = "qc_setting";
	private static final String ENABLE_HAL3_KEY = "hal3";
	private static final String ENABLE_SEMIIDLE_KEY = "semiidle";
	private static final String ENABLE_IMSSRV_KEY = "imssrv";
	private static final String ENABLE_DEVIDLE_KEY = "devidle";
	private static final String ENABLE_AUTOSUSPEND_KEY = "autosuspend";
	private static final String ENABLE_KILLTOPAPP_KEY = "killtopapp";

	private static final String ENABLE_AUTOSUSPEND_CPU0_KEY = "autosuspendcpu0";
	private static final String ENABLE_AUTOSUSPEND_CPU2_KEY = "autosuspendcpu2";

	private static final String ENABLE_IDLE_CPU0_KEY = "idlecpu0";
	private static final String ENABLE_IDLE_CPU2_KEY = "idlecpu2";

	private static final String ENABLE_WAKELOCK_BLOCKER_SCREENOFF_KEY = "wbsoff";
	private static final String ENABLE_WAKELOCK_BLOCKER_SCREENON_KEY = "wbson";

	private static final String AKT_KEY = "akt";

	private static final String QC_SYSTEM_PROPERTY = "persist.sys.le_fast_chrg_enable";
	private static final String HAL3_SYSTEM_PROPERTY = "persist.camera.HAL3.enabled";
	private static final String AKT_SYSTEM_PROPERTY = "persist.AKT.profile";
	private static final String SEMIIDLE_SYSTEM_PROPERTY = "persist.semiidle.enabled";
    	private static final String IMSSRV_SYSTEM_PROPERTY = "persist.ims.enabled";
    	private static final String DEVIDLE_SYSTEM_PROPERTY = "persist.devidle.enabled";
    	private static final String AUTOSUSPEND_SYSTEM_PROPERTY = "persist.autosuspend.enabled";
    	private static final String KILLTOPAPP_SYSTEM_PROPERTY = "persist.killtopapp.enabled";

    	private static final String AUTOSUSPEND_CPU0_SYSTEM_PROPERTY = "persist.autosuspend.cpu0";
    	private static final String AUTOSUSPEND_CPU2_SYSTEM_PROPERTY = "persist.autosuspend.cpu2";

    	private static final String IDLE_CPU0_SYSTEM_PROPERTY = "persist.deviceidle.cpu0";
    	private static final String IDLE_CPU2_SYSTEM_PROPERTY = "persist.deviceidle.cpu2";

    	private static final String WAKELOCK_BLOCKER_SCREENOFF_SYSTEM_PROPERTY = "persist.wblock.soff";
    	
	private static final String WAKELOCK_BLOCKER_SCREENON_SYSTEM_PROPERTY = "persist.wblock.son";

	private boolean mEnabled = false;


	private SwitchPreference mEnableQC;
	private SwitchPreference mEnableHAL3;
	private SwitchPreference mEnableSemiIdle;
	private SwitchPreference mEnableIms;
	private SwitchPreference mEnableDevIdle;
	private SwitchPreference mEnableAutoSuspend;
	private SwitchPreference mEnableKillTopApp;

	private SwitchPreference mEnableAutosuspendCpu0;
	private SwitchPreference mEnableAutosuspendCpu2;

	private SwitchPreference mEnableIdleCpu0;
	private SwitchPreference mEnableIdleCpu2;
	
	private SwitchPreference mWakelockBlockerScreenOn;
	private SwitchPreference mWakelockBlockerScreenOff;

	private ListPreference mAKT;

    private Context mContext;
    private SharedPreferences mPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.le_settings);
        mContext = getApplicationContext();
        
        mEnableQC = (SwitchPreference) findPreference(ENABLE_QC_KEY);
        mEnableQC.setChecked(SystemProperties.getBoolean(QC_SYSTEM_PROPERTY, false));
        mEnableQC.setOnPreferenceChangeListener(this);
                
        mEnableHAL3 = (SwitchPreference) findPreference(ENABLE_HAL3_KEY);
        mEnableHAL3.setChecked(SystemProperties.getBoolean(HAL3_SYSTEM_PROPERTY, false));
        mEnableHAL3.setOnPreferenceChangeListener(this);

        mEnableDevIdle = (SwitchPreference) findPreference(ENABLE_DEVIDLE_KEY);
        mEnableDevIdle.setChecked(SystemProperties.getBoolean(DEVIDLE_SYSTEM_PROPERTY, false));
        mEnableDevIdle.setOnPreferenceChangeListener(this);

        mEnableKillTopApp = (SwitchPreference) findPreference(ENABLE_KILLTOPAPP_KEY);
        mEnableKillTopApp.setChecked(SystemProperties.getBoolean(KILLTOPAPP_SYSTEM_PROPERTY, false));
        mEnableKillTopApp.setOnPreferenceChangeListener(this);

        mEnableSemiIdle = (SwitchPreference) findPreference(ENABLE_SEMIIDLE_KEY);
        mEnableSemiIdle.setChecked(SystemProperties.getBoolean(SEMIIDLE_SYSTEM_PROPERTY, false));
        mEnableSemiIdle.setOnPreferenceChangeListener(this);

        mEnableAutoSuspend = (SwitchPreference) findPreference(ENABLE_AUTOSUSPEND_KEY);
        mEnableAutoSuspend.setChecked(SystemProperties.getBoolean(AUTOSUSPEND_SYSTEM_PROPERTY, false));
        mEnableAutoSuspend.setOnPreferenceChangeListener(this);
        
        mEnableIms = (SwitchPreference) findPreference(ENABLE_IMSSRV_KEY);
        mEnableIms.setChecked(SystemProperties.getBoolean(IMSSRV_SYSTEM_PROPERTY, false));
        mEnableIms.setOnPreferenceChangeListener(this);


	/*
        mEnableAutosuspendCpu0 = (SwitchPreference) findPreference(ENABLE_AUTOSUSPEND_CPU0_KEY);
	mEnabled = SystemProperties.getInt(AUTOSUSPEND_CPU0_SYSTEM_PROPERTY,2) == 1 ?  true : false;
        mEnableAutosuspendCpu0.setChecked(mEnabled);
        mEnableAutosuspendCpu0.setOnPreferenceChangeListener(this);
        mEnableAutosuspendCpu0.setVisibility(View.GONE);

        mEnableAutosuspendCpu2 = (SwitchPreference) findPreference(ENABLE_AUTOSUSPEND_CPU2_KEY);
	mEnabled = SystemProperties.getInt(AUTOSUSPEND_CPU2_SYSTEM_PROPERTY,2) == 0 ?  true : false;
        mEnableAutosuspendCpu2.setChecked(mEnabled);
        mEnableAutosuspendCpu2.setOnPreferenceChangeListener(this);
        mEnableAutosuspendCpu2.setVisibility(View.GONE);

        mEnableIdleCpu0 = (SwitchPreference) findPreference(ENABLE_IDLE_CPU0_KEY);
	mEnabled = SystemProperties.getInt(IDLE_CPU0_SYSTEM_PROPERTY,2) == 1 ?  true : false;
        mEnableIdleCpu0.setChecked(mEnabled);
        mEnableIdleCpu0.setOnPreferenceChangeListener(this);
        mEnableIdleCpu0.setVisibility(View.GONE);

        mEnableIdleCpu2 = (SwitchPreference) findPreference(ENABLE_IDLE_CPU2_KEY);
	mEnabled = SystemProperties.getInt(IDLE_CPU2_SYSTEM_PROPERTY,2) == 0 ?  true : false;
        mEnableIdleCpu2.setChecked(mEnabled);
        mEnableIdleCpu2.setOnPreferenceChangeListener(this);
        mEnableIdleCpu2.setVisibility(View.GONE);
        */

        mWakelockBlockerScreenOn = (SwitchPreference) findPreference(ENABLE_WAKELOCK_BLOCKER_SCREENON_KEY);
        mWakelockBlockerScreenOn.setChecked(SystemProperties.getBoolean(WAKELOCK_BLOCKER_SCREENON_SYSTEM_PROPERTY, false));
        mWakelockBlockerScreenOn.setOnPreferenceChangeListener(this);

        mWakelockBlockerScreenOff = (SwitchPreference) findPreference(ENABLE_WAKELOCK_BLOCKER_SCREENOFF_KEY);
        mWakelockBlockerScreenOff.setChecked(SystemProperties.getBoolean(WAKELOCK_BLOCKER_SCREENOFF_SYSTEM_PROPERTY, false));
        mWakelockBlockerScreenOff.setOnPreferenceChangeListener(this);
        
        mAKT = (ListPreference) findPreference(AKT_KEY);
        mAKT.setValue(SystemProperties.get(AKT_SYSTEM_PROPERTY, "Stock"));
        mAKT.setOnPreferenceChangeListener(this);
        
        if (DEBUG) Log.d(TAG, "Initializating done");
    }

	// Control Quick Charge
    private void setEnableQC(boolean value) {
		if (DEBUG) Log.d(TAG, "QC Changed");
		if(value) {
			SystemProperties.set(QC_SYSTEM_PROPERTY, "1");
		} else {
			SystemProperties.set(QC_SYSTEM_PROPERTY, "0");
		}
		if (DEBUG) Log.d(TAG, "QC setting changed");
    }

    // Set AKT
    private void setAKT(String value) {
		try {
			Process su = Runtime.getRuntime().exec("su");
			DataOutputStream outputStream = new DataOutputStream(su.getOutputStream());
			outputStream.writeBytes("mount -o remount,rw /system\n");
			outputStream.writeBytes("cat /system/etc/lepref/AKT/" + value + " > /system/etc/init.d/99AKT\n");
			outputStream.writeBytes("chmod 777 /system/etc/init.d/99AKT\n");
			outputStream.writeBytes("/system/etc/init.d/99AKT\n");
			outputStream.writeBytes("mount -o remount,ro /system\n");
			outputStream.flush();
			outputStream.writeBytes("exit\n");
			outputStream.flush();
			su.waitFor();
		} catch(IOException e){
			Toast toast = Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT);
			toast.show();
		} catch(InterruptedException e){
			Toast toast = Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT);
			toast.show();
		}
		SystemProperties.set(AKT_SYSTEM_PROPERTY, value);
    }
   

    private void setProperty(String property, boolean value) {
	if(value) {
		SystemProperties.set(property, "1");
	} else {
			SystemProperties.set(property, "0");
	}
	if (DEBUG) Log.d(TAG, property + " setting changed");
    }

    private void setProperty(String property, int value) {
	String prop = "" + value;
	SystemProperties.set(property, prop);
	if (DEBUG) Log.d(TAG, property + " setting changed");
    }


    private void setProperty(String property, String value) {
	SystemProperties.set(property, value);
	if (DEBUG) Log.d(TAG, property + " setting changed");
    }

    // Control Camera2API
    private void setEnableHAL3(boolean value) {
	if(value) {
		SystemProperties.set(HAL3_SYSTEM_PROPERTY, "1");
	} else {
			SystemProperties.set(HAL3_SYSTEM_PROPERTY, "0");
	}
	if (DEBUG) Log.d(TAG, "HAL3 setting changed");
    }

    private void setEnableDevIdle(boolean value) {
	if(value) {
		SystemProperties.set(DEVIDLE_SYSTEM_PROPERTY, "1");
	} else {
		SystemProperties.set(DEVIDLE_SYSTEM_PROPERTY, "0");
	}
	if (DEBUG) Log.d(TAG, "DEVIDLE setting changed");
    }

    private void setEnableKillTopApp(boolean value) {
	if(value) {
		SystemProperties.set(KILLTOPAPP_SYSTEM_PROPERTY, "1");
	} else {
		SystemProperties.set(KILLTOPAPP_SYSTEM_PROPERTY, "0");
	}
	if (DEBUG) Log.d(TAG, "KILLTOPAPP setting changed");
    }

    private void setEnableAutoSuspend(boolean value) {
	if(value) {
		SystemProperties.set(AUTOSUSPEND_SYSTEM_PROPERTY, "1");
	} else {
		SystemProperties.set(AUTOSUSPEND_SYSTEM_PROPERTY, "0");
	}
	if (DEBUG) Log.d(TAG, "AUTOSUSPEND setting changed");
    }

    private void setEnableSemiIdle(boolean value) {
	if(value) {
		SystemProperties.set(SEMIIDLE_SYSTEM_PROPERTY, "1");
	} else {
		SystemProperties.set(SEMIIDLE_SYSTEM_PROPERTY, "0");
	}
	if (DEBUG) Log.d(TAG, "SEMIIDLE setting changed");
    }

    private void setEnableImsSrv(boolean value) {
	if(value) {
		SystemProperties.set(IMSSRV_SYSTEM_PROPERTY, "1");
	} else {
		SystemProperties.set(IMSSRV_SYSTEM_PROPERTY, "0");
	}
	if (DEBUG) Log.d(TAG, "IMSSRV setting changed");
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return false;
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        final String key = preference.getKey();
        boolean value;
        String strvalue;
        if (DEBUG) Log.d(TAG, "Preference changed.");
        if (ENABLE_QC_KEY.equals(key)) {
			value = (Boolean) newValue;
			mEnableQC.setChecked(value);
			setEnableQC(value);
			return true;
		} else if (ENABLE_HAL3_KEY.equals(key)) {
			value = (Boolean) newValue;
			mEnableHAL3.setChecked(value);
			setEnableHAL3(value);
			return true;
		} else if (ENABLE_SEMIIDLE_KEY.equals(key)) {
			value = (Boolean) newValue;
			mEnableSemiIdle.setChecked(value);
			setEnableSemiIdle(value);
			return true;
		} else if (ENABLE_DEVIDLE_KEY.equals(key)) {
			value = (Boolean) newValue;
			mEnableDevIdle.setChecked(value);
			setEnableDevIdle(value);
			return true;
		} else if (ENABLE_KILLTOPAPP_KEY.equals(key)) {
			value = (Boolean) newValue;
			mEnableKillTopApp.setChecked(value);
			setEnableKillTopApp(value);
			return true;
		} else if (ENABLE_AUTOSUSPEND_KEY.equals(key)) {
			value = (Boolean) newValue;
			mEnableAutoSuspend.setChecked(value);
			setEnableAutoSuspend(value);
			return true;
		} else if (ENABLE_IMSSRV_KEY.equals(key)) {
			value = (Boolean) newValue;
			mEnableIms.setChecked(value);
			setEnableImsSrv(value);
			return true;
		} else if (ENABLE_AUTOSUSPEND_CPU0_KEY.equals(key)) {
			value = (Boolean) newValue;
			mEnableAutosuspendCpu0.setChecked(value);
			if( value ) setProperty(AUTOSUSPEND_CPU0_SYSTEM_PROPERTY,"1");
			else setProperty(AUTOSUSPEND_CPU0_SYSTEM_PROPERTY,"2");
			return true;
		} else if (ENABLE_AUTOSUSPEND_CPU2_KEY.equals(key)) {
			value = (Boolean) newValue;
			mEnableAutosuspendCpu2.setChecked(value);
			if( value ) setProperty(AUTOSUSPEND_CPU2_SYSTEM_PROPERTY,"0");
			else setProperty(AUTOSUSPEND_CPU2_SYSTEM_PROPERTY,"2");
			return true;
		} else if (ENABLE_IDLE_CPU0_KEY.equals(key)) {
			value = (Boolean) newValue;
			mEnableIdleCpu0.setChecked(value);
			if( value ) setProperty(IDLE_CPU0_SYSTEM_PROPERTY,"1");
			else setProperty(IDLE_CPU0_SYSTEM_PROPERTY,"2");
			return true;
		} else if (ENABLE_IDLE_CPU2_KEY.equals(key)) {
			value = (Boolean) newValue;
			mEnableIdleCpu2.setChecked(value);
			if( value ) setProperty(IDLE_CPU2_SYSTEM_PROPERTY,"0");
			else setProperty(IDLE_CPU2_SYSTEM_PROPERTY,"2");
			return true;
		} else if (ENABLE_WAKELOCK_BLOCKER_SCREENOFF_KEY.equals(key)) {
			value = (Boolean) newValue;
			mWakelockBlockerScreenOff.setChecked(value);
			setProperty(WAKELOCK_BLOCKER_SCREENOFF_SYSTEM_PROPERTY,value);
			return true;
		} else if (ENABLE_WAKELOCK_BLOCKER_SCREENON_KEY.equals(key)) {
			value = (Boolean) newValue;
			mWakelockBlockerScreenOn.setChecked(value);
			setProperty(WAKELOCK_BLOCKER_SCREENON_SYSTEM_PROPERTY,value);
			return true;
		} else if (AKT_KEY.equals(key)) {
			strvalue = (String) newValue;
			//mEnableHAL3.setChecked(value);
			//setEnableHAL3(value);
			if (DEBUG) Log.d(TAG, "AKT setting changed: " + strvalue);
			setAKT(strvalue);
			return true;
		}
          
          
        return false;
    }

}

