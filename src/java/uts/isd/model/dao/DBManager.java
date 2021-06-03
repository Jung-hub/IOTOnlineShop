/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uts.isd.model.dao;

import uts.isd.model.*;
import java.sql.*;
/**
 *
 * @author Administrator
 */
public class DBManager {
    private PreparedStatement preparedStmt;
    private Connection connection;
    private ResultSet resultSet;
    
    public DBManager(Connection connection) throws SQLException {       
          this.connection = connection;
          this.preparedStmt = null;
          this.resultSet = null;
    }
    
    //find the specific user by username and password
    public User findUser(String username, String password) throws SQLException {
        String fetch = "SELECT * FROM ROOT.USERS " +
            "WHERE USERNAME = ? AND PASSWORD = ?";
        this.preparedStmt = connection.prepareStatement(fetch);
        this.preparedStmt.setString(1, username);
        this.preparedStmt.setString(2, password);
        resultSet = preparedStmt.executeQuery();
        
        while(resultSet.next()) {
            String userName = resultSet.getString(1);
            String userPassword = resultSet.getString(2);
            if(userName.equals(username) && userPassword.equals(password))
                return new User(userName, userPassword, 
                        resultSet.getString(3), 
                        resultSet.getString(4),  
                        resultSet.getString(5), 
                        resultSet.getString(6), 
                        resultSet.getString(7), 
                        resultSet.getString(8),
                        resultSet.getString(9)
                        );
        }
        return null;
    }
    
