/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.employeedatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
        
/**
 *
 * @author Paul Jericho Dacuan
 * For Computer Programming 1 MO-IT101
 */

// This program will show the Data of MotorPH Employees and allow searching by employee number.

public class EmployeeDatabase {

    public static void main(String[] args) {
        // Create a map to store employee data
        Map<String, String[]> employeeData = new HashMap<>();
        
        // Populate employee data (employeeNumber -> [name, birthdate])
        employeeData.put("10001", new String[]{"Garcia, Manuel III", "10/11/1983"});
        employeeData.put("10002", new String[]{"Lim, Antonio", "06/19/1988"});
        employeeData.put("10003", new String[]{"Aquino, Bianca Sofia", "08/04/1989"});
        employeeData.put("10004", new String[]{"Reyes, Isabella", "06/16/1994"});
        employeeData.put("10005", new String[]{"Hernandez, Eduard", "09/23/1989"});
        employeeData.put("10006", new String[]{"Villanueva, Andrea Mae", "02/14/1988"});
        employeeData.put("10007", new String[]{"San Jose, Brad", "03/15/1996"});
        employeeData.put("10008", new String[]{"Romualdez, Alice", "05/14/1992"});
        employeeData.put("10009", new String[]{"Atienza, Rosie", "09/24/1948"});
        employeeData.put("10010", new String[]{"Alvaro, Roderick", "03/30/1988"});
        employeeData.put("10011", new String[]{"Salcedo, Anthony", "09/14/1993"});
        employeeData.put("10012", new String[]{"Lopez, Josie", "01/14/1987"});
        employeeData.put("10013", new String[]{"Farala, Martha", "01/11/1942"});
        employeeData.put("10014", new String[]{"Martinez, Leila", "07/11/1970"});
        employeeData.put("10015", new String[]{"Romualdez, Fredrick", "03/10/1985"});
        employeeData.put("10016", new String[]{"Mata, Christian", "10/21/1987"});
        employeeData.put("10017", new String[]{"De Leon, Selena", "02/20/1975"});
        employeeData.put("10018", new String[]{"San Jose, Allison", "06/24/1986"});
        employeeData.put("10019", new String[]{"Rosario, Cydney", "10/06/1996"});
        employeeData.put("10020", new String[]{"Bautista, Mark", "02/12/1991"});
        employeeData.put("10021", new String[]{"Lazaro, Darlene", "11/25/1985"});
        employeeData.put("10022", new String[]{"Delos Santos, Kolby", "02/26/1980"});
        employeeData.put("10023", new String[]{"Santos, Vella", "12/31/1983"});
        employeeData.put("10024", new String[]{"Del Rosario, Thomas", "12/18/1978"});
        employeeData.put("10025", new String[]{"Tolentino, Jacklyn", "05/19/1984"});
        employeeData.put("10026", new String[]{"Gutierrez, Percival", "12/18/1970"});
        employeeData.put("10027", new String[]{"Manalaysay, Garfield", "08/28/1986"});
        employeeData.put("10028", new String[]{"Villegas, Lizeth", "12/12/1981"});
        employeeData.put("10029", new String[]{"Ramos, Carol", "08/20/1978"});
        employeeData.put("10030", new String[]{"Maceda, Emelia", "04/14/1973"});
        employeeData.put("10031", new String[]{"Aguilar, Delia", "01/27/1989"});
        employeeData.put("10032", new String[]{"Castro, John Rafael", "02/09/1992"});
        employeeData.put("10033", new String[]{"Martinez, Carlos Ian", "11/16/1990"});
        employeeData.put("10034", new String[]{"Santos, Beatriz", "08/07/1990"});
        
        // Display menu and handle user input
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        
        while (choice != 3) {
            System.out.println("\n===== MOTOR PH EMPLOYEE DATABASE =====");
            System.out.println("1. View All Employees");
            System.out.println("2. Search Employee by Number");
            System.out.println("3. Exit");
            System.out.print("Enter your choice (1-3): ");
            
            // Handle potential non-integer input
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 3.");
                continue;
            }
            
            switch (choice) {
                case 1:
                    displayAllEmployees(employeeData);
                    break;
                case 2:
                    searchEmployee(scanner, employeeData);
                    break;
                case 3:
                    System.out.println("Thank you for using the Employee Database. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 3.");
            }
        }
        
        scanner.close();
    }
    
    // Method to display all employees
    private static void displayAllEmployees(Map<String, String[]> employeeData) {
        System.out.println("\nEMPLOYEE# || SURNAME/FIRST NAME || BIRTHDATE");
        System.out.println("------------------------------------------------");
        
        for (String empNum : employeeData.keySet()) {
            String[] data = employeeData.get(empNum);
            System.out.printf("#%s | %s | %s\n", empNum, data[0], data[1]);
        }
    }
    
    // Method to search for an employee by number
    private static void searchEmployee(Scanner scanner, Map<String, String[]> employeeData) {
        System.out.print("\nEnter employee number (without the # symbol): ");
        String empNum = scanner.nextLine();
        
        if (employeeData.containsKey(empNum)) {
            String[] data = employeeData.get(empNum);
            System.out.println("\n----- EMPLOYEE FOUND -----");
            System.out.println("Employee Number: #" + empNum);
            System.out.println("Name: " + data[0]);
            System.out.println("Birthdate: " + data[1]);
        } else {
            System.out.println("\nEmployee with number #" + empNum + " not found.");
        }
    }
}