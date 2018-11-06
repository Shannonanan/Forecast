package co.za.forecast.data;


import co.za.forecast.data.local.LocalWeatherDataSource;
import co.za.forecast.data.remote.RemoteWeatherDataSource;
import co.za.forecast.features.showWeather.domain.model.CurrentWeather;
import co.za.forecast.features.showWeather.domain.model.FiveDayForecast;
import co.za.forecast.utils.DateTimeCreator;

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
                    checkIfFresh(lat,lon,currentWeather,currentWeatherCallBack);
                    currentWeatherCallBack.onDataLoaded(currentWeather);
                }
    }

            @Override
            public void onDataloadedFailed(Throwable exception) {
                currentWeatherCallBack.onDataloadedFailed(exception);            }
        });
}

    private void checkIfFresh(String lat, String lon, CurrentWeather currentWeather, final LoadCurrentWeatherCallBack currentWeatherCallBack ) {
        if(DateTimeCreator.isTimemoreThan2hrsPast(currentWeather.getTime())){
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

    private void updateLocalWeather(CurrentWeather currentWeather) {
        //get time and add to current weather model
        localWeatherDataSource.updateWeather(currentWeather);
    }

    @Override
    public void getFiveDayForecast(String lat, String lon, final LoadFiveDayWeatherCallBack loadFiveDayWeatherCallBack) {
        //check in local
        //if nothing - pull remote and update local, update timestamp
        //if something - populate ui and check if stale
        // if stale - pull from remote and update local and ui
        //if not - nothing
        remoteWeatherDataSource.getFiveDayForecast(lat, lon, new LoadFiveDayWeatherCallBack() {
            @Override
            public void onDataLoaded(FiveDayForecast fiveDayForecast) {
                loadFiveDayWeatherCallBack.onDataLoaded(fiveDayForecast);
            }

            @Override
            public void onDataloadedFailed(Throwable exception) {
                loadFiveDayWeatherCallBack.onDataloadedFailed(exception);
            }
        });
    }


}
