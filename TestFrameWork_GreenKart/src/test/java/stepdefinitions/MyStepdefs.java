package stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.AcademyHomePage;
import org.example.AcademyLandingPageObjects;
import org.example.AcademyLoginPageObjects;
import org.testng.Assert;
import resources.Base;
import resources.BaseAcademy;

import java.io.IOException;

public class MyStepdefs extends BaseAcademy {
    @Given("User chooses {string} as the browser")
    public void user_chooses_as_the_browser(String browserName) throws IOException {

        driver = initializeBrowser(browserName);
    }

    @Given("Navigate to the website {string}")
    public void navigate_to_the_website(String string) {
        driver.get(string);
    }

    @Given("Checks and close if any popup is present")
    public void checks_and_close_if_any_popup_is_present() {
        // Write code here that turns the phrase above into concrete actions
        //throw new io.cucumber.java.PendingException();
        System.out.println("No popup found");
    }

    @Given("Clicks on Login button")
    public void clicks_on_Login_button() {
        // Write code here that turns the phrase above into concrete actions
        AcademyLandingPageObjects academyLandingPageObjects = new AcademyLandingPageObjects(driver);
        academyLandingPageObjects.getLoginButton().click();
    }

    @When("User login into Application with {string} and {string}")
    public void user_login_into_Application_with_and(String userName, String passWord) {
        AcademyLoginPageObjects academyLoginPageObjects = new AcademyLoginPageObjects(driver);
        academyLoginPageObjects.getUserNameTxtBox().sendKeys(userName);
        academyLoginPageObjects.getPassWordTxtBox().sendKeys(passWord);
        academyLoginPageObjects.getLoginButton().click();
    }



    @Then("Home page is populated for User {string}")
    public void homePageIsPopulatedForUser(String accountHolder) {

        AcademyHomePage academyHomePage = new AcademyHomePage(driver);

        String nameTxt = academyHomePage.getAccountHolderName().getText();
        Assert.assertEquals(nameTxt, accountHolder);
    }
}

