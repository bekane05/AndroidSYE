package com.example.rpiandroidapp;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;

public class ButtonActivity extends Activity {
	
	private Socket socket;
	private PrintWriter out;
	// "10.32.95.167"; IP address of the RPi server

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_button);
		
		// Show the Up button in the action bar.
		setupActionBar();
		
		// Get the text from the intent
		Intent intent = getIntent();
		String ip = intent.getStringExtra(MainActivity.EXTRA_IP);
		int port = Integer.parseInt(intent.getStringExtra(MainActivity.EXTRA_PORT));
		
		new Thread(new ClientThread()).start();
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.button, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	/* Called when the user clicks one of the four directional buttons */
	public void buttonAction(View view) throws IOException {

		// Assigns the four directional buttons to Button objects
		Button upButton = (Button) findViewById(R.id.forwardButton);
		Button leftButton = (Button) findViewById(R.id.leftButton);
		Button rightButton = (Button) findViewById(R.id.rightButton);
		Button downButton = (Button) findViewById(R.id.reverseButton);

		// Interface definition for a callback to be invoked when a touch event is dispatched to this view
		// * Need separate onTouchListeners for each button
		upButton.setOnTouchListener(new OnTouchListener() {

			// Function called when a touch event is dispatched to a view
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				switch(event.getAction()){

					case MotionEvent.ACTION_DOWN:
						out.println("Up-P");
						return true;
					case MotionEvent.ACTION_MOVE:
						out.println("Up-P");
						break;
					case MotionEvent.ACTION_UP:
						out.println("Up-R");
						return true;
				}
				return false;
			}
		});

		leftButton.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				switch(event.getAction()){

					case MotionEvent.ACTION_DOWN:
						return true;
					case MotionEvent.ACTION_MOVE:
						out.println("Left-P");
						break;
					case MotionEvent.ACTION_UP:
						out.println("Left-R");
						return true;
				}
				return false;
			}
		});

		rightButton.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				switch(event.getAction()){

					case MotionEvent.ACTION_DOWN:
						return true;
					case MotionEvent.ACTION_MOVE:
						out.println("Right-P");
						break;
					case MotionEvent.ACTION_UP:
						out.println("Right-R");
						return true;
				}
				return true;
			}
		});

		downButton.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				switch(event.getAction()){

					case MotionEvent.ACTION_DOWN:
						return true;
					case MotionEvent.ACTION_MOVE:
						out.println("Down-P");
						break;
					case MotionEvent.ACTION_UP:
						out.println("Down-R");
						return true;
				}
				return false;
			}
		});
	}
    
    	/** Private inner class to set up ClientThread */
 	public class ClientThread implements Runnable {

 		/** Starting the thread causes the run method to be called in a separately executing thread.
 		    Function attempts to create a stream socket and connect it to the user-specified port number
 		    at the user-specified IP address */
 		@Override
        	public void run() {
        			
 			try {
 				/*
 				// get user-inputed IP address and port number from the TextViews
 				String ipAddr = SERVER_IP.getText().toString();
 				String portNumber = SERVER_PORT.getText().toString();
 				Integer portNum = Integer.parseInt(portNumber);
 				*/
 				
		                InetAddress serverAddr = InetAddress.getByName(ip);
		                socket = new Socket(serverAddr, port);

	        } catch (UnknownHostException e1) {
	                e1.printStackTrace();
	        } catch (IOException e1) {
	                e1.printStackTrace();
	        }
 			
 			try {
        			out = new PrintWriter(new BufferedWriter(
        				new OutputStreamWriter(socket.getOutputStream())),
        				true);

 			} catch (IOException e) {
 				System.out.println("Problem with establishing OutputStream");
 				e.printStackTrace();
 			}
 		}
 	}

}
