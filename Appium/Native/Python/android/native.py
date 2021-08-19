
from appium.webdriver.common.mobileby import MobileBy
from selenium import webdriver
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.support.ui import WebDriverWait

 #  1. Replace <<cloud name>> with your perfecto cloud name (e.g. demo is the cloudName of demo.perfectomobile.com).
cloudName = "<<cloud name>>"

capabilities = {
    #  2. Replace <<security token>> with your perfecto security token.
    'securityToken' : "<<security token>>",
    
    # 3. Set device capabilities.
    'platformName': 'Android',
    'model': 'Galaxy S.*|LG.*',
    
     # 4. Set Perfecto Media repository path of App under test.
    'app': 'PUBLIC:ExpenseTracker/Native/android/ExpenseAppVer1.0.apk',
    
    # 5. Set the unique identifier of your app
    'bundleId': 'io.perfecto.expense.tracker',
    
    # Set other capabilities.
    'enableAppiumBehavior': True, # Enable new architecture of Appium
    'autoLaunch': True, # Whether to have Appium install and launch the app automatically.
    # 'fullReset': false, # Reset app state by uninstalling app
    'takesScreenshot': False,
    'screenshotOnError': True,
    'openDeviceTimeout': 5
}
# Initialize the Appium driver
driver = webdriver.Remote('https://' + cloudName + '.perfectomobile.com/nexperience/perfectomobile/wd/hub', capabilities)
# set implicit wait time 
driver.implicitly_wait(5)

wait = WebDriverWait(driver, 30)

email = wait.until(EC.presence_of_element_located((MobileBy.ID, "io.perfecto.expense.tracker:id/login_email")))
email.send_keys('test@perfecto.com')

password = wait.until(EC.presence_of_element_located((MobileBy.ID, "io.perfecto.expense.tracker:id/login_password")))
password.send_keys('test123')

params = {
  'mode': 'off'
}
driver.execute_script('mobile:keyboard:display', params);
login = wait.until(EC.presence_of_element_located((MobileBy.ID, "io.perfecto.expense.tracker:id/login_login_btn")))
login.click()

add = wait.until(EC.presence_of_element_located((MobileBy.ID, "io.perfecto.expense.tracker:id/list_add_btn")))
add.click()

head = wait.until(EC.presence_of_element_located((MobileBy.ID, "io.perfecto.expense.tracker:id/input_layout_head")))
head.click()

flight = wait.until(EC.presence_of_element_located((MobileBy.XPATH, '//*[@text="Flight"]')))
flight.click()

amount = wait.until(EC.presence_of_element_located((MobileBy.ID, "io.perfecto.expense.tracker:id/add_amount")))
amount.send_keys('100')

save = wait.until(EC.presence_of_element_located((MobileBy.ID, "io.perfecto.expense.tracker:id/layout_buttons")))
save.click()

expectedText = "Select Currency"
wait.until(EC.presence_of_element_located((MobileBy.XPATH, "//*[@text='"+expectedText+"']")))

    
#Quits the driver
driver.quit()
print("Python Android Execution completed")
