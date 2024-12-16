package mirrorflytest;

import org.openqa.selenium.By;

import Runners.base;

public class element extends base {

	public static By pageloader = By.xpath("//div[@class='pageLoader']");
	public static By recent_loader_container = By.xpath("//div[@class='loader-container']");
	// profile
	public static By profilename_container = By.xpath("//div[@class='profile-img-name']//span");
	// register
	public static By number = By.xpath("//input[@name='mobileNumber']");
	public static By country_code = By.xpath("//span[@class='countryCode']");
	public static By continue_btn = By.xpath("//button[@id='GetOtp']");
	public static By verify_otp_btn = By.xpath("//button[@id='VerifyOtp']");
	public static By session_confirm_btn = By.xpath("//button[@name='btn-logout']");
	public static By session_cancel_btn = By.xpath("//button[@name='btn-cancel']");

	// recent chat
	public static By calllog_nav_btn = By.xpath("//div[@class='callLogCount']");

	// call_log
	public static By FloatingCallAction = By.xpath("//label[@class='FloatingCallAction']");
	public static By Floating_audiocall_btn = By.xpath("//li[@title='Audio call']");
	public static By contacts_search = By.xpath("(//input[@name='search-contacts'])[2]");

	public static By contacts_selection(String number) {
		return By.xpath("(//label[@for=\"" + "91" + number + "\"])[2]");
	}

	public static By call_now_btn = By.xpath("//div[@class='callButton']");

	// call screen
	public static By callattend_button = By.xpath("//span[@class='attenCall']//i");

	public static By calls_status(String text) {
		return By.xpath("//span[contains(text(),'" + text + "')]");
	}

}
