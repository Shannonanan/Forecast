package co.za.forecast.data;

import co.za.forecast.features.showWeather.domain.model.CurrentWeather;
import co.za.forecast.features.showWeather.domain.model.FiveDayForecast;

public interface WeatherDataSource {

    interface LoadCurrentWeatherCallBack{
        void onDataLoaded(CurrentWeather currentWeather);
        void onDataloadedFailed(Throwable exception);
    }

    interface LoadFiveDayWeatherCallBack{
        void onDataLoaded(FiveDayForecast fiveDayForecast);
        void onDataloadedFailed(Exception exception);
    }

    void getCurrentWeather(String lat, String lon,LoadCurrentWeatherCallBack currentWeatherCallBack);
    void getFiveDayForecast(String lat, String lon, LoadFiveDayWeatherCallBack loadFiveDayWeatherCallBack);
}
