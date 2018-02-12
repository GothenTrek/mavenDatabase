/*
 * Author: George Othen
 * Title: Simple Data Source
 * Date: 23/02/2016
 */
package com.mycompany.mavendatabase;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author Gurge
 */
public class SimpleDataSource {
    private static String url;
    private static String username;
    private static String password;
    
    /**
     * 
     * @param stream file input properties file
     * @throws IOException failure to load properties files
     * @throws ClassNotFoundException  failure to locate class
     */
    public static void init(InputStream stream) throws IOException, ClassNotFoundException
    {
        Properties props = new Properties();
        //FileInputStream in = new FileInputStream(fileName);
        props.load(stream);
        
        String driver = props.getProperty("jdbc.driver");
        url = props.getProperty("jdbc.url");
        username = props.getProperty("jdbc.username");
        if(username == null) { username = "";}
        password = props.getProperty("jdbc.password");
        if(password == null) { password = ""; }
        if (driver != null) { Class.forName(driver); }
    }
    
    /**
     * 
     * @return connection to driver manager
     * @throws SQLException failure to connect to sql server
     */
    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(url, username, password);
    }
}
