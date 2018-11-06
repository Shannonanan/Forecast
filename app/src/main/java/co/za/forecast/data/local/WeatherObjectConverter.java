package co.za.forecast.data.local;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import co.za.forecast.features.showWeather.domain.model.Weather;

import static co.za.forecast.data.local.MainObjectConverter.gson;

public class WeatherObjectConverter {

    @TypeConverter
    public static Weather stringToSomeObject(String data) {
        if (data == null) {
            return null;
        }

        Type objectType = new TypeToken<Weather>() {
        }.getType();

        return gson.fromJson(data, objectType);
    }

    @TypeConverter
    public static String someObjectToString(Weather someObjects) {
        return gson.toJson(someObjects);
    }
}


