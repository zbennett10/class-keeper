package com.example.classkeeper;

import android.widget.DatePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by benne on 3/25/2018.
 */

public class Constants {
    public String[] CourseStatuses = {"In Progress", "Completed", "Dropped", "Plan To Take"};
    public String[] AssessmentTypes = {"Performance", "Objective"};

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
