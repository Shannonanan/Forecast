package co.za.forecast.features.showWeather;

import android.os.Bundle;


public class ShowWeatherActivity extends BaseActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initializeActivity();
    }

    private void initializeActivity() {
        addFragment(new ShowWeatherFragment());
    }

}
