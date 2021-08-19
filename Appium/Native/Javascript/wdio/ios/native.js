describe('Native NodeJS', function() {

    it('iOS Sample', function() {
        browser.reportingClient.stepStart('Enter email');
        var email = $('[name="login_email"]');
        email.waitForDisplayed({ timeout: 30000 });
        email.click()
        email.addValue("test@perfecto.com");
        browser.reportingClient.stepEnd();

        browser.reportingClient.stepStart('Enter password');
        var password = $('[name="login_password"]');
        password.waitForDisplayed({ timeout: 30000 });
        password.click();
        password.addValue("test123");
        browser.reportingClient.stepEnd();

        browser.reportingClient.stepStart('Click login');
        driver.hideKeyboard();
        var login = $('[name="login_login_btn"]');
        login.waitForDisplayed({ timeout: 30000 });
        login.click();
        browser.reportingClient.stepEnd();

        browser.reportingClient.stepStart('Add expense');
        var add = $('[name="list_add_btn"]');
        add.waitForDisplayed({ timeout: 30000 });
        add.click();
        browser.reportingClient.stepEnd();

        browser.reportingClient.stepStart('Select head');
        var head = $('[name="edit_head"]');
        head.waitForDisplayed({ timeout: 30000 });
        head.click();
        var flight = $("//*[@value='- Select -']");
        flight.waitForDisplayed({ timeout: 30000 });
        flight.click();
        flight.addValue("Flight");
        browser.reportingClient.stepEnd();

        browser.reportingClient.stepStart('Enter amount');
        var add = $('[name="edit_amount"]');
        add.waitForDisplayed({ timeout: 30000 });
        add.click();
        add.addValue("100");
        browser.reportingClient.stepEnd();

        browser.reportingClient.stepStart('Save expense');
        var layout = $('[name="add_save_btn"]');
        layout.waitForDisplayed({ timeout: 30000 });
        layout.click();
        browser.reportingClient.stepEnd();

        browser.reportingClient.stepStart("Verify alert");
        expectedText = "Please enter valid category"
        var alert = $('[name="' + expectedText + '"]');
        alert.waitForDisplayed({ timeout: 30000 });
        var result = alert.getText();
        browser.reportingClient.reportiumAssert(expectedText, result == expectedText);

    });
});