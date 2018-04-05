package com.example.classkeeper;

import android.widget.DatePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

/**
 * Created by benne on 3/25/2018.
 */

public class Constants {
    public static ArrayList<String> CourseStatuses = new ArrayList<String>(Arrays.asList("In Progress", "Completed", "Dropped", "Plan To Take"));
    public static ArrayList<String> AssessmentTypes = new ArrayList<String>(Arrays.asList("Performance", "Objective"));

    public static String getTermDateFromDatePicker(DatePicker picker) {
        int day = picker.getDayOfMonth();
        int month = picker.getMonth();
        int year = picker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-mm-yyyy");
        return dateFormatter.format(calendar.getTime());
    }
}
