package com.sn.config;

import java.util.Properties;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.openqa.selenium.WebDriver;
import org.testng.IHookCallBack;
import org.testng.IHookable;
import org.testng.ITestResult;
import org.testng.asserts.SoftAssert;

import com.sn.apt.listeners.WebDriverSupplier;

/**
 * @author Pankaj Tarar
 */
@SuppressWarnings("squid:S1200")
public class BaseConfig implements IHookable, WebDriverSupplier {

    private static Properties config;

    private static final String SOFT_ASSERT = "softAssert";

    /**
     * created variable for config properties.
     */
    public static final String CONFIG_PROP = "config.properties";

    /**
     * created variable for current user directory.
     */

    public static final String USER_DIR = "user.dir";

    /**
     * created variable for target directory.
     */
    public static final String TARGET = "target";

    private static String executionEnvApp;

    /**
     * @return the executionEnvApp
     */
    public static String getExecutionEnvApp() {
        return executionEnvApp;
    }

    /**
     * @return the config
     */
    public Properties getConfig() {
        return config;
    }

    @Override
    public void run(final IHookCallBack callBack, final ITestResult testResult) {
        SoftAssert softAssert = new SoftAssert();
        testResult.setAttribute(SOFT_ASSERT, softAssert);
        callBack.runTestMethod(testResult);
        softAssert = getSoftAssert(testResult);
        if (!testResult.isSuccess()) {
            Throwable throwable = testResult.getThrowable();
            if (null != throwable) {
                if (null != throwable.getCause()) {
                    throwable = throwable.getCause();
                }
                softAssert.assertNull(throwable, ExceptionUtils.getStackTrace(throwable));
            }
        }
        softAssert.assertAll();
    }

    /**
     * Returns SoftAssert.
     * 
     * @param result
     *            ITestResult
     * @return (SoftAssert) object
     * @throws IllegalStateException
     */
    private static SoftAssert getSoftAssert(final ITestResult result) {
        final Object object = result.getAttribute(SOFT_ASSERT);
        if (object instanceof SoftAssert) {
            return (SoftAssert) object;
        }
        throw new IllegalStateException("Could not find a soft assertion object");
    }

    @Override
    public WebDriver getBaseDriver() {
        // TODO Auto-generated method stub
        return null;
    }

}
