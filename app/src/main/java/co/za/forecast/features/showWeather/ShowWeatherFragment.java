package co.za.forecast.features.showWeather;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ShowWeatherFragment extends Fragment {

    private ShowWeatherViewModel showWeatherViewModel;
   // private TasksFragBinding mTasksFragBinding;
  // private TasksAdapter mListAdapter;

    public static ShowWeatherFragment newInstance() {
        return new ShowWeatherFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        showWeatherViewModel.start();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        mTasksFragBinding = TasksFragBinding.inflate(inflater, container, false);
//
//        mTasksFragBinding.setView(this);
//
//        mTasksFragBinding.setViewmodel(mTasksViewModel);
//
//        setHasOptionsMenu(true);
//
//        View root = mTasksFragBinding.getRoot();

        return null;
    }

    public void setViewModel(ShowWeatherViewModel viewModel) {
        showWeatherViewModel = viewModel;
    }
}


//    /**
//     * Checks if the device has any active internet connection.
//     *
//     * @return true device with internet connection, otherwise false.
//     */
//    private boolean isThereInternetConnection() {
//        boolean isConnected;
//
//        ConnectivityManager connectivityManager =
//                (ConnectivityManager) this.mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
//        isConnected = (networkInfo != null && networkInfo.isConnectedOrConnecting());
//
//        return isConnected;
//    }
//if(isThereInternetConnection()){}