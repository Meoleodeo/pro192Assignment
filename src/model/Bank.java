package model;

import controller.Utils;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;

public class Bank {
    
    private final ArrayList<Customer> customerList;
    private String bankUser;
    private String bankPassWord;
    
    public Bank() {
        this.customerList = new ArrayList<>();
        bankUser = "Group9";
        bankPassWord = "se18d06";
        loadCustomersFromFile("bank.txt");
    }
    
    public ArrayList<Customer> getCustomerList() {
        return customerList;
    }
    
    private void loadCustomersFromFile(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(":");
                if (data.length == 9) {
                    String username = data[0].trim();
                    String password = data[1].trim();
                    String fullName = data[2].trim();
                    LocalDate dob = Utils.isValidDate(data[3].trim());
                    String phone = data[4].trim();
                    String mail = data[5].trim();
                    String id = data[6].trim();
                    String numberAccount = data[7].trim();
                    double balanceStr = Double.parseDouble(data[8].trim());
                    if (!Utils.checkDuplicate(id, customerList) && Utils.isValidMail(mail) && Utils.isValidPassword(password) && Utils.isValidPhone(phone) && dob != null) {
                        Customer customer = new Customer(username, password, fullName, dob, phone, mail, id, numberAccount, balanceStr, new ArrayList<>());
                        customerList.add(customer);
                    }
                } else {
                    System.out.println("Invalid line format: " + line);
                }
            }
        } catch (Exception e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
    
    public String getBankUser() {
        return bankUser;
    }
    
    public void setBankUser(String bankUser) {
        this.bankUser = bankUser;
    }
    
    public String getBankPassWord() {
        return bankPassWord;
    }
    
    public void setBankPassWord(String oldPassword, String newPassWord) {
        if (this.bankPassWord.equals(oldPassword)) {
            this.bankPassWord = newPassWord;
            System.out.println("Updated password successfully.");
        } else {
            System.out.println("Incorrect current password provided.");
        }
    }
    
    public boolean authenticateCustomer(String username, String password) {
        Customer customer = getCustomerByUserName(username);
        if (customer != null) {
            return password.equals(customer.getPassword());
        }
        return false;
    }
    
    public void addNewCustomer(Customer newCustomer) {
        if (!Utils.checkDuplicate(newCustomer.getGovernmentID(), customerList)) {
            customerList.add(newCustomer);
            System.out.println("Added successfully.");
        } else {
            System.out.println("the user-name of customer must be unique.");
        }
    }
    
    public void deleteCustomer(String userName) {
        boolean check = customerList.removeIf(customer -> customer.getUsername().equals(userName));
        if (check) {
            System.out.println("Deleted successfully.");
        } else {
            System.out.println("The customer not found with the given user name.");
        }
    }
    
    public void displayCustomeList(ArrayList<Customer> customerList) {
        customerList.sort(Comparator.comparing(Customer::getFullName));
        System.out.printf("|%-17s|%-20s|%-15s|%-15s|%-25s|%-11s|\n",
                "User-Name", "Full-Name", "Account-Number", "Identification", "Mail", "Phone");
        for (Customer cus : customerList) {
            System.out.println(cus);
        }
    }
    
    public ArrayList<Customer> searchCustomerByUserName(String userName) {
        ArrayList<Customer> UserNameList = new ArrayList<>();
        for (Customer cus : customerList) {
            if (cus.getUsername().equals(userName)) {
                UserNameList.add(cus);
            }
        }
        return UserNameList;
    }
    
    public ArrayList<Customer> searchCustomerByCCCD(String cccd) {
        ArrayList<Customer> cccdList = new ArrayList<>();
        for (Customer cus : customerList) {
            if (cus.getGovernmentID().equals(cccd)) {
                cccdList.add(cus);
            }
        }
        return cccdList;
    }
    
    public ArrayList<Customer> searchCustomerByName(String name) {
        ArrayList<Customer> sameNameList = new ArrayList<>();
        for (Customer cus : customerList) {
            if (cus.getFullName().contains(name)) {
                sameNameList.add(cus);
            }
        }
        return sameNameList;
    }
    
    public Customer getCustomerByUserName(String userName) {
        for (Customer cus : customerList) {
            if (cus.getUsername().equals(userName)) {
                return cus;
            }
        }
        return null;
    }
    
    public void saveCustomer(String fileName) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        DecimalFormat decimal = new DecimalFormat("0.00", symbols);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (Customer customer : customerList) {
                String output = String.format(customer.getUsername() + ":" + customer.getPassword() + ":" + customer.getFullName() + ":"
                        + customer.getDob().format(formatter) + ":" + customer.getPhone() + ":" + customer.getMail() + ":" + customer.getGovernmentID() + ":" + customer.getaccountNumber() + ":" + decimal.format(customer.getBalance()));
                bw.write(output);
                bw.newLine();
            }
            System.out.println("Backup successful.");
        } catch (Exception e) {
            System.out.println("Error backing up data: " + e.getMessage());
        }
    }
}
