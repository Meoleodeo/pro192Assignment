/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.*;
import View.Menu;
import java.time.LocalDate;

/**
 *
 * @author NhatNS
 */
public class CustomerApp extends Menu {

    private Customer customer;
    private Bank bankManagement;
    static String title = "Customer App";
    static String[] listOfChoices = {"Account Details", "Money Transfer", "Change Email", "Change Phone Number", "Change Password", "Withdraw Money", "Deposit.", "Quit"};

    public CustomerApp(Customer customer, Bank bankManagement) {
        super(title, listOfChoices);
        this.customer = customer;
        this.bankManagement = bankManagement;
        congratulateCustomersWithBirthdayToday();
    }

    @Override
    public void execute(int choice) {
        switch (choice) {
            case 1 -> {
                displayAccountDetails();
            }
            case 2 -> {
                transferMoney();
            }
            case 3 -> {
                changeEmail();
            }
            case 4 -> {
                changePhoneNumber();
            }
            case 5 -> {
                changePassword();
            }
            case 6 -> {
                withdrawMoney();
            }
            case 7 -> {
                depositMoney();
            }
            case 0 -> {
                bankManagement.saveCustomer("bank.txt");
            }
            default -> {
                System.out.println("Invalid choose");
            }
        }
    }

    private void displayAccountDetails() {
        System.out.println(String.format(
                "%-20s|%-20s|%-15s|%-20s",
                "CustomerID", "Name", "Account Number", "Account Balance"
        ));
        System.out.printf("%-20s|%-20s|%-15s|%-20.2f", customer.getGovernmentID(), customer.getFullName(), customer.getaccountNumber(),
                customer.getBalance());
    }

    private void transferMoney() {
        Customer recipient = null;
        String recipientAccountNumber = Utils.getValue("Enter recipient's account number: ");

        for (Customer cus : bankManagement.getCustomerList()) {
            if (cus.getaccountNumber().equals(recipientAccountNumber)) {
                recipient = cus;
                break;
            }
        }

        if (recipient != null) {
            try {
                double amount = Double.parseDouble(Utils.getValue("Enter amount to transfer: "));
                if (amount <= 0) {
                    System.out.println("Amount must be greater than zero.");
                    return;
                }

                if (customer.getBalance() >= amount) {
                    customer.balance(customer.getBalance() - amount);
                    recipient.balance(recipient.getBalance() + amount);

                    //add transactions to both sender and recipient
                    LocalDate currentDate = LocalDate.now();
                    Transaction senderTransaction = new Transaction(currentDate, "transfer", amount, "Transfer to " + recipientAccountNumber);
                    Transaction recipientTransaction = new Transaction(currentDate, "transfer", amount, "Received from " + customer.getaccountNumber());
                    customer.addTransaction(senderTransaction);
                    recipient.addTransaction(recipientTransaction);
                    System.out.println("Transfer successful.");
                } else {
                    System.out.println("Insufficient funds.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid amount entered. Please enter a numeric value.");
            }
        } else {
            System.out.println("Customer not found by the given account number.");
        }
    }

    private void withdrawMoney() {
        try {
            double amount = Double.parseDouble(Utils.getValue("Enter amount to withdraw: "));
            if (amount <= 0) {
                System.out.println("Amount must be greater than zero.");
                return;
            }

            // Check if the customer has sufficient funds
            if (customer.getBalance() >= amount) {
                // Withdraw money
                customer.balance(customer.getBalance() - amount);

                // Add transaction
                Transaction withdrawTransaction = new Transaction(LocalDate.now(), "withdrawal", amount, "Withdrawal");
                customer.addTransaction(withdrawTransaction);

                System.out.println("Withdrawal successful.");
            } else {
                System.out.println("Insufficient funds.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount entered. Please enter a numeric value.");
        }
    }

    private void depositMoney() {
        try {
            double amount = Double.parseDouble(Utils.getValue("Enter amount to deposit: "));
            if (amount <= 0) {
                System.out.println("Amount must be greater than zero.");
                return;
            }
            
            customer.setAccountFund(customer.getBalance() + amount);

            Transaction depositTransaction = new Transaction(LocalDate.now(), "deposit", amount, "Deposit");
            customer.addTransaction(depositTransaction);

            System.out.println("Deposit successfully.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount entered. Please enter a numeric value.");
        }
    }

    private void changeEmail() {
        String newEmail = Utils.getValue("Enter new email: ");
        customer.setMail(newEmail);
        System.out.println("Email updated successfully.");
    }

    private void changePhoneNumber() {
        String newPhoneNumber = Utils.getValue("Enter new phone number: ");
        customer.setPhone(newPhoneNumber);
        System.out.println("Phone number updated successfully.");
    }

    private void changePassword() {
        String currentPassword = Utils.getValue("Enter current password: ");
//        if (customer.getPassword().equals(customer.hashPassword(currentPassword))) {
        if (customer.getPassword().equals(currentPassword)) {
            String newPassword = Utils.getValue("Enter new password: ");
            customer.setPassword(newPassword);
            System.out.println("Password updated successfully.");
        } else {
            System.out.println("Incorrect current password.");
        }
    }

    public void congratulateCustomersWithBirthdayToday() {
        LocalDate today = LocalDate.now();
        LocalDate dob = customer.getDob();
        if (dob.getDayOfMonth() == today.getDayOfMonth() && dob.getMonth() == today.getMonth()) {
            System.out.println("Happy Birthday " + customer.getFullName() + "!");
        }
    }
}
