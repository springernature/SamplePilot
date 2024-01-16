package com.sn.pageobjects;

/**
 * This file is for reference only.
 */

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.Status;
import com.sn.apt.listeners.ExtentTestManager;
import com.sn.config.BaseConfig;
import com.sn.utils.CommonUtils;

/**
 * This class will have all webements and functions from HomePageClass.
 * 
 * @author Pankaj Tarar
 */
public class HomePage {

    private static final Logger LOGGER = LogManager.getLogger(HomePage.class);

    private CommonUtils commonUtils;

    private BaseConfig baseconfig;

    @FindBy(xpath = "//a[contains(@class,'xcpid_mnu_home')]//span[2]")
    private WebElement tabHome;

    @FindBy(xpath = "//a[contains(@class,'xcpid_mnu_production')]")
    private WebElement mnuHomeProduction;

    @FindBy(xpath = "//div[contains(@class,'xcpid_menu6 ')]//a//span[text()='Production Order']")
    private WebElement mnuPO;

    @FindBy(xpath = "//div[contains(@class,'xcpid_menu6 ')]//a//span[text()='Purchase Order']")
    private WebElement mnuPurchaseOrder;

    @FindBy(xpath = "//div[contains(@class,'xcpid_menu6 ')]//a//span[text()='Search PO']")
    private WebElement mnuSearchPO;

    @FindBy(xpath = "//div[contains(@class,'xcpid_menu6 ')]//a//span[text()='Search Quotes']")
    private WebElement mnuSearchQuote;

    @FindBy(xpath = "//input[@placeholder='Minimum 3 characters']")
    private WebElement editboxISBN;

    @FindBy(xpath = "//a[contains(@class,'xcpid_btn_search xcp_button-cls')]")
    private WebElement btnGlobalSearch;

    @FindBy(xpath = "//table//tr//td[2]//a")
    private WebElement lnkComponentName;

    @FindBy(xpath = "(//a[contains(@class,'xcpid_user_button')]//span)[1]")
    private WebElement btnDropDownSignOut;

    @FindBy(xpath = "//a[not(contains(@class,'x-btn-disabled'))]//span[text()='Sign out']")
    private WebElement btnSignOut;

    @FindBy(xpath = "//a[not(contains(@class,'x-btn-disabled'))]//span[text()='Sign In']")
    private WebElement btnLogIn;

    @FindBy(xpath = "//link[@rel='icon']")
    private WebElement icoEdFluxLogo;

    @FindBy(xpath = "//a[@class='x-img xcp-image-widget-wrapper"
            + " xcpid_image1 xcp_image-cls x-box-item x-img-default']/img")
    private WebElement imgEdFluxLogo;

    @FindBy(xpath = "//a[contains(@class,'x-img xcp-image-widget-wrapper"
            + " xcpid_image xcp_image-cls x-box-item x-img-default')]/img")
    private WebElement imgSpringerNatureLogo;

    private String filenameEdFluxLogo = "Flux_Logo.png";

    private String filenameSpringerNatureLogo = "SN_logo.png";

    /**
     * This is the constructor for Homepage class.
     * 
     * @param baseconfig
     *            BaseConfig
     */
    @SuppressWarnings("squid:S3366")
    public HomePage(final BaseConfig baseconfig) {
        this.baseconfig = baseconfig;
        PageFactory.initElements(baseconfig.getBaseDriver(), this);
        commonUtils = new CommonUtils(baseconfig);
        LOGGER.info("Constructor initialize in Generate Production Order class");
    }

    /**
     * @param menuName
     *            String menuName
     * @param menuValue
     *            String menuValue
     * @param logMessage
     *            String loggerStatement if action passess
     * @return HomePage class driver.
     * @throws InterruptedException
     */
    public HomePage selectChildOptionFromMenu(
            final String menuName,
            final String menuValue,
            final String logMessage) {
        baseconfig
                .getExplicitWaits()
                .waitforVisibilityOfElementPresent(baseconfig.getBaseDriver(), icoEdFluxLogo, 60);

        final String xPathMenuName = "//*[@class='x-btn-inner x-btn-inner-xcp-nav-button-small' and text()='"
                + menuName
                + "']";

        final String xPathMenuValue = "//a//span[text()='" + menuValue + "']";

        final WebElement elementMenuName = baseconfig
                .getBaseDriver()
                .findElement(By.xpath(xPathMenuName));

        commonUtils.clickOnElement(elementMenuName);

        final WebElement elementMenuValue = baseconfig
                .getBaseDriver()
                .findElement(By.xpath(xPathMenuValue));

        commonUtils.clickOnElement(elementMenuValue);
        LOGGER
                .info(
                        "Main Menu Name: "
                                + menuName
                                + ", Child Menu Name: "
                                + menuValue
                                + " is selected successfully.");
        ExtentTestManager.getTest().log(Status.PASS, logMessage);

        commonUtils
                .commonLogger(
                        "Main Menu Name: "
                                + menuName
                                + ", Child Menu Name: "
                                + menuValue
                                + " is selected successfully.",
                        Status.PASS,
                        Level.INFO);
        return this;

    }

    /**
     * Methods for Browse Logo tests.
     * 
     * @author Svetlana Kustova
     */

