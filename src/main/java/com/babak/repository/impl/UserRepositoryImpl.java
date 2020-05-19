package com.babak.repository.impl;

import com.babak.entity.User;
import com.babak.repository.UserRepository;
import com.babak.utils.constants.EntityConstants;
import com.babak.utils.constants.SqlQuery;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class UserRepositoryImpl implements UserRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRepositoryImpl.class);

    @Override
    public User insert(Connection connection, User user) {
        int result = 0;
        try (PreparedStatement preparedStatement = connection
                .prepareStatement(SqlQuery.INSERT_USER, Statement.RETURN_GENERATED_KEYS)) {
            int i = 1;
            preparedStatement.setString(i++, user.getName());
            preparedStatement.setString(i++, user.getSurname());
            preparedStatement.setString(i++, user.getEmail());
            preparedStatement.setString(i++, user.getLogin());
            preparedStatement.setString(i++, user.getPassword());
            result = preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                user.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            LOGGER.error("Failed to add to database. Exception occurred : {}", e.getMessage());
        }
        if (result > 0) {
            return user;
        } else {
            return null;
        }
    }

    /**
     * Check exist user with login.
     *
     * @param login the login user.
     */
    public boolean isExistByLogin(Connection connection, String login) {
        ResultSet resultSet;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SqlQuery.FIND_USER_BY_LOGIN)) {
            preparedStatement.setString(1, login);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            LOGGER.error("Exception occurred : {}", e.getMessage());
        }
        return false;
    }

    @Override
    public User checkLoginAndPassword(Connection connection, String login, String password) {
        ResultSet resultSet;
        User user = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SqlQuery.FIND_USER_BY_LOGIN_AND_PASSWORD)) {
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt(EntityConstants.ID));
                user.setName(resultSet.getString(EntityConstants.USER_NAME));
                user.setSurname(resultSet.getString(EntityConstants.SURNAME));
                user.setEmail(resultSet.getString(EntityConstants.EMAIL));
                user.setLogin(resultSet.getString(EntityConstants.LOGIN));
                user.setPassword(resultSet.getString(EntityConstants.USER_PASSWORD));
            }
        } catch (SQLException e) {
            LOGGER.error("Exception occurred : {}", e.getMessage());
        }
        return user;
    }
}
