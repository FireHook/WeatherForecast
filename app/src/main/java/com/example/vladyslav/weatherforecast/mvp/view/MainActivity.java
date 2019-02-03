package com.example.vladyslav.weatherforecast.mvp.view;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.vladyslav.weatherforecast.R;
import com.example.vladyslav.weatherforecast.mvp.presenter.MainPresenter;
import com.example.vladyslav.weatherforecast.mvp.presenter.MapPresenter;

import butterknife.ButterKnife;
import timber.log.Timber;

public class MainActivity extends MvpAppCompatActivity implements MainView {

    @InjectPresenter MainPresenter mPresenter;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mPresenter.showMap();
    }

    @Override public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count > 1) {
            super.onBackPressed();
        } else {
            finish();
        }
    }

    @Override public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override public void openMapScreen() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, new MapFragment(), MapFragment.class.getSimpleName())
                .addToBackStack(MapFragment.class.getSimpleName())
                .commit();
    }
}
