package model;

import controller.Utils;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Bank {

    private final ArrayList<Customer> customerList;
    private String bankUser;
    private String bankPassWord;

    public Bank() {
        this.customerList = new ArrayList<>();
        bankUser = "1";
        bankPassWord = "1";
        loadCustomersFromFile("bank.txt");
    }

    public ArrayList<Customer> getCustomerList() {
        return customerList;
    }

    private void loadCustomersFromFile(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isEmpty()) {
                    continue;
                }

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
                    ArrayList<Transaction> transactions = (loadTransaction("transaction.txt").get(id) != null) ? loadTransaction("transaction.txt").get(id) : new ArrayList<>();
                    if (!Utils.checkDuplicate(id, customerList) && Utils.isValidMail(mail) && Utils.isValidPassword(password) && Utils.isValidPhone(phone) && dob != null) {
                        Customer customer = new Customer(username, password, fullName, dob, phone, mail, id, numberAccount, balanceStr, transactions);
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
        Customer customer = getCustomerMethod(cus -> cus.getUsername().equals(username));
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
        System.out.println(String.format(
                "|%-17s|%-20s|%-20s|%-20s|%-25s|%-15s|",
                Utils.center("User-Name", 17), Utils.center("Full-Name", 20), Utils.center("Account-Number", 20), Utils.center("Identification", 20), Utils.center("Mail", 25), Utils.center("Phone", 15)));
        System.out.println("|-----------------|--------------------|--------------------|--------------------|-------------------------|---------------|");
        for (Customer cus : customerList) {
            System.out.println(cus);
        }
    }

    public ArrayList<Customer> searchMethod(Predicate<Customer> p) {
        ArrayList<Customer> searchList = new ArrayList<>();
        for (Customer cus : customerList) {
            if (p.test(cus)) {
                searchList.add(cus);
            }
        }
        return searchList;
    }

    public Customer getCustomerMethod(Predicate<Customer> p) {
        for (Customer cus : customerList) {
            if (p.test(cus)) {
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
        } catch (Exception e) {
            System.out.println("Error backing up data: " + e.getMessage());
        }
    }

    public void saveTransaction(String fileName) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            DecimalFormatSymbols symbols = new DecimalFormatSymbols();
            symbols.setDecimalSeparator('.');
            DecimalFormat decimal = new DecimalFormat("0.00", symbols);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            for (Customer cus : customerList) {
                bw.write(cus.getGovernmentID());
                bw.newLine();
                for (Transaction tran : cus.getTransaction()) {
                    String formattedDate = tran.getDate().format(formatter);
                    String outPut = String.format("%s:%s:%s:%s", formattedDate, tran.getType(), decimal.format(tran.getAmount()), tran.getDetails());
                    bw.write(outPut);
                    bw.newLine();
                }
                bw.newLine();
            }
        } catch (IOException ex) {
            Logger.getLogger(Bank.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Map<String, ArrayList<Transaction>> loadTransaction(String fileName) {
        Map<String, ArrayList<Transaction>> transactionList = new HashMap<>();
        String line;
  
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String id = null;
            while ((line = br.readLine()) != null) {
                String[] parts = line.trim().split(":");
                if(parts.length == 1){
                    id = parts[0];
                    transactionList.computeIfAbsent(id, k -> new ArrayList<Transaction>());
                    continue;
                }
                if (parts.length == 4) {
                    LocalDate date = LocalDate.parse(parts[0].trim(), formatter);
                    String type = parts[1].trim();
                    double amount = Double.parseDouble(parts[2].trim());
                    String details = parts[3].trim();
                    transactionList.get(id).add(new Transaction(date, type, amount, details));
                }
            }
        } catch (Exception ex) {
            System.out.println("Error loading:" + ex.getMessage());
        }
        return transactionList;
    }
}
