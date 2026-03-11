package com.Inventory.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginDao {
    private static final String jdbcURL = "jdbc:mariadb://localhost:3306/system";
    private static final String jdbcUsername = "root";
    private static final String jdbcPassword = "";
    private static final String SELECT_USER_SQL = "SELECT * FROM users WHERE username = ? AND password = ?";
    private static final Logger logger = Logger.getLogger(LoginDao.class.getName());

    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (ClassNotFoundException | SQLException e) {
            logger.log(Level.SEVERE, "Database connection failed: ", e);
        }
        return connection;
    }

    public boolean validate(LoginBean loginBean) {
        boolean status = false;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_SQL)) {
            preparedStatement.setString(1, loginBean.getUsername());
            preparedStatement.setString(2, loginBean.getPassword());
            try (ResultSet rs = preparedStatement.executeQuery()) {
                status = rs.next();
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error validating user: ", e);
        }
        return status;
    }
}
