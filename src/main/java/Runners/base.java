package Runners;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chromium.ChromiumDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v126.v126Network;
import org.openqa.selenium.devtools.v128.network.Network;
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
	public static String Browser, caller, receiver, url, janus_Url;
	public static DevTools caller_devTool, receiver_devTool;
	public static Set<String> caller_ws, receiver_ws;

	public WebDriver launchbrowser(String browser) {

		switch (browser.toLowerCase()) {
		case "chrome": {
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--use-fake-ui-for-media-stream");
			options.addArguments("--disable-notifications");
//			options.addArguments("--headless"); // Enable headless mode
//			options.addArguments("--disable-gpu"); // Optional: For Windows systems
			return new ChromeDriver(options);
		}
		case "edge": {
			EdgeOptions options = new EdgeOptions();
			options.addArguments("--use-fake-ui-for-media-stream");
			options.addArguments("--disable-notifications");
			return new EdgeDriver(options);
		}
		case "firefox": {
			FirefoxOptions options = new FirefoxOptions();
			return new FirefoxDriver(options);
		}
		case "safari": {
			SafariOptions options = new SafariOptions();
			return new SafariDriver(options);
		}
		default:
			return null;
		}
	}

	public void Environment(String Env) {
		switch (Env.toUpperCase()) {
		case "QA":
			url = "https://webchat-uikit-qa.contus.us/";

			caller = "7358337102";
			receiver = "7305466010";
			janus_Url = "wss://janus-trickle.mirrorfly.com/";
			break;

		case "DEV":
			url = "https://webchat-uikit-dev.contus.us/";

			caller = "7358337102";
			receiver = "7305466010";
			janus_Url = "wss://janus-trickle.mirrorfly.com/";
			break;

		case "Live":
			url = "https://webchat-uikit-qa.contus.us/";

			caller = "7358331702";
			receiver = "9159673388";
			janus_Url = "wwss://janus.mirrorfly.com/";
			break;
		}
	}

	public static void offline(WebDriver driver) {
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
		onlineParams.put("latency", 0);
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

	public static void block_url(String action, DevTools devTools) {

		switch (action.toLowerCase()) {
		case "block":
			devTools.send(Network.setBlockedURLs(java.util.List.of("*")));
			System.out.println("WebSocket connections blocked.");

			break;

		case "unblock":
			devTools.send(Network.setBlockedURLs(java.util.List.of()));
			System.out.println("WebSocket connections unblocked.");
			break;
		}
	}

	public static DevTools get_devTools(WebDriver driver) {
		DevTools devTools = null;
		switch (Browser.toLowerCase()) {
		case "chrome": {
			devTools = ((ChromeDriver) driver).getDevTools();
			devTools.createSession();
			devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
			return devTools;
		}

		case "edge": {
			devTools = ((EdgeDriver) driver).getDevTools();
			devTools.createSession();
			devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
			return devTools;
		}

		default:
			System.out.println("unsupported driver");
			return devTools;
		}
	}

	public static Set<String> get_ws(DevTools devTools) {
		Set<String> webSocketIds = new HashSet<>();

		devTools.addListener(Network.webSocketCreated(), ws -> {
			System.out.println("WebSocket created: " + ws.getUrl());
			webSocketIds.add(ws.getRequestId().toString());
		});

		devTools.addListener(Network.webSocketClosed(), ws -> {
			System.out.println("WebSocket closed: " + ws.getRequestId());
			webSocketIds.remove(ws.getRequestId().toString());
		});
		return webSocketIds;
	}

	public static String ws_disconnect = "if (window.WebSocket) {"
			+ "    window.WebSocket.prototype.close = function() {"
			+ "        console.log('WebSocket closed manually.');" + "    };" + "    let openSockets = []; "
			+ "    for (const ws of openSockets) {" + "        ws.close();" + "    }" + "} else {"
			+ "    console.log('WebSocket not supported in this browser.');" + "}";
}
