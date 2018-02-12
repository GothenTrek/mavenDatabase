/*
 * Author: George Othen
 * Title: Database Main
 * Date: 24/02/2016
 */
package com.mycompany.mavendatabase;

import java.io.IOException;
import java.sql.SQLException;
/**
 * 
 * @author Gurge
 */
public class DatabaseMain {
    public static void main(String[]args) throws IOException, SQLException, ClassNotFoundException{
        DatabaseTextInterface2 currentUse = new DatabaseTextInterface2();
        currentUse.runInterface();
    }    
}
