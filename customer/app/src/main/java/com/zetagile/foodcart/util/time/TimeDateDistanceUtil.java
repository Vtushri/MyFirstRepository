package com.zetagile.foodcart.util.time;

import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimeDateDistanceUtil {

    public static String getDurationAmPm(long milliseconds) {
        int hrs = getHours(milliseconds);
        int min = getMinutes(milliseconds);

        SimpleDateFormat format24 = new SimpleDateFormat("HH:mm", Locale.US);
        SimpleDateFormat format12 = new SimpleDateFormat("hh:mm a", Locale.US);
        try {
            return format12.format(format24.parse(hrs + ":" + min));
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getDurationHrMin(long milliseconds) {
        if(milliseconds > 0) {
            int hrs = getHours(milliseconds);
            int mins = getMinutes(milliseconds);

            String duration;

            if (hrs == 0) {
                duration = mins + "min";
            } else if (mins == 0) {
                duration = hrs + "h ";
            } else
                duration = hrs + "h " + mins + "min";

            return duration;
        }
        return "NA";
    }

    public static long getDurationOfTodayTime(long time_in_milliseconds) {
        long dateTime = getTodayDateTime(0,0);

        if(time_in_milliseconds > dateTime)
            return time_in_milliseconds - dateTime;
        return 0;
    }

    public static long getTodayDateTime(int hourOfDay, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        return calendar.getTime().getTime();
    }

    private static int getHours(long duration_milliseconds) {
        return (int) (( duration_milliseconds / (1000 * 60 * 60)) % 24);
    }

    private static int getMinutes(long duration_milliseconds) {
        return (int) ((duration_milliseconds / (60 * 1000)) % 60);
    }

    public static long getCurrentMillis() {
        return new Date().getTime();
    }

    public static int getHourOfDay(long time_milliseconds) {
        if(time_milliseconds > 0) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(time_milliseconds);
            return calendar.get(Calendar.HOUR_OF_DAY);
        }
        return 0;
    }

    public static int getMinuteOfDay(long time_milliseconds) {
        if(time_milliseconds > 0) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(time_milliseconds);
            return calendar.get(Calendar.MINUTE);
        }
        return 0;
    }

    public static String getDistance(long distance_in_meters) {
        float distance_km = distance_in_meters / 1000;

        if(distance_in_meters > 0)
            return String.format("%.1f", distance_km)+"km";
        return "NA";
    }

    public static String addAndReturnDateFormat(long milliseconds) {

        Date date = new Date();
        long time = date.getTime() + milliseconds;

        SimpleDateFormat format = new SimpleDateFormat("hh:mm a");
        return format.format(new Date(time));
    }

    public static String getTimeOfDate(long timeInMillis) {
        long duration = getDurationOfTodayTime(timeInMillis);
        return getDurationAmPm(duration);
    }
}
