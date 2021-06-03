/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uts.isd.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author Administrator
 */
public class Order {
    private int orderID;
    private String username;
    private Date orderDate;
    private int status;
    private String paymentType;
    private String paymentNumber;
    private Date paymentDate;
    private int amount;
    
    public Order() {
        orderID = -1;
        username = "None";
        orderDate = new Date(System.currentTimeMillis());
        status = -1;
        paymentType = "None";
        paymentNumber = "None";
        paymentDate = new Date(System.currentTimeMillis());
        amount = 0;
    }
    
    public Order(int orderID, String username, Date orderDate, int status, int amount) {
        this.orderID = orderID;
        this.username = username;
        this.orderDate = orderDate;
        this.status = status;
        this.amount = amount;
    }

    public Order(int orderID, String username, Date orderDate, int status, String paymentType, String paymentNumber, Date paymentDate, int amount) {
        this.orderID = orderID;
        this.username = username;
        this.orderDate = orderDate;
        this.status = status;
        this.paymentType = paymentType;
        this.paymentNumber = paymentNumber;
        this.paymentDate = paymentDate;
        this.amount = amount;
    }
    private int getMonthDays(int month) {
        switch(month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 2:
                return 28;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            default:
                return 0;
        }
    
    }
    
    private boolean isMonthIntegerable(String month) {
        try {
            int number = Integer.parseInt(month);
            if(number > 0 && number <= 12)
                return true;
            return false;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    private boolean isDayIntegerable(String days) {
        try {
            int number = Integer.parseInt(days);
            return number > 0 && number <= 31;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    public boolean isDateMatchByDate(String month, String days) {
        
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int numberOfMonth = isMonthIntegerable(month)? Integer.parseInt(month): 0;   
        int numberOfDays = isDayIntegerable(days)? Integer.parseInt(days): 0; 
        
        if(numberOfMonth != 0 && numberOfDays != 0) {
            Calendar compared = new GregorianCalendar(year, numberOfMonth - 1, numberOfDays);
            return compared.getTime().compareTo(getOrderDate()) == 0;
        }
        if(numberOfMonth != 0 && numberOfDays == 0) {
            Calendar startCalendar = new GregorianCalendar(year , numberOfMonth - 1 , 1);
            Calendar endCalendar = new GregorianCalendar(year , numberOfMonth - 1, getMonthDays(numberOfMonth));
            return this.orderDate.compareTo(startCalendar.getTime()) >= 0 && this.orderDate.compareTo(endCalendar.getTime()) <= 0;
        }
        
        if(numberOfMonth == 0 && numberOfDays != 0) {
            ArrayList<Date> dateList = new ArrayList<>();
            for(int i = 1; i <= 12; i++) {
                Calendar c = new GregorianCalendar(year , i - 1, numberOfDays, 0, 0, 0);
                dateList.add(new java.sql.Date(c.getTimeInMillis()));
            }
            for(int i = 0; i < 12; i++) {
                if(dateList.get(i).compareTo(this.orderDate) == 0)
                    return true;
            }
            return false;
        }    
        return true;
    }
    
    
    public int getOrderID() {
        return orderID;
    }

    public String getUsername() {
        return username;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public int getStatus() {
        return status;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public String getPaymentNumber() {
        return paymentNumber;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public int getAmount() {
        return amount;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public void setPaymentNumber(String paymentNumber) {
        this.paymentNumber = paymentNumber;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
    
    @Override
    public String toString() {
        return "" + this.orderID + " " +
        this.username + " " +
        this.orderDate + " " +
        this.status + " " +
        this.paymentType + " " +
        this.paymentNumber + " " +
        this.paymentDate + " " +
        this.amount;
    }
}
