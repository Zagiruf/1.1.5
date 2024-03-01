package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
Connection connection = Util.getConnection();
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String s = "CREATE TABLE IF NOT EXISTS users (id BIGINT primary key AUTO_INCREMENT, name VARCHAR(45), lastName VARCHAR(45),age TINYINT)";
        try(Statement statement = connection.createStatement()){
            statement.executeUpdate(s);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
       String s = "DROP TABLE IF EXISTS users";
        try(Statement statement = connection.createStatement()){
            statement.executeUpdate(s);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
         String s = "INSERT INTO users (name,lastName,age) VALUES (?,?,?)";
         try(PreparedStatement preparedStatement = connection.prepareStatement(s)){
            preparedStatement.setString(1,name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3,age);
            preparedStatement.executeUpdate();
         } catch (SQLException e) {
            throw new RuntimeException(e);
         }
    }
    public void removeUserById(long id) {
      String s = "DELETE FROM users WHERE id = ?";
        try(PreparedStatement statement = connection.prepareStatement(s)){
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        String s = "SELECT * from users";
        List <User> arrayList = new ArrayList<>();
        try(Statement statement = connection.createStatement()){
          ResultSet resultSet =  statement.executeQuery(s);
          while (resultSet.next()){
            User user = new User();
            user.setId(resultSet.getLong(1));
             user.setName(resultSet.getString(2));
              user.setLastName (resultSet.getString(3));
              user.setAge(resultSet.getByte(4));
              arrayList.add(user);
          }
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return arrayList;
    }

    public void cleanUsersTable() {
String s = "TRUNCATE TABLE users";
        try(Statement statement = connection.createStatement()){
            statement.executeUpdate(s);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    }

