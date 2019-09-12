import com.mysql.cj.jdbc.Driver;

import java.sql.*;
import java.util.ArrayList;

public class UserDb {

    public void setNewUser(String name, int age) throws SQLException {
        String createUseSQL = "INSERT INTO table_test (name, age) VALUE ('" + name + "', '"+ age +"')";
        Connection connection = null;
        Statement statement;
        statement = null;
        try {
            connection = getDBConnection();
            statement = connection.createStatement();
            statement.execute(createUseSQL);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    public static ArrayList<String> getUsersNames() throws SQLException, ClassNotFoundException {
        String getUserDataSql = "SELECT * FROM table_test";
        Connection connection = null;
        ResultSet resultSet = null;
        Statement statement;
        statement = null;
        ArrayList<String> users = new ArrayList<>();

        try {
            connection = getDBConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(getUserDataSql);
            while (resultSet.next()) {
                users.add(resultSet.getString(2));
            }
            return users;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return users;
    }

    private static Connection getDBConnection() throws ClassNotFoundException, SQLException {
        String url = "jdbc:mysql://localhost:3306/test?serverTimezone=Europe/Kiev&useSSL=false";
        String username = "root";
        String password = "password";
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(url, username, password);
    }
}
