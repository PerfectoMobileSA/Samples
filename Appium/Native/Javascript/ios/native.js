"use strict";

var wd = require("selenium-webdriver");

// 1. Replace <<cloud name>> with your perfecto cloud name (e.g. demo is the cloudName of demo.perfectomobile.com).
var cloudName = "<<cloud name>>"

var desiredCaps = {
    // 2. Replace <<security token>> with your perfecto security token.
    securityToken : "<<security token>>",
    
    // 3. Set device capabilities.
    platformName: 'iOS',
    manufacturer: 'Apple',
    model: 'iPhone.*',

    // 4. Set Perfecto Media repository path of App under test.
    app: 'PUBLIC:Genesis/Sample/iOSInvoiceApp1.0.ipa',
    
    // 5. Set the unique identifier of your app
    bundleId: 'io.perfecto.expense.tracker',

    // Set other capabilities.
    browserName: '',
    enableAppiumBehavior: true, // Enable new architecture of Appium
    autoLaunch: true, // Whether to have Appium install and launch the app automatically.
    autoInstrument: true, // To work with hybrid applications, install the iOS/Android application as instrumented.
    // fullReset: false, // Whether to install and launch the app automatically.
};

async function testApp() {
  // Initialize the driver
  let driver = await new wd.Builder().usingServer('https://' + cloudName + '.perfectomobile.com/nexperience/perfectomobile/wd/hub').withCapabilities(desiredCaps).build();
  
  // Your code goes here
  await driver.sleep(5000);
  
  //Quits the driver
  await driver.quit();
  await console.log("Javascript iOS execution completed")
  }

  testApp()