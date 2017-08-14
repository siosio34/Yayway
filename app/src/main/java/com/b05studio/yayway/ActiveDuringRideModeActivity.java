package com.b05studio.yayway;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Chronometer;
import android.widget.VideoView;

import org.w3c.dom.Text;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ActiveDuringRideModeActivity extends AppCompatActivity {

    private Chronometer chronometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_during_ride_mode);
        VideoView vidioview = (VideoView) findViewById(R.id.activeDuringRideModeVideoview);
        try {
            Uri path = Uri.parse("android.resource://" + getPackageName() + "/"+ R.raw.video_during_riding);
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


        chronometer = (Chronometer) findViewById(R.id.chronometer);
        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                    long time = SystemClock.elapsedRealtime() - chronometer.getBase();
                    int h   = (int)(time /3600000);
                    int m = (int)(time - h*3600000)/60000;
                    int s= (int)(time - h*3600000- m*60000)/1000 ;
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
            return ;

        startActivity(new Intent(this,ActiveYayActivity.class));
        overridePendingTransition(0,0);
        finish();
    }

}
