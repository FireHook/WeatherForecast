package com.example.vladyslav.weatherforecast.mvp.view;

import android.os.Bundle;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.vladyslav.weatherforecast.R;
import com.example.vladyslav.weatherforecast.mvp.presenter.MainPresenter;

import butterknife.ButterKnife;

public class MainActivity extends MvpAppCompatActivity implements MainView {

    @InjectPresenter MainPresenter mPresenter;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mPresenter.showMap();
    }

    @Override public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1)
            super.onBackPressed();
        else
            finish();
    }

    @Override public void openMapScreen() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, new MapFragment(), MapFragment.class.getSimpleName())
                .addToBackStack(MapFragment.class.getSimpleName())
                .commit();
    }
}
