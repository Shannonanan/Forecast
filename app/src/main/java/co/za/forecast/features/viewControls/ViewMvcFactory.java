package co.za.forecast.features.viewControls;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import co.za.forecast.features.showWeather.ShowWeatherContract;
import co.za.forecast.features.showWeather.ShowWeatherViewImpl;

public class ViewMvcFactory {

    private final LayoutInflater mLayoutInflater;
    private static ViewMvcFactory sInstance;
    private static final Object LOCK = new Object();

    public ViewMvcFactory(LayoutInflater layoutInflater) {
        mLayoutInflater = layoutInflater;
        }

    /**
     * Instantiate a new implementation of MVC view. The returned instance will be casted to MVC view
     * type inferred by java's automatic type inference.
     * @param mvcViewClass the class of the required MVC view
     * @param container this container will be used as MVC view's parent. See {@link LayoutInflater#inflate(int, ViewGroup)}
     * @param <T> the type of the required MVC view
     * @return new instance of MVC view
     */
    public <T extends ViewMvc> T newInstance(Class<T> mvcViewClass, @Nullable ViewGroup container) {

        ViewMvc viewMvc = null;

        if (mvcViewClass == ShowWeatherContract.class) {
            viewMvc = new ShowWeatherViewImpl(mLayoutInflater, container);
        }
        else {
            throw new IllegalArgumentException("unsupported MVC view class " + mvcViewClass);
        }
        //noinspection unchecked
        return (T) viewMvc;
    }


    public static ViewMvcFactory getInstance(LayoutInflater layoutInflater){
        if(sInstance == null){
            synchronized (LOCK){
                sInstance = new ViewMvcFactory(layoutInflater);
            }
        }
        return sInstance;
    }
}