    public User findUserByUserName(String username) throws SQLException {
        String fetch = "SELECT * FROM ROOT.USERS " +
            "WHERE USERNAME = ?";
        this.preparedStmt = connection.prepareStatement(fetch);
        this.preparedStmt.setString(1, username);
        resultSet = preparedStmt.executeQuery();
        
        while(resultSet.next()) {
            String userName = resultSet.getString(1);
            if(userName.equals(username))
                return new User(userName,
                        resultSet.getString(2),
                        resultSet.getString(3), 
                        resultSet.getString(4),  
                        resultSet.getString(5), 
                        resultSet.getString(6), 
                        resultSet.getString(7), 
                        resultSet.getString(8),
                        resultSet.getString(9)
                        );
        }
        resultSet.close();
        return null;
    }
    
    
    public UserAccount getAllUsers() throws SQLException{
        String fetch = "SELECT * FROM ROOT.USERS";
        this.preparedStmt = connection.prepareStatement(fetch);
        resultSet = preparedStmt.executeQuery();
        UserAccount accountList = new UserAccount();
        
        while(resultSet.next()) {
            
            accountList.setAnUser(new User(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3), 
                        resultSet.getString(4),  
                        resultSet.getString(5), 
                        resultSet.getString(6), 
                        resultSet.getString(7), 
                        resultSet.getString(8),
                        resultSet.getString(9)
                        ));
        }
        resultSet.close();
        return accountList;
    }
    public UserAccount getAllUsersByUsertype(String usertype) throws SQLException {
        String fetch = "SELECT * FROM ROOT.USERS WHERE USERTYPE = ?";
        this.preparedStmt = connection.prepareStatement(fetch);
        this.preparedStmt.setString(1, usertype);
        resultSet = preparedStmt.executeQuery();
        UserAccount accountList = new UserAccount();
        
        while(resultSet.next()) {
            accountList.setAnUser(
                    new User(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3), 
                        resultSet.getString(4),  
                        resultSet.getString(5), 
                        resultSet.getString(6), 
                        resultSet.getString(7), 
                        resultSet.getString(8),
                        resultSet.getString(9)
                    ));
            
        }
        return accountList;
    }
    
    public UserAccount getAllUsersByKeyWord(String usertype, String keyword) throws SQLException {
        if(keyword.equals("")) return getAllUsersByUsertype(usertype);
        String fetch = "SELECT * FROM ROOT.USERS WHERE USERTYPE = ?";
        this.preparedStmt = connection.prepareStatement(fetch);
        this.preparedStmt.setString(1, usertype);
        resultSet = preparedStmt.executeQuery();
        UserAccount accountList = new UserAccount();
        
        while(resultSet.next()) {
            String username = resultSet.getString(1);
            String fullName = resultSet.getString(4) + " " + resultSet.getString(5);
            String phone = resultSet.getString(6);
            if(fullName.toLowerCase().equals(keyword.toLowerCase()) ||
                    phone.equals(keyword) || username.equals(keyword)) {
                accountList.setAnUser(
                    new User(
                        username,
                        resultSet.getString(2),
                        resultSet.getString(3), 
                        resultSet.getString(4),  
                        resultSet.getString(5), 
                        phone, 
                        resultSet.getString(7), 
                        resultSet.getString(8),
                        resultSet.getString(9)
                    ));
            }
        }
        return accountList;
    }
    
    public UserAccount getAllUsersWithoutRoot() throws SQLException {
        String fetch;
        fetch = "SELECT * FROM ROOT.USERS WHERE USERTYPE != ?"; 
              
        this.preparedStmt = connection.prepareStatement(fetch);
        this.preparedStmt.setString(1, "0");
        resultSet = preparedStmt.executeQuery();
        UserAccount accountList = new UserAccount();
        
        while(resultSet.next()) {
            accountList.setAnUser(
                    new User(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3), 
                        resultSet.getString(4),  
                        resultSet.getString(5), 
                        resultSet.getString(6), 
                        resultSet.getString(7), 
                        resultSet.getString(8),
                        resultSet.getString(9)
                    )
                );
            
        }
        return accountList;
    }
        
    public UserAccount getAllUsersByUsername(String username) throws SQLException {
        String fetch = "SELECT * FROM ROOT.USERS";
        this.preparedStmt = connection.prepareStatement(fetch);
        resultSet = preparedStmt.executeQuery();
        UserAccount accountList = new UserAccount();
        
        while(resultSet.next()) {
            //if(resultSet.getString(1).contains(username) || username.equals(resultSet.getString(6))
                    //|| (username.toLowerCase().contains(resultSet.getString(4).toLowerCase()) && username.toLowerCase().contains(resultSet.getString(5).toLowerCase()))) {
            if(resultSet.getString("USERNAME").toLowerCase().equals(username.toLowerCase())) {    
                accountList.setAnUser(
                    new User(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3), 
                        resultSet.getString(4),  
                        resultSet.getString(5), 
                        resultSet.getString(6), 
                        resultSet.getString(7), 
                        resultSet.getString(8),
                        resultSet.getString(9)
                        )
                );
            }
        }
        return accountList;
    
    }
    
    public UserAccount getAllUsersByKeyWord(String keyword) throws SQLException {
        if(keyword.equals("")) return getAllUsersWithoutRoot(); 
        String fetch; 
        fetch = "SELECT * FROM ROOT.USERS WHERE USERTYPE != ?"; 
        this.preparedStmt = connection.prepareStatement(fetch);
        this.preparedStmt.setString(1, "0");
        resultSet = preparedStmt.executeQuery();
        UserAccount accountList = new UserAccount();
        
        while(resultSet.next()) {
                //if(resultSet.getString(1).toLowerCase().contains(keyword.toLowerCase()) ||
                        //keyword.equals(resultSet.getString(6)) ||
                        //(keyword.toLowerCase().contains(resultSet.getString(4).toLowerCase()) &&
                        //keyword.toLowerCase().contains(resultSet.getString(5).toLowerCase()))) {
                String username = resultSet.getString(1);
                String fullName = resultSet.getString(4) + " " + resultSet.getString(5);
                String phone = resultSet.getString(6);
                if(username.equals(keyword) || fullName.toLowerCase().equals(keyword.toLowerCase()) || phone.equals(keyword)) {
                    accountList.setAnUser(
                        new User(
                            username,
                            resultSet.getString(2),
                            resultSet.getString(3), 
                            resultSet.getString(4),  
                            resultSet.getString(5), 
                            phone, 
                            resultSet.getString(7), 
                            resultSet.getString(8),
                            resultSet.getString(9)
                            )
                    );
                }
        }
        return accountList;
    
    }
    
    public void addUser(String username, String password,
            String usertype, String email, String status) throws SQLException {
        if(!isUsernameExist(username)) {
            String query = "INSERT INTO USERS " + 
            "(USERNAME,PASSWORD,USERTYPE,FIRSTNAME,LASTNAME,PHONE,EMAIL,DOB,STATUS) " +
            " VALUES(?,?,?,'','','',?,'',?)";
            this.preparedStmt = connection.prepareStatement(query);
            this.preparedStmt.setString(1, username);
            this.preparedStmt.setString(2, password);
            this.preparedStmt.setString(3, usertype);
            this.preparedStmt.setString(4, email);
            this.preparedStmt.setString(5, status);
        
            //Execute the query, then return a value for storing successfully
            int executeUpdate = preparedStmt.executeUpdate();
        }
    }
    
    public void updateUserProfile(String username, String password, 
            String firstname, String lastname, String email, String birthday, 
            String phone) throws SQLException {
        String query = "UPDATE ROOT.USERS SET " + 
            "FIRSTNAME = ?, LASTNAME = ?, EMAIL = ?, DOB = ?, PHONE = ? " + 
            "WHERE USERNAME = ? AND PASSWORD = ?";
        //Store values into each column
        preparedStmt = connection.prepareStatement(query);
        preparedStmt.setString(1, firstname);
        preparedStmt.setString(2, lastname);
        preparedStmt.setString(3, email);
        preparedStmt.setString(4, birthday);
        preparedStmt.setString(5, phone);
        preparedStmt.setString(6, username);
        preparedStmt.setString(7, password);
        
        //Execute the query and get a reponse from database
        preparedStmt.executeUpdate();
    }
    
    public void updateUserPassword(String username, 
            String newpassword) throws SQLException {
        String query = "UPDATE ROOT.USERS SET " + 
            "PASSWORD = ? " + 
            "WHERE USERNAME = ?";
        //Store values into each column
        preparedStmt = connection.prepareStatement(query);
        preparedStmt.setString(1, newpassword);
        preparedStmt.setString(2, username);
               
        //Execute the query and get a reponse from database
        preparedStmt.executeUpdate();
        
    }
    
    public void updateUserStatus(String username, String status)
            throws SQLException {
        String query = "UPDATE ROOT.USERS SET " + 
            "STATUS = ? " + 
            "WHERE USERNAME = ?";
        //Store values into each column
        String reverseStatus = status.equals("0")? "1": "0";
        System.out.println(reverseStatus);
        preparedStmt = connection.prepareStatement(query);
        preparedStmt.setString(1, reverseStatus);
        preparedStmt.setString(2, username);
        preparedStmt.executeUpdate();
        preparedStmt.close();
        
        
    }
    
    
    
    public void deleteUser(String username) throws SQLException {
        String query = "DELETE FROM ROOT.USERS WHERE USERNAME = ?";
        if(isUsernameExist(username)) {
            preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString(1, username);
            preparedStmt.executeUpdate();
            preparedStmt.close();
        }
        
        
        
    }
    
    public boolean checkUser(String username, 
            String password)throws SQLException {
        String fetch = "SELECT * FROM ROOT.USERS " +
            "WHERE USERNAME = ? AND PASSWORD = ?";
        this.preparedStmt = connection.prepareStatement(fetch);
        this.preparedStmt.setString(1, username);
        this.preparedStmt.setString(2, password);
        resultSet = preparedStmt.executeQuery();
        
        while(resultSet.next()) {
            String userName = resultSet.getString(1);
            String userPassword = resultSet.getString(2);
            if(userName.equals(username) && userPassword.equals(password))
                return true;
        }
        resultSet.close();
        return false;
    }
    
    public boolean isUsernameExist(String username) throws SQLException {
        String fetch = "SELECT * FROM ROOT.USERS WHERE USERNAME = ?";
        this.preparedStmt = connection.prepareStatement(fetch);
        this.preparedStmt.setString(1, username);
        resultSet = preparedStmt.executeQuery();
        return resultSet.next();
    }
}
