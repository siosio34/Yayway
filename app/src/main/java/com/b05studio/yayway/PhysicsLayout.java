package com.b05studio.yayway;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.commit451.gitbal.Gimbal;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by young on 2017-08-07.
 */

public class PhysicsLayout extends AppCompatActivity {


    @Bind(R.id.physics_layout)
    PhysicsFlowLayout physicsLayout;

    SensorManager sensorManager;
    Sensor gravitySensor;
    Gimbal gimbal;

    ImageView timeImageView;
    CircleImageView circleImageView2;
    CircleImageView circleImageView3;
    CircleImageView circleImageView4;
    CircleImageView circleImageView5;
    public static File lastPictureImage;

    private final SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_GRAVITY) {
                gimbal.normalizeGravityEvent(event);
                physicsLayout.getPhysics().setGravity(-event.values[0], event.values[1]);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) { }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gimbal = new Gimbal(this);
        gimbal.lock();
        setContentView(R.layout.activity_physics);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        gravitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);

        ButterKnife.bind(this);
        ImageView imageView = (ImageView)findViewById(R.id.exitDetailRecordButton);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2017-08-14 위에서 아래로 내려가는

                Intent intent = new Intent(PhysicsLayout.this,ActiveModeActivity.class);
                intent.putExtra("activityFlag",true);
                startActivity(intent);
                finish();
                //overridePendingTransition(R.anim.slide_in_down, R.anim.hold);
            }
        });

        timeImageView = (ImageView) findViewById (R.id.physicsLayoutTimeView);
        circleImageView2 = (CircleImageView) findViewById(R.id.circle2);
        circleImageView3 = (CircleImageView) findViewById(R.id.circle3);
        circleImageView4 = (CircleImageView) findViewById(R.id.circle4);
        circleImageView5 = (CircleImageView) findViewById(R.id.circle5);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(Color.parseColor("#e5e5e5"));
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(sensorEventListener, gravitySensor, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(sensorEventListener);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public void ic_ball1_click(View view) {
        timeImageView.setImageResource(R.drawable.ball2_timeview);
        circleImageView2.setImageResource(R.drawable.ic_ball2);
        circleImageView3.setImageResource(R.drawable.ic_ball2_pic);
        circleImageView4.setImageResource(R.drawable.ic_ball3);
        circleImageView5.setImageResource(R.drawable.ic_ball4);
    }

    public void ic_ball2_click(View view) {
        timeImageView.setImageResource(R.drawable.ball2_timeview);
        circleImageView2.setImageResource(R.drawable.ic_ball2_pic);
        circleImageView3.setImageResource(R.drawable.ic_ball2);
        circleImageView4.setImageResource(R.drawable.ic_ball3);
        circleImageView5.setImageResource(R.drawable.ic_ball4);
    }

    public void ic_ball3_click(View view) {
        timeImageView.setImageResource(R.drawable.ball3_timeview);
        circleImageView2.setImageResource(R.drawable.ic_ball2);
        circleImageView3.setImageResource(R.drawable.ic_ball2);
        circleImageView4.setImageResource(R.drawable.ic_ball3_pic);
        circleImageView5.setImageResource(R.drawable.ic_ball4);

        //ic_ball4가 6콤보
    }

    public void ic_ball4_click(View view) {
        timeImageView.setImageResource(R.drawable.ball4_timeview);
        circleImageView2.setImageResource(R.drawable.ic_ball2);
        circleImageView3.setImageResource(R.drawable.ic_ball2);
        circleImageView4.setImageResource(R.drawable.ic_ball3);

       // Toast.makeText(getApplicationContext() , lastPictureImage.toString(),Toast.LENGTH_LONG).show();
        Picasso.with(getApplicationContext()).load(PhysicsLayout.lastPictureImage).into(circleImageView5, new Callback() {
            @Override
            public void onSuccess() {
                Log.d("success","success");
            }

            @Override
            public void onError() {
                circleImageView4.setImageResource(R.drawable.ic_ball4_pic);
            }
        });
    }



}
