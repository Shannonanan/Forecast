package co.za.forecast.utils;

import java.util.ArrayList;

import co.za.forecast.data.local.CurrentWeatherEntity;
import co.za.forecast.data.local.SingleFiveDayForecastEntity;
import co.za.forecast.features.showWeather.domain.model.CurrentWeather;
import co.za.forecast.features.showWeather.domain.model.List;
import co.za.forecast.features.showWeather.domain.model.Weather;

public class ObjectTransformer {
    public static CurrentWeatherEntity transformToEntity(CurrentWeather result) {
        Weather weather = result.getWeather().get(0);
        CurrentWeatherEntity currentWeatherEntity = null;
        if (result != null) {
            currentWeatherEntity = new CurrentWeatherEntity();
            currentWeatherEntity.setIdd(result.getId());
            currentWeatherEntity.setMain(result.getMain());
            currentWeatherEntity.setWeather(weather);
            currentWeatherEntity.setName(result.getName());
            currentWeatherEntity.setId(result.getId());
            currentWeatherEntity.setDate(DateTimeCreator.getTodaysDate());
            currentWeatherEntity.setTime(DateTimeCreator.getTimeNow());
        }
        return currentWeatherEntity;
    }

    public static CurrentWeather transformToCurrent(CurrentWeatherEntity result) {
        java.util.List<Weather> listWeather = new ArrayList<>();
        CurrentWeather currentWeather = null;
        if (result != null) {
            currentWeather = new CurrentWeather();

            listWeather.add(result.getWeather());

            currentWeather.setId(result.getId());
            currentWeather.setMain(result.getMain());
            currentWeather.setWeather(listWeather);
            currentWeather.setName(result.getName());
            currentWeather.setId(result.getId());
            currentWeather.setDate(result.getDate());
            currentWeather.setTime(result.getTime());
        }
        return currentWeather;
    }

    public static SingleFiveDayForecastEntity transformToSingleEntity(List result) {
        Weather weather = result.getWeather().get(0);
        SingleFiveDayForecastEntity singleFiveDayForecastEntity = null;
        if (result != null) {
            singleFiveDayForecastEntity = new SingleFiveDayForecastEntity();
            singleFiveDayForecastEntity.setMain(result.getMain());
            singleFiveDayForecastEntity.setWeather(weather);
            singleFiveDayForecastEntity.setDtTxt(result.getDtTxt());
            singleFiveDayForecastEntity.setDt(result.getDt());
            singleFiveDayForecastEntity.setTimeSinceSaved(DateTimeCreator.getTimeNow());
        }
        return singleFiveDayForecastEntity;
    }

    public static List transformToForecast(SingleFiveDayForecastEntity result) {
        java.util.List<Weather> listWeather = new ArrayList<>();
        List forecastObj = null;
        if (result != null) {
            forecastObj = new List();
            listWeather.add(result.getWeather());

            forecastObj.setMain(result.getMain());
            forecastObj.setWeather(listWeather);
            forecastObj.setDtTxt(result.getDtTxt());
            forecastObj.setDt(result.getDt());
            forecastObj.setTimeSinceSaved(result.getTimeSinceSaved());
        }
        return forecastObj;
    }

}
