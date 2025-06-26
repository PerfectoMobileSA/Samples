package android;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
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

public class PerfectoNativeSample {

	public static void main(String[] args) throws Exception {

		DesiredCapabilities capabilities = new DesiredCapabilities("", "", Platform.ANY);

		// 1. Replace <<cloud name>> with your perfecto cloud name (e.g. demo is the
		// cloudName of demo.perfectomobile.com).
		String host = "<<cloud name>>.perfectomobile.com";

		// 2. Replace <<security token>> with your perfecto security token.
		String securityToken = "<<security token>>";

		Map<String, Object> cloudOptions = new HashMap<>();

		cloudOptions.put("platformName", "Android");
		cloudOptions.put("model", "Galaxy S.*|LG.*");
		cloudOptions.put("securityToken", securityToken);
		cloudOptions.put("appiumVersion", "latest");
		cloudOptions.put("enableAppiumBehavior", true);
		cloudOptions.put("automationName", "Appium");
		cloudOptions.put("appPackage", "io.perfecto.expense.tracker");
		cloudOptions.put("app", "PUBLIC:ExpenseTracker/Native/ExpenseAppVer1.3.apk");
		cloudOptions.put("autoLaunch", true); // Whether to install and launch the app automatically.
		capabilities.setCapability("perfecto:options", cloudOptions);

		// Initialize the AndroidDriver driver
		RemoteWebDriver driver = new RemoteWebDriver(new URL("https://" + host + "/nexperience/perfectomobile/wd/hub"), capabilities);

		// Setting implicit wait
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		// Reporting client. For more details, see http://developers.perfectomobile.com/display/PD/Reporting
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

		ReportiumClient reportiumClient = new ReportiumClientFactory().createPerfectoReportiumClient(perfectoExecutionContext);

		reportiumClient.testStart("Native Java Android Sample", new TestContext("native", "android"));

		try{
			reportiumClient.stepStart("Enter email");
			driver.findElement(By.id("login_email")).sendKeys("test@perfecto.com");
			reportiumClient.stepEnd();

			reportiumClient.stepStart("Enter password");
			driver.findElement(By.id("login_password")).sendKeys("test123");
			reportiumClient.stepEnd();

			reportiumClient.stepStart("Click login");
			driver.findElement(By.id("login_login_btn")).click();
			reportiumClient.stepEnd();

			reportiumClient.stepStart("Add expense");
			driver.findElement(By.id("list_add_btn")).click();
			reportiumClient.stepEnd();

			reportiumClient.stepStart("Select head");
			driver.findElement(By.id("input_layout_head")).click();
			driver.findElement(By.xpath("//*[@text=\"Flight\"]")).click();
			reportiumClient.stepEnd();

//			reportiumClient.stepStart("Enter amount");
//			driver.findElement(By.id("add_amount")).sendKeys("100");
//			reportiumClient.stepEnd();
			reportiumClient.stepStart("Enter Amount");
			driver.findElement(By.id("amount_seek_bar")).click();
			reportiumClient.stepEnd();

			reportiumClient.stepStart("Save expense");
			driver.findElement(By.id("layout_buttons")).click();
			reportiumClient.stepEnd();

			reportiumClient.stepStart("Verify alert");
			String expectedText = "Select Currency";
			WebElement alert = driver.findElement(By.id("snackbar_text"));
			reportiumClient.reportiumAssert(expectedText, alert.getText().equalsIgnoreCase(expectedText));
			reportiumClient.stepEnd();

			reportiumClient.stepStart("Select Currency");
			driver.findElement(By.id("signup_currency")).click();
			driver.findElement(By.xpath("//*[@text=\"INR-₹\"]")).click();
			reportiumClient.stepEnd();

			reportiumClient.stepStart("Save expense");
			driver.findElement(By.id("layout_buttons")).click();
			reportiumClient.stepEnd();

			reportiumClient.stepStart("Logout Application");
			driver.findElement(By.xpath("//*[@content-desc=\"Open Drawer\"]")).click();
			driver.findElement(By.xpath("//*[@text=\"Logout\"]")).click();
			driver.findElement(By.xpath("//*[@resource-id=\"android:id/button1\"]")).click();
			reportiumClient.stepEnd();

			reportiumClient.testStop(TestResultFactory.createSuccess());
		} catch (Exception e) {
			reportiumClient.testStop(TestResultFactory.createFailure(e.getMessage(), e));
			e.printStackTrace();
		} finally {
			try {

				driver.quit();

				// Retrieve the URL to the DigitalZoom Report (= Reportium Application) for an aggregated view over the execution
				String reportURL = reportiumClient.getReportUrl();

				// Retrieve the URL to the Execution Summary PDF Report
				String reportPdfUrl = (String)(driver.getCapabilities().getCapability("reportPdfUrl"));
				// For detailed documentation on how to export the Execution Summary PDF Report, the Single Test report and other attachments such as
				// video, images, device logs, vitals and network files - see http://developers.perfectomobile.com/display/PD/Exporting+the+Reports

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
