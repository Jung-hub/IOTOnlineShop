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
import uts.isd.model.OrderList;
import uts.isd.model.ProductList;
import uts.isd.model.dao.DBConnector;
import uts.isd.model.dao.DBOrderManager;
import uts.isd.model.dao.DBOrderlineManager;

/**
 *
 * @author Administrator
 */
public class TestDBOrder {
    private static Scanner in = new Scanner(System.in);
    private DBConnector connector;
    private Connection connection;
    private DBOrderManager orderManager;
    
    public TestDBOrder() throws SQLException {
        try {
            connector = new DBConnector();
            connection = connector.getConnection();
            orderManager = new DBOrderManager(connection);
        
        }catch(ClassNotFoundException | SQLException ex) {
            Logger.getLogger(TestDBOrder.class.getName()).log(Level.SEVERE, null, ex);
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
            System.out.print("Username: ");
            String username = in.nextLine();
        
            System.out.print("Amount: ");
            int amount = Integer.parseInt(in.nextLine());
            
            orderManager.addOrder(username, amount);
            System.out.println("A order is added into database.");
        }catch(SQLException ex) {
            Logger.getLogger(TestDBOrder.class.getName()).log(Level.SEVERE, null, ex);
        }catch (NumberFormatException e){
            System.out.println("Input format is incorrect");
        }
    }
    
    private void testRead() throws SQLException {
        System.out.print("Username: ");
        String username = in.nextLine();
        
        OrderList orderList = orderManager.getOrdersByUsername(username);
        orderList.displayOrders();
    }
    
    private void testUpdate() {
        
        try {
            System.out.print("OrderID: ");
            int orderID = Integer.parseInt(in.nextLine());
            
            System.out.print("Payment Type: ");
            String paymentType = in.nextLine();
            
            System.out.print("Payment Number: ");
            String paymentNumber = in.nextLine();
            
            if(orderManager.findOrderByOrderID(orderID)) {
                System.out.print("Status: ");
                int status = Integer.parseInt(in.nextLine());
                                
                orderManager.updateOrder(orderID, status, paymentType.toLowerCase(), paymentNumber);
                System.out.println("Order payment is updated.");
            }else {
                System.out.println("Order does not exist in database.");
            }
        }catch(SQLException ex) {
            Logger.getLogger(TestDBOrder.class.getName()).log(Level.SEVERE, null, ex);
        }catch (NumberFormatException e){
            System.out.println("Input format is incorrect");
        }
    }
    
    private void testDelete() {
        try {
            System.out.print("OrderID: ");
            int orderID = Integer.parseInt(in.nextLine());
            
            if(orderManager.findOrderByOrderID(orderID)) {
                //test update user profile
                orderManager.deleteOrder(orderID);
                System.out.println("Order is removed from database.");
            }else {
                System.out.println("Order does not exist in database.");
            }
        }catch(SQLException ex) {
            Logger.getLogger(TestDBOrder.class.getName()).log(Level.SEVERE, null, ex);
        }catch (NumberFormatException e){
            System.out.println("Input format is incorrect");
        }
    }
    
    private void showAll() {
        try {
            OrderList orderList = orderManager.getAllOrders();
            orderList.displayOrders();
        }catch(SQLException ex) {
            Logger.getLogger(TestDBOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private void testFilter() {
        try {
            System.out.print("Username: ");
            String username = in.nextLine();
            
            System.out.print("Payment Type: ");
            String paymentType = in.nextLine();
            
            System.out.print("Payment Number: ");
            String paymentNumber = in.nextLine();
            OrderList orderList = orderManager.getOrdersByPaymentDetail(username, paymentType, paymentNumber);
            orderList.displayOrders();
        }catch (SQLException ex){
            Logger.getLogger(TestDBOrder.class.getName()).log(Level.SEVERE, null, ex);
        }catch (NumberFormatException e){
            System.out.println("Input format is incorrect");
        }
    }
    
    
    public static void main(String [] args) throws SQLException {
        new TestDBOrder().runQueries();
    }

    
}
