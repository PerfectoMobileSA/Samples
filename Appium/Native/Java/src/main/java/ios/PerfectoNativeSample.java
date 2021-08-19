package ios;

import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.perfecto.reportium.client.ReportiumClient;
import com.perfecto.reportium.client.ReportiumClientFactory;
import com.perfecto.reportium.model.PerfectoExecutionContext;
import com.perfecto.reportium.model.Project;
import com.perfecto.reportium.test.TestContext;
import com.perfecto.reportium.test.result.TestResultFactory;
import com.perfecto.reportium.model.Job;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;

public class PerfectoNativeSample {

	public static void main(String[] args) throws Exception {

		DesiredCapabilities capabilities = new DesiredCapabilities("", "", Platform.ANY);

		// 1. Replace <<cloud name>> with your perfecto cloud name (e.g. demo is the
		// cloud name of demo.perfectomobile.com).
		String cloudName = "<<cloud name>>";

		// 2. Replace <<security token>> with your perfecto security token.
		String securityToken = "<<security token>>";
	
		capabilities.setCapability("securityToken", securityToken);

		// 3. Set device capabilities.
		capabilities.setCapability("platformName", "iOS");
		capabilities.setCapability("model", "iPhone.*");

		// 4. Set Perfecto Media repository path of App under test.
		capabilities.setCapability("app", "PUBLIC:ExpenseTracker/Native/iOS/InvoiceApp1.0.ipa");

		// 5. Set the unique identifier of your app
		capabilities.setCapability("bundleId", "io.perfecto.expense.tracker");

		// Set other capabilities.
		capabilities.setCapability("enableAppiumBehavior", true);
		capabilities.setCapability("autoLaunch", true); // Whether to install and launch the app automatically.
		capabilities.setCapability("iOSResign",true); // Resign with developer certificate
		// capabilities.setCapability("fullReset", false); // Reset app state by  uninstalling app.
		capabilities.setCapability("takesScreenshot", false);
		capabilities.setCapability("screenshotOnError", true);
		capabilities.setCapability("openDeviceTimeout", 5);

		// Initialize the IOSDriver driver
		IOSDriver<IOSElement> driver = new IOSDriver<IOSElement>(
				new URL("https://" + cloudName + ".perfectomobile.com/nexperience/perfectomobile/wd/hub"),
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

		reportiumClient.testStart("Native Java iOS Sample", new TestContext("native", "ios"));

		try {
			// Sample test
			reportiumClient.stepStart("Enter email");
			WebDriverWait wait = new WebDriverWait(driver, 30);
			IOSElement email = (IOSElement) wait
					.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.name("login_email"))));
			email.sendKeys("test@perfecto.com");
			reportiumClient.stepEnd();

			reportiumClient.stepStart("Enter password");
			IOSElement password = (IOSElement) wait
					.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.name("login_password"))));
			password.sendKeys("test123");
			reportiumClient.stepEnd();

			reportiumClient.stepStart("Click login");
			driver.hideKeyboard();
			IOSElement login = (IOSElement) wait
					.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.name("login_login_btn"))));
			login.click();
			reportiumClient.stepEnd();

			reportiumClient.stepStart("Add expense");
			IOSElement add_expense = (IOSElement) wait
					.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.name("list_add_btn"))));
			add_expense.click();
			reportiumClient.stepEnd();

			reportiumClient.stepStart("Select head");
			IOSElement head = (IOSElement) wait
					.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.name("edit_head"))));
			head.click();
			List<WebElement> picker = wait
					.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//*[@value=\"- Select -\"]")));
			picker.get(0).sendKeys("Flight");
			reportiumClient.stepEnd();

			reportiumClient.stepStart("Enter amount");
			IOSElement amount = (IOSElement) wait
			.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.name("edit_amount"))));
			amount.sendKeys("100");
			reportiumClient.stepEnd();

			reportiumClient.stepStart("Save expense");
			IOSElement save_expense = (IOSElement) wait
			.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.name("add_save_btn"))));
			save_expense.click();
			reportiumClient.stepEnd();

			reportiumClient.stepStart("Verify alert");
			String expectedText = "Please enter valid category";
			IOSElement alert = (IOSElement) wait
			.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.name(expectedText))));
			reportiumClient.reportiumAssert(expectedText, alert.getText().equalsIgnoreCase(expectedText));
			reportiumClient.stepEnd();

			reportiumClient.testStop(TestResultFactory.createSuccess());
		} catch (Exception e) {
			reportiumClient.testStop(TestResultFactory.createFailure(e));
			assert (false);
		}

		// Prints the Smart Reporting URL
		String reportURL = reportiumClient.getReportUrl();
		System.out.println("Report url - " + reportURL);

		// Quits the driver
		driver.quit();
	}
}
