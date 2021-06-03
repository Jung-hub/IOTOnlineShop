/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uts.isd.model;

import java.util.ArrayList;

/**
 *
 * @author Administrator
 */
public class OrderList {
    private ArrayList<Order> orderList;

    
    public OrderList() {
        this.orderList = new ArrayList<Order>();
    }
    public OrderList(ArrayList<Order> orderList) {
        this.orderList = orderList;
    }
    
    public void addOrder(Order newOrder) {
        this.orderList.add(newOrder);
    }
    
    public int listSize() {
        return orderList.size();
    }
    
    public int availableOrder() {
        int count = 0;
        for(Order each: orderList) {
            if(each.getStatus() >= 0) {
                count = count + 1;
            }
        } 
        return count;
    }
    
    
    public Order getOrderByIndex(int i) {
        return orderList.get(i);
    }
    public OrderList getListByDate(String month, String day) {
        OrderList filterList = new OrderList();
        
        for(int i = 0; i < listSize(); i++) {
            if(getOrderByIndex(i).isDateMatchByDate(month, day)) {
                filterList.addOrder(getOrderByIndex(i));
            }
        }
        return filterList;
    }
    public OrderList getListByOrderID(int orderID) {
        OrderList filterList = new OrderList();
        
        for(int i = 0; i < listSize(); i++) {
            if(getOrderByIndex(i).getOrderID() == orderID) {
                filterList.addOrder(getOrderByIndex(i));
            }
        }
        return filterList;
    }
    
    
    public void displayOrders() {
        for(Order each: orderList) {
            System.out.println(each);
        }
    }
    
}
