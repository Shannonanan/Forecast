package co.za.forecast.data.local;


import co.za.forecast.common.AppExecutors;
import co.za.forecast.data.WeatherDataSource;
import co.za.forecast.features.showWeather.domain.model.CurrentWeather;
import co.za.forecast.utils.DateTimeCreator;
import co.za.forecast.utils.ObjectTransformer;

public class LocalWeatherDataSource {

    // For Singleton instantiation
    private static LocalWeatherDataSource sInstance;
    private static final Object LOCK = new Object();
    private CurrentWeatherDao currentDao;
  //  private FiveDayWeatherDao  fiveDayDao;

    public LocalWeatherDataSource(CurrentWeatherDao currentDao, AppExecutors mExecutors) {
        this.currentDao = currentDao;
        this.mExecutors = mExecutors;
    }

    private final AppExecutors mExecutors;


    public static LocalWeatherDataSource getInstance(CurrentWeatherDao currentDao, AppExecutors mExecutors) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new LocalWeatherDataSource(currentDao,mExecutors);
            }
        }
        return sInstance;
    }


    public void getCurrentWeather(final WeatherDataSource.LoadCurrentWeatherCallBack currentWeatherCallBack) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                String date = DateTimeCreator.getTodaysDate();
                CurrentWeatherEntity currentWeatherEntity = currentDao.getCurrentWeather(date);
                CurrentWeather currentWeather = ObjectTransformer.transformToCurrent(currentWeatherEntity);
                currentWeatherCallBack.onDataLoaded(currentWeather);

            }
        };
        mExecutors.diskIO().execute(runnable);
    }


    public void saveCurrentWeather(final CurrentWeather currentWeather) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                CurrentWeatherEntity currentWeatherEntity = ObjectTransformer.transformToEntity(currentWeather);
                currentDao.insertCurrentWeather(currentWeatherEntity);
            }
        };
        mExecutors.diskIO().execute(runnable);
    }


    public void saveFiveDayForecast(String lat, String lon, WeatherDataSource.LoadFiveDayWeatherCallBack loadFiveDayWeatherCallBack) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
               // CurrentWeatherEntity currentWeatherEntity = transform(currentWeather);
               // currentDao.insertCurrentWeather(currentWeatherEntity);
            }
        };
        mExecutors.diskIO().execute(runnable);
    }


    public void getFiveDayForecast(String lat, String lon, WeatherDataSource.LoadFiveDayWeatherCallBack loadFiveDayWeatherCallBack) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                // CurrentWeatherEntity currentWeatherEntity = transform(currentWeather);
                // currentDao.insertCurrentWeather(currentWeatherEntity);
            }
        };
        mExecutors.diskIO().execute(runnable);
    }

    public void updateWeather(Object currentWeather) {
    }


}
