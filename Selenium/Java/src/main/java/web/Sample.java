package web;

import com.perfecto.reportium.client.ReportiumClient;
import com.perfecto.reportium.client.ReportiumClientFactory;
import com.perfecto.reportium.model.Job;
import com.perfecto.reportium.model.PerfectoExecutionContext;
import com.perfecto.reportium.model.Project;
import com.perfecto.reportium.test.TestContext;
import com.perfecto.reportium.test.result.TestResultFactory;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Sample {

	public static void main(String[] args) throws Exception {

		DesiredCapabilities capabilities = new DesiredCapabilities("", "", Platform.ANY);

		// 1. Replace <<cloud name>> with your perfecto cloud name (e.g. demo is the
		// cloudName of demo.perfectomobile.com).
		String host = "<<cloud name>>.perfectomobile.com";

		// 2. Replace <<security token>> with your perfecto security token.
		String securityToken = "<<security token>>";

		Map<String, Object> cloudOptions = new HashMap<>();

		cloudOptions.put("platformName", "Windows");
		cloudOptions.put("platformVersion", "11");
		cloudOptions.put("securityToken", securityToken);
		cloudOptions.put("browserName", "Chrome");
		cloudOptions.put("browserVersion", "latest");
		cloudOptions.put("location", "US East");
		cloudOptions.put("resolution", "1024x768");

		capabilities.setCapability("perfecto:options", cloudOptions);
		capabilities.setCapability("automationName", "Appium");

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

		reportiumClient.testStart("Selenium Desktop Web Sample", new TestContext("native", "android"));

		try{

			reportiumClient.stepStart("Navigate to Dbank Demo page");
			Map<String, Object> params = new HashMap<>();
			driver.get("http://dbankdemo.com/bank/login");
			reportiumClient.stepEnd();

			reportiumClient.stepStart("Login Dbank Demo application");
			driver.findElement(By.id("username")).sendKeys("jsmith@demo.io");
			driver.findElement(By.id("password")).sendKeys("Demo123!");
			driver.findElement(By.id("submit")).click();
			reportiumClient.stepEnd();

			reportiumClient.stepStart("Verify Title");
			String expectedText = "Dashboard";
			String test = driver.findElement(By.id("page-title")).getText();
			// Adding assertions to the Execution Report. This method will not fail the test
			reportiumClient.reportiumAssert(expectedText, driver.findElement(By.id("page-title")).getText().contains(expectedText));
			reportiumClient.stepEnd();

			reportiumClient.stepStart("Logout Application");
			driver.findElement(By.xpath("//img[@alt='User Avatar']")).click();
			driver.findElement(By.xpath("//a[@href='/bank/logout']")).click();
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
