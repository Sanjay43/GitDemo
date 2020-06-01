package org.example;

import java.io.IOException;
import java.util.ArrayList;

public class TestData {

    public static void main( String[] args ) throws IOException {

        System.out.println("Hello World");
        System.out.println("*********************************");
        System.out.println();
        //Call function from a class FetchData to get data from the spreadsheet
        FetchData fd = new FetchData();
        String filename = "C:\\Users\\Sanjay\\Selenium - RS\\Test Data Spreadsheets\\GreenKart Shopping List.xlsx";
        Boolean horzScan = false;  // false implies that test data is in vertical direction under the testCase heading
        String sheetName = "GroceryV";  //for horzScan = true (i.e. for test data to be scanned horizontally) use sheetName GroceryH
        String headerColName = "Test Case";
        String testCase = "";
        int testCount = 2;
        int outerIndex = 0;
        int innerIndex = 0;
        int noOfRows = 0;
        int noOfColumns = 0;

        ArrayList<ArrayList<String>> a2DimArray = fd.getData(filename, sheetName, headerColName, testCase, testCount, horzScan);
        outerIndex = a2DimArray.size();
        noOfRows = outerIndex;
        //Use for debug
        //System.out.println("In TestData class outerIndex value is: " + outerIndex);

        for(int i=0;i<outerIndex;i++){
            innerIndex = a2DimArray.get(i).size();
            if(noOfColumns < innerIndex){
                noOfColumns = innerIndex;
            }
            //Use for debug
            //System.out.println("In TestData class for row " + i + "innerIndex value is: " + innerIndex);
            /*for(int j=0;j<innerIndex;j++){
                System.out.print(a2DimArray.get(i).get(j) + " ");
            }
            System.out.println();*/
        }
        //Use for debug
        /*System.out.println("No of rows to be created " + noOfRows);
        System.out.println("No of columns to be created " + noOfColumns);*/
        Object[][] testDataArray = new Object[noOfRows+1][noOfColumns+1];
        for(int i=0;i<outerIndex;i++){
            innerIndex = a2DimArray.get(i).size();
            if(noOfColumns < innerIndex){
                noOfColumns = innerIndex;
            }
            //System.out.println("In TestData class for row " + i + "innerIndex value is: " + innerIndex);
            for(int j=0;j<innerIndex;j++){
                testDataArray[i][j] = a2DimArray.get(i).get(j);
            }
            //System.out.println();
        }
        System.out.println("************Values in testDataArray************");
        for(int i=0;i<noOfRows;i++){
            for(int j=0;j<noOfColumns;j++){
                System.out.print(testDataArray[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println();
        System.out.println("*********************************");
        System.out.println("Goodbye World");

    }
}
