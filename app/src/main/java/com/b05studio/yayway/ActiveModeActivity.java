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
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.squareup.picasso.Picasso;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ActiveModeActivity extends AppCompatActivity {


    private static final int REQUEST_CODE_LOC = 1009;

    private static final String TAG = "ActiveModeActivity";
    private boolean checkMode = true;
    AppBarLayout appBarLayout;
    ConstraintLayout constraintLayout;
    private BluetoothAdapter mBluetoothAdapter = null;
    private BluetoothSwitchService bluetoothSwitch = null;


    public boolean downFlag = false;
    public boolean checkFlag = true;
    int count = 0;


    ImageView modeBackImgeView;
    ImageView longImageView;
    TextView yaywayLogoTextView;
    TextView wayTextView;
    TextView makeYourTextView;
    CollapsingToolbarLayout collapsingToolbar;
    Button button;
    Button button2;

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

        if (bluetoothSwitch == null) {
            bluetoothSwitch = new BluetoothSwitchService(this, mHandHandler);
        }

       // IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
       // this.registerReceiver(bloothReceiver, filter);
       // filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
       // this.registerReceiver(bloothReceiver, filter);

       // bluetoothSwitch.connect(bluetoothDevice,true);
        BluetoothDevice bluetoothDevice = mBluetoothAdapter.getRemoteDevice("98:D3:32:30:C8:1B");
        bluetoothSwitch.connect(bluetoothDevice,true);


      //  mBluetoothAdapter.startDiscovery();

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
                   //Toast.makeText(getApplicationContext(),readMessage,Toast.LENGTH_SHORT).show();
                    Log.d("readMessage",readMessage);

                    if(readMessage.equals("1")) {
                        checkMode = !checkMode;
                    }

                    if(checkMode) {
                        modeBackImgeView.setImageResource(R.drawable.back);
                        Window window = getWindow();
                        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                        window.setStatusBarColor(Color.parseColor("#FA6B58"));
                        Picasso.with(getApplicationContext())
                                .load(R.drawable.ic_record_long).into(longImageView);

                        constraintLayout.setBackgroundResource(R.drawable.ic_constraint);
                        wayTextView.setPadding(0,0,0,0);

                        YoYo.with(Techniques.FadeIn).duration(500).playOn(findViewById(R.id.makeYourTextView));
                        YoYo.with(Techniques.FadeIn).duration(500).playOn(findViewById(R.id.wayTextView));
                        YoYo.with(Techniques.FadeIn).duration(500).playOn(findViewById(R.id.logoTextview));
                        yaywayLogoTextView.setText("active");


                    } else {
                        modeBackImgeView.setImageResource(R.drawable.back_speedy);
                        Window window = getWindow();
                        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                        window.setStatusBarColor(Color.parseColor("#3BC8E0"));
                        Picasso.with(getApplicationContext())
                                .load(R.drawable.ic_record_long2).into(longImageView);

                        constraintLayout.setBackgroundResource(R.drawable.ic_rect);
                        wayTextView.setPadding(70,0,0,0);

                        YoYo.with(Techniques.FadeIn).duration(500).playOn(findViewById(R.id.makeYourTextView));
                        YoYo.with(Techniques.FadeIn).duration(500).playOn(findViewById(R.id.wayTextView));
                        YoYo.with(Techniques.FadeIn).duration(500).playOn(findViewById(R.id.logoTextview));
                        yaywayLogoTextView.setText("speedy");

                    }
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



    private final BroadcastReceiver bloothReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {

                BluetoothDevice bluetoothDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                Log.d("ddddd",bluetoothDevice.getName());
                if(bluetoothDevice.getName().contains("NOWNIZ")) {

                    bluetoothSwitch.connect(bluetoothDevice,true);
                    mBluetoothAdapter.cancelDiscovery();

                }
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) { // 검색이 끝났을때!
              //  Toast.makeText(getApplicationContext(), "기기 검색 완료.", Toast.LENGTH_LONG).show();
            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        boolean flag = getIntent().getBooleanExtra("activityFlag",false);
        if(flag) {
            //overridePendingTransition( R.anim.slide_in_down,R.anim.slide_out_down);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_mode);



        appBarLayout = (AppBarLayout) findViewById(R.id.activeModeTabbar);
        constraintLayout = (ConstraintLayout) findViewById(R.id.activeModeTabLayout);
        constraintLayout.bringToFront();
        constraintLayout.setVisibility(View.INVISIBLE);
        constraintLayout.setBackgroundResource(R.drawable.ic_constraint);
       // constraintLayout.bringToFront();
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                if (Math.abs(verticalOffset)-appBarLayout.getTotalScrollRange() == 0)
                {
                    constraintLayout.setVisibility(View.VISIBLE);
                    checkFlag = false;
                    button.setVisibility(View.VISIBLE);
                   // downFlag = true;


                    //constraintLayout.setVisibility(View.VISIBLE);
                }
                else
                {
                    constraintLayout.setVisibility(View.INVISIBLE);
                    checkFlag = true;
                    button.setVisibility(View.GONE);
                    //button.setOnTouchListener(new View.OnTouchListener() {
                    //    @Override
                    //    public boolean onTouch(View v, MotionEvent event) {
                    //        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //
                    //            return true;
                    //        }
                    //        return false;
                    //    }
                    //});

                 // if(downFlag) {
                 //
                 //
                 //
                 //
                 //
                 //
                 //
                 //
                 //
                 //
                 //     downFlag = false;
                 // }

                   // constraintLayout.setVisibility(View.GONE);
                 //   Log.d("으으어어","응어어어1");
                }
            }
        });

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(Color.parseColor("#FA6B58"));

        longImageView = (ImageView) findViewById(R.id.longRecordImageView);
        Picasso.with(this).load(R.drawable.ic_record_long).into(longImageView);

        makeYourTextView = (TextView) findViewById(R.id.makeYourTextView);
        yaywayLogoTextView = (TextView) findViewById(R.id.logoTextview);
        wayTextView = (TextView) findViewById(R.id.wayTextView);

        modeBackImgeView = (ImageView) findViewById(R.id.imageView01);
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarLayout01);
        button= (Button)findViewById(R.id.goPhylayoutHiddenButton);
        button2= (Button)findViewById(R.id.goBeforeRidingHiddebutton);
        button2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if(checkFlag) {
                        startActivity(new Intent(ActiveModeActivity.this, ActiveBeforeRideModeActivity.class));
                        return true;
                    }
                }
                return false;
            }
        });

        initBluetooth();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBluetoothAdapter != null) {
            mBluetoothAdapter.cancelDiscovery();
        }
        this.unregisterReceiver(bloothReceiver);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


  // public void ic_hidden_button_click(View view) {
  //     if(checkFlag) {
  //         overridePendingTransition(0,0);
  //         startActivity(new Intent(this, ActiveBeforeRideModeActivity.class));
  //         overridePendingTransition(0,0);
  //         finish();
  //     }
  // }

    public void ic_hidden_list_button(View view) {
        overridePendingTransition(0,0);
        startActivity(new Intent(this,PhysicsLayout.class));
        overridePendingTransition(0,0);
    }

    public void temp(View view) {
        checkMode = !checkMode;
        if(checkMode) {
            modeBackImgeView.setImageResource(R.drawable.back);
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.parseColor("#FA6B58"));
            Picasso.with(getApplicationContext())
                    .load(R.drawable.ic_record_long).into(longImageView);

            constraintLayout.setBackgroundResource(R.drawable.ic_constraint);
            wayTextView.setPadding(0,0,0,0);

            YoYo.with(Techniques.FadeIn).duration(500).playOn(findViewById(R.id.makeYourTextView));
            YoYo.with(Techniques.FadeIn).duration(500).playOn(findViewById(R.id.wayTextView));
            YoYo.with(Techniques.FadeIn).duration(500).playOn(findViewById(R.id.logoTextview));
            yaywayLogoTextView.setText("active");


        } else {
            modeBackImgeView.setImageResource(R.drawable.back_speedy);
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.parseColor("#3BC8E0"));
            Picasso.with(getApplicationContext())
                    .load(R.drawable.ic_record_long2).into(longImageView);

            constraintLayout.setBackgroundResource(R.drawable.ic_rect);
            wayTextView.setPadding(70,0,0,0);

            YoYo.with(Techniques.FadeIn).duration(500).playOn(findViewById(R.id.makeYourTextView));
            YoYo.with(Techniques.FadeIn).duration(500).playOn(findViewById(R.id.wayTextView));
            YoYo.with(Techniques.FadeIn).duration(500).playOn(findViewById(R.id.logoTextview));
            yaywayLogoTextView.setText("speedy");

        }
    }
}
