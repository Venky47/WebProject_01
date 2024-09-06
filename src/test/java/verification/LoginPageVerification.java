package verification;

import org.testng.Assert;
import utils.BaseClass;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoginPageVerification extends BaseClass {
BaseClass bc=new BaseClass();

    public boolean iVerifyTheFollowingMessageIsDisplayedOn(String title, List<String> expectedTitle) {
        String expected = "";
        String actual = "";
        boolean result = true;
        switch (title) {
            case "Title":
                for (String key : expectedTitle) {
                    switch (key) {
                        case "OrangeHRM":
                            //expected = errMsg.get(0);
                            System.out.println("Expected success message for order confirmation is ==> " + key);
                            // bc.waitForElementToBeVisible(driver, nbf_oc_orderConfirmationSucMsg);
                            actual = bc.getTitle();
                            //the actual is [OrangeHRM] so converted to list
                            List<String> myList = new ArrayList<String>(Arrays.asList(actual.split(",")));
                            Assert.assertEquals(myList,expectedTitle);
                            System.out.println("actual "+actual+"expected "+expectedTitle);

                    }
                }
        }
        return result;
    }
}
