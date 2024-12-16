package Runners;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chromium.ChromiumDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import io.cucumber.testng.AbstractTestNGCucumberTests;

public class base extends AbstractTestNGCucumberTests {
	public static WebDriver caller_driver;
	public static WebDriver receiver_driver;
	public static String Browser, caller, receiver, url;

	public WebDriver launchbrowser(String browser) {
		if (browser.toLowerCase().equals("chrome")) {
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--use-fake-ui-for-media-stream");
			options.addArguments("--disable-notifications");
//			options.addArguments("--headless"); // Enable headless mode
//			options.addArguments("--disable-gpu"); // Optional: For Windows systems
			return new ChromeDriver(options);
		} else if (browser.toLowerCase().equals("edge")) {
			EdgeOptions options = new EdgeOptions();
			options.addArguments("--use-fake-ui-for-media-stream");
			options.addArguments("--disable-notifications");
			return new EdgeDriver(options);
		} else if (browser.toLowerCase().equals("firefox")) {
			FirefoxOptions options = new FirefoxOptions();
			return new FirefoxDriver(options);
		} else if (browser.toLowerCase().equals("safari")) {
			SafariOptions options = new SafariOptions();
			return new SafariDriver(options);
		} else {
			return null;
		}
	}

	public void Environment(String Env) {
		switch (Env.toUpperCase()) {
		case "QA":
			url = "https://webchat-uikit-qa.contus.us/";

			caller = "7305466010";
			receiver = "9159673388";
			break;

		case "DEV":
			url = "https://webchat-uikit-qa.contus.us/";

			caller = "7358331702";
			receiver = "9159673388";
			break;

		case "Live":
			url = "https://webchat-uikit-qa.contus.us/";

			caller = "7358331702";
			receiver = "9159673388";
			break;
		}
	}

	public static void offline(WebDriver driver){
		Map<String, Object> offlineParams = new HashMap<>();
		offlineParams.put("offline", true);
		offlineParams.put("latency", 0); // Latency in ms (no delay)
		offlineParams.put("downloadThroughput", 0); // Download speed (0 = disconnected)
		offlineParams.put("uploadThroughput", 0); // Upload speed (0 = disconnected)

		switch (Browser.toLowerCase()) {
		case "chrome":
			((ChromeDriver) driver).executeCdpCommand("Network.emulateNetworkConditions", offlineParams);
			System.out.println("Network disconnected");
			break;

		case "firefox":
			// not working
			((FirefoxDriver) driver)
					.executeScript("window.navigator.serviceWorker.controller.postMessage({offline: true});");
			break;
		case "edge":
			((EdgeDriver) driver).executeCdpCommand("Network.emulateNetworkConditions", offlineParams);
			break;
		}
	}

	public static void online(WebDriver driver) {
		Map<String, Object> onlineParams = new HashMap<>();
		onlineParams.put("offline", false);
		onlineParams.put("latency", 0); // Set small latency to simulate a real connection
		onlineParams.put("downloadThroughput", -1); // -1 for normal speed
		onlineParams.put("uploadThroughput", -1); // -1 for normal speed

		switch (Browser.toLowerCase()) {
		case "chrome":
			((ChromeDriver) driver).executeCdpCommand("Network.emulateNetworkConditions", onlineParams);
			System.out.println("Network connected");
			break;

		case "firefox":
			// not working
			((FirefoxDriver) driver)
					.executeScript("window.navigator.serviceWorker.controller.postMessage({offline: false});");
			break;

		case "edge":
			((EdgeDriver) driver).executeCdpCommand("Network.emulateNetworkConditions", onlineParams);
			break;
		}
	}

	public static void screen_position(WebDriver driver, String position) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = (int) screenSize.getWidth();
		int screenHeight = (int) screenSize.getHeight();
		switch (position.toLowerCase()) {
		case "left":
			driver.manage().window().setSize(new org.openqa.selenium.Dimension(screenWidth / 2, screenHeight));
			caller_driver.manage().window().setPosition(new org.openqa.selenium.Point(0, 0));
			break;

		case "right":
			driver.manage().window().setSize(new org.openqa.selenium.Dimension(screenWidth / 2, screenHeight));
			driver.manage().window().setPosition(new org.openqa.selenium.Point(screenWidth / 2, 0));
			break;
		}
		Map<String, Object> deviceMetrics = new HashMap<>();
		deviceMetrics.put("width", 1280);
		deviceMetrics.put("height", 720);
		deviceMetrics.put("deviceScaleFactor", 1); // No scaling
		deviceMetrics.put("mobile", false); // Not emulating a mobile device
		((ChromiumDriver) driver).executeCdpCommand("Emulation.setDeviceMetricsOverride", deviceMetrics);

	}
}
