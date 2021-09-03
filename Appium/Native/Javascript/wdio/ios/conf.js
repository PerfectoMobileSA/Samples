const path = require('path');
const Reporting = require('perfecto-reporting');
var reportingClient;
//1. Replace <<cloud name>> with your perfecto cloud name (e.g. demo is the cloudName of demo.perfectomobile.com).
const host = '<<cloud name>>';
//   2. Replace <<security token>> with your perfecto security token.
const securityToken = '<<security token>>';

//Define your global tags here:
const tags = ['SampleTag1'];
global.STEP_TIMEOUT = 900000;
global.IMPLICIT_TIMEOUT = 5000;

exports.config = {
    securityToken: securityToken,
    protocol: 'http',
    hostname: host + '.perfectomobile.com',
    path: '/nexperience/perfectomobile/wd/hub',
    port: 80,
    sync: true,
    bail: 0,
    exclude: [],
    specs: [
        'ios/native.js'
    ],
    maxInstances: 1,

    capabilities: [{
        securityToken: securityToken,
        automationName: 'Appium',
        // 3. Set device capabilities.
        platformName: 'iOS',
        model: 'iPhone.*',
        // 4. Set Perfecto Media repository path of App under test.
        app: 'PUBLIC:ExpenseTracker/Native/iOS/InvoiceApp1.0.ipa',

        // 5. Set the unique identifier of your app
        bundleId: 'io.perfecto.expense.tracker',
        autoLaunch: true, // Whether to have Appium install and launch the app automatically.
        iOSResign: true, // To work with hybrid applications, install the iOS/Android application as instrumented.
        // fullReset: false, // Reset app state by uninstalling app
        browserName: '',
        takesScreenshot: false,
        screenshotOnError: true,
        openDeviceTimeout: 5
    }, ],
    // Default timeout for all waitFor* commands.
    waitforTimeout: 30000,
    // Default timeout in milliseconds for request
    // if Selenium Grid doesn't send response
    connectionRetryTimeout: 90000,
    // Default request retries count
    connectionRetryCount: 3,
    framework: 'jasmine',
    // Options to be passed to Jasmine.
    jasmineNodeOpts: {
        // Jasmine default timeout
        defaultTimeoutInterval: 100000,
        // The Jasmine framework allows interception of each assertion in order to log the state of the application
        // or website depending on the result. For example, it is pretty handy to take a screenshot every time
        // an assertion fails.
        expectationResultHandler: function(passed, assertion) {
            // do something
        }
    },
    reporters: ['spec'],
    //
    // =====
    // Hooks
    // =====
    // Gets executed before test execution begins. At this point you can access all global
    // variables, such as `browser`. It is the perfect place to define custom commands.
    before: function(capabilities, specs) {
        jasmine.DEFAULT_TIMEOUT_INTERVAL = 1000000;
        if (process.env.jobName != null) {
            reportingClient = new Reporting.Perfecto.PerfectoReportingClient(new Reporting.Perfecto.PerfectoExecutionContext({
                webdriver: {
                    executeScript: (command, params) => {
                        return browser.execute(command, params);
                    }
                },
                job: new Reporting.Model.Job({
                    jobName: process.env.jobName,
                    buildNumber: parseInt(process.env.jobNumber)
                }),
                tags: tags
            }));
        } else {
            reportingClient = new Reporting.Perfecto.PerfectoReportingClient(new Reporting.Perfecto.PerfectoExecutionContext({
                webdriver: {
                    executeScript: (command, params) => {
                        return browser.execute(command, params);
                    }
                },
                tags: tags
            }));
        }
        browser.reportingClient = reportingClient;

        var myReporter = {
            specStarted: function(result) {
                reportingClient.testStart(result.fullName);
            },
            specDone: async function(result) {
                if (result.status === 'failed') {
                    const failure = await result.failedExpectations[result.failedExpectations.length - 1];
                    await reportingClient.testStop({
                        status: Reporting.Constants.results.failed,
                        message: `${failure.message} ${failure.stack}`
                    });
                } else {
                    await reportingClient.testStop({
                        status: Reporting.Constants.results.passed
                    });
                }
            }
        }
        jasmine.getEnv().addReporter(myReporter);
    },
    // Gets executed after all tests are done. You still have access to all global variables from
    // the test.
    after: async function(result, capabilities, specs) {
        await console.log("\n\nReport: " + browser.capabilities['testGridReportUrl']);
    },
}