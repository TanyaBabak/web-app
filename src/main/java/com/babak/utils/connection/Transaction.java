package com.babak.utils.connection;

import com.babak.exception.InternalServerErrorException;
import java.sql.Connection;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Transaction {

    private HikariConnection hikariDataSource;
    private static final Logger LOGGER = LoggerFactory.getLogger(Transaction.class);

    public Transaction(HikariConnection hikariDataSource) {
        this.hikariDataSource = hikariDataSource;
    }

    /**
     * Execute anything request with connection database.
     */
    public <T> T executeTransaction(Operation<T> operation) {
        Connection connection = null;
        T result;
        try {
            connection = hikariDataSource.getConnection();
            connection.setAutoCommit(false);
            result = operation.execute(connection);
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            throw new InternalServerErrorException("Can't execute sql query: " + e.getMessage(), e);
        } finally {
            close(connection);
        }
        return result;
    }

    private void close(AutoCloseable autoCloseable) {
        if (autoCloseable != null) {
            try {
                autoCloseable.close();
            } catch (Exception e) {
                throw new InternalServerErrorException("Connection wasn't closed" + e.getMessage(), e);
            }
        }
    }
}