    /**
     * Method to compare two images.
     * 
     * @param img1
     *            BufferedImage
     * @param img2
     *            BufferedImage
     * @return boolean
     */
    public boolean bufferedImagesEqual(final BufferedImage img1, final BufferedImage img2) {
        final boolean isSizeEqual = (img1.getWidth() == img2.getWidth())
                && (img1.getHeight() == img2.getHeight());
        if (!isSizeEqual) return false;
        for (int x = 0; x < img1.getWidth(); x++) {
            for (int y = 0; y < img1.getHeight(); y++) {
                if (img1.getRGB(x, y) != img2.getRGB(x, y)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Method to save image using URL.
     * 
     * @param srcImg
     *            String
     * @param destImg
     *            String
     * @throws IOException
     */
    @SuppressWarnings("squid:S4797")
    public void downloadImage(final String srcImg, final String destImg) throws IOException {

        try (InputStream in = new URL(srcImg).openStream()) {
            Files.copy(in, Paths.get(destImg));
        } catch (final IOException e) {
            commonUtils
                    .commonLogger("Exception ocurred in copying image ", Status.FAIL, Level.ERROR);

            throw e;
        }
        commonUtils.commonLogger("Image is saved in:" + destImg, Status.PASS, Level.INFO);
    }

    /**
     * Method to compare image with standard one.
     * 
     * @param imgElement
     *            WebElement
     * @param standardImgFilename
     *            String
     * @return boolean
     * @throws IOException
     */
    @SuppressWarnings("squid:S4797")
    public boolean verifyImage(
            final WebElement imgElement,
            final String standardImgFilename) throws IOException {
        final String currentDirectory = System.getProperty("user.dir");
        final String tmpDirectory = currentDirectory + "\\Tmp_Img\\";
        final String standardFilename = currentDirectory + "\\TestData\\" + standardImgFilename;
        final String tmpFilename = tmpDirectory + standardImgFilename;
        final String srcAttributeImg = imgElement.getAttribute("src");
        boolean isEqualImg = false;

        try {
            final Path path = Paths.get(tmpDirectory);
            if (!path.toFile().exists()) {
                Files.createDirectory(path);
                commonUtils
                        .commonLogger(
                                "Directory created: " + tmpDirectory,
                                Status.PASS,
                                Level.INFO);
            } else {
                commonUtils
                        .commonLogger(
                                "Directory already exists : " + tmpDirectory,
                                Status.PASS,
                                Level.INFO);

            }
            commonUtils
                    .commonLogger(
                            "Source of standard image: " + standardFilename,
                            Status.PASS,
                            Level.INFO);

            final File imgFileStandard = new File(standardFilename);
            final BufferedImage imgBuffStandard = ImageIO.read(imgFileStandard);
            LOGGER.info("File object for standard image is created");

            LOGGER.info("Source of image from WebElement: " + tmpFilename);
            downloadImage(srcAttributeImg, tmpFilename);
            final File imgFileWebElement = new File(tmpFilename);
            final BufferedImage imgBuffWebElement = ImageIO.read(imgFileWebElement);
            LOGGER.info("File object for image from Web Element is created");

            if (bufferedImagesEqual(imgBuffWebElement, imgBuffStandard)) {
                commonUtils.commonLogger("Images are equal", Status.PASS, Level.INFO);

                isEqualImg = true;
            } else {
                commonUtils
                        .commonLogger(
                                "Image is not equal to standard " + standardImgFilename,
                                Status.FAIL,
                                Level.ERROR);

            }

            Files.delete(Paths.get(tmpFilename));
            LOGGER.info("Temporary image is deleted");

        } catch (final IOException e) {
            commonUtils.commonLogger("Unable to compare images", Status.FAIL, Level.ERROR);
            throw e;
        }
        return isEqualImg;
    }

    /**
     * Method to check the EdFlux logo.
     * 
     * @return driver of ProductHierarchyPage class
     * @throws IOException
     */
    public HomePage verifyEdfluxLogo() throws IOException {
        boolean isCorrectLogo = false;
        try {
            baseconfig
                    .getExplicitWaits()
                    .waitforVisibilityOfElementPresent(
                            baseconfig.getBaseDriver(),
                            imgEdFluxLogo,
                            30);
            commonUtils.verifyElement(imgEdFluxLogo);
            LOGGER.info("Edflux logo is present");
            isCorrectLogo = verifyImage(imgEdFluxLogo, filenameEdFluxLogo);
            LOGGER.info("EdFlux logo has compared with the standard. ");
            if (isCorrectLogo) {
                ExtentTestManager.getTest().log(Status.PASS, "Edflux logo is correct");
            } else {
                throw new IOException();
            }
        } catch (final IOException e) {
            commonUtils.commonLogger("Edflux logo is incorrect", Status.FAIL, Level.ERROR);
            throw e;
        }
        return this;
    }

    /**
     * Method to check the SpringerNature logo.
     * 
     * @return driver of ProductHierarchyPage class
     * @throws IOException
     */
    public HomePage verifySpringerNatureLogo() throws IOException {
        boolean isCorrectLogo = false;
        try {
            baseconfig
                    .getExplicitWaits()
                    .waitforVisibilityOfElementPresent(
                            baseconfig.getBaseDriver(),
                            imgSpringerNatureLogo,
                            30);
            commonUtils.verifyElement(imgSpringerNatureLogo);
            LOGGER.info("SpringerNature logo is present");
            isCorrectLogo = verifyImage(imgSpringerNatureLogo, filenameSpringerNatureLogo);
            LOGGER.info("SpringerNature logo has compared with the standard. ");
            if (isCorrectLogo) {
                ExtentTestManager.getTest().log(Status.PASS, "SpringerNature logo is correct");
            } else {
                throw new IOException();
            }
        } catch (final IOException e) {
            commonUtils.commonLogger("SpringerNature logo is incorrect", Status.FAIL, Level.ERROR);
            throw e;
        }
        return this;
    }

}
