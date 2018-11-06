package co.za.forecast.data.local;


import java.util.ArrayList;
import java.util.List;

import co.za.forecast.common.AppExecutors;
import co.za.forecast.data.WeatherDataSource;
import co.za.forecast.features.showWeather.domain.model.CurrentWeather;
import co.za.forecast.features.showWeather.domain.model.FiveDayForecast;
import co.za.forecast.utils.DateTimeCreator;
import co.za.forecast.utils.ObjectTransformer;

public class LocalWeatherDataSource {

    // For Singleton instantiation
    private static LocalWeatherDataSource sInstance;
    private static final Object LOCK = new Object();
    private CurrentWeatherDao currentDao;
    private FiveDayWeatherDao  fiveDayDao;
    private final AppExecutors mExecutors;

    public LocalWeatherDataSource(CurrentWeatherDao currentDao, FiveDayWeatherDao fiveDayDao, AppExecutors mExecutors) {
        this.currentDao = currentDao;
        this.fiveDayDao = fiveDayDao;
        this.mExecutors = mExecutors;
    }

    public static LocalWeatherDataSource getInstance(CurrentWeatherDao currentDao, FiveDayWeatherDao fiveDayDao, AppExecutors mExecutors) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new LocalWeatherDataSource(currentDao, fiveDayDao, mExecutors);
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


    public void saveFiveDayForecast(final List<SingleFiveDayForecastEntity> entities) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                fiveDayDao.bulkInsert(entities);
            }
        };
        mExecutors.diskIO().execute(runnable);
    }


    public void getFiveDayForecast(final WeatherDataSource.LoadFiveDayWeatherCallBack loadFiveDayWeatherCallBack) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                List<SingleFiveDayForecastEntity> savesEntities = new ArrayList<>(fiveDayDao.getFiveDayForecast());
                List<co.za.forecast.features.showWeather.domain.model.List> forecastObjList = new ArrayList<>();
                FiveDayForecast fiveDayForecast = null;
                if(!savesEntities.isEmpty()){
                 fiveDayForecast = new FiveDayForecast();
                for (SingleFiveDayForecastEntity obj: savesEntities) {
                    forecastObjList.add(ObjectTransformer.transformToForecast(obj));
                }
                fiveDayForecast.setList(forecastObjList);}

                loadFiveDayWeatherCallBack.onDataLoaded(fiveDayForecast);
            }
        };
        mExecutors.diskIO().execute(runnable);
    }

    public void updateWeather(Object currentWeather) {
    }


}
