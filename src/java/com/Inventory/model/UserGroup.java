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
public class UserGroup {
     private int usergroupID;
    private String groupName;
    private int groupLevel;
    private int groupStatus;

    // Constructors
    public UserGroup() {}

    public UserGroup(int usergroupID, String groupName, int groupLevel, int groupStatus) {
        this.usergroupID = usergroupID;
        this.groupName = groupName;
        this.groupLevel = groupLevel;
        this.groupStatus = groupStatus;
    }

    // Getters and Setters
    public int getUsergroupID() {
        return usergroupID;
    }

    public void setUsergroupID(int usergroupID) {
        this.usergroupID = usergroupID;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getGroupLevel() {
        return groupLevel;
    }

    public void setGroupLevel(int groupLevel) {
        this.groupLevel = groupLevel;
    }

    public int getGroupStatus() {
        return groupStatus;
    }

    public void setGroupStatus(int groupStatus) {
        this.groupStatus = groupStatus;
    }
}
