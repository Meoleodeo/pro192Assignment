/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import View.Menu;
import model.*;

/**
 *
 * @author NhatNS
 */
public class MainScreen extends Menu {

    private Bank bankManagement;

    static String title = "Main Screen";
    static String[] listOfChoices = {"Create Account", "Login", "Quit."};

    public MainScreen() {
        super(title, listOfChoices);
        bankManagement = new Bank();
    }

    @Override
    public void execute(int choice) {
        switch (choice) {
            case 1 -> {
                try {
                   createAccount(); 
                } catch (IllegalArgumentException e){
                    System.out.println(e.getMessage());
                }
            }
            case 2 -> {
                login();
            }
            default -> {
                System.out.println("Invalid choice.");
            }
        }
    }

    private void createAccount() {
        AdminApp admin = new AdminApp(bankManagement);
        admin.addCustomer();
    }

    private void login() {
        Menu loginMenu = new Menu("Select Mode", new String[]{"Admin", "Customer","Back to main screen."}) {
            @Override
            public void execute(int choice) {
                switch (choice) {
                    case 1 -> {
                        System.out.println("Admin login:");
                        String userNameAdmin = Utils.getValue("Enter admin username: ");
                        String passWordAdmin = Utils.getValue("Enter admin password: ");
                        if (bankManagement.getBankUser().equals(userNameAdmin) && bankManagement.getBankPassWord().equals(passWordAdmin)) {
                            AdminApp adminApp = new AdminApp(bankManagement);
                            adminApp.run();
                        } else {
                            System.out.println("Invalid admin username or password.");
                        }
                    }
                    case 2 -> {
                        if(bankManagement.getCustomerList().isEmpty()){
                            System.out.println("There is no customer in list.");
                            break;
                        }
                        String userNameCustomer = Utils.getValue("Enter customer username: ");
                        String passWordCustomer = Utils.getValue("Enter customer password: ");

                        Customer customer = bankManagement.getCustomerByUserName(userNameCustomer);
                        if (customer != null && customer.getPassword().equals(passWordCustomer)) {
                            CustomerApp customerApp = new CustomerApp(customer, bankManagement);
                            customerApp.run();
                        } else {
                            System.out.println("Invalid customer username or password.");
                        }
                    }
                    default ->
                        System.out.println("Invalid choice. Please enter again.");
                }
            }
        };
        loginMenu.run();
    }

    public static void main(String[] args) {
        MainScreen mainScreen = new MainScreen();
        mainScreen.run();
    }
}
