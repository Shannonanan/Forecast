package co.za.forecast.features.showWeather;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import co.za.forecast.R;
import co.za.forecast.di.InjectorUtils;
import co.za.forecast.utils.ViewModelHolder;

public class ShowWeatherActivity extends BaseActivity {

    public static final String TASKS_VIEWMODEL_TAG = "TASKS_VIEWMODEL_TAG";
    private ShowWeatherViewModel showWeatherViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_weather);

        ShowWeatherFragment showWeatherFragment = findOrCreateViewFragment();

        showWeatherViewModel = findOrCreateViewModel();

        // Link View and ViewModel
        ShowWeatherFragment.setViewModel(showWeatherViewModel);

    }

    private ShowWeatherViewModel findOrCreateViewModel() {
        // In a configuration change we might have a ViewModel present. It's retained using the
        // Fragment Manager.
        @SuppressWarnings("unchecked")
        ViewModelHolder<ShowWeatherViewModel> retainedViewModel =
                (ViewModelHolder<ShowWeatherViewModel>) getSupportFragmentManager()
                        .findFragmentByTag(TASKS_VIEWMODEL_TAG);

        if (retainedViewModel != null && retainedViewModel.getViewmodel() != null) {
            // If the model was retained, return it.
            return retainedViewModel.getViewmodel();
        } else {
            // There is no ViewModel yet, create it.
            ShowWeatherViewModel viewModel = new ShowWeatherViewModel(
                    InjectorUtils.provideRepository());
            // and bind it to this Activity's lifecycle using the Fragment Manager.
            addFragment(
                    getSupportFragmentManager(),
                    ViewModelHolder.createContainer(viewModel),
                    TASKS_VIEWMODEL_TAG);
            return viewModel;
        }
    }

    @NonNull
    private ShowWeatherFragment findOrCreateViewFragment() {
        ShowWeatherFragment showWeatherFragment =
                (ShowWeatherFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (showWeatherFragment == null) {
            // Create the fragment
            showWeatherFragment = ShowWeatherFragment.newInstance();
            addFragment(
                    getSupportFragmentManager(), showWeatherFragment, R.id.contentFrame);
        }
        return showWeatherFragment;
    }
}
