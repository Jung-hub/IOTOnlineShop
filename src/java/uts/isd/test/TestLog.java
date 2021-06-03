/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uts.isd.test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Calendar;
import java.util.logging.*;
import java.util.Scanner;
import uts.isd.model.LogList;
import uts.isd.model.dao.DBConnector;
import uts.isd.model.dao.LOGManager;


/**
 *
 * @author Administrator
 */
public class TestLog {
    private static Scanner in = new Scanner(System.in);
    private DBConnector connector;
    private Connection connection;
    private LOGManager logManager;
    
    public TestLog() throws SQLException {
        try {
            connector = new DBConnector();
            connection = connector.getConnection();
            logManager = new LOGManager(connection);
        
        }catch(ClassNotFoundException | SQLException ex) {
            Logger.getLogger(TestLog.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public char readChoice() {
        System.out.print("Operation CRUDS or * to exit: ");
        return in.nextLine().charAt(0);
    }
    
    private void testAdd() {
        System.out.print("Username: ");
        String username = in.nextLine();
                      
        System.out.print("LogType: login or logout ");
        String type = in.nextLine();
        
        try {
            logManager.addLog(username, type);
        }catch(SQLException ex) {
            Logger.getLogger(TestDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("New log is added into database.");
        
    }
    
    private void testRead() throws SQLException {
        System.out.print("Username: ");
        String username = in.nextLine();
        
        LogList logList = logManager.getLogsByUsername(username);
        logList.displayLogs();
    }
    
    private void testFilter() throws SQLException {
        System.out.print("Username: ");
        String username = in.nextLine();
        System.out.print("Month: ");
        String month = in.nextLine();
        System.out.print("Days: ");
        String days = in.nextLine();
        LogList logList = logManager.getLogsByNameAndDate(username, month, days);
        logList.displayLogs();
    }
    private void testUpdate() {
        System.out.print("Number: ");
        int number = Integer.parseInt(in.nextLine());
        try {
            if(logManager.findLog(number)) {
                //test update user profile
                System.out.print("Username: ");
                String username = in.nextLine();
                System.out.print("Type(login or logout): ");
                String type = in.nextLine();
                System.out.print("Year: ");
                int year = Integer.parseInt(in.nextLine());
                System.out.print("Month(1 - 12): ");
                int month = Integer.parseInt(in.nextLine());
                System.out.print("Days(1 - 30): ");
                int days = Integer.parseInt(in.nextLine());
                System.out.print("Hour(0 - 23): ");
                int hour = Integer.parseInt(in.nextLine());
                System.out.print("Minute(0 - 59): ");
                int minute = Integer.parseInt(in.nextLine());
                System.out.print("Second(0 - 59): ");
                int second = Integer.parseInt(in.nextLine());
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month - 1, days, hour, minute, second);
                Date date = new Date(calendar.getTimeInMillis());
                Time time = new Time(calendar.getTimeInMillis());
                logManager.updateLog(number, username, type, date, time);
                System.out.println("Log Number " + number + " password is updated.");
            }else {
                System.out.println("Log Number " + number + " does not exist in database.");
            }
            //dbManager.addUser(username, password, usertype);
        }catch(SQLException ex) {
            Logger.getLogger(TestLog.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //test DBManager.deleteUser()
    private void testDelete() {
        System.out.print("Number: ");
        int number = Integer.parseInt(in.nextLine());
        try {
            if(logManager.findLog(number)) {
                //test update user profile
                logManager.deleteLog(number);
                System.out.println("Log Number: " + number + " is removed from database.");
            }else {
                System.out.println("Log Number: " + number + " does not exist in database.");
            }
        }catch(SQLException ex) {
            Logger.getLogger(TestLog.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    private void testDeleteUserLog() {
        System.out.print("Username: ");
        String username = in.nextLine();
        try {
            if(logManager.findLogByUsername(username)) {
                //test update user profile
                logManager.deleteLog(username);
                System.out.println("Username: " + username + "'s logs are removed from database.");
            }else {
                System.out.println("Username: " + username + "'s logs do not exist in database.");
            }
        }catch(SQLException ex) {
            Logger.getLogger(TestLog.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    
    
    private void showAll() {
        try {
            LogList logList = logManager.getAllLogs();
            logList.displayLogs();
        }catch(SQLException ex) {
            Logger.getLogger(TestLog.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void runQueries() throws SQLException {
        char c;
        
        while((c = readChoice()) != '*') {
            switch(c) {
                case 'C':
                    testAdd();
                    break;
                case 'R':
                    testRead();
                    break;
                case 'U':
                    testUpdate();
                    break;
                case 'D':
                    testDelete();
                    testDeleteUserLog();
                    break;
                case 'F':
                    testFilter();
                    break;
                case 'S':
                    showAll();
                    break;
                default:
                    System.out.println("Unknown command");
                    break;
            }
        }
    
    }
    
    public static void main(String [] args) throws SQLException {
        new TestLog().runQueries();
    }
}
