using System;
using System.Collections;
using System.Collections.Generic;
using System.Threading;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using OpenQA.Selenium;
using OpenQA.Selenium.Appium;
using OpenQA.Selenium.Appium.iOS;
using OpenQA.Selenium.Support.UI;
using Reportium.Client;
using Reportium.Model;
using Reportium.Test;
using TestContext = Microsoft.VisualStudio.TestTools.UnitTesting.TestContext;

[assembly: Parallelize(Workers = 3, Scope = ExecutionScope.MethodLevel)]
namespace IOS
{
    [TestClass]
    public class IOSTest
    {
        public TestContext TestContext { get; set; }
        IOSDriver<IOSElement> driver = null;
        ReportiumClient reportiumClient = null;

        [TestInitialize]
        public void BeforeTest()
        {
            AppiumOptions capabilities = new AppiumOptions();
            // 1. Replace <<cloud name>> with your perfecto cloud name (e.g. demo is the cloudName of demo.perfectomobile.com).
            String cloudName = "<<cloud name>>";

            // 2. Replace <<security token>> with your perfecto security token.
            String securityToken = "<<security token>>";
            capabilities.AddAdditionalCapability("securityToken", securityToken);

            // 3. Set device capabilities.
            capabilities.PlatformName = "iOS";

            // 4. Set Perfecto Media repository path of App under test.
            capabilities.AddAdditionalCapability("app", "PUBLIC:ExpenseTracker/Native/iOS/InvoiceApp1.0.ipa");

            // 5. Set the unique identifier of your app
            capabilities.AddAdditionalCapability("appPackage", "io.perfecto.expense.tracker");

            // Set other capabilities.
            capabilities.AddAdditionalCapability("enableAppiumBehavior", true);
            capabilities.AddAdditionalCapability("autoLaunch", true); // Whether to install and launch the app automatically.
            capabilities.AddAdditionalCapability("iOSResign", true); // Resign with developer certificate
            // capabilities.AddAdditionalCapability("fullReset", false); // Reset app state by uninstalling app.
            capabilities.AddAdditionalCapability("takesScreenshot", false);
            capabilities.AddAdditionalCapability("screenshotOnError", true);
            capabilities.AddAdditionalCapability("openDeviceTimeout", 5);
            capabilities.AddAdditionalCapability("model", "iPhone.*");

            // Initialize the Appium driver
            driver = new IOSDriver<IOSElement>(
                    new Uri(string.Format("https://{0}.perfectomobile.com/nexperience/perfectomobile/wd/hub/fast", cloudName)), capabilities);
            driver.Manage().Timeouts().ImplicitWait = TimeSpan.FromSeconds(5);

            PerfectoExecutionContext perfectoExecutionContext;
            if (System.Environment.GetEnvironmentVariable("JOB_NAME") != null)
            {
                perfectoExecutionContext = new PerfectoExecutionContext.PerfectoExecutionContextBuilder()
                        .WithProject(new Project("My Project", "1.0"))
                        .WithJob(new Job(System.Environment.GetEnvironmentVariable("JOB_NAME"),
                                int.Parse(System.Environment.GetEnvironmentVariable("BUILD_NUMBER"))))
                        .WithWebDriver(driver).Build();
            }
            else
            {
                perfectoExecutionContext = new PerfectoExecutionContext.PerfectoExecutionContextBuilder()
                        .WithProject(new Project("My Project", "1.0"))
                        .WithWebDriver(driver).Build();
            }
            reportiumClient = PerfectoClientFactory.CreatePerfectoReportiumClient(perfectoExecutionContext);

            reportiumClient.TestStart(TestContext.TestName, new Reportium.Test.TestContext("native", "ios"));
        }


