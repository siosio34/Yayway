package com.b05studio.yayway;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.solver.widgets.ConstraintAnchor;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ActiveModeActivity extends AppCompatActivity {

    AppBarLayout appBarLayout;
    ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_mode);

        appBarLayout = (AppBarLayout) findViewById(R.id.activeModeTabbar);
        constraintLayout = (ConstraintLayout) findViewById(R.id.activeModeTabLayout);
        constraintLayout.bringToFront();
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                if (Math.abs(verticalOffset)-appBarLayout.getTotalScrollRange() == 0)
                {
                    constraintLayout.setVisibility(View.VISIBLE);

                    Log.d("으으어어","응어어어##");
                    // TODO: 2017-08-15 작업하면댐오 ㅋㅋ
                }
                else
                {
                    constraintLayout.setVisibility(View.GONE);
                    Log.d("으으어어","응어어어1");
                }
            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


}
