package com.babak.service;

import com.babak.entity.User;

public interface UserService {

    boolean isUserExistByLogin(String login);

    User insertUser(User user);

    User checkUserLoginAndPassword(String login, String password);
}
