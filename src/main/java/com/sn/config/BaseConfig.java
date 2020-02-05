package com.sn.config;

import java.io.File;
import java.util.Properties;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.documentum.fc.common.DfException;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.sn.apt.browser.core.BrowserDriverImpl;
import com.sn.apt.browser.core.CustomChromeDriver;
import com.sn.apt.browser.core.IBrowerDriver;
import com.sn.apt.browser.navigation.IBrowserNavigator;
import com.sn.apt.browser.operations.BrowserOperationsImpl;
import com.sn.apt.browserstack.BrowserStack;
import com.sn.apt.documentum.JobHelper;
import com.sn.apt.excel.read.ReadExcelData;
import com.sn.apt.frame.actions.FrameActions;
import com.sn.apt.keyboard.actions.IKeyboardActions;
import com.sn.apt.keyboard.actions.KeyboardActions;
import com.sn.apt.mouse.actions.IMouseActions;
import com.sn.apt.mouse.actions.MouseActions;
import com.sn.apt.util.AutoPilotUtil;
import com.sn.apt.waits.explicit.ExplicitWait;
import com.sn.apt.waits.explicit.IExplicitWaitHandler;
import com.sn.apt.waits.implicit.ImplicitWait;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author ptn9587
 */
public class BaseConfig {

    static final Logger LOGGER = LogManager.getLogger(BaseConfig.class);

    /**
     * ExtentTest.
     */
    private static ExtentTest extentTest;

    /**
     * ExtentReports.
     */
    private static ExtentReports report;

    private static Properties config;

    /**
     * Selenium wait of implicit type.
     */
    private static ImplicitWait implicitWait;

    private static FrameActions frameactions;

    private static IBrowerDriver browserutil;

    private static BrowserDriverImpl browserDriverImpl;

    private static BrowserOperationsImpl browseroprimpl;

    private static IBrowserNavigator browsernavigator;

    private static ReadExcelData readexceldata;

    protected static String environment;

    private BrowserStack browserStack;

    protected static WebDriver baseDriver;

    private static IKeyboardActions keyboardactions;

    protected static IMouseActions mouseactions;

    private static IExplicitWaitHandler explicitWaits;

    private static JobHelper jobHelper;

    public static final String CONFIG_PROP = "config.properties";

    public static final String USER_DIR = "user.dir";

    public static final String TARGET = "target";

    public static final String EXTENT_REPORT_HTML = "ExtentReportResults.html";

    public static final String REPO_USERNAME_INT = "jfxi";

    public static final String REPO_PASS_INT = "welk0m";

    public static final String REPO_NAME_INT = "jfxibase";

    public static final String REPO_ARGUMENT_INT =
        "-user_name jflux_ftp_ex -docbase_name jfxibase -password welk0m";

    public static final String REPO_USERNAME_ACC = "jfxa";

    public static final String REPO_PASS_ACC = "jfxa08";

    public static final String REPO_NAME_ACC = "jfxabase";

    public static final String REPO_ARGUMENT_ACC =
        "-user_name jfxa -docbase_name jfxabase -password jfxa08 -jobIdentifier rest";

    @SuppressWarnings("squid:S2696")
    @BeforeSuite
    public void beforeSuite() {

        report =
            new ExtentReports(
                System.getProperty(USER_DIR) + File.separator + TARGET + File.separator
                    + EXTENT_REPORT_HTML,
                true);

        config = AutoPilotUtil.getPropertiesFile(CONFIG_PROP);
        frameactions = new FrameActions();
        explicitWaits = new ExplicitWait();
        implicitWait = new ImplicitWait();
        keyboardactions = new KeyboardActions();
        readexceldata = new ReadExcelData();
        mouseactions = new MouseActions();
        jobHelper = new JobHelper();

    }

    @Parameters("env")
    @SuppressWarnings("squid:S2696")
    @BeforeTest
    public void beforeTest(String env) {
        environment = env;
        browserutil = new CustomChromeDriver(config.getProperty("chromedriverVersion"));
        baseDriver = browserutil.getDriver();
        browseroprimpl = new BrowserOperationsImpl(baseDriver);
        if (env.equals("INT")) {
            baseDriver.get(config.getProperty("INTURL"));
        } else if (env.equals("ACC")) {
            baseDriver.get(config.getProperty("ACCURL"));
        }
        browseroprimpl.maximizeBrowser();
    }

    @AfterTest(alwaysRun = true)
    public void afterTest() {
        baseDriver.quit();
    }

    @SuppressWarnings("squid:S2696")
    @BeforeClass(alwaysRun = true)
    public void beforeClass() {
        extentTest = report.startTest(this.getClass().getSimpleName() + " ::");
    }

    @AfterClass(alwaysRun = true)
    public void afterClass() {
        getExtentTest().log(LogStatus.INFO, "Quiting driver in after Class");
        getReport().endTest(getExtentTest());
        getReport().flush();
    }

    @AfterSuite
    public void afterSuite() {
        baseDriver.quit();
        getReport().endTest(getExtentTest());
        getReport().flush();
    }

    public static ReadExcelData getReadExcel() {
        return readexceldata;
    }

    public static void setRead(final ReadExcelData readexceldata) {
        BaseConfig.readexceldata = readexceldata;
    }

    /**
     * @return the test
     */
    public static ExtentTest getExtentTest() {
        return extentTest;
    }

    /**
     * @param extentTest the test to set
     */
    public static void setExtentTest(final ExtentTest extentTest) {
        BaseConfig.extentTest = extentTest;
    }

    /**
     * @return the report
     */
    public static ExtentReports getReport() {
        return report;
    }

