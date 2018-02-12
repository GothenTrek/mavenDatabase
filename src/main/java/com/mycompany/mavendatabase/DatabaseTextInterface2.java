/*
 * Author: George Othen
 * Title: Database Text Interface 2
 * Date: 25/02/2016
 */
package com.mycompany.mavendatabase;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;
import static jdk.nashorn.internal.runtime.JSType.isNumber;

/**
 *
 * @author George
 */
public class DatabaseTextInterface2
{
    //scanner for user input
    Scanner scanner = new Scanner(System.in);
    //database manager
    DatabaseManager2 manager;    
    //control runtime of program, controls whether teaches table is displayed
    boolean exit = true, teaches;
    //response of user in menu system
    String response;    
    
    //array to hold column headers to be altered
    String[] attributes = new String[4];
    //array to hold the information to be added, updated, etc
    String[] changes = new String[3];
    
    /**
     * 
     * @throws IOException failure to load properties files
     * @throws SQLException failure to execute query
     * @throws ClassNotFoundException failure to locate class
     */
    public DatabaseTextInterface2() throws IOException, SQLException, ClassNotFoundException
    {
        manager = new DatabaseManager2();
    }
    
    /**
     * 
     * @throws SQLException failure to close connection
     */
    public void runInterface() throws SQLException{
        //whilst still running
        while(exit){
            //main menu
            System.out.println("Main Menu\n********************");
            System.out.println("1. Student");
            System.out.println("2. Staff");
            System.out.println("3. Modules");
            System.out.println("4. Registrations");
            System.out.println("5. Reports");
            System.out.println("0. Quit");
            System.out.print("\n\n:> ");
            
            //take user input to control menu system
            response = scanner.nextLine();
            
            //redirect user to relevant locations
            if(isNumber(response)){
                if(Integer.parseInt(response) == 0) {manager.closeConnection(); exit = false;}
                else if(Integer.parseInt(response) >= 1 && Integer.parseInt(response) <= 4) {SubMenu();}
                else if(Integer.parseInt(response) == 5) {Reports();}
                else {System.out.println("This is not one of the options available");}
            }
            else{
                System.out.println("Unrecognised Input");
            }
            
        }
    }
    
    /**
     * 
     * @throws SQLException failure to execute query
     */
    private void SubMenu() throws SQLException
    {
        //set attributes array and changes array based on 'response'
        Decisions();
        
        //print sub-menu system
        System.out.println("Sub Menu (" + attributes[3] + ")\n********************");
        System.out.println("1. Add " + attributes[3]);
        System.out.println("2. Remove " + attributes[3]);
        if(!"4".equals(response)){
            System.out.println("3. Update " + attributes[3]);
            System.out.println("4. List " + attributes[3]);
        }
        else if("2".equals(response)){
            System.out.println("5. List Staff Modules" + attributes[3]);
        }
        else {System.out.println("3. List " + attributes[3]);}
        System.out.println("0. Main Menu");
        System.out.print("\n\n:> ");
        
        //accept user sub-menu response
        String subResponse = scanner.nextLine();        
        
        //navigate through sub-menu items
        switch(subResponse){
            case "1":
                Add();
                break;
            case "2":
                Remove();
                break;
            case "3":
                if(!"4".equals(response)){
                Update();
                }else{List();}
                break;
            case "4":
                List();
                break;
            case "5":
                if("2".equals(response)){
                teaches = true;
                List();}
                break;
            case "0":
                break;
            default:
                System.out.println("This is not an option");
                break;
        }
    }

    /**
     * 
     * @throws SQLException failure to perform query
     */
    private void Reports() throws SQLException {
        System.out.println("Reports\n********************");
        System.out.println("1. Modules taught by");
        System.out.println("2. Students registered on");
        System.out.println("3. Staff who teach student");
        System.out.println("4. Staff who teach more than");
        System.out.println("0. Main Menu");
        System.out.print("\n\n:> ");
        
        //take user input to navigate reports sub-menu
        String subResponse = scanner.nextLine();        
        
        //switch user input for relevant queries
        switch(subResponse){
            case "1":
                System.out.print("\nInsert the name of the staff member: ");
                System.out.println(manager.modulesTaughtBy(scanner.nextLine()));
                break;
            case "2":
                System.out.print("\nInsert the name of the module: ");
                System.out.println(manager.studentsRegisteredOn(scanner.nextLine()));
                break;
            case "3":
                System.out.print("\nInsert the name of the student: ");
                System.out.println(manager.staffWhoTeachStudent(scanner.nextLine()));
                break;
            case "4":
                System.out.print("\nInsert a decremented by 1 number of modules the" 
                        + " staff member teaches: ");
                System.out.println(manager.staffWhoTeachMoreThan(Integer.parseInt(scanner.nextLine())));
                break;
            default:
                System.out.println("Unrecognised Input");
                break;
        }
    }
    
