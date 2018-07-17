package com.blackbox.common.selenium;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.interactions.Actions;


public class DriverUtil {
    private WebDriver driver = null;
    private final static int explicitWaitSeconds = 30;


    // ----------------------Constructors--------------------------------------------

    public DriverUtil(WebDriver myDriver) {
        driver = myDriver;
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }


    // ----------------------Utility Methods-----------------------------------------

    protected By parseLocator(String locator) {
        if (locator.toLowerCase().startsWith("name=")) {
            return By.name(locator.substring(5));
        } else if (locator.toLowerCase().startsWith("xpath=")) {
            return By.xpath(locator.substring(6));
        } else if (locator.toLowerCase().startsWith("/")) {
            return By.xpath(locator);
        } else if (locator.toLowerCase().startsWith("domtagname=")) {
            return By.tagName(locator.substring(11));
        } else if (locator.toLowerCase().startsWith("domclass=")) {
            return By.className(locator.substring(9));
        } else if (locator.toLowerCase().startsWith("css=")) {
            return By.cssSelector(locator.substring(4));
        } else if (locator.toLowerCase().startsWith("link=")) {
            return By.linkText(locator.substring(5));
        } else if (locator.toLowerCase().startsWith("partiallink=")) {
            return By.partialLinkText(locator.substring(12));
        } else if (locator.toLowerCase().startsWith("id=")) {
            return By.id(locator.substring(3));
        } else if (locator.toLowerCase().startsWith("identifier=")) {
            return By.id(locator.substring(11));
        }

        return By.id(locator);
    }

    protected By parseLocator(StringBuilder locator) {
        return (parseLocator(locator.toString()));
    }

    public String getAttribute(String locator, String attribute) {
        try {
            WebElement element = driver.findElement(parseLocator(locator));
            String retValue = element.getAttribute(attribute);

            if (retValue == null)
                throw new WebTestingException(
                        String.format(
                                "Unable to find the attribute \"%s\" for %s, it was null.",
                                attribute,
                                locator
                        )
                );
            return retValue;
        } catch (org.openqa.selenium.NoSuchElementException t) {
            throw new WebTestingException(
                    String.format(
                            "Unable to find the attribute \"%s\" for %s: %s",
                            attribute,
                            locator,
                            t.getMessage()),
                    t
            );
        } catch (Throwable t) {
            throw new WebTestingException(
                    String.format(
                            "Problem with attribute \"%s\" for %s: %s",
                            attribute,
                            locator,
                            t.getMessage()),
                    t
            );
        }
    }

    public String getAttribute(StringBuilder locator, String attribute) {
        return (getAttribute(locator.toString(), attribute));
    }


    // ------------------------------Methods-----------------------------------------

    public void quit() {
        driver.quit();
    }

    public void click(String locator) {
        int attempts = 0;

        while (attempts < explicitWaitSeconds) {
            try {
                WebElement element = driver.findElement(parseLocator(locator));
                element.click();
                break;
            } catch (StaleElementReferenceException | NoSuchElementException e) {
                attempts++;
                waitSeconds(1);
            } catch (Throwable t) {
                throw new WebTestingException(t.getMessage(), t);
            }
        }
    }

    public void click(StringBuilder locator) {
        click(locator.toString());
    }

    public void clearElementValue(String locator) {
        try {
            WebElement element = driver.findElement(parseLocator(locator));
            element.clear();
        } catch (Throwable t) {
            throw new WebTestingException(
                    String.format("clearElementValue: %s", t.getMessage()),
                    t
            );
        }
    }

    public void setElementValue(String locator, String text) {
        try {
            WebElement element = driver.findElement(parseLocator(locator));
            //NOTE: The behavior of the selenium driver was to always clear
            //the input field before sending the text to it.  Calling this
            //method with an empty string would clear the value as well.
            String type = element.getAttribute("type");
            if ("text".equalsIgnoreCase(type))
                element.clear();
            if (text != null && !text.isEmpty())
                element.sendKeys(text);
        } catch (Throwable t) {
            throw new WebTestingException(
                    String.format("setElementValue: %s", t.getMessage()),
                    t
            );
        }
    }

    public void setElementValue(StringBuilder locator, String text) {
        setElementValue(locator.toString(), text);
    }

    public void setElementValue(String locator, int value) {
        setElementValue(locator, Integer.toString(value));
    }

    public String getElementValue(String locator) {
        return this.getAttribute(locator, "value");
    }

    public String getElementValue(StringBuilder locator) {
        return (getElementValue(locator.toString()));
    }

    public String getText(String locator) {
        try {
            return driver.findElement(parseLocator(locator)).getText();
        } catch (Throwable t) {
            throw new WebTestingException(
                    String.format("Unable to get text for %s", locator),
                    t
            );
        }
    }

