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
public class DBCartManager {
    private PreparedStatement preparedStmt;
    private Connection connection;
    private ResultSet resultSet;
    
    public DBCartManager(Connection connection) throws SQLException {       
          this.connection = connection;
          this.preparedStmt = null;
          this.resultSet = null;
    }
    
    public void addProductInCart(String username, int productNo, String name,
            String type, int price, int quantity) throws SQLException {
        String queryGetLine = "SELECT NUMBER FROM ROOT.CART WHERE NUMBER = ?";
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
                
        String query = "INSERT INTO ROOT.CART " + 
            "(NUMBER,USERNAME,PRODUCTNO,NAME,TYPE,PRICE,QUANTITY) " +
            " VALUES(?,?,?,?,?,?,?)";
        this.preparedStmt = connection.prepareStatement(query);
        this.preparedStmt.setInt(1, getInt);
        this.preparedStmt.setString(2, username);
        this.preparedStmt.setInt(3, productNo);
        this.preparedStmt.setString(4, name.toLowerCase());
        this.preparedStmt.setString(5, type.toLowerCase());
        this.preparedStmt.setInt(6, price);
        this.preparedStmt.setInt(7, quantity);
                
        //Execute the query, then return a value for storing successfully
        int row = preparedStmt.executeUpdate();
        preparedStmt.close();
        
    }
    
    public ProductList getCartProductsByUsername(String username) throws SQLException {
        String fetch = "SELECT PRODUCTNO,NAME,TYPE,PRICE,QUANTITY FROM ROOT.CART " +
            "WHERE USERNAME = ?";
        this.preparedStmt = connection.prepareStatement(fetch);
        this.preparedStmt.setString(1, username);
        resultSet = preparedStmt.executeQuery();
        ProductList productList = new ProductList();
        
        while(resultSet.next()) {
            int number = resultSet.getInt("PRODUCTNO");
            String name = resultSet.getString("NAME");
            String type = resultSet.getString("TYPE");
            int price = resultSet.getInt("PRICE");
            int stock = resultSet.getInt("QUANTITY");
            productList.addProduct(new Product(number, name, type, price, stock));
        }
        return productList;   
    }
    
    public ProductList getProductByKeyword(String username, String keyword) throws SQLException {
        
        if(keyword.equals("")) {
            return getCartProductByUsername(username);
        }
        ProductList productList = new ProductList();
        String fetch = "SELECT PRODUCTNO,NAME,TYPE,PRICE,QUANTITY FROM ROOT.CART " + 
                "WHERE USERNAME=? AND (NAME=? OR TYPE=?)";
        this.preparedStmt = connection.prepareStatement(fetch);
        this.preparedStmt.setString(1, username);
        this.preparedStmt.setString(2, keyword.toLowerCase());
        this.preparedStmt.setString(3, keyword.toLowerCase());
        resultSet = preparedStmt.executeQuery();
        
        
        while(resultSet.next()) {
            int number = resultSet.getInt("PRODUCTNO");
            String name = resultSet.getString("NAME");
            String type = resultSet.getString("TYPE");
            int price = resultSet.getInt("PRICE");
            int stock = resultSet.getInt("QUANTITY");
            productList.addProduct(new Product(number, name, type, price, stock));
        }
        resultSet.close();
        return productList;
    }
    
    public void updateProduct(String username, int productNo, int quantity) throws SQLException {
        String query = "UPDATE ROOT.CART SET " + 
            "QUANTITY = ?" + 
            "WHERE USERNAME = ? AND PRODUCTNO = ?";
        //Store values into each column
        try {
            preparedStmt = connection.prepareStatement(query);
            preparedStmt.setInt(1, quantity);
            preparedStmt.setString(2, username);
            preparedStmt.setInt(3, productNo);
            //Execute the query and get a reponse from database
            preparedStmt.executeUpdate();
        }catch(SQLException ex) {
            resultSet.close();         
        } 
    }
    
    public boolean findCartProduct(String username, int productNo) throws SQLException {
        String fetch = "SELECT * FROM ROOT.CART " +
            "WHERE USERNAME = ? AND PRODUCTNO = ?";
        this.preparedStmt = connection.prepareStatement(fetch);
        this.preparedStmt.setString(1, username);
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
    
    public boolean findCartProductByUsername(String username) throws SQLException {
        String fetch = "SELECT * FROM ROOT.CART " +
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
    
    
    
    
    public void deleteProduct(String username, int productNo) throws SQLException {
        String query = "DELETE FROM ROOT.CART WHERE USERNAME = ? AND PRODUCTNO = ?";
        if(findCartProduct(username, productNo)) {
            preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString(1, username);
            preparedStmt.setInt(2, productNo);
            int executeUpdate = preparedStmt.executeUpdate();
            preparedStmt.close();
        }
    }
    
    public void deleteProduct(String username) throws SQLException {
        String query = "DELETE FROM ROOT.CART WHERE USERNAME = ?";
        if(findCartProductByUsername(username)) {
            preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString(1, username);
            int executeUpdate = preparedStmt.executeUpdate();
            preparedStmt.close();
        }
    }
    
    
    
    public ProductList getCartProductByUsername(String username) throws SQLException {
        ProductList productList = new ProductList();
        if(findCartProductByUsername(username)) {
            String fetch = "SELECT PRODUCTNO,NAME,TYPE,PRICE,QUANTITY FROM ROOT.CART " + 
                "WHERE USERNAME = ?";
        
            this.preparedStmt = connection.prepareStatement(fetch);
            this.preparedStmt.setString(1, username);
            resultSet = preparedStmt.executeQuery();
        
            
            while(resultSet.next()) {
                int productNO = resultSet.getInt("PRODUCTNO");
                String name = resultSet.getString("NAME");
                String type = resultSet.getString("TYPE");
                int price = resultSet.getInt("PRICE");
                int quantity = resultSet.getInt("QUANTITY");
                productList.addProduct(new Product(productNO, name, type, price, quantity));
            }
            resultSet.close();
        }
        return productList;
    }
    
    
    public ProductList getAllRecords() throws SQLException {
        String fetch = "SELECT PRODUCTNO,NAME,TYPE,PRICE,QUANTITY FROM ROOT.CART";
        this.preparedStmt = connection.prepareStatement(fetch);
        resultSet = preparedStmt.executeQuery();
        
        ProductList productList = new ProductList();
        
        while(resultSet.next()) {
            int number = resultSet.getInt("PRODUCTNO");
            String name = resultSet.getString("NAME");
            String type = resultSet.getString("TYPE");
            int price = resultSet.getInt("PRICE");
            int stock = resultSet.getInt("QUANTITY");
            productList.addProduct(new Product(number, name, type, price, stock));
        }
        resultSet.close();
        return productList;
    }
}
