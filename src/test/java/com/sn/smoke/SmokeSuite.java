package com.sn.smoke;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.sn.config.BaseConfig;
import com.sn.pageobjects.HomePage;
import com.sn.pageobjects.LoginPage;

public class SmokeSuite extends BaseConfig {

    @SuppressWarnings("unused")
    private LoginPage loginobj;

    @SuppressWarnings("unused")
    private HomePage homeobj;

    /**
     * @BeforeClass - contains tasks that needs to be performed before SmokeSuite class execution
     */
    @BeforeClass
    public void beforeClass() {
        homeobj = new HomePage(this);

    }

    /**
     * Test to verify application login functionality.
     * 
     * @throws InterruptedException
     */
    @Test(priority = 0, testName = "Login to the application", groups = { "login" })
    public void verifyApplicationLogin() throws InterruptedException {
        loginobj = new LoginPage(this, "user_da", "pass_da");
    }

}
