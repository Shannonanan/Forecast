package co.za.forecast.data.local;


import co.za.forecast.data.WeatherDataSource;

public class LocalWeatherDataSource implements WeatherDataSource {

    // For Singleton instantiation
    private static LocalWeatherDataSource sInstance;
    private static final Object LOCK = new Object();


    public static LocalWeatherDataSource getInstance() {
        if(sInstance == null){
            synchronized (LOCK){
                sInstance = new LocalWeatherDataSource();
            }
        }
        return sInstance;
    }

    @Override
    public void getCurrentWeather(String lat, String lon, LoadCurrentWeatherCallBack currentWeatherCallBack) {

    }

    @Override
    public void getFiveDayForecast(String lat, String lon, LoadFiveDayWeatherCallBack loadFiveDayWeatherCallBack) {

    }

    public void updateWeather(Object currentWeather) {
    }
}
