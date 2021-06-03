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
import uts.isd.model.Product;
import uts.isd.model.ProductList;

/**
 *
 * @author Administrator
 */
public class DBOrderlineManager {
    private PreparedStatement preparedStmt;
    private Connection connection;
    private ResultSet resultSet;
    
    public DBOrderlineManager(Connection connection) throws SQLException {       
          this.connection = connection;
          this.preparedStmt = null;
          this.resultSet = null;
    }
    
    public void addProductOrderline(int orderID, int productNo, String name,
            String type, int price, int quantity) throws SQLException {
        String queryGetLine = "SELECT NUMBER FROM ROOT.ORDERLINE WHERE NUMBER = ?";
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
                
        String query = "INSERT INTO ROOT.ORDERLINE " + 
            "(NUMBER,ORDERID,PRODUCTNO,NAME,TYPE,PRICE,QUANTITY,STATUS) " +
            " VALUES(?,?,?,?,?,?,?,?)";
        this.preparedStmt = connection.prepareStatement(query);
        this.preparedStmt.setInt(1, getInt);
        this.preparedStmt.setInt(2, orderID);
        this.preparedStmt.setInt(3, productNo);
        this.preparedStmt.setString(4, name.toLowerCase());
        this.preparedStmt.setString(5, type.toLowerCase());
        this.preparedStmt.setInt(6, price);
        this.preparedStmt.setInt(7, quantity);
        this.preparedStmt.setInt(8, 0);
                
        //Execute the query, then return a value for storing successfully
        int row = preparedStmt.executeUpdate();
        preparedStmt.close();
        
    }
    
    public ProductList getProductsByOrderID(int orderID, int status) throws SQLException {
        String fetch = "SELECT PRODUCTNO,NAME,TYPE,PRICE,QUANTITY FROM ROOT.ORDERLINE " +
            "WHERE ORDERID = ? AND STATUS = ?";
        this.preparedStmt = connection.prepareStatement(fetch);
        this.preparedStmt.setInt(1, orderID);
        this.preparedStmt.setInt(2, status);
        resultSet = preparedStmt.executeQuery();
        ProductList productList = new ProductList();
        
        while(resultSet.next()) {
            int number = resultSet.getInt("PRODUCTNO");
            String name = resultSet.getString("NAME");
            String type = resultSet.getString("TYPE");
            int price = resultSet.getInt("PRICE");
            int quantity = resultSet.getInt("QUANTITY");
            productList.addProduct(new Product(number, type, name, price, quantity));
        }
        return productList;   
    }
    
    public boolean findOrderlineProduct(int orderID, int productNo) throws SQLException {
        String fetch = "SELECT * FROM ROOT.ORDERLINE " +
            "WHERE ORDERID = ? AND PRODUCTNO = ?";
        this.preparedStmt = connection.prepareStatement(fetch);
        this.preparedStmt.setInt(1, orderID);
        this.preparedStmt.setInt(2, productNo);
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
    
    public void updateOrderlineProduct(int orderID, int productNo, int quantity) throws SQLException {
        String query = "UPDATE ROOT.ORDERLINE SET " + 
            "QUANTITY = ?" + 
            "WHERE ORDERID = ? AND PRODUCTNO = ?";
        //Store values into each column
        try {
            preparedStmt = connection.prepareStatement(query);
            preparedStmt.setInt(1, quantity);
            preparedStmt.setInt(2, orderID);
            preparedStmt.setInt(3, productNo);
            //Execute the query and get a reponse from database
            preparedStmt.executeUpdate();
        }catch(SQLException ex) {
            resultSet.close();         
        } 
    }
    
    public void updateProductStatus(int orderID, int productNo) throws SQLException {
        String query = "UPDATE ROOT.ORDERLINE SET " + 
            "STATUS = ?" + 
            "WHERE ORDERID = ? AND PRODUCTNO = ?";
        //Store values into each column
        try {
            preparedStmt = connection.prepareStatement(query);
            preparedStmt.setInt(1, -1);
            preparedStmt.setInt(2, orderID);
            preparedStmt.setInt(3, productNo);
            //Execute the query and get a reponse from database
            preparedStmt.executeUpdate();
        }catch(SQLException ex) {
            resultSet.close();         
        } 
    }
    
     public void updateAllProductStatus(int orderID, int status) throws SQLException {
        String query = "UPDATE ROOT.ORDERLINE SET " + 
            "STATUS = ?" + 
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
    
    
    
    
    
    public void deleteProduct(int orderID, int productNo) throws SQLException {
        String query = "DELETE FROM ROOT.ORDERLINE WHERE ORDERID = ? AND PRODUCTNO = ?";
        if(findOrderlineProduct(orderID, productNo)) {
            preparedStmt = connection.prepareStatement(query);
            preparedStmt.setInt(1, orderID);
            preparedStmt.setInt(2, productNo);
            int executeUpdate = preparedStmt.executeUpdate();
            preparedStmt.close();
        }
    }
    
    public ProductList getProductByKeyword(int orderID, int orderIDStatus, String keyword) throws SQLException {
        
        if(keyword.equals("")) {
            return getProductsByOrderID(orderID, orderIDStatus);
        }
        ProductList productList = new ProductList();
        String fetch = "SELECT PRODUCTNO,NAME,TYPE,PRICE,QUANTITY FROM ROOT.ORDERLINE " + 
                "WHERE ORDERID=? AND STATUS=? AND (NAME=? OR TYPE=?)";
        this.preparedStmt = connection.prepareStatement(fetch);
        this.preparedStmt.setInt(1, orderID);
        this.preparedStmt.setInt(2, orderIDStatus);
        this.preparedStmt.setString(3, keyword.toLowerCase());
        this.preparedStmt.setString(4, keyword.toLowerCase());
        resultSet = preparedStmt.executeQuery();
        
        
        while(resultSet.next()) {
            int number = resultSet.getInt("PRODUCTNO");
            String name = resultSet.getString("NAME");
            String type = resultSet.getString("TYPE");
            int price = resultSet.getInt("PRICE");
            int stock = resultSet.getInt("QUANTITY");
            productList.addProduct(new Product(number, type, name, price, stock));
        }
        resultSet.close();
        return productList;
    }
    
    
    public ProductList getAllRecords() throws SQLException {
        String fetch = "SELECT PRODUCTNO,NAME,TYPE,PRICE,QUANTITY FROM ROOT.ORDERLINE";
        this.preparedStmt = connection.prepareStatement(fetch);
        resultSet = preparedStmt.executeQuery();
        
        ProductList productList = new ProductList();
        
        while(resultSet.next()) {
            int number = resultSet.getInt("PRODUCTNO");
            String name = resultSet.getString("NAME");
            String type = resultSet.getString("TYPE");
            int price = resultSet.getInt("PRICE");
            int quantity = resultSet.getInt("QUANTITY");
            productList.addProduct(new Product(number, name, type, price, quantity));
        }
        resultSet.close();
        return productList;
    }
}
