/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.salary.governmentdeductionscalculator;

import java.util.Scanner;

/**
 *
 * @author Paul Jericho Dacuan
 * For Computer Programming 1 MO-IT101
 */

/** 
 * This program will calculate the government mandated deductions from the gross monthly salary of an employee
 * It will then subtract the deductions and will compute the withholding tax
 * lastly after deducting the withholding tax, the net pay of employee will be provided. 
 */

public class GovernmentDeductionsCalculator {

    // Constants for contribution rates
    static final double PAG_IBIG_MAX_CONTRIBUTION = 100.0;
    static final double PHILHEALTH_RATE = 0.03;  // 3% of salary

    // Pag-IBIG contribution calculation
    public static double calculatePagIbigContribution(double salary) {
        double employeeContribution = 0.01 * salary; // 1% of salary for below ₱1,500
        double employerContribution = 0.02 * salary; // 2% of salary for below ₱1,500

        if (salary > 1500) {
            employeeContribution = 0.02 * salary; // 2% of salary for over ₱1,500
            employerContribution = 0.02 * salary; // 2% of salary for over ₱1,500
        }

        double totalContribution = employeeContribution + employerContribution;

        // Ensure that the contribution does not exceed the maximum
        return Math.min(totalContribution, PAG_IBIG_MAX_CONTRIBUTION);
    }

    // SSS contribution calculation based on the provided salary ranges
    public static double calculateSSSContribution(double salary) {
        if (salary <= 3250) {
            return 135.0;
        } else if (salary <= 3750) {
            return 157.5;
        } else if (salary <= 4250) {
            return 180.0;
        } else if (salary <= 4750) {
            return 202.5;
        } else if (salary <= 5250) {
            return 225.0;
        } else if (salary <= 5750) {
            return 247.5;
        } else if (salary <= 6250) {
            return 270.0;
        } else if (salary <= 6750) {
            return 292.5;
        } else if (salary <= 7250) {
            return 315.0;
        } else if (salary <= 7750) {
            return 337.5;
        } else if (salary <= 8250) {
            return 360.0;
        } else if (salary <= 8750) {
            return 382.5;
        } else if (salary <= 9250) {
            return 405.0;
        } else if (salary <= 9750) {
            return 427.5;
        } else if (salary <= 10250) {
            return 450.0;
        } else if (salary <= 10750) {
            return 472.5;
        } else if (salary <= 11250) {
            return 495.0;
        } else if (salary <= 11750) {
            return 517.5;
        } else if (salary <= 12250) {
            return 540.0;
        } else if (salary <= 12750) {
            return 562.5;
        } else if (salary <= 13250) {
            return 585.0;
        } else if (salary <= 13750) {
            return 607.5;
        } else if (salary <= 14250) {
            return 630.0;
        } else if (salary <= 14750) {
            return 652.5;
        } else if (salary <= 15250) {
            return 675.0;
        } else if (salary <= 15750) {
            return 697.5;
        } else if (salary <= 16250) {
            return 720.0;
        } else if (salary <= 16750) {
            return 742.5;
        } else if (salary <= 17250) {
            return 765.0;
        } else if (salary <= 17750) {
            return 787.5;
        } else if (salary <= 18250) {
            return 810.0;
        } else if (salary <= 18750) {
            return 832.5;
        } else if (salary <= 19250) {
            return 855.0;
        } else if (salary <= 19750) {
            return 877.5;
        } else if (salary <= 20250) {
            return 900.0;
        } else if (salary <= 20750) {
            return 922.5;
        } else if (salary <= 21250) {
            return 945.0;
        } else if (salary <= 21750) {
            return 967.5;
        } else if (salary <= 22250) {
            return 990.0;
        } else if (salary <= 22750) {
            return 1012.5;
        } else if (salary <= 23250) {
            return 1035.0;
        } else if (salary <= 23750) {
            return 1057.5;
        } else if (salary <= 24250) {
            return 1080.0;
        } else if (salary <= 24750) {
            return 1102.5;
        } else if (salary <= 25250) {
            return 1125.0;
        }
        return 1125.0;
    }

    // PhilHealth contribution calculation (shared between employee and employer)
    public static double calculatePhilHealthContribution(double salary) {
        double premium = salary * PHILHEALTH_RATE;
        return Math.min(premium, 1800.0);  // Maximum contribution is capped at 1,800
    }

    // Withholding Tax Computation (based on provided tax slabs)
    public static double computeWithholdingTax(double taxableIncome) {
        double tax = 0;

        if (taxableIncome <= 20832) {
            tax = 0; // No tax for income up to ₱20,832
        } else if (taxableIncome <= 33332) {
            tax = (taxableIncome - 20832) * 0.20; // 20% in excess of ₱20,833
        } else if (taxableIncome <= 66666) {
            tax = 2500 + (taxableIncome - 33332) * 0.25; // ₱2,500 plus 25% of excess over ₱33,333
        } else if (taxableIncome <= 166666) {
            tax = 10833 + (taxableIncome - 66667) * 0.30; // ₱10,833 plus 30% of excess over ₱66,667
        } else if (taxableIncome <= 666666) {
            tax = 40833.33 + (taxableIncome - 166667) * 0.32; // ₱40,833.33 plus 32% of excess over ₱166,667
        } else {
            tax = 200833.33 + (taxableIncome - 666667) * 0.35; // ₱200,833.33 plus 35% of excess over ₱666,667
        }

        return tax;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Get the employee's gross salary
        System.out.print("Enter the gross salary of the employee: ₱");
        double grossSalary = scanner.nextDouble();

        // Calculate the government contributions
        double pagIbigContribution = calculatePagIbigContribution(grossSalary);
        double sssContribution = calculateSSSContribution(grossSalary);
        double philhealthContribution = calculatePhilHealthContribution(grossSalary);

        // Calculate total deductions for government contributions
        double totalContributions = pagIbigContribution + sssContribution + philhealthContribution;

        // Compute taxable income (gross salary minus government contributions)
        double taxableIncome = grossSalary - totalContributions;

        // Calculate the withholding tax
        double withholdingTax = computeWithholdingTax(taxableIncome);

        // Calculate net income (taxable income minus withholding tax)
        double netIncome = taxableIncome - withholdingTax;

        // Display the breakdown
        System.out.println("\nGovernment Mandated Contributions:");
        System.out.println("Pag-IBIG Contribution: ₱" + String.format("%.2f", pagIbigContribution));
        System.out.println("SSS Contribution: ₱" + String.format("%.2f", sssContribution));
        System.out.println("PhilHealth Contribution: ₱" + String.format("%.2f", philhealthContribution));
        System.out.println("Total Government Contributions: ₱" + String.format("%.2f", totalContributions));

        System.out.println("\nWithholding Tax Computation:");
        System.out.println("Taxable Income: ₱" + String.format("%.2f", taxableIncome));
        System.out.println("Withholding Tax: ₱" + String.format("%.2f", withholdingTax));

        System.out.println("\nNet Income (Salary after all deductions): ₱" + String.format("%.2f", netIncome));

        scanner.close();
    }
}
