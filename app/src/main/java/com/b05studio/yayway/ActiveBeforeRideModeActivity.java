package com.b05studio.yayway;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.VideoView;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ActiveBeforeRideModeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_ride_mode);
        VideoView vidioview = (VideoView) findViewById(R.id.splashActiveBeforeVideoView);
        try {
            Uri path = Uri.parse("android.resource://" + getPackageName() + "/"+ R.raw.video_before_riding);
            vidioview.setVideoURI(path);
            vidioview.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    startActiveDuringRideActivity();
                }
            });
            vidioview.start();
        }
        catch (Exception e) {
            startActiveDuringRideActivity();
        }

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(Color.parseColor("#213d53"));
    }

    private void startActiveDuringRideActivity() {
        if(isFinishing())
            return ;

        overridePendingTransition(0,0);
        startActivity(new Intent(this,ActiveDuringRideModeActivity.class));
        overridePendingTransition(0,0);
        finish();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }




}
