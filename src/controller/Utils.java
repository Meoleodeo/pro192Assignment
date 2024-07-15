/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author ASUS
 */
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;
import model.Customer;

/**
 *
 * @author ASUS
 */
public class Utils {
    public static String getValue(String message) {
        System.out.print(message);
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public static int getValueInt(String message, int min, int max) {
        while (true) {
            String input = getValue(message).trim();
            try {
                int value = Integer.parseInt(input);
                if(min <= value && value <= max)
                    return value;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
    }


    public static double getValueDouble(String message, double min, double max) {
        while (true) {
            String input = getValue(message).trim();
            try {
                double value = Double.parseDouble(input);
                if(min <= value && value <= max)
                    return value;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a real number.");
            }
        }
    }

    public static LocalDate getLocalDate(String message) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate;
        while (true) {
            String input = getValue(message).trim();
            try {
                localDate = LocalDate.parse(input, formatter);
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid input. Please enter a valid date(dd/MM/yyyy).");
            }
        }
        return localDate;
    }


    public static String normalizeName(String name) {
        name = name.replaceAll("[^a-zA-Z ]", "");
        name = name.replaceAll("\\s+", " ");
        String[] words = name.split("\\s+");
        StringBuilder normalized = new StringBuilder();
        for (String word : words) {
            if (!word.isEmpty()) {
                String capitalizedWord = word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
                normalized.append(capitalizedWord).append(" ");
            }
        }
        return normalized.toString().trim();
    }

    
    public static boolean isValidPassword(String password) {
        if (password.length() < 8) {
            return false;
        }
        String upperCasePattern = ".*[A-Z].*";
        String digitPattern = ".*[0-9].*";
        String specialCharPattern = ".*[^a-zA-Z0-9].*";

        boolean hasUpperCase = password.matches(upperCasePattern);
        boolean hasDigit = password.matches(digitPattern);
        boolean hasSpecialChar = password.matches(specialCharPattern);

        return hasUpperCase && hasDigit && hasSpecialChar;
    }

    public static boolean isValidMail(String email) {
        String emailPattern = "^[a-zA-Z0-9._%+-]+@gmail\\.com$";
        return email != null && email.matches(emailPattern);
    }

    public static boolean isValidPhone(String phone) {
        String regex = "^(\\d{9}|\\d{10})$";
        return phone != null && phone.matches(regex);
    }
    
    public static boolean checkDuplicate(String id, ArrayList<Customer> list){
        boolean checkDuplicate = false;
        for(Customer cus : list){
            if(cus.getUsername().equals(id)){
                checkDuplicate = true;
                break;
            }
        }
        return checkDuplicate;
    }
    
    public static LocalDate isValidDate(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dateFormat;
        try{
            dateFormat = LocalDate.parse(date, formatter);
            return dateFormat;
        } catch (DateTimeParseException e){
            System.out.println("Invalid format. Please use correct format(dd/MM/yyyy).");
            return null;
        }
    }
}



