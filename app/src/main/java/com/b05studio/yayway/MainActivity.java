package com.b05studio.yayway;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onStart() {
        super.onStart();
        final VideoView vidioview = (VideoView) findViewById(R.id.splashVideoView);
        try {
            Uri path = Uri.parse("android.resource://" + getPackageName() + "/"+ R.raw.splash_new_mini);
            Log.d("path",path.toString());
            vidioview.setVideoURI(path);
            vidioview.requestFocus();
            vidioview.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    startNextActivity();
                }
            });
            vidioview.start();
        }
        catch (Exception e) {
            startNextActivity();
        }
    }

    private void startNextActivity() {
        if(isFinishing())
            return ;

        startActivity(new Intent(this,ActiveBeforeRideModeActivity.class));
        overridePendingTransition(0,0);
        finish();
    }
}