    public String getText(WebElement element) {
        return element.getText();
    }

    public String getText(WebElement parentElement, String childElementXpath) {
        return getChildElement(parentElement, childElementXpath).getText();
    }

    public String getText(String parentElementLocator, String childElementXpath) {
        return getText(getElement(parentElementLocator), childElementXpath);
    }

    public String getText(StringBuilder locator) {
        return (getText(locator.toString()));
    }

    public String getText(WebElement element, String[] substringsToIgnore) {
        String text = element.getText();

        for (String substring : substringsToIgnore) {
            text = text.replace(substring, "");
        }

        return text;
    }

    public String getText(String locator, String[] substringsToIgnore) {
        return getText(getElement(locator), substringsToIgnore);
    }

    public String getText(WebElement parentElement, String childElementXpath, String[] substringsToIgnore) {
        return getText(getChildElement(parentElement, childElementXpath), substringsToIgnore);
    }

    public String getText(String parentElementLocator, String childElementXpath, String[] substringsToIgnore) {
        return getText(getElement(parentElementLocator), childElementXpath, substringsToIgnore);
    }

    public void open(String url) {
        if (url == null || url.isEmpty())
            throw new WebTestingException("Unable to navigate to a null or empty url");

        try {
            driver.get(url);
        } catch (Throwable t) {
            throw new WebTestingException(
                    String.format("Unable to navigate to: %s", url),
                    t
            );
        }
    }

    public boolean isElementEditable(String locator) {
        try {
            WebElement element = driver.findElement(parseLocator(locator));
            return element.isEnabled();
        } catch (Throwable t) {
            throw new WebTestingException(
                    String.format("Unable to check if element is enabled for %s", locator),
                    t
            );
        }
    }

    public boolean isElementEditable(StringBuilder locator) {
        return (isElementEditable(locator.toString()));
    }

    public String getPageTitle() {
        try {
            return driver.getTitle();
        } catch (Throwable t) {
            throw new WebTestingException("Unable to obtain the current page's title", t);
        }
    }

    public List<String> getSelectOptions(String locator) {
        Select selector = new Select(driver.findElement(parseLocator(locator)));
        return selector.getOptions().stream().map(el -> el.getAttribute("value")).collect(Collectors.toList());
    }

