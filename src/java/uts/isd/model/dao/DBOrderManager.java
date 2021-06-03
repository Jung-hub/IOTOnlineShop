/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uts.isd.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import uts.isd.model.Order;
import uts.isd.model.OrderList;

/**
 *
 * @author Administrator
 */
public class DBOrderManager {
    private PreparedStatement preparedStmt;
    private Connection connection;
    private ResultSet resultSet;
    
    public DBOrderManager(Connection connection) throws SQLException {       
          this.connection = connection;
          this.preparedStmt = null;
          this.resultSet = null;
    }
    public int addOrder(String username, int amount) throws SQLException {
        String queryGetLine = "SELECT ORDERID FROM ROOT.ORDERS WHERE ORDERID = ?";
        Random rand = new Random();
        int getInt = 0;
        int upperbound = 1000000;
        do {
            getInt = rand.nextInt(upperbound);
            this.preparedStmt = connection.prepareStatement(queryGetLine);
            this.preparedStmt.setInt(1, getInt);
            resultSet = preparedStmt.executeQuery();
        }while(resultSet.next());
        
        preparedStmt.close();
        resultSet.close();
        Date date = new Date(System.currentTimeMillis());       
        String query = "INSERT INTO ROOT.ORDERS " + 
            "(ORDERID,USERNAME,DATE,STATUS,AMOUNT) " +
            " VALUES(?,?,?,?,?)";
        this.preparedStmt = connection.prepareStatement(query);
        this.preparedStmt.setInt(1, getInt);
        this.preparedStmt.setString(2, username);
        this.preparedStmt.setDate(3, date);
        this.preparedStmt.setInt(4, 0);
        this.preparedStmt.setInt(5, amount);
                       
        //Execute the query, then return a value for storing successfully
        int row = preparedStmt.executeUpdate();
        preparedStmt.close();
        return getInt;
        
    }
    
    public OrderList getOrdersByUsername(String username) throws SQLException {
        String fetch = "SELECT * FROM ROOT.ORDERS " +
            "WHERE USERNAME = ? AND STATUS >= ?";
        this.preparedStmt = connection.prepareStatement(fetch);
        this.preparedStmt.setString(1, username);
        this.preparedStmt.setInt(2, -1);
        resultSet = preparedStmt.executeQuery();
        OrderList orderList = new OrderList();
        
        while(resultSet.next()) {
            int orderID = resultSet.getInt("ORDERID");
            Date date = resultSet.getDate("DATE");
            int status = resultSet.getInt("STATUS");
            String type = resultSet.getString("PAYMENTTYPE");
            String paymentNumber = resultSet.getString("PAYMENTNUMBER");
            Date paymentDate = resultSet.getDate("PAYMENTDATE");
            int amount = resultSet.getInt("AMOUNT");
            orderList.addOrder(new Order(orderID, username, date, status,
                    type, paymentNumber, paymentDate, amount));
        }
        return orderList;   
    }
    
    public OrderList getOrdersByUsernameAndStatus(String username, int givenStatus) throws SQLException {
        String fetch = "SELECT * FROM ROOT.ORDERS " +
            "WHERE USERNAME = ? AND STATUS = ?";
        this.preparedStmt = connection.prepareStatement(fetch);
        this.preparedStmt.setString(1, username);
        this.preparedStmt.setInt(2, givenStatus);
        resultSet = preparedStmt.executeQuery();
        OrderList orderList = new OrderList();
        
        while(resultSet.next()) {
            int orderID = resultSet.getInt("ORDERID");
            Date date = resultSet.getDate("DATE");
            int status = resultSet.getInt("STATUS");
            String type = resultSet.getString("PAYMENTTYPE");
            String paymentNumber = resultSet.getString("PAYMENTNUMBER");
            Date paymentDate = resultSet.getDate("PAYMENTDATE");
            int amount = resultSet.getInt("AMOUNT");
            orderList.addOrder(new Order(orderID, username, date, status,
                    type, paymentNumber, paymentDate, amount));
        }
        return orderList;   
    }
      
