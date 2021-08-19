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
    'platformName': 'iOS',
    'manufacturer': 'Apple',
    'model': 'iPhone.*',
    
     # 4. Set Perfecto Media repository path of App under test.
    'app': 'PUBLIC:ExpenseTracker/Native/iOS/InvoiceApp1.0.ipa',
    
    # 5. Set the unique identifier of your app
    'bundleId': 'io.perfecto.expense.tracker',
    
    # Set other capabilities.
    'enableAppiumBehavior': True, # Enable new architecture of Appium
    'autoLaunch': True, # To work with hybrid applications, install the iOS/Android application as instrumented.
    'iOSResign': True, # Resigns using developer certificate
    # 'fullReset': false, # Reset app state by uninstalling app
    'takesScreenshot': False,
    'screenshotOnError': True,
    'openDeviceTimeout': 5
}
# Initialize the Appium driver
driver = webdriver.Remote('https://' + cloudName + '.perfectomobile.com/nexperience/perfectomobile/wd/hub', capabilities)
# set implicit wait time 
driver.implicitly_wait(5)
# Your code goes here

wait = WebDriverWait(driver, 30)


email = wait.until(EC.presence_of_element_located((MobileBy.NAME, "login_email")))
email.send_keys('test@perfecto.com')

password = wait.until(EC.presence_of_element_located((MobileBy.NAME, "login_password")))
password.send_keys('test123')

login = wait.until(EC.presence_of_element_located((MobileBy.NAME, "login_login_btn")))
login.click()

add = wait.until(EC.presence_of_element_located((MobileBy.NAME, "list_add_btn")))
add.click()

head = wait.until(EC.presence_of_element_located((MobileBy.NAME, "edit_head")))
head.click()

flight = wait.until(EC.presence_of_element_located((MobileBy.XPATH, "//*[@value='- Select -']")))
flight.send_keys("Flight") 

amount = wait.until(EC.presence_of_element_located((MobileBy.NAME, "edit_amount")))
amount.send_keys('100')

save = wait.until(EC.presence_of_element_located((MobileBy.NAME, "add_save_btn")))
save.click()

expectedText = "Please enter valid category"
wait.until(EC.presence_of_element_located((MobileBy.XPATH, "//*[@name='"+expectedText+"']")))

#Quits the driver
driver.quit()
print("Python iOS Execution completed")
