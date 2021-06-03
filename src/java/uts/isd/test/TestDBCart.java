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
import uts.isd.model.dao.DBCartManager;
import uts.isd.model.dao.DBConnector;

/**
 *
 * @author Administrator
 */
public class TestDBCart {
    private static Scanner in = new Scanner(System.in);
    private DBConnector connector;
    private Connection connection;
    private DBCartManager cartManager;
    
    public TestDBCart() throws SQLException {
        try {
            connector = new DBConnector();
            connection = connector.getConnection();
            cartManager = new DBCartManager(connection);
        
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
        System.out.print("Username: ");
        String username = in.nextLine();
                      
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
        
        try {
            cartManager.addProductInCart(username, productNo, name, type, price, quantity);
        }catch(SQLException ex) {
            Logger.getLogger(TestDBCart.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("A product is added into cart database.");
        
    }
    
    private void testRead() throws SQLException {
        System.out.print("Username: ");
        String username = in.nextLine();
        
        ProductList productList = cartManager.getCartProductByUsername(username);
        productList.displayProducts();
    }
    
    private void testUpdate() {
        System.out.print("Username: ");
        String username = in.nextLine();
        System.out.print("Product Number: ");
        int productNo = Integer.parseInt(in.nextLine());
        try {
            if(cartManager.findCartProduct(username, productNo)) {
                System.out.print("Quantity: ");
                int quantity = Integer.parseInt(in.nextLine());
                
                cartManager.updateProduct(username, productNo, quantity);
                System.out.println("Username's product quantity is updated.");
            }else {
                System.out.println("Username's product does not exist in database.");
            }
        }catch(SQLException ex) {
            Logger.getLogger(TestDBCart.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void testDelete() {
        System.out.print("Username: ");
        String username = in.nextLine();
        System.out.print("Product Number: ");
        int productNo = Integer.parseInt(in.nextLine());
        try {
            if(cartManager.findCartProduct(username, productNo)) {
                //test update user profile
                cartManager.deleteProduct(username, productNo);
                System.out.println("Username's product is removed from database.");
            }else {
                System.out.println("Username's product does not exist in database.");
            }
        }catch(SQLException ex) {
            Logger.getLogger(TestDBCart.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void showAll() {
        try {
            ProductList productList = cartManager.getAllRecords();
            productList.displayProducts();
        }catch(SQLException ex) {
            Logger.getLogger(TestDBCart.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private void testFilter() {
        System.out.print("Username: ");
        String username = in.nextLine();
        System.out.print("Keyword: ");
        String keyword = in.nextLine();
        try {
            ProductList productList = cartManager.getProductByKeyword(username, keyword);
            productList.displayProducts();
        }catch (SQLException ex){
            Logger.getLogger(TestDBCart.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public static void main(String [] args) throws SQLException {
        new TestDBCart().runQueries();
    }

    
}
