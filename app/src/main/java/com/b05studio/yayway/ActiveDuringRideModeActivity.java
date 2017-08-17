package com.b05studio.yayway;

import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.Timer;
import java.util.TimerTask;

public class ActiveDuringRideModeActivity extends AppCompatActivity implements SensorEventListener {


    private Chronometer chronometer;

    private long lastTime;
    private float speed;
    private float lastX;
    private float lastY;
    private float lastZ;
    private float x, y, z;

    Timer timer;
    Timer timer2;
    private static final int SHAKE_THRESHOLD = 800;
    private static final int DATA_X = SensorManager.DATA_X;
    private static final int DATA_Y = SensorManager.DATA_Y;
    private static final int DATA_Z = SensorManager.DATA_Z;

    private boolean flag = true;

    private SensorManager sensorManager;
    private Sensor accelerormeterSensor;

    private TextView velocityView;

    private Handler mHandler;
    private Runnable mRunnable;

    VideoView vidioview;

    Timer jobScheduler;

    private int num = 0;
    int numArr[] = {7,8,9,10,11,12,13,14,15,16,17,18,19,20};
    private final Handler handler = new Handler();

    public static long timeSpend;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_during_ride_mode);
        vidioview = (VideoView) findViewById(R.id.activeDuringRideModeVideoview);
        velocityView = (TextView) findViewById(R.id.activeVelocityTextView);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(Color.parseColor("#213d53"));

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerormeterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        chronometer = (Chronometer) findViewById(R.id.chronometer);
        timeSpend = chronometer.getBase();

        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {

                    long time = SystemClock.elapsedRealtime() - timeSpend;
                    int h   = (int)(time /3600000);
                    int m = (int)(time - h*3600000)/60000;
                    int s= (int)(time - h*3600000- m*60000)/1000;
                    m = m + 12;

                    String hh = h < 10 ? "0"+h: h+"";
                    String mm = m < 10 ? "0"+m: m+"";
                    String ss = s < 10 ? "0"+s: s+"";
                    chronometer.setText(hh+" : "+mm+" : "+ss);
            }
        });

        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
    }


    private void startActiveDuringRideActivity() {

        if(isFinishing())
            return;

        overridePendingTransition(0,0);

        startActivity(new Intent(this,ActiveYayActivity.class));
        overridePendingTransition(0,0);

    }

    @Override
    public void onStart() {
        super.onStart();
        if (accelerormeterSensor != null)
            sensorManager.registerListener(this, accelerormeterSensor,
                    SensorManager.SENSOR_DELAY_GAME);

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                updateTextView();
            }
        }, 0, 200);

        timer2 = new Timer();
        timer2.schedule(new TimerTask() {
            @Override
            public void run() {
                updateTextView();
            }
        }, 2100, 600);

        try {
            Uri path = Uri.parse("android.resource://" + getPackageName() + "/"+ R.raw.video_during_riding);
            vidioview.setVideoURI(path);
            vidioview.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.seekTo(0);
                    mp.start();


                    //  startActiveDuringRideActivity();
                }
            });
            vidioview.start();

        }
        catch (Exception e) {
            Log.d("dddd","Dddd");
            startActiveDuringRideActivity();
        }


    }

    @Override
    public void onResume() {
        Log.d("dddd","Dddd");
        super.onResume();
        flag = true;

    }

    private void updateTextView() {
        Runnable updater = new Runnable() {
                public void run() {
                velocityView.setText(numArr[num] + "");
                    num++;
                    if(num == 9) {
                        timer.cancel();
                    } else if(num == 14) {
                        timer2.cancel();
                        num = 0;
                    }

            }
        };
        handler.post(updater);
    }


    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (sensorManager != null)
            sensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            long currentTime = System.currentTimeMillis();
            long gabOfTime = (currentTime - lastTime);
            if (gabOfTime > 100) {
                lastTime = currentTime;
                x = event.values[DATA_X];
                y = event.values[DATA_Y];
                z = event.values[DATA_Z];

                speed = Math.abs(x + y + z - lastX - lastY - lastZ) / gabOfTime * 10000;
                Log.d("speend",speed + "");

                if (speed > SHAKE_THRESHOLD) {
                    // 이벤트발생!!
                    if(flag)
                        startActiveDuringRideActivity();

                    flag = false;
                }

                lastX = event.values[DATA_X];
                lastY = event.values[DATA_Y];
                lastZ = event.values[DATA_Z];
            }

        }

    }
}
