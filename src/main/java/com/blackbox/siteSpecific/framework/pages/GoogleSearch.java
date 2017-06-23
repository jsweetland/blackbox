package com.blackbox.siteSpecific.framework.pages;

import com.blackbox.common.selenium.DriverUtil;
import com.blackbox.common.selenium.WebSite;


public class GoogleSearch extends com.blackbox.siteSpecific.framework.base.SiteWebPage {
    private static final String PAGE_TITLE = "Google";

    // Login form elements
    private static final String GOOGLE_LOGO = "id=hplogo";

    public GoogleSearch(DriverUtil driverutil, WebSite webSite) {
        super(driverutil, webSite);
    }

    @Override
    public String getPageTitle() {
        return PAGE_TITLE;
    }


    // <editor-fold desc="Wait for Page to Load">

    public void waitForPageToLoad() {
        driverutil.waitForVisibleElement(GOOGLE_LOGO, 60);
    }

    // </editor-fold>
}

