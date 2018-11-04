package co.za.forecast.features;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import co.za.forecast.R;
import co.za.forecast.features.showWeather.ShowWeatherActivity;


public class GetCurrentLocation extends AppCompatActivity {


    private AlertDialog mGPSDialog;
    private static final int GPS_ENABLE_REQUEST = 0x1001;
    private static final int WIFI_ENABLE_REQUEST = 0x1006;
    LocationManager locationManager;
    LocationListener locationListener;
    private double longitude;
    private double latitude;
    private boolean requestPermission = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (isThereInternetConnection()) {
            checkGPSOn();
        } else {
            noInternetMessage();
        }

    }

    private void setupLocationListener() {
        locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                coOrdinatesAquired(location);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) { }

            @Override
            public void onProviderEnabled(String s) { }

            @Override
            public void onProviderDisabled(String s) { }
        };

        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        } catch (SecurityException ex) {
            String temp = ex.getMessage();
        }
    }

    private void coOrdinatesAquired(Location location) {
        locationManager.removeUpdates(locationListener);
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        Intent intent = new Intent(this, ShowWeatherActivity.class);
        intent.putExtra(getString(R.string.lat),latitude);
        intent.putExtra(getString(R.string.longs), longitude);
        startActivity(intent);
        finish();

    }

    private void noInternetMessage() {
        Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_LONG).show();
    }

    public void checkGPSOn() {
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            showGPSDiabledDialog();
        } else {
            checkPermissionOn();
        }
    }

    public void checkPermissionOn() {
        if (!checkLocationPermission()) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            requestPermission = true;
        } else {
            setupLocationListener();
        }
    }


    public boolean checkLocationPermission() {
        String permission = "android.permission.ACCESS_FINE_LOCATION";
        int res = checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

    public boolean showGPSDiabledDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("GPS Disabled");
        builder.setMessage("Gps is disabled, in order to use the application properly you need to enable GPS of your device");
        builder.setPositiveButton("Enable GPS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS), GPS_ENABLE_REQUEST);
            }
        }).setNegativeButton("No, Just Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        mGPSDialog = builder.create();
        mGPSDialog.show();

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == GPS_ENABLE_REQUEST) {
            checkPermissionOn();
        } else if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            showGPSDiabledDialog();
        }
    }

    /**
     * Checks if the device has any active internet connection.
     *
     * @return true device with internet connection, otherwise false.
     */
    private boolean isThereInternetConnection() {
        boolean isConnected;

        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        isConnected = (networkInfo != null && networkInfo.isConnectedOrConnecting());

        return isConnected;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (checkLocationPermission() && requestPermission) {
          setupLocationListener();
        }
    }
}
