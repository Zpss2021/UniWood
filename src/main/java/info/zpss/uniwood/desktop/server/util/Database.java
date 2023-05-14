package info.zpss.uniwood.desktop.server.util;

import info.zpss.uniwood.desktop.common.Log;

import java.sql.*;

public class Database {
    private final String dbUrl;
    private final String dbUsername;
    private final String dbPassword;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            Log.add(e, Thread.currentThread());
            System.exit(1);
        }
    }

    public Database(String dbUrl, String dbUsername, String dbPassword) throws SQLException {
        this.dbUrl = dbUrl;
        this.dbUsername = dbUsername;
        this.dbPassword = dbPassword;
        tryConnect();
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
    }

    private void tryConnect() throws SQLException {
        Connection connection = getConnection();
        connection.close();
    }
}