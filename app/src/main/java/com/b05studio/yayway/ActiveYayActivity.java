package com.b05studio.yayway;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
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

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(Color.parseColor("#FA6B58"));

}
    private void startActiveDuringRideActivity() {
        if(isFinishing())
            return ;

        //startActivity(new Intent(this,PhysicsLayout.class));
        overridePendingTransition(0,0);
        dispatchTakePictureIntent();
        finish();
        overridePendingTransition(0,0);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            // TODO: 2017-08-19 여기 비트맵 이미지 가져다가 다른데써야도미
            assert imageBitmap != null;
            Log.d("ddddddd",imageBitmap.toString());

        }
    }



}
