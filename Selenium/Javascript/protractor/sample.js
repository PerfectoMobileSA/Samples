const { browser } = require("protractor");

describe('Selenium NodeJS', () => {
    let EC = protractor.ExpectedConditions;
    let timeout = 30000;
    var search = "perfectomobile";
    browser.waitForAngular();
    it('Sample', function () {
        browser.reportingClient.stepStart('Navigate to Google');
        browser.driver.get('https://www.google.com');
        browser.reportingClient.stepEnd();

        browser.reportingClient.stepStart('Search for ' + search);
        var q = element(by.name('q'));
        browser.wait(EC.elementToBeClickable(q), timeout);
        q.sendKeys(search);
        browser.reportingClient.stepEnd();

        browser.reportingClient.stepStart('Select ' + search);
        browser.wait(EC.elementToBeClickable(q), timeout);
        browser.executeScript("arguments[0].click()", q);
        browser.sleep(3000);
        browser.actions().sendKeys(protractor.Key.ENTER).perform();
        browser.reportingClient.stepEnd();

        browser.reportingClient.stepStart('Navigate to Perfecto');
        var href = element(by.xpath('(//*[contains(@href,"https://www.perfecto.io/")])[1]'));
        browser.wait(EC.urlContains(search), timeout);
        browser.wait(EC.elementToBeClickable(href), timeout);
        browser.executeScript("arguments[0].scrollIntoView()", href);
        browser.wait(EC.elementToBeClickable(href), timeout);
        browser.executeScript("arguments[0].click()", href);
        browser.reportingClient.stepEnd();

        browser.reportingClient.stepStart("Press back");
        browser.getCapabilities().then(function (c) {
            console.log(c.get('platformName'));
            if (c.get('platformName') == "Windows") {
                browser.navigate().back();
            } else {
                var params = {
                    "target": "back"
                }
                browser.executeScript('mobile:browser:navigate', params);
            }
        });
        browser.reportingClient.stepEnd();

        browser.reportingClient.stepStart('Verify Title');
        var expectedText = "perfectomobile - Google Search";
        browser.getTitle().then(function (result) {
            browser.reportingClient.reportiumAssert(expectedText.toLowerCase(), result.toLowerCase() == expectedText.toLowerCase());
        });
        browser.reportingClient.stepEnd();
    });

    //teardown on finish all
    afterAll(() => {
        browser.close();
    });
});
