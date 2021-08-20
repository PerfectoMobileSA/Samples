package android;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Keys;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.perfecto.reportium.client.ReportiumClient;
import com.perfecto.reportium.client.ReportiumClientFactory;
import com.perfecto.reportium.model.Job;
import com.perfecto.reportium.model.PerfectoExecutionContext;
import com.perfecto.reportium.model.Project;
import com.perfecto.reportium.test.TestContext;
import com.perfecto.reportium.test.result.TestResultFactory;

public class Sample {

	public static void main(String[] args) throws Exception {

		DesiredCapabilities capabilities = new DesiredCapabilities("", "", Platform.ANY);

		// 1. Replace <<cloud name>> with your perfecto cloud name (e.g. demo is the cloudName of demo.perfectomobile.com).
		String cloudName = "<<cloud name>>";

		// 2. Replace <<security token>> with your perfecto security token.
		String securityToken = "<<security token>>";
		capabilities.setCapability("securityToken", securityToken);

		// 3. Set device capabilities.
		capabilities.setCapability("platformName", "Android");
		capabilities.setCapability("model", "Galaxy S.*");

		// 4. Other capabilities
		capabilities.setCapability("useAppiumForWeb", true);
		capabilities.setCapability("openDeviceTimeout", 5);

		// Initialize the driver
		RemoteWebDriver driver = new RemoteWebDriver(new URL("https://" + cloudName.replace(".perfectomobile.com", "")
				+ ".perfectomobile.com/nexperience/perfectomobile/wd/hub"), capabilities);

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
					.withProject(new Project("My Project", "1.0")).withWebDriver(driver).build();
		}
		ReportiumClient reportiumClient = new ReportiumClientFactory()
				.createPerfectoReportiumClient(perfectoExecutionContext);

		reportiumClient.testStart("Selenium Java Android Sample", new TestContext("selenium", "android"));

		try {
			WebDriverWait wait = new WebDriverWait(driver, 30);
			String search = "perfectomobile";
			reportiumClient.stepStart("Navigate to Google");
			 Map<String, Object> params = new HashMap<>();
			driver.get("https://www.google.com");
			reportiumClient.stepEnd();

			reportiumClient.stepStart("Search for Perfecto");
			WebElement searchbox = wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//*[@name='q']"))));
			searchbox.sendKeys(search);
			searchbox.sendKeys(Keys.RETURN);
			reportiumClient.stepEnd();
		
			reportiumClient.stepStart("Verify Title");
			String expectedText = "perfectomobile - Google Search";
			System.out.println(driver.getTitle());
			// Adding assertions to the Execution Report. This method will not fail the test
			reportiumClient.reportiumAssert(expectedText, driver.getTitle().contains(expectedText));
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
