/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Inventory.model;

/**
 *
 * @author User
 */

import java.util.Date;

public class UserItem {
    private int userItemId;
    private int userId;
    private int itemId;
    private int newQty;
    private Date dateAdded;

    // Constructors
    public UserItem() {}

    public UserItem(int userItemId, int userId, int itemId, int newQty, Date dateAdded) {
        this.userItemId = userItemId;
        this.userId = userId;
        this.itemId = itemId;
        this.newQty = newQty;
        this.dateAdded = dateAdded;
    }

    // Getters and Setters
    public int getUserItemId() {
        return userItemId;
    }

    public void setUserItemId(int userItemId) {
        this.userItemId = userItemId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
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
}
