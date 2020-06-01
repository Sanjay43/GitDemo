package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AcademyHomePage {

    WebDriver driver;

    public AcademyHomePage(WebDriver driver) {
        this.driver = driver;
    }

    By accountHolderName = By.cssSelector("span[class='navbar-current-user']");

    public WebElement getAccountHolderName(){
        return driver.findElement(accountHolderName);
    }
}
