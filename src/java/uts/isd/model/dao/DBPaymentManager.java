/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uts.isd.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import uts.isd.model.Payment;
import uts.isd.model.PaymentList;

/**
 *
 * @author Administrator
 */
public class DBPaymentManager {
    private PreparedStatement preparedStmt;
    private Connection connection;
    private ResultSet resultSet;
    
    public DBPaymentManager(Connection connection) throws SQLException {       
          this.connection = connection;
          this.preparedStmt = null;
          this.resultSet = null;
    }
    
    public void addPayment(String username, String type,
            String paymentNumber) throws SQLException {
        String queryGetLine = "SELECT NUMBER FROM ROOT.PAYMENT WHERE NUMBER = ?";
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
                
        String query = "INSERT INTO ROOT.PAYMENT " + 
            "(NUMBER,USERNAME,PAYMENTTYPE,PAYMENTNUMBER) " +
            "VALUES(?,?,?,?)";
        this.preparedStmt = connection.prepareStatement(query);
        this.preparedStmt.setInt(1, getInt);
        this.preparedStmt.setString(2, username);
        this.preparedStmt.setString(3, type.toLowerCase());
        this.preparedStmt.setString(4, paymentNumber);
                
        //Execute the query, then return a value for storing successfully
        int row = preparedStmt.executeUpdate();
        preparedStmt.close();
        
    }
    public boolean findPaymentByUsername(String username) throws SQLException {
        String fetch = "SELECT * FROM ROOT.PAYMENT " +
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
    
    public boolean findPaymentByTypeAndNumber(String username, String type, String number) throws SQLException {
        String fetch = "SELECT * FROM ROOT.PAYMENT " +
            "WHERE USERNAME = ? AND PAYMENTTYPE = ? AND PAYMENTNUMBER = ?";
        this.preparedStmt = connection.prepareStatement(fetch);
        this.preparedStmt.setString(1, username);
        this.preparedStmt.setString(2, type);
        this.preparedStmt.setString(3, number);
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
    
    
    
    public boolean findPayment(int paymentNo) throws SQLException {
        String fetch = "SELECT * FROM ROOT.PAYMENT " +
            "WHERE NUMBER = ?";
        this.preparedStmt = connection.prepareStatement(fetch);
        this.preparedStmt.setInt(1, paymentNo);
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
    
    public Payment getPaymentByPaymentNo(int paymentNo) throws SQLException{
        String fetch = "SELECT NUMBER,PAYMENTTYPE,PAYMENTNUMBER FROM ROOT.PAYMENT " +
            "WHERE NUMBER = ?";
        this.preparedStmt = connection.prepareStatement(fetch);
        this.preparedStmt.setInt(1, paymentNo);
        resultSet = preparedStmt.executeQuery();
        if(resultSet.next()) {
            String type = resultSet.getString("PAYMENTTYPE");
            String paymentNumber = resultSet.getString("PAYMENTNUMBER");
            return new Payment(paymentNo, type, paymentNumber);
        }
        return new Payment();
    }
     
    
    public PaymentList getPaymentByUsername(String username) throws SQLException {
        String fetch = "SELECT NUMBER,PAYMENTTYPE,PAYMENTNUMBER FROM ROOT.PAYMENT " +
            "WHERE USERNAME = ?";
        this.preparedStmt = connection.prepareStatement(fetch);
        this.preparedStmt.setString(1, username);
        resultSet = preparedStmt.executeQuery();
        PaymentList paymentList = new PaymentList();
        
        while(resultSet.next()) {
            int paymentNo = resultSet.getInt("NUMBER");
            String type = resultSet.getString("PAYMENTTYPE");
            String paymentNumber = resultSet.getString("PAYMENTNUMBER");
            paymentList.addPayment(new Payment(paymentNo, type, paymentNumber));
        }
        return paymentList;   
    }
    
    public void updatePayment(int paymentNo, String type, String number) throws SQLException {
        String query = "UPDATE ROOT.PAYMENT SET " + 
            "PAYMENTTYPE = ?, PAYMENTNUMBER = ? " + 
            "WHERE NUMBER = ?";
        //Store values into each column
        try {
            preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString(1, type.toLowerCase());
            preparedStmt.setString(2, number);
            preparedStmt.setInt(3, paymentNo);
            //Execute the query and get a reponse from database
            preparedStmt.executeUpdate();
        }catch(SQLException ex) {
            resultSet.close();         
        } 
    }
    
    public void deletePayment(int paymentNo) throws SQLException {
        String query = "DELETE FROM ROOT.PAYMENT WHERE NUMBER = ?";
        
        if(findPayment(paymentNo)) {
            preparedStmt = connection.prepareStatement(query);
            preparedStmt.setInt(1, paymentNo);
            preparedStmt.executeUpdate();
            preparedStmt.close();
        }
    }
    
    public void deletePaymentByUsername(String username) throws SQLException {
        String query = "DELETE FROM ROOT.PAYMENT WHERE USERNAME = ?";
        preparedStmt = connection.prepareStatement(query);
        preparedStmt.setString(1, username);
        preparedStmt.executeUpdate();
        preparedStmt.close();
    }    
    
    
    
    
    
    public PaymentList getAllRecords() throws SQLException {
        String fetch = "SELECT NUMBER,PAYMENTTYPE,PAYMENTNUMBER FROM ROOT.PAYMENT";
        this.preparedStmt = connection.prepareStatement(fetch);
        resultSet = preparedStmt.executeQuery();
        
        PaymentList paymentList = new PaymentList();
        
        while(resultSet.next()) {
            int number = resultSet.getInt("NUMBER");
            String type = resultSet.getString("PAYMENTTYPE");
            String paymentNumber = resultSet.getString("PAYMENTNUMBER");
            paymentList.addPayment(new Payment(number, type, paymentNumber));
        }
        resultSet.close();
        return paymentList;
    }

    public PaymentList getPaymentByKeyword(String username, String keyword) throws SQLException {
        String fetch = "SELECT NUMBER,PAYMENTTYPE,PAYMENTNUMBER FROM ROOT.PAYMENT " +
            "WHERE USERNAME = ? AND (PAYMENTTYPE = ? OR PAYMENTNUMBER = ?)";
        this.preparedStmt = connection.prepareStatement(fetch);
        this.preparedStmt.setString(1, username);
        this.preparedStmt.setString(2, keyword.toLowerCase());
        this.preparedStmt.setString(3, keyword);
        resultSet = preparedStmt.executeQuery();
        PaymentList paymentList = new PaymentList();
        
        while(resultSet.next()) {
            int paymentNo = resultSet.getInt("NUMBER");
            String type = resultSet.getString("PAYMENTTYPE");
            String paymentNumber = resultSet.getString("PAYMENTNUMBER");
            paymentList.addPayment(new Payment(paymentNo, type, paymentNumber));
        }
        return paymentList;   
    }
       
    
}
