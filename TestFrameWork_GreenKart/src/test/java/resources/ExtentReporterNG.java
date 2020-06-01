package resources;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReporterNG {

    private static ExtentReports extent;


    //declaring getReportObjects() static enables us to call this method in Listeners class without creating an object.
    public static ExtentReports getReportObject(){

        String todays_date = Base.getTodaysDate("yyyyMMdd-HHmmss");
        String path =System.getProperty("user.dir")+"\\reports\\index_"+todays_date+".html";

        ExtentSparkReporter reporter = new ExtentSparkReporter(path);

        reporter.config().setReportName("Web Automation Results");

        reporter.config().setDocumentTitle("Test Results");

        extent =new ExtentReports();

        extent.attachReporter(reporter);

        extent.setSystemInfo("Tester", "Sanjay Chaubal");

        return extent;
    }
}
