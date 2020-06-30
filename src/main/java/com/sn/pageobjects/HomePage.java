package com.sn.pageobjects;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.Status;
import com.sn.config.BaseConfig;
import com.sn.listeners.ExtentTestManager;
import com.sn.utils.CommonUtils;

/**
 * This class will have all webements and functions from HomePageClass.
 * 
 * @author Pankaj Tarar
 */
public class HomePage {

    private static final Logger LOGGER = LogManager.getLogger(HomePage.class);

    private CommonUtils commonutils;

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

    @FindBy(
            xpath = "//a[@class='x-img xcp-image-widget-wrapper"
                + " xcpid_image1 xcp_image-cls x-box-item x-img-default']/img")
    private WebElement imgEdFluxLogo;

    @FindBy(
            xpath = "//a[contains(@class,'x-img xcp-image-widget-wrapper"
                + " xcpid_image xcp_image-cls x-box-item x-img-default')]/img")
    private WebElement imgSpringerNatureLogo;

    private String filenameEdFluxLogo = "Flux_Logo.png";

    private String filenameSpringerNatureLogo = "SN_logo.png";

    /**
     * This is the constructor for Homepage class.
     * 
     * @param baseconfig BaseConfig
     */
    @SuppressWarnings("squid:S3366")
    public HomePage(final BaseConfig baseconfig) {
        this.baseconfig = baseconfig;
        PageFactory.initElements(baseconfig.getBaseDriver(), this);
        commonutils = new CommonUtils(baseconfig);
        LOGGER.info("Constructor initialize in Generate Production Order class");
    }

    /**
     * This opens the Generate Prod Order window action flow.
     * 
     * @return driver of HomePage class
     */
    public HomePage openGeneratePOWindow() {
        try {
            baseconfig.getExplicitWaits().waitforVisibilityOfElementPresent(
                baseconfig.getBaseDriver(),
                mnuHomeProduction,
                30);
            baseconfig.getImouseactions().mouseHover(baseconfig.getBaseDriver(), mnuHomeProduction);
            commonutils.clickOnElement(mnuPO);
            LOGGER.info("Popup is open now");
            ExtentTestManager.getTest().log(Status.PASS, "Generate PO Popup is open now");
        } catch (final Exception e) {
            LOGGER.error(e);
            ExtentTestManager.getTest().log(Status.FAIL, "Generate PO window did not open");
        }

        return this;
    }

    /**
     * This method open the searchPO page.
     * 
     * @param mnuSearchOption WebElement
     * @return driver of HomePage class
     */
    public HomePage openSearchPage(final WebElement mnuSearchOption) {

        try {
            baseconfig.getExplicitWaits().waitforVisibilityOfElementPresent(
                baseconfig.getBaseDriver(),
                mnuHomeProduction,
                30);
            baseconfig.getImouseactions().mouseHover(baseconfig.getBaseDriver(), mnuHomeProduction);
            commonutils.clickOnElement(mnuSearchOption);
            LOGGER.info("Search PO Page is open now");
            ExtentTestManager.getTest().log(Status.PASS, "Search PO Page is open now");
        } catch (final Exception e) {
            LOGGER.error(e);
            ExtentTestManager.getTest().log(
                Status.FAIL,
                "Search PO Page did not open :: " + e.getMessage());
        }

        return this;

    }

    /**
     * This method opens the purchase order ation flow.
     * 
     * @return driver HomePage class.
     */
    public HomePage openPurchaseOrder() {
        try {

            baseconfig.getExplicitWaits().waitforVisibilityOfElementPresent(
                baseconfig.getBaseDriver(),
                mnuHomeProduction,
                10);
            baseconfig.getImouseactions().mouseHover(baseconfig.getBaseDriver(), mnuHomeProduction);
            baseconfig.getExplicitWaits().waitforElementToBeClickable(
                baseconfig.getBaseDriver(),
                mnuPurchaseOrder,
                10);
            baseconfig.getKeyboardactions().clickOnElement(
                baseconfig.getBaseDriver(),
                mnuPurchaseOrder);
            LOGGER.info("Purchase order is open now.");
            ExtentTestManager.getTest().log(
                Status.PASS,
                "Generate Purchase order window is open now.");
        } catch (final Exception e) {
            LOGGER.error(e);
            ExtentTestManager.getTest().log(
                Status.FAIL,
                "Generate purchase order window did not open.");
        }

        return this;
    }

