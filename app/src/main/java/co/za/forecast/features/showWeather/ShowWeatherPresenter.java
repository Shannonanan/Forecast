package co.za.forecast.features.showWeather;

import android.support.annotation.NonNull;

import java.util.ArrayList;

import co.za.forecast.data.WeatherRepository;
import co.za.forecast.features.showWeather.domain.model.CurrentWeather;
import co.za.forecast.features.showWeather.domain.model.FiveDayForecast;
import co.za.forecast.features.showWeather.domain.model.List;

import static co.za.forecast.data.WeatherDataSource.*;

public class ShowWeatherPresenter {

    private WeatherRepository weatherRepository;
    private ShowWeatherContract showWeatherContract;
    private static ShowWeatherPresenter sInstance;
    private static final Object LOCK = new Object();

    public ShowWeatherPresenter(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    public static ShowWeatherPresenter getInstance(WeatherRepository weatherRepository1) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new ShowWeatherPresenter(weatherRepository1);
            }
        }
        return sInstance;
    }

    public void setView(@NonNull ShowWeatherContract view) {
        this.showWeatherContract = view;
    }

    public void getCurrentWeather(final String latitude, final String longitude) {
        weatherRepository.getCurrentWeather(latitude, longitude, new LoadCurrentWeatherCallBack() {
            @Override
            public void onDataLoaded(CurrentWeather currentWeather) {

                showWeatherContract.renderCurrentWeather(currentWeather);
                getFiveDayForecast(latitude, longitude);
            }

            @Override
            public void onDataloadedFailed(Throwable exception) {

            }
        });

    }

    private java.util.List filterList(FiveDayForecast renderCollection) {
        java.util.List<List> collection = new ArrayList<>(renderCollection.getList());
        java.util.List<List> filteredCollection = new ArrayList<>();
        for (List obj : collection) {
            if (obj.getDtTxt().contains("12:00:00")) {
                filteredCollection.add(obj);
            }
        }
        return filteredCollection;
    }

    public void getFiveDayForecast(String latitude, String longitude) {
        weatherRepository.getFiveDayForecast(latitude, longitude, new LoadFiveDayWeatherCallBack() {
            @Override
            public void onDataLoaded(FiveDayForecast fiveDayForecast) {
                showWeatherContract.renderFiveDayForecast(filterList(fiveDayForecast));
            }

            @Override
            public void onDataloadedFailed(Throwable exception) {
                //SET ERROR
            }
        });

    }


}