package com.atguigu;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

public class PoiTest {
    @Test
    public void testPoi() throws Exception{
        XSSFWorkbook sheets = new XSSFWorkbook("D:\\hello.xlsx");
        XSSFSheet sheetAt = sheets.getSheetAt(0);
        for (Row row : sheetAt) {
            for (Cell cell : row) {
                String stringCellValue = cell.getStringCellValue();
                System.out.println(stringCellValue);
            }
        }
        sheets.close();
    }

    @Test
    public void testPoi2() throws Exception{
        XSSFWorkbook sheets = new XSSFWorkbook("D:\\hello.xlsx");
        XSSFSheet sheetAt = sheets.getSheetAt(0);
        int lastRowNum = sheetAt.getLastRowNum();
        for (int i = 0; i <= lastRowNum; i++) {
            XSSFRow row = sheetAt.getRow(i);
            short lastCellNum = row.getLastCellNum();
            for (int j = 0; j < lastCellNum; j++) {
                String stringCellValue = row.getCell(j).getStringCellValue();
                System.out.println(stringCellValue);
            }
        }
        sheets.close();
    }
    
    @Test
    public void testExport(){

    }
}
