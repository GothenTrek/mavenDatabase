/*
 * Author: George Othen
 * Title: Database Manager
 * Date: 24/02/2016
 */
package com.mycompany.mavendatabase;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author eeu24e
 */
public class DatabaseManager
{
    //Connection
    Connection conn;
    
    /**
     * 
     * @throws IOException failure to load properties file
     * @throws SQLException failure to execute sql query
     * @throws ClassNotFoundException failure to locate class
     */
    public DatabaseManager() throws IOException, SQLException, ClassNotFoundException
    {
        //Create an input stream to get properties files
        InputStream stream = DatabaseManager.class.getResourceAsStream("/database.properties");
        //Feed properties file into SimpleDataSource class
        SimpleDataSource.init(stream);
        //get connection to database with provided details in generated properties file
        conn = SimpleDataSource.getConnection();
    }

    /**
     * 
     * @param id the student_id of the student being added
     * @param name the name of the student being added
     * @param degree the name of the degree scheme the student is being added does
     * @throws SQLException failure to add student
     */
    public void addStudent(String id, String name, String degree) throws SQLException
    {        
        //Query to add student to student table
        String addStudentCom = "INSERT INTO student (`student_Id`, `student_name`, "
                + "`degree_scheme`) VALUES ('" + id + "', '" + name + "', '" + degree +"')";
        Statement stat = conn.createStatement();
            try
            {
                //execute query
                boolean addStu = stat.execute(addStudentCom);
            }
            catch(SQLException e){} 
    }
    
    /**
     * 
     * @param id student_id of the student being removed from the student table
     * @throws SQLException failure to remove student from the student table
     */
    public void delStudent(String id) throws SQLException
    {        
        //Query to remove student from student table
        String delStudentCom = "DELETE FROM student WHERE student_Id='" + id + "';";
        Statement stat = conn.createStatement();
            try
            {
                //execute query
                boolean delStu = stat.execute(delStudentCom);
            }
            catch(SQLException e){}
    }
    
    /**
     * 
     * @param id the student_id of the student row that's being updated
     * @param rows an array containing references to the columns being updated in a specific student row
     * @param attributes an array containing the information being updated
     * @throws SQLException failure to update the student row
     */
    public void updStudent(String id, int[] rows, String[] attributes) throws SQLException
    {        
        //Query to update student row in the student table
        String updStudentCom = "UPDATE student";
        if(rows[0] == 2){
            updStudentCom += " SET student_name='" + attributes[0] + "', degree_scheme='" + attributes[1] + "'" + " WHERE student_Id='" + id + "';";
        }
        else{
            updStudentCom += " SET ";
            if(rows[1] == 1) updStudentCom += "student_name='" + attributes[0] + "'";
            if(rows[1] == 2) updStudentCom += "degree_scheme='" + attributes[0] + "'";
            updStudentCom += " WHERE student_Id='" + id + "';";
        }
        
        Statement stat = conn.createStatement();
            try
            {
                //execute query
                boolean updStu = stat.execute(updStudentCom);
            }
            catch(SQLException e){} 
    }
    
    /**
     * 
     * @return returns a formatted string of the student table to the interface
     * @throws SQLException failure to read the student table
     */
    public String listStudent() throws SQLException{
        Statement stat2 = conn.createStatement();
        String custString = "";
            try
            {
                //Select all rows from the student table
                ResultSet rs = stat2.executeQuery("SELECT * FROM student");
                //get metadata from resultset
                ResultSetMetaData rsMeta = rs.getMetaData();
                //variable to hold the number of columns
                int columnsNumber = rsMeta.getColumnCount();
                //iterates through the column headers and formats into a string above a separator
                for(int i = 1; i <= columnsNumber; i++){
                    custString += (rsMeta.getColumnName(i) + "\t");
                    if(i == columnsNumber) custString += 
                            "\n----------------------------------------------\n";
                }
                
                //formats the row data into a string
                while (rs.next()) {
                    for (int i = 1; i <= columnsNumber; i++) {
                        custString += (rs.getString(i) + "\t");
                        if(i == columnsNumber) custString += "\n";
                    }
                }
            }
            catch(SQLException e){}          
            return custString;
    }
    
