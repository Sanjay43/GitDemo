package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import resources.Base;

import java.io.IOException;
//import java.util.Arrays;
import java.util.*;
import java.util.List;
//import java.util.function.Function;

//this calls will use the TestNG framework
public class HomePage extends Base {
    private static Logger log = LogManager.getLogger("test.org.example.HomePage");
    private static List<WebElement> productsList;
    private static List<WebElement> incrementButton;
    private static List<WebElement> addItemButton;
    private static HomePageObjects homePageObjects;
    private static WebElement addToCartButton;
    private static WebElement proceedToCheckOutButton;
    //private String[] itemsNeeded = new String[10];
    private ArrayList<String> itemsToAdd = new ArrayList<>();
    private int arrayIndex = 0;
    WebDriver obj =  null;

    //Before any test is executed initialise the driver
    @BeforeSuite
    public void initialize() throws IOException {
        driver = initializeBrowser();
    }

    /*@Test
    public void readTestData(){

    }*/
    @Test (dataProvider = "getData")
    public void navigateToHomePage(String s, String s1[]) throws InterruptedException {
        obj = driver;
        log.info("Inside method navigateToHomePage()");
        //First get the URL value from the datadriven.properties file
        String urlName = prop.getProperty("url");

        //Now access the website
        driver.get(urlName);
        log.info("Home Page Displayed for Test Case: " + s);
        log.info("Exiting method navigateToHomePage()");

        System.out.println(s);
        System.out.println(s1.length);

        for(int i=0;i<s1.length;i++) {

            System.out.println(i +": " + s1[i]);
            if(s1[i]!=null) {
                addItemsToArray(s1[i]);
            }

        }
        addItemsToCart();
        //Assert.assertTrue(false);
    }

    //@Test(dependsOnMethods = "navigateToHomePage")
    private void addItemsToArray(String productsList) throws InterruptedException {
        System.out.println(productsList);
        log.info("Inside method addItemsToArray()");
        itemsToAdd.add(productsList);

        log.info("Exiting method addItemsToArray()");
    }
    //@Test(dependsOnMethods = "addItemsToArray", enabled = false)
    private void addItemsToCart() throws InterruptedException {
        log.info("Inside method addItemsToCart()");

        //Create a new object for the HomePageObjects class
//        homePageObjects = new HomePageObjects(driver);
        addItemsCss(driver,itemsToAdd);
        prop.setProperty("currentUrl",driver.getCurrentUrl());
        log.info("Exiting method addItemsToCart()");

    }
    private static void addItemsCss(WebDriver driver, ArrayList<String> itemsToAdd) throws InterruptedException {
        //WebDriver driver = driver;
        homePageObjects = new HomePageObjects(driver);
        productsList = homePageObjects.getProductsList();
        incrementButton = homePageObjects.getIncrementButton();
        addItemButton = homePageObjects.getAddItemButton();
        addToCartButton = homePageObjects.getAddToCartButton();
        proceedToCheckOutButton = homePageObjects.getProceedToCheckOutButton();



        WebDriverWait w =new WebDriverWait(driver,5);

        int j = 0;
        int quantity = 0;

        //Convert the String array of addItems to Array list for eay processing
        List addItemsList = itemsToAdd;


        for(int i=0; i< productsList.size(); i++){
            String[] productName = productsList.get(i).getText().split("-");
            String formattedProductName = productName[0].trim();
            for(int k=0;k<addItemsList.size();k++){
                String[] addItem = addItemsList.get(k).toString().split("-");
                if(addItem[0].contains(formattedProductName)) {
                    j++;
                    System.out.println("addItem[0]: " + addItem[0].trim());
                    System.out.println("addItem[1]: " + addItem[1].trim());
                    quantity = Integer.parseInt(addItem[1].trim());
                    int l = 1;
                    while(l<quantity) {
                        incrementButton.get(i).click();
                        l++;
                    }
                    addItemButton.get(i).click();

                    //This delay is added so that the items get added correctly and not over add due to the processing speed of the code
                    w.until(ExpectedConditions.textToBePresentInElement(driver.findElements(By.cssSelector("div[class='product-action'] button")).get(i),"ADD TO CART"));
                    //Thread.sleep(2000L);
                }

            }
            if(j==itemsToAdd.size()){
                break;
            }

        }

        addToCartButton.click();

        Assert.assertTrue(true);
        proceedToCheckOutButton.click();
        //Thread.sleep(2000L);
        w.until(ExpectedConditions.urlContains("cart"));
        System.out.println("In HomePage Class after clicking proceed to CheckOutButton: " + driver.getCurrentUrl());
        System.out.println();



    }

    @DataProvider
    public Object[][] getData() throws IOException {
        FetchData fd = new FetchData();
        String filename = System.getProperty("user.dir")+"\\src\\test\\java\\resources\\GreenKart Shopping List.xlsx";
        Boolean horzScan = true;  // false implies that test data is in vertical direction under the testCase heading
        String sheetName = "GroceryH";  //for horzScan = true (i.e. for test data to be scanned horizontally) use sheetName GroceryH
        String headerColName = "Test Case";
        String testCase = "TestCase4";
        int testCount = 0;
        int outerIndex = 0;
        int innerIndex = 0;
        int noOfRows = 0;
        int noOfColumns = 0;

        ArrayList<ArrayList<String>> a2DimArray = fd.getData(filename, sheetName, headerColName, testCase, testCount, horzScan);
        outerIndex = a2DimArray.size();
        noOfRows = outerIndex;

        for(int i=0;i<outerIndex;i++) {
            innerIndex = a2DimArray.get(i).size();
            if (noOfColumns < innerIndex) {
                noOfColumns = innerIndex;
            }
        }

        Object[][] testDataArray = new Object[noOfRows][noOfColumns];

        for(int i=0;i<outerIndex;i++){
            innerIndex = a2DimArray.get(i).size();
            if(noOfColumns < innerIndex){
                noOfColumns = innerIndex;
            }
            System.out.println("In TestData class for row " + i + "innerIndex value is: " + innerIndex);
            for(int j=0;j<innerIndex;j++){
                testDataArray[i][j] = a2DimArray.get(i).get(j);
                System.out.println("In Data Provider method testArrayData["+i+"]["+j+"]" + testDataArray[i][j]);
            }
            //System.out.println();
        }

        return testDataArray;
    }

    //After all tests are executed close the browser and release the memory occupied by the driver object
    @AfterSuite
    public void tearDown() throws InterruptedException {
        Thread.sleep(5000L);
        
        /*for(int i = 0; i<itemsNeeded.length;i++){
            System.out.println(itemsNeeded[i]);
        }*/

        /*for(int i=0; i<itemsToAdd.size();i++){
            System.out.println(itemsToAdd.get(i).toString());
        }*/

        driver.close();
        driver = null;
    }


}
