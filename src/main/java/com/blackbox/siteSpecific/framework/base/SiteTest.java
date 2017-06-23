package com.blackbox.siteSpecific.framework.base;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.net.PortProber;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;


public abstract class SiteTest {
    protected MyWebSite mySite;
	public static SiteDeployment deployment = new SiteDeployment();
	private DesiredCapabilities browserCapabilities = new DesiredCapabilities();
	private FirefoxProfile firefoxProfile = new FirefoxProfile();
	private FirefoxBinary firefoxBinary = new FirefoxBinary();
	private String browserPort;
    public static final String EMPTY_STRING = "";
    public static final int rowIndexToValidateTablePopulation = 1;
    public String performanceTestResultsFilePath;
	
	@BeforeTest
	public void testFrameworkSetup() {
		browserCapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		browserPort = Integer.toString(PortProber.findFreePort());

        // Set up the browser
        if(deployment.browserType.equals(BrowserType.MOZILLA_FIREFOX)) {
            firefoxProfile.setPreference(FirefoxProfile.PORT_PREFERENCE, browserPort);
            mySite = new MyWebSite(new FirefoxDriver(firefoxBinary, firefoxProfile, browserCapabilities));
        } else if(deployment.browserType.equals(BrowserType.GOOGLE_CHROME)) {
            System.setProperty("webdriver.chrome.driver", "/opt/chromedriver");
            mySite = new MyWebSite(new ChromeDriver(browserCapabilities));
        }

        performanceTestResultsFilePath = "./performance_test_results";
	}

	@AfterTest
	public void testFrameworkTearDown() {
		mySite.quit();
	}

    public void waitSeconds(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    public double calculateMean(List values) {
        double sum = 0;

        for(int valueIndex = 0; valueIndex < values.size(); valueIndex++) {
            if(values.get(valueIndex) instanceof Double) {
                sum += (Double) values.get(valueIndex);
            } else if(values.get(valueIndex) instanceof Long) {
                sum += (Long) values.get(valueIndex);
            }
        }

        return sum / values.size();
    }

    public double calculateMetricScore(double loadTime) {
        final double metricTimeFactor = 0.05;
        return loadTime * metricTimeFactor;
    }

    public void printIterationMetricSummary(int iteration, ArrayList<Double> pageLoadTimes, ArrayList<Double> totalTimes, ArrayList<Long> entryCounts, String entryType, ArrayList<Double> metricScores) {
        System.out.println(String.format("\t%d: %f ms page load, %f ms table load, %f ms total, %d %s, score is %f", iteration + 1, pageLoadTimes.get(iteration), totalTimes.get(iteration) - pageLoadTimes.get(iteration), totalTimes.get(iteration), entryCounts.get(iteration), entryType, metricScores.get(iteration)));
    }

    public void printIterationDeviceMetricSummary(int iteration, ArrayList<Double> pageLoadTimes, ArrayList<Double> totalTimes, ArrayList<Long> deviceCounts, ArrayList<Double> metricScores) {
        printIterationMetricSummary(iteration, pageLoadTimes, totalTimes, deviceCounts, "devices", metricScores);
    }

    public void printOverallMetricSummary(ArrayList<Double> pageLoadTimes, ArrayList<Double> totalTimes, ArrayList<Long> entryCounts, String entryType, ArrayList<Double> metricScores) {
        System.out.println("\nOverall Results:");
        System.out.println(String.format("\tAvg. Page Load Time:  %f ms", calculateMean(pageLoadTimes)));
        System.out.println(String.format("\tAvg. Table Load Time: %f ms", calculateMean(totalTimes) - calculateMean(pageLoadTimes)));
        System.out.println(String.format("\tAvg. Total Time:      %f ms", calculateMean(totalTimes)));
        System.out.println(String.format("\tAvg. No. of %s:       %f", entryType, calculateMean(entryCounts)));
        System.out.println(String.format("\tAvg. Metric Score:    %f", calculateMean(metricScores)));
        System.out.println();
    }

    public void printOverallDeviceMetricSummary(ArrayList<Double> pageLoadTimes, ArrayList<Double> totalTimes, ArrayList<Long> deviceCounts, ArrayList<Double> metricScores) {
        printOverallMetricSummary(pageLoadTimes, totalTimes, deviceCounts, "devices", metricScores);
    }
}
