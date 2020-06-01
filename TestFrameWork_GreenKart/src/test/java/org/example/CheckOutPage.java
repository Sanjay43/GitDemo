package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import resources.Base;


//this calls will use the TestNG framework
public class CheckOutPage extends Base {
    private static Logger log = LogManager.getLogger("test.org.example.CheckOutPage");

    private static WebElement promoCodeText;
    private static WebElement applyButton;
    private static WebElement promoInfo;
    private static CheckOutPageObjects checkOutPageObjects;
    private static WebElement placeOrderButton;
    //private static WebElement proceedToCheckOutButton;

    @Test
    public void applyPromo() throws InterruptedException {
        WebDriverWait w =new WebDriverWait(driver,5);
        System.out.println("In CheckOutPage Class at the beginning: " + driver.getCurrentUrl());
        String currentUrl = prop.getProperty("currentUrl");
        driver.get(currentUrl);
        prop.remove("currentUrl");
        checkOutPageObjects = new CheckOutPageObjects(driver);
        String promoCode = prop.getProperty("promoCode");
        String expectedPromoInfo = prop.getProperty("promoInfo");

        promoCodeText = checkOutPageObjects.getPromoCodeText();
        applyButton = checkOutPageObjects.getApplyButton();

        placeOrderButton = checkOutPageObjects.getPlaceOrderButton();

        promoCodeText.sendKeys(promoCode);
        applyButton.click();
        //Thread.sleep(3000L);
        w.until(ExpectedConditions.visibilityOf(checkOutPageObjects.getPromoInfo()));
        promoInfo = checkOutPageObjects.getPromoInfo();
        String actualPromoInfo = promoInfo.getText();
        Assert.assertEquals(actualPromoInfo, expectedPromoInfo);

        placeOrderButton.click();

    }

}
