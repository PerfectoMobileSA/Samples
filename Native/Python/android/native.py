
import time

from selenium import webdriver

 #  1. Replace <<cloud name>> with your perfecto cloud name (e.g. demo is the cloudName of demo.perfectomobile.com).
cloudName = "<<cloud name>>"

capabilities = {
    #  2. Replace <<security token>> with your perfecto security token.
    'securityToken' : "<<security token>>",
    
    # 3. Set device capabilities.
    'platformName': 'iOS',
    'manufacturer': 'Apple',
    'model': 'iPhone.*',
    
     # 4. Set Perfecto Media repository path of App under test.
    'app': 'PUBLIC:Genesis/Sample/iOSInvoiceApp1.0.ipa',
    
    # 5. Set the unique identifier of your app
    'bundleId': 'io.perfecto.expense.tracker',
    
    # Set other capabilities.
    'enableAppiumBehavior': True, # Enable new architecture of Appium
    'autoLaunch': True, # To work with hybrid applications, install the iOS/Android application as instrumented.
    'autoInstrument': True, # To work with hybrid applications, install the iOS/Android application as instrumented.
    # 'fullReset': false, # Whether to install and launch the app automatically.
}
# Initialize the Appium driver
driver = webdriver.Remote('https://' + cloudName + '.perfectomobile.com/nexperience/perfectomobile/wd/hub', capabilities)
# set implicit wait time 
driver.implicitly_wait(15)
# Your code goes here
time.sleep(5)

#Quits the driver
driver.quit()