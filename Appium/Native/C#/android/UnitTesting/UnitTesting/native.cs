using Microsoft.VisualStudio.TestTools.UnitTesting;
using System;
using OpenQA.Selenium.Appium;
using OpenQA.Selenium.Appium.Android;
using System.Threading;

namespace Android
{

    [TestClass]
    public class AndroidTest
    {

        [TestMethod]
        public void AndroidSample()
        {
            AppiumOptions capabilities = new AppiumOptions();
            // 1. Replace <<cloud name>> with your perfecto cloud name (e.g. demo is the cloudName of demo.perfectomobile.com).
            String cloudName = "<<cloud name>>";

            // 2. Replace <<security token>> with your perfecto security token.
            String securityToken = "<<security token>>";
            capabilities.AddAdditionalCapability("securityToken", securityToken);

            // 3. Set device capabilities.
            capabilities.PlatformName = "Android";

            // 4. Set Perfecto Media repository path of App under test.
            capabilities.AddAdditionalCapability("app", "PUBLIC:ExpenseTracker/Native/android/ExpenseAppVer1.0.apk");

            // 5. Set the unique identifier of your app
            capabilities.AddAdditionalCapability("appPackage", "io.perfecto.expense.tracker");

            // Set other capabilities.
            capabilities.AddAdditionalCapability("enableAppiumBehavior", true);
            capabilities.AddAdditionalCapability("autoLaunch", true); // Whether to install and launch the app automatically.
            capabilities.AddAdditionalCapability("autoInstrument", true); // To work with hybrid applications, install the iOS/Android application as instrumented.
            // capabilities.AddAdditionalCapability("fullReset", false); // Reset app state by uninstalling app.

            // Initialize the Appium driver
            AndroidDriver<AndroidElement> driver = new AndroidDriver<AndroidElement>(
                    new Uri(string.Format("https://{0}.perfectomobile.com/nexperience/perfectomobile/wd/hub/fast", cloudName)), capabilities);

            // Your code goes here
            Thread.Sleep(5000);

            //Close connection and ends the test
            driver.Quit();
            Console.WriteLine("C# Android execution completed");

        }

    }

}
