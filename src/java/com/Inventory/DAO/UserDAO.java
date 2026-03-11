
package com.Inventory.DAO;

import com.Inventory.model.User;
import com.Inventory.model.UserGroup;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    
    public static boolean isNameTaken(String fullName, Connection conn) throws SQLException {
        String query = "SELECT COUNT(*) FROM users WHERE name = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, fullName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }
    
    public static boolean isUsernameTaken(String username, Connection conn) throws SQLException {
        String query = "SELECT COUNT(*) FROM users WHERE username = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    
    public static boolean isPasswordTaken(String hashedPassword, Connection conn) throws SQLException {
        String query = "SELECT COUNT(*) FROM users WHERE password = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, hashedPassword);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    
    public static void deleteUser(Connection conn, int userID) throws SQLException {
        String sql = "DELETE FROM users WHERE userID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            stmt.executeUpdate();
        }
    }

    public static User getUserDetails(String username, String password, Connection conn) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Hash the input password
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] passwordBytes = password.getBytes("UTF-8");
            byte[] hashBytes = md.digest(passwordBytes);
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            String hashedPassword = sb.toString();

            stmt.setString(1, username);
            stmt.setString(2, hashedPassword);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setUserID(rs.getInt("userID"));
                    user.setName(rs.getString("name"));
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    user.setUser_level(rs.getInt("user_level"));
                    user.setStatus(rs.getInt("status"));
                    user.setLast_login(rs.getTimestamp("last_login"));
                    return user;
                }
            }
        } catch (SQLException | NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int countByUserId(Connection conn) {
        String sql = "SELECT COUNT(userID) AS total FROM users";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static List<User> findAllUsers(Connection conn) throws SQLException {
        String sql = "SELECT u.userID, u.name, u.username, u.user_level, u.status, u.last_login, g.group_name " +
                     "FROM users u " +
                     "LEFT JOIN user_groups g ON g.group_level = u.user_level " +
                     "ORDER BY u.last_login DESC, u.userID ASC";
        List<User> users = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                User user = new User();
                user.setUserID(rs.getInt("userID"));
                user.setName(rs.getString("name"));
                user.setUsername(rs.getString("username"));
                user.setUser_level(rs.getInt("user_level"));
                user.setStatus(rs.getInt("status"));
                user.setLast_login(rs.getTimestamp("last_login"));
                user.setGroup_name(rs.getString("group_name"));
                users.add(user);
            }
        }
        return users;
    }

    public static boolean addUser(User user, Connection conn) throws SQLException {
        String sql = "INSERT INTO users (name, username, password, user_level, status) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getUsername());
            stmt.setString(3, user.getPassword());
            stmt.setInt(4, user.getUser_level());
            stmt.setInt(5, user.getStatus());
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Added methods
    public User findByUserId(int userID, Connection conn) throws SQLException {
        String query = "SELECT u.*, g.group_name FROM users u JOIN user_groups g ON u.user_level = g.group_level WHERE u.userID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setUserID(rs.getInt("userID"));
                    user.setName(rs.getString("name"));
                    user.setUsername(rs.getString("username"));
                    user.setUser_level(rs.getInt("user_level"));
                    user.setStatus(rs.getInt("status"));
                    user.setLast_login(rs.getTimestamp("last_login"));
                    user.setGroup_name(rs.getString("group_name"));
                    return user;
                }
            }
        }
        return null;
    }

    public boolean updateUser(User user, Connection conn) throws SQLException {
        String query = "UPDATE users SET name = ?, username = ?, user_level = ?, status = ? WHERE userID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getUsername());
            stmt.setInt(3, user.getUser_level());
            stmt.setInt(4, user.getStatus());
            stmt.setInt(5, user.getUserID());
            return stmt.executeUpdate() == 1;
        }
    }
   

    public List<UserGroup> findAllUserGroups(Connection conn) throws SQLException {
        String query = "SELECT * FROM user_groups";
        List<UserGroup> groups = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                UserGroup group = new UserGroup();
                group.setGroupLevel(rs.getInt("group_level"));
                group.setGroupName(rs.getString("group_name"));
                groups.add(group);
            }
        }
        return groups;
    }
    


    
    public static boolean verifyPassword(int userID, String password, Connection conn) throws SQLException {
        String sql = "SELECT password FROM users WHERE userID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String storedPassword = rs.getString("password");
                    // Assuming passwords are stored as SHA-1 hashes in the database
                    return storedPassword.equals(sha1(password)); // Simplified example, use hashed comparison
                }
            }
        }
        return false;
    }

    public static boolean updateUserPassword(int userID, String newPassword, Connection conn) throws SQLException {
        String sql = "UPDATE users SET password = ? WHERE userID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, sha1(newPassword)); // Assuming we store passwords as SHA-1 hashes
            stmt.setInt(2, userID);
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        }
    }

    public static boolean updateUserDetails(User user, Connection conn) throws SQLException {
        String sql = "UPDATE users SET name = ?, username = ? WHERE userID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getUsername());
            stmt.setInt(3, user.getUserID());
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        }
    }



    // New method to update last login time
    public static boolean updateLastLogIn(int userID, Connection conn) throws SQLException {
        String sql = "UPDATE users SET last_login = ? WHERE userID = ? LIMIT 1";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            Timestamp now = new Timestamp(System.currentTimeMillis());
            stmt.setTimestamp(1, now);
            stmt.setInt(2, userID);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected == 1;
        }
    }

    // Existing methods ...

    public static String sha1(String input) {
        try {
            MessageDigest mDigest = MessageDigest.getInstance("SHA1");
            byte[] result = mDigest.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < result.length; i++) {
                sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public static User getUserByUsernameAndPassword(String username, String password, Connection conn) throws UnsupportedEncodingException, NoSuchAlgorithmException, SQLException {
    String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        // Hash the input password
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] passwordBytes = password.getBytes("UTF-8");
        byte[] hashBytes = md.digest(passwordBytes);
        StringBuilder sb = new StringBuilder();
        for (byte b : hashBytes) {
            sb.append(String.format("%02x", b));
        }
        String hashedPassword = sb.toString();

        stmt.setString(1, username);
        stmt.setString(2, hashedPassword);

        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                User user = new User();
                user.setUserID(rs.getInt("userID"));
                user.setName(rs.getString("name"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setUser_level(rs.getInt("user_level"));
                user.setStatus(rs.getInt("status"));
                user.setLast_login(rs.getTimestamp("last_login"));
                return user;
            }
        }
    }
    return null;
}
    
    public String getPasswordByUserId(int userID, Connection conn) throws SQLException {
        String query = "SELECT password FROM users WHERE userID = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, userID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("password");
                }
            }
        }
        return null;
}
    
 public static User getUserByUsername(String username, Connection conn) throws SQLException {
    String sql = "SELECT * FROM users WHERE username = ?";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, username);
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                User user = new User();
                user.setUserID(rs.getInt("userID"));
                user.setName(rs.getString("name"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setUser_level(rs.getInt("user_level"));
                user.setStatus(rs.getInt("status"));
                user.setLast_login(rs.getTimestamp("last_login"));
                return user;
            }
        }
    }
    return null; // If no user is found with the provided username
}
}
