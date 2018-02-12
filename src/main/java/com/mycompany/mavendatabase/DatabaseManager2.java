/*
 * Author: George Othen
 * Title: Database Manager 2
 * Date: 25/02/2016
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
 * @author George
 */
public class DatabaseManager2
{
    //Connection
    Connection conn;
    
    /**
     * 
     * @throws IOException failure to load properties file
     * @throws SQLException failure to connect to server
     * @throws ClassNotFoundException  failure to locate class
     */
    public DatabaseManager2() throws IOException, SQLException, ClassNotFoundException
    {
    InputStream stream = DatabaseManager2.class.getResourceAsStream("/database.properties");
    SimpleDataSource.init(stream);
    conn = SimpleDataSource.getConnection();
    }   

    /**
     * 
     * @param response the main menu response number, indicative of student, staff, module or registered
     * @param changes an array of the changes to be made to the new row being added
     * @param attributes an array of column names and the table name for which the data will be added to
     * @throws SQLException failure to add to the table
     */
    void add(String response, String[] changes, String[] attributes) throws SQLException
    {
        //see 'Decisions' method in interface class for attribute implementation
        String addCom = "INSERT INTO " + attributes[3] + "(`"+ attributes[0]+ "`, `" + attributes[1] + "`";
        if(!"4".equals(response)) addCom += ", `" + attributes[2] + "`)"; else addCom += ")";
        addCom += " VALUES ('" + changes[0] + "', '" + changes[1] + "'";
        if(!"4".equals(response)) addCom += ", '" + changes[2] +"')"; else addCom += ")";
        Statement stat = conn.createStatement();
            try
            {
                //execute query
                System.out.println(addCom);
                boolean addStu = stat.execute(addCom);
            }
            catch(SQLException e){}
    }
    
    /**
     * 
     * @param attributes an array of column names and the table name for which the data will be removed from
     * @param changes the id of the primary key value to identify the row that is to be deleted
     * @throws SQLException failure to delete a row from the table
     */
    public void delete(String response, String[] attributes, String[] changes) throws SQLException
    {        
        String delCom;
         //see 'Decisions' method in interface class for attribute implementation
        if("4".equals(response)){
            delCom = "DELETE FROM " + attributes[3] + " WHERE " + attributes[0] + "='" + changes[0] + "' AND " + attributes[1] + " ='" + changes[1] + "';";
        }
        else{
            delCom = "DELETE FROM " + attributes[3] + " WHERE " + attributes[0] + "='" + changes[0] + "';";
        }
        Statement stat = conn.createStatement();
            try
            {
                //execute query
                boolean delStu = stat.execute(delCom);
            }
            catch(SQLException e){}
    }
    
    /**
     * 
     * @param response the main menu response number, indicative of student, staff, module
     * @param columns an array of the changes to be made to the row being updated
     * @param attributes an array of column names and the table name for which the data will be updated
     * @param changes the current row data to identify the data that is to be updated
     * @throws SQLException failure to update the rows in the table
     */
    public void update(String response, int[] columns, String[] attributes, String[] changes) throws SQLException
    {    
         //see 'Decisions' method in interface class for attribute implementation
        String updCom = "UPDATE " + attributes[3];
        if(columns[0] == 2 && !"4".equals(response)){
            updCom += " SET " + attributes[1] + "='" + changes[1] + "', " + attributes[2] + "='" + changes[2] + "' WHERE " + attributes[0] + "='" + changes[0] + "';";
        }
        else if (columns[0] == 1){
            updCom += " SET ";
            if(columns[1] == 1) updCom += attributes[1] + "='" + changes[1] + "'";
            if(columns[1] == 2) updCom += attributes[2] + "='" + changes[2] + "'";
            updCom += " WHERE " + attributes[0] + "='" + changes[0] + "';";
        }
        Statement stat = conn.createStatement();
            try
            {
                //execute query
                boolean updStu = stat.execute(updCom);
            }
            catch(SQLException e){} 
    }
    
