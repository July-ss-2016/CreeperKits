package vip.creeper.mcserverplugins.creeperkits.managers;

import java.sql.*;

/**
 * Created by July on 2018/2/14.
 */
public class SQLiteManager {
    private Connection con;
    private String folderPath;
    private String database;

    public SQLiteManager(String folderPath, String database) {
        this.folderPath = folderPath;
        this.database = database;
    }

    public Connection getCon() {
        return con;
    }

    public boolean isConnected() {
        try {
            return con != null && !con.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + folderPath + "/" + database +  ".db");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return con != null;
    }

    public boolean disconnect() {
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean executeStatement(String s) {
        try {
            PreparedStatement preparedStatement = this.con.prepareStatement(s);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