    /**
     * 
     * @return returns a formatted string of the staff table to the interface
     * @throws SQLException failure to read the staff table
     */
    public String listStaff() throws SQLException{
        Statement stat3 = conn.createStatement();
        String custString = "";
            try
            {
                //Select all rows from the staff table
                ResultSet rs = stat3.executeQuery("SELECT * FROM staff");
                //get metadata from resultset
                ResultSetMetaData rsMeta = rs.getMetaData();
                //variable to hold the number of columns
                int columnsNumber = rsMeta.getColumnCount();
                
                //iterates through the column headers and formats into a string above a separator
                for(int i = 1; i <= columnsNumber; i++){
                    custString += (rsMeta.getColumnName(i) + "\t");
                    if(i == columnsNumber) custString += 
                            "\n----------------------------------------------\n";
                }
                
                //formats the row data into a string
                while (rs.next()) {
                    for (int i = 1; i <= columnsNumber; i++) {
                        custString += (rs.getString(i) + "\t");
                        if(i == columnsNumber) custString += "\n";
                    }
                }
            }
            catch(SQLException e){}         
            return custString;
    }
    
    /**
     * 
     * @return returns a formatted string of the module table to the interface
     * @throws SQLException failure to read the module table
     */
    public String listModules() throws SQLException{
        Statement stat4 = conn.createStatement();
        String custString = "";
            try
            {
                //select all rows from module table
                ResultSet rs = stat4.executeQuery("SELECT * FROM module");
                //get metadata from resultset
                ResultSetMetaData rsMeta = rs.getMetaData();
                //variable to hold the number of columns
                int columnsNumber = rsMeta.getColumnCount();
                
                //iterates through the column headers and formats into a string above a separator
                for(int i = 1; i <= columnsNumber; i++){
                    custString += (rsMeta.getColumnName(i) + "\t");
                    if(i == columnsNumber) custString += 
                            "\n----------------------------------------------\n";
                }
                
                //formats the row data into a string
                while (rs.next()) {
                    for (int i = 1; i <= columnsNumber; i++) {
                        custString += (rs.getString(i) + "\t");
                        if(i == columnsNumber) custString += "\n";
                    }
                }
            }
            catch(SQLException e){}
            return custString;
    }
    
    /**
     * 
     * @return returns a formatted string of the registered table to the interface
     * @throws SQLException failure to read the registered table
     */
    public String listRegistrations() throws SQLException {
        Statement stat5 = conn.createStatement();
        String custString = "";
            try
            {
                //select all rows from the registered table
                ResultSet rs = stat5.executeQuery("SELECT * FROM registered");
                //get metadata from resultset
                ResultSetMetaData rsMeta = rs.getMetaData();
                //variable that holds the number of columns
                int columnsNumber = rsMeta.getColumnCount();
                
                //iterates through the column headers and formats into a string above a separator
                for(int i = 1; i <= columnsNumber; i++){
                    custString += (rsMeta.getColumnName(i) + "\t");
                    if(i == columnsNumber) custString += 
                            "\n----------------------------------------------\n";
                }
                
                //formats the row data into a string
                while (rs.next()) {
                    for (int i = 1; i <= columnsNumber; i++) {
                        custString += (rs.getString(i) + "\t\t");
                        if(i == columnsNumber) custString += "\n";
                    }
                }
            }
            catch(SQLException e){}
            return custString;
    }
    
    /**
     * 
     * @return returns a formatted string of the teaches table to the interface
     * @throws SQLException failure to read the teaches table
     */
    public String listTeaches() throws SQLException {
        Statement stat6 = conn.createStatement();
        String custString = "";
            try
            {
                //select all rows from the teaches table
                ResultSet rs = stat6.executeQuery("SELECT * FROM teaches");
                //get metadata from resultset
                ResultSetMetaData rsMeta = rs.getMetaData();
                //variable that holds the number of columns
                int columnsNumber = rsMeta.getColumnCount();
                
                //iterates through the column headers and formats into a string above a separator
                for(int i = 1; i <= columnsNumber; i++){
                    custString += (rsMeta.getColumnName(i) + "\t");
                    if(i == columnsNumber) custString += 
                            "\n----------------------------------------------\n";
                }
                
                //formats the row data into a string
                while (rs.next()) {
                    for (int i = 1; i <= columnsNumber; i++) {
                        custString += (rs.getString(i) + "\t\t");
                        if(i == columnsNumber) custString += "\n";
                    }
                }
            }
            catch(SQLException e){}
            return custString;
    }
    
    /**
     * 
     * @throws SQLException failure to close the connection to the database
     */
    public void closeConnection() throws SQLException{
        conn.close();
    }

    
}
