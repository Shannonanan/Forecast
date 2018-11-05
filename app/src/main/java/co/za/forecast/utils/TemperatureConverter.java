package co.za.forecast.utils;

public class TemperatureConverter {
    public static  String convertKelvinToCelsius(Double kelvin){
        String str = String.valueOf(kelvin - 273.15);
        return str.substring(0,str.indexOf('.'));
    }

}
