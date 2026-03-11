package com.Inventory.model;

import java.util.Date;

public class Item {
    private int itemID;
    private String itemName;
    private String MOQ;
    private double itemPrice;
    private String currentQty;
    private int supplierID;

    // Additional fields for user_item table
    private int userID;
    private int newQty;
    private Date dateAdded;
    private String username; // Add this field

    // Constructors
    public Item() {}

    public Item(int itemID, String itemName, String MOQ, double itemPrice, String currentQty, int supplierID) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.MOQ = MOQ;
        this.itemPrice = itemPrice;
        this.currentQty = currentQty;
        this.supplierID = supplierID;
    }

    // Getters and Setters
    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getMOQ() {
        return MOQ;
    }

    public void setMOQ(String MOQ) {
        this.MOQ = MOQ;
    }
    
    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getCurrentQty() {
        return currentQty;
    }

    public void setCurrentQty(String currentQty) {
        this.currentQty = currentQty;
    }

    public int getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(int supplierID) {
        this.supplierID = supplierID;
    }

    // Getters and Setters for user_item table fields
    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getNewQty() {
        return newQty;
    }

    public void setNewQty(int newQty) {
        this.newQty = newQty;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    // Additional fields and methods for handling join queries
    private String supplierName;

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }
    
     public String getUsername() {
        return username; // Add this getter
    }

    public void setUsername(String username) {
        this.username = username; // Add this setter
    }
}

