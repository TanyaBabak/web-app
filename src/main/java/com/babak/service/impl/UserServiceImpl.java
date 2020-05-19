package com.babak.service.impl;

import com.babak.entity.User;
import com.babak.repository.UserRepository;
import com.babak.service.UserService;
import com.babak.utils.connection.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private Transaction transaction;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    public UserServiceImpl(UserRepository userRepository, Transaction transaction) {
        this.userRepository = userRepository;
        this.transaction = transaction;
    }

    /**
     * Check login exist.
     *
     * @param login the login user's
     */
    public boolean isUserExistByLogin(String login) {
        return transaction.executeTransaction((connection -> userRepository.isExistByLogin(connection, login)));
    }

    @Override
    public User insertUser(User user) {
        return transaction.executeTransaction((connection -> userRepository.insert(connection, user)));

    }

    @Override
    public User checkUserLoginAndPassword(String login, String password) {
        return transaction.executeTransaction((connection -> userRepository.checkLoginAndPassword(connection, login, password)));
    }

}
