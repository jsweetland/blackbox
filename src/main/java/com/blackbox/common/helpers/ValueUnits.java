package com.blackbox.common.helpers;

public class ValueUnits {
    // Common units
    public static final String MINUTE = "Minute";
    public static final String HOUR = "Hour";
    public static final String DAY = "Day";
    public static final String WEEK = "Week";
    public static final String MONTH = "Month";
    public static final String YEAR = "Year";

    private String value, units;

    public ValueUnits(String newValue, String newUnits) {
        value = newValue;
        units = newUnits;
    }

    public ValueUnits(int newValue, String newUnits) {
        value = Integer.toString(newValue);
        units = newUnits;
    }

    public ValueUnits(ValueUnits objectToCopy) {
        value = objectToCopy.getValue();
        units = objectToCopy.getUnits();
    }

    public String getValue() { return value; }

    public String getUnits() {
        return units;
    }

    public boolean equals(ValueUnits objectToCompare) {
        return (getValue().equals(objectToCompare.getValue()) && getUnits().equals(objectToCompare.getUnits()));
    }
}
