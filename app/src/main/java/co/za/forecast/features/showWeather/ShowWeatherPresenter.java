package co.za.forecast.features.showWeather;

import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import java.util.ArrayList;

import co.za.forecast.data.WeatherDataSource;
import co.za.forecast.data.WeatherRepository;
import co.za.forecast.data.local.LocalWeatherDataSource;
import co.za.forecast.features.showWeather.domain.model.CurrentWeather;
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
        if(sInstance == null){
            synchronized (LOCK){
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
                filterList();
                    showWeatherContract.renderCurrentWeather(currentWeather);
                   // getFiveDayForecast(latitude, longitude);
            }

            @Override
            public void onDataloadedFailed(Throwable exception) {

            }
        });

    }

    private void filterList(java.util.List<co.za.forecast.features.showWeather.domain.model.List> renderCollection) {
        java.util.List<List> filteredCollection = new ArrayList<>();
        for (co.za.forecast.features.showWeather.domain.model.List obj: renderCollection) {
            if(obj.getDtTxt().contains("12:00:00")){
                filteredCollection.add(obj);
            }
        }
    }

    public void getFiveDayForecast(String latitude, String longitude){

    }


    }