package org.example;



import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class FetchData
{
    private FileInputStream fis = null;
    private ArrayList<ArrayList<String>> twoDimArray = new ArrayList<>();

    public ArrayList<ArrayList<String>> getData(String fileName, String sheetName, String headerColName, String testCase, int testCount, Boolean horzScan) throws IOException {
        boolean validInput = false;
        validInput = validateInput(testCase, testCount);
        if(validInput) {
            fis = accessFile(fileName);
            if(testCount>0) {
                readExcelFile(fis, sheetName, headerColName, testCount, horzScan);
            } else {
                readExcelFile(fis, sheetName, headerColName, testCase, horzScan);
            }
        }
        return twoDimArray;
    }

    private boolean validateInput(String testCase, int testCount){
        boolean validInput = true;
        ArrayList<String> a = new ArrayList<>();
        String errMsg1 = "Fatal Error! Need to pass either testCase or testCount";
        String errMsg2 = "Cannot run the application for single testcase and multiple testcases simultaneously";
        if(testCase == null){
            if(testCount <= 0) {
                a.add(errMsg1);
            }
        } else if(testCase.isEmpty() && testCount <= 0) {
            a.add(errMsg1);
        } else if(!testCase.isEmpty() && testCount>0){
            a.add(errMsg2);
        }

        if(a.size()>0){
            addTo2DimArray(a,0, 0);
            validInput = false;
        }

        return validInput;

    }

   /* public ArrayList<ArrayList<String>> getData(String fileName, String sheetName, String headerColName, int testCount, Boolean horzScan) throws IOException {
        if(testCount<=0){
            return twoDimArray = null;
        }
        fis = accessFile(fileName);
        readExcelFile(fis,sheetName,headerColName,testCount, horzScan);
        return twoDimArray;
    }*/

    private FileInputStream accessFile(String fileName) throws FileNotFoundException {
        fis = new FileInputStream(fileName);
        return fis;
    }

    private ArrayList<String> readExcelFile(FileInputStream f, String sheetName, String headerColName, String testCase, Boolean horzScan) throws IOException {
        int numberOfSheets = 0;
        int sheetIndexValue = 0;
        boolean sheetIndexFound = false;
        String localSheetName = null;
        int firstRow = 0;
        ArrayList<String> a= null;

        //Process workbook to identify the required sheet
        XSSFWorkbook wb1 = new XSSFWorkbook(f);

        //Determine the number of sheets in the spreadsheet and locate the sheet for the name provided
        numberOfSheets = wb1.getNumberOfSheets();
        for(int i=0;i<numberOfSheets;i++){
            localSheetName = wb1.getSheetName(i);
            if(localSheetName.equalsIgnoreCase(sheetName)){
                sheetIndexValue = i;
                sheetIndexFound = true;
            }
        }
        if(sheetIndexFound){
            //Process Sheet to identify Rows
            XSSFSheet sheet = wb1.getSheetAt(sheetIndexValue);
            //Use iterator on the sheet which is collection of rows
            Iterator<Row> row = sheet.iterator();

            //Let's first identify the first row on the sheet which has data. This data will be the Header row
            Row headerRow = null;
            Row generalRow;
            int headerRowNum = 0;
            int headerColIndex = 0;
            int rowCount = 0;
            boolean headerRowFound = false;
            int cellCount = 0;
            Iterator<Cell> cell = null;
            Cell headerCell = null;
            Cell generalCell;
            while(row.hasNext()){
                generalRow = row.next();
                if(!headerRowFound) {
                    headerRow = generalRow;
                    //Use iterator on the Row which is collection of cells

                    cell = headerRow.cellIterator();
                    //Now let's identify the cell on the header row identified which corresponds to the Header Column value passed

                    cellCount = 0;
                    boolean headerCellFound = false;

                    while(cell.hasNext()){
                        generalCell = cell.next();
                        if(!headerCellFound){
                            if(generalCell.getStringCellValue().equalsIgnoreCase(headerColName)) {
                                headerCell = generalCell;
                                headerCellFound = true;
                                headerColIndex = headerCell.getColumnIndex();
                                headerRowFound = true;
                                headerRowNum = headerRow.getRowNum();
                            }
                        }
                        cellCount++;
                        /*//Limit the columns to 10 including the Header Column
                        if(cellCount>9){
                            break;
                        }*/
                    }

                }
                rowCount++;

                //Limit the testdata to 100 rows including Header row
                /*if(rowCount>99){
                    break;
                }*/
            }

            row = sheet.iterator();
            scanTestData(row, sheet, headerRow, headerRowNum, cell, headerCell, headerColIndex, testCase, horzScan);
        }
        else{
            //Sheet with the name passed not found. Return error
            System.out.println("Error!");
        }
        return a;
    }

    //This method is called when a number is passed to generate the given number of testcases
    private void readExcelFile(FileInputStream f, String sheetName, String headerColName, int testCount, Boolean horzScan) throws IOException {
        int numberOfSheets = 0;
        int sheetIndexValue = 0;
        boolean sheetIndexFound = false;
        String localSheetName = null;
        int firstRow = 0;
        ArrayList<String> a= null;

        //Process workbook to identify the required sheet
        XSSFWorkbook wb1 = new XSSFWorkbook(f);

        //Determine the number of sheets in the spreadsheet and locate the sheet for the name provided
        numberOfSheets = wb1.getNumberOfSheets();
        for(int i=0;i<numberOfSheets;i++){
            localSheetName = wb1.getSheetName(i);
            if(localSheetName.equalsIgnoreCase(sheetName)){
                sheetIndexValue = i;
                sheetIndexFound = true;
            }
        }
        if(sheetIndexFound){
            //Process Sheet to identify Rows
            XSSFSheet sheet = wb1.getSheetAt(sheetIndexValue);
            //Use iterator on the sheet which is collection of rows
            Iterator<Row> row = sheet.iterator();

            //Let's first identify the first row on the sheet which has data. This data will be the Header row
            Row headerRow = null;
            Row generalRow;
            int headerRowNum = 0;
            int headerColIndex = 0;
            int rowCount = 0;
            boolean headerRowFound = false;
            int cellCount = 0;
            Iterator<Cell> cell = null;
            Cell headerCell = null;
            Cell generalCell;
            while(row.hasNext()){
                generalRow = row.next();
                if(!headerRowFound) {
                    headerRow = generalRow;
                    //Use iterator on the Row which is collection of cells

                    cell = headerRow.cellIterator();
                    //Now let's identify the cell on the header row identified which corresponds to the Header Column value passed

                    cellCount = 0;
                    boolean headerCellFound = false;

                    while(cell.hasNext()){
                        generalCell = cell.next();
                        if(!headerCellFound){
                            if(generalCell.getStringCellValue().equalsIgnoreCase(headerColName)) {
                                headerCell = generalCell;
                                headerCellFound = true;
                                headerColIndex = headerCell.getColumnIndex();
                                headerRowFound = true;
                                headerRowNum = headerRow.getRowNum();
                            }
                        }
                        cellCount++;
                        //Limit the columns to 10 including the Header Column
                        if(cellCount>9){
                            break;
                        }
                    }

                }
                rowCount++;

                //Limit the testdata to 100 rows including Header row
                if(rowCount>99){
                    break;
                }
            }

            row = sheet.iterator();

            scanTestData(row, sheet, headerRow, headerRowNum, cell, headerCell, headerColIndex, testCount, horzScan);
        }
        else{
            //Sheet with the name passed not found. Return error
            System.out.println("Error!");
        }
    }

    private void scanTestData(Iterator<Row> row, XSSFSheet sheet, Row headerRow, int headerRowNum, Iterator<Cell> cell, Cell headerCell, int headerColIndex, String testCase, boolean horzScan){
        Iterator<Row> inputRow = row;
        Row testRow;
        Row testCol;
        Cell testCell;
        int colIndex = 0;
        int cellsHeaderScanned = 0;
        int cellsTestDataScanned = 0;
        int rowsScanned = 0;
        String cellStringVal;
        double cellNumericVal = 0;
        String colName = null;
        String addToArray;
        boolean hdrRowFlagged = false;
        boolean hdrColFlagged = false;
        ArrayList<String> a = new ArrayList();
        if(horzScan){
            //Test Data is in horizontal direction - i.e. Cell n of the identified testcase row has the testcase name &
            //subsequent columns in that row has the actual data

            while(row.hasNext()){
                testCol = row.next();
                colIndex = 0;
                while(testCol.getRowNum() == headerRowNum){

                    //Count the number of Data columns including the Header Column
                    Iterator<Cell> localCell = testCol.cellIterator();
                    while(localCell.hasNext()){
                        localCell.next();
                        cellsHeaderScanned++;
                    }
                    break;
                }
                //As the data is in horizontal direction we need to scan data from the identified row only
                while(testCol.getRowNum() >= headerRowNum) {
                    cellStringVal = convToString(testCol,headerColIndex);

                    if (!cellStringVal.isEmpty()) {
                        while (cellStringVal.equalsIgnoreCase(testCase)) {
                            /*Iterator<Cell> cellLocal = testCol.cellIterator();
                            while (cellLocal.hasNext()) {
                                testCell = cellLocal.next();
                                cellStringVal = convToString(testCell);
                                addToArray = cellStringVal;
                                a.add(addToArray);
                            }*/
                            while(colIndex<=cellsHeaderScanned){
                                if(testCol.getCell(colIndex)!=null){
                                    cellStringVal = convToString(testCol.getCell(colIndex));
                                    addToArray = cellStringVal;
                                    cellsTestDataScanned = colIndex;
                                    colIndex++;
                                    a.add(addToArray);
                                }
                                else{
                                    addToArray = null;
                                    a.add(addToArray);
                                    cellsTestDataScanned = colIndex;
                                    colIndex++;
                                }
                            }
                            //Call Add to 2 dimensional array list function here
                            if(cellsTestDataScanned <= cellsHeaderScanned) {
                                addTo2DimArray(a, rowsScanned, cellsTestDataScanned);
                                a.clear();
                            } else {
                                System.out.println("Test Data Scanned and Header Data Scanned mismatch!!!");
                            }
                            rowsScanned++;
                        }

                        if(rowsScanned > 0){
                            break;
                        }
                    }
                    break;
                }
                if(rowsScanned > 0){
                    break;
                }
            }
        }
        else{
            //Test Data is in vertical direction - i.e. Cell n of the identified testcase row has the testcase name &
            //Cell n of subsequent rows has the actual data
            while(row.hasNext()) {
                testRow = row.next();
                if (testRow.getRowNum() >= headerRowNum) {
                    cellsHeaderScanned = testRow.getRowNum();
                }
            }
            //Assign the row iterator to new variable - cannot reinitialise a Iterator
            Iterator<Row> inputRow1 = sheet.iterator();

            //Now traverse column wise from the column which has been identified as having the Header - Test Case
            while(inputRow1.hasNext()){
                testRow = inputRow1.next();
                while(testRow.getRowNum() == headerRowNum) {
                    /*Iterator<Cell> localCell = testRow.cellIterator();
                    while(localCell.hasNext()) {
                        testCell = localCell.next();
                        cellStringVal = convToString(testCell);
                        if (cellStringVal.equalsIgnoreCase(testCase)) {
                            colIndex = testCell.getColumnIndex();
                            a.add(cellStringVal);
                        }
                    }

                    while(row.hasNext()){
                        testRow = row.next();
                        cellStringVal = convToString(testRow,colIndex);
                        addToArray = cellStringVal;
                        a.add(addToArray);
                    }*/
                    Iterator<Cell> localCell = testRow.cellIterator();
                    while(localCell.hasNext()) {
                        testCell = localCell.next();
                        //This code will exclude the Header Column (Test Case) and start from the next Column (e.g. TestCase1)
                        if(testCell.getColumnIndex() > headerColIndex){
                            cellStringVal = convToString(testCell);
                            if(cellStringVal.equalsIgnoreCase(testCase)) {
                                colIndex = testCell.getColumnIndex();
                                Iterator<Row> tempRow = sheet.iterator();
                                while (tempRow.hasNext()) {
                                    testCol = tempRow.next();
                                    if (testCol.getRowNum() >= headerRowNum) {
                                        if (testCol.getCell(colIndex) != null) {
                                            cellStringVal = convToString(testCol, colIndex);
                                            addToArray = cellStringVal;
                                            a.add(addToArray);
                                            cellsTestDataScanned = testCol.getRowNum();
                                        } else {
                                            addToArray = null;
                                            a.add(addToArray);
                                            cellsTestDataScanned = testCol.getRowNum();
                                        }
                                    }
                                }
                                //Call Add to 2 dimensional array list function here
                                if (cellsTestDataScanned <= cellsHeaderScanned) {
                                    //Use for debug
                                /*for(int i=0; i<a.size(); i++){
                                    System.out.print(a.get(i) + " ");
                                }*/
                                    addTo2DimArray(a, rowsScanned, cellsTestDataScanned);
                                    a.clear();
                                } else {
                                    System.out.println("Test Data Scanned and Header Data Scanned mismatch!!!");
                                }
                                rowsScanned++;
                                if (rowsScanned > 0) {
                                    break;
                                }
                            }
                        }
                        if (rowsScanned > 0) {
                            break;
                        }
                    }
                    break;
                }
            }
        }
        //addTo2DimArray(a);
        //return a;
    }

    //This method is called when a number is passed to generate the given number of testcases
    private void scanTestData(Iterator<Row> row, XSSFSheet sheet, Row headerRow, int headerRowNum, Iterator<Cell> cell, Cell headerCell, int headerColIndex, int testCount, boolean horzScan){
        Iterator<Row> inputRow = row;
        Iterator<Cell> inputCell = cell;
        Row testRow;
        Row testCol;
        Cell testCell;
        int rowsScanned = 0;
        int cellsHeaderScanned = 0;
        int cellsTestDataScanned = 0;
        int colIndex = 0;
        String cellStringVal;
        double cellNumericVal = 0;
        String colName = null;
        String addToArray;
        boolean hdrRowFlagged = false;
        boolean hdrColFlagged = false;
        ArrayList<String> a = new ArrayList();
        if(horzScan){
            //Test Data is in horizontal direction - i.e. Cell n of the identified testcase row has the testcase name &
            //subsequent columns in that row has the actual data

            while(inputRow.hasNext()){
                testCol = inputRow.next();
                if(rowsScanned>=testCount){
                    break;
                }
                colIndex = 0;
                //As the data is in horizontal direction we need to scan data from the identified row only
                while(testCol.getRowNum() == headerRowNum){

                    //Count the number of Data columns including the Header Column
                    Iterator<Cell> localCell = testCol.cellIterator();
                    while(localCell.hasNext()){
                        localCell.next();
                        cellsHeaderScanned++;
                    }
                    break;
                }
                while(testCol.getRowNum() > headerRowNum) {

                    cellStringVal = convToString(testCol,headerColIndex);

                    if (!cellStringVal.isEmpty()) {

                        while(colIndex<=cellsHeaderScanned){
                            if(testCol.getCell(colIndex)!=null){
                                cellStringVal = convToString(testCol.getCell(colIndex));
                                addToArray = cellStringVal;
                                cellsTestDataScanned = testCol.getCell(colIndex).getColumnIndex();
                                colIndex++;
                                a.add(addToArray);
                            }
                            else{
                                addToArray = null;
                                a.add(addToArray);
                                cellsTestDataScanned = colIndex;
                                colIndex++;
                            }
                        }

                        //Call Add to 2 dimensional array list function here
                        if(cellsTestDataScanned <= cellsHeaderScanned) {
                            addTo2DimArray(a, rowsScanned, cellsTestDataScanned);
                            a.clear();
                        } else {
                            System.out.println("Test Data Scanned and Header Data Scanned mismatch!!!");
                        }

                    }
                    //System.out.println("Rows scanned: " + rowsScanned);
                    rowsScanned++;
                    break;
                }
            }
        }
        else{
            //Test Data is in vertical direction - i.e. Cell n of the identified testcase row has the testcase name &
            //Cell n of subsequent rows has the actual data
            while(inputRow.hasNext()){
                //This gives the number of Data Items present on the given spreadsheet. These Data items (
                // including the header Row are in vertical direction)
                testRow = inputRow.next();
                if(testRow.getRowNum()>=headerRowNum) {
                    cellsHeaderScanned = testRow.getRowNum();
                }
            }

            //Assign the row iterator to new variable - cannot reinitialise a Iterator
            Iterator<Row> inputRow1 = sheet.iterator();

            //Now traverse column wise from the column which has been identified as having the Header - Test Case
            while(inputRow1.hasNext()){

                testRow = inputRow1.next();
                while(testRow.getRowNum() == headerRowNum) {

                    Iterator<Cell> localCell = testRow.cellIterator();
                    while(localCell.hasNext()) {
                        if(rowsScanned >= testCount){
                            break;
                        }
                        testCell = localCell.next();
                        //This code will exclude the Header Column (Test Case) and start from the next Column (e.g. TestCase1)
                        if(testCell.getColumnIndex() > headerColIndex){
                            colIndex = testCell.getColumnIndex();
                            Iterator<Row> tempRow = sheet.iterator();
                            while(tempRow.hasNext()){
                                testCol = tempRow.next();
                                if(testCol.getRowNum()>=headerRowNum){
                                    if(testCol.getCell(colIndex)!=null) {
                                        cellStringVal = convToString(testCol, colIndex);
                                        addToArray = cellStringVal;
                                        a.add(addToArray);
                                        cellsTestDataScanned = testCol.getRowNum();
                                    }
                                    else{
                                        addToArray = null;
                                        a.add(addToArray);
                                        cellsTestDataScanned = testCol.getRowNum();
                                    }
                                }
                            }
                            //Call Add to 2 dimensional array list function here
                            if(cellsTestDataScanned <= cellsHeaderScanned) {
                                //Use for debug
                                /*for(int i=0; i<a.size(); i++){
                                    System.out.print(a.get(i) + " ");
                                }*/
                                addTo2DimArray(a, rowsScanned, cellsTestDataScanned);
                                a.clear();
                            } else {
                                System.out.println("Test Data Scanned and Header Data Scanned mismatch!!!");
                            }
                            rowsScanned++;
                        }
                    }
                    break;
                }
            }
        }
    }

    private String convToString(Row r, int colIndex){
        CellType cellType = r.getCell(colIndex).getCellType();
        String cellStringVal = "Data other than String and Numeric";
        if(cellType == CellType.STRING){
            cellStringVal = r.getCell(colIndex).getStringCellValue();
        } else if(cellType == CellType.NUMERIC){
            double cellNumericVal = r.getCell(colIndex).getNumericCellValue();
            cellStringVal = NumberToTextConverter.toText(cellNumericVal);
        }
        return cellStringVal;
    }

    private String convToString(Cell c){
        CellType cellType = c.getCellType();
        String cellStringVal = "Data other than String and Numeric";
        if(cellType == CellType.STRING){
            cellStringVal = c.getStringCellValue();
        } else if(cellType == CellType.NUMERIC){
            double cellNumericVal = c.getNumericCellValue();
            cellStringVal = NumberToTextConverter.toText(cellNumericVal);
        }
        return cellStringVal;
    }

    private void addTo2DimArray(ArrayList<String> a, int rowsScanned, int cellsScanned){
        ArrayList<String> inputArrayList = a;
        int outerIndex = rowsScanned;
        int innerIndex = cellsScanned;
        int arrayListIndex = 0;

        while(twoDimArray.size()<=outerIndex){
            twoDimArray.add(new ArrayList<String>());
        }

        ArrayList<String> inner = twoDimArray.get(outerIndex);
        while(inner.size()<=innerIndex){
            if(inputArrayList.get(arrayListIndex) != null) {
                inner.add(arrayListIndex, inputArrayList.get(arrayListIndex));
            }
            else
            {
                inner.add(null);
            }
            arrayListIndex++;
        }
    }

    private void addTo2DimArray(ArrayList<String> a){
        ArrayList<String> inputArrayList = a;
        int outerIndex = 0;
        int innerIndex = 0;
        int arrayListIndex = 0;

        while(twoDimArray.size()<=outerIndex){
            twoDimArray.add(new ArrayList<String>());
        }

        ArrayList<String> inner = twoDimArray.get(outerIndex);
        while(inner.size()<=innerIndex){
            if(inputArrayList.get(arrayListIndex) != null) {
                inner.add(arrayListIndex, inputArrayList.get(arrayListIndex));
            }
            else
            {
                inner.add(null);
            }
            arrayListIndex++;
        }
    }
}   //
