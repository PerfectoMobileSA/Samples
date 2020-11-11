package android;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class PerfectoNativeSample {

	public static void main(String[] args) throws Exception {

		DesiredCapabilities capabilities = new DesiredCapabilities("", "", Platform.ANY);

		// 1. Replace <<cloud name>> with your perfecto cloud name (e.g. demo is the
		// cloud name of demo.perfectomobile.com).
		String cloudName = "<<cloud name>>";

		// 2. Replace <<security token>> with your perfecto security token.
		String securityToken = "<<security token>>";
		capabilities.setCapability("securityToken",
				securityToken.equalsIgnoreCase("<<security token>>") ? System.getProperty("securityToken")
						: securityToken);

		// 3. Set device capabilities.
		capabilities.setCapability("model", "Galaxy.*");
		capabilities.setCapability("platformName", "Android");
		capabilities.setCapability("enableAppiumBehavior", true);

		// 4. Set Perfecto Media repository path of App under test.
		capabilities.setCapability("app", "PUBLIC:ExpenseTracker/Native/ExpenseAppVer1.0.apk");

		// 5. Set the unique identifier of your app
		capabilities.setCapability("appPackage", "io.perfecto.expense.tracker");

		// Set other capabilities.
		capabilities.setCapability("fullReset", false);
		capabilities.setCapability("autoLaunch", true);
		capabilities.setCapability("autoInstrument", true);
		
		// Initialize the Appium driver
		AndroidDriver<AndroidElement> driver = new AndroidDriver<AndroidElement>(new URL("https://"
				+ (cloudName.equalsIgnoreCase("<<cloud name>>") ? System.getProperty("cloudName") : cloudName)
				+ ".perfectomobile.com/nexperience/perfectomobile/wd/hub"), capabilities);

		// Setting implicit wait
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

		// Sample test
		AndroidElement email = (AndroidElement) new WebDriverWait(driver, 30)
				.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//*[@resource-id=\"io.perfecto.expense.tracker:id/login_email\"]")));
		email.click();
		email.sendKeys("123");
		driver.quit();
	}
}