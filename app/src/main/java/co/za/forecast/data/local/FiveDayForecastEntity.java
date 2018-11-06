package co.za.forecast.data.local;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import co.za.forecast.features.showWeather.domain.model.List;
import static co.za.forecast.data.local.FiveDayForecastEntity.TABLE_NAME;

@Entity(tableName = TABLE_NAME)
public class FiveDayForecastEntity {

    public static final String TABLE_NAME = "fiveDay";

    @PrimaryKey(autoGenerate = true)
    private int id;
    private java.util.List<List> list = null;

    public FiveDayForecastEntity(int id, java.util.List<List> list) {
        this.id = id;
        this.list = list;
    }

    public java.util.List<List> getList() {
        return list;
    }
    public void setList(java.util.List<List> list) {
        this.list = list;
    }
}
