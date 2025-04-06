/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.salary.payrollsystemmotorph;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.*;

/**
 *
 * @author Paul Jericho Dacuan
 * For Computer Programming 1 MO-IT101
 */

/**
 * MotorPH Payroll System
 * This application calculates employee work hours, weekly hours, and monthly gross salary
 * based on Excel data from Copy of MotorPH Employee Data.xlsx.
 * Employee search functionality added
 */
public class PayrollSystemMotorPH {

    // Constants for work schedule
    private static final LocalTime SHIFT_START = LocalTime.of(8, 0);
    private static final LocalTime SHIFT_END = LocalTime.of(17, 0);
    private static final LocalTime GRACE_PERIOD_END = LocalTime.of(8, 10);
    private static final double LUNCH_DURATION_HOURS = 1.0;
    private static final int WORK_DAYS_PER_MONTH = 22; // Average work days in a month

    public static void main(String[] args) {
        // Path of the excel file that I use for reference
        String filePath = "Copy of MotorPH Employee Data.xlsx";
        
        try {
            // Check if file exists at the specified location
            File file = new File(filePath);
            if (!file.exists()) {
                System.err.println("File not found: " + file.getAbsolutePath());
                
                // Try alternative locations
                String[] possiblePaths = {
                    "src/Copy of MotorPH Employee Data.xlsx",
                    "src/main/resources/Copy of MotorPH Employee Data.xlsx",
                    "./Copy of MotorPH Employee Data.xlsx",
                    "../Copy of MotorPH Employee Data.xlsx"
                };
                
                boolean fileFound = false;
                for (String path : possiblePaths) {
                    File altFile = new File(path);
                    if (altFile.exists()) {
                        filePath = path;
                        fileFound = true;
                        System.out.println("Found file at: " + altFile.getAbsolutePath());
                        break;
                    }
                }
                
                if (!fileFound) {
                    System.err.println("Please place 'Copy of MotorPH Employee Data.xlsx' in one of these locations:");
                    for (String path : possiblePaths) {
                        System.err.println("  - " + new File(path).getAbsolutePath());
                    }
                    System.err.println("Or update the filePath variable with the correct location.");
                    return;
                }
            } else {
                System.out.println("Using file at: " + file.getAbsolutePath());
            }
            
            // Get employee details including hourly rate
            Map<String, EmployeeDetails> employeeData = getEmployeeDetails(filePath);
            
            // Calculate work hours for each employee
            Map<String, Map<String, WorkHours>> employeeWorkData = calculateWorkHours(filePath);
            
            // Start user interface for employee search
            startEmployeeSearch(employeeData, employeeWorkData);
            
        } catch (Exception e) {
            System.err.println("Error in payroll processing: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Starts the interactive employee search interface
     * @param employeeData Map of employee details
     * @param employeeWorkData Map of employee work hours
     */
    public static void startEmployeeSearch(Map<String, EmployeeDetails> employeeData, 
                                         Map<String, Map<String, WorkHours>> employeeWorkData) {
        Scanner scanner = new Scanner(System.in);
        boolean continueSearch = true;
        
        System.out.println("\n===== MOTORPH PAYROLL SYSTEM =====");
        
        while (continueSearch) {
            System.out.println("\nOptions:");
            System.out.println("1. Search for employee by Employee #");
            System.out.println("2. List all available Employee #s");
            System.out.println("3. Exit");
            System.out.print("\nEnter your choice (1-3): ");
            
            String choice = scanner.nextLine().trim();
            
            switch (choice) {
                case "1":
                    searchEmployee(scanner, employeeData, employeeWorkData);
                    break;
                case "2":
                    listEmployeeNumbers(employeeData);
                    break;
                case "3":
                    continueSearch = false;
                    System.out.println("Exiting MotorPH Payroll System. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter 1, 2, or 3.");
            }
        }
        
        scanner.close();
    }
    
    /**
     * Lists all available employee numbers
     * @param employeeData Map of employee details
     */
    private static void listEmployeeNumbers(Map<String, EmployeeDetails> employeeData) {
        System.out.println("\n=== Available Employee Numbers ===");
        System.out.println("----------------------------------------");
        System.out.printf("%-10s %-30s\n", "Emp #", "Name");
        System.out.println("----------------------------------------");
        
        for (Map.Entry<String, EmployeeDetails> entry : employeeData.entrySet()) {
            System.out.printf("%-10s %-30s\n", 
                    entry.getKey(), 
                    entry.getValue().getFullName());
        }
        
        System.out.println("----------------------------------------");
    }
    
    /**
     * Searches for an employee by employee number and displays their payroll information
     * @param scanner Scanner for user input
     * @param employeeData Map of employee details
     * @param employeeWorkData Map of employee work hours
     */
    private static void searchEmployee(Scanner scanner, 
                                     Map<String, EmployeeDetails> employeeData, 
                                     Map<String, Map<String, WorkHours>> employeeWorkData) {
        System.out.print("\nEnter Employee # to search: ");
        String empNumber = scanner.nextLine().trim();
        
        if (employeeData.containsKey(empNumber)) {
            displayEmployeePayrollReport(empNumber, employeeData, employeeWorkData);
            
            // Ask if user wants to see weekly breakdown
            System.out.print("\nDo you want to see the detailed weekly breakdown? (y/n): ");
            String showDetails = scanner.nextLine().trim().toLowerCase();
            
            if (showDetails.equals("y") || showDetails.equals("yes")) {
                displayWeeklyBreakdown(empNumber, employeeData, employeeWorkData);
            }
        } else {
            System.out.println("Employee # " + empNumber + " not found!");
        }
    }
    
    /**
     * Displays a summary payroll report for a specific employee
     * @param empNumber Employee number
     * @param employeeData Map of employee details
     * @param employeeWorkData Map of employee work hours
     */
    public static void displayEmployeePayrollReport(String empNumber, 
                                                 Map<String, EmployeeDetails> employeeData, 
                                                 Map<String, Map<String, WorkHours>> employeeWorkData) {
        EmployeeDetails employee = employeeData.get(empNumber);
        
        System.out.println("\n===== EMPLOYEE PAYROLL REPORT =====");
        System.out.println("Employee # " + empNumber);
        System.out.println("Name: " + employee.getFullName());
        System.out.println("Birthday: " + employee.getBirthday());
        System.out.println("Hourly Rate: ₱" + String.format("%.2f", employee.getHourlyRate()));
        
        if (employeeWorkData.containsKey(empNumber)) {
            Map<String, WorkHours> weeksData = employeeWorkData.get(empNumber);
            double totalMonthlyHours = 0;
            double totalRegularHours = 0;
            double totalOvertimeHours = 0;
            
            // Calculate totals
            for (WorkHours weekData : weeksData.values()) {
                totalRegularHours += weekData.getRegularHours();
                totalOvertimeHours += weekData.getOvertimeHours();
                totalMonthlyHours += weekData.getTotalHours();
            }
            
            System.out.println("\nMonthly Hours Summary:");
            System.out.println("-----------------------------------------------------------");
            System.out.printf("Total Regular Hours: %.2f hours\n", totalRegularHours);
            System.out.printf("Total Overtime Hours: %.2f hours\n", totalOvertimeHours);
            System.out.printf("Total Monthly Hours: %.2f hours\n", totalMonthlyHours);
            
            // Calculate gross monthly salary
            double regularPay = totalRegularHours * employee.getHourlyRate();
            double overtimePay = totalOvertimeHours * employee.getHourlyRate() * 1.5; // 1.5x for overtime
            double grossMonthlySalary = regularPay + overtimePay;
            
            // Calculate estimated monthly salary (based on standard work month)
            double estimatedMonthlySalary = WORK_DAYS_PER_MONTH * 8 * employee.getHourlyRate();
            
            System.out.println("\nSalary Computation:");
            System.out.println("-----------------------------------------------------------");
            System.out.printf("Regular Pay (%.2f hrs @ ₱%.2f/hr): ₱%.2f\n", 
                    totalRegularHours, employee.getHourlyRate(), regularPay);
            System.out.printf("Overtime Pay (%.2f hrs @ ₱%.2f/hr x 1.5): ₱%.2f\n", 
                    totalOvertimeHours, employee.getHourlyRate(), overtimePay);
            System.out.println("-----------------------------------------------------------");
            System.out.printf("Gross Monthly Salary: ₱%.2f\n", grossMonthlySalary);
            System.out.printf("Standard Monthly Salary (for %d days): ₱%.2f\n", 
                    WORK_DAYS_PER_MONTH, estimatedMonthlySalary);
        } else {
            System.out.println("\nNo attendance records found for this employee.");
        }
        
        System.out.println("===========================================================");
    }
    
    /**
     * Displays detailed weekly breakdown for a specific employee
     * @param empNumber Employee number
     * @param employeeData Map of employee details
     * @param employeeWorkData Map of employee work hours
     */
    public static void displayWeeklyBreakdown(String empNumber, 
                                           Map<String, EmployeeDetails> employeeData, 
                                           Map<String, Map<String, WorkHours>> employeeWorkData) {
        EmployeeDetails employee = employeeData.get(empNumber);
        
        if (employeeWorkData.containsKey(empNumber)) {
            Map<String, WorkHours> weeksData = employeeWorkData.get(empNumber);
            
            System.out.println("\n===== WEEKLY BREAKDOWN FOR " + employee.getFullName() + " =====");
            System.out.println("-----------------------------------------------------------");
            System.out.printf("%-30s %-10s %-10s %-10s %-12s\n", 
                    "Week", "Regular", "Overtime", "Total", "Weekly Pay");
            System.out.println("-----------------------------------------------------------");
            
            double totalMonthlyPay = 0;
            
            // Sort weeks chronologically
            List<String> sortedWeekIds = new ArrayList<>(weeksData.keySet());
            Collections.sort(sortedWeekIds);
            
            for (String weekId : sortedWeekIds) {
                WorkHours weekData = weeksData.get(weekId);
                
                double weeklyRegular = weekData.getRegularHours();
                double weeklyOvertime = weekData.getOvertimeHours();
                double weeklyTotal = weeklyRegular + weeklyOvertime;
                
                double regularPay = weeklyRegular * employee.getHourlyRate();
                double overtimePay = weeklyOvertime * employee.getHourlyRate() * 1.5;
                double weeklyPay = regularPay + overtimePay;
                totalMonthlyPay += weeklyPay;
                
                System.out.printf("%-30s %-10.2f %-10.2f %-10.2f ₱%-12.2f\n", 
                        weekData.getWeekRange(), weeklyRegular, weeklyOvertime, weeklyTotal, weeklyPay);
            }
            
            System.out.println("-----------------------------------------------------------");
            double totalRegularHours = weeksData.values().stream().mapToDouble(WorkHours::getRegularHours).sum();
            double totalOvertimeHours = weeksData.values().stream().mapToDouble(WorkHours::getOvertimeHours).sum();
            double totalHours = totalRegularHours + totalOvertimeHours;
            
            System.out.printf("%-30s %-10.2f %-10.2f %-10.2f ₱%-12.2f\n", 
                    "Monthly Total:", totalRegularHours, totalOvertimeHours, totalHours, totalMonthlyPay);
            
        } else {
            System.out.println("\nNo weekly data available for this employee.");
        }
        
        System.out.println("===========================================================");
    }
    
    /**
     * Retrieves employee details from the Excel file
     * @param filePath Path to the Excel file
     * @return Map of employee details keyed by employee number
     */
    public static Map<String, EmployeeDetails> getEmployeeDetails(String filePath) {
        Map<String, EmployeeDetails> employeeData = new LinkedHashMap<>();
        
        try (FileInputStream fis = new FileInputStream(new File(filePath));
             Workbook workbook = new XSSFWorkbook(fis)) {
            
            Sheet sheet = workbook.getSheet("Employee Details");
            if (sheet == null) {
                System.err.println("Employee Details sheet not found!");
                return employeeData;
            }
            
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Skip header row
                
                Cell empNumberCell = row.getCell(0);
                Cell lastNameCell = row.getCell(1);
                Cell firstNameCell = row.getCell(2);
                Cell birthdayCell = row.getCell(3);
                Cell hourlyRateCell = row.getCell(19); // Column T (19) for hourly rate
                
                if (empNumberCell == null) continue;
                
                String empNumber = getCellValueAsString(empNumberCell);
                String lastName = getCellValueAsString(lastNameCell);
                String firstName = getCellValueAsString(firstNameCell);
                String birthday = formatDateCell(birthdayCell);
                double hourlyRate = hourlyRateCell != null ? hourlyRateCell.getNumericCellValue() : 0.0;
                
                employeeData.put(empNumber, new EmployeeDetails(empNumber, firstName, lastName, birthday, hourlyRate));
            }
            
            System.out.println("Successfully loaded " + employeeData.size() + " employees from the Excel file.");
        } catch (IOException e) {
            System.err.println("Error reading Employee Details: " + e.getMessage());
        }
        
        return employeeData;
    }
    
    /**
     * Calculates work hours for each employee
     * @param filePath Path to the Excel file
     * @return Map of employee work hours keyed by employee number and week
     */
    public static Map<String, Map<String, WorkHours>> calculateWorkHours(String filePath) {
        Map<String, Map<String, WorkHours>> employeeWorkData = new HashMap<>();
        
        try (FileInputStream fis = new FileInputStream(new File(filePath));
             Workbook workbook = new XSSFWorkbook(fis)) {
            
            Sheet sheet = workbook.getSheet("Attendance Record");
            if (sheet == null) {
                System.err.println("Attendance Record sheet not found!");
                return employeeWorkData;
            }
            
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy", Locale.ENGLISH);
            int recordCount = 0;
            
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Skip header row
                
                Cell empNumberCell = row.getCell(0);
                Cell dateCell = row.getCell(3);
                Cell timeInCell = row.getCell(4);
                Cell timeOutCell = row.getCell(5);
                
                if (empNumberCell == null || dateCell == null || timeInCell == null || timeOutCell == null) {
                    continue;
                }
                
                String empNumber = getCellValueAsString(empNumberCell);
                LocalDate date = getCellDateValue(dateCell);
                
                // Get week start and end for grouping
                LocalDate weekStart = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
                LocalDate weekEnd = weekStart.plusDays(4); // Friday
                int weekNumber = date.get(WeekFields.of(Locale.getDefault()).weekOfYear());
                int year = date.getYear();
                String weekId = year + "-W" + String.format("%02d", weekNumber);
                String weekRange = "Week of " + weekStart.format(dateFormatter) + " to " + weekEnd.format(dateFormatter);
                
                // Calculate work hours for the day
                DailyWorkHours dailyHours = calculateDailyWorkHours(timeInCell, timeOutCell);
                
                // Add to employee's weekly data
                employeeWorkData.putIfAbsent(empNumber, new LinkedHashMap<>());
                employeeWorkData.get(empNumber).putIfAbsent(weekId, new WorkHours(weekRange));
                WorkHours weeklyData = employeeWorkData.get(empNumber).get(weekId);
                
                weeklyData.addRegularHours(dailyHours.getRegularHours());
                weeklyData.addOvertimeHours(dailyHours.getOvertimeHours());
                recordCount++;
            }
            
            System.out.println("Successfully processed " + recordCount + " attendance records.");
        } catch (IOException e) {
            System.err.println("Error reading Attendance Record: " + e.getMessage());
        }
        
        return employeeWorkData;
    }
    
    /**
     * Calculates daily work hours accounting for grace period, lunch, and overtime
     * @param timeInCell Cell containing time in
     * @param timeOutCell Cell containing time out
     * @return DailyWorkHours object with regular and overtime hours
     */
    private static DailyWorkHours calculateDailyWorkHours(Cell timeInCell, Cell timeOutCell) {
        try {
            LocalTime timeIn = getTimeFromCell(timeInCell);
            LocalTime timeOut = getTimeFromCell(timeOutCell);
            
            // Apply grace period (if logged in between 8:00 and 8:10, count as 8:00)
            LocalTime effectiveTimeIn = (timeIn.isAfter(SHIFT_START) && timeIn.isBefore(GRACE_PERIOD_END)) 
                                      ? SHIFT_START 
                                      : timeIn;
            
            // Calculate hours worked
            double totalHoursWorked = ChronoUnit.MINUTES.between(effectiveTimeIn, timeOut) / 60.0;
            
            // Subtract lunch break (1 hour) only if the total hours worked is more than 5 hours
            if (totalHoursWorked > 5.0) {
                totalHoursWorked -= LUNCH_DURATION_HOURS;
            }
            
            // Calculate regular hours and overtime
            double regularHours = 0.0;
            double overtimeHours = 0.0;
            
            // Calculate regular hours (capped at shift duration)
            double shiftDuration = ChronoUnit.MINUTES.between(SHIFT_START, SHIFT_END) / 60.0 - LUNCH_DURATION_HOURS;
            
            if (timeIn.isAfter(SHIFT_START)) {
                // Late arrival - reduce regular hours
                regularHours = Math.min(shiftDuration - ChronoUnit.MINUTES.between(SHIFT_START, effectiveTimeIn) / 60.0, totalHoursWorked);
            } else {
                regularHours = Math.min(shiftDuration, totalHoursWorked);
            }
            
            // If total hours worked exceeds regular hours, the difference is overtime
            if (totalHoursWorked > regularHours) {
                overtimeHours = totalHoursWorked - regularHours;
            }
            
            // Ensure no negative values
            regularHours = Math.max(0, regularHours);
            overtimeHours = Math.max(0, overtimeHours);
            
            return new DailyWorkHours(regularHours, overtimeHours);
        } catch (Exception e) {
            System.err.println("Error calculating work hours: " + e.getMessage());
            return new DailyWorkHours(0, 0);
        }
    }
    
    /**
     * Gets time value from a cell
     * @param cell Cell containing time
     * @return LocalTime object
     * @throws Exception if cell format is invalid
     */
    private static LocalTime getTimeFromCell(Cell cell) throws Exception {
        if (cell.getCellType() == CellType.NUMERIC) {
            // Excel stores times as fraction of 24 hours
            Date date = DateUtil.getJavaDate(cell.getNumericCellValue());
            return date.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
        } else {
            // Parse string time format
            String timeStr = cell.getStringCellValue();
            
            try {
                // Try common time formats
                String[] formats = {"HH:mm:ss", "h:mm:ss a", "HH:mm", "h:mm a"};
                
                for (String format : formats) {
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat(format);
                        Date date = sdf.parse(timeStr);
                        return LocalTime.of(date.getHours(), date.getMinutes(), date.getSeconds());
                    } catch (ParseException e) {
                        // Try next format
                    }
                }
                
                // If all formats fail, try a custom approach
                String[] timeParts = timeStr.split(":");
                int hour = Integer.parseInt(timeParts[0]);
                int minute = Integer.parseInt(timeParts[1]);
                int second = timeParts.length > 2 ? Integer.parseInt(timeParts[2]) : 0;
                
                // Check for AM/PM
                if (timeStr.toUpperCase().contains("PM") && hour < 12) {
                    hour += 12;
                } else if (timeStr.toUpperCase().contains("AM") && hour == 12) {
                    hour = 0;
                }
                
                return LocalTime.of(hour, minute, second);
                
            } catch (Exception e) {
                System.err.println("Failed to parse time: " + timeStr + " - " + e.getMessage());
                throw e;
            }
        }
    }
    
