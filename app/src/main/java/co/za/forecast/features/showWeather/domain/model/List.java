
package co.za.forecast.features.showWeather.domain.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class List {

    @SerializedName("dt")
    @Expose
    private Integer dt;
    @SerializedName("main")
    @Expose
    private Main main;
    @SerializedName("weather")
    @Expose
    private java.util.List<Weather> weather = null;
    @SerializedName("dt_txt")
    @Expose
    private String dtTxt;
    private String timeSinceSaved;

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

    public java.util.List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(java.util.List<Weather> weather) {
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

}
