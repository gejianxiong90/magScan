package com.example.frank.magscan;

import android.app.Service;
import android.content.Intent;
import android.device.MagManager;
import android.os.IBinder;
import android.util.Log;


public class MainServer extends Service {

	private MagManager magManager;
	private MagReaderThread magReaderThread;
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub

		return null;
	}
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.e("服务销毁", "服务销毁");

		magManager = null;
		magReaderThread.stopMagReader();
		magReaderThread.interrupt();
		try {
			magReaderThread.join();
			Log.e("线程退出","退出线程");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		Log.e("服务启动", "服务启动");
		if(magReaderThread != null) {
			magReaderThread.stopMagReader();
			magReaderThread = null;
		}
		magManager = new MagManager();
		magReaderThread = new MagReaderThread("magScan");
		magReaderThread.start();
		return super.onStartCommand(intent, flags, startId);
	}
	private class MagReaderThread extends Thread {
		private boolean running = true;


		private boolean isValid;

		public MagReaderThread(String name) {
			super(name);
			running = true;
		}

		public void stopMagReader() {
			running = false;
		}

		public void run() {
			if (magManager != null) {
				int ret = magManager.open();
				if (ret != 0) {

					return;
				}
			}
			while (running) {
				int size = 0;
				if (magManager == null)
					return;
				int ret = magManager.checkCard();
				if (ret != 0) {
					try {
						Thread.sleep(600);
					} catch (Exception e) {
					}
					continue;
				} else {
					try {
						Thread.sleep(500);
					} catch (Exception e) {
					}
				}
				StringBuffer trackOne = new StringBuffer();
				byte[] stripInfo = new byte[1024];
				int allLen = magManager.getAllStripInfo(stripInfo);
				if (allLen > 0) {
					int len = stripInfo[1];
					if (len != 0)
						trackOne.append(" track1: " + new String(stripInfo, 2, len));
					int len2 = stripInfo[3 + len];
					if (len2 != 0)
						trackOne.append(" \ntrack2: " + new String(stripInfo, 4 + len, len2));
					int len3 = stripInfo[5 + len+len2];
					if (len3 != 0 && len3 < 1024)
						trackOne.append(" \ntrack3: " + new String(stripInfo, 6 + len + len2, len3));

					if(!trackOne.toString().equals("")) {
						Log.i("卡片信息",trackOne.toString());
					}
					trackOne = null;
				}
				try {
					Thread.sleep(800);
				} catch (Exception e) {
				}
			}

		}
	}



}
