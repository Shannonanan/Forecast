package co.za.forecast.data.local;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class ListObjectConverter {

    static Gson gson = new Gson();

    @TypeConverter
    public static List<SingleFiveDayForecastEntity> stringTobjectList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<SingleFiveDayForecastEntity>>() {}.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String objectListToString(List<SingleFiveDayForecastEntity> someObjects) {
        return gson.toJson(someObjects);
    }
}
