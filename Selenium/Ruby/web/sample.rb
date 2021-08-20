require 'perfecto-reporting'
require 'selenium-webdriver'

#  1. Replace <<cloud name>> with your perfecto cloud name (e.g. demo is the cloudName of demo.perfectomobile.com).
server_url = 'https://%s.perfectomobile.com/nexperience/perfectomobile/wd/hub' % "<<cloud name>>" 
   
capabilities = {
        #  2. Replace <<security token>> with your perfecto security token.
        securityToken: '<<security token>>',

        # 3. Set web capabilities. More info: https://developers.perfectomobile.com/display/PD/Select+a+device+for+manual+testing#Selectadeviceformanualtesting-genCapGeneratecapabilities   
        platformName: 'Windows',
        platformVersion: '10',
        browserName: 'Chrome',
        browserVersion: 'latest',
        location: 'US East',
        resolution: '1024x768',
        takesScreenshot: false,
        screenshotOnError: true
}
# Initialize the driver
@driver = Selenium::WebDriver.for(:remote, :url => server_url, :desired_capabilities => capabilities)

# Setting implicit wait
@driver.manage.timeouts.implicit_wait = 5

# Initialize Smart Reporting
if ENV["jobName"] != nil
    perfectoExecutionContext = PerfectoExecutionContext.new(PerfectoExecutionContext::PerfectoExecutionContextBuilder
    .withWebDriver(@driver).withJob(Job.new(ENV["jobName"], ENV["jobNumber"].to_i)).build)
else
    perfectoExecutionContext = PerfectoExecutionContext.new(PerfectoExecutionContext::PerfectoExecutionContextBuilder
            .withWebDriver(@driver).build)
end
@reportiumClient = PerfectoReportiumClient.new(perfectoExecutionContext)
tec = TestContext::TestContextBuilder.build()
@reportiumClient.testStart("Selenium Ruby Web Sample", tec)

begin
    timeout = 30
    wait = Selenium::WebDriver::Wait.new(:timeout => timeout)
    search = "perfectomobile"
    @reportiumClient.stepStart('Navigate to Google');
    @driver.get('https://www.google.com');
    @reportiumClient.stepEnd();

    @reportiumClient.stepStart('Search for ' + search);
    wait.until{ @driver.find_element(:xpath => '//*[@name="q"]') }
    @driver.find_element(:xpath => '//*[@name="q"]').send_keys(search)
    @driver.find_element(:name => 'q').send_keys:return
    @reportiumClient.stepEnd();

    @reportiumClient.stepStart('Verify Title');
    expectedText = "perfectomobile - Google Search";
    @reportiumClient.reportiumAssert(expectedText, @driver.title === expectedText)
    @reportiumClient.stepEnd();
            
    @reportiumClient.testStop(TestResultFactory.createSuccess(), tec)

rescue Exception => exception
    @exception = exception
    @reportiumClient.testStop(TestResultFactory.createFailure(@exception.exception.message, @exception.exception, nil), tec)
    raise exception
ensure
    # Prints the report url
     puts 'Report url - ' + @reportiumClient.getReportUrl
     
    #Quits the driver
    @driver.quit
    puts "Ruby web Execution completed"
end