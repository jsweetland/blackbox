package com.blackbox.siteSpecific.framework.base;

import java.util.Map;


public class SiteDeployment {
    public String siteUrl;
    public String deploymentFlag, browserFlag;
    public BrowserType browserType;

    public SiteDeployment() {
        // Load the system deployment variables
        Map<String, String> systemEnvironment = System.getenv();
        deploymentFlag = System.getProperty("blackbox.environment", "local");
        browserFlag = System.getProperty("blackbox.browser", "chrome");

        if(deploymentFlag.toUpperCase().equals("LOCAL")) {
            siteUrl = "http://www.google.com/";
        }

        // Set the browser type
        if(browserFlag.toUpperCase().equals("FIREFOX")) {
            browserType = BrowserType.MOZILLA_FIREFOX;
        } else if(browserFlag.toUpperCase().equals("CHROME")) {
            browserType = BrowserType.GOOGLE_CHROME;
        }
    }
}

