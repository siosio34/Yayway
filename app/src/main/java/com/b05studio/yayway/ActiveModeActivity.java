package com.b05studio.yayway;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ActiveModeActivity extends AppCompatActivity {

    private static final String TAG = "ActiveModeActivity";
    AppBarLayout appBarLayout;
    ConstraintLayout constraintLayout;
    private BluetoothAdapter mBluetoothAdapter = null;
    private BluetoothSwitchService bluetoothSwitch = null;

    private void initBluetooth() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        if (!mBluetoothAdapter.isEnabled()) {
            mBluetoothAdapter.enable();
        }

        mBluetoothAdapter.startDiscovery();

        if (bluetoothSwitch == null) {
            bluetoothSwitch = new BluetoothSwitchService(this, mHandHandler);
            Log.d(TAG,"bluetoothSwitch 생성됨.");
        }
    }

    private final Handler mHandHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Context context = getApplicationContext();
            switch (msg.what) {
                case BluetoothConstants.MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case BluetoothSwitchService.STATE_CONNECTED:
                            break;
                        case BluetoothSwitchService.STATE_CONNECTING:
                            break;
                        case BluetoothSwitchService.STATE_LISTEN:
                        case BluetoothSwitchService.STATE_NONE:
                            break;
                    }
                    break;
                case BluetoothConstants.MESSAGE_WRITE:
                    byte[] writeBuf = (byte[]) msg.obj;
                    // construct ball3_timeview string from the buffer
                    String writeMessage = new String(writeBuf);
                    Log.d(TAG, writeMessage);
                    break;
                case BluetoothConstants.MESSAGE_READ:
                    byte[] readBuf = (byte[]) msg.obj;
                    String readMessage = new String(readBuf, 0, msg.arg1);
                    Toast.makeText(getApplicationContext(),readMessage,Toast.LENGTH_SHORT).show();
                    break;
                case BluetoothConstants.MESSAGE_DEVICE_NAME:
                    break;
                case BluetoothConstants.MESSAGE_TOAST:
                    if (null != context) {
                        Toast.makeText(context, msg.getData().getString(BluetoothConstants.TOAST),
                                Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };

    private void connectDevice(boolean secure) {


       // String name =  data.getExtras()
       //         .getString(BluetoothSearchActivity.EXTRA_DEVICE_NAME);
//
       // String address = data.getExtras()
       //         .getString(BluetoothSearchActivity.EXTRA_DEVICE_ADDRESS);
//
       // Toast.makeText(this, address, Toast.LENGTH_SHORT).show();
       // BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
//
       // if(name.contains("RESIGHT")) {
       //
       // }
    }

    private final BroadcastReceiver bloothReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice bluetoothDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (bluetoothDevice.getBondState() != BluetoothDevice.BOND_BONDED) {
                    Log.d("이름 및 주소",bluetoothDevice.getName() + " " + bluetoothDevice.getAddress());
                }

            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) { // 검색이 끝났을때!
                Toast.makeText(getApplicationContext(), "기기 검색 완료.", Toast.LENGTH_LONG).show();
            }
        }
    };

    private void temp() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "이 기종은 블루투스를 지원하지 않아 Resight와 사용할 수 없습니다.", Toast.LENGTH_LONG).show();
            finish();
        }

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        this.registerReceiver(bloothReceiver, filter);
        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        this.registerReceiver(bloothReceiver, filter);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_mode);

        initBluetooth();



        appBarLayout = (AppBarLayout) findViewById(R.id.activeModeTabbar);
       // constraintLayout = (ConstraintLayout) findViewById(R.id.activeModeTabLayout);
       // constraintLayout.bringToFront();
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                if (Math.abs(verticalOffset)-appBarLayout.getTotalScrollRange() == 0)
                {
                    //constraintLayout.setVisibility(View.VISIBLE);

                   // Log.d("으으어어","응어어어##");
                    // TODO: 2017-08-15 작업하면댐오 ㅋㅋ
                }
                else
                {
                   // constraintLayout.setVisibility(View.GONE);
                 //   Log.d("으으어어","응어어어1");
                }
            }
        });

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(Color.parseColor("#FA6B58"));

        ImageView imageView = (ImageView) findViewById(R.id.longRecordImageView);
        Picasso.with(this).load(R.drawable.ic_record_long).into(imageView);

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


}
