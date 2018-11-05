package co.za.forecast.features.showWeather;

import android.content.res.Resources;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.za.forecast.R;
import co.za.forecast.features.showWeather.domain.model.CurrentWeather;
import co.za.forecast.features.showWeather.domain.model.Weather;
import co.za.forecast.features.viewControls.BaseViewMvc;
import co.za.forecast.utils.TemperatureConverter;

public class ShowWeatherViewImpl extends BaseViewMvc<ShowWeatherContract.Listener>
        implements ShowWeatherContract {

    @BindView(R.id.rv_five_day_forecast)
    RecyclerView mRecyclerView;
    @BindView(R.id.iv_main_weather_pic)
    ImageView iv_main_weather_pic;
    @BindView(R.id.tv_min_temp)
    TextView tv_min_temp;
    @BindView(R.id.tv_current_temp)
    TextView tv_current_temp;
    @BindView(R.id.tv_max_temp)
    TextView tv_max_temp;
    @BindView(R.id.tv_conditions)
    TextView tv_conditions;
    @BindView(R.id.tv_temp)
    TextView tv_temp_main;
    @BindView(R.id.constraint_layout)
    ConstraintLayout constraint_layout;
    static final String DEGREE = "°";

    ShowWeatherAdapter showWeatherAdapter;

    public ShowWeatherViewImpl(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_show_weather, container, false);
        setRootView(view);
        ButterKnife.bind(this, view);
        setupViews();
    }

    private void setupViews() {

        LinearLayoutManager gridLayoutManager =
                new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        showWeatherAdapter = new ShowWeatherAdapter(getContext());
        mRecyclerView.setAdapter(showWeatherAdapter);
    }

    @Override
    public void renderCurrentWeather(CurrentWeather currentWeather) {
        List<Weather> getCondition = new ArrayList<>(currentWeather.getWeather());
        String conditions = getCondition.get(0).getMain();
        tv_conditions.setText(conditions.toUpperCase());
        switch (conditions) {
            case "Clear":
                iv_main_weather_pic.setImageDrawable(getContext().getResources().getDrawable(
                        R.drawable.sea_sunnypng));
                constraint_layout.setBackgroundColor(Color.parseColor("#5190E0"));
                break;
            case "Clouds":
                iv_main_weather_pic.setImageDrawable(getContext().getResources().getDrawable(
                        R.drawable.sea_cloudy));
                constraint_layout.setBackgroundColor(Color.parseColor("#54717A"));

                break;
            case "Thunderstorm":
                iv_main_weather_pic.setImageDrawable(getContext().getResources().getDrawable(
                        R.drawable.sea_rainy));
                constraint_layout.setBackgroundColor(Color.parseColor("#57575D"));
                break;
            case "Rain":
                iv_main_weather_pic.setImageDrawable(getContext().getResources().getDrawable(
                        R.drawable.sea_rainy));
                constraint_layout.setBackgroundColor(Color.parseColor("#57575D"));
                break;
            default:
                break;

        }
        String maintemp = TemperatureConverter.convertKelvinToCelsius(currentWeather.getMain().getTemp());
        String minTemp = TemperatureConverter.convertKelvinToCelsius(currentWeather.getMain().getTempMin());
        String maxTemp = TemperatureConverter.convertKelvinToCelsius(currentWeather.getMain().getTempMax());
        tv_temp_main.setText(maintemp + DEGREE);
        tv_current_temp.setText(maintemp + DEGREE);
        tv_min_temp.setText(minTemp + DEGREE);
        tv_max_temp.setText(maxTemp + DEGREE);

        for (Listener listener: getListeners()) {
            listener.changeStatusColour(conditions);
        }
    }

    @Override
    public void renderFiveDayForecast(java.util.List<co.za.forecast.features.showWeather.domain.model.List> renderCollection) {
        if (renderCollection != null) {
            this.showWeatherAdapter.setInfoCollection(renderCollection);
        }
    }


}
