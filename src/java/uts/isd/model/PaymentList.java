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
public class PaymentList {
    private ArrayList<Payment> paymentList;

    public PaymentList(ArrayList<Payment> paymentList) {
        this.paymentList = paymentList;
    }
    
    public PaymentList() {
        this.paymentList = new ArrayList<Payment>();
    }
    
    public void addPayment(Payment payment) {
        this.paymentList.add(payment);
    } 
    
    public int listSize() {
        return paymentList.size();
    }
    
    public Payment getPaymentByIndex(int index) {
        return paymentList.get(index);
    
    }
    
    
    public void displayPaymentList() {
        paymentList.forEach((each) -> {
            System.out.println(each);
        });
    }
}
