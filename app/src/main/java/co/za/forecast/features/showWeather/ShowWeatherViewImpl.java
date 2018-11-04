package co.za.forecast.features.showWeather;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.za.forecast.R;
import co.za.forecast.features.showWeather.domain.model.CurrentWeather;
import co.za.forecast.features.showWeather.domain.model.Weather;
import co.za.forecast.features.viewControls.BaseViewMvc;

public class ShowWeatherViewImpl extends BaseViewMvc<ShowWeatherContract.Listener>
        implements ShowWeatherContract {

    @BindView(R.id.rv_five_day_forecast) RecyclerView mRecyclerView;
    @BindView(R.id.iv_main_weather_pic) ImageView iv_main_weather_pic;
    @BindView(R.id.tv_min_temp) TextView tv_min_temp;
    @BindView(R.id.tv_current_temp) TextView tv_current_temp;
    @BindView(R.id.tv_max_temp) TextView tv_max_temp;
    @BindView(R.id.tv_conditions) TextView tv_conditions;
    @BindView(R.id.tv_temp) TextView tv_temp_main;

    ShowWeatherAdapter showWeatherAdapter;

    public ShowWeatherViewImpl(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_show_weather, container, false);
        setRootView(view);
        ButterKnife.bind(this, view);
        setupViews();
    }

    private void setupViews() {
    }

    @Override
    public void renderCurrentWeather(CurrentWeather currentWeather) {
        //convert temp from kelvin to celsius
        List<Weather> getCondition = new ArrayList<>();
        getCondition.addAll(currentWeather.getWeather());
        String conditions = getCondition.get(0).getMain();
        switch ("Rainy")
        {
            case "Clear":
                iv_main_weather_pic.setImageDrawable(getContext().getResources().getDrawable(
                        R.drawable.sea_sunnypng));
                break;
            case "Cloudy":
                iv_main_weather_pic.setImageDrawable(getContext().getResources().getDrawable(
                        R.drawable.sea_cloudy));
                break;
            case "Rainy":
                iv_main_weather_pic.setImageDrawable(getContext().getResources().getDrawable(
                        R.drawable.sea_rainy));
                break;
                default:
                    break;
        }
        String maintemp = String.valueOf(currentWeather.getMain().getTemp());
        String minTemp = String.valueOf(currentWeather.getMain().getTempMin());
        String maxTemp = String.valueOf(currentWeather.getMain().getTempMax());
        tv_temp_main.setText(maintemp);
        tv_current_temp.setText(maintemp);
        tv_min_temp.setText(minTemp);
        tv_max_temp.setText(maxTemp);
    }

    @Override
    public void renderFiveDayForecast(java.util.List<co.za.forecast.features.showWeather.domain.model.List> renderCollection) {
        if (renderCollection != null) {
            this.showWeatherAdapter.setInfoCollection(renderCollection);
        }
    }


}
