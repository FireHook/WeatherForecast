package com.example.vladyslav.weatherforecast;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        openMapScreen();
    }

    void openMapScreen() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, new MapFragment(), MapFragment.class.getSimpleName())
                .commit();
    }

    @Override public void onBackPressed() {
        super.onBackPressed();
        //TODO
    }

    @Override public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //TODO
    }
}
