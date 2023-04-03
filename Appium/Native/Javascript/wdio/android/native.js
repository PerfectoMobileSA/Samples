describe('Native NodeJS', function () {

    it('Android Sample', async () => {
        await browser.reportingClient.stepStart('Enter email');
        var email =
            $('android=new UiSelector().resourceId("io.perfecto.expense.tracker:id/login_email")');
        await email.waitForDisplayed({ timeout: 30000 });
        await email.addValue("test@perfecto.com");
        await browser.reportingClient.stepEnd();

        await browser.reportingClient.stepStart('Enter password');
        var password =
            $('android=new UiSelector().resourceId("io.perfecto.expense.tracker:id/login_password")');
        await password.waitForDisplayed({ timeout: 30000 });
        await password.addValue("test123");
        await browser.reportingClient.stepEnd();

        await browser.reportingClient.stepStart('Click login');
        await driver.hideKeyboard();
        var login =
            $('android=new UiSelector().resourceId("io.perfecto.expense.tracker:id/login_login_btn")');
        await login.waitForDisplayed({ timeout: 30000 });
        await login.click();
        await browser.reportingClient.stepEnd();

        await browser.reportingClient.stepStart('Add expense')
        var add =
            $('android=new UiSelector().resourceId("io.perfecto.expense.tracker:id/list_add_btn")');
        await add.waitForDisplayed({ timeout: 30000 });
        await add.click();
        await browser.reportingClient.stepEnd();

        await browser.reportingClient.stepStart('Select head')
        var head =
            $('android=new UiSelector().resourceId("io.perfecto.expense.tracker:id/input_layout_head")');
        await head.waitForDisplayed({ timeout: 30000 });
        await head.click();
        var flight =
            $('//*[@text="Flight"]');
        await flight.waitForDisplayed({ timeout: 30000 });
        await flight.click();
        await browser.reportingClient.stepEnd();

        await browser.reportingClient.stepStart('Enter amount')
        var add =
            $('android=new UiSelector().resourceId("io.perfecto.expense.tracker:id/add_amount")');
        await add.waitForDisplayed({ timeout: 30000 });
        await add.addValue("100");
        await browser.reportingClient.stepEnd();

        await browser.reportingClient.stepStart('Save expense')
        var layout =
            $('android=new UiSelector().resourceId("io.perfecto.expense.tracker:id/layout_buttons")');
        await layout.waitForDisplayed({ timeout: 30000 });
        await layout.click();
        await browser.reportingClient.stepEnd();

        await browser.reportingClient.stepStart("Verify alert");
        expectedText = "Select Currency"
        var alert =
            $('//*[@text="' + expectedText + '"]');
        await alert.waitForDisplayed({ timeout: 30000 });
        var result = await alert.getText();
        await browser.reportingClient.reportiumAssert(expectedText, result == expectedText);

    });
});