package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {
    }

    @Override
    public void createUsersTable() {
        String query = "CREATE TABLE IF NOT EXISTS user (id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, name VARCHAR(255), lastname VARCHAR(255), age TINYINT) ";
        try (Connection connection = Util.getConnection()){
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            statement.execute(query);
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        String query = "DROP TABLE IF EXISTS user";
        try (Connection connection = Util.getConnection()){
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            statement.execute(query);
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        String query = "INSERT INTO user (id, name, lastname, age) VALUES (?, ?, ?, ?)";
        try (Connection connection = Util.getConnection();){
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, 0); // Здесь нужно указать значение для id
            statement.setString(2, name);
            statement.setString(3, lastName);
            statement.setByte(4, age);
            statement.executeUpdate();
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void removeUserById(long id) {
        String query = "DELETE FROM user WHERE id = ?";
        try (Connection connection = Util.getConnection();){
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            statement.executeUpdate();
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection connection = Util.getConnection();){
            PreparedStatement statement = connection.prepareStatement("select * from user");
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                User user = new User();
                user.setId(result.getLong("id"));
                user.setName(result.getString("name"));
                user.setLastName(result.getString("lastname"));
                user.setAge(result.getByte("age"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        String query = "TRUNCATE TABLE user";
        try (Connection connection = Util.getConnection()){
            PreparedStatement statement = connection.prepareStatement(query);
            connection.setAutoCommit(false);
            statement.execute();
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
