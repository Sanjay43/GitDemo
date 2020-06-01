package resources;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class BaseAcademy {

    //Declare WebDriver variable
    public static WebDriver driver = null;

    public static Properties prop = null;


    //Write a initializeBrowser method using data from the datadrive.properties file. Declare the Properties variable as a class variable
    //so that rest of the methods can access it
    public WebDriver initializeBrowser(String browserName) throws IOException {
        /*prop = new Properties();

        //Assign the datadriven.properties to FileInputStream class
        FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\java\\resources\\datadriven.properties");

        //Load this file in the Properties bject
        prop.load(fis);

        //String browserName = prop.getProperty("browser");
        String browserName = System.getProperty("browser");
        System.out.println(browserName);*/



        if(browserName.contains("chrome")){
            System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\src\\test\\java\\resources\\chromedriver.exe");
            ChromeOptions options = new ChromeOptions();
            if(browserName.contains("headless")){
                options.addArguments("headless");
            }
            driver = new ChromeDriver(options);

        } else if(browserName.contains("firefox")){
            System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"\\src\\test\\java\\resources\\geckodriver.exe");
            driver = new FirefoxDriver();
        }
        else{
            System.out.println("invalid browser");
            exitOnError();
        }

        //String urlName = prop.getProperty("url");

        //Set impilict wait to 10 seconds for all tests
        driver.manage().timeouts().implicitlyWait(10L, TimeUnit.SECONDS);

        return driver;
    }

    public String captureScreenShot(WebDriver driver, String testCase, String methodName) throws IOException {
        File source = null;
        TakesScreenshot ts = (TakesScreenshot) driver;
        source = ts.getScreenshotAs(OutputType.FILE);

        /*SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd-HHmmss");

        String todays_date = format.format( new Date()   );*/
        String todays_date = getTodaysDate("yyyyMMdd-HHmmss");
        String destinationFile = System.getProperty("user.dir")+"\\reports\\" + testCase + "_" + methodName + "_" + todays_date + ".png";

        FileUtils.copyFile(source, new File(destinationFile));

        return destinationFile;
    }

    public static String getTodaysDate(String pattern){
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        String todays_date = format.format(new Date());
        return todays_date;
    }
    private static void exitOnError(){
        System.out.println("Fatal Error");
    }
}
