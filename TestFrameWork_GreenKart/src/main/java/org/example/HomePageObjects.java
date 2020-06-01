package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class HomePageObjects {

    WebDriver driver;

    public HomePageObjects(WebDriver driver) {
        this.driver = driver;
    }

    private By productsList =  By.cssSelector("h4.product-name");
    private By addItemButton = By.xpath("//div[@class='product-action']/button");
    private By incrementButton = By.cssSelector("a[class='increment']");
    private By addToCartButton = By.cssSelector("a[class='cart-icon']");
    private By proceedToCheckOutButton = By.cssSelector("div[class='action-block'] button");

    public List<WebElement> getProductsList(){
        return driver.findElements(productsList);
    }

    public List<WebElement> getIncrementButton(){
        return driver.findElements(incrementButton);
    }

    public List<WebElement> getAddItemButton(){
        return driver.findElements(addItemButton);
    }

    public WebElement getAddToCartButton(){
        return driver.findElement(addToCartButton);
    }

    public WebElement getProceedToCheckOutButton(){
        return driver.findElement(proceedToCheckOutButton);
    }
}
