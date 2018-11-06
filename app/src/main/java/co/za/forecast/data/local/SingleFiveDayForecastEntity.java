package co.za.forecast.data.local;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import co.za.forecast.features.showWeather.domain.model.Main;
import co.za.forecast.features.showWeather.domain.model.Weather;

import static co.za.forecast.data.local.SingleFiveDayForecastEntity.TABLE_NAME;

@Entity(tableName = TABLE_NAME)
@TypeConverters({MainObjectConverter.class, WeatherObjectConverter.class})
public class SingleFiveDayForecastEntity {

    public static final String TABLE_NAME = "fiveDay";
    @PrimaryKey(autoGenerate = true)
    private int id;
    private Integer dt;
    @TypeConverters(WeatherObjectConverter.class)
    private Weather weather;
    @TypeConverters(MainObjectConverter.class)
    private Main main;
    private String dtTxt;
    private String timeSinceSaved;

    public SingleFiveDayForecastEntity(int id, Integer dt, Weather weather, Main main, String dtTxt, String timeSinceSaved) {
        this.id = id;
        this.dt = dt;
        this.weather = weather;
        this.main = main;
        this.dtTxt = dtTxt;
        this.timeSinceSaved = timeSinceSaved;
    }

    @Ignore
    public SingleFiveDayForecastEntity() {
    }

    public Integer getDt() {
        return dt;
    }

    public void setDt(Integer dt) {
        this.dt = dt;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    public String getDtTxt() {
        return dtTxt;
    }

    public void setDtTxt(String dtTxt) {
        this.dtTxt = dtTxt;
    }

    public String getTimeSinceSaved() { return timeSinceSaved;
    }

    public void setTimeSinceSaved(String timeSinceSaved) { this.timeSinceSaved = timeSinceSaved;
    }

    public int getId() { return id;
    }

    public void setId(int id) { this.id = id;
    }
}
