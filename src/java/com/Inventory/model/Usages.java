/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Inventory.model;

import java.util.Date;

public class Usages {
    private int usagesID;
    private Date usageDate;
    private int userID;

    // Setters
    public void setUsagesID(int usagesID) {
        this.usagesID = usagesID;
    }

    public void setUsageDate(java.sql.Date usageDate) {
        this.usageDate = usageDate;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    // Getters
    public int getUsagesID() {
        return usagesID;
    }

    public Date getUsageDate() {
        return usageDate;
    }

    public int getUserID() {
        return userID;
    }
}
