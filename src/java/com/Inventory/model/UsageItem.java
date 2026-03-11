/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Inventory.model;

import java.util.Date;

public class UsageItem {
    private int usageitemID;
    private int usagesID;
    private int itemID;
    private int usedQty;
    
    private String itemName;
    private String MOQ;
    private Date usageDate;
    

    // Setters
    public void setUsageitemID(int usageitemID) {
        this.usageitemID = usageitemID;
    }

    public void setUsagesID(int usagesID) {
        this.usagesID = usagesID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public void setUsedQty(int usedQty) {
        this.usedQty = usedQty;
    }
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
     
     public void setMOQ(String MOQ) {
        this.MOQ = MOQ;
    }
     
     public void setUsageDate(Date usageDate) {
        this.usageDate = usageDate;
    }

    // Getters
    public int getUsageitemID() {
        return usageitemID;
    }

    public int getUsagesID() {
        return usagesID;
    }

    public int getItemID() {
        return itemID;
    }

    public int getUsedQty() {
        return usedQty;
    }
    public String getItemName() {
        return itemName;
    }
     public String getMOQ() {
        return MOQ;
    }
     public Date getUsageDate() {
        return usageDate;
    }
}