    /**
     * Gets date value from a cell
     * @param cell Cell containing date
     * @return LocalDate object
     */
    private static LocalDate getCellDateValue(Cell cell) {
        if (cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
            return cell.getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        } else {
            try {
                String dateStr = cell.getStringCellValue();
                
                // Try different date formats
                String[] formats = {
                    "dd-MMM-yyyy", 
                    "M/d/yyyy", 
                    "MM/dd/yyyy", 
                    "yyyy-MM-dd"
                };
                
                for (String format : formats) {
                    try {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format, Locale.ENGLISH);
                        return LocalDate.parse(dateStr, formatter);
                    } catch (Exception e) {
                        // Try next format
                    }
                }
                
                System.err.println("Could not parse date: " + dateStr);
                return LocalDate.now(); // Fallback
            } catch (Exception e) {
                System.err.println("Error parsing date: " + e.getMessage());
                return LocalDate.now(); // Fallback
            }
        }
    }
    
    /**
     * Formats a date cell as string
     * @param cell Cell containing date
     * @return Formatted date string
     */
    private static String formatDateCell(Cell cell) {
        if (cell == null) return "";
        
        if (cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            return sdf.format(cell.getDateCellValue());
        } else {
            return cell.getStringCellValue();
        }
    }
    
    /**
     * Gets cell value as string regardless of cell type
     * @param cell Cell to get value from
     * @return String value of cell
     */
    private static String getCellValueAsString(Cell cell) {
        if (cell == null) return "";
        
        switch (cell.getCellType()) {
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                    return sdf.format(cell.getDateCellValue());
                }
                // If it's a whole number, remove decimal part
                double value = cell.getNumericCellValue();
                if (value == Math.floor(value)) {
                    return String.valueOf((int) value);
                }
                return String.valueOf(value);
            case STRING:
                return cell.getStringCellValue();
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                try {
                    return String.valueOf(cell.getNumericCellValue());
                } catch (Exception e) {
                    return cell.getStringCellValue();
                }
            default:
                return "";
        }
    }
    
    /**
     * Class to store employee details
     */
    public static class EmployeeDetails {
        private String empNumber;
        private String firstName;
        private String lastName;
        private String birthday;
        private double hourlyRate;
        
        public EmployeeDetails(String empNumber, String firstName, String lastName, String birthday, double hourlyRate) {
            this.empNumber = empNumber;
            this.firstName = firstName;
            this.lastName = lastName;
            this.birthday = birthday;
            this.hourlyRate = hourlyRate;
        }
        
        public String getEmpNumber() {
            return empNumber;
        }
        
        public String getFirstName() {
            return firstName;
        }
        
        public String getLastName() {
            return lastName;
        }
        
        public String getFullName() {
            return firstName + " " + lastName;
        }
        
        public String getBirthday() {
            return birthday;
        }
        
        public double getHourlyRate() {
            return hourlyRate;
        }
    }
    
    /**
     * Class to store work hours for a specific week
     */
    public static class WorkHours {
        private String weekRange;
        private double regularHours;
        private double overtimeHours;
        
        public WorkHours(String weekRange) {
            this.weekRange = weekRange;
            this.regularHours = 0;
            this.overtimeHours = 0;
        }
        
        public String getWeekRange() {
            return weekRange;
        }
        
        public double getRegularHours() {
            return regularHours;
        }
        
        public double getOvertimeHours() {
            return overtimeHours;
        }
        
        public void addRegularHours(double hours) {
            this.regularHours += hours;
        }
        
        public void addOvertimeHours(double hours) {
            this.overtimeHours += hours;
        }
        
        public double getTotalHours() {
            return regularHours + overtimeHours;
        }
    }
    
    /**
     * Class to store daily work hours breakdown
     */
    public static class DailyWorkHours {
        private double regularHours;
        private double overtimeHours;
        
        public DailyWorkHours(double regularHours, double overtimeHours) {
            this.regularHours = regularHours;
            this.overtimeHours = overtimeHours;
        }
        
        public double getRegularHours() {
            return regularHours;
        }
        
        public double getOvertimeHours() {
            return overtimeHours;
        }
    }
    
}