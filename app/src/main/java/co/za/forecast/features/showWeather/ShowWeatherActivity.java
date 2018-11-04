package co.za.forecast.features.showWeather;


import android.os.Bundle;

import co.za.forecast.R;


public class ShowWeatherActivity extends BaseActivity {

    public static double longitude;
    public static double latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        latitude = getIntent().getDoubleExtra(getString(R.string.lat), 0);
        longitude = getIntent().getDoubleExtra(getString(R.string.longs),0);
        initializeActivity(latitude, longitude);
    }


    private void initializeActivity(Double lat, Double lon) {

        Bundle bundle = new Bundle();
        bundle.putDouble(getString(R.string.lat), lat);
        bundle.putDouble(getString(R.string.longs), lon);

        ShowWeatherFragment showWeatherFragment = new ShowWeatherFragment();
        showWeatherFragment.setArguments(bundle);
        addFragment(showWeatherFragment);
    }


}
