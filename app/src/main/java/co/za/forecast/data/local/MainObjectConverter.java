package co.za.forecast.data.local;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

import co.za.forecast.features.showWeather.domain.model.Main;
import co.za.forecast.features.showWeather.domain.model.Weather;


class MainObjectConverter {

       static Gson gson = new Gson();

       @TypeConverter
       public static Main stringToSomeObject(String data) {
           if (data == null) {
               return null;
           }

           Type objectType = new TypeToken<Main>() {}.getType();

           return gson.fromJson(data, objectType);
       }

       @TypeConverter
       public static String someObjectToString(Main someObjects) {
           return gson.toJson(someObjects);
       }

}
