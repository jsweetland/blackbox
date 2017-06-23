package com.blackbox.common.helpers;

import java.text.ParseException;

public class TimeOfDay {
    public static final String AM = "AM";
    public static final String PM = "PM";

    private int hours, minutes;
    private String amPm;

    public TimeOfDay(int newHours, int newMinutes, String newAmPm) {
        hours = newHours;
        minutes = newMinutes;
        amPm = newAmPm;
    }

    public TimeOfDay(String newHours, String newMinutes, String newAmPm) {
        hours = Integer.parseInt(newHours);
        minutes = Integer.parseInt(newMinutes);
        amPm = newAmPm;
    }

    public TimeOfDay(String newHours, int newMinutes, String newAmPm) {
        hours = Integer.parseInt(newHours);
        minutes = newMinutes;
        amPm = newAmPm;
    }

    public TimeOfDay(int newHours, String newMinutes, String newAmPm) {
        hours = newHours;
        minutes = Integer.parseInt(newMinutes);
        amPm = newAmPm;
    }

    public TimeOfDay(TimeOfDay objectToCopy) {
        hours = objectToCopy.hours;
        minutes = objectToCopy.minutes;
        amPm = objectToCopy.amPm;
    }

    public int getHours() {
        return hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public String getMinutesString() {
        if(getMinutes() < 10) {
            return String.format("0%d", getMinutes());
        } else {
            return Integer.toString(getMinutes());
        }
    }

    public String getAmPm() { return amPm; }

    public boolean equals(TimeOfDay objectToCompare) {
        return (hours == objectToCompare.getHours() && minutes == objectToCompare.getMinutes()) && amPm.equals(objectToCompare.getAmPm());
    }

    public String toString() {
        return String.format("%d:%s %s", getHours(), getMinutesString(), getAmPm());
    }
}
