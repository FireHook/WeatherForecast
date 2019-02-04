package com.example.vladyslav.weatherforecast.mvp.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.vladyslav.weatherforecast.R;
import com.example.vladyslav.weatherforecast.mvp.presenter.MapPresenter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MapFragment extends MvpAppCompatFragment implements MapView, OnMapReadyCallback, GoogleMap.OnMapClickListener {

    @BindView(R.id.action_forecast) FloatingActionButton mActionForecast;

    @InjectPresenter MapPresenter mPresenter;

    private GoogleMap mMap;
    private Marker mMarker;

    @Nullable @Override public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_fragment);
        if (mMapFragment != null)
            mMapFragment.getMapAsync(this);
    }

    @Override public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(this);

        mPresenter.showMarker();
    }

    @Override public void onMapClick(LatLng latLng) {
        if (mMarker != null)
            mMarker.remove();

        mMarker = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(latLng.latitude, latLng.longitude))
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .draggable(true));
        mPresenter.saveCoordinates(latLng);
    }

    @OnClick(R.id.action_forecast)
    public void onActionButtonClick() {
        mPresenter.openForecast();
        if (mMarker != null)
            mMarker.remove();
    }

    @Override public void openForecastDetailScreen() {
        if (getActivity() != null)
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_frame, new ForecastDetailFragment(), ForecastDetailFragment.class.getSimpleName())
                    .addToBackStack(ForecastDetailFragment.class.getSimpleName())
                    .commit();
    }

    @Override public void drawMarker(LatLng coords) {
        if (mMap != null) {
            mMarker = mMap.addMarker(new MarkerOptions()
                    .position(coords)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                    .draggable(true));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(coords));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
        }
    }

    @Override public void showError(int message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }
}
