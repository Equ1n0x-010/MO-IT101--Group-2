/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.salary.motorphemployeefilehandler;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 *
 * @author Paul Jericho Dacuan
 * For Computer Programming 1 MO-IT101
 */

public class MotorPHEmployeeFilehandler {

    public static void main(String[] args) {
        String filePath ="src/Copy of MotorPH Employee Data.xlsx";
        readEmployeeDetails(filePath);
    }
    public static void readEmployeeDetails(String filePath) {
        try(FileInputStream fis = new FileInputStream(new File(filePath));
                Workbook workbook = new XSSFWorkbook(fis)) {
            
                Sheet sheet = workbook.getSheet("Employee Details");
                
                if(sheet == null) { 
                        System.out.println("Sheet not found.");
                        return;
                }
                
                for(Row row: sheet) {
                    if(row.getRowNum() == 0) continue; //Skip header row
                    
                    Cell empNumberCell = row.getCell(0);
                    Cell firstNameCell = row.getCell(2);
                    Cell lastNameCell = row.getCell(1);
                    Cell birthdayCell = row.getCell(3);
                    
                    if(empNumberCell == null || firstNameCell == null || lastNameCell == null || birthdayCell == null) {
                        continue;
                    }
                    
                    int empNumber = (int)empNumberCell.getNumericCellValue();
                    String firstName = firstNameCell.getStringCellValue();
                    String lastName = lastNameCell.getStringCellValue();
                    String birthday = birthdayCell.toString();
                    
                    System.out.println("Employee #: " + empNumber);
                    System.out.println("Name: " + firstName + " " + lastName);
                    System.out.println("Birthday: " + birthday);
                    System.out.println("------------------------------------");
                }
            } catch(IOException e) {
                System.out.println("Error reading file:" + e.getMessage());
            }
    }
}
