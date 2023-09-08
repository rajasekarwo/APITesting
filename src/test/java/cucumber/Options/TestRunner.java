package cucumber.Options;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/java/features", 
				 glue = { "stepDefinitions" },
				 plugin = {"pretty", "json:target/cucumber-reports/Cucumber.json"
						 		   , "html:target/cucumber-reports/report.html"},
				 monochrome = true)
public class TestRunner {

}
