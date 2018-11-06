package co.za.forecast.data.local;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import co.za.forecast.features.showWeather.domain.model.FiveDayForecast;
//
//@Dao
//public interface FiveDayWeatherDao {
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    long insertFiveDayWeather(FiveDayForecastEntity saveFiveDay);
//
//    @Query("SELECT * from fiveDay")
//    LiveData<List<List>> getFiveDay();
//
//    @Query("DELETE FROM current WHERE id= :id")
//    int deleteFiveDay(Integer id);
//}
