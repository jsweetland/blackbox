package com.blackbox.common.selenium;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;


public abstract class WebSite {
    private WebPage currentPage;
    protected DriverUtil driverutil;
    private final static int PAGE_NAVIGATION_TIMEOUT = 30;

    // <editor-fold desc="Constructor">

    public WebSite(WebDriver driver) {
        this.driverutil = new DriverUtil(driver);
    }

    // </editor-fold>


    // <editor-fold desc="Public Methods">

    public void quit() {
        driverutil.quit();
    }

    public WebPage getCurrentPage(Class<? extends WebPage> pageClass) {
        if (currentPage == null)
            throw new WebTestingException(String.format(
                    "The current page is not an instance of the %s class. The current page is not set (null).",
                    pageClass.getName())
            );

        if (!pageClass.isInstance(currentPage))
            throw new WebTestingException(String.format(
                    "The current page is not an instance of the %s class. The current page is an instance of the %s class.",
                    pageClass.getName(),
                    currentPage.getClass().getName())
            );

        return currentPage;
    }

    public boolean isCurrentPage(Class<? extends WebPage> pageClass) {
        return pageClass.isInstance(currentPage);
    }

    public <T extends WebPage> T setCurrentPage(Class<? extends WebPage> pageClass) {
        WebPage page = createPageInstance(pageClass);
        checkSamePageAsDriverPage(page);

        currentPage = page;

        return (T) currentPage;
    }

    public <T extends WebPage> T getCurrentPage() {
        return (T) currentPage;
    }

    // </editor-fold>


    // <editor-fold desc="Private Helper Methods">

    private void checkSamePageAsDriverPage(WebPage page) {
        int secondsElapsed = 0;

        while (!page.verifyTitle() && secondsElapsed < PAGE_NAVIGATION_TIMEOUT) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }

            secondsElapsed++;
        }

        if (secondsElapsed >= PAGE_NAVIGATION_TIMEOUT) {
            throw new WebTestingException(String.format(
                    "Page titles mismatch.  The expected page title is %s and the actual page title is %s.",
                    page.getPageTitle(),
                    driverutil.getPageTitle())
            );
        }
    }

    private WebPage createPageInstance(Class<? extends WebPage> pageClass) {
        WebPage page;

        try {
            page = createWebPageClass(pageClass);
        } catch (Exception e) {
            throw new WebTestingException(
                    String.format("Unable to create an instance of class %s.", pageClass.getName()),
                    e
            );
        }

        return page;
    }

    private WebPage createWebPageClass(Class<? extends WebPage> pageClass) throws
            NoSuchMethodException,
            InstantiationException,
            IllegalAccessException,
            InvocationTargetException {
        Constructor<? extends WebPage> ctor = pageClass.getDeclaredConstructor(new Class[]{DriverUtil.class, WebSite.class});
        Object obj = ctor.newInstance(driverutil, this);

        return pageClass.cast(obj);
    }

    // </editor-fold>
}
