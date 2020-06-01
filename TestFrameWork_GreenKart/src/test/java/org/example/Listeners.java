package org.example;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import resources.Base;
import resources.ExtentReporterNG;

import java.io.IOException;

public class Listeners extends Base implements ITestListener {

    ExtentTest test;
    ExtentReports extent = ExtentReporterNG.getReportObject();
    ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>(); //to enable executions of tests in parallel

    @Override
    public void onTestStart(ITestResult iTestResult) {

        test= extent.createTest(iTestResult.getMethod().getMethodName());
        extentTest.set(test);
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        System.out.println("Test Successfully executed: " + iTestResult.getName());
        extentTest.get().log(Status.PASS, "Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        extentTest.get().fail(iTestResult.getThrowable());
        WebDriver driver = null;

        try {
            driver = (WebDriver)iTestResult.getTestClass().getRealClass().getDeclaredField("obj").get(iTestResult.getInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
        String testCase = iTestResult.getTestClass().getRealClass().getName();
        String methodName = iTestResult.getMethod().getMethodName();

        //System.out.println("Test failed in test: " + testCase + " and method: " + methodName);

        try {
            String imagePath = captureScreenShot(driver, testCase, methodName);
            extentTest.get().addScreenCaptureFromPath(imagePath,iTestResult.getMethod().getMethodName());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

    }

    @Override
    public void onStart(ITestContext iTestContext) {

    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        extent.flush();
    }
}
