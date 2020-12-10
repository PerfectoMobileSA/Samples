package ios;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSElement;

public class PerfectoNativeSample {

	public static void main(String[] args) throws Exception {

		DesiredCapabilities capabilities = new DesiredCapabilities("", "", Platform.ANY);

		// 1. Replace <<cloud name>> with your perfecto cloud name (e.g. demo is the cloud name of demo.perfectomobile.com).
		String cloudName = "<<cloud name>>";

		// 2. Replace <<security token>> with your perfecto security token.
		String securityToken = "<<security token>>";
		capabilities.setCapability("securityToken", securityToken);

		// 3. Set device capabilities.
		capabilities.setCapability("model", "iPhone.*");
		capabilities.setCapability("platformVersion", "13.*");
		capabilities.setCapability("platformName", "iOS");
		capabilities.setCapability("enableAppiumBehavior", true);

		// 4. Set Perfecto Media repository path of App under test.
		capabilities.setCapability("app", "PUBLIC:Genesis/Sample/iOSInvoiceApp1.0.ipa");

		// 5. Set the unique identifier of your app
		capabilities.setCapability("bundleId", "io.perfecto.expense.tracker");

		// Set other capabilities.
		capabilities.setCapability("autoLaunch", true); // Whether to install and launch the app automatically.
		capabilities.setCapability("autoInstrument", true); // To work with hybrid applications, install the iOS/Android application as instrumented.
		// capabilities.setCapability("fullReset", false); // Reset app state by uninstalling app.
		

		// Initialize the Appium driver
		AppiumDriver<?> driver = new AppiumDriver<>(new URL("https://" + cloudName + ".perfectomobile.com/nexperience/perfectomobile/wd/hub"), capabilities);

		// Setting implicit wait
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

		// Sample test
		IOSElement email = (IOSElement) new WebDriverWait(driver, 30)
				.until(ExpectedConditions.elementToBeClickable(driver.findElementByName("login_email")));
		email.click();
		email.sendKeys("123");
		driver.quit();
	}
}
