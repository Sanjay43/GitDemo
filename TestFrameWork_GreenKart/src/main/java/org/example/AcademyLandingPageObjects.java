package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AcademyLandingPageObjects {

    public WebDriver driver;

    public AcademyLandingPageObjects(WebDriver driver) {
        this.driver = driver;
    }

    private By loginButton = By.cssSelector("div.login-btn:nth-child(3) > a:nth-child(1)");

    public WebElement getLoginButton(){
        return driver.findElement(loginButton);
    }
}
