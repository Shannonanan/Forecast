package co.za.forecast.features.showWeather;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;

public class ShowWeatherFragment extends Fragment {

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