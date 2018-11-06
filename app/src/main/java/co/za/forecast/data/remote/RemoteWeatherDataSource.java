package co.za.forecast.data.remote;

import co.za.forecast.data.WeatherDataSource;
import co.za.forecast.data.exception.DatabaseException;
import co.za.forecast.features.showWeather.domain.model.CurrentWeather;
import co.za.forecast.features.showWeather.domain.model.FiveDayForecast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RemoteWeatherDataSource implements WeatherDataSource {

    private  OpenWeatherService service;
    private Call<CurrentWeather> callCurrentWeather;
    private Call<FiveDayForecast> callFiveDayForecast;
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
        callCurrentWeather = service.getCurrentWeather(lat,lon,"bd93967a9fa781ed9a813e9601cca72a");
        callCurrentWeather.enqueue(new Callback<CurrentWeather>() {
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
    public void getFiveDayForecast(String lat, String lon, final LoadFiveDayWeatherCallBack loadFiveDayWeatherCallBack) {
        callFiveDayForecast = service.getFiveDayForecast(lat,lon,"bd93967a9fa781ed9a813e9601cca72a");
        callFiveDayForecast.enqueue(new Callback<FiveDayForecast>() {
            @Override
            public void onResponse(Call<FiveDayForecast> call, Response<FiveDayForecast> fiveDayForecastResponse) {
                if(fiveDayForecastResponse.body() != null){
                    if (fiveDayForecastResponse.isSuccessful()){
                        loadFiveDayWeatherCallBack.onDataLoaded(fiveDayForecastResponse.body());
                    }
                }else{
                    loadFiveDayWeatherCallBack.onDataloadedFailed(new DatabaseException());
                }
            }

            @Override
            public void onFailure(Call<FiveDayForecast> call, Throwable t) {
                loadFiveDayWeatherCallBack.onDataloadedFailed(t);
            }
        });
    }
}
