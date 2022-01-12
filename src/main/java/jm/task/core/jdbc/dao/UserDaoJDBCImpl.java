package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private Connection connection = Util.MySQL();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String create = "CREATE TABLE IF NOT EXISTS users (`id` BIGINT NOT NULL AUTO_INCREMENT," +
                "`name` VARCHAR(45)," +
                "`lastName` VARCHAR(45)," +
                "`age` SMALLINT NOT NULL," +
                "PRIMARY KEY (id))";
        try (PreparedStatement preparedStatement = connection.prepareStatement(create)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        String drop = "DROP TABLE IF EXISTS users";
        try (PreparedStatement preparedStatement = connection.prepareStatement(drop)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String save = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(save)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String remove = "DELETE FROM users WHERE id = id";
        try (PreparedStatement preparedStatement = connection.prepareStatement(remove)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> array = new ArrayList<>();
        String getUsers = "SELECT * FROM users";
        try (PreparedStatement preparedStatement = connection.prepareStatement(getUsers)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong(1));
                user.setName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setAge(resultSet.getByte(4));
                array.add(user);
                connection.setAutoCommit(false);
                connection.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return array;
    }

    public void cleanUsersTable() {
        String clean = "TRUNCATE users";
        try (PreparedStatement preparedStatement = connection.prepareStatement(clean)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}