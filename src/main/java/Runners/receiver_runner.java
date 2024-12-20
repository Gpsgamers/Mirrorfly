package Runners;

import java.time.Duration;
import java.util.Optional;

import org.openqa.selenium.devtools.v128.network.Network;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = { "src\\test\\resources\\login\\receiver_login.feature",

}, // Path to feature files
		glue = { "mirrorflytest" }, // Package for step definitions
		plugin = { "pretty", // For console output
				"html:target/cucumber-reports/Cucumber.html", // HTML report
				"json:target/cucumber-reports/Cucumber.json", // JSON report
				"io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm" }, monochrome = true // To make console output more
																						// readable
)
public class receiver_runner extends base {
	@BeforeClass
	@Parameters({ "browser", "environment" })
	public void browserlaunchconfiguration(String browser, String environment) {
		Browser = browser;
		System.out.println(Browser);
		receiver_driver = launchbrowser(browser);
		receiver_devTool = get_devTools(receiver_driver);
		Environment(environment);
		// receiver_driver.manage().window().maximize();
		screen_position(receiver_driver, "right");
		receiver_driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
	}
}
