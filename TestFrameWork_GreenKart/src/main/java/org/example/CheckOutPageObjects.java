package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CheckOutPageObjects {

    WebDriver driver;

    public CheckOutPageObjects(WebDriver driver) {
        this.driver = driver;
    }

    private By promoCodeText =  By.cssSelector("input.promoCode");
    private By applyButton = By.cssSelector("button.promoBtn");
    private By promoInfo = By.cssSelector("span[class='promoInfo']");
    private By placeOrderButton = By.cssSelector("div.products button:nth-child(14)");


    public WebElement getPromoCodeText(){
        return driver.findElement(promoCodeText);
    }

    public WebElement getApplyButton(){
        return driver.findElement(applyButton);
    }

    public WebElement getPromoInfo(){
        return driver.findElement(promoInfo);
    }

    public WebElement getPlaceOrderButton(){
        return driver.findElement(placeOrderButton);
    }

}
