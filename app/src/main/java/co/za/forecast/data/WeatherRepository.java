package co.za.forecast.data;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import co.za.forecast.common.AppExecutors;
import co.za.forecast.data.local.LocalWeatherDataSource;
import co.za.forecast.data.remote.RemoteWeatherDataSource;
import co.za.forecast.features.showWeather.domain.model.CurrentWeather;

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
        if(sInstance == null){
            synchronized (LOCK){
                sInstance = new WeatherRepository(localWeatherDataSource,remoteWeatherDataSource);
            }
        }
        return sInstance;
    }


    @Override
    public void getCurrentWeather(final String lat, final String lon, final LoadCurrentWeatherCallBack currentWeatherCallBack) {

        //check in local
        localWeatherDataSource.getCurrentWeather(lat, lon, new LoadCurrentWeatherCallBack() {
            @Override
            public void onDataLoaded(CurrentWeather currentWeather) {
                if (currentWeather == null) {
                    //if nothing - pull remote
                    getCurrentWeatherFromRemote(lat, lon, currentWeatherCallBack);
                } else {
                    checkIfFresh(currentWeather);
                    currentWeatherCallBack.onDataLoaded(currentWeather);
                }
            }

            @Override
            public void onDataloadedFailed(Throwable exception) {
                currentWeatherCallBack.onDataloadedFailed(exception);
            }
        });


        //if something - populate ui and check if stale
        // if stale - pull from remote and update local and ui
        //if not - nothing


    }

    private void checkIfFresh(CurrentWeather currentWeather) {
        //if fresh cool
        //else STARTSERVICE to pull remote and update ui
    }

    public void getCurrentWeatherFromRemote(String lat, String lon, final LoadCurrentWeatherCallBack currentWeatherCallBack) {
        remoteWeatherDataSource.getCurrentWeather(lat, lon, new LoadCurrentWeatherCallBack() {
            @Override
            public void onDataLoaded(CurrentWeather currentWeather) {
                //  and update local, update timestamp
             //   updateLocalWeather(currentWeather);
                currentWeatherCallBack.onDataLoaded(currentWeather);
            }

            @Override
            public void onDataloadedFailed(Throwable exception) {
                currentWeatherCallBack.onDataloadedFailed(exception);
            }
        });
    }

    private void updateLocalWeather(CurrentWeather currentWeather) {
        //get time and add to current weather model
        localWeatherDataSource.updateWeather(currentWeather);
    }

    @Override
    public void getFiveDayForecast(String lat, String lon, LoadFiveDayWeatherCallBack loadFiveDayWeatherCallBack) {
        //check in local
        //if nothing - pull remote and update local, update timestamp
        //if something - populate ui and check if stale
        // if stale - pull from remote and update local and ui
        //if not - nothing
    }


}
