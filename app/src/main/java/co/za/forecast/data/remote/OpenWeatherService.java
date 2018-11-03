package co.za.forecast.data.remote;

import co.za.forecast.features.showWeather.domain.model.CurrentWeather;
import co.za.forecast.features.showWeather.domain.model.FiveDayForecast;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeatherService {

    @GET("/data/2.5/weather")
    Call<CurrentWeather> getCurrentWeather(@Query("lat") String lat,
                                           @Query("lon") String lon,
                                           @Query("appid") String appid);


    @GET("/data/2.5/forecast/")
    Call<FiveDayForecast> getFiveDayForecast(@Query("lat")String lat,
                                             @Query("lon")String lon,
                                             @Query("appid")String appid);
}
