/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import View.Menu;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;
import model.*;

/**
 *
 * @author NhatNS
 */
public class AdminApp extends Menu {

    private Bank bankManagement;

    static String title = "Admin App";
    static String[] listOfChoices = {"View All Customers", "Add Customer", "Remove Customer", "Search Customer", "View Transaction History", "Change password.", "Quit."};

    public AdminApp(Bank bankManagement) {
        super(title, listOfChoices);
        this.bankManagement = bankManagement;
    }

    @Override
    public void execute(int choice) {
        switch (choice) {
            case 1 ->
                displayAllCustomer();
            case 2 -> {
                try {
                    addCustomer();
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }
            case 3 ->
                removeCustomer();
            case 4 -> searchCustomer();
            case 5 -> viewTransaction();
            case 6 -> changePassword();
            case 0 -> bankManagement.saveCustomer("input.txt");
            default ->
                System.out.println("invalid choices");
        }
    }

    private void displayAllCustomer() {
        bankManagement.displayCustomeList(bankManagement.getCustomerList());
    }

    public void addCustomer() throws IllegalArgumentException {
        String userName = getUniqueUserName();
        String passWord;
        while (true) {
            passWord = Utils.getValue("Enter pass-word (password include 8 digit, at least 1 Upper-case, number and special character): ");
            if (Utils.isValidPassword(passWord)) {
                break;
            } else {
                System.out.println("Invalid format. Please Enter again.");
            }
        }
        String name = Utils.getValue("Enter full-name: ");
        LocalDate dobs = Utils.getLocalDate("Enter date of birth of customer with format(dd/MM/yyyy): ");

        String phone;
        while (true) {
            phone = Utils.getValue("Enter phone(the phone must be 9 or 10 digit): ");
            if (Utils.isValidPhone(phone)) {
                break;
            } else {
                System.out.println("Invalid format. Please enter again.");
            }
        }
        String mail;
        while (true) {
            mail = Utils.getValue("Enter mail(@gmail.com: ");
            if (Utils.isValidMail(mail)) {
                break;
            } else {
                System.out.println("Invalid format. Please enter again.");
            }
        }
        String id = getUniqueId();
        String accountNumber = getUniqueNumberAccount();
        

        bankManagement.addNewCustomer(new Customer(userName, passWord, name, dobs, phone, mail, id, accountNumber, 0.0, new ArrayList<>()));
    }

    public String getUniqueUserName() {
        String userName;
        while (true) {
            userName = Utils.getValue("Enter user name: ");
            boolean isUnique = true;

            for (Customer cus : bankManagement.getCustomerList()) {
                if (cus.getUsername().equals(userName)) {
                    isUnique = false;
                    break;
                }
            }

            if (isUnique) {
                break;
            } else {
                System.out.println("The user name must be unique.");
            }
        }
        return userName;
    }

    public String getUniqueId() {
        String id;
        while (true) {
            id = Utils.getValue("Enter id: ");
            boolean isUnique = true;

            for (Customer cus : bankManagement.getCustomerList()) {
                if (cus.getGovernmentID().equals(id)) {
                    isUnique = false;
                    break;
                }
            }

            if (isUnique) {
                break;
            } else {
                System.out.println("The id must be unique.");
            }
        }
        return id;
    }

    public String getUniqueNumberAccount() {
        String numberAccount;
        while (true) {
            numberAccount = Utils.getValue("Enter number Account: ");
            boolean isUnique = true;

            for (Customer cus : bankManagement.getCustomerList()) {
                if (cus.getaccountNumber().equals(numberAccount)) {
                    isUnique = false;
                    break;
                }
            }

            if (isUnique) {
                break;
            } else {
                System.out.println("The number account must be unique.");
            }
        }
        return numberAccount;
    }

    private void removeCustomer() {
        String userName = Utils.getValue("Enter customer username to remove: ");
        bankManagement.deleteCustomer(userName);
    }

    private void searchCustomer() {
        String[] searchMenuchoice = {"Search by user-name", "Search by name", "Search by ID", "Back to main menu"};
        Menu searchMenu = new Menu("Search Menu", searchMenuchoice) {
            @Override
            public void execute(int choice) {
                switch (choice) {
                    case 1:
                        String userName = Utils.getValue("Enter user-name: ");
                        bankManagement.displayCustomeList(bankManagement.searchCustomerByUserName(userName));
                        break;
                    case 2:
                        String name = Utils.getValue("Enter name: ");
                        bankManagement.displayCustomeList(bankManagement.searchCustomerByName(name));
                        break;
                    case 3:
                        String id = Utils.getValue("Enter id: ");
                        bankManagement.displayCustomeList(bankManagement.searchCustomerByCCCD(id));
                        break;
                    case 0:
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter option correctly.");
                        break;
                }
            }
        };
        searchMenu.run();
    }

    public void viewTransaction() {
        String numberAccount = Utils.getValue("Enter account-number: ");
        Customer check = null;
        for (Customer cus : bankManagement.getCustomerList()) {
            if (cus.getaccountNumber().equals(numberAccount)) {
                check = cus;
                break;
            }
        }
        if (check != null) {
            System.out.printf("%-15s|%-10s|%-15s|%-20s", "DATE", "TYPE", "AMOUNT", "DETAILS");
            System.out.println(check.getTransaction());
        } else {
            System.out.println("Customer not found with the given number account.");
        }
    }

    public void changePassword() {
        String currentPassword = Utils.getValue("Enter the current password: ");
        String newPassword = Utils.getValue("Enter the new password: ");
        if (Utils.isValidPassword(newPassword)) {
            bankManagement.setBankPassWord(currentPassword, newPassword);
        } else {
            System.out.println("Invalid format of valid password.");
        }
    }
}
