package co.za.forecast.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateTimeCreator {
    public static String getTodaysDate(){
        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return format.format(today);
    }

    static String getTimeNow() {
        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("hh", Locale.getDefault());
        return format.format(today);
    }

    public static boolean isTimemoreThan3hrsPast(String past){
        String now = getTimeNow();
        int nowInt = Integer.parseInt(now);
        int pastInt = Integer.parseInt(past);
        int threeHours = 3;
        return nowInt - pastInt >= threeHours;
    }
}
