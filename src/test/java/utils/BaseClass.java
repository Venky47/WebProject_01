package utils;


import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.*;
import java.util.concurrent.TimeUnit;


public class BaseClass extends DriverFactory {
    protected WebDriverWait wait;

    protected JavascriptExecutor jsExecutor;
    private String screenshotName;


    public BaseClass() {

        jsExecutor = ((JavascriptExecutor) driver);

    }

    /**
     * Web automation generic methods
     */

    public void navigateToLandingPage(String siteName) {
        String url;
        FileInputStream fi = null;
        Properties p = new Properties();
        try {
            fi = new FileInputStream("src/test/java/com/fabricautomation/utils/config.properties");
            p.load(fi);
        } catch (Exception e) {
            e.printStackTrace();
        }


        switch (siteName.toLowerCase()) {
            case "cosco":
                // url = p.getProperty("coscoUrl");
                url = "https://cosca-wip2.pnistaging.com/Home";
                break;
            case "office chairs":
//               url = pValues.getProperty("OfficeChairs");
                url = "https://uat-officechairs.nbf.fabric.zone/";
//                url = "https://www.officechairs.com/";
                break;
            case "office furnitures":
//               url = pValues.getProperty("OfficeChairs");
                url = "https://uat-officefurnitures.nbf.fabric.zone";
                break;
            case "klondike":
                url = "https://dev.klondike.fabric.zone/";
                break;
//            case "costco-pni ca":
//                url = pValues.getProperty("CostcoSite_PNI_CAN");
//                SiteInfoDto.getSiteInfoDto().setSiteName(pValues.getProperty("strCostcoPniCANSite"));
//                break;
//            case "costco-pni us":
//                url = pValues.getProperty("CostcoSite_PNI_US");
//                SiteInfoDto.getSiteInfoDto().setSiteName(pValues.getProperty("strCostcoPniUSSite"));
//                break;
            default:
                throw new IllegalStateException("Unexpected value: " + siteName.toLowerCase());
        }
        getDriver().get(url);
        //driver.get(url);

    }

