package com.babak.repository;

import com.babak.entity.User;
import java.sql.Connection;

public interface UserRepository {

    User insert(Connection connection, User user);

    boolean isExistByLogin(Connection connection, String login);

    User checkLoginAndPassword(Connection connection, String login, String password);

}
