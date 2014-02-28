package com.example.rpiandroidapp;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * Represents the first activity a user sees. The user enters the IP address
    of the server running on the Raspberry Pi and the port number that the
    server is listening at.
    
    Presents a simple UI providing text views for the user to provide the IP-address
    and port number as well as a button whose action method attempts to connect to the server.
    If the connection is successful, the second activity starts, otherwise it displays an error message
    and asks the user to re-enter the IP address and port number.
 */
public class MainActivity extends Activity {
	
	public final static String EXTRA_IP = "com.example.AndroidSYE.IP";
	public final static String EXTRA_PORT = "com.example.AndroidSYE.PORT";
	
	/** Initialize the activity */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState); // specify the layout resource defining the UI
	    setContentView(R.layout.activity_main);
	}
	
        @Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu; this adds items to the action bar if it is present.
	    getMenuInflater().inflate(R.menu.main, menu);
	    return true;
	}
	    
        /** Called when the user clicks the Connect button */
        public void connect(View view) {
	    	Intent intent = new Intent(this, ButtonActivity.class);
	    	TextView ipText = (TextView) findViewById(R.id.ipAddress);
		TextView portText = (TextView) findViewById(R.id.portNumber);
		String ip = ipText.getText().toString();
		String port = portText.getText().toString();
		intent.putExtra(EXTRA_IP, ip);
		intent.putExtra(EXTRA_PORT, port);
	}
}
