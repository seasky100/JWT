package com.bj.jwt;

import java.io.UnsupportedEncodingException;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity  {
 
	 // Debugging
    private static final String TAG = "jwt";
    private static final boolean D = true;
    public  boolean bFlagBluetoot=false;
    public  boolean bbluetootconn=false;

    // Message types sent from the BluetoothService Handler
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;

    // Key names received from the BluetoothService Handler
    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";

    // Intent request codes
    public static final int REQUEST_CONNECT_DEVICE = 1;
    public static final int REQUEST_ENABLE_BT = 2;
    
    // Name of the connected device
    public String mConnectedDeviceName = null;
    // String buffer for outgoing messages
    public StringBuffer mOutStringBuffer;
    // Local Bluetooth adapter
    public static BluetoothAdapter mBluetoothAdapter = null;
    // Member object for the services
    public static BluetoothService mService = null;
    
    
	 ImageButton btnlogin,btnpunish,btnquery;
	 TextView tvPoliceName,tvPoliceNum,tvPoliceTeam;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnpunish= (ImageButton)findViewById(R.id.allpunish);
        tvPoliceNum=(TextView)findViewById(R.id.policenum);
        
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // If the adapter is null, then Bluetooth is not supported
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
            //finish();
            //return;
            bFlagBluetoot=false;
        }
    }

    @Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		 if (mService != null) mService.stop();
	     if(D) Log.e(TAG, "--- ON DESTROY ---");
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	    if(D) Log.e(TAG, "+ ON RESUME +");

	        // Performing this check in onResume() covers the case in which BT was
	        // not enabled during onStart(), so we were paused to enable it...
	        // onResume() will be called when ACTION_REQUEST_ENABLE activity returns.
	        if (mService != null) {
	            // Only if the state is STATE_NONE, do we know that we haven't started already
	            if (mService.getState() == BluetoothService.STATE_NONE) {
	              // Start the Bluetooth services
	              mService.start();
	            }
	        }
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		// If BT is not on, request that it be enabled.
        // setupChat() will then be called during onActivityResult
		 if (!mBluetoothAdapter.isEnabled()) {
	            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
	            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        } 
		 else {
            if (mService == null) 
            	mService = new BluetoothService(this, mHandler);;
        }
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		if(D) Log.e(TAG, "-- ON STOP --");
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(D) Log.d(TAG, "onActivityResult " + resultCode);
        switch (requestCode) {
        case REQUEST_CONNECT_DEVICE:
            // When DeviceListActivity returns with a device to connect
            if (resultCode == Activity.RESULT_OK) {
                // Get the device MAC address
                String address = data.getExtras()
                                     .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
                // Get the BLuetoothDevice object
                BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
                // Attempt to connect to the device
                mService.connect(device);
            }
            break;
        case REQUEST_ENABLE_BT:
            // When the request to enable Bluetooth returns
            if (resultCode == Activity.RESULT_OK) {
                // Bluetooth is now enabled, so set up a session
            	bFlagBluetoot=true;
            	mService = new BluetoothService(this, mHandler);
            } else {
                // User did not enable Bluetooth or an error occured
                Log.d(TAG, "BT not enabled");
                Toast.makeText(MainActivity.this, R.string.bt_not_enabled_leaving, Toast.LENGTH_SHORT).show();
                bFlagBluetoot=false;
                finish();
            }
        }
	}
	// The Handler that gets information back from the BluetoothService
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case MESSAGE_STATE_CHANGE:
                if(D) Log.i(TAG, "MESSAGE_STATE_CHANGE: " + msg.arg1);
                switch (msg.arg1) {
                case BluetoothService.STATE_CONNECTED: 
                    Toast.makeText(MainActivity.this,R.string.title_connected_to, Toast.LENGTH_LONG).show();
                    //bbluetootconn=true;
                    break;
                case BluetoothService.STATE_CONNECTING:
                    Toast.makeText(MainActivity.this,R.string.title_connecting, Toast.LENGTH_LONG).show();
                    break;
                case BluetoothService.STATE_LISTEN:
                case BluetoothService.STATE_NONE:
                    Toast.makeText(MainActivity.this,R.string.title_not_connected, Toast.LENGTH_LONG).show();
                   // bbluetootconn=false;
                    break;
                }
                break;
            case MESSAGE_WRITE:
                //byte[] writeBuf = (byte[]) msg.obj;
                // construct a string from the buffer
                //String writeMessage = new String(writeBuf);
                break;
            case MESSAGE_READ:
                //byte[] readBuf = (byte[]) msg.obj;
                // construct a string from the valid bytes in the buffer
                //String readMessage = new String(readBuf, 0, msg.arg1);
                break;
            case MESSAGE_DEVICE_NAME:
                // save the connected device's name
                mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
                Toast.makeText(getApplicationContext(), "Connected to "
                               + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
                break;
            case MESSAGE_TOAST:
                Toast.makeText(getApplicationContext(), msg.getData().getString(TOAST),
                               Toast.LENGTH_SHORT).show();
                break;
            }
        }
    };
   public static boolean sendprintinfo(String message)
    {
	   if (mService.getState() != BluetoothService.STATE_CONNECTED) {
          // Toast.makeText(MainActivity.this, R.string.not_connected, Toast.LENGTH_SHORT).show();
           return false;
       }

       // Check that there's actually something to send
       if (message.length() > 0) {
           // Get the message bytes and tell the BluetoothService to write
           byte[] send;            
           try{
           	send = message.getBytes("GB2312");
           }
           catch(UnsupportedEncodingException e)
           {
           	send = message.getBytes();
           }
           mService.write(send);
       }
   return true;
    }
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
	
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
    	 switch (item.getItemId()) {
         case R.id.scan:
             // Launch the DeviceListActivity to see devices and do scan
             Intent serverIntent = new Intent(this, DeviceListActivity.class);
             startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
             return true;
         case R.id.disconnect:
             // disconnect
         	mService.stop();
             return true;
         }
         return false;
		//return super.onOptionsItemSelected(item);
	}

	/*登录*/
    public void OnClickLogin(View v)
    {
    	
    	Intent intent=new Intent(MainActivity.this,LoginActivity.class);
    	startActivity(intent);
    	//MainActivity.this.startActivity(intent);
    }
    /*执法*/
    public void OnClickPunish(View v)
    {
    	
    	 if (mService.getState() != BluetoothService.STATE_CONNECTED) {
              Toast.makeText(MainActivity.this, R.string.not_connected, Toast.LENGTH_SHORT).show();
             // return ;
          }
    	 
    	String spolicenum;
    	spolicenum=tvPoliceNum.getText().toString();
    
    	if(spolicenum.equals("00000"))
    	{
    		new AlertDialog.Builder(MainActivity.this)  
        	
            .setTitle("标题")

            .setMessage("没有登录警员")

            .setPositiveButton("确定", null)

            .show();
    		return;
    	}
    	Intent intent = new Intent(MainActivity.this,AllpunishActivity.class);
    	startActivity(intent);
    	
    }
    //查询
    public void OnClickQuery(View v)
    {
    	String spolicenum;
    	spolicenum=tvPoliceNum.getText().toString();
    
    	if(spolicenum.equals("00000"))
    	{
    		new AlertDialog.Builder(MainActivity.this)  
        	
            .setTitle("标题")

            .setMessage("没有登录警员")

            .setPositiveButton("确定", null)

            .show();
    		return;
    	}
    	Intent intent = new Intent(MainActivity.this,AllQueryActivity.class);
    	startActivity(intent);
    }
    //核录
    public void OnClickCheck(View v)
    {
    	new AlertDialog.Builder(MainActivity.this)  
    	
        .setTitle("标题")

        .setMessage("暂无此功能")

        .setPositiveButton("确定", null)

        .show();
    }
    //日志
    public void OnClickWorklog(View v)
    {
    	
    	Intent intent = new Intent(MainActivity.this,WorklogActivity.class);
    	startActivity(intent);
    }
    //工具
    public void OnClickTool(View v)
    {
    	new AlertDialog.Builder(MainActivity.this)  
    	
        .setTitle("标题")

        .setMessage("暂无此功能")

        .setPositiveButton("确定", null)

        .show();
    }
    //退出
    public void OnClickLogout(View v)
    {
    	
    	AlertDialog dialog = new AlertDialog.Builder(
				MainActivity.this)
				.setTitle("提示")
				.setMessage("是否退出移动警务？")
				.setPositiveButton("返回", new DialogInterface.OnClickListener()
				{

					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						// TODO Auto-generated method stub
						return;
					}
				})
				.setNegativeButton("退出", new DialogInterface.OnClickListener()
				{

					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						// TODO Auto-generated method stub
						
						MainActivity.this.finish();
					}
				}).create();
		dialog.show();
    }
  
    
}
