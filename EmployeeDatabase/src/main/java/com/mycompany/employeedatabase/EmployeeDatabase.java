/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.employeedatabase;

import java.util.Scanner;
        
/**
 *
 * @author Paul Jericho Dacuan
 * For Computer Programming 1 MO-IT101
 */

// This program will show the Data of MotorPH Employees.

public class EmployeeDatabase {

    public static void main(String[] args) {
         // EMPLOYEE DATABASE SHOWING Employee#, Lastname and Firstname and Lastly, Birthdate for each employee
        String header = "EMPLOYEE# || SURNAME/FIRST NAME || BIRTHDATE";
        System.out.println(header);
        
        String emp1 = "#10001 |";String Name1 = " Garcia, Manuel III |"; String BD1 = " 10/11/1983";
        System.out.println( emp1 + Name1 + BD1);
        
        String emp2 = "#10002 |";String Name2 = " Lim, Antonio |"; String BD2 = " 06/19/1988";
        System.out.println( emp2 + Name2 + BD2);
        
        String emp3 = "#10003 |";String Name3 = " Aquino, Bianca Sofia |"; String BD3 = " 08/04/1989";
        System.out.println( emp3 + Name3 + BD3);
        
        String emp4 = "#10004 |";String Name4 = " Reyes, Isabella |"; String BD4 = " 06/16/1994";
        System.out.println( emp4 + Name4 + BD4);
        
        String emp5 = "#10005 |";String Name5 = " Hernandez, Eduard |"; String BD5 = " 09/23/1989";
        System.out.println( emp5 + Name5 + BD5);
        
        String emp6 = "#10006 |";String Name6 = " Villanueva, Andrea Mae |"; String BD6 = " 02/14/1988";
        System.out.println( emp6 + Name6 + BD6);
        
        String emp7 = "#10007 |";String Name7 = " San Jose, Brad |"; String BD7 = " 03/15/1996";
        System.out.println( emp7 + Name7 + BD7);
        
        String emp8 = "#10008 |";String Name8 = " Romualdez, Alice |"; String BD8 = " 05/14/1992";
        System.out.println( emp8 + Name8 + BD8);
        
        String emp9 = "#10009 |";String Name9 = " Atienza, Rosie |"; String BD9 = " 09/24/1948";
        System.out.println( emp9 + Name9 + BD9);
        
        String emp10 = "#10010 |";String Name10 = " Alvaro, Roderick |"; String BD10 = " 03/30/1988";
        System.out.println( emp10 + Name10 + BD10);
        
        String emp11 = "#10011 |";String Name11 = " Salcedo, Anthony |"; String BD11 = " 09/14/1993";
        System.out.println( emp11 + Name11 + BD11);
        
        String emp12 = "#10012 |";String Name12 = " Lopez, Josie |"; String BD12 = " 01/14/1987";
        System.out.println( emp12 + Name12 + BD12);
        
        String emp13 = "#10013 |";String Name13 = " Farala, Martha |"; String BD13 = " 01/11/1942";
        System.out.println( emp13+ Name13 + BD13);
        
        String emp14 = "#10014 |";String Name14 = " Martinez, Leila |"; String BD14 = " 07/11/1970";
        System.out.println( emp14 + Name14 + BD14);
        
        String emp15 = "#10015 |";String Name15 = " Romualdez, Fredrick |"; String BD15 = " 03/10/1985";
        System.out.println( emp15 + Name15 + BD15);
        
        String emp16 = "#10016 |";String Name16 = " Mata, Christian |"; String BD16 = " 10/21/1987";
        System.out.println( emp16 + Name16 + BD16);
        
        String emp17 = "#10017 |";String Name17 = " De Leon, Selena |"; String BD17 = " 02/20/1975";
        System.out.println( emp17 + Name17 + BD17);
        
        String emp18 = "#10018 |";String Name18 = " San Jose, Allison |"; String BD18 = " 06/24/1986";
        System.out.println( emp18 + Name18 + BD18);
        
        String emp19 = "#10019 |";String Name19 = " Rosario, Cydney |"; String BD19 = " 10/06/1996";
        System.out.println( emp19 + Name19 + BD19);
        
        String emp20 = "#10020 |";String Name20 = " Bautista, Mark |"; String BD20 = " 02/12/1991";
        System.out.println( emp20 + Name20 + BD20);
        
        String emp21 = "#10021 |";String Name21 = " Lazaro, Darlene |"; String BD21 = " 11/25/1985";
        System.out.println( emp21 + Name21 + BD21);
        
        String emp22 = "#10022 |";String Name22 = " Delos Santos, Kolby |"; String BD22 = " 02/26/1980";
        System.out.println( emp22 + Name22 + BD22);
        
        String emp23 = "#10023 |";String Name23 = " Santos, Vella |"; String BD23 = " 12/31/1983";
        System.out.println( emp23 + Name23 + BD23);
        
        String emp24 = "#10024 |";String Name24 = " Del Rosario, Thomas |"; String BD24 = " 12/18/1978";
        System.out.println( emp24 + Name24 + BD24);
        
        String emp25 = "#10025 |";String Name25 = " Tolentino, Jacklyn |"; String BD25 = " 05/19/1984";
        System.out.println( emp25 + Name25 + BD25);
        
        String emp26 = "#10026 |";String Name26 = " Gutierrez, Percival |"; String BD26 = " 12/18/1970";
        System.out.println( emp26 + Name26 + BD26);
        
        String emp27 = "#10027 |";String Name27 = " Manalaysay, Garfield |"; String BD27 = " 08/28/1986";
        System.out.println( emp27 + Name27 + BD27);
        
        String emp28 = "#10028 |";String Name28 = " Villegas, Lizeth |"; String BD28 = " 12/12/1981";
        System.out.println( emp28 + Name28 + BD28);
        
        String emp29 = "#10029 |";String Name29 = " Ramos, Carol |"; String BD29 = " 08/20/1978";
        System.out.println( emp29 + Name29 + BD29);
        
        String emp30 = "#10030 |";String Name30 = " Maceda, Emelia |"; String BD30 = " 04/14/1973";
        System.out.println( emp30 + Name30 + BD30);
        
        String emp31 = "#10031 |";String Name31 = " Aguilar, Delia |"; String BD31 = " 01/27/1989";
        System.out.println( emp31 + Name31 + BD31);
        
        String emp32 = "#10032 |";String Name32 = " Castro, John Rafael |"; String BD32 = " 02/09/1992";
        System.out.println( emp32 + Name32 + BD32);
        
        String emp33 = "#10033 |";String Name33 = " Martinez, Carlos Ian |"; String BD33 = " 11/16/1990";
        System.out.println( emp33 + Name33 + BD33);
        
        String emp34 = "#10034 |";String Name34 = " Santos, Beatriz |"; String BD34 = " 08/07/1990";
        System.out.println( emp34 + Name34 + BD34);
        
      
    }
}
