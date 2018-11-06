package co.za.forecast.data;


import java.util.ArrayList;
import java.util.List;

import co.za.forecast.data.local.LocalWeatherDataSource;
import co.za.forecast.data.local.SingleFiveDayForecastEntity;
import co.za.forecast.data.remote.RemoteWeatherDataSource;
import co.za.forecast.features.showWeather.domain.model.CurrentWeather;
import co.za.forecast.features.showWeather.domain.model.FiveDayForecast;
import co.za.forecast.utils.DateTimeCreator;
import co.za.forecast.utils.ObjectTransformer;


public class WeatherRepository implements WeatherDataSource {

    private LocalWeatherDataSource localWeatherDataSource;
    private RemoteWeatherDataSource remoteWeatherDataSource;
    private static WeatherRepository sInstance;
    private static final Object LOCK = new Object();

    public WeatherRepository(LocalWeatherDataSource localWeatherDataSource,
                             RemoteWeatherDataSource remoteWeatherDataSource) {
        this.localWeatherDataSource = localWeatherDataSource;
        this.remoteWeatherDataSource = remoteWeatherDataSource;
    }

    public static WeatherRepository getInstance(LocalWeatherDataSource localWeatherDataSource,
                                                RemoteWeatherDataSource remoteWeatherDataSource) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new WeatherRepository(localWeatherDataSource, remoteWeatherDataSource);
            }
        }
        return sInstance;
    }


    @Override
    public void getCurrentWeather(final String lat, final String lon, final LoadCurrentWeatherCallBack currentWeatherCallBack) {

        //check in local
        localWeatherDataSource.getCurrentWeather(new LoadCurrentWeatherCallBack() {
            @Override
            public void onDataLoaded(CurrentWeather currentWeather) {
                if (currentWeather == null) {
                    //if nothing - pull remote
                    getCurrentWeatherFromRemote(lat, lon, currentWeatherCallBack);
                } else {
                    checkIfFresh(lat, lon, currentWeather, currentWeatherCallBack);
                    currentWeatherCallBack.onDataLoaded(currentWeather);
                }
            }

            @Override
            public void onDataloadedFailed(Throwable exception) {
                currentWeatherCallBack.onDataloadedFailed(exception);
            }
        });
    }

    private void checkIfFresh(String lat, String lon, CurrentWeather currentWeather, final LoadCurrentWeatherCallBack currentWeatherCallBack) {
        if (DateTimeCreator.isTimemoreThan3hrsPast(currentWeather.getTime())) {
            getCurrentWeatherFromRemote(lat, lon, currentWeatherCallBack);
        }
    }

    public void getCurrentWeatherFromRemote(String lat, String lon, final LoadCurrentWeatherCallBack currentWeatherCallBack) {
        remoteWeatherDataSource.getCurrentWeather(lat, lon, new LoadCurrentWeatherCallBack() {
            @Override
            public void onDataLoaded(CurrentWeather forecast) {
                //  and update local, update timestamp
                localWeatherDataSource.saveCurrentWeather(forecast);
                currentWeatherCallBack.onDataLoaded(forecast);
            }

            @Override
            public void onDataloadedFailed(Throwable exception) {
                currentWeatherCallBack.onDataloadedFailed(exception);
            }
        });
    }


    @Override
    public void getFiveDayForecast(final String lat, final String lon, final LoadFiveDayWeatherCallBack loadFiveDayWeatherCallBack) {
        localWeatherDataSource.getFiveDayForecast(new LoadFiveDayWeatherCallBack() {
            @Override
            public void onDataLoaded(FiveDayForecast fiveDayForecast) {
                if (fiveDayForecast == null) {
                    //if nothing - pull remote
                    getfiveDayForecastFromRemote(lat, lon, loadFiveDayWeatherCallBack);
                } else {
                    checkIfFreshFiveDay(lat, lon, loadFiveDayWeatherCallBack, fiveDayForecast);
                    loadFiveDayWeatherCallBack.onDataLoaded(fiveDayForecast);
                }
            }

            @Override
            public void onDataloadedFailed(Throwable exception) {
                loadFiveDayWeatherCallBack.onDataloadedFailed(exception);
            }
        });

    }

    private void checkIfFreshFiveDay(String lat, String lon, LoadFiveDayWeatherCallBack loadFiveDayWeatherCallBack, FiveDayForecast fiveDayForecast) {
        if (DateTimeCreator.isTimemoreThan3hrsPast(fiveDayForecast.getList().get(0).getTimeSinceSaved())) {
            getfiveDayForecastFromRemote(lat, lon, loadFiveDayWeatherCallBack);
        }


    }

    private void getfiveDayForecastFromRemote(String lat, String lon, final LoadFiveDayWeatherCallBack loadFiveDayWeatherCallBack) {
        remoteWeatherDataSource.getFiveDayForecast(lat, lon, new LoadFiveDayWeatherCallBack() {
            @Override
            public void onDataLoaded(FiveDayForecast fiveDayForecast) {
                //update local
                localWeatherDataSource.saveFiveDayForecast(fiveDayForecastProcessed(fiveDayForecast));
                loadFiveDayWeatherCallBack.onDataLoaded(fiveDayForecast);
            }

            @Override
            public void onDataloadedFailed(Throwable exception) {
                loadFiveDayWeatherCallBack.onDataloadedFailed(exception);
            }
        });
    }

    private List<SingleFiveDayForecastEntity> fiveDayForecastProcessed(FiveDayForecast fiveDayForecast) {
        List<SingleFiveDayForecastEntity> getSingleForecastEntities = new ArrayList<>();

        for (co.za.forecast.features.showWeather.domain.model.List obj : fiveDayForecast.getList()) {
            getSingleForecastEntities.add(ObjectTransformer.transformToSingleEntity(obj));
        }

        return getSingleForecastEntities;
    }
}



