package com.example.vladyslav.weatherforecast;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.vladyslav.weatherforecast.network.model.Forecast;
import com.example.vladyslav.weatherforecast.network.retrofit.WeatherApiClient;
import com.example.vladyslav.weatherforecast.network.retrofit.WeatherService;
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
import com.google.android.gms.tasks.OnSuccessListener;
import javax.inject.Singleton;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapClickListener{

    @BindView(R.id.action_forecast) FloatingActionButton mActionForecast;

    @Singleton DataManager mDataManager = new DataManager();

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
        mMapFragment = (SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.map_fragment);
        mMapFragment.getMapAsync(this);
    }

    @Override public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(this);
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity().getApplicationContext());

        if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getContext(), "Turn ON Location Services", Toast.LENGTH_LONG).show();
            return;
        }

        mFusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    Timber.d("%s, %s", location.getLatitude(), location.getLongitude());
                    mMarker = googleMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())).
                            icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)).draggable(true));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(),location.getLongitude())));

                }
            }
        });
    }

    @Override public void onMapClick(LatLng latLng) {
        if (mMarker != null) mMarker.remove();

        mMarker = mMap.addMarker(new MarkerOptions().position(new LatLng(latLng.latitude, latLng.longitude)).
                icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)).draggable(true));
        mDataManager.mCoordinates = latLng;
    }

    @OnClick(R.id.action_forecast)
    public void onActionButtonClick(View view) {

        WeatherService mWeatherService = WeatherApiClient.getClient().create(WeatherService.class);
        mWeatherService.getForecast(mDataManager.mCoordinates.latitude, mDataManager.mCoordinates.longitude)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Forecast>() {
                    @Override public void onSubscribe(Disposable d) { /* No need */ }
                    @Override public void onNext(Forecast forecast) { openForecastDetailScreen(forecast); }
                    @Override public void onError(Throwable e) { showError(); }
                    @Override public void onComplete() { /* No need */ }
                });
    }

    void openForecastDetailScreen(Forecast forecast) {
        Timber.d("%s", forecast.name);
        if (getActivity() != null)
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_frame, ForecastDetailFragment.newInstance(forecast), ForecastDetailFragment.class.getSimpleName())
                    .commit();
    }

    void showError() { Toast.makeText(getContext(), "Turn ON Network Connection", Toast.LENGTH_LONG).show(); }

}
