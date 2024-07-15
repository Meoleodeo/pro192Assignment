package model;

import controller.Utils;

import java.time.LocalDate;
import java.util.ArrayList;

public class Customer {

    private String username;
    private String password;
    private String fullName;
    private LocalDate dob;
    private String phone;
    private String mail;
    private String govermentID;
    private String accountNumber;
    private Double balance;
    private ArrayList<Transaction> transactions;

    public Customer() {
    }

    ;

    public Customer(String username, String password, String fullName, LocalDate dob, String phone, String mail, String id, String accountNumber, Double balance, ArrayList<Transaction> transactions) {
        this.username = username;
        setPassword(password);
        this.fullName = fullName;
        this.dob = dob;
        setPhone(phone);
        setMail(mail);
        this.govermentID = id;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.transactions = transactions;
    }

    public final void setDobByString(LocalDate dob) {
        this.dob = dob;
    }

    public void setPassword(String password) throws IllegalArgumentException {
        if (Utils.isValidPassword(password)) {
            this.password = password;
        } else {
            throw new IllegalArgumentException("Password must have 8 characters, uppercase letters, numbers and special characters");
        }
    }

    public void setPhone(String phone) throws IllegalArgumentException {
        if (Utils.isValidPhone(phone)) {
            this.phone = phone;
        } else {
            throw new IllegalArgumentException("Invalid phone number");
        }
    }

    public void setMail(String mail) throws IllegalArgumentException {
        if (Utils.isValidMail(mail)) {
            this.mail = mail;
        } else {
            throw new IllegalArgumentException("Invalid mail");
        }
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setID(String ID) {
        this.govermentID = ID;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void balance(Double balance) {
        this.balance = balance;
    }

    public void setAccountFund(double balanceStr) throws IllegalArgumentException {
        if (balanceStr < 0) {
            throw new IllegalArgumentException("Balance must be greater or equal 0.");
        } else {
            this.balance = balanceStr;
        }

    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return fullName;
    }

    public String getFirstName() {
        String[] nameParts = fullName.split(" ");
        return nameParts[nameParts.length - 1];
    }

    public LocalDate getDob() {
        return dob;
    }

    public String getPhone() {
        return phone;
    }

    public String getMail() {
        return mail;
    }

    public String getGovernmentID() {
        return govermentID;
    }

    public String getaccountNumber() {
        return accountNumber;
    }

    public Double getBalance() {
        return balance;
    }

    public ArrayList<Transaction> getTransaction() {
        return this.transactions;
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    @Override
    public String toString() {
        return String.format("|%-17s|%-20s|%-15s|%-15s|%-25s|%-11s|", username, fullName, accountNumber, govermentID, mail, phone);
    }
}
