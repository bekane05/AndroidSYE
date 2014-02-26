package com.example.rpiandroidapp;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

import com.example.rpiandroidapp.MainActivity.ClientThread;

public class ClientService extends Service {
	
	ClientThread client;
	
	// Binder given to clients
	private final IBinder mBinder = new ClientBinder();
	
	/**
       Class used for the client Binder.  Because we know this service always
       runs in the same process as its clients, we don't need to deal with IPC.
     */
	public class ClientBinder extends Binder {
		ClientService getService() {
			// Return this instance of ClientService so clients can call public methods
			return ClientService.this;
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		Toast.makeText(this, "Service Created", 300).show();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		Toast.makeText(this, "Service Destroyed", 300).show();
	}
	
	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		Toast.makeText(this, "Service Stared", 300).show();
	}
}
