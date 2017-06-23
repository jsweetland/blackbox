package com.blackbox.common.helpers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateRange {
    Calendar startDate, endDate;
    public static final DateFormat dateFormatting = new SimpleDateFormat("MM/dd/yyyy");

    public DateRange(Date newStartDate, Date newEndDate) {
        startDate = Calendar.getInstance();
        endDate = Calendar.getInstance();

        startDate.setTime(newStartDate);
        endDate.setTime(newEndDate);
    }

    public DateRange(Date newStartDate, int timeUnit, int count) {
        startDate = Calendar.getInstance();
        endDate = Calendar.getInstance();

        // Set the dates to the specified start date and add the indicated number of specified time units
        startDate.setTime(newStartDate);
        endDate.setTime(startDate.getTime());
        endDate.add(timeUnit, count);
    }

    public DateRange(String newStartDate, String newEndDate) {
        startDate = Calendar.getInstance();
        endDate = Calendar.getInstance();

        // Parse the date strings and set the values
        startDate.setTime(parseDateString(newStartDate));
        endDate.setTime(parseDateString(newEndDate));
    }

    public DateRange(String newStartDate, Date newEndDate) {
        startDate = Calendar.getInstance();
        endDate = Calendar.getInstance();

        // Parse the date strings and set the values
        startDate.setTime(parseDateString(newStartDate));
        endDate.setTime(newEndDate);
    }

    public DateRange(Date newStartDate, String newEndDate) {
        startDate = Calendar.getInstance();
        endDate = Calendar.getInstance();

        // Parse the date strings and set the values
        startDate.setTime(newStartDate);
        endDate.setTime(parseDateString(newEndDate));
    }

    public DateRange(String newStartDate, int timeUnit, int count) {
        startDate = Calendar.getInstance();
        endDate = Calendar.getInstance();

        // Set the dates to the specified start date and add the indicated number of specified time units
        startDate.setTime(parseDateString(newStartDate));
        endDate.setTime(startDate.getTime());
        endDate.add(timeUnit, count);
    }

    private Date parseDateString(String dateToParse) {
        try { return dateFormatting.parse(dateToParse); }
        catch(ParseException pe) { throw new RuntimeException(String.format("Invalid date format of '%s'", dateToParse)); }
    }

    public String getStartDate() {
        return dateFormatting.format(startDate.getTime());
    }

    public String getEndDate() {
        return dateFormatting.format(endDate.getTime());
    }
}
