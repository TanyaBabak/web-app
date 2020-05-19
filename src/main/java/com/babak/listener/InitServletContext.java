package com.babak.listener;

import com.babak.repository.impl.CatalogRepositoryImpl;
import com.babak.repository.impl.OrderRepositoryImpl;
import com.babak.repository.impl.UserRepositoryImpl;
import com.babak.service.impl.CaptchaServiceImpl;
import com.babak.service.impl.CatalogServiceImpl;
import com.babak.service.impl.OrderServiceImpl;
import com.babak.service.impl.UserServiceImpl;
import com.babak.utils.connection.HikariConnection;
import com.babak.utils.connection.Transaction;
import com.babak.utils.constants.PathConstants;
import com.babak.utils.constants.WebConstants;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebListener
public class InitServletContext implements ServletContextListener {

    private HikariConnection hikariConnection;
    private static final Logger LOGGER = LoggerFactory.getLogger(InitServletContext.class);

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        hikariConnection = new HikariConnection();
        Connection connection = hikariConnection.getConnection();
        runScript(connection);
        ServletContext servletContext = servletContextEvent.getServletContext();
        initServices(hikariConnection, servletContext);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        hikariConnection.closeConnection();
    }

    private void runScript(Connection connection) {
        String path = PathConstants.SCRIPT_SQL;
        try {
            ScriptRunner sr = new ScriptRunner(connection);
            Reader reader = new BufferedReader(new FileReader(path));
            sr.runScript(reader);
        } catch (Exception e) {
            LOGGER.error("Failed to Execute.Exception occurred : {}", e.getMessage());
        }
    }

    private void initServices(HikariConnection hikariDataSource, ServletContext servletContext) {
        Transaction transaction = new Transaction(hikariDataSource);
        try {
            servletContext.setAttribute(WebConstants.SERVICE_USER, new UserServiceImpl(new UserRepositoryImpl(), transaction));
            servletContext.setAttribute(WebConstants.SERVICE_CAPTCHA, new CaptchaServiceImpl());
            servletContext.setAttribute(WebConstants.SERVICE_CATALOG, new CatalogServiceImpl(transaction, new CatalogRepositoryImpl()));
            servletContext.setAttribute(WebConstants.SERVICE_ORDER, new OrderServiceImpl(transaction, new OrderRepositoryImpl()));
        } catch (RuntimeException e) {
            LOGGER.error("Web application 'shop' init failed: " + e.getMessage(), e);
            throw e;
        }
    }
}
