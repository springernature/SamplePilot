package com.sn.pageobjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.Status;
import com.sn.config.BaseConfig;
import com.sn.listeners.ExtentTestManager;
import com.sn.utils.CommonUtils;

/**
 * This class is created to have all page objects and methods from the login page.
 * 
 * @author Pankaj Tarar
 */

public class LoginPage {

    private static final Logger LOGGER = LogManager.getLogger(LoginPage.class);

    private BaseConfig baseConfig;

    private CommonUtils commonUtils;

    @FindBy(id = "j_username-inputEl")
    private WebElement txtbxUserName;

    @FindBy(id = "j_password-inputEl")
    private WebElement txtbxPassword;

    @FindBy(xpath = "//a[not(contains(@class,'x-btn-disabled'))]//span[text()='Sign In']")
    private WebElement btnLogIn;

    /**
     * This is the constructor for LoginPage class.
     * 
     * @param baseConfig BaseConfig
     */
    @SuppressWarnings("squid:S3366")
    public LoginPage(final BaseConfig baseConfig) {

        this.baseConfig = baseConfig;
        PageFactory.initElements(baseConfig.getBaseDriver(), this);
        this.commonUtils = new CommonUtils(baseConfig);
        LOGGER.info("Constructor 1 initialize in Login Page class.");
    }

    /**
     * @param baseConfig BaseConfig
     * @param userKey String
     * @param passKey String
     */
    public LoginPage(final BaseConfig baseConfig, final String userKey, final String passKey) {

        this(baseConfig);
        LOGGER.info(
            "Before constructor 2 " + this.baseConfig.getConfig().getProperty(
                passKey + "_" + BaseConfig.getExecutionEnvApp().toLowerCase()));
        login(
            this.baseConfig.getConfig().getProperty(
                userKey + "_" + BaseConfig.getExecutionEnvApp().toLowerCase()),
            this.baseConfig.getConfig().getProperty(
                passKey + "_" + BaseConfig.getExecutionEnvApp().toLowerCase()));
        LOGGER.info("Constructor 2 initialize in Login Page class.");
    }

    /**
     * This action sets the login username value.
     * 
     * @param user String
     * @return driver for Loginpage Class
     * @throws Exception
     */
    private LoginPage setLoginUserName(final String user) {

        commonUtils.setText(
            txtbxUserName,
            user,
            "Username:: " + user + " :: has been set successfully.");

        return this;
    }

    /**
     * This action sets the login password value.
     * 
     * @param pwd String
     * @return driver for Loginpage Class
     * @throws Exception
     */
    private LoginPage setLoginPassword(final String pwd) {
        commonUtils.setText(
            txtbxPassword,
            pwd,
            "Password:: " + pwd + " :: has been set successfully.");

        return this;
    }

    /**
     * This action clicks on the login sign in button.
     * 
     * @return driver for Loginpage Class
     * @throws Exception
     */
    private LoginPage clickLoginSignInButtob() {

        commonUtils.clickOnButton(btnLogIn);
        return this;
    }

    /**
     * This action is to login user into the application.
     * 
     * @param username String
     * @param password String
     * @return driver for Loginpage Class
     */
    public LoginPage login(final String username, final String password) {
        try {
            setLoginUserName(username);
            setLoginPassword(password);
            clickLoginSignInButtob();
            LOGGER.info("Login is Successful.");
        } catch (final Exception e) {
            LOGGER.error("Error occurred while Login process.");
            ExtentTestManager.getTest().log(
                Status.FAIL,
                "Error occurred while Login process." + e.getMessage());
            throw e;
        }

        return this;

    }
}
