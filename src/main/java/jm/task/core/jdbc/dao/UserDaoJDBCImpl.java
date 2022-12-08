package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() throws SQLException {      //создание таблицы
        Connection connection = Util.getConnection();
        PreparedStatement preparedStatement = null;
        String sql = "CREATE TABLE IF NOT EXISTS users" + "(id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(140), lastName VARCHAR(140), age int)";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            System.out.println("Таблица создана");
        } catch (SQLException e) {
            System.out.println("Таблица существует");
        }
    }

    public void dropUsersTable() throws SQLException {       //удаление таблицы
        Connection connection = Util.getConnection();
        PreparedStatement preparedStatement = null;
        String sql = "DROP TABLE if EXISTS users";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            System.out.println("Таблица удалена");
        } catch (SQLException e) {
            System.out.println("Таблицы и так не было");
        }
    }


    public void saveUser(String name, String lastName, byte age) throws SQLException {    //добавление юзера
        Connection connection = Util.getConnection();
        PreparedStatement preparedStatement = null;
        String sql = "INSERT INTO users" + "(name, lastName, age)" + " VALUES (?, ?, ?)";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User с именем - " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            System.out.println("User не добавлен");
        }
       /* try (Connection connection =
                     Util.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement("INSERT INTO users (name, lastname, age) VALUES (?, ?, ?);")
        ) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
    }


    public void removeUserById(long id) throws SQLException {    //удаление по айди
        Connection connection = Util.getConnection();
        PreparedStatement preparedStatement = null;
        String sql = "DELETE FROM users WHERE ID = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Пользователь с ID " + id + " был удален");
        } catch (SQLException e) {
            System.out.println("Пользователь не удален");
        }
    }



    public List<User> getAllUsers() {
//        List<User> user = new ArrayList<>();
//        String sql = "SELECT * FROM users";
//        try (Connection connection = Util.getConnection()) {
//            try (PreparedStatement statement = connection.prepareStatement(sql)) {
//                ResultSet rs = statement.executeQuery();
//                while (rs.next()) {
//                    long id = rs.getLong("id");
//                    String name = rs.getString("name");
//                    String lastname = rs.getString("lastName");
//                    byte age = rs.getByte("age");
//
//                    user.add(new User(name, lastname, age));
//
//                }
//                System.out.println("Таблица пользователей выведена: ");
//                System.out.println(user);
//
//            }
//
//        } catch (SQLException e) {
//            System.out.println("Таблица пользователей не выведена");
//            e.printStackTrace();
//
//        }
//        return user;
        List<User> users = new ArrayList<>();

        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users");
             ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {   //очищение таблицы
        Connection connection = Util.getConnection();
        PreparedStatement preparedStatement = null;
        String sql = "TRUNCATE TABLE users";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            System.out.println("Таблица очищена");
        } catch (SQLException e) {
            System.out.println("Таблица не была очищена");
        }
    }
}



