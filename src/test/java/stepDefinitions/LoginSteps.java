package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.messages.types.Hook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.asserts.SoftAssert;
import pages.LoginPage;
import utils.BaseClass;
import utils.DriverFactory;
import utils.Hooks;
import verification.LoginPageVerification;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class LoginSteps extends DriverFactory{
    BaseClass bc=new BaseClass();
    LoginPage loginPage=new LoginPage();
    LoginPageVerification loginPageVerification=new LoginPageVerification();

    @Given("application url {string}")
    public void applicationUrl(String siteName) throws Exception {
//       Properties p=new Properties();
//        try {
//            FileInputStream fis=new FileInputStream("src/test/properties/config.properties");
//            p.load(fis);
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

        if (siteName.equalsIgnoreCase( "Orange HRM")) {
            driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/dashboard/index");
        } else {
            throw new IllegalStateException(siteName);
        }

        }


    @Given("open the application")
    public void openTheApplication() {
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/dashboard/index");
    }

    @When("enter the below valid credentials")
    public void enterTheBelowValidCredentials(Map<String, String> loginCred) {
        loginPage.user_enters_valid_email_address_and_password(loginCred);
    }

    @Then("I verify the {string}")
    public void iVerifyThe(String title, List<String> expectedTitle) {
        loginPageVerification.iVerifyTheFollowingMessageIsDisplayedOn(title,expectedTitle);
    }
}

