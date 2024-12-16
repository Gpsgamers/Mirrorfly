package mirrorflytest;

import java.time.Duration;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class stepdefination extends methods {
	public static WebDriverWait caller_wait, receiver_wait;
	public static String caller_name, receiver_name;

	@When("caller : click on the call log btn from the recent chat screen")
	public void caller_click_on_the_call_log_btn_from_the_recent_chat_screen() {
		caller_wait = new WebDriverWait(caller_driver, Duration.ofSeconds(30));
		receiver_wait = new WebDriverWait(receiver_driver, Duration.ofSeconds(30));

		caller_name = caller_wait.until(ExpectedConditions.visibilityOfElementLocated(profilename_container)).getText();
		receiver_name = receiver_wait.until(ExpectedConditions.visibilityOfElementLocated(profilename_container))
				.getText();

		caller_wait.until(ExpectedConditions.invisibilityOfElementLocated(recent_loader_container));
		caller_driver.findElement(calllog_nav_btn).click();
	}

	@Then("call log screen is displayed")
	public void call_log_screen_is_displayed() {
		caller_wait.until(ExpectedConditions.visibilityOfElementLocated(FloatingCallAction)).click();
	}

	@When("click on the floating call icon")
	public void click_on_the_floating_call_icon() {
	}

	@Then("audio call option is displayed")
	public void audio_call_option_is_displayed() {
		Assert.assertTrue(
				caller_wait.until(ExpectedConditions.visibilityOfElementLocated(Floating_audiocall_btn)).isDisplayed(),
				"audio call option is not displayed");
	}

	@When("click on the audio call icon")
	public void click_on_the_audio_call_icon() {
		caller_driver.findElement(Floating_audiocall_btn).click();
	}

	@Then("contacts screen is displayed")
	public void contacts_screen_is_displayed() {
		Assert.assertTrue(
				caller_wait.until(ExpectedConditions.visibilityOfElementLocated(contacts_search)).isDisplayed(),
				"contacts screen is not displayed");
	}

	@When("select any of the contacts and click on the call now button")
	public void select_any_of_the_contacts_and_click_on_the_call_now_button() {
		caller_driver.findElement(contacts_search).sendKeys(receiver_name);
		caller_wait.until(ExpectedConditions.visibilityOfElementLocated(contacts_selection(receiver))).click();
		caller_driver.findElement(call_now_btn).click();
	}

	@Then("call is triggered to receiver")
	public void call_is_triggered_to_receiver() {
		Assert.assertTrue(
				receiver_wait.until(ExpectedConditions.visibilityOfElementLocated(callattend_button)).isDisplayed(),
				"attend button is not displayed");
	}

	@When("Receiver attend the call")
	public void receiver_attend_the_call() throws InterruptedException {
		Thread.sleep(2000);
		receiver_driver.findElement(callattend_button).click();
	}

	@Then("the call is connected to both caller and receiver")
	public void the_call_is_connected_to_both_caller_and_receiver() {
		Boolean caller_timer_running = caller_wait
				.until(ExpectedConditions.visibilityOfElementLocated(calls_status("00:05"))).isDisplayed();
		Boolean receiver_timer_running = caller_wait
				.until(ExpectedConditions.visibilityOfElementLocated(calls_status("00:09"))).isDisplayed();
		Assert.assertTrue(caller_timer_running && receiver_timer_running, "call is not connected");
	}

	@When("caller goes to offline")
	public void caller_goes_to_offline() throws InterruptedException {
		block_url(caller_driver, "block", "wss://janus.mirrorfly.com/");
		offline(caller_driver);
	}

	@Then("caller goes to reconnection state")
	public void caller_goes_to_reconnection_state() {
		Assert.assertTrue(caller_wait.until(ExpectedConditions.visibilityOfElementLocated(calls_status("Reconnecting")))
				.isDisplayed(), "Reconnection text is not displayed");
		block_url(caller_driver, "unblock", null);
	}

	@When("caller connects the call after {int} second")
	public void caller_connects_the_call_after_second(Integer int1) throws InterruptedException {
		Thread.sleep(int1 * 1000);
		online(caller_driver);
	}

	@Then("caller:reconnection text is removed for both caller and receiver")
	public void caller_reconnection_text_is_removed_for_both_caller_and_receiver() {
		caller_wait = new WebDriverWait(caller_driver, Duration.ofSeconds(8));
		receiver_wait = new WebDriverWait(receiver_driver, Duration.ofSeconds(8));
		Boolean caller_status = caller_wait
				.until(ExpectedConditions.invisibilityOfElementLocated(calls_status("Reconnecting")));
		Boolean receiver_status = caller_wait
				.until(ExpectedConditions.invisibilityOfElementLocated(calls_status("Reconnecting")));
		Assert.assertTrue(caller_status, "Reconnection text is changed on caller side within the gien time");
		Assert.assertTrue(receiver_status, "Reconnection text is changed on receiver side within the gien time");
	}

	@When("receiver goes to offline")
	public void receiver_goes_to_offline() {

	}

	@Then("receiver goes to reconnection state")
	public void receiver_goes_to_reconnection_state() {
	}

	@When("receiver connects the call after {int} second")
	public void receiver_connects_the_call_after_second(Integer int1) {
	}

	@Then("receiver:reconnection text is removed for both caller and receiver")
	public void receiver_reconnection_text_is_removed_for_both_caller_and_receiver() {
	}

	@When("caller goes to offline for {int} min")
	public void caller_goes_to_offline_for_min(Integer int1) {
	}

	@Then("caller is disconnected for both caller and receiver")
	public void caller_is_disconnected_for_both_caller_and_receiver() {
	}
}
