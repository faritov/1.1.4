package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Util {
    private static String URL = "jdbc:mysql://localhost:3306/user";
    private static String USERNAME = "root";
    private static String PASSWORD = "root";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
           // System.out.println("Подключение есть");
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return connection;
    }
}





