package com.babak.utils.connection;

import com.babak.exception.InternalServerErrorException;
import com.babak.utils.constants.PathConstants;
import com.babak.utils.constants.WebConstants;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HikariConnection {

    private static final Logger LOGGER = LoggerFactory.getLogger(HikariDataSource.class);
    private static final HikariDataSource HIKARI;

    /**
     * Initialize connection.
     */

    static {
        HikariConfig config = new HikariConfig();
        Properties props = new Properties();
        try (InputStream fis = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(PathConstants.CONFIG_PROPERTIES)) {
            props.load(fis);
            String url = props.getProperty(WebConstants.DATA_SOURCE_URL);
            String user = props.getProperty(WebConstants.DATA_SOURCE_USER);
            String password = props.getProperty(WebConstants.DATA_SOURCE_PASSWORD);
            String driverClassName = props.getProperty(WebConstants.DATA_SOURCE_DRIVER_NAME);
            config.setJdbcUrl(url);
            config.setUsername(user);
            config.setPassword(password);
            config.setDriverClassName(driverClassName);
            HIKARI = new HikariDataSource(config);
        } catch (IOException e) {
            throw new InternalServerErrorException("Connection with database wasn't open" + e.getMessage(), e);
        }
    }

    /**
     * Get connection with database.
     *
     * @return the Connection
     */
    public Connection getConnection() {
        Connection connection;
        try {
            connection = HIKARI.getConnection();
        } catch (SQLException e) {
            LOGGER.error("Exception occurred : {}", e.getMessage());
            throw new InternalServerErrorException("Connection with database wasn't open" + e.getMessage(), e);
        }
        return connection;
    }

    /**
     * Close all pool connection.
     */
    public void closeConnection() {
        HikariDataSource hikari = HIKARI;
        if (hikari != null) {
            hikari.close();
        } else {
            throw new InternalServerErrorException("Connection can't close");
        }
    }
}
