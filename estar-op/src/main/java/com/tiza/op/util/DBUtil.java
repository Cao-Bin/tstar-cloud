package com.tiza.op.util;

import org.apache.hadoop.conf.Configuration;

import java.sql.*;

/**
 * Description: DBUtil
 * Author: DIYILIU
 * Update: 2017-09-28 09:49
 */
public class DBUtil {

    private final static String DATABASE_DRIVER = "tstar.op.database.driver";
    private final static String DATABASE_URL = "tstar.op.database.url";
    private final static String DATABASE_USERNAME = "tstar.op.database.username";
    private final static String DATABASE_PASSWORD = "tstar.op.database.password";


    public static Connection getConnection(Configuration configuration) throws Exception{
        String driver = configuration.get(DATABASE_DRIVER);
        String url = configuration.get(DATABASE_URL);
        String username = configuration.get(DATABASE_USERNAME);
        String password = configuration.get(DATABASE_PASSWORD);

        Class.forName(driver);
        return DriverManager.getConnection(url, username, password);
    }

    public static void close(Connection connection){

        if (connection != null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void close(Statement statement){
        if (statement != null){
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void close(ResultSet resultSet){
        if (resultSet != null){
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
