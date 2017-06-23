package com.blackbox.siteSpecific.framework.base;

import com.blackbox.siteSpecific.framework.pages.*;
import org.openqa.selenium.WebDriver;
import com.blackbox.common.selenium.WebSite;


public class MyWebSite extends WebSite {

    // <editor-fold desc="Constructor">

	public MyWebSite(WebDriver driver) {
		super(driver);
	}

    // </editor-fold>


    // <editor-fold desc="Convenience Method for logging out and going to the root">

    public GoogleSearch openSite(SiteDeployment deployment) {
        driverutil.open(deployment.siteUrl);
        return (GoogleSearch) this.setCurrentPage(GoogleSearch.class);
    }

    // </editor-fold>


    // <editor-fold desc="Convenience Methods for Accessing Pages Classes">

	// Top Level Pages
    public GoogleSearch googleSearch() { return this.getCurrentPage(); }

    // </editor-fold>
}

