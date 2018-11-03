package co.za.forecast.features.showWeather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import co.za.forecast.R;
import co.za.forecast.data.WeatherDataSource;
import co.za.forecast.data.WeatherRepository;
import co.za.forecast.di.InjectorUtils;
import co.za.forecast.features.showWeather.domain.model.CurrentWeather;

public class ShowWeatherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_weather);

        final TextView textView = findViewById(R.id.currentWeather);

        WeatherRepository weatherRepository = InjectorUtils.provideRepository();
        weatherRepository.getCurrentWeatherFromRemote("26.288111999999998", "28.0578538", new WeatherDataSource.LoadCurrentWeatherCallBack() {
            @Override
            public void onDataLoaded(CurrentWeather currentWeather) {
                textView.setText(currentWeather.getName());
            }

            @Override
            public void onDataloadedFailed(Throwable exception) {
                    textView.setText(exception.getMessage());
            }
        });
    }
}
