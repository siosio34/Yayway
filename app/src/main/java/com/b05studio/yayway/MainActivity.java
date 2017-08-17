package com.b05studio.yayway;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(Color.parseColor("#213753"));

    }

    @Override
    protected void onStart() {
        super.onStart();
        final VideoView vidioview = (VideoView) findViewById(R.id.splashVideoView);
        try {
            Uri path = Uri.parse("android.resource://" + getPackageName() + "/"+ R.raw.video_splash2);
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
