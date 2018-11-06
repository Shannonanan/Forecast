package co.za.forecast.data.local;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import java.util.List;

import co.za.forecast.features.showWeather.domain.model.Main;
import co.za.forecast.features.showWeather.domain.model.Weather;

import static co.za.forecast.data.local.CurrentWeatherEntity.TABLE_NAME1;

@Entity(tableName = TABLE_NAME1)
@TypeConverters({MainObjectConverter.class, WeatherObjectConverter.class})
public class CurrentWeatherEntity {

    public static final String TABLE_NAME1 = "current";


    @PrimaryKey(autoGenerate = true)
    private int idd;
    @TypeConverters(WeatherObjectConverter.class)
    private Weather weather;
    @TypeConverters(MainObjectConverter.class)
    private Main main;
    private Integer id;
    private String name;
    private String date;
    private String time;

    CurrentWeatherEntity(int idd, Weather weather, Main main, Integer id, String name, String date, String time) {
        this.idd = idd;
        this.weather = weather;
        this.main = main;
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    @Ignore
    public CurrentWeatherEntity() {
    }

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIdd() {
        return idd;
    }

    public void setIdd(int idd) {
        this.idd = idd;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public String getTime() { return time;
    }

    public void setTime(String time) { this.time = time;
    }
}
