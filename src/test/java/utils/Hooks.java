package utils;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Reporter;


public class Hooks extends DriverFactory{
    @Before
    public void setUp() throws Exception {
        //Reading the parameters from testng.xml file
        String browser = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("browserType");
        init_driver(browser);
    }

    @After
    public void afterScenario(Scenario scenario) {
            driver.manage().deleteAllCookies();
            DriverFactory.closeBrowser();
        }
    }


