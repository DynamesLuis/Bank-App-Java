package db_objs;

import java.math.BigDecimal;
import java.sql.*;

public class JDBC {
    private static final String DB_URL = "url";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "password";

    public static User validateLogin(String username, String password) {
        try {
            Connection conecction = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            PreparedStatement preparedStatement = conecction.prepareStatement(
                    "SELECT * FROM users WHERE username = ? AND password = ?"
            );
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int userId = resultSet.getInt("id");
                BigDecimal currentBalance = resultSet.getBigDecimal("current_balance");
                return new User(userId, username, password, currentBalance);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
