package com.sn.config;

import java.util.Properties;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.IHookCallBack;
import org.testng.IHookable;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;

import com.sn.apt.browser.core.BrowserDriverImpl;
import com.sn.apt.browser.core.CustomThreadLocalDriverWithoutDriverVersion;
import com.sn.apt.browser.core.CustomThreadLocalDriverWithoutDriverVersion.DriverType;
import com.sn.apt.browser.core.IBrowerDriver;
import com.sn.apt.browser.navigation.IBrowserNavigator;
import com.sn.apt.browser.operations.BrowserOperationsImpl;
import com.sn.apt.browserstack.BrowserStack;
import com.sn.apt.documentum.JobHelper;
import com.sn.apt.documentum.QueryHelper;
import com.sn.apt.email.util.SendEmail;
import com.sn.apt.frame.actions.FrameActions;
import com.sn.apt.keyboard.actions.IKeyboardActions;
import com.sn.apt.keyboard.actions.KeyboardActions;
import com.sn.apt.listeners.WebDriverSupplier;
import com.sn.apt.mouse.actions.IMouseActions;
import com.sn.apt.mouse.actions.MouseActions;
import com.sn.apt.util.AutoPilotUtil;
import com.sn.apt.waits.explicit.ExplicitWait;
import com.sn.apt.waits.implicit.ImplicitWait;

/**
 * @author Pankaj Tarar
 */
@SuppressWarnings("squid:S1200")
public class BaseConfig implements IHookable, WebDriverSupplier {

    private static final Logger LOGGER = LogManager.getLogger(BaseConfig.class);

    /**
     * driver.
     */
    private static WebDriver baseDriver;

    private static Properties config;

    /**
     * seleniumWaits.
     */
    private static ExplicitWait explicitWaits;

    /**
     * Selenium wait of implicit type.
     */
    private static ImplicitWait implicitWait;

    private static FrameActions frameActions;

    private static SendEmail sendMail;

    private static IBrowerDriver browserUtil;

    private static BrowserDriverImpl browserDriverImpl;

    private static BrowserOperationsImpl browseroprimpl;

    private static IBrowserNavigator browsernavigator;

    private static IKeyboardActions keyboardActions;

    private static IMouseActions imouseActions;

    private static BrowserStack browserStack;

    private static JobHelper jobHelper;

    private static QueryHelper queryHelper;

    private static AutoPilotUtil autopilotUtils;

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
     * It will execute beforeSuite.
     */
    @SuppressWarnings("squid:S2696")
    @BeforeSuite
    public void beforeSuite() {

        config = AutoPilotUtil.getPropertiesFile(CONFIG_PROP);
        frameActions = new FrameActions();
        explicitWaits = new ExplicitWait();
        implicitWait = new ImplicitWait();
        keyboardActions = new KeyboardActions();
        imouseActions = new MouseActions();
        browseroprimpl = new BrowserOperationsImpl(getBaseDriver());
        jobHelper = new JobHelper();
        queryHelper = new QueryHelper();

        LOGGER.info("Before suite set up completed");

    }

    /**
     * It will execute beforeclass.
     */
    @SuppressWarnings("squid:S2696")
    @BeforeClass(alwaysRun = true)
    @Parameters({ "env", "executionEnv" })
    public void beforeClass(final String env, final String executionEnv) {
        executionEnvApp = executionEnv;
        if ("local".equals(env)) {
            configureLocalBrowser(executionEnv);
        } else {
            // TODO for other environments
        }

    }

    /**
     * @return the executionEnvApp
     */
    public static String getExecutionEnvApp() {
        return executionEnvApp;
    }

    /**
     * This method is to configure execution in local browser.
     * 
     * @param executionEnv
     *            String
     */
    @SuppressWarnings("squid:S2696")
    @Parameters("executionEnv")
    private void configureLocalBrowser(final String executionEnv) {
        browserUtil = new CustomThreadLocalDriverWithoutDriverVersion(DriverType.CHROME);
        baseDriver = browserUtil.getThreadLocalDriver();
        browseroprimpl = new BrowserOperationsImpl(baseDriver);
        if (executionEnv.equals("local")) {
            baseDriver.get(config.getProperty("local"));
        } else if (executionEnv.equals("test")) {
            baseDriver.get(config.getProperty("test"));
        }
        getBaseDriver().manage().window().maximize();
    }

    /**
     * It will execute afterclass.
     */
    @AfterClass(alwaysRun = false)
    public void afterClass() {
        LOGGER.info("Quiting driver in after class");
        getBaseDriver().quit();
    }

    /**
     * It will execute after Suite.
     */
    @AfterSuite
    public void afterSuite() {
        LOGGER.info("After suite executed");

    }

    /**
     * @return the baseDriver
     */
    public WebDriver getBaseDriver() {
        return baseDriver;
    }

    /**
     * @param baseDriver
     *            the baseDriver to set
     */
    @SuppressWarnings("squid:S2696")
    public void setBaseDriver(final WebDriver baseDriver) {
        this.baseDriver = baseDriver;
    }

    /**
     * @return the config
     */
    public Properties getConfig() {
        return config;
    }

    /**
     * @return the explicitWaits
     */
    public ExplicitWait getExplicitWaits() {
        return explicitWaits;
    }

    /**
     * @return the implicitWait
     */
    public ImplicitWait getImplicitWait() {
        return implicitWait;
    }

    /**
     * @return the frameactions
     */
    public FrameActions getFrameactions() {
        return frameActions;
    }

    /**
     * @return the sendmail
     */
    public SendEmail getSendmail() {
        return sendMail;
    }

    /**
     * @return the browserutil
     */
    public IBrowerDriver getBrowserutil() {
        return browserUtil;
    }

    /**
     * @return the browserDriverImpl.
     */
    public BrowserDriverImpl getBrowserDriverImpl() {
        return browserDriverImpl;
    }

    /**
     * @return the browseroprimpl
     */
    public static BrowserOperationsImpl getBrowseroprimpl() {
        return browseroprimpl;
    }

    /**
     * @return the browsernavigator
     */
    public IBrowserNavigator getBrowsernavigator() {
        return browsernavigator;
    }

    /**
     * @return the keyboardactions
     */
    public IKeyboardActions getKeyboardactions() {
        return keyboardActions;
    }

    /**
     * @return the imouseactions
     */
    public IMouseActions getImouseactions() {
        return imouseActions;
    }

    /**
     * @return the browserStack
     */
    public BrowserStack getBrowserStack() {
        return browserStack;
    }

    /**
     * @return the jobhelper
     */
    public JobHelper getJobhelper() {
        return jobHelper;
    }

    /**
     * @return queryHelper
     */
    public QueryHelper getQueryhelper() {
        return queryHelper;
    }

    /**
     * @return the autopilotUtils
     */
    public AutoPilotUtil getAutopilotUtils() {
        return autopilotUtils;
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
     * @return softAssert
     */
    public static SoftAssert getSoftAssert() {
        return getSoftAssert(Reporter.getCurrentTestResult());
    }

    /**
     * @param result
     *            ITestResult
     * @return (SoftAssert) object
     */
    private static SoftAssert getSoftAssert(final ITestResult result) {
        final Object object = result.getAttribute(SOFT_ASSERT);
        if (object instanceof SoftAssert) {
            return (SoftAssert) object;
        }
        throw new IllegalStateException("Could not find a soft assertion object");
    }

}
