package me.frostythedev.oitq.sql;

import me.frostythedev.oitq.OITQ;

import java.sql.*;

/**
 * Programmed by Tevin on 5/14/2015.
 */
public class SQLite {

    private static String directory = OITQ.getInstance().getDataFolder().getAbsolutePath();
    private static String name = "database";

    private static Connection conn;
    private static String disabled = "this is disabled!";

    public static synchronized Connection getConnection() {
        try {
            if (conn == null || conn.isClosed()) {
                openConnection();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static synchronized void openConnection() {
        closeConnection();
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:" + directory + "/" + name + ".sqlite3");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(disabled);
        }
    }

    public static synchronized boolean hasConnection() {
        try {
            if (conn == null || conn.isClosed()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static synchronized void closeConnection() {
        try {
            if ((conn != null) && (conn.isClosed())) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();

            try {
                conn.close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    public static void createTable(String table, String fields) {
        try {
            Statement stmt = getConnection().createStatement();
            stmt.execute("CREATE TABLE `" + table + "` (" + fields + ")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean executeStatement(String sql) {
        Statement stmt = null;
        try {
            stmt = getConnection().createStatement();
            stmt.execute(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean updateTable(String table, String column, Object value, String row, Object where) {
        return executeStatement("UPDATE `" + table + "` SET `" + column + "` =" + value + " WHERE `" + row + "` = " + where);
    }

    public static ResultSet query(String sql) {
        Statement stmt;
        ResultSet rs;
        try {
            stmt = getConnection().createStatement();
            rs = stmt.executeQuery(sql);
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}

