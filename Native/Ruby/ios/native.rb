require 'appium_lib'

desired_caps = {
    #  1. Replace <<cloud name>> with your perfecto cloud name (e.g. demo is the cloudName of demo.perfectomobile.com).
    appium_lib: {
        server_url: 'https://%s.perfectomobile.com/nexperience/perfectomobile/wd/hub' % "<<cloud name>>", 
    },
    caps: {
        #  2. Replace <<security token>> with your perfecto security token.
        securityToken: "<<security token>>", 
        
        # 3. Set device capabilities.
        platformName: 'iOS',
        manufacturer: 'Apple',
        model: 'iPhone.*',

        # 4. Set Perfecto Media repository path of App under test.
        app: 'PUBLIC:Genesis/Sample/iOSInvoiceApp1.0.ipa',
        
        # 5. Set the unique identifier of your app
        bundleId: 'io.perfecto.expense.tracker',

        # Set other capabilities.
        enableAppiumBehavior: true, # Enable new architecture of Appium
        autoLaunch: true, # To work with hybrid applications, install the iOS/Android application as instrumented.
        autoInstrument: true, # To work with hybrid applications, install the iOS/Android application as instrumented.
        # fullReset: false, # Whether to install and launch the app automatically.
    }
}
# Initialize the Appium driver
@driver = Appium::Driver.new(desired_caps, true)
# Setting implicit wait
@driver.start_driver.manage.timeouts.implicit_wait = 15

# Your code goes here
sleep(5)

#Quits the driver
@driver.driver_quit
    