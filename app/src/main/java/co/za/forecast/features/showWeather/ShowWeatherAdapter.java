package co.za.forecast.features.showWeather;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;


import butterknife.BindView;
import butterknife.ButterKnife;

import co.za.forecast.R;
import co.za.forecast.features.showWeather.domain.model.List;
import co.za.forecast.features.showWeather.domain.model.Weather;


public class ShowWeatherAdapter extends RecyclerView.Adapter<ShowWeatherAdapter.ShowWeatherViewHolder> {



    private java.util.List<List> getWeatherCollection;
    private Context mContext;


    public ShowWeatherAdapter(Context mContext) {
        this.getWeatherCollection = Collections.emptyList();
        this.mContext = mContext;

    }

    @NonNull
    @Override
    public ShowWeatherViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.cell_five_day_forecast;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        ShowWeatherViewHolder viewHolder = new ShowWeatherViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ShowWeatherViewHolder showWeatherViewHolder, int i) {
        final List weatherToday = this.getWeatherCollection.get(i);

        String dayOfWeek = weatherToday.getDtTxt();
       showWeatherViewHolder.tv_day_of_week.setText(weatherToday.getDtTxt());
       String tempToday = String.valueOf(weatherToday.getMain().getTemp());
       showWeatherViewHolder.tv_temperature.setText(tempToday);
        java.util.List<Weather> getCondition = new ArrayList<>(weatherToday.getWeather());
        String conditions = getCondition.get(0).getMain();
        switch (conditions)
        {
            case "Clear":
                showWeatherViewHolder.iv_icon_condition.setImageDrawable(mContext.getResources().getDrawable(
                        R.drawable.clear));
                break;
            case "Clouds":
                showWeatherViewHolder.iv_icon_condition.setImageDrawable(mContext.getResources().getDrawable(
                        R.drawable.clouds));
                break;
            case "Rain":
                showWeatherViewHolder.iv_icon_condition.setImageDrawable(mContext.getResources().getDrawable(
                        R.drawable.rain));
                break;
            case "Thunderstorm":
                showWeatherViewHolder.iv_icon_condition.setImageDrawable(mContext.getResources().getDrawable(
                        R.drawable.rain));
                break;
            default:
                break;
        }

    }

    @Override
    public int getItemCount() {
        return (this.getWeatherCollection != null) ? this.getWeatherCollection.size() : 0;
    }


    static class ShowWeatherViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_day_of_week) TextView tv_day_of_week;
        @BindView(R.id.tv_temperature) TextView tv_temperature;
        @BindView(R.id.iv_icon_condition) ImageView iv_icon_condition;

        public ShowWeatherViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    public void setInfoCollection(java.util.List<List> INFO) {
        this.validateCollection(INFO);
        this.getWeatherCollection = INFO;
        this.notifyDataSetChanged();
    }


    private void validateCollection(Collection<List> infoCollection) {
        if (infoCollection == null) {
            throw new IllegalArgumentException("The list cannot be null");
        }
    }

}
