package com.b05studio.yayway;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.VideoView;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ActiveYayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_yay);

        VideoView vidioview = (VideoView) findViewById(R.id.activeYayeModeVideoview);
        try {
            Uri path = Uri.parse("android.resource://" + getPackageName() + "/"+ R.raw.video_active_yay);
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
    }

    private void startActiveDuringRideActivity() {
        if(isFinishing())
            return ;

        startActivity(new Intent(this,PhysicsLayout.class));
        overridePendingTransition(0,0);
        finish();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


}
