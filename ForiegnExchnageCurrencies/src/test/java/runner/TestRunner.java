package runner;

import org.junit.runner.RunWith;

import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.Cucumber;

@RunWith(Cucumber.class)

// Configuring cucumber options
// features - Path of feature path
// glue - Path of Step Definitions
// tags - Indicate group of tests
// plugin - Report plugins

@CucumberOptions(features = "src/test/resources/features", glue = "stepDefinitions", plugin = {
		"pretty", "html:target/cucumber-html-report", "json:target/cucumber-reports/cucumber.json",
		"junit:target/cucumber-reports/cucumber.xml",
		"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:" })

public class TestRunner 
{

}
