package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Parameters;

public class DriverFactory {

    public static RemoteWebDriver driver;
    public static ThreadLocal<RemoteWebDriver> dr = new ThreadLocal<>();

    public static RemoteWebDriver getDriver() {
        return dr.get();
    }

    public static void setDriver(RemoteWebDriver driverRef) {
        dr.set(driverRef);
    }

    public static void closeBrowser() {
        driver.close();
    }

    @Parameters("browserType")
    public static void init_driver(String browser) throws Exception {
        switch (browser.toLowerCase()) {
            case "chrome":
                    System.setProperty("webDriver.chrome.driver", "src/Drivers/chromedriver.exe");
                    driver=new ChromeDriver();
                    driver.manage().window().maximize();
                }

        }

    }


