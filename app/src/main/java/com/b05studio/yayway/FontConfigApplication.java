package com.b05studio.yayway;

import android.app.Application;


import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


/**
 * Created by young on 2017-08-10.
 */

public class FontConfigApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Spoqa Han Sans Light.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}
