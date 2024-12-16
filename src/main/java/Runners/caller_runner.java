package Runners;

import java.awt.Dimension;
import java.time.Duration;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = { "src\\test\\resources\\login\\caller_login.feature",

}, // Path to feature files
		glue = { "mirrorflytest" }, // Package for step definitions
		plugin = { "pretty", // For console output
				"html:target/cucumber-reports/Cucumber.html", // HTML report
				"json:target/cucumber-reports/Cucumber.json", // JSON report
				"io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm" }, monochrome = true // To make console output more
																						// readable
)
public class caller_runner extends base {
	@BeforeClass
	@Parameters({ "browser", "environment" })
	public void browserlaunchconfiguration(String browser, String environment) {
		Browser = browser;
		System.out.println(Browser);
		caller_driver = launchbrowser(browser);
		Environment(environment);
		caller_driver.manage().window().maximize();
        //screen_position(caller_driver,"left");
		caller_driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
	}

}
