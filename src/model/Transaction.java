/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author ASUS
 */
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
public class Transaction {
    private LocalDate date;
    private String type;
    private double amount;
    private String details;
    
    public Transaction(){};

    public Transaction(LocalDate date, String type, double amount, String details) {
        this.date = date;
        this.type = type;
        this.amount = amount;
        this.details = details;
    }

    public LocalDate getDate() {
        return date;
    }


    public String getType() {
        return type;
    }


    public double getAmount() {
        return amount;
    }

    public String getDetails() {
        return details;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = date.format(formatter);
        return String.format("%-15s|%-10s|%-15.2f|%-20s", formattedDate, type, amount, details);
    }
}
