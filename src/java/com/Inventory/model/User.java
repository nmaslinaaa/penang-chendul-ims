package com.Inventory.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class User {
    private int userID;
    private String name;
    private String username;
    private String password;
    private int user_level;
    private int status;
    private Timestamp last_login;
    private String group_name; // Add this field to store group name

    // Getters and setters
    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUser_level() {
        return user_level;
    }

    public void setUser_level(int user_level) {
        this.user_level = user_level;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Timestamp getLast_login() {
        return last_login;
    }

    public void setLast_login(Timestamp last_login) {
        this.last_login = last_login;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public boolean authenticate(Connection conn) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, this.username);
            stmt.setString(2, this.password);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    this.userID = rs.getInt("userID");
                    this.name = rs.getString("name");
                    this.user_level = rs.getInt("user_level");
                    this.status = rs.getInt("status");
                    this.last_login = rs.getTimestamp("last_login");
                    
                    // Update the last login time
                    updateLastLogin(conn, this.userID);

                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void updateLastLogin(Connection conn, int userID) {
        String updateSql = "UPDATE users SET last_login = ? WHERE userID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(updateSql)) {
            stmt.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            stmt.setInt(2, userID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
