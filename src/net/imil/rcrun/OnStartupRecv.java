package net.imil.rcrun;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.File;
import java.util.Arrays;

public class OnStartupRecv extends BroadcastReceiver {
	
	private static final String BOOT_ACTION = "android.intent.action.BOOT_COMPLETED",
								TAG = "RcRunStartupRecv",
								shell = "/system/bin/sh";
	
	private void suexec(String rc) {
		Process p = null;
		DataOutputStream os = null;
		String cmd = shell + " " + rc + "\n";

		Log.d(TAG, "Executing: " + cmd);
		try {
			p = Runtime.getRuntime().exec("su");
			os = new DataOutputStream(p.getOutputStream());
			os.writeBytes(cmd);
			os.writeBytes("exit\n");  
	        os.flush();

	        try {
	        	p.waitFor();
	        } catch (InterruptedException e) {
	        	e.printStackTrace();
	        }
			Log.d(TAG, "exit code: " + p.exitValue());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private boolean rcdParse() {
		File f;
		String sdroot = Environment.getExternalStorageDirectory().toString();
		String rcd = sdroot + File.separator + "rc.d";
		
		f = new File(rcd);
		if (f.exists() == false) {
			Log.w(TAG, "rc.d directory does not exist, creating");
			f.mkdirs();
			return false;
		}
		File list[] = f.listFiles();
		Arrays.sort(list);
		for (int i = 0; i < list.length; i++) {
			suexec(rcd + File.separator + list[i].getName());
		}
		return true;
	}
	
	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		if (action.equalsIgnoreCase(BOOT_ACTION)) {
			Log.d(TAG, "Got BOOT_COMPLETED");
			if (rcdParse() == false) {
				Log.d(TAG, "no script to start");
			}
		}
	}
}
