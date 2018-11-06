package co.za.forecast.data.local;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;

import java.util.List;

import co.za.forecast.features.showWeather.domain.model.FiveDayForecast;

@Dao
public interface FiveDayWeatherDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    @TypeConverters(ListObjectConverter.class)
    void bulkInsert(List<SingleFiveDayForecastEntity> list);

    @Query("SELECT * from fiveDay")
    List<SingleFiveDayForecastEntity> getFiveDayForecast();

    @Query("DELETE FROM fiveDay WHERE dtTxt =:dtTxt")
    int deleteFiveDayentity(String dtTxt);
}
