package android;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.perfecto.reportium.client.ReportiumClient;
import com.perfecto.reportium.client.ReportiumClientFactory;
import com.perfecto.reportium.model.PerfectoExecutionContext;
import com.perfecto.reportium.model.Project;
import com.perfecto.reportium.model.Job;
import com.perfecto.reportium.test.TestContext;
import com.perfecto.reportium.test.result.TestResultFactory;
import org.openqa.selenium.By;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class PerfectoNativeSample {

	public static void main(String[] args) throws Exception {

		DesiredCapabilities capabilities = new DesiredCapabilities("", "", Platform.ANY);

		// 1. Replace <<cloud name>> with your perfecto cloud name (e.g. demo is the
		// cloudName of demo.perfectomobile.com).
		String cloudName = "<<cloud name>>";

		// 2. Replace <<security token>> with your perfecto security token.
		String securityToken = "<<security token>>";
		capabilities.setCapability("securityToken", securityToken);

		// 3. Set device capabilities.
		capabilities.setCapability("platformName", "Android");
		capabilities.setCapability("model", "Galaxy S.*|LG.*");
		
		// 4. Set Perfecto Media repository path of App under test.
		capabilities.setCapability("app", "PUBLIC:ExpenseTracker/Native/android/ExpenseAppVer1.0.apk");

		// 5. Set the unique identifier of your app
		capabilities.setCapability("appPackage", "io.perfecto.expense.tracker");

		// Set other capabilities.
		capabilities.setCapability("enableAppiumBehavior", true); // Enable new Appium Architecture
		capabilities.setCapability("autoLaunch", true); // Whether to install and launch the app automatically.
		// capabilities.setCapability("fullReset", false); // Reset app state by uninstalling app.
		capabilities.setCapability("takesScreenshot", false);
		capabilities.setCapability("screenshotOnError", true);
		capabilities.setCapability("openDeviceTimeout", 5);

		// Initialize the AndroidDriver driver
		AndroidDriver<AndroidElement> driver = new AndroidDriver<AndroidElement>(
				new URL("https://" + cloudName.replace(".perfectomobile.com", "")
						+ ".perfectomobile.com/nexperience/perfectomobile/wd/hub"),
				capabilities);

		// Setting implicit wait
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		PerfectoExecutionContext perfectoExecutionContext;
		if (System.getProperty("jobName") != null) {
			perfectoExecutionContext = new PerfectoExecutionContext.PerfectoExecutionContextBuilder()
					.withProject(new Project("My Project", "1.0"))
					.withJob(new Job(System.getProperty("jobName"),
							Integer.parseInt(System.getProperty("jobNumber"))))
					.withWebDriver(driver).build();
		} else {
			perfectoExecutionContext = new PerfectoExecutionContext.PerfectoExecutionContextBuilder()
					.withProject(new Project("My Project", "1.0"))
					.withWebDriver(driver).build();
		}
		ReportiumClient reportiumClient = new ReportiumClientFactory()
				.createPerfectoReportiumClient(perfectoExecutionContext);

		reportiumClient.testStart("Native Java Android Sample", new TestContext("native", "android"));

		try {
			// Sample test
			reportiumClient.stepStart("Enter email");
			WebDriverWait wait = new WebDriverWait(driver, 30);
			AndroidElement email = (AndroidElement) wait.until(ExpectedConditions.elementToBeClickable(
					driver.findElement(By.id("login_email"))));
			email.sendKeys("test@perfecto.com");
			reportiumClient.stepEnd();

			reportiumClient.stepStart("Enter password");
			AndroidElement password = (AndroidElement) wait.until(ExpectedConditions.elementToBeClickable(
					driver.findElement(By.id("login_password"))));
			password.sendKeys("test123");
			reportiumClient.stepEnd();

			reportiumClient.stepStart("Click login");
			driver.hideKeyboard();
			AndroidElement login = (AndroidElement) wait.until(ExpectedConditions.elementToBeClickable(
					driver.findElement(By.id("login_login_btn"))));
			login.click();
			reportiumClient.stepEnd();

			reportiumClient.stepStart("Add expense");
			AndroidElement add_expense = (AndroidElement) wait.until(ExpectedConditions.elementToBeClickable(
					driver.findElement(By.id("list_add_btn"))));
			add_expense.click();
			reportiumClient.stepEnd();

			reportiumClient.stepStart("Select head");
			AndroidElement head = (AndroidElement) wait.until(ExpectedConditions.elementToBeClickable(
					driver.findElement(By.id("input_layout_head"))));
			head.click();
			AndroidElement flight_option = (AndroidElement) wait
					.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//*[@text=\"Flight\"]")));
			flight_option.click();
			reportiumClient.stepEnd();

			reportiumClient.stepStart("Enter amount");
			AndroidElement amount = (AndroidElement) wait.until(ExpectedConditions.elementToBeClickable(
				driver.findElement(By.id("add_amount"))));
			amount.sendKeys("100");
			reportiumClient.stepEnd();

			reportiumClient.stepStart("Save expense");
			AndroidElement save_expense = (AndroidElement) wait.until(ExpectedConditions.elementToBeClickable(
				driver.findElement(By.id("layout_buttons"))));
			save_expense.click();
			reportiumClient.stepEnd();

			reportiumClient.stepStart("Verify alert");
			String expectedText = "Select Currency";
			AndroidElement alert = (AndroidElement) wait.until(ExpectedConditions.elementToBeClickable(
					driver.findElementByXPath("//*[ @text='" + expectedText + "']")));
			reportiumClient.reportiumAssert(expectedText, alert.getText().equalsIgnoreCase(expectedText));
			reportiumClient.stepEnd();

			reportiumClient.testStop(TestResultFactory.createSuccess());
		} catch (Exception e) {
			reportiumClient.testStop(TestResultFactory.createFailure(e));
		}

		// Prints the Smart Reporting URL
		String reportURL = reportiumClient.getReportUrl();
		System.out.println("Report url - " + reportURL);

		// Quits the driver
		driver.quit();
	}
}
