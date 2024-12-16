package Runners;

import org.testng.annotations.AfterClass;

import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = { "src\\test\\resources\\calls\\calls.feature",

}, // Path to feature files
		glue = { "mirrorflytest" }, // Package for step definitions
		plugin = { "pretty", // For console output
				"html:target/cucumber-reports/Cucumber.html", // HTML report
				"json:target/cucumber-reports/Cucumber.json", // JSON report
				"io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm" }, monochrome = true // To make console output more
																						// readable
)
public class runner extends base {
	@AfterClass
	public void browserclose() {
		caller_driver.quit();
		receiver_driver.quit();
	}
}
