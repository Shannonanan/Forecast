
package co.za.forecast.features.showWeather.domain.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FiveDayForecast {

    @SerializedName("list")
    @Expose
    private java.util.List<List> list = null;

    public java.util.List<List> getList() {
        return list;
    }

    public void setList(java.util.List<List> list) {
        this.list = list;
    }


}