    public boolean findOrderByOrderID(int orderID) throws SQLException {
        String fetch = "SELECT ORDERID FROM ROOT.ORDERS " +
            "WHERE ORDERID = ?";
        this.preparedStmt = connection.prepareStatement(fetch);
        this.preparedStmt.setInt(1, orderID);
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
    
    public void updateOrder(int orderID, int status, String paymentType, String paymentNumber) throws SQLException {
        String query = "UPDATE ROOT.ORDERS SET " + 
            "STATUS = ?, PAYMENTTYPE = ?, PAYMENTNUMBER = ?, PAYMENTDATE = ? " + 
            "WHERE ORDERID = ?";
        //Store values into each column
        try {
            preparedStmt = connection.prepareStatement(query);
            preparedStmt.setInt(1, status);
            preparedStmt.setString(2, paymentType);
            preparedStmt.setString(3, paymentNumber);
            preparedStmt.setDate(4, new Date(System.currentTimeMillis()));
            preparedStmt.setInt(5, orderID);
            //Execute the query and get a reponse from database
            preparedStmt.executeUpdate();
        }catch(SQLException ex) {
            resultSet.close();         
        } 
    }
    
    public void updateOrderStatus(int orderID, int status) throws SQLException {
        String query = "UPDATE ROOT.ORDERS SET " + 
            "STATUS = ? " + 
            "WHERE ORDERID = ?";
        //Store values into each column
        try {
            preparedStmt = connection.prepareStatement(query);
            preparedStmt.setInt(1, status);
            preparedStmt.setInt(2, orderID);
            //Execute the query and get a reponse from database
            preparedStmt.executeUpdate();
        }catch(SQLException ex) {
            resultSet.close();         
        } 
    }
    
    public void updateOrderAmount(int orderID, int amount) throws SQLException {
        String query = "UPDATE ROOT.ORDERS SET " + 
            "AMOUNT = ? " + 
            "WHERE ORDERID = ?";
        //Store values into each column
        try {
            preparedStmt = connection.prepareStatement(query);
            preparedStmt.setInt(1, amount);
            preparedStmt.setInt(2, orderID);
            //Execute the query and get a reponse from database
            preparedStmt.executeUpdate();
        }catch(SQLException ex) {
            resultSet.close();         
        } 
    }
    
       
    
    public void updateOrderPayment(String username, String pType, String pNumber, String nType, String nNumber) throws SQLException {
        String query = "UPDATE ROOT.ORDERS SET " + 
            "PAYMENTTYPE = ? AND PAYMENTNUMBER = ? " + 
            "WHERE USERNAME = ? AND PAYMENTTYPE = ? AND PAYMENTNUMBER = ?";
        //Store values into each column
        try {
            preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString(1, nType);
            preparedStmt.setString(2, nNumber);
            preparedStmt.setString(3, username);
            preparedStmt.setString(4, pType);
            preparedStmt.setString(5, pNumber);
            //Execute the query and get a reponse from database
            preparedStmt.executeUpdate();
        }catch(SQLException ex) {
            resultSet.close();         
        } 
    }
    
    public void deleteOrder(int orderID) throws SQLException {
        String query = "DELETE FROM ROOT.ORDERS WHERE ORDERID = ?";
        if(findOrder(orderID)) {
            preparedStmt = connection.prepareStatement(query);
            preparedStmt.setInt(1, orderID);
            int executeUpdate = preparedStmt.executeUpdate();
            preparedStmt.close();
        }
    }

    private boolean findOrder(int orderID) throws SQLException {
        String fetch = "SELECT * FROM ROOT.ORDERS " +
            "WHERE ORDERID = ?";
        this.preparedStmt = connection.prepareStatement(fetch);
        this.preparedStmt.setInt(1, orderID);
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
    
    public OrderList getOrdersByPaymentDetail(String username, String paymentType, 
            String paymentNumber) throws SQLException {
        String fetch = "SELECT * FROM ROOT.ORDERS " +
            "WHERE PAYMENTTYPE = ? AND PAYMENTNUMBER = ? AND USERNAME = ?";
        this.preparedStmt = connection.prepareStatement(fetch);
        this.preparedStmt.setString(1, paymentType);
        this.preparedStmt.setString(2, paymentNumber);
        this.preparedStmt.setString(3, username);
        resultSet = preparedStmt.executeQuery();
        OrderList orderList = new OrderList();
        
        while(resultSet.next()) {
            int orderID = resultSet.getInt("ORDERID");
            Date date = resultSet.getDate("DATE");
            int status = resultSet.getInt("STATUS");
            Date paymentDate = resultSet.getDate("PAYMENTDATE");
            int amount = resultSet.getInt("AMOUNT");
            orderList.addOrder(new Order(orderID, username, date, status,
                    paymentType, paymentNumber, paymentDate, amount));
        }
        return orderList;
    }
    
    public OrderList getOrderByPaymentDate(String username, Date paymentDate) throws SQLException {
        String fetch = "SELECT * FROM ROOT.ORDERS " +
            "WHERE PAYMENTDATE = ? AND USERNAME = ?";
        this.preparedStmt = connection.prepareStatement(fetch);
        this.preparedStmt.setDate(1, paymentDate);
        this.preparedStmt.setString(2, username);
        resultSet = preparedStmt.executeQuery();
        OrderList orderList = new OrderList();
        while(resultSet.next()) {
            int orderID = resultSet.getInt("ORDERID");
            Date date = resultSet.getDate("DATE");
            int status = resultSet.getInt("STATUS");
            String paymentType = resultSet.getString("PAYMENTTYPE");
            String paymentNumber = resultSet.getString("PAYMENTNUMBER");
            //Date paymentDate = resultSet.getDate("PAYMENTDATE");
            int amount = resultSet.getInt("AMOUNT");
            orderList.addOrder(new Order(orderID, username, date, status,
                    paymentType, paymentNumber, paymentDate, amount));
        }
        return orderList;
    }
    
    
    public Order getOrderByOrderID(int order) throws SQLException {
        String fetch = "SELECT * FROM ROOT.ORDERS WHERE ORDERID = ?";
        this.preparedStmt = connection.prepareStatement(fetch);
        this.preparedStmt.setInt(1, order);
        resultSet = preparedStmt.executeQuery();
        
        while(resultSet.next()) {
            int orderID = resultSet.getInt("ORDERID");
            String username = resultSet.getString("USERNAME");
            Date date = resultSet.getDate("DATE");
            int status = resultSet.getInt("STATUS");
            String paymentType = resultSet.getString("PAYMENTTYPE");
            String paymentNumber = resultSet.getString("PAYMENTNUMBER");
            Date paymentDate = resultSet.getDate("PAYMENTDATE");
            int amount = resultSet.getInt("AMOUNT");
            return new Order(orderID, username, date, status,
                    paymentType, paymentNumber, paymentDate, amount);
        }
        return new Order();
    }
    
    
    
    public OrderList getAllOrders() throws SQLException {
        String fetch = "SELECT * FROM ROOT.ORDERS";
        this.preparedStmt = connection.prepareStatement(fetch);
        resultSet = preparedStmt.executeQuery();
        OrderList orderList = new OrderList();
        while(resultSet.next()) {
            int orderID = resultSet.getInt("ORDERID");
            String username = resultSet.getString("USERNAME");
            Date date = resultSet.getDate("DATE");
            int status = resultSet.getInt("STATUS");
            String paymentType = resultSet.getString("PAYMENTTYPE");
            String paymentNumber = resultSet.getString("PAYMENTNUMBER");
            Date paymentDate = resultSet.getDate("PAYMENTDATE");
            int amount = resultSet.getInt("AMOUNT");
            orderList.addOrder(new Order(orderID, username, date, status,
                    paymentType, paymentNumber, paymentDate, amount));
        }
        return orderList;
    }
}
