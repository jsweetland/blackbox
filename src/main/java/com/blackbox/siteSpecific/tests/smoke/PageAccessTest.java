package com.blackbox.siteSpecific.tests.smoke;

import com.blackbox.siteSpecific.framework.base.SiteTest;
import org.testng.annotations.Test;


public class PageAccessTest extends SiteTest {
    @Test
    public void accessMainPage() {
        // Open the site
        mySite.openSite(deployment);

        // Wait for the logo to appear
        mySite.googleSearch().waitForPageToLoad();
    }
}