    /**
     * Open Search PO dashboard.
     * 
     * @return driver of HomePage class
     */
    public HomePage openSearchPOPage() {

        try {
            openSearchPage(mnuSearchPO);
        } catch (final Exception e) {
            LOGGER.error(e);
            ExtentTestManager.getTest().log(Status.FAIL, "Search PO Page did not open");
        }

        return this;

    }

    /**
     * Open Search Quote dashboard.
     * 
     * @return driver of HomePage class
     */
    public HomePage openSearchQuotesPage() {

        try {
            openSearchPage(mnuSearchQuote);
        } catch (final Exception e) {
            LOGGER.error(e);
            ExtentTestManager.getTest().log(
                Status.FAIL,
                "Search Quote Page did not open " + e.getMessage());
        }

        return this;

    }

    /**
     * This function is used for global search and open the component.
     * 
     * @param compISBN String
     * @return driver of HomePage class
     */

    public HomePage searchAndOpenComponentInNewTab(final String compISBN) {
        try {
            final String wHandle = BaseConfig.getBrowseroprimpl().storeWindowHandle();
            Thread.sleep(10000);
            commonutils.setText(
                editboxISBN,
                compISBN,
                "Successfully enter the component in the global search:: " + compISBN);

            commonutils.clickOnButton(btnGlobalSearch);
            Thread.sleep(2000);
            commonutils.clickOnElement(lnkComponentName);
            BaseConfig.getBrowseroprimpl().switchToChildWindow(wHandle);
            Thread.sleep(3000);
            baseconfig.getExplicitWaits().waitforInVisibilityOfElementLocated(
                baseconfig.getBaseDriver(),
                By.xpath("//img[@class='edfluximagespin']"),
                15);
            ExtentTestManager.getTest().log(
                Status.PASS,
                "Successfully opened the search component in New Tab.");
        } catch (final Exception e) {
            LOGGER.error(e);
            ExtentTestManager.getTest().log(
                Status.FAIL,
                "Failed to open the component through Global Search");
        }

        return this;
    }

    /**
     * This function is used for moving back to Global Search Page.
     * 
     * @return driver of HomePage class
     */
    public HomePage switchToGlobalSearch() {
        try {
            final String wHandle = BaseConfig.getBrowseroprimpl().storeWindowHandle();
            baseconfig.getBaseDriver().close();
            BaseConfig.getBrowseroprimpl().switchToChildWindow(wHandle);
            Thread.sleep(3000);
        } catch (final Exception e) {
            LOGGER.error(e);
            ExtentTestManager.getTest().log(Status.FAIL, "Failed to open the Global Search");

        }
        return this;
    }

    /**
     * To sign out from the application.
     * 
     * @return driver of HomePage class
     */
    public HomePage signOut() {
        baseconfig.getExplicitWaits().waitforVisibilityOfElementPresent(
            baseconfig.getBaseDriver(),
            btnDropDownSignOut,
            10);
        baseconfig.getKeyboardactions().clickOnElement(
            baseconfig.getBaseDriver(),
            btnDropDownSignOut);
        baseconfig.getExplicitWaits().waitforVisibilityOfElementPresent(
            baseconfig.getBaseDriver(),
            btnSignOut,
            10);
        baseconfig.getKeyboardactions().clickOnElement(baseconfig.getBaseDriver(), btnSignOut);
        baseconfig.getExplicitWaits().waitforVisibilityOfElementPresent(
            baseconfig.getBaseDriver(),
            btnLogIn,
            20);
        return this;
    }

    /**
     * To go to Home Tab on homepage.
     * 
     * @return current class driver.
     */
    public HomePage clickOnHomeTab() {
        commonutils.clickOnElement(tabHome);
        return this;
    }

