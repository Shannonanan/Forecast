package co.za.forecast.features.showWeather;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import co.za.forecast.data.WeatherRepository;
import co.za.forecast.di.InjectorUtils;
import co.za.forecast.features.viewControls.ViewMvcFactory;

public class ShowWeatherFragment extends Fragment implements ShowWeatherContract.Listener{
    String latitude;
    String longitude;

    ShowWeatherPresenter showWeatherPresenter;
    ViewMvcFactory mViewMvcFactory;

    private ShowWeatherContract mViewMvc;
    public ShowWeatherFragment() { setRetainInstance(true); }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewMvcFactory = InjectorUtils.provideViewMvcFactory(getContext());
        showWeatherPresenter = InjectorUtils.providePresenter();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mViewMvc  = mViewMvcFactory.newInstance(ShowWeatherContract.class, container);
        mViewMvc.registerListener(this);
        return mViewMvc.getRootView();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        latitude = String.valueOf(getArguments().getDouble("lat"));
        longitude = String.valueOf(getArguments().getDouble("longs"));
        if(savedInstanceState == null){
        this.showWeatherPresenter.setView(mViewMvc);
        showWeatherPresenter.getCurrentWeather(latitude,longitude);}

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mViewMvc.unregisterListener(this);
    }




}


