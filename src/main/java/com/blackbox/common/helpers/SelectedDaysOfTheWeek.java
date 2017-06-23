package com.blackbox.common.helpers;

/**
 * Created by sweetlandj on 11/10/15.
 */
public class SelectedDaysOfTheWeek {
    private boolean monday, tuesday, wednesday, thursday, friday, saturday, sunday;

    // Empty constructor
    public SelectedDaysOfTheWeek() {
        monday = false;
        tuesday = false;
        wednesday = false;
        thursday = false;
        friday = false;
        saturday = false;
        sunday = false;
    }

    // Full constructor
    public SelectedDaysOfTheWeek(boolean newMonday, boolean newTuesday, boolean newWednesday, boolean newThursday, boolean newFriday, boolean newSaturday, boolean newSunday) {
        setMondaySelection(newMonday);
        setTuesdaySelection(newTuesday);
        setWednesdaySelection(newWednesday);
        setThursdaySelection(newThursday);
        setFridaySelection(newFriday);
        setSaturdaySelection(newSaturday);
        setSundaySelection(newSunday);
    }

    // Copy constructor
    public SelectedDaysOfTheWeek(SelectedDaysOfTheWeek objectToCopy) {
        monday = objectToCopy.isMondaySelected();
        tuesday = objectToCopy.isTuesdaySelected();
        wednesday = objectToCopy.isWednesdaySelected();
        thursday = objectToCopy.isThursdaySelected();
        friday = objectToCopy.isFridaySelected();
        saturday = objectToCopy.isSaturdaySelected();
        sunday = objectToCopy.isSundaySelected();
    }

    public boolean isMondaySelected() { return monday; }

    public void setMondaySelection(boolean selection) { monday = selection; }

    public boolean isTuesdaySelected() { return tuesday; }

    public void setTuesdaySelection(boolean selection) { tuesday = selection; }

    public boolean isWednesdaySelected() { return wednesday; }

    public void setWednesdaySelection(boolean selection) { wednesday = selection; }

    public boolean isThursdaySelected() { return thursday; }

    public void setThursdaySelection(boolean selection) { thursday = selection; }

    public boolean isFridaySelected() { return friday; }

    public void setFridaySelection(boolean selection) { friday = selection; }

    public boolean isSaturdaySelected() { return saturday; }

    public void setSaturdaySelection(boolean selection) { saturday = selection; }

    public boolean isSundaySelected() { return sunday; }

    public void setSundaySelection(boolean selection) { sunday = selection; }

    public boolean equals(SelectedDaysOfTheWeek objectToCompare) {
        return (
                (isMondaySelected() == objectToCompare.isMondaySelected()) &&
                (isTuesdaySelected() == objectToCompare.isTuesdaySelected()) &&
                (isWednesdaySelected() == objectToCompare.isWednesdaySelected()) &&
                (isThursdaySelected() == objectToCompare.isThursdaySelected()) &&
                (isFridaySelected() == objectToCompare.isFridaySelected()) &&
                (isSaturdaySelected() == objectToCompare.isSaturdaySelected()) &&
                (isSundaySelected() == objectToCompare.isSundaySelected())
        );
    }
}
