package co.za.forecast.data.remote;

import co.za.forecast.common.AppExecutors;
import co.za.forecast.data.WeatherDataSource;
import co.za.forecast.data.exception.DatabaseException;
import co.za.forecast.data.exception.NetworkConnectionException;
import co.za.forecast.features.showWeather.domain.model.CurrentWeather;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RemoteWeatherDataSource implements WeatherDataSource {

    private  OpenWeatherService service;
    private Call<CurrentWeather> call;
    private static final Object LOCK = new Object();
    private static RemoteWeatherDataSource sInstance;

    public RemoteWeatherDataSource(OpenWeatherService service) {
        this.service = service;
    }


    public static RemoteWeatherDataSource getInstance(OpenWeatherService openWeatherService) {
        if(sInstance == null){
            synchronized (LOCK){
                sInstance = new RemoteWeatherDataSource(openWeatherService);
            }
        }
        return sInstance;
    }

    @Override
    public void getCurrentWeather(String lat, String lon, final LoadCurrentWeatherCallBack currentWeatherCallBack) {
        call = service.getCurrentWeather(lat,lon,"");
            call.enqueue(new Callback<CurrentWeather>() {
                @Override
                public void onResponse(Call<CurrentWeather> call, Response<CurrentWeather> response) {
                    if(response.body() != null){
                        if (response.isSuccessful()){
                            currentWeatherCallBack.onDataLoaded(response.body());
                        }
                    }else{
                        currentWeatherCallBack.onDataloadedFailed(new DatabaseException());
                    }
                }

                @Override
                public void onFailure(Call<CurrentWeather> call, Throwable t) {
                    currentWeatherCallBack.onDataloadedFailed(t);
                }
            });
    }

    @Override
    public void getFiveDayForecast(String lat, String lon, LoadFiveDayWeatherCallBack loadFiveDayWeatherCallBack) {

    }
}
