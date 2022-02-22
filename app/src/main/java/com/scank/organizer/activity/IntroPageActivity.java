package com.scank.organizer.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.scank.organizer.R;
import com.scank.organizer.Utility.PreferencesHelper;

public class IntroPageActivity extends Activity {

    LinearLayout llLetsGo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_page);

        PreferencesHelper.setIntroPage(this, true);

        llLetsGo = findViewById(R.id.llLetsGo);
        llLetsGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(IntroPageActivity.this, LoginActivity.class));
                finish();
            }
        });

    }
}
