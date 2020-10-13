package com.yoyo.airquality;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cardiomood.android.controls.gauge.SpeedometerGauge;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;
import com.yoyo.airquality.models.BreeZoMeter;
import com.yoyo.airquality.viewmodel.BreeZoMeterViewModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private SpeedometerGauge mSpeedometer;
    private TextView mAirQualityNumberTV;
    private TextView mAirQualityTV;
    private Button mRefreshBTN;
    private FusedLocationProviderClient mClient;
    private LocationCallback mLocationCallback;
    private BreeZoMeterViewModel mViewModel;
    private NotificationHelper mNotificationHelper;
    private final int AIR_QUALITY_LIMIT_FOR_NOTIFICATION = 80;
    private final String MESSAGE_CHANNEL = "msg_channel";
    Location mLastLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        configGauge();

        mNotificationHelper = new NotificationHelper(this);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PermissionChecker.PERMISSION_GRANTED) {
            mClient = new FusedLocationProviderClient(getApplicationContext());
            requestLocationUpdates();
        } else {
            callPermissions();
        }

        mViewModel = new ViewModelProvider(this).get(BreeZoMeterViewModel.class);
        mViewModel.setContext(getApplicationContext());

        mViewModel.getData().observe(this, new Observer<BreeZoMeter>() {
            @Override
            public void onChanged(@Nullable BreeZoMeter data) {

                double nidlle = data.getData().getIndexes().getBaqi().getAqi();
                String airNum = Integer.toString(Math.round((data.getData().getIndexes().getBaqi().getAqi())));
                String airTxt = data.getData().getIndexes().getBaqi().getCategory();
                mSpeedometer.setSpeed(nidlle);
                mAirQualityNumberTV.setText(airNum);
                mAirQualityTV.setText(airTxt);
                mAirQualityTV.setBackgroundColor(Color.parseColor(data.getData().getIndexes().getBaqi().getColor()));

                if (Integer.parseInt(airNum) < AIR_QUALITY_LIMIT_FOR_NOTIFICATION) {
                    mNotificationHelper.createNotification("Air quality is less than 80", MESSAGE_CHANNEL);
                }

            }
        });

        mRefreshBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestLocationUpdates();
            }
        });
    }

    private void configGauge() {
        // configure value range and ticks
        mSpeedometer.setMaxSpeed(300);
        mSpeedometer.setMajorTickStep(30);
        mSpeedometer.setMinorTicks(2);


        // Configure value range colors
        mSpeedometer.addColoredRange(30, 140, Color.GREEN);
        mSpeedometer.addColoredRange(140, 180, Color.YELLOW);
        mSpeedometer.addColoredRange(180, 400, Color.RED);
    }

    private void initViews() {
        mSpeedometer = findViewById(R.id.speedometerW);
        mAirQualityNumberTV = findViewById(R.id.airQualityNumberTV);
        mAirQualityTV = findViewById(R.id.airQualityTV);
        mRefreshBTN = findViewById(R.id.refreshBTN);
    }

    public void callPermissions() {
        Permissions.check(this, Manifest.permission.ACCESS_FINE_LOCATION, "Location permissions are required to get Air Quality", new PermissionHandler() {
            @Override
            public void onGranted() {
                mClient = new FusedLocationProviderClient(getApplicationContext());
                requestLocationUpdates();
            }

            @Override
            public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                super.onDenied(context, deniedPermissions);
                callPermissions();
            }

        });
    }

    public void requestLocationUpdates() {

        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY).setFastestInterval(1000 * 60)
                .setInterval(1000 * 60 * 60);

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(final LocationResult locationResult) {
                super.onLocationResult(locationResult);
                if (locationResult == null) return;

                double lat = locationResult.getLastLocation().getLatitude();
                double lon = locationResult.getLastLocation().getLongitude();
                mViewModel.setLatLon(lat, lon);

                if (mLastLocation == null) {
                    mLastLocation = locationResult.getLastLocation();
                }
                if (locationResult.getLastLocation().distanceTo(mLastLocation) > 500.00) {
                    mNotificationHelper.createNotification("your location is changed more than 500 meters", MESSAGE_CHANNEL);
                }


            }
        };
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            callPermissions();
        }
        mClient.requestLocationUpdates(locationRequest, mLocationCallback, null);

    }

    @Override
    public void onPause() {
        super.onPause();
        if (mClient != null)
            stopLocationUpdates();
    }

    private void stopLocationUpdates() {
        mClient.removeLocationUpdates(mLocationCallback);
    }
}
