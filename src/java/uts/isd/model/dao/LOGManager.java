/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uts.isd.model.dao;
import uts.isd.model.*;
import java.sql.*;
import java.util.Random;
 

/**
 *
 * @author Administrator
 */
public class LOGManager {
    private PreparedStatement preparedStmt;
    private Connection connection;
    private ResultSet resultSet;
    
    public LOGManager(Connection connection) throws SQLException {       
          this.connection = connection;
          this.preparedStmt = null;
          this.resultSet = null;
    }
    
    public void addLog(String username, String type) throws SQLException {
        
        String queryGetLine = "SELECT NUMBER FROM ROOT.LOGS WHERE NUMBER = ?";
        Random rand = new Random();
        int getInt = 0;
        int upperbound = 1000;
        do {
            getInt = rand.nextInt(upperbound);
            this.preparedStmt = connection.prepareStatement(queryGetLine);
            this.preparedStmt.setInt(1, getInt);
            resultSet = preparedStmt.executeQuery();
        }while(resultSet.next());
        
        preparedStmt.close();
        resultSet.close();
                
        String query = "INSERT INTO ROOT.LOGS " + 
            "(NUMBER,USERNAME,DATE,TIME,TYPE) " +
            " VALUES(?,?,?,?,?)";
        this.preparedStmt = connection.prepareStatement(query);
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        Time time = new Time(now);
        this.preparedStmt.setInt(1, getInt);
        this.preparedStmt.setString(2, username);
        this.preparedStmt.setDate(3, date);
        this.preparedStmt.setTime(4, time);
        this.preparedStmt.setString(5, type);
                
        //Execute the query, then return a value for storing successfully
        int row = preparedStmt.executeUpdate();
        preparedStmt.close();
        
    }
    
    public boolean findLog(int number) throws SQLException {
        String fetch = "SELECT * FROM ROOT.LOGS " +
            "WHERE NUMBER = ?";
        this.preparedStmt = connection.prepareStatement(fetch);
        this.preparedStmt.setInt(1, number);
        resultSet = preparedStmt.executeQuery();
        try {
            if(resultSet.next()) {
                resultSet.close();
                return true;
            }
        }catch(SQLException ex) {
            resultSet.close(); 
            
        } 
        return false;   
            
    }
    
    public boolean findLogByUsername(String username) throws SQLException {
        String fetch = "SELECT * FROM ROOT.LOGS " +
            "WHERE USERNAME = ?";
        this.preparedStmt = connection.prepareStatement(fetch);
        this.preparedStmt.setString(1, username);
        resultSet = preparedStmt.executeQuery();
        try {
            if(resultSet.next()) {
                resultSet.close();
                return true;
            }
        }catch(SQLException ex) {
            resultSet.close(); 
            
        } 
        return false;   
            
    }
    
    
    
    public void updateLog(int number, String username,
            String type, Date date, Time time) throws SQLException {
        String query = "UPDATE ROOT.LOGS SET " + 
            "USERNAME = ?, TYPE = ?, DATE = ?, TIME = ?" + 
            "WHERE NUMBER = ?";
        //Store values into each column
        try {
            preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString(1, username);
            preparedStmt.setString(2, type);
            preparedStmt.setDate(3, date);
            preparedStmt.setTime(4, time);
            preparedStmt.setInt(5, number);
               
            //Execute the query and get a reponse from database
            preparedStmt.executeUpdate();
        }catch(SQLException ex) {
                       
        } 
       
    }
       
    
    public void deleteLog(int number) throws SQLException {
        String query = "DELETE FROM ROOT.LOGS WHERE NUMBER = ?";
        if(findLog(number)) {
            preparedStmt = connection.prepareStatement(query);
            preparedStmt.setInt(1, number);
            preparedStmt.executeUpdate();
            preparedStmt.close();
        }
    }
    
    
    public void deleteLog(String username) throws SQLException {
        String query = "DELETE FROM ROOT.LOGS WHERE USERNAME = ?";
        if(findLogByUsername(username)) {
            preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString(1, username);
            preparedStmt.executeUpdate();
            preparedStmt.close();
        }
    }
    
    
    
    
    
    public LogList getLogsByUsername(String username) throws SQLException {
        String fetch = "SELECT NUMBER,DATE,TIME,TYPE FROM ROOT.LOGS " + 
                "WHERE USERNAME=?";
        this.preparedStmt = connection.prepareStatement(fetch);
        this.preparedStmt.setString(1, username);
        resultSet = preparedStmt.executeQuery();
        
        LogList logList = new LogList();
        
        while(resultSet.next()) {
            int number = resultSet.getInt("NUMBER");
            String type = resultSet.getString("TYPE");
            Date date = resultSet.getDate("DATE");
            Time time = resultSet.getTime("TIME");
            logList.addLog(new Log(number, username, type, date, time));
        }
        resultSet.close();
        return logList;
    }
    
    public LogList getLogsByNameAndDate(String username, String month,
            String day) throws SQLException {
        LogList filterList = getLogsByUsername(username).getListByDate(month, day);
        return  filterList;
    }
    
    public LogList getAllLogsByDate(String month,
            String day) throws SQLException  {
    
        LogList filterList = getAllLogs().getListByDate(month, day);
        return  filterList;
    }
    
    public LogList getAllLogs() throws SQLException {
        String fetch = "SELECT NUMBER,USERNAME,TYPE,DATE,TIME FROM ROOT.LOGS ";
        this.preparedStmt = connection.prepareStatement(fetch);
        resultSet = preparedStmt.executeQuery();
        
        LogList logList = new LogList();
        
        while(resultSet.next()) {
            int number = resultSet.getInt("NUMBER");
            String username = resultSet.getString("USERNAME");
            String type = resultSet.getString("TYPE");
            Date date = resultSet.getDate("DATE");
            Time time = resultSet.getTime("TIME");
            logList.addLog(new Log(number, username, type, date, time));
        }
        resultSet.close();
        return logList;
    }
    
    
    
    
}