    /**
     * 
     * @param attributes an array containing the table name for which the data will be listed from
     * @return returns a formatted string of the resultset data from the predefined table name
     * @throws SQLException failure to obtain the result set
     */
    public String list(String[] attributes) throws SQLException
    {
         //see 'Decisions' method in interface class for attribute implementation
        Statement stat = conn.createStatement();
        String custString = "";
            try
            {
                //Select all rows from the table
                ResultSet rs = stat.executeQuery("SELECT * FROM " + attributes[3]);
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
     * @param staffName the name of the staff member to query
     * @return a formatted string containing the resultset of the query
     * @throws SQLException failure to perform the query
     */
    public String modulesTaughtBy(String staffName) throws SQLException{
        String custString = "";
        Statement stat = conn.createStatement();
        try
            {
                ResultSet rs = stat.executeQuery("SELECT `module`.`module_Id`, `module_name` " 
                    + "FROM `teaches`" 
                    + "JOIN `staff` ON `staff`.`staff_Id` = `teaches`.`staff_Id` "
                    + "JOIN `module` ON `module`.`module_Id` = `teaches`.`module_Id`"
                    + "WHERE `staff`.`staff_name` = '" + staffName + "';");
                ResultSetMetaData rsMeta = rs.getMetaData();
                int columnsNumber = rsMeta.getColumnCount();
                for(int i = 1; i <= columnsNumber; i++){
                    custString += (rsMeta.getColumnName(i) + "\t");
                    if(i == columnsNumber) custString += 
                            "\n----------------------------------------------\n";
                }
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
     * @param moduleName the name of the module to query
     * @return a formatted string containing the resultset of the query
     * @throws SQLException failure to perform the query
     */
    public String studentsRegisteredOn(String moduleName) throws SQLException{
        String custString = "";
        Statement stat = conn.createStatement();
        try
            {
                ResultSet rs = stat.executeQuery("SELECT `student`.`student_Id`, `student_name` " 
                    + "FROM `registered` "
                    + "JOIN `student` ON `student`.`student_Id` = `registered`.`student_Id` "
                    + "JOIN `module` ON `module`.`module_Id` = `registered`.`module_Id` "
                    + "WHERE `module`.`module_name` = '" + moduleName + "';");
                ResultSetMetaData rsMeta = rs.getMetaData();
                int columnsNumber = rsMeta.getColumnCount();
                for(int i = 1; i <= columnsNumber; i++){
                    custString += (rsMeta.getColumnName(i) + "\t");
                    if(i == columnsNumber) custString += 
                            "\n----------------------------------------------\n";
                }
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
     * @param studentName the name of the student to query
     * @return a formatted string containing the resultset of the query
     * @throws SQLException failure to perform the query
     */
    public String staffWhoTeachStudent(String studentName) throws SQLException{
        String custString = "";
        Statement stat = conn.createStatement();
        try
            {
                ResultSet rs = stat.executeQuery("SELECT `staff`.`staff_Id`, `staff_name`, `teaches`.`module_Id` "
                    + "FROM `teaches` "
                    + "JOIN `staff` ON `staff`.`staff_Id` = `teaches`.`staff_Id` "
                    + "JOIN `registered` ON `registered`.`module_Id` = `teaches`.`module_Id` "
                    + "JOIN `student` ON `student`.`student_Id` = `registered`.`student_Id` "
                    + "WHERE `student_name` = '" + studentName + "';");
                ResultSetMetaData rsMeta = rs.getMetaData();
                int columnsNumber = rsMeta.getColumnCount();
                for(int i = 1; i <= columnsNumber; i++){
                    custString += (rsMeta.getColumnName(i) + "\t");
                    if(i == columnsNumber) custString += 
                            "\n----------------------------------------------\n";
                }
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
     * @param count a decremented by one number to display all staff members who teach more than this values no. of modules
     * @return a formatted string containing the resultset of the query
     * @throws SQLException failure to perform the query
     */
    public String staffWhoTeachMoreThan(int count) throws SQLException{
        String custString = "";
        Statement stat = conn.createStatement();
        try
            {
                ResultSet rs = stat.executeQuery("SELECT `staff`.`staff_Id`, `staff_name` "
                    + "FROM `teaches` "
                    + "JOIN `staff` ON `staff`.`staff_Id` = `teaches`.`staff_Id` "
                    + "GROUP BY `staff_name` "
                    + "HAVING COUNT(*) > " + count);
                ResultSetMetaData rsMeta = rs.getMetaData();
                int columnsNumber = rsMeta.getColumnCount();
                for(int i = 1; i <= columnsNumber; i++){
                    custString += (rsMeta.getColumnName(i) + "\t");
                    if(i == columnsNumber) custString += 
                            "\n----------------------------------------------\n";
                }
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
     * @throws SQLException failure to close the connection
     */
    public void closeConnection() throws SQLException{
        conn.close();
    }    
}
