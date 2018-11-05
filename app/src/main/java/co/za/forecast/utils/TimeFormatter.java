package co.za.forecast.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeFormatter {
    public static String getDayOfTheWeek(String dateRecorded) {

        String dayOfTheWeek = "";
        String pattern = "yyyy-MM-dd";
        String date = dateRecorded;


        try {
            DateFormat df = new SimpleDateFormat(pattern);
            Date today = df.parse(date);

            SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
            dayOfTheWeek = sdf.format(today);

        } catch (ParseException ex) {
            String message = ex.getMessage();
        }

        return dayOfTheWeek;

    }
}
