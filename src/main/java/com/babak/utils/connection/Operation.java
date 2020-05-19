package com.babak.utils.connection;

import java.sql.Connection;

@FunctionalInterface
public interface Operation<T> {

    T execute(Connection connection);
}
