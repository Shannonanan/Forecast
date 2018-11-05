package co.za.forecast.utils;

public class TemperatureConverter {
    public static TemperatureConverter sInstance;
    private static final Object LOCK = new Object();

    public TemperatureConverter() {
    }


    public String convertKelvinToCelsius(Double kelvin){
        // Kelvin to Degree Celsius Conversion
        String str = String.valueOf(kelvin - 273.15);
        return str.substring(0,str.indexOf('.'));
    }

    public static TemperatureConverter getInstance() {
        if(sInstance == null){
            sInstance = new TemperatureConverter();
        }
       return sInstance;
    }
}
