/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uts.isd.model;

import java.sql.Date;

/**
 *
 * @author Administrator
 */
public class Payment {
    private int paymentNo;
    private String paymentType;
    private String paymentNumber;
    private Date date;
    
    public Payment(int paymentNumber, String type, String number, Date date) {
        this.paymentNo = paymentNumber;
        this.paymentType = type;
        this.paymentNumber = number;
        this.date = date;
    }
    
    public Payment(int paymentNumber, String type, String number) {
        this.paymentNo = paymentNumber;
        this.paymentType = type;
        this.paymentNumber = number;
        this.date = new Date(System.currentTimeMillis());
    }

    public Payment () {
        this.paymentNo = -1;
        this.paymentType = "none";
        this.paymentNumber = "none";
        this.date = new Date(System.currentTimeMillis());
    }
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setPaymentNo(int paymentNo) {
        this.paymentNo = paymentNo;
    }

    public int getPaymentNo() {
        return paymentNo;
    }
    
    public String getPaymentType() {
        return paymentType;
    }

    public String getPaymentNumber() {
        return paymentNumber;
        
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public void setPaymentNumber(String paymentNumber) {
        this.paymentNumber = paymentNumber;
    }
    
    @Override
    public String toString() {
        return "" + paymentNo + " " + paymentType + " " + paymentNumber;
    }
}
