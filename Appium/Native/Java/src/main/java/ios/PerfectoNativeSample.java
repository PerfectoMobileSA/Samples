package ios;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

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

		cloudOptions.put("platformName", "iOS");
		cloudOptions.put("model", "iPhone.*");
		cloudOptions.put("securityToken", securityToken);
		cloudOptions.put("appiumVersion", "latest");
		cloudOptions.put("enableAppiumBehavior", true);
		cloudOptions.put("bundleId", "io.perfecto.expense.tracker");
		cloudOptions.put("iOSResign", "true");
		cloudOptions.put("automationName", "Appium");
		cloudOptions.put("app", "PUBLIC:ExpenseTracker/Native/InvoiceApp1.0.ipa");
		cloudOptions.put("enableAppiumBehavior", true); // Enable new Appium Architecture
		cloudOptions.put("autoLaunch", true); // Whether to install and launch the app automatically.
		capabilities.setCapability("perfecto:options", cloudOptions);


		// Initialize the AndroidDriver driver
		AppiumDriver driver = new AppiumDriver(new URL("https://" + host + "/nexperience/perfectomobile/wd/hub"), capabilities);

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
		reportiumClient.testStart("Native iOS Android Sample", new TestContext("native", "android"));

		try{

			reportiumClient.stepStart("Enter password");
			driver.findElement(By.id("login_password")).sendKeys("test123");
			reportiumClient.stepEnd();

			reportiumClient.stepStart("Enter email");
			driver.findElement(By.id("login_email")).sendKeys("test@perfecto.com");
			reportiumClient.stepEnd();

			reportiumClient.stepStart("Click login");
			driver.findElement(By.xpath("//*[@label=\"done\"]")).click();
			driver.findElement(By.xpath("//*[@name=\"Login\"]")).click();
			reportiumClient.stepEnd();

			reportiumClient.stepStart("Add expense");
			driver.findElement(By.id("list_add_btn")).click();
			reportiumClient.stepEnd();

			reportiumClient.stepStart("Select head");
			driver.findElement(By.id("edit_head")).click();
			WebElement picker = driver.findElement(By.xpath("//XCUIElementTypePickerWheel"));
			picker.sendKeys("Flight");
			reportiumClient.stepEnd();

//			reportiumClient.stepStart("Enter amount");
//			driver.findElement(By.id("add_amount")).sendKeys("100");
//			reportiumClient.stepEnd();
			reportiumClient.stepStart("Enter Amount");
			driver.findElement(By.xpath("//XCUIElementTypeSlider")).click();
			reportiumClient.stepEnd();

			reportiumClient.stepStart("Save expense");
			driver.findElement(By.xpath("//*[@label=\"Save\"]")).click();
			reportiumClient.stepEnd();

			reportiumClient.stepStart("Verify alert");
			String expectedText = "Please enter valid category";
			WebElement alert = driver.findElement(By.xpath("//*[@label=\"Please enter valid category\"]"));
			reportiumClient.reportiumAssert(expectedText, alert.getText().equalsIgnoreCase(expectedText));
			driver.findElement(By.xpath("//*[@label=\"OK\"]")).click();
			reportiumClient.stepEnd();

			reportiumClient.stepStart("Select Category and Date");
			driver.findElement(By.id("edit_category")).click();
			picker = driver.findElement(By.xpath("//XCUIElementTypePickerWheel"));
			picker.sendKeys("Business");
			driver.findElement(By.id("edit_date")).click();
			reportiumClient.stepEnd();

			reportiumClient.stepStart("Save expense");
			driver.findElement(By.xpath("//*[@label=\"Save\"]")).click();
			driver.findElement(By.xpath("//*[@label=\"OK\"]")).click();

			reportiumClient.stepEnd();

			reportiumClient.stepStart("Logout Application");
			driver.findElement(By.xpath("//*[@label=\"side menu\"]")).click();
			driver.findElement(By.xpath("//*[@name=\"list_logout_menu\"]")).click();
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
