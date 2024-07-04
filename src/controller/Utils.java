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
import java.util.Scanner;

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

    public static int getValueNonNegativeInt(String message) {
        while (true) {
            String input = getValue(message).trim();
            try {
                int value = Integer.parseInt(input);
                if (value >= 0) {
                    return value;
                } else {
                    System.out.println("Invalid input. Please enter a non-negative number.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
    }

    public static int getValueInt(String message) {
        while (true) {
            String input = getValue(message).trim();
            try {
                int value = Integer.parseInt(input);
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
    }


    public static double getValueDouble(String message) {
        while (true) {
            String input = getValue(message).trim();
            try {
                double value = Double.parseDouble(input);
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a real number.");
            }
        }
    }

    public static double getValueNonNegativeDouble(String message) {
        while (true) {
            String input = getValue(message).trim();
            try {
                double value = Double.parseDouble(input);
                if(value >= 0){
                    return value;
                } else {
                    System.out.println("Invalid input. Please enter a positive number.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a real-number.");
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

    public static boolean getBooleanGender() {
        while (true) {
            String genderStr = Utils.getValue("Enter gender (true = Male, false = Female): ");
            if (genderStr.equalsIgnoreCase("true") || genderStr.equalsIgnoreCase("male")) {
                return true;
            } else if (genderStr.equalsIgnoreCase("false") || genderStr.equalsIgnoreCase("female")) {
                return false;
            } else {
                System.out.println("Invalid input. Please enter 'true' for Male or 'false' for Female.");
            }
        }
    }
}


