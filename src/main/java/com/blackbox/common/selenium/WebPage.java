package com.blackbox.common.selenium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public abstract class WebPage {
    protected final DriverUtil driverutil;
    protected final WebSite webSite;
    public final static String EMPTY_STRING = "";

    public WebPage(DriverUtil driverutil, WebSite webSite) {
        this.driverutil = driverutil;
        this.webSite = webSite;
    }


    // <editor-fold desc="Page Title">

    public abstract String getPageTitle();

    public boolean verifyTitle() {
        return getPageTitle().equals(driverutil.getPageTitle());
    }

    // </editor-fold>


    // <editor-fold desc="Wait for Page to Load">

    public abstract void waitForPageToLoad();

    // </editor-fold>


    // <editor-fold desc="Generic Method to Click an Element and Return a Page Object">

    public <T extends WebPage> T clickAndGoToPage(String elementLocatorToClick, Class<T> pageClass) {
        driverutil.click(elementLocatorToClick);
        return webSite.setCurrentPage(pageClass);
    }

    // </editor-fold>


    // <editor-fold desc="Validate Options for Select Elements">

    public boolean areExpectedSelectOptionsPresent(String selectElement, List<String> expectedOptions) {
        List<String> gatheredOptions = driverutil.getSelectOptions(selectElement);

        // Compare the arrays
        if (gatheredOptions.size() != expectedOptions.size()) {
            return false;
        } else {
            for (int i = 0; i < gatheredOptions.size(); i++) {
                if (!gatheredOptions.get(i).equals(expectedOptions.get(i))) {
                    return false;
                }
            }
        }

        // If there were no mismatches, return true
        return true;
    }

    public boolean areExpectedSelectOptionsPresent(String selectElement, String[] expectedOptions) {
        return areExpectedSelectOptionsPresent(selectElement, Arrays.asList(expectedOptions));
    }

    public boolean areExpectedSelectOptionsPresent(String selectElement, Map<String, String> expectedOptions) {
        return areExpectedSelectOptionsPresent(selectElement, new ArrayList<String>(expectedOptions.values()));
    }

    // </editor-fold>
}
