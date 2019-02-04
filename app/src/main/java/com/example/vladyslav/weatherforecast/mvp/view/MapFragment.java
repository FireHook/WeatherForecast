package com.example.vladyslav.weatherforecast.mvp.view;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.vladyslav.weatherforecast.R;
import com.example.vladyslav.weatherforecast.mvp.presenter.MapPresenter;
import com.example.vladyslav.weatherforecast.network.model.Root;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
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
import timber.log.Timber;

public class MapFragment extends MvpAppCompatFragment implements MapView, OnMapReadyCallback, GoogleMap.OnMapClickListener{

    @BindView(R.id.action_forecast) FloatingActionButton mActionForecast;

    @InjectPresenter MapPresenter mPresenter;

    private SupportMapFragment mMapFragment;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private GoogleMap mMap;
    private Marker mMarker;

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_fragment);
        mPresenter.initMap();
    }

    @Override public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(this);
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity().getApplicationContext());

        if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        mFusedLocationProviderClient.getLastLocation().addOnSuccessListener( location -> {
            if (location != null) {
                Timber.d("%s, %s", location.getLatitude(), location.getLongitude());
                mMarker = googleMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())).
                        icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)).draggable(true));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(),location.getLongitude())));
                googleMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
                mPresenter.setCoordinates(new LatLng(location.getLatitude(), location.getLongitude()));
            }
        });
    }

    @Override public void onMapClick(LatLng latLng) {
        if (mMarker != null) mMarker.remove();

        mMarker = mMap.addMarker(new MarkerOptions().position(new LatLng(latLng.latitude, latLng.longitude)).
                icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)).draggable(true));
        mPresenter.setCoordinates(latLng);
    }

    @OnClick(R.id.action_forecast)
    public void onActionButtonClick() {
        mPresenter.loadForecast();
        if (mMarker != null) mMarker.remove();
    }

    @Override public void openForecastDetailScreen(Root root) {
        Timber.d("%s", root.city.name);
        if (getActivity() != null)
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_frame, ForecastDetailFragment.newInstance(root), ForecastDetailFragment.class.getSimpleName())
                    .addToBackStack(ForecastDetailFragment.class.getSimpleName())
                    .commit();
    }

    @Override public void loadMap() {
        mMapFragment.getMapAsync(this);
    }

    @Override public void showToastText(int message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }
}
