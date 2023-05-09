package info.zpss.uniwood.desktop.server.util;

import info.zpss.uniwood.desktop.common.Log;

import java.sql.*;

public class Database {
    private final String db_url;
    private final String db_username;
    private final String db_password;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            Log.add(e, Thread.currentThread());
            System.exit(1);
        }
    }

    public Database(String db_url, String db_username, String db_password) throws SQLException {
        this.db_url = db_url;
        this.db_username = db_username;
        this.db_password = db_password;
        tryConnect();
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(db_url, db_username, db_password);
    }

    private void tryConnect() throws SQLException {
        Connection connection = getConnection();
        connection.close();
    }
}