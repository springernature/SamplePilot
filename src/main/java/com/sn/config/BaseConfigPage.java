package com.sn.config;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;

import com.sn.apt.mouse.actions.IMouseActions;

/**
 * Base Config Page Class.
 */
public class BaseConfigPage extends BaseConfig {

    /**
     * Logger .
     */
    private static final Logger LOGGER = LogManager.getLogger(BaseConfigPage.class);

    /**
     * Mouse Actions.
     * 
     * @author desh17
     */
    private static IMouseActions mouseactions;

    /**
     * String.
     */
    public static final String PROGRESSBAR = "//div[@role='progressbar'][@aria-hidden='false']";

    /**
     * String.
     */
    public static final String LOADINGBLOCK = "//div[@id='loading_block']";

    /**
     * Set File Path in JFlux.
     * 
     * @author desh17
     * @param fileLocation
     *            String
     * @param element
     *            String
     */
    public void setFilePathinJFlux(final WebElement element, final String fileLocation) {
        try {
            final String pwdSysProp = System.getProperty("user.dir");
            final File pwdDir = new File(pwdSysProp);
            final String pwdCanonicalPath = pwdDir.getCanonicalPath();
            final String filePath = pwdCanonicalPath + fileLocation;
            element.sendKeys(filePath);
        } catch (final Exception e) {

            LOGGER.info(e);
        }
    }

    /**
     * Mouse Actions.
     * 
     * @author nap0878
     * @return mouseactions
     */
    public static IMouseActions getMouseactions() {
        return mouseactions;
    }

}
