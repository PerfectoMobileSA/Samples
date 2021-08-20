using System;
using System.Drawing;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using OpenQA.Selenium;
using OpenQA.Selenium.Remote;
using OpenQA.Selenium.Support.UI;
using Reportium.Client;
using Reportium.Model;
using Reportium.Test.Result;

namespace PerfectoCSharpWebSample
{
	[TestClass]
    public class Sample
    {
		[TestMethod]
        public void WebTest()
        {
			DesiredCapabilities capabilities = new DesiredCapabilities();

			// 1. Replace <<cloud name>> with your perfecto cloud name (e.g. demo is the cloudName of demo.perfectomobile.com).
			String cloudName = "<<cloud name>>";

			// 2. Replace <<security token>> with your perfecto security token.
			String securityToken = "<<security token>>";
			capabilities.SetCapability("securityToken", securityToken);

			// 3. Set web capabilities.
			capabilities.SetCapability("platformName", "Windows");
			capabilities.SetCapability("platformVersion", "10");
			capabilities.SetCapability("browserName", "Chrome");
			capabilities.SetCapability("browserVersion", "latest");
			capabilities.SetCapability("location", "US East");
			capabilities.SetCapability("resolution", "1024x768");

			// Set other capabilities.
			capabilities.SetCapability("takesScreenshot", false);
			capabilities.SetCapability("screenshotOnError", true);

			// Initialize the  driver
			RemoteWebDriver driver = new RemoteWebDriver(
					new Uri("https://" + cloudName.Replace(".perfectomobile.com", "")
					+ ".perfectomobile.com/nexperience/perfectomobile/wd/hub"),
					capabilities);

			// Setting implicit wait
			driver.Manage().Timeouts().ImplicitWait = TimeSpan.FromSeconds(5);

			PerfectoExecutionContext perfectoExecutionContext;
			if (System.Environment.GetEnvironmentVariable("jobName") != null)
			{
				perfectoExecutionContext = new PerfectoExecutionContext.PerfectoExecutionContextBuilder()
						.WithProject(new Project("My Project", "1.0"))
						.WithJob(new Job(System.Environment.GetEnvironmentVariable("jobName"),
								int.Parse(System.Environment.GetEnvironmentVariable("jobNumber"))))
						.WithWebDriver(driver).Build();
			}
			else
			{
				perfectoExecutionContext = new PerfectoExecutionContext.PerfectoExecutionContextBuilder()
						.WithProject(new Project("My Project", "1.0"))
						.WithWebDriver(driver).Build();
			}
			ReportiumClient reportiumClient = PerfectoClientFactory.CreatePerfectoReportiumClient(perfectoExecutionContext);

			reportiumClient.TestStart("Selenium C# Web Sample", new Reportium.Test.TestContext("selenium", "web"));

			try
			{
				WebDriverWait wait = new WebDriverWait(driver, TimeSpan.FromSeconds(30));
				String search = "perfectomobile";
				reportiumClient.StepStart("Navigate to Google");
				driver.Url = "https://www.google.com";
				reportiumClient.StepEnd();

				reportiumClient.StepStart("Search for " + search);
				IWebElement searchbox = wait.Until(ExpectedConditions.ElementToBeClickable(driver.FindElement(By.XPath("//*[@name='q']"))));
				searchbox.SendKeys(search);
				searchbox.SendKeys(Keys.Enter);
				reportiumClient.StepEnd();

				reportiumClient.StepStart("Verify Title");
				String expectedText = "perfectomobile - Google Search";
				// Adding assertions to the Execution Report. This method will not fail the test
				reportiumClient.ReportiumAssert(expectedText, driver.Title.Contains(expectedText));
				reportiumClient.StepEnd();

				reportiumClient.TestStop(TestResultFactory.CreateSuccess());
			}
			catch (Exception e)
			{
				reportiumClient.TestStop(TestResultFactory.CreateFailure(e));
			}

			// Prints the Smart Reporting URL
			String reportURL = reportiumClient.GetReportUrl();
			Console.WriteLine("Report url - " + reportURL);

			// Quits the driver
			driver.Quit();
		}
    }
}
