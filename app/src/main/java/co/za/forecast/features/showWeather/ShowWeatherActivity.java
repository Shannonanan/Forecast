package co.za.forecast.features.showWeather;


import android.os.Build;
import android.os.Bundle;

import co.za.forecast.R;


public class ShowWeatherActivity extends BaseActivity implements ShowWeatherFragment.onConditionChangeListener{

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


    @Override
    public void onConditionChangeColour(final String condition) {

        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                switch (condition) {
                    case "Clear":
                        if (Build.VERSION.SDK_INT >= 21) {
                            getWindow().setNavigationBarColor(getResources().getColor(R.color.cloudy));
                            getWindow().setStatusBarColor(getResources().getColor(R.color.sunny));
                        }
                        break;
                    case "Clouds":
                        if (Build.VERSION.SDK_INT >= 21) {
                            getWindow().setNavigationBarColor(getResources().getColor(R.color.cloudy));
                            getWindow().setStatusBarColor(getResources().getColor(R.color.cloudy));
                        }
                        break;
                    case "Thunderstorm":
                        if (Build.VERSION.SDK_INT >= 21) {
                            getWindow().setNavigationBarColor(getResources().getColor(R.color.cloudy));
                            getWindow().setStatusBarColor(getResources().getColor(R.color.rainy));
                        }
                        break;
                    case "Rain":
                        if (Build.VERSION.SDK_INT >= 21) {
                            getWindow().setNavigationBarColor(getResources().getColor(R.color.cloudy));
                            getWindow().setStatusBarColor(getResources().getColor(R.color.rainy));
                        }
                        break;
                    default:
                        break;

                }

            }
        });


        }
    }

