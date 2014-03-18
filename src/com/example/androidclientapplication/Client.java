package com.example.androidclientapplication;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ResourceBundle.Control;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/** Main Activity

    Represents the first activity a user sees. The user enters the IP-address
    of the server running on the Raspberry Pi and the port number that the
    server is listening at.

    Presents a simple UI providing text views for the user to provide the IP-address
    and port number as well as a button whose action method attempts to connect to the server.
    If connection is successful, the second activity starts, otherwise displays an error message
    and asks the user to re-enter the IP-address and port number.

 */
public class Client extends Activity{

	private Socket socket;
	private PrintWriter out;
	private static int SERVERPORT;
	private static String SERVER_IP;
	private Thread connection;
	private static final String TAG = "ClientActivity"; 
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		if(savedInstanceState != null){
			SERVER_IP = savedInstanceState.getString("IP");
			SERVERPORT = savedInstanceState.getInt("PORT");
		}else{		
			Intent intent = getIntent();
			SERVER_IP = intent.getStringExtra("ServerIP");
			SERVERPORT = intent.getIntExtra("ServerPort", 0);
		}
		connection = new Thread(new ClientThread());
		connection.start();
	}


	public void onSaveInstanceState(Bundle savedInstanceState){
		super.onSaveInstanceState(savedInstanceState);
		Log.i(TAG, "onSaveInstanceState");
		savedInstanceState.putInt("PORT", SERVERPORT);
		savedInstanceState.putString("IP", SERVER_IP);
	}
	
	public void disconnectAction(View view) throws IOException{
		Button disconnectButton = (Button) findViewById(R.id.disconnectButton);
		disconnectButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try {
					out.println("close");
					out.close();
					socket.close();
					connection.stop();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
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
	
	
	
	// Private inner class to set up ClientThread
	
	/** Starting the thread causes the run method to be called in a separately executing thread.
	    Function attempts to create a stream socket and connect it to the user-specified port number
	    at the user-specified IP address */
	private class ClientThread implements Runnable {

		/** Starting the thread causes the run method to be called in a separately executing thread.
		    Function attempts to creates a stream socket and connect it to the user-specified port number
		    at the user-specified IP address */
		@Override
		public void run() {

			try {
				InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
				socket = new Socket(serverAddr, SERVERPORT);

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
		/*
		public void disconnect() throws IOException{
			out.println("close");
			out.close();
			socket.close();
		}*/
	}
}