    public void waitingFor(int timeout) {
        try {
            int x = 1;
            while (x <= timeout) {
//                logger.info("waiting . . . ");
                Thread.sleep(1000);
                x = x + 1;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void clearText(By element) {
        getDriver().findElement(element).clear();
    }

    //send keys
    public boolean enterText(By ele, String textToEnter) {
        try {
            //        logger.info("Entering the text "+ textToEnter);
            driver.findElement(ele).clear();
            driver.findElement(ele).sendKeys(textToEnter);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }


    /**********************************************************************************
     **CLICK METHODS
     * @throws IOException
     **********************************************************************************/
    public void waitAndClickElement(WebElement element) throws InterruptedException, IOException {
        boolean clicked = false;
        int attempts = 0;
        while (!clicked && attempts < 10) {
            try {
                this.wait.until(ExpectedConditions.elementToBeClickable(element)).click();
                System.out.println("Successfully clicked on the WebElement: " + "<" + element.toString() + ">");
                clicked = true;
            } catch (Exception e) {
                System.out.println("Unable to wait and click on WebElement, Exception: " + e.getMessage());
                Assert.fail("Unable to wait and click on the WebElement, using locator: " + "<" + element.toString() + ">");
            }
            attempts++;
        }
    }

    public void waitAndClickElementsUsingByLocator(By by) throws InterruptedException {
        boolean clicked = false;
        int attempts = 0;
        while (!clicked && attempts < 10) {
            try {
                this.wait.until(ExpectedConditions.elementToBeClickable(by)).click();
                System.out.println("Successfully clicked on the element using by locator: " + "<" + by.toString() + ">");
                clicked = true;
            } catch (Exception e) {
                System.out.println("Unable to wait and click on the element using the By locator, Exception: " + e.getMessage());
                Assert.fail("Unable to wait and click on the element using the By locator, element: " + "<" + by.toString() + ">");
            }
            attempts++;
        }
    }

    //javascript click
    public void clickElementAngularJS(By ele) {
        WebElement element = driver.findElement(ele);
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", element);
    }

    public void clickElement(By ele) {
        WebElement element = driver.findElement(ele);
      element.click();
    }



    /**********************************************************************************/
/**********************************************************************************/


    /**********************************************************************************
     **ACTION METHODS
     **********************************************************************************/

    public void actionMoveAndClick(WebElement element) throws Exception {
        Actions ob = new Actions(driver);
        try {
            this.wait.until(ExpectedConditions.elementToBeClickable(element)).isEnabled();
            ob.moveToElement(element).click().build().perform();
            System.out.println("Successfully Action Moved and Clicked on the WebElement, using locator: " + "<" + element.toString() + ">");
        } catch (StaleElementReferenceException elementUpdated) {
            WebElement elementToClick = element;
            Boolean elementPresent = wait.until(ExpectedConditions.elementToBeClickable(elementToClick)).isEnabled();
            if (elementPresent == true) {
                ob.moveToElement(elementToClick).click().build().perform();
                System.out.println("(Stale Exception) - Successfully Action Moved and Clicked on the WebElement, using locator: " + "<" + element.toString() + ">");
            }
        } catch (Exception e) {
            System.out.println("Unable to Action Move and Click on the WebElement, using locator: " + "<" + element.toString() + ">");
            Assert.fail("Unable to Action Move and Click on the WebElement, Exception: " + e.getMessage());
        }
    }

    public void actionMoveAndClickByLocator(By element) throws Exception {
        Actions ob = new Actions(driver);
        try {
            Boolean elementPresent = wait.until(ExpectedConditions.elementToBeClickable(element)).isEnabled();
            if (elementPresent == true) {
                WebElement elementToClick = driver.findElement(element);
                ob.moveToElement(elementToClick).click().build().perform();
                System.out.println("Action moved and clicked on the following element, using By locator: " + "<" + element.toString() + ">");
            }
        } catch (StaleElementReferenceException elementUpdated) {
            WebElement elementToClick = driver.findElement(element);
            ob.moveToElement(elementToClick).click().build().perform();
            System.out.println("(Stale Exception) - Action moved and clicked on the following element, using By locator: " + "<" + element.toString() + ">");
        } catch (Exception e) {
            System.out.println("Unable to Action Move and Click on the WebElement using by locator: " + "<" + element.toString() + ">");
            Assert.fail("Unable to Action Move and Click on the WebElement using by locator, Exception: " + e.getMessage());
        }
    }

    /**********************************************************************************/
/**********************************************************************************/


    /**********************************************************************************
     **SEND KEYS METHODS /
     **********************************************************************************/
    public void sendKeysToWebElement(WebElement element, String textToSend) throws Exception {
        try {
            this.WaitUntilWebElementIsVisible(element);
            element.clear();
            element.sendKeys(textToSend);
            System.out.println("Successfully Sent the following keys: '" + textToSend + "' to element: " + "<" + element.toString() + ">");
        } catch (Exception e) {
            System.out.println("Unable to locate WebElement: " + "<" + element.toString() + "> and send the following keys: " + textToSend);
            Assert.fail("Unable to send keys to WebElement, Exception: " + e.getMessage());
        }
    }

    public void scrollDown() {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0,500)", "");
            System.out.println("Succesfully scrolled to the WebElement");
        } catch (Exception e) {
            System.out.println("Unable to scroll to the WebElement");
        }
    }

    public void jsPageScroll(int numb1, int numb2) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("scroll(" + numb1 + "," + numb2 + ")");
            System.out.println("Succesfully scrolled to the correct position! using locators: " + numb1 + ", " + numb2);
        } catch (Exception e) {
            System.out.println("Unable to scroll to element using locators: " + "<" + numb1 + "> " + " <" + numb2 + ">");
            // Assert.fail("Unable to manually scroll to WebElement, Exception: " + e.getMessage());
        }
    }

    /**********************************************************************************
     **WAIT METHODS
     **********************************************************************************/
    public boolean WaitUntilWebElementIsVisible(WebElement element) {
        try {
            this.wait.until(ExpectedConditions.visibilityOf(element));
            System.out.println("WebElement is visible using locator: " + "<" + element.toString() + ">");
            return true;
        } catch (Exception e) {
            System.out.println("WebElement is NOT visible, using locator: " + "<" + element.toString() + ">");
            Assert.fail("WebElement is NOT visible, Exception: " + e.getMessage());
            return false;
        }
    }

    public boolean WaitUntilWebElementIsVisibleUsingByLocator(By element) {
        try {
            this.wait.until(ExpectedConditions.visibilityOfElementLocated(element));
            System.out.println("Element is visible using By locator: " + "<" + element.toString() + ">");
            return true;
        } catch (Exception e) {
            System.out.println("WebElement is NOT visible, using By locator: " + "<" + element.toString() + ">");
            Assert.fail("WebElement is NOT visible, Exception: " + e.getMessage());
            return false;
        }
    }

