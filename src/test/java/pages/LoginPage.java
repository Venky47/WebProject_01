package pages;


import org.openqa.selenium.By;
import utils.BaseClass;
import utils.DriverFactory;

import java.sql.Driver;
import java.util.Map;

public class LoginPage extends DriverFactory {
    BaseClass bc = new BaseClass();
    By userName = By.name("username");
    By password = By.name("password");
    By login_btn = By.xpath("//button[text()=' Login ']");


    public void user_enters_valid_email_address_and_password(Map<String, String> loginCred) {
        bc.pageloadWait();
        bc.waitForElementToBeVisible(driver, userName);
        bc.clickElement(userName);
        for (String key : loginCred.keySet()) {
            switch (key) {
                case "username":
                    bc.waitForElementToBeVisible(driver, userName);
                    bc.enterText(userName, loginCred.get(key));
                    break;
                case "password":
                    bc.waitForElementToBeVisible(driver, password);
                    bc.enterText(password, loginCred.get(key));
                    break;

            }
        }
        bc.waitForElementToBeVisible(driver, login_btn);
        bc.clickElementAngularJS(login_btn);
        bc.waitingFor(2);
        bc.pageloadWait();
    }
}

