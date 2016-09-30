package com.bliss31.todoapp.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by jdemott on 9/30/16.
 */

public class TodoDate {
    public static String longToString(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        SimpleDateFormat format = new SimpleDateFormat("MMM-dd-yyyy");
        String timeString = format.format(calendar.getTime());
        return timeString;
    }
}