    public boolean isElementClickable(WebElement element) {
        try {
            this.wait.until(ExpectedConditions.elementToBeClickable(element));
            System.out.println("WebElement is clickable using locator: " + "<" + element.toString() + ">");
            return true;
        } catch (Exception e) {
            System.out.println("WebElement is NOT clickable using locator: " + "<" + element.toString() + ">");
            return false;
        }
    }

    public void pageloadWait() {
        try {
           driver.manage().timeouts().pageLoadTimeout(30000, TimeUnit.SECONDS);
            System.out.println("Page loaded successfully");
        } catch (Exception e) {
            System.out.println("Page timeout expired: " + e.getMessage());
        }
    }

    public boolean waitUntilPreLoadElementDissapears(By element) {
        return this.wait.until(ExpectedConditions.invisibilityOfElementLocated(element));
    }

    /**********************************************************************************/
/**********************************************************************************/


    /**********************************************************************************
     **PAGE METHODS
     **********************************************************************************/

    public String getCurrentURL() {
        try {
            String url = driver.getCurrentUrl();
            System.out.println("Found(Got) the following URL: " + url);
            return url;
        } catch (Exception e) {
            System.out.println("Unable to locate (Get) the current URL, Exception: " + e.getMessage());
            return e.getMessage();
        }
    }

    public String waitForSpecificPage(String urlToWaitFor) {
        try {
            String url = driver.getCurrentUrl();
            this.wait.until(ExpectedConditions.urlMatches(urlToWaitFor));
            System.out.println("The current URL was: " + url + ", " + "navigated and waited for the following URL: " + urlToWaitFor);
            return urlToWaitFor;
        } catch (Exception e) {
            System.out.println("Exception! waiting for the URL: " + urlToWaitFor + ",  Exception: " + e.getMessage());
            return e.getMessage();
        }
    }

    public boolean isElementPresent(WebElement element, String textToCheck) {
        try {
            if (element.getText() == textToCheck)
                System.out.println("The Text is Present: ");
            return true;
        } catch (Exception e) {
            System.out.println("The Text is not Present: " + textToCheck + ",  Exception: " + e.getMessage());
            return false;
        }
    }

    /**********************************************************************************/
/**********************************************************************************/


    /**********************************************************************************
     **ALERT & POPUPS METHODS
     * @throws Exception
     **********************************************************************************/
    public void closePopups(By locator) throws Exception {
        try {
            List<WebElement> elements = this.wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
            for (WebElement element : elements) {
                if (element.isDisplayed()) {
                    element.click();
                    this.wait.until(ExpectedConditions.invisibilityOfAllElements(elements));
                    System.out.println("The popup has been closed Successfully!");
                }
            }
        } catch (Exception e) {
            System.out.println("Exception! - could not close the popup!, Exception: " + e.toString());
            throw (e);
        }
    }

    public boolean checkPopupIsVisible() {
        try {
            @SuppressWarnings("unused")
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            System.out.println("A popup has been found!");
            return true;
        } catch (Exception e) {
            System.err.println("Error came while waiting for the alert popup to appear. " + e.getMessage());
        }
        return false;
    }

    public boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void closeAlertPopupBox() throws AWTException, InterruptedException {
        try {
            Alert alert = this.wait.until(ExpectedConditions.alertIsPresent());
            alert.accept();
        } catch (UnhandledAlertException f) {
            Alert alert = driver.switchTo().alert();
            alert.accept();
        } catch (Exception e) {
            System.out.println("Unable to close the popup");
            Assert.fail("Unable to close the popup, Exception: " + e.getMessage());
        }
    }

    //get text method
    public String getElememntText(By element) {
        String actualText = "";
        if (this.verifyElementExists(element)) {
            actualText = getDriver().findElement(element).getText();
        }
        return actualText;
    }


    public boolean verifyElementExists(By element) {

        boolean result = false;
        if (element.getClass().toString().contains("By")) {
            try {
                getDriver().findElement(element);
                return true;
            } catch (NoSuchElementException e) {
                return false;
            }
        } else {
            try {
                this.wait.until(ExpectedConditions.visibilityOf((WebElement) element));
                return true;
            } catch (Exception e) {
                System.out.println(element.toString() + "does not exists");
                return false;
            }
        }
    }

    public void implicitWait(int time) {
        driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);//follow this method in detail,what are enum
    }

    /**
     * Wait for element to appear on the webpage
     *
     * @param driver
     * @param locatorObject
     */
    public void waitForElementToBeVisible(RemoteWebDriver driver, By locatorObject) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(6000));
        wait.until(ExpectedConditions.visibilityOfElementLocated(locatorObject));
    }

    public String getTitle(){
        String actualTitle=driver.getTitle();
        return actualTitle;
    }

}