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
import uts.isd.model.dao.DBOrderlineManager;

/**
 *
 * @author Administrator
 */
public class TestDBOrderline {
    private static Scanner in = new Scanner(System.in);
    private DBConnector connector;
    private Connection connection;
    private DBOrderlineManager orderlineManager;
    
    public TestDBOrderline() throws SQLException {
        try {
            connector = new DBConnector();
            connection = connector.getConnection();
            orderlineManager = new DBOrderlineManager(connection);
        
        }catch(ClassNotFoundException | SQLException ex) {
            Logger.getLogger(TestDBOrderline.class.getName()).log(Level.SEVERE, null, ex);
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
    
    private void testAdd() {
        try {
            System.out.print("OrderID: ");
            int orderID = Integer.parseInt(in.nextLine());
                      
            System.out.print("Product Number: ");
            int productNo = Integer.parseInt(in.nextLine());
        
            System.out.print("Product Name: ");
            String name = in.nextLine();
        
            System.out.print("Product Type: ");
            String type = in.nextLine();
        
            System.out.print("Product Price: ");
            int price = Integer.parseInt(in.nextLine());
                
            System.out.print("Product Quantity: ");
            int quantity = Integer.parseInt(in.nextLine());
            orderlineManager.addProductOrderline(orderID, productNo, name, type, price, quantity);
            System.out.println("A product is added into orderline database.");
        }catch(SQLException ex) {
            Logger.getLogger(TestDBOrderline.class.getName()).log(Level.SEVERE, null, ex);
        }catch (NumberFormatException e){
            System.out.println("Input format is incorrect");
        }
        
        
    }
    
    private void testRead() throws SQLException {
        System.out.print("OrderID: ");
        int orderID = Integer.parseInt(in.nextLine());
        System.out.print("Order Status: ");
        int status = Integer.parseInt(in.nextLine());
        
        ProductList productList = orderlineManager.getProductsByOrderID(orderID, status);
        productList.displayProducts();
    }
    
    private void testUpdate() {
        
        try {
            System.out.print("OrderID: ");
            int orderID = Integer.parseInt(in.nextLine());
            System.out.print("Product Number: ");
            int productNo = Integer.parseInt(in.nextLine());
            if(orderlineManager.findOrderlineProduct(orderID, productNo)) {
                System.out.print("Quantity: ");
                int quantity = Integer.parseInt(in.nextLine());
                
                orderlineManager.updateOrderlineProduct(orderID, productNo, quantity);
                System.out.println("Order's product quantity is updated.");
            }else {
                System.out.println("Order's product does not exist in database.");
            }
        }catch(SQLException ex) {
            Logger.getLogger(TestDBOrderline.class.getName()).log(Level.SEVERE, null, ex);
        }catch (NumberFormatException e){
            System.out.println("Input format is incorrect");
        }
    }
    
    private void testDelete() {
        try {
            System.out.print("OrderID: ");
            int orderID = Integer.parseInt(in.nextLine());
            System.out.print("Product Number: ");
            int productNo = Integer.parseInt(in.nextLine());
            if(orderlineManager.findOrderlineProduct(orderID, productNo)) {
                //test update user profile
                orderlineManager.deleteProduct(orderID, productNo);
                System.out.println("Order's product is removed from database.");
            }else {
                System.out.println("Order's product does not exist in database.");
            }
        }catch(SQLException ex) {
            Logger.getLogger(TestDBOrderline.class.getName()).log(Level.SEVERE, null, ex);
        }catch (NumberFormatException e){
            System.out.println("Input format is incorrect");
        }
    }
    
    private void showAll() {
        try {
            ProductList productList = orderlineManager.getAllRecords();
            productList.displayProducts();
        }catch(SQLException ex) {
            Logger.getLogger(TestDBOrderline.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private void testFilter() {
        try {
            System.out.print("OrderID: ");
            int orderID = Integer.parseInt(in.nextLine());
            System.out.print("Keyword: ");
            String keyword = in.nextLine();
            System.out.print("Status: ");
            int status = Integer.parseInt(in.nextLine());
            ProductList productList = orderlineManager.getProductByKeyword(orderID, status, keyword);
            productList.displayProducts();
        }catch (SQLException ex){
            Logger.getLogger(TestDBOrderline.class.getName()).log(Level.SEVERE, null, ex);
        }catch (NumberFormatException e){
            System.out.println("Input format is incorrect");
        }
    }
    
    
    public static void main(String [] args) throws SQLException {
        new TestDBOrderline().runQueries();
    }

    
}
