package com.example.vladyslav.weatherforecast;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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

import javax.inject.Inject;
import javax.inject.Singleton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    @BindView(R.id.action_forecast) FloatingActionButton mActionForecast;

    @Singleton
    DataManager mDataManager = new DataManager();
    private SupportMapFragment mMapFragment;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private GoogleMap mMap;
    private Marker mMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Timber.d("OnCreate");
        mMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_fragment);
        mMapFragment.getMapAsync(this);
    }

    @OnClick(R.id.action_forecast)
    public void onActionButtonClick(View view){
        Timber.d("Float button clicked!");
        WeatherService mWeatherService = WeatherApiClient.getClient().create(WeatherService.class);
        mWeatherService.getForecast(mDataManager.mCoordinates.latitude, mDataManager.mCoordinates.longitude)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Forecast>() {
                    @Override public void onSubscribe(Disposable d) { /* No need */ }
                    @Override public void onNext(Forecast forecast) {
                        openForecastDetailScreen(forecast);
                    }

                    @Override public void onError(Throwable e) {
                        //showError();
                        Timber.d("Error!: [%s]", e.toString());
                    }
                    @Override public void onComplete() { /* No need */ }
                });
    }

    void openForecastDetailScreen(Forecast forecast) {
        Timber.d("%s", forecast.name);
    }

    void showError() {
        Toast.makeText(this, "Turn ON Network Connection", Toast.LENGTH_LONG).show();
    }

    @Override public void onMapClick(LatLng latLng) {
        Timber.d("on Map Click");
        if (mMarker != null) mMarker.remove();

        mMarker = mMap.addMarker(new MarkerOptions().position(new LatLng(latLng.latitude, latLng.longitude)).
                icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)).draggable(true));
        mDataManager.mCoordinates = latLng;

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Timber.d("On Map Ready");
        mMap = googleMap;
        mMap.setOnMapClickListener(this);
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());

        if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Timber.d("return! Location Services doesnt work");
            Toast.makeText(getApplicationContext(), "Turn ON Location Services", Toast.LENGTH_LONG).show();
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
}
