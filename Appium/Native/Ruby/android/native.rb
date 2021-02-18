require 'perfecto-reporting'
require 'appium_lib'

desired_caps = {
        #  1. Replace <<cloud name>> with your perfecto cloud name (e.g. demo is the cloudName of demo.perfectomobile.com).
    appium_lib: {
        server_url: 'https://%s.perfectomobile.com/nexperience/perfectomobile/wd/hub' % "<<cloud name>>",  
    },
    caps: {
        #  2. Replace <<security token>> with your perfecto security token.
        securityToken: '<<security token>>',
        
        # 3. Set device capabilities.
        platformName: 'Android',

        # 4. Set Perfecto Media repository path of App under test.
        app: 'PUBLIC:ExpenseTracker/Native/android/ExpenseAppVer1.0.apk',
        
        # 5. Set the unique identifier of your app
        appPackage: 'io.perfecto.expense.tracker',

        # Set other capabilities.
        enableAppiumBehavior: true, # Enable new architecture of Appium
        autoLaunch: true, # Whether to have Appium install and launch the app automatically.
        autoInstrument: true, # To work with hybrid applications, install the iOS/Android application as instrumented.
        # fullReset: false, # Reset app state by uninstalling app
    }
}
# Initialize the Appium driver
@driver = Appium::Driver.new(desired_caps, true).start_driver

# Setting implicit wait
@driver.manage.timeouts.implicit_wait = 5

# Initialize Smart Reporting
perfectoExecutionContext = PerfectoExecutionContext.new(PerfectoExecutionContext::PerfectoExecutionContextBuilder
        .withWebDriver(@driver).build)
@reportiumClient = PerfectoReportiumClient.new(perfectoExecutionContext)
tec = TestContext::TestContextBuilder.build()
@reportiumClient.testStart("Native Ruby Android Sample", tec)

begin
    wait = Selenium::WebDriver::Wait.new(:timeout => 30)

    @reportiumClient.stepStart('Enter email')
    wait.until{ @driver.find_element(:id => 'login_email') }
    @driver.find_element(:id => 'login_email').send_keys('test@perfecto.com')
    @reportiumClient.stepEnd()

    @reportiumClient.stepStart('Enter password')
    wait.until{ @driver.find_element(:id => 'login_password').displayed? }
    @driver.find_element(:id => 'login_password').send_keys('test123')
    @reportiumClient.stepEnd()

    @reportiumClient.stepStart('Click login')
    wait.until{ @driver.find_element(:id => 'login_login_btn').displayed? }
    @driver.find_element(:id => 'login_login_btn').click
    @reportiumClient.stepEnd()

    @reportiumClient.stepStart('Add expense')
    wait.until{ @driver.find_element(:id => 'list_add_btn').displayed? }
    @driver.find_element(:id => 'list_add_btn').click
    @reportiumClient.stepEnd()

    @reportiumClient.stepStart('Select head')
    wait.until{ @driver.find_element(:id => 'input_layout_head').displayed? }
    @driver.find_element(:id => 'input_layout_head').click
    wait.until{ @driver.find_element(:xpath => "//*[@text='Flight']").displayed? }
    @driver.find_element(:xpath => "//*[@text='Flight']").click
    @reportiumClient.stepEnd()

    @reportiumClient.stepStart('Enter amount')
    wait.until{ @driver.find_element(:id => 'add_amount').displayed? }
    @driver.find_element(:id => 'add_amount').send_keys('100') 
    @reportiumClient.stepEnd()

    @reportiumClient.stepStart('Save expense')
    wait.until{ @driver.find_element(:id => 'add_save_btn').displayed? }
    @driver.find_element(:id => 'layout_buttons').click
    @reportiumClient.stepEnd()

    @reportiumClient.stepStart("Verify alert");
    expectedText = "Select Currency"
    wait.until{ @driver.find_element(:xpath => "//*[@text='"+expectedText+"']").displayed? }
    @reportiumClient.reportiumAssert(expectedText, @driver.find_element(:xpath => "//*[@text='"+expectedText+"']").text === expectedText)
    @reportiumClient.stepEnd();
            
    @reportiumClient.testStop(TestResultFactory.createSuccess(), tec)

rescue Exception => exception
    @exception = exception
    @reportiumClient.testStop(TestResultFactory.createFailure(@exception.exception.message, @exception.exception, nil), tec)
    raise exception
ensure
    # Prints the report url
     puts 'report url: ' + @reportiumClient.getReportUrl
     
    #Quits the driver
    @driver.quit
    puts "Ruby Android Execution completed"
end
