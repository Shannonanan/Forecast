package co.za.forecast.di;


import android.content.Context;
import android.view.LayoutInflater;
import co.za.forecast.common.AppExecutors;
import co.za.forecast.data.WeatherRepository;
import co.za.forecast.data.local.LocalWeatherDataSource;
import co.za.forecast.data.local.WeatherDatabase;
import co.za.forecast.data.remote.OpenWeatherService;
import co.za.forecast.data.remote.RemoteWeatherDataSource;
import co.za.forecast.features.showWeather.ShowWeatherPresenter;
import co.za.forecast.features.viewControls.ViewMvcFactory;
import co.za.forecast.utils.Constants;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Provides static methods to inject the various classes needed for Forecast
 */
public class InjectorUtils {


    private static Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private static OpenWeatherService getWeatherService(Retrofit retrofit){
        return retrofit.create(OpenWeatherService.class);
    }

    public static ViewMvcFactory provideViewMvcFactory(Context context){
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        return ViewMvcFactory.getInstance(layoutInflater);
    }

    public static ShowWeatherPresenter providePresenter(Context context) {
        AppExecutors appExecutors = AppExecutors.getInstance();
        WeatherDatabase weatherDatabase = WeatherDatabase.getInstance(context.getApplicationContext());
        LocalWeatherDataSource localWeatherDataSource = LocalWeatherDataSource.getInstance(weatherDatabase.currentDao(), weatherDatabase.fiveDayDao(), appExecutors);
        RemoteWeatherDataSource remoteWeatherDataSource = RemoteWeatherDataSource.getInstance(getWeatherService(getRetrofit()));
        WeatherRepository weatherRepository1 = new WeatherRepository(localWeatherDataSource,remoteWeatherDataSource);
        return ShowWeatherPresenter.getInstance(weatherRepository1);
    }

}
