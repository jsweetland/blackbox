package com.blackbox.common.helpers;

/**
 * Created by sweetlandj on 11/10/15.
 */
public class ValueRange {
    public int lowest, highest;

    // Constructor with lowest and highest values specified
    public ValueRange(int newLowest, int newHighest) {
        if(newLowest > newHighest) {
            lowest = newHighest;
            highest = newLowest;
        } else {
            lowest = newLowest;
            highest = newHighest;
        }
    }

    // Copy constructor
    public ValueRange(ValueRange objectToCopy) {
        lowest = objectToCopy.lowest;
        highest = objectToCopy.highest;
    }

    // Equality test
    public boolean equals(ValueRange objectToCompare) {
        return (lowest == objectToCompare.lowest) && (highest == objectToCompare.highest);
    }
}
