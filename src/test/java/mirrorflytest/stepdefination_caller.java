package mirrorflytest;

import java.time.Duration;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class stepdefination_caller extends methods {
	public static WebDriverWait wait;

	@Given("Caller : Navigate to register page")
	public void caller_navigate_to_register_page() {
		caller_driver.get(url);
	}

	@When("Caller : Enter the number on the number field")
	public void caller_enter_the_number_on_the_number_field() {
		wait = new WebDriverWait(caller_driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(pageloader));
		wait.until(ExpectedConditions.visibilityOfElementLocated(country_code));
		caller_driver.findElement(number).sendKeys(caller);
	}

	@When("Caller : click on the register button")
	public void caller_click_on_the_register_button() {
		caller_driver.findElement(continue_btn).click();
		caller_driver.findElement(verify_otp_btn).click();
		try {
			wait.until(ExpectedConditions.invisibilityOfElementLocated(pageloader));
			caller_driver.findElement(session_confirm_btn).click();
			wait.until(ExpectedConditions.invisibilityOfElementLocated(pageloader));
		} catch (Exception e) {
		}
	}

	@Then("Caller : page should be navigating to the home page")
	public void caller_page_should_be_navigatin_to_the_home_page() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(calllog_nav_btn));
	}
}
