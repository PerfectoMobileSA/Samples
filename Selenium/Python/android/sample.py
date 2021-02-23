from appium.webdriver.common.mobileby import MobileBy
from selenium import webdriver
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.support.ui import WebDriverWait

#  1. Replace <<cloud name>> with your perfecto cloud name (e.g. demo is the cloudName of demo.perfectomobile.com).
cloudName = "<<cloud name>>"

capabilities = {
    #  2. Replace <<security token>> with your perfecto security token.
    "securityToken": "<<security token>>",
    
    # 3. Set device capabilities.
    "platformName": "Android",
    "model": 'Galaxy S.*',
    
    # Set other capabilities.
    "browserName": "mobileOS",
    "useAppiumForWeb": True,
    "openDeviceTimeout": 5
    
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
email = wait.until(EC.presence_of_element_located((MobileBy.NAME, "q")))
email.send_keys(search)

q = wait.until(
    EC.presence_of_element_located(
        (MobileBy.XPATH, '(//*[text()="' + search + '"])[1]')
    )
)
q.click()

href = wait.until(
    EC.presence_of_element_located(
        (MobileBy.XPATH, '(//*[contains(@href,"https://www.perfecto.io/")])[1]')
    )
)
driver.execute_script("arguments[0].scrollIntoView()", href)
driver.execute_script("arguments[0].click()", href)

params = {"target": "back"}
driver.execute_script("mobile:browser:navigate", params)
expectedText = "perfectomobile - Google Search"
assert expectedText == driver.title

# Quits the driver
driver.quit()
print("Python Android Execution completed")