    /**
     * @param menuName String menuName
     * @param menuValue String menuValue
     * @param logMessage String loggerStatement if action passess
     * @return HomePage class driver.
     * @throws InterruptedException
     */
    public HomePage selectChildOptionFromMenu(
            final String menuName,
            final String menuValue,
            final String logMessage) {
        baseconfig.getExplicitWaits().waitforVisibilityOfElementPresent(
            baseconfig.getBaseDriver(),
            icoEdFluxLogo,
            60);

        final String xPathMenuName =
            "//*[@class='x-btn-inner x-btn-inner-xcp-nav-button-small' and text()='" + menuName
                + "']";

        final String xPathMenuValue = "//a//span[text()='" + menuValue + "']";

        final WebElement elementMenuName =
            baseconfig.getBaseDriver().findElement(By.xpath(xPathMenuName));

        commonutils.clickOnElement(elementMenuName);

        final WebElement elementMenuValue =
            baseconfig.getBaseDriver().findElement(By.xpath(xPathMenuValue));

        commonutils.clickOnElement(elementMenuValue);
        LOGGER.info(
            "Main Menu Name: " + menuName + ", Child Menu Name: " + menuValue
                + " is selected successfully.");
        ExtentTestManager.getTest().log(Status.PASS, logMessage);
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
     * @param img1 BufferedImage
     * @param img2 BufferedImage
     * @return boolean
     */
    public boolean bufferedImagesEqual(final BufferedImage img1, final BufferedImage img2) {
        final boolean isSizeEqual =
            (img1.getWidth() == img2.getWidth()) && (img1.getHeight() == img2.getHeight());
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
     * @param srcImg String
     * @param destImg String
     * @throws IOException
     */
    @SuppressWarnings("squid:S4797")
    public void downloadImage(final String srcImg, final String destImg) throws IOException {

        try (InputStream in = new URL(srcImg).openStream()) {
            Files.copy(in, Paths.get(destImg));
        } catch (final IOException e) {
            LOGGER.error(e);
            ExtentTestManager.getTest().log(Status.FAIL, "Unable to download image");
            throw e;
        }

        LOGGER.info("Image is saved in:" + destImg);
    }

    /**
     * Method to compare image with standard one.
     * 
     * @param imgElement WebElement
     * @param standardImgFilename String
     * @return boolean
     * @throws IOException
     */
    @SuppressWarnings("squid:S4797")
    public boolean verifyImage(final WebElement imgElement, final String standardImgFilename)
                                                                                              throws IOException {
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
                LOGGER.info("Directory created: " + tmpDirectory);
            } else {
                LOGGER.info("Directory already exists : " + tmpDirectory);
            }

            LOGGER.info("Source of standard image: " + standardFilename);
            final File imgFileStandard = new File(standardFilename);
            final BufferedImage imgBuffStandard = ImageIO.read(imgFileStandard);
            LOGGER.info("File object for standard image is created");

            LOGGER.info("Source of image from WebElement: " + tmpFilename);
            downloadImage(srcAttributeImg, tmpFilename);
            final File imgFileWebElement = new File(tmpFilename);
            final BufferedImage imgBuffWebElement = ImageIO.read(imgFileWebElement);
            LOGGER.info("File object for image from Web Element is created");

            if (bufferedImagesEqual(imgBuffWebElement, imgBuffStandard)) {
                LOGGER.info("Images are equal");
                isEqualImg = true;
            } else {
                LOGGER.error("Images are different");
                ExtentTestManager.getTest().log(
                    Status.FAIL,
                    "Image is not equal to standard " + standardImgFilename);
            }

            Files.delete(Paths.get(tmpFilename));
            LOGGER.info("Temporary image is deleted");

        } catch (final IOException e) {
            LOGGER.error(e);
            ExtentTestManager.getTest().log(Status.FAIL, "Unable to compare images");
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
            baseconfig.getExplicitWaits().waitforVisibilityOfElementPresent(
                baseconfig.getBaseDriver(),
                imgEdFluxLogo,
                30);
            commonutils.verifyElement(imgEdFluxLogo);
            LOGGER.info("Edflux logo is present");
            isCorrectLogo = verifyImage(imgEdFluxLogo, filenameEdFluxLogo);
            LOGGER.info("EdFlux logo has compared with the standard. ");
            if (isCorrectLogo) {
                ExtentTestManager.getTest().log(Status.PASS, "Edflux logo is correct");
            } else {
                throw new IOException();
            }
        } catch (final IOException e) {
            LOGGER.error(e);
            ExtentTestManager.getTest().log(Status.FAIL, "Edflux logo is incorrect");
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
            baseconfig.getExplicitWaits().waitforVisibilityOfElementPresent(
                baseconfig.getBaseDriver(),
                imgSpringerNatureLogo,
                30);
            commonutils.verifyElement(imgSpringerNatureLogo);
            LOGGER.info("SpringerNature logo is present");
            isCorrectLogo = verifyImage(imgSpringerNatureLogo, filenameSpringerNatureLogo);
            LOGGER.info("SpringerNature logo has compared with the standard. ");
            if (isCorrectLogo) {
                ExtentTestManager.getTest().log(Status.PASS, "SpringerNature logo is correct");
            } else {
                throw new IOException();
            }
        } catch (final IOException e) {
            LOGGER.error(e);
            ExtentTestManager.getTest().log(Status.FAIL, "SpringerNature logo is incorrect");
            throw e;
        }
        return this;
    }

}
