package co.za.forecast.features.showWeather;

import co.za.forecast.features.showWeather.domain.model.CurrentWeather;
import co.za.forecast.features.showWeather.domain.model.List;
import co.za.forecast.features.viewControls.ObservableViewMvc;

public interface ShowWeatherContract extends ObservableViewMvc<ShowWeatherContract.Listener> {


       interface Listener{
        void changeStatusColour(String condition);
    }

    void renderCurrentWeather(CurrentWeather currentWeather);
    void renderFiveDayForecast(java.util.List<List> renderCollection);

}
