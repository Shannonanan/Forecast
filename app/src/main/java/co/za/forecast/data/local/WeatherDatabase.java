package co.za.forecast.data.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.util.Log;

@Database(entities = {CurrentWeatherEntity.class, SingleFiveDayForecastEntity.class}, version = 1, exportSchema = false)
@TypeConverters({DateConverter.class, MainObjectConverter.class, WeatherObjectConverter.class, ListObjectConverter.class})
public abstract class WeatherDatabase extends RoomDatabase{

    private static final String LOG_TAG = WeatherDatabase.class.getSimpleName();
    private static final String DATABASE_NAME = "weather";

    private static final Object LOCK = new Object();
    private static WeatherDatabase sInstance;

    public static WeatherDatabase getInstance(Context context) {
        Log.d(LOG_TAG, "Getting the database");
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        WeatherDatabase.class, WeatherDatabase.DATABASE_NAME)
                        .build();
                Log.d(LOG_TAG, "Made new database");
            }
        }
        return sInstance;
    }

    public abstract CurrentWeatherDao currentDao();
    public abstract FiveDayWeatherDao fiveDayDao();

}



