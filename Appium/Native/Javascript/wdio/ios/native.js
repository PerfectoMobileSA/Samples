describe('Native NodeJS', function() {

    it('iOS Sample', async () => {
        await browser.reportingClient.stepStart('Enter email');
        var email = $('[name="login_email"]');
        await email.waitForDisplayed({ timeout: 30000 });
        await email.click()
        await email.addValue("test@perfecto.com");
        await browser.reportingClient.stepEnd();

        await browser.reportingClient.stepStart('Enter password');
        var password = $('[name="login_password"]');
        await password.waitForDisplayed({ timeout: 30000 });
        await password.click();
        await password.addValue("test123");
        await browser.reportingClient.stepEnd();

        await browser.reportingClient.stepStart('Click login');
        await driver.hideKeyboard();
        var login = $('[name="login_login_btn"]');
        await login.waitForDisplayed({ timeout: 30000 });
        await login.click();
        await browser.reportingClient.stepEnd();

        await browser.reportingClient.stepStart('Add expense');
        var add = $('[name="list_add_btn"]');
        await add.waitForDisplayed({ timeout: 30000 });
        await add.click();
        await browser.reportingClient.stepEnd();

        await browser.reportingClient.stepStart('Select head');
        var head = $('[name="edit_head"]');
        await head.waitForDisplayed({ timeout: 30000 });
        await head.click();
        var flight = $("//*[@value='- Select -']");
        await flight.waitForDisplayed({ timeout: 30000 });
        await flight.click();
        await flight.addValue("Flight");
        await browser.reportingClient.stepEnd();

        await browser.reportingClient.stepStart('Enter amount');
        var add = $('[name="edit_amount"]');
        await add.waitForDisplayed({ timeout: 30000 });
        await add.click();
        await add.addValue("100");
        await browser.reportingClient.stepEnd();

        await browser.reportingClient.stepStart('Save expense');
        var layout = $('[name="add_save_btn"]');
        await layout.waitForDisplayed({ timeout: 30000 });
        await layout.click();
        await browser.reportingClient.stepEnd();

        await browser.reportingClient.stepStart("Verify alert");
        expectedText = "Please enter valid category"
        var alert = $('[name="' + expectedText + '"]');
        await alert.waitForDisplayed({ timeout: 30000 });
        var result = await alert.getText();
        await browser.reportingClient.reportiumAssert(expectedText, result == expectedText);

    });
});