    public List<String> getSelectOptionsText(String locator) {
        Select selector = new Select(driver.findElement(parseLocator(locator)));
        return selector.getOptions().stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public WebElement getElement(String locator) {
        return driver.findElement(parseLocator(locator));
    }

    public WebElement getChildElement(WebElement parentElement, String childXpath) {
        return parentElement.findElement(By.xpath(childXpath));
    }

    public void uploadFile(String locator, String upLoadFilePath) {

        LocalFileDetector detector = new LocalFileDetector();
        File f = detector.getLocalFile(upLoadFilePath);

        this.setElementValue(locator, f.getAbsolutePath());
    }

    public void uploadFile(StringBuilder locator, String uploadFilePath) {
        uploadFile(locator.toString(), uploadFilePath);
    }

    public void waitForVisibleText(String locator, final String text, int waitTimeOutInSeconds) {
        int attempts = 0;
        boolean success = false;

        while (!success) {
            final WebElement element = driver.findElement(parseLocator(locator));
            try {
                new WebDriverWait(driver, waitTimeOutInSeconds).until(new ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver d) {
                        return element.isDisplayed() && element.getText().equals(text);
                    }
                });
                success = true;
            } catch (NoSuchElementException nsee) {
                attempts++;
                if (attempts < explicitWaitSeconds) {
                    waitSeconds(1);
                } else {
                    throw new WebTestingException(
                            String.format("%s, seconds=\"%d\", locator=\"%s\", text=\"%s\"",
                                    nsee.getMessage(),
                                    waitTimeOutInSeconds,
                                    locator,
                                    text),
                            nsee
                    );
                }
            } catch (Throwable t) {
                throw new WebTestingException(
                        String.format("%s, seconds=\"%d\", locator=\"%s\", text=\"%s\"",
                                t.getMessage(),
                                waitTimeOutInSeconds,
                                locator,
                                text),
                        t
                );
            }
        }
    }

    public void waitForVisibleElement(String locator, int waitTimeOutInSeconds) {
        int attempts = 0;
        boolean success = false;

        while (!success) {
            final WebElement element = driver.findElement(parseLocator(locator));
            try {
                new WebDriverWait(driver, waitTimeOutInSeconds).until(new ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver d) {
                        return element.isDisplayed();
                    }
                });
                success = true;
            } catch (NoSuchElementException nsee) {
                attempts++;
                if (attempts < explicitWaitSeconds) {
                    waitSeconds(1);
                } else {
                    throw new WebTestingException(
                            String.format(
                                    "%s, seconds=\"%d\", locator=\"%s\"",
                                    nsee.getMessage(),
                                    waitTimeOutInSeconds,
                                    locator
                            )
                    );
                }
            } catch (Throwable t) {
                throw new WebTestingException(
                        String.format(
                                "%s, seconds=\"%d\", locator=\"%s\"",
                                t.getMessage(),
                                waitTimeOutInSeconds,
                                locator
                        )
                );
            }
        }
    }

    public void waitForVisibleText(StringBuilder locator, final String text, int waitTimeOutInSeconds) {
        waitForVisibleText(locator.toString(), text, waitTimeOutInSeconds);
    }

    public boolean doesElementExist(String locator) {
        try {
            WebElement element = driver.findElement(parseLocator(locator));
            return true;
        } catch (NoSuchElementException nsee) {
            return false;
        }
    }

    public boolean doesElementExist(StringBuilder locator) {
        return doesElementExist(locator.toString());
    }

    public boolean isElementVisible(String locator) {
        try {
            WebElement element = driver.findElement(parseLocator(locator));
            return element.isDisplayed();
        } catch (NoSuchElementException nsee) {
            return false;
        }
    }

    public boolean isElementVisible(WebElement parentElement, String childXpath) {
        try {
            WebElement element = getChildElement(parentElement, childXpath);
            return element.isDisplayed();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }

    public boolean isElementVisible(String locator, int timeoutInSeconds) {
        try {
            waitForVisibleElement(locator, timeoutInSeconds);

            WebElement element = driver.findElement(parseLocator(locator));
            return element.isDisplayed();
        } catch (NoSuchElementException | WebTestingException e) {
            return false;
        }
    }

    public boolean isElementVisible(StringBuilder locator) {
        return isElementVisible(locator.toString());
    }

    public void setElementAttribute(String locator, String attribute, String value) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(String.format(
                "document.getElementByID('%s').setAttribute('%s', '%s'",
                parseLocator(locator),
                attribute,
                value
        ));
    }

    public boolean isAttributePresent(String locator, String attribute) {
        WebElement element = driver.findElement(parseLocator(locator));

        if (element.getAttribute(attribute) != null) {
            return true;
        } else {
            return false;
        }
    }

    public void setElementAttribute(StringBuilder locator, String attribute, String value) {
        setElementAttribute(locator.toString(), attribute, value);
    }

    public WebElement getWebElement(String locator) {
        return (driver.findElement(parseLocator(locator)));
    }

    public WebElement getWebElement(StringBuilder locator) {
        return (getWebElement(locator.toString()));
    }

    public WebElement getParentElement(WebElement element) {
        return element.findElement(By.xpath(".."));
    }

    public WebElement getParentElement(String locator) {
        return getParentElement(driver.findElement(parseLocator(locator)));
    }

    public void selectOptionByValue(String selectLocator, String optionToSelect) {
        Select selector = new Select(driver.findElement(parseLocator(selectLocator)));
        selector.selectByValue(optionToSelect);
    }

    public void selectOptionByValue(StringBuilder selectLocator, String optionToSelect) {
        selectOptionByValue(selectLocator.toString(), optionToSelect);
    }

    public void selectOptionByIndex(String selectLocator, int optionToSelect) {
        Select selector = new Select(driver.findElement(parseLocator(selectLocator)));
        List<WebElement> selectorOptions = selector.getOptions();
        selectorOptions.get(optionToSelect).click();
    }

    public void selectOptionByIndex(StringBuilder selectLocator, int optionToSelect) {
        selectOptionByIndex(selectLocator.toString(), optionToSelect);
    }

    public boolean isElementSelected(String locator) {
        return (driver.findElement(parseLocator(locator)).isSelected());
    }

    public boolean isElementSelected(StringBuilder locator) {
        return (isElementSelected(locator.toString()));
    }

    public void hitEnterKey() {
        Actions action = new Actions(driver);
        action.sendKeys(Keys.ENTER).perform();
    }

    public void hitTabKey() {
        Actions action = new Actions(driver);
        action.sendKeys(Keys.TAB).perform();
    }

    public void hitShiftTabKeys() {
        Actions action = new Actions(driver);
        action.keyDown(Keys.SHIFT).perform();
        action.sendKeys(Keys.TAB).perform();
        action.keyUp(Keys.SHIFT).perform();
    }

    public void waitSeconds(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    public void acceptNativeDialog() {
        driver.switchTo().alert().accept();
    }

    public void dismissNativeDialog() {
        driver.switchTo().alert().dismiss();
    }
}
