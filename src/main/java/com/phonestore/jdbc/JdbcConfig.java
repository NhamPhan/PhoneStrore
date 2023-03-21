package com.phonestore.jdbc;

import com.phonestore.util.ConfigurationUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Objects;

public class JdbcConfig {

    private static Connection connection;
    private static String username;
    private static String password;
    private static String url;
    private static String driverClassName;

    static {
        username = ConfigurationUtil.getValueConfig("datasource.username");
        password = ConfigurationUtil.getValueConfig("datasource.password");
        url = ConfigurationUtil.getValueConfig("datasource.url");
        driverClassName = ConfigurationUtil.getValueConfig("datasource.driver-class-name");
    }

    private JdbcConfig() {}

    public static Connection openConnection() {
        try {
            Class.forName(driverClassName);
            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception ex) {
            throw new RuntimeException("Jdbc connect fail");
        }
        return connection;
    }

    public static void closeConnection() {
        try {
            if (Objects.nonNull(connection) && !connection.isClosed()) {
                connection.close();
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}