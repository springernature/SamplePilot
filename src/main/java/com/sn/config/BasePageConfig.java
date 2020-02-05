package com.sn.config;

import java.io.File;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class BasePageConfig extends BaseConfig {

    protected static WebDriver baseDriver;

    @SuppressWarnings("squid:S3010")
    public BasePageConfig(final WebDriver driver) {
        baseDriver = driver;
        PageFactory.initElements(baseDriver, this);
    }

    private static final Logger LOGGER = LogManager.getLogger(BasePageConfig.class);

    public void setFilePath(final WebElement element, final String fileLocation) {
        try {
            final String pwdSysProp = System.getProperty("user.dir");
            final File pwdDir = new File(pwdSysProp);
            final String pwdCanonicalPath = pwdDir.getCanonicalPath();
            final String filePath = pwdCanonicalPath + fileLocation;
            element.sendKeys(filePath);
            LOGGER.info("FilePathSet%s", filePath);
        } catch (final Exception e) {
            throw new NoSuchElementException("TypeText::" + e);
        }
    }

}
