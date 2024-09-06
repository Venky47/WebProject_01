import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions
        (
                features= "C:/Users/Venkatesh T S/IdeaProjects/WebProject_01/src/test/java/featureFiles",
                glue="",
                tags = "@Login",
                monochrome = true,
                plugin = {
                        "pretty",
                }

        )
public class Runner extends AbstractTestNGCucumberTests {


}
