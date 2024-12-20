package mirrorflytest;

import java.time.Duration;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class stepdefication_receiver extends methods {
	public static WebDriverWait wait;

	@Given("Receiver : Navigate to register page")
	public void receiver_navigate_to_register_page() {
		methods.receiver_driver.get(url);

	}

	@When("Receiver : Enter the number on the number field")
	public void receiver_enter_the_number_on_the_number_field() {
		wait = new WebDriverWait(receiver_driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(pageloader));
		wait.until(ExpectedConditions.visibilityOfElementLocated(country_code));
		receiver_driver.findElement(number).sendKeys(receiver);
	}

	@When("Receiver : click on the register button")
	public void receiver_click_on_the_register_button() {
		receiver_driver.findElement(continue_btn).click();
		receiver_driver.findElement(verify_otp_btn).click();

		try {
			wait.until(ExpectedConditions.invisibilityOfElementLocated(pageloader));
			receiver_driver.findElement(session_confirm_btn).click();
			wait.until(ExpectedConditions.invisibilityOfElementLocated(pageloader));
		} catch (Exception e) {
		}
	}

	@Then("Receiver : page should be navigating to the home page")
	public void receiver_page_should_be_navigatin_to_the_home_page() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(calllog_nav_btn));
	}

}
