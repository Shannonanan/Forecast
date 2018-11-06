package co.za.forecast.data.local;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface CurrentWeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertCurrentWeather(CurrentWeatherEntity saveCurrent);

    @Query("SELECT * from current WHERE date= :date")
    CurrentWeatherEntity getCurrentWeather(String date);

    @Query("DELETE FROM current WHERE date= :date")
    int deleteCurrentWeather(String date);
}
