package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AcademyLoginPageObjects {

    public WebDriver driver;

    public AcademyLoginPageObjects(WebDriver driver) {
        this.driver = driver;
    }

    private By userNameTxtBox = By.cssSelector("input[id='user_email']");
    private By passWordTxtBox = By.cssSelector("input[id='user_password']");
    private By loginButton = By.cssSelector("input[name='commit']");


    public WebElement getUserNameTxtBox(){
        return driver.findElement(userNameTxtBox);
    }

    public WebElement getPassWordTxtBox(){
        return driver.findElement(passWordTxtBox);
    }

    public WebElement getLoginButton(){
        return driver.findElement(loginButton);
    }

}
