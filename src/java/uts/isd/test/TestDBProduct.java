/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uts.isd.test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import uts.isd.model.ProductList;
import uts.isd.model.dao.DBConnector;
import uts.isd.model.dao.DBProductManager;

/**
 *
 * @author Administrator
 */
public class TestDBProduct {
    private static Scanner in = new Scanner(System.in);
    private DBConnector connector;
    private Connection connection;
    private DBProductManager productManager;
    
    public TestDBProduct() throws SQLException {
        try {
            connector = new DBConnector();
            connection = connector.getConnection();
            productManager = new DBProductManager(connection);
        
        }catch(ClassNotFoundException | SQLException ex) {
            Logger.getLogger(TestDBProduct.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public char readChoice() {
        System.out.print("Operation CRUDS or * to exit: ");
        return in.nextLine().charAt(0);
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
    
    private void testAdd() {
        System.out.print("Product Name: ");
        String name = in.nextLine();
                      
        System.out.print("Product Type: ");
        String type = in.nextLine();
        
        System.out.print("Product price: ");
        int price = Integer.parseInt(in.nextLine());
        
        System.out.print("Product stock: ");
        int stock = Integer.parseInt(in.nextLine());
        
        try {
            productManager.addProduct(name, type, price, stock);
        }catch(SQLException ex) {
            Logger.getLogger(TestDBProduct.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("New product is added into database.");
        
    }
    
    private void testRead() throws SQLException {
        System.out.print("Product Name: ");
        String name = in.nextLine();
        
        ProductList productList = productManager.getProductByKeyword(name);
        productList.displayProducts();
    }
    
    private void testUpdate() {
        System.out.print("Number: ");
        int number = Integer.parseInt(in.nextLine());
        try {
            if(productManager.findProduct(number)) {
                //test update user profile
                System.out.print("Product Name: ");
                String name = in.nextLine();
                System.out.print("Type: ");
                String type = in.nextLine();
                System.out.print("Price: ");
                int price = Integer.parseInt(in.nextLine());
                System.out.print("Stock: ");
                int stock = Integer.parseInt(in.nextLine());
                
                productManager.updateProduct(number, name, type, price, stock);
                System.out.println("Product NO " + number + "  is updated.");
            }else {
                System.out.println("Product NO " + number + " does not exist in database.");
            }
        }catch(SQLException ex) {
            Logger.getLogger(TestDBProduct.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void testDelete() {
        System.out.print("Number: ");
        int number = Integer.parseInt(in.nextLine());
        try {
            if(productManager.findProduct(number)) {
                //test update user profile
                productManager.deleteProduct(number);
                System.out.println("Product NO " + number + " is removed from database.");
            }else {
                System.out.println("Product NO " + number + " does not exist in database.");
            }
        }catch(SQLException ex) {
            Logger.getLogger(TestDBProduct.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    private void showAll() {
        try {
            ProductList productList = productManager.getAllProducts();
            productList.displayProducts();
        }catch(SQLException ex) {
            Logger.getLogger(TestDBProduct.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public static void main(String [] args) throws SQLException {
        new TestDBProduct().runQueries();
    }
}
