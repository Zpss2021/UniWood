package info.zpss.uniwood.server.util;

import info.zpss.uniwood.server.Main;
import info.zpss.uniwood.common.Arguable;

import java.sql.*;

public class Database implements Arguable {
    private String dbUrl;
    private String dbUsername;
    private String dbPassword;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            Main.logger().add(e, Thread.currentThread());
            System.exit(1);
        }
    }

    public Database() {
        this.dbUrl = null;
        this.dbUsername = null;
        this.dbPassword = null;
    }

    @Override
    public void config(String[] args) throws SQLException {
        dbUrl = Arguable.stringInArgs(args, "-u", "--url");
        dbUsername= Arguable.stringInArgs(args, "-n", "--username");
        dbPassword = Arguable.stringInArgs(args, "-p", "--password");
        if (dbUrl == null) {
            dbUrl = "jdbc:mysql://localhost:3306/uniwood" +
                    "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=GMT%2B8&useServerPrepStmts=true";
            Main.logger().add("未指定数据库URL，使用默认URL", ServerLogger.Type.INFO, Thread.currentThread());
        }
        if (dbUsername == null) {
            dbUsername = "zpss";
            Main.logger().add("未指定数据库用户名，使用默认用户名", ServerLogger.Type.INFO, Thread.currentThread());
        }
        if (dbPassword == null) {
            dbPassword = "henu";
            Main.logger().add("未指定数据库密码，使用默认密码", ServerLogger.Type.INFO, Thread.currentThread());
        }
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