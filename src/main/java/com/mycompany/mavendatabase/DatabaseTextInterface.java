/*
 * Author: George Othen
 * Title: Database Text Interface
 * Date: 24/02/2016
 */
package com.mycompany.mavendatabase;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

/**
 *
 * @author eeu24e
 */
public class DatabaseTextInterface 
{
    //creates a database manager to handle sql requests
    DatabaseManager manager;
    //scanner to accept user input
    Scanner scanner = new Scanner(System.in);
    //determines whether program continues to run
    boolean exit = true;
    
    /**
     * 
     * @throws IOException failure to read properties files
     * @throws SQLException failure to execute query
     * @throws ClassNotFoundException  failure to locate class
     */
    public DatabaseTextInterface() throws IOException, SQLException, ClassNotFoundException
    {
        manager = new DatabaseManager();
    }
    
    /**
     * 
     * @throws SQLException failure to close connection
     */
    public void runInterface() throws SQLException{
        //While still running, run
        while(exit){
            //display menu
            System.out.println("Main Menu\n********************");
            System.out.println("1. List student");
            System.out.println("2. List staff");
            System.out.println("3. List modules");
            System.out.println("4. List registrations");
            System.out.println("5. List courses taught by staff");
            System.out.println("6. Add student");
            System.out.println("7. Delete student");
            System.out.println("8. Update student");
            System.out.println("9. Reports");
            System.out.println("0. Quit");
            System.out.print("\n\n:> ");
            //accept user input for menu item
            String response = scanner.nextLine();

            //switch that directs user through menu system using methods
            switch(response){
                case "1":
                    ListStudent();
                    break;
                case "2":
                    ListStaff();
                    break;
                case "3":
                    ListModules();
                    break;
                case "4":
                    ListRegistrations();
                    break;
                case "5":
                    ListCoursesTaughtByStaff();
                    break;
                case "6":
                    AddStudent();
                    break;
                case "7":
                    DelStudent();
                    break;
                case "8":
                    UpdStudent();
                    break;
                case "9":
                    Reports();
                    break;
                case "0":
                    //close connection and exit the program
                    manager.closeConnection();
                    exit = false;
                    break;
                default:
                    System.out.println("This is not one of the options available");
                    break;
            }       
        }
    }
    
    /**
     * 
     * @throws SQLException failure to add student to student table
     */
    public void AddStudent() throws SQLException
    {
        //variables to hold student details
        String student_id, student_name, student_deg;
        student_id = scanner.nextLine(); //prevent relevant scanner skipping
        
        //Take in student details to add to student table
        System.out.println("Add Student: Insert Details Below");
        System.out.print("\nEnter Student ID: "); student_id = scanner.nextLine();
        System.out.print("\nEnter Student Name: ");student_name = scanner.nextLine();
        System.out.print("\nEnter Student Degree Scheme: ");student_deg = scanner.nextLine();

        //input details into manager
        manager.addStudent(student_id, student_name, student_deg);
        
        //confirmation
        System.out.println("1 row added to student: " + student_id + " " + student_name + " " + student_deg);
    }

    /**
     * 
     * @throws SQLException failure to get table
     */
    private void ListStudent() throws SQLException {        
        System.out.println(manager.listStudent());
        scanner.nextLine();
    }

    /**
     * 
     * @throws SQLException failure to get table
     */
    private void ListStaff() throws SQLException {
        System.out.println(manager.listStaff());
        scanner.nextLine();
    }

    /**
     * 
     * @throws SQLException failure to get table
     */
    private void ListModules() throws SQLException {
        System.out.println(manager.listModules());
        scanner.nextLine();
    }

    /**
     * 
     * @throws SQLException failure to get table
     */
    private void ListRegistrations() throws SQLException {
        System.out.println(manager.listRegistrations());
        scanner.nextLine();
    }

    /**
     * 
     * @throws SQLException failure to get table
     */
    private void ListCoursesTaughtByStaff() throws SQLException {
        System.out.println(manager.listTeaches());
        scanner.nextLine();
    }

    /**
     * 
     * @throws SQLException failure to delete student row
     */
    private void DelStudent() throws SQLException {
        String student_id;
        student_id = scanner.nextLine(); //prevent relevant scanner skipping
        
        //take input from user, student_id to mark row for deletion
        System.out.print("\nEnter Student ID of Student to Delete: "); student_id = scanner.nextLine();
        
        //send user input to manager
        manager.delStudent(student_id);
    }

    /**
     * 
     * @throws SQLException failure to update student row
     */
    private void UpdStudent() throws SQLException {
        //identifying primary key value to update relevant student row
        String student_id;
        //store the number of columns being updated and name those columns
        int[] columns = new int[3];
        student_id = scanner.nextLine(); //prevent relevant scanner skipping
        
        //take user input
        System.out.print("\nEnter Student ID of Student to Update: "); student_id = scanner.nextLine();
        
        //request number of columns to update
        System.out.print("\nHow many rows are you Updating? "); columns[0] = scanner.nextInt();
        switch(columns[0]){
            case 0:
                System.out.println("0 rows will be updated");
                break;
            case 1:
                //which of the 2 available columns will be updated?
                System.out.println("1 row will be updated. Which attribute are you changing?");
                System.out.print("1. student_name\n2. student_degree\n:> ");
                columns[1] = scanner.nextInt();
                break;                
            case 2:
                System.out.println("All rows will be updated.");
                break;
            default:
                //don't attempt to update
                columns[0] = 0;
                break;
        }
        if(columns[0] != 0) manager.updStudent(student_id, columns, getNameInputs(columns[0]));
    }

    /**
     * 
     * @param i number of inputs the user will be providing
     * @return an array of inputs from the user
     */
    private String[] getNameInputs(int i){
        String[] custString = new String[3];
        custString[0] = scanner.nextLine(); //prevent skipping of relevant scanner
        
        //for the number of attributes the user is changing, take input
        for(int j = 1; j <= i; j++){
            System.out.print("\nEnter attribute " + (j) + ": ");
            custString[j-1] = scanner.nextLine();

        }
        return custString;
    }
    
    /**
     * an unfinished method to hold reports
     */
    private void Reports() {
        System.out.println("Not supported yet.");
    }
}