        [TestMethod]
        [Description("Native C# iOS Sample1")]
        public void CSharpIOSNativeTest1()
        {
            try
            {
                WebDriverWait wait = new WebDriverWait(driver, TimeSpan.FromSeconds(30));

                reportiumClient.StepStart("Enter email");
                IOSElement email = (IOSElement)wait.Until(ExpectedConditions.ElementIsVisible((By.Name("login_email"))));
                email.SendKeys("test@perfecto.com");

                reportiumClient.StepStart("Enter password");
                IOSElement password = (IOSElement)wait.Until(ExpectedConditions.ElementIsVisible((By.Name("login_password"))));
                password.SendKeys("test123");
                driver.HideKeyboard();

                reportiumClient.StepStart("Click login");
                driver.HideKeyboard();
                IOSElement login = (IOSElement)wait.Until(ExpectedConditions.ElementIsVisible((By.Name("login_login_btn"))));
                login.Click();

                reportiumClient.StepStart("Add expense");
                IOSElement add = (IOSElement)wait.Until(ExpectedConditions.ElementIsVisible((By.Name("list_add_btn"))));
                add.Click();

                reportiumClient.StepStart("Select head");
                IOSElement head = (IOSElement)wait.Until(ExpectedConditions.ElementIsVisible((By.Name("edit_head"))));
                head.Click();

                wait.Until(ExpectedConditions.PresenceOfAllElementsLocatedBy(By.XPath("//*[@value='- Select -']")))[0].SendKeys("Flight");

                reportiumClient.StepStart("Enter amount");
                IOSElement amount = (IOSElement)wait.Until(ExpectedConditions.ElementIsVisible((By.Name("edit_amount"))));
                amount.SendKeys("100");

                reportiumClient.StepStart("Save expense");
                IOSElement save = (IOSElement)wait.Until(ExpectedConditions.ElementIsVisible((By.Name("add_save_btn"))));
                save.Click();

                reportiumClient.StepStart("Verify alert");
                String expectedText = "Please enter valid category";
                Boolean res = wait.Until(ExpectedConditions.ElementIsVisible(By.XPath("//*[@name='" + expectedText + "']"))).Displayed;
                reportiumClient.ReportiumAssert("Alert text validation.", res);
                reportiumClient.TestStop(Reportium.Test.Result.TestResultFactory.CreateSuccess());
            }
            catch (Exception e)
            {
                reportiumClient.TestStop(Reportium.Test.Result.TestResultFactory.CreateFailure(e));
            }

            ////Close connection and ends the test
            driver.Quit();
            Console.WriteLine("C# IOS execution completed");
        }

        [TestMethod]
        [Description("Native C# iOS Sample2")]
        public void CSharpIOSNativeTest2()
        {
            try
            {
                WebDriverWait wait = new WebDriverWait(driver, TimeSpan.FromSeconds(30));

                reportiumClient.StepStart("Enter email");
                IOSElement email = (IOSElement)wait.Until(ExpectedConditions.ElementIsVisible((By.Name("login_email"))));
                email.SendKeys("test@perfecto.com");

                reportiumClient.StepStart("Enter password");
                IOSElement password = (IOSElement)wait.Until(ExpectedConditions.ElementIsVisible((By.Name("login_password"))));
                password.SendKeys("test123");
                driver.HideKeyboard();

                reportiumClient.StepStart("Click login");
                IOSElement login = (IOSElement)wait.Until(ExpectedConditions.ElementIsVisible((By.Name("login_login_btn"))));
                login.Click();

                reportiumClient.StepStart("Add expense");
                IOSElement add = (IOSElement)wait.Until(ExpectedConditions.ElementIsVisible((By.Name("list_add_btn"))));
                add.Click();

                reportiumClient.StepStart("Select head");
                IOSElement head = (IOSElement)wait.Until(ExpectedConditions.ElementIsVisible((By.Name("edit_head"))));
                head.Click();

                wait.Until(ExpectedConditions.PresenceOfAllElementsLocatedBy(By.XPath("//*[@value='- Select -']")))[0].SendKeys("Flight");

                reportiumClient.StepStart("Enter amount");
                IOSElement amount = (IOSElement)wait.Until(ExpectedConditions.ElementIsVisible((By.Name("edit_amount"))));
                amount.SendKeys("100");

                reportiumClient.StepStart("Save expense");
                IOSElement save = (IOSElement)wait.Until(ExpectedConditions.ElementIsVisible((By.Name("add_save_btn"))));
                save.Click();

                reportiumClient.StepStart("Verify alert");
                String expectedText = "Please enter valid category";
                Boolean res = wait.Until(ExpectedConditions.ElementIsVisible(By.XPath("//*[@name='" + expectedText + "']"))).Displayed;
                reportiumClient.ReportiumAssert("Alert text validation.", res);
                reportiumClient.TestStop(Reportium.Test.Result.TestResultFactory.CreateSuccess());
            }
            catch (Exception e)
            {
                reportiumClient.TestStop(Reportium.Test.Result.TestResultFactory.CreateFailure(e));
            }

            ////Close connection and ends the test
            driver.Quit();
            Console.WriteLine("C# IOS execution completed");
        }

    }
}
