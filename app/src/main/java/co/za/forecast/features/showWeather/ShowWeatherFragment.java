package co.za.forecast.features.showWeather;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import co.za.forecast.R;
import co.za.forecast.di.InjectorUtils;
import co.za.forecast.features.showWeather.domain.model.CurrentWeather;
import co.za.forecast.features.viewControls.ViewMvcFactory;

public class ShowWeatherFragment extends Fragment implements ShowWeatherContract.Listener {
    String latitude;
    String longitude;

    ShowWeatherPresenter showWeatherPresenter;
    ViewMvcFactory mViewMvcFactory;
    public onConditionChangeListener onConditionChangeListener;
    CurrentWeather currentWeather;

    private ShowWeatherContract mViewMvc;

    public ShowWeatherFragment() {
        setRetainInstance(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewMvcFactory = InjectorUtils.provideViewMvcFactory(getContext());
        showWeatherPresenter = InjectorUtils.providePresenter(getContext());
        onConditionChangeListener = (onConditionChangeListener) getContext();
        currentWeather = new CurrentWeather();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mViewMvc = mViewMvcFactory.newInstance(ShowWeatherContract.class, container);
        mViewMvc.registerListener(this);
        return mViewMvc.getRootView();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        latitude = String.valueOf(getArguments().getDouble(getString(R.string.lat)));
        longitude = String.valueOf(getArguments().getDouble(getString(R.string.longs)));
        if (savedInstanceState == null) {
            this.showWeatherPresenter.setView(mViewMvc);
            showWeatherPresenter.getCurrentWeather(latitude, longitude);
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mViewMvc.unregisterListener(this);
    }


    public interface onConditionChangeListener {
        void onConditionChangeColour(String condition);
    }

    @Override
    public void changeStatusColour(String condition) {
        onConditionChangeListener.onConditionChangeColour(condition);

    }

}


