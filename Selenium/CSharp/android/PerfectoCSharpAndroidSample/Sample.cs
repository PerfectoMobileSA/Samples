using System;
using System.Collections.Generic;
using System.Drawing;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using OpenQA.Selenium;
using OpenQA.Selenium.Remote;
using OpenQA.Selenium.Support.UI;
using Reportium.Client;
using Reportium.Model;
using Reportium.Test.Result;
namespace PerfectoCSharpAndroidSample
{
    [TestClass]
    public class Sample
    {
        [TestMethod]
        public void AndroidTest()
        {
			DesiredCapabilities capabilities = new DesiredCapabilities();

			// 1. Replace <<cloud name>> with your perfecto cloud name (e.g. demo is the cloudName of demo.perfectomobile.com).
			String cloudName = "<<cloud name>>";

			// 2. Replace <<security token>> with your perfecto security token.
			String securityToken = "<<security token>>";
			capabilities.SetCapability("securityToken", securityToken);

			// 3. Set web capabilities.
			capabilities.SetCapability("platformName", "Android");
			capabilities.SetCapability("model", "Galaxy S.*");
			capabilities.SetCapability("browserName", "Chrome");
			capabilities.SetCapability("useAppiumForWeb", true);
            capabilities.SetCapability("openDeviceTimeout", 5);


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

			reportiumClient.TestStart("Selenium C# Android Sample", new Reportium.Test.TestContext("selenium", "android"));

			try
			{
				WebDriverWait wait = new WebDriverWait(driver, TimeSpan.FromSeconds(30));
				String search = "perfectomobile";
				reportiumClient.StepStart("Navigate to Google");
				Dictionary<String, Object> pars = new Dictionary<String, Object>();
				driver.Url = "https://www.google.com";
				reportiumClient.StepEnd();

				reportiumClient.StepStart("Search for Perfecto");
				IWebElement email = wait.Until(ExpectedConditions.ElementToBeClickable(driver.FindElement(By.XPath("//*[@name='q']"))));
				email.SendKeys(search);
				reportiumClient.StepEnd();

				reportiumClient.StepStart("Select Perfecto");
				IWebElement searchItem = wait.Until(ExpectedConditions
						.ElementToBeClickable(driver.FindElement(By.XPath("(//*[text()='" + search + "'])[1]"))));
				searchItem.Click();
				reportiumClient.StepEnd();

				reportiumClient.StepStart("Navigate to Perfecto");
				IWebElement href = wait.Until(ExpectedConditions.ElementIsVisible(By.XPath("(//*[contains(@href,'https://www.perfecto.io/')])[1]")));
				driver.ExecuteScript("arguments[0].scrollIntoView()", href);
				driver.ExecuteScript("arguments[0].click()", href);
				reportiumClient.StepEnd();

				reportiumClient.StepStart("Verify Perfecto page load");
				wait.Until(ExpectedConditions.ElementToBeClickable(driver.FindElement(By.XPath("//*[@alt='Home']"))));
				reportiumClient.StepEnd();

				reportiumClient.StepStart("Press back");
				pars = new Dictionary<String, Object>();
				pars.Add("target", "back");
				driver.ExecuteScript("mobile:browser:navigate", pars);
				reportiumClient.StepEnd();

				reportiumClient.StepStart("Verify Title");
				String expectedText = "perfectomobile - Google Search";
				Console.WriteLine(driver.Title);
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
