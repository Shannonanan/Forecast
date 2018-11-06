package co.za.forecast.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeCreator {
    public static String getTodaysDate(){
        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dateToStr = format.format(today);
        System.out.println(dateToStr);

        return  dateToStr;
    }

    public static String getTimeNow() {
        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("hh");
        String timeToString = format.format(today);
        return timeToString;
    }

    public static boolean isTimemoreThan2hrsPast(String past){
        String now = getTimeNow();
        int nowInt = Integer.parseInt(now);
        int pastInt = Integer.parseInt(past);
        int threeHours = 3;
        return nowInt - pastInt >= threeHours;
    }
}
