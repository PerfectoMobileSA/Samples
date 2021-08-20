let perfectoReporting = require('perfecto-reporting');
var reportingClient;

exports.config = {
    //  1. Replace <<cloud name>> with your perfecto cloud name (e.g. demo is the cloudName of demo.perfectomobile.com).
    seleniumAddress: 'https://<<cloud name>>.perfectomobile.com/nexperience/perfectomobile/wd/hub/fast',
    specs: ['sample.js'],
    multiCapabilities: [{
        // 2. Replace <<security token>> with your perfecto security token.
        securityToken: '<<security token>>',

        // 3. Set device capabilities.
        platformName: 'Android',
        model: 'Galaxy S.*',

        // Set other capabilities.
        browserName: 'mobileOS',
        useAppiumForWeb: true,
        openDeviceTimeout: 5
    }],
    //default page loading timeout in ms
    getPageTimeout: 10000,

    //set perfecto reporter
    onPrepare: function() {
        browser.ignoreSynchronization = true;
        var perfectoExecutionContext;
        if (process.env.jobName != null) {
            perfectoExecutionContext = new perfectoReporting.Perfecto.PerfectoExecutionContext({
                webdriver: browser.driver,
                job: new perfectoReporting.Model.Job({
                    jobName: process.env.jobName,
                    buildNumber: parseInt(process.env.jobNumber)
                }),
                tags: ['jasmine', 'protractor']
            });
        } else {
            perfectoExecutionContext = new perfectoReporting.Perfecto.PerfectoExecutionContext({
                webdriver: browser.driver,
                tags: ['jasmine', 'protractor']
            });
        }
        reportingClient = new perfectoReporting.Perfecto.PerfectoReportingClient(perfectoExecutionContext);
        browser.reportingClient = reportingClient;


        var perfectoReporter = {
            jasmineStarted: function(suiteInfo) {
                // put insome info on jasmine started
            },
            suiteStarted: (result) => {
                // here you can add some custom code to execute when each suite is started
            },
            specStarted: (result) => {
                // each spec will be a test in Perfecto Reporting
                reportingClient.testStart(result.fullName);
            },
            specDone: (result) => {
                // ending the test
                // here we report about test end event

                if (result.status === 'failed') {
                    // on a failure we report the failure message and stack trace
                    console.log('Test status is: ' + result.status);
                    const failure = result.failedExpectations[result.failedExpectations.length - 1];

                    reportingClient.testStop({
                        status: perfectoReporting.Constants.results.failed,
                        message: `${failure.message} ${failure.stack}`
                    });

                } else {
                    // on success we report that the test has passed
                    console.log('Test status is: ' + result.status);
                    reportingClient.testStop({
                        status: perfectoReporting.Constants.results.passed
                    });
                }
            },
            suiteDone: (result) => {
                // when the suite is done we print in the console its description and status
                console.log('Suite done: ' + result.description + ' was ' + result.status);
            }
        };
        jasmine.getEnv().addReporter(perfectoReporter);
    },
    onComplete: function() {
        // Output report URL
        return reportingClient.getReportUrl().then(
            function(url) {
                console.log(`Report url - ${url}`);
            }
        );
    },
    //set jasmine options
    jasmineNodeOpts: {
        showColors: true,
        defaultTimeoutInterval: 1000000,
    },
}