/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author ASUS
 */
import controller.Utils;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private final LocalDate date;
    private final String type;
    private final double amount;
    private final String details;
    
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
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        DecimalFormat decimal = new DecimalFormat("0.00", symbols);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = date.format(formatter);
        return String.format("|%-15s|%-10s|%-15s|%-30s|", Utils.center(formattedDate, 15), Utils.center(type, 10), Utils.center(decimal.format(amount), 15), Utils.center(details, 30));
    }
}
