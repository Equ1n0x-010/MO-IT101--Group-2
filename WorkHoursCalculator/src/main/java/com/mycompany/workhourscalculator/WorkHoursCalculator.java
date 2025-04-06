/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.workhourscalculator;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;


/**
 *
 * @author Paul Jericho Dacuan
 * For Computer Programming 1 MO-IT101
 */

// This is a basic Work Hour calculator that computes the total work hours of an employee. 

public class WorkHoursCalculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        
        System.out.println("Enter log-in time (HH:mm): ");
        LocalTime loginTime = LocalTime.parse(scanner.next(), formatter);
       
        System.out.println("Enter log-out time (HH:mm): ");
        LocalTime logoutTime = LocalTime.parse(scanner.next(), formatter);
        
        Duration duration = Duration.between(loginTime, logoutTime).minusHours(1);
        long hoursWorked = duration.toHours();
        long minutesWorked = duration.toMinutesPart();
        
        System.out.println(duration);
        System.out.println("Total hours worked (excluding lunch): " + hoursWorked + " Hours and " + minutesWorked + " Minutes");
        
        // The output will show the total hours work exluding lunch and in 24:00 format. 
        
    }
}
