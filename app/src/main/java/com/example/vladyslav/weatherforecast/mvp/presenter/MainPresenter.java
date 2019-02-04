package com.example.vladyslav.weatherforecast.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.vladyslav.weatherforecast.mvp.view.MainView;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {

    public void showMap() {
        getViewState().openMapScreen();
    }

}
