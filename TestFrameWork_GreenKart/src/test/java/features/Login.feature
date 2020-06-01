Feature: Application Login

  Scenario: Home Page defaults to Login
    Given User chooses "chrome" as the browser
    And Navigate to the website "https://www.rahulshettyacademy.com"
    And Checks and close if any popup is present
    And Clicks on Login button
    When User login into Application with "schaubal43@gmail.com" and "Selenium12$"
    Then Home page is populated for User "Sanjay Chaubal"