    /**
     * 
     * @throws SQLException failure to add row to table
     */
    private void Add() throws SQLException{   
        //set attributes array and changes array based on 'response'
        Decisions();
        
        //take user input to add to rows in future
        System.out.println("Add "+ attributes[3] + ": Insert Details Below");
        System.out.print("\nEnter " + attributes[0] + ": "); changes[0] = scanner.nextLine();
        System.out.print("\nEnter " + attributes[1] + ": "); changes[1] = scanner.nextLine();
        if(!"4".equals(response)) {System.out.print("\nEnter " + attributes[2] 
                + ": "); changes[2] = scanner.nextLine();}        
        //input data to manager
        manager.add(response, changes, attributes);
        
        //confirmation
        System.out.println("1 row added to " + attributes[3]);
    }        

    /**
     * 
     * @throws SQLException failure to remove row from table
     */
    private void Remove() throws SQLException
    {
        //set attributes array and changes array based on 'response'
        Decisions();
        
        //take id from user to delete undesired row
        System.out.println("Remove "+ attributes[3] + ": Insert Details Below");
        System.out.print("\nEnter " + attributes[0] + ": "); changes[0] = scanner.nextLine();
        if("4".equals(response)){
            System.out.print("\nEnter " + attributes[1] + ": "); changes[1] = scanner.nextLine();
        }
        
        //input data to manager
        manager.delete(response, attributes, changes);
        
        //confirmation
        System.out.println("1 row removed from " + attributes[3]);
    }

    /**
     * 
     * @throws SQLException failure to update row/column in table
     */
    private void Update() throws SQLException
    {
        //set attributes array and changes array based on 'response'
        Decisions();

        //hold column positions and number of columns to be affected
        int[] columns = new int[3];
        String fixInt; //error prevention string
        
        //take user input for id of primary key that identifies row for being updated
        System.out.print("\nEnter " + attributes[3] + " ID of " + attributes[3] 
                + " to Update: "); changes[0] = scanner.nextLine();
        System.out.print("\nHow many columns are you Updating? "); fixInt = scanner.nextLine();
        
        //check if input is valid
        if(isNumber(fixInt)) columns[0] = Integer.parseInt(fixInt);

        //switch number of columns user is planning to update
        switch(columns[0]){
            case 0:
                System.out.println("0 columns will be updated");
                break;
            case 1:
                System.out.println("1 column will be updated. Which attribute are you changing?");
                System.out.print("1. " + attributes[1] + "\n2. " + attributes[2] + "\n:> ");
                fixInt = scanner.nextLine();
                //if input is valid
                if(isNumber(fixInt)){
                    System.out.print("What do you want to replace that with? : ");
                    //take in information to replace current
                    columns[1] = Integer.parseInt(fixInt);
                        if(columns[1] == 1){  
                            changes[1] = scanner.nextLine();
                        }
                        else if(columns[1] == 2){
                            changes[2] = scanner.nextLine();
                        }
                }
                else {System.out.println("Incorrect Input"); columns[0] = 0;} //invalid input, going back to menu
                break;                
            case 2:
                System.out.println("All columns will be updated.");
                //take in information to replace current
                System.out.print("\nWhat do you want to replace " + attributes[1] + " with? : ");
                changes[1] = scanner.nextLine();
                System.out.print("\nWhat do you want to replace " + attributes[2] + " with? : ");
                changes[2] = scanner.nextLine();
                break;
            default:
                System.out.println("Unrecognised input");
                break;
        }
        //only update if valid input
        if(columns[0] != 0) manager.update(response, columns, attributes, changes);
    }
    
    /**
     * 
     * @throws SQLException failure to read table data
     */
    private void List() throws SQLException
    {
        //set attributes array and changes array based on 'response'
        Decisions();
        //list table, name provided in "attributes"
        System.out.println(manager.list(attributes));
    }   

    /**
     * a method that determines whether the user is wanting to add, remove, update or list: student OR staff OR module OR registered
     */
    private void Decisions()
    {
        //student is selected, fill attributes array elements 0-2 with column header names && element 3 with table name
        if("1".equals(response)){
             attributes[0] = "student_Id"; attributes[1] = "student_name"; 
             attributes[2] = "degree_scheme"; attributes[3] = "student";
        }        
        if("2".equals(response)){
            attributes[0] = "staff_Id"; attributes[1] = "staff_name"; attributes[2] = "staff_grade"; 
            //if teaches is true, set table name to 'teaches' to list that tables data
            if(!teaches) attributes[3] = "staff"; else{ attributes[3] = "teaches"; teaches = false;} 
        }        
        if("3".equals(response)){
            attributes[0] = "module_Id"; attributes[1] = "module_name"; attributes[2] = "credits"; 
            attributes[3] = "module";
        }        
        if("4".equals(response)){
            attributes[0] = "student_Id"; attributes[1] = "module_Id"; attributes[3] = "registered";
        }
    }
}