    /**
     * @return the config
     */
    public Properties getConfig() {
        return config;
    }

    /**
     * @param config to set
     */
    @SuppressWarnings("squid:S2696")
    public void setConfig(final Properties config) {
        BaseConfig.config = config;
    }

    /**
     * @return IKeyboardActions
     */

    public IKeyboardActions getKeywordactions() {
        return keyboardactions;
    }

    /**
     * @return SeleniumImplicitWait
     */
    public ImplicitWait getSeleniumImplicitWait() {
        return implicitWait;
    }

    /**
     * @return the browserutil
     */
    public static IBrowerDriver getBrowserutil() {
        return browserutil;
    }

    /**
     * @return the browserStack
     */
    public BrowserStack getBrowserStack() {
        return browserStack;
    }

    /**
     * @param browserStack the browserStack to set
     */
    public void setBrowserStack(final BrowserStack browserStack) {
        this.browserStack = browserStack;
    }

    /**
     * @return the seleniumexphicitWaits
     */
    public static IExplicitWaitHandler getSeleniumexplicitWaits() {
        return explicitWaits;
    }

    /**
     * @return the frameactions
     */
    public static FrameActions getFrameactions() {
        return frameactions;
    }

    /**
     * @return the browseroprimpl
     */
    public static BrowserOperationsImpl getBrowseroprimpl() {
        return browseroprimpl;
    }

    /**
     * @param browseroprimpl the browseroprimpl to set
     */
    public static void setBrowseroprimpl(final BrowserOperationsImpl browseroprimpl) {
        BaseConfig.browseroprimpl = browseroprimpl;
    }

    /**
     * @return the browsernavigator
     */
    public static IBrowserNavigator getBrowsernavigator() {
        return browsernavigator;
    }

    /**
     * @param browsernavigator the browsernavigator to set
     */
    public static void setBrowsernavigator(final IBrowserNavigator browsernavigator) {
        BaseConfig.browsernavigator = browsernavigator;
    }

    /**
     * @return the browserDriverImpl
     */
    public static BrowserDriverImpl getBrowserDriverImpl() {
        return browserDriverImpl;
    }

    /**
     * @param browserDriverImpl the browserDriverImpl to set
     */
    public static void setBrowserDriverImpl(final BrowserDriverImpl browserDriverImpl) {
        BaseConfig.browserDriverImpl = browserDriverImpl;
    }

    public boolean isElementPresent(final WebElement element) {
        try {
            if (element.isDisplayed()) {
                LOGGER.info("isElementPresent:: is present");
                return true;
            } else {
                LOGGER.warn("isElementPresent::FAIL - is not present");
                return false;
            }
        } catch (Exception e) {
            throw new NoSuchElementException("ERROR: Element not found", e);
        }
    }

    protected void runFTPExchangeJob(final String Env) throws DfException {
        if (Env.equals("INT")) {
            jobHelper.executeMethod(
                "jspr_ftp_exchange_job",
                REPO_USERNAME_INT,
                REPO_PASS_INT,
                REPO_NAME_INT,
                REPO_ARGUMENT_INT);
        } else {
            jobHelper.executeMethod(
                "jspr_ftp_exchange_job",
                REPO_USERNAME_ACC,
                REPO_PASS_ACC,
                REPO_NAME_ACC,
                REPO_ARGUMENT_ACC);
        }
    }

    protected void runSendToVendorJob(final String Env) throws DfException {
        if (Env.equals("INT")) {
            jobHelper.executeMethod(
                "jspr_sc_send_packages",
                REPO_USERNAME_INT,
                REPO_PASS_INT,
                REPO_NAME_INT,
                REPO_ARGUMENT_INT);
        } else {
            jobHelper.executeMethod(
                "jspr_sc_send_packages",
                REPO_USERNAME_ACC,
                REPO_PASS_ACC,
                REPO_NAME_ACC,
                REPO_ARGUMENT_ACC);

        }
    }

    protected void runGetResultPackageJob(final String Env) throws DfException {
        if (Env.equals("INT")) {
            jobHelper.executeMethod(
                "jspr_sc_get_result_packages",
                REPO_USERNAME_INT,
                REPO_PASS_INT,
                REPO_NAME_INT,
                REPO_ARGUMENT_INT);
        } else {
            jobHelper.executeMethod(
                "jspr_sc_get_result_packages",
                REPO_USERNAME_ACC,
                REPO_PASS_ACC,
                REPO_NAME_ACC,
                REPO_ARGUMENT_ACC);
        }
    }

    protected void runImportFromVendorJob(final String Env) throws DfException {
        if (Env.equals("INT")) {
            jobHelper.executeMethod(
                "jspr_import_from_vendor",
                REPO_USERNAME_INT,
                REPO_PASS_INT,
                REPO_NAME_INT,
                REPO_ARGUMENT_INT);
        } else {
            jobHelper.executeMethod(
                "jspr_import_from_vendor",
                REPO_USERNAME_ACC,
                REPO_PASS_ACC,
                REPO_NAME_ACC,
                REPO_ARGUMENT_ACC);
        }
    }

    protected void runEnrichmentControllerJob(final String env) throws DfException {
        if (env.equals("INT")) {
            jobHelper.executeMethod(
                "jspr_import_from_ec_job",
                REPO_USERNAME_INT,
                REPO_PASS_INT,
                REPO_NAME_INT,
                REPO_ARGUMENT_INT);

        } else {
            jobHelper.executeMethod(
                "jspr_import_from_ec_job",
                REPO_USERNAME_ACC,
                REPO_PASS_ACC,
                REPO_NAME_ACC,
                REPO_ARGUMENT_ACC);
        }

    }

}
