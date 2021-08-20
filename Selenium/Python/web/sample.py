from appium.webdriver.common.mobileby import MobileBy
from selenium import webdriver
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.common.keys import Keys

#  1. Replace <<cloud name>> with your perfecto cloud name (e.g. demo is the cloudName of demo.perfectomobile.com).
cloudName = "<<cloud name>>"

capabilities = {
    #  2. Replace <<security token>> with your perfecto security token.
    "securityToken": "<<security token>>",
    # 3. Set web capabilities. More info: https://developers.perfectomobile.com/display/PD/Select+a+device+for+manual+testing#Selectadeviceformanualtesting-genCapGeneratecapabilities
    "platformName": "Windows",
    "platformVersion": "10",
    "browserName": "Chrome",
    "browserVersion": "latest",
    "location": "US East",
    "resolution": "1024x768",
    "takesScreenshot": False,
    "screenshotOnError": True
}
# Initialize the Appium driver
driver = webdriver.Remote(
    "https://" + cloudName + ".perfectomobile.com/nexperience/perfectomobile/wd/hub",
    capabilities,
)
# set implicit wait time
driver.implicitly_wait(5)

timeout = 30
wait = WebDriverWait(driver, timeout)
search = "perfectomobile"

driver.get("https://www.google.com")
searchbox = wait.until(EC.presence_of_element_located((MobileBy.NAME, "q")))
searchbox.send_keys(search)
searchbox.send_keys(Keys.ENTER)
expectedText = "perfectomobile - Google Search"
assert expectedText == driver.title


# Quits the driver
driver.quit()
print("Python web Execution completed")
