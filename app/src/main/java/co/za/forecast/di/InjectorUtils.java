package co.za.forecast.di;

import co.za.forecast.common.AppExecutors;
import co.za.forecast.data.WeatherDataSource;
import co.za.forecast.data.WeatherRepository;
import co.za.forecast.data.local.LocalWeatherDataSource;
import co.za.forecast.data.remote.OpenWeatherService;
import co.za.forecast.data.remote.RemoteWeatherDataSource;
import co.za.forecast.utils.Constants;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Provides static methods to inject the various classes needed for Forecast
 */
public class InjectorUtils {

    public static Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static OpenWeatherService getWeatherService(Retrofit retrofit){
        return retrofit.create(OpenWeatherService.class);
    }

    public static WeatherRepository provideRepository(){
        AppExecutors executors = AppExecutors.getInstance();
        LocalWeatherDataSource localWeatherDataSource = LocalWeatherDataSource.getInstance();
        RemoteWeatherDataSource remoteWeatherDataSource = RemoteWeatherDataSource.getInstance(getWeatherService(getRetrofit()));
        return  WeatherRepository.getInstance(localWeatherDataSource, remoteWeatherDataSource);
    }
}
