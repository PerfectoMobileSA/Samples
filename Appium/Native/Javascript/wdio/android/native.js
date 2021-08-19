describe('Native NodeJS', function() {

    it('Android Sample', function() {
        browser.reportingClient.stepStart('Enter email');
        var email =
            $('android=new UiSelector().resourceId("io.perfecto.expense.tracker:id/login_email")');
        email.waitForDisplayed({ timeout: 30000 });
        email.addValue("test@perfecto.com");
        browser.reportingClient.stepEnd();

        browser.reportingClient.stepStart('Enter password');
        var password =
            $('android=new UiSelector().resourceId("io.perfecto.expense.tracker:id/login_password")');
        password.waitForDisplayed({ timeout: 30000 });
        password.addValue("test123");
        browser.reportingClient.stepEnd();

        browser.reportingClient.stepStart('Click login');
        driver.hideKeyboard();
        var login =
            $('android=new UiSelector().resourceId("io.perfecto.expense.tracker:id/login_login_btn")');
        login.waitForDisplayed({ timeout: 30000 });
        login.click();
        browser.reportingClient.stepEnd();

        browser.reportingClient.stepStart('Add expense')
        var add =
            $('android=new UiSelector().resourceId("io.perfecto.expense.tracker:id/list_add_btn")');
        add.waitForDisplayed({ timeout: 30000 });
        add.click();
        browser.reportingClient.stepEnd();

        browser.reportingClient.stepStart('Select head')
        var head =
            $('android=new UiSelector().resourceId("io.perfecto.expense.tracker:id/input_layout_head")');
        head.waitForDisplayed({ timeout: 30000 });
        head.click();
        var flight =
            $('//*[@text="Flight"]');
        flight.waitForDisplayed({ timeout: 30000 });
        flight.click();
        browser.reportingClient.stepEnd();

        browser.reportingClient.stepStart('Enter amount')
        var add =
            $('android=new UiSelector().resourceId("io.perfecto.expense.tracker:id/add_amount")');
        add.waitForDisplayed({ timeout: 30000 });
        add.addValue("100");
        browser.reportingClient.stepEnd();

        browser.reportingClient.stepStart('Save expense')
        var layout =
            $('android=new UiSelector().resourceId("io.perfecto.expense.tracker:id/layout_buttons")');
        layout.waitForDisplayed({ timeout: 30000 });
        layout.click();
        browser.reportingClient.stepEnd();

        browser.reportingClient.stepStart("Verify alert");
        expectedText = "Select Currency"
        var alert =
            $('//*[@text="' + expectedText + '"]');
        alert.waitForDisplayed({ timeout: 30000 });
        var result = alert.getText();
        browser.reportingClient.reportiumAssert(expectedText, result == expectedText);

    });
});