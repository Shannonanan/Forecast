package co.za.forecast.features.showWeather;


import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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


    private ShowWeatherAdapter showWeatherAdapter;

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
        tv_conditions.setText(conditions.toUpperCase(Locale.getDefault()));
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
        String maintemp = getString(R.string.show_temp,TemperatureConverter.convertKelvinToCelsius(currentWeather.getMain().getTemp()),getString(R.string.degree_symbol));
        String minTemp = getString(R.string.show_temp,TemperatureConverter.convertKelvinToCelsius(currentWeather.getMain().getTempMin()),getString(R.string.degree_symbol));
        String maxTemp = getString(R.string.show_temp,TemperatureConverter.convertKelvinToCelsius(currentWeather.getMain().getTempMax()),getString(R.string.degree_symbol));

        tv_temp_main.setText(maintemp);
        tv_current_temp.setText(maintemp);
        tv_min_temp.setText(minTemp);
        tv_max_temp.setText(maxTemp);

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

    @Override
    public void getInfoFailed() {
        Toast.makeText(getContext(), getString(R.string.no_info_found), Toast.LENGTH_LONG).show();
    }


}
