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
import java.sql.Time;
import java.util.Random;
import uts.isd.model.Product;
import uts.isd.model.ProductList;

/**
 *
 * @author Administrator
 */
public class DBProductManager {
    private PreparedStatement preparedStmt;
    private Connection connection;
    private ResultSet resultSet;
    
    public DBProductManager(Connection connection) throws SQLException {       
          this.connection = connection;
          this.preparedStmt = null;
          this.resultSet = null;
    }
    
    public void addProduct(String name, String type, int price, int stock) throws SQLException {
        
        String queryGetLine = "SELECT NUMBER FROM ROOT.PRODUCTS WHERE NUMBER = ?";
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
                
        String query = "INSERT INTO ROOT.PRODUCTS " + 
            "(NUMBER,NAME,TYPE,PRICE,STOCK) " +
            " VALUES(?,?,?,?,?)";
        this.preparedStmt = connection.prepareStatement(query);
        this.preparedStmt.setInt(1, getInt);
        this.preparedStmt.setString(2, name.toLowerCase());
        this.preparedStmt.setString(3, type.toLowerCase());
        this.preparedStmt.setInt(4, price);
        this.preparedStmt.setInt(5, stock);
                
        //Execute the query, then return a value for storing successfully
        int row = preparedStmt.executeUpdate();
        preparedStmt.close();
        
    }
    
    public boolean findProduct(int number) throws SQLException {
        String fetch = "SELECT * FROM ROOT.PRODUCTS " +
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
    
    public boolean findProductByName(String name) throws SQLException {
        String fetch = "SELECT * FROM ROOT.PRODUCTS " +
            "WHERE NAME = ?";
        this.preparedStmt = connection.prepareStatement(fetch);
        this.preparedStmt.setString(1, name.toLowerCase());
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
    
    
    public void updateProduct(int number, String name,
            String type, int price, int stock) throws SQLException {
        String query = "UPDATE ROOT.PRODUCTS SET " + 
            "NAME = ?, TYPE = ?, PRICE = ?, STOCK = ?" + 
            "WHERE NUMBER = ?";
        //Store values into each column
        try {
            preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString(1, name.toLowerCase());
            preparedStmt.setString(2, type.toLowerCase());
            preparedStmt.setInt(3, price);
            preparedStmt.setInt(4, stock);
            preparedStmt.setInt(5, number);
            //Execute the query and get a reponse from database
            preparedStmt.executeUpdate();
        }catch(SQLException ex) {
            resultSet.close();         
        } 
    }
    
    
    public void updateProductByNumber(int number, int stock) throws SQLException {
        String query = "UPDATE ROOT.PRODUCTS SET " + 
            "STOCK = ?" + 
            "WHERE NUMBER = ?";
        //Store values into each column
        try {
            preparedStmt = connection.prepareStatement(query);
            preparedStmt.setInt(1, stock);
            preparedStmt.setInt(2, number);
            //Execute the query and get a reponse from database
            preparedStmt.executeUpdate();
        }catch(SQLException ex) {
            resultSet.close();         
        } 
    }
    
    
    
    public void deleteProduct(int number) throws SQLException {
        String query = "DELETE FROM ROOT.PRODUCTS WHERE NUMBER = ?";
        if(findProduct(number)) {
            preparedStmt = connection.prepareStatement(query);
            preparedStmt.setInt(1, number);
            preparedStmt.executeUpdate();
            preparedStmt.close();
        }
    }
    
    public Product getProductByNumber(int number) throws SQLException {
        String fetch = "SELECT * FROM ROOT.PRODUCTS " +
            "WHERE NUMBER = ?";
        this.preparedStmt = connection.prepareStatement(fetch);
        this.preparedStmt.setInt(1, number);
        resultSet = preparedStmt.executeQuery();
        Product newProduct = null;
        
        while(resultSet.next()) {
            newProduct = new Product(
                resultSet.getInt("NUMBER"),
                resultSet.getString("NAME"),
                resultSet.getString("TYPE"),
                resultSet.getInt("PRICE"),
                resultSet.getInt("STOCK")
            );
        }
        resultSet.close();
        return newProduct;   
    }
    
        
    
    public ProductList getProductByKeyword(String keyword) throws SQLException {
        
        if(keyword.equals("")) {
            return getAllProducts();
        }
        ProductList productList = new ProductList();
        String fetch = "SELECT NUMBER,NAME,TYPE,PRICE,STOCK FROM ROOT.PRODUCTS " + 
                "WHERE NAME=? OR TYPE=?";
        this.preparedStmt = connection.prepareStatement(fetch);
        this.preparedStmt.setString(1, keyword.toLowerCase());
        this.preparedStmt.setString(2, keyword.toLowerCase());
        resultSet = preparedStmt.executeQuery();
        
        
        while(resultSet.next()) {
            int number = resultSet.getInt("NUMBER");
            String name = resultSet.getString("NAME");
            String type = resultSet.getString("TYPE");
            int price = resultSet.getInt("PRICE");
            int stock = resultSet.getInt("STOCK");
            productList.addProduct(new Product(number, name, type, price, stock));
        }
        resultSet.close();
        return productList;
    }
    
    public ProductList getLogsByName(String pName) throws SQLException {
        String fetch = "SELECT NUMBER,NAME,TYPE,PRICE,STOCK FROM ROOT.PRODUCTS " + 
                "WHERE NAME=?";
        this.preparedStmt = connection.prepareStatement(fetch);
        this.preparedStmt.setString(1, pName.toLowerCase());
        resultSet = preparedStmt.executeQuery();
        
        ProductList productList = new ProductList();
        while(resultSet.next()) {
            int number = resultSet.getInt("NUMBER");
            String name = resultSet.getString("NAME");
            String type = resultSet.getString("TYPE");
            int price = resultSet.getInt("PRICE");
            int stock = resultSet.getInt("STOCK");
            productList.addProduct(new Product(number, name, type, price, stock));
        }
        resultSet.close();
        return productList;
    }
    
    
    public ProductList getAllProducts() throws SQLException {
        String fetch = "SELECT NUMBER,NAME,TYPE,PRICE,STOCK FROM ROOT.PRODUCTS ";
        this.preparedStmt = connection.prepareStatement(fetch);
        resultSet = preparedStmt.executeQuery();
        
        ProductList productList = new ProductList();
        
        while(resultSet.next()) {
            int number = resultSet.getInt("NUMBER");
            String name = resultSet.getString("NAME");
            String type = resultSet.getString("TYPE");
            int price = resultSet.getInt("PRICE");
            int stock = resultSet.getInt("STOCK");
            productList.addProduct(new Product(number, name, type, price, stock));
        }
        resultSet.close();
        return productList;
    }
}

