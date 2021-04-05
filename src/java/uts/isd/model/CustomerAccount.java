/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uts.isd.model;
import java.io.Serializable;
import java.util.*;
/**
 *
 * @author Jung
 */
public class CustomerAccount implements Serializable {
    private LinkedList<Customer> customerList;

    //Constructor
    public CustomerAccount() {
        customerList = new LinkedList<Customer>();
    }
    
    //Setters
    public boolean setAnCustomer(Customer newCustomer) {
        if (!isCustomerExist(newCustomer)) {
            customerList.add(newCustomer);
            return true;
        }
        return false;
            
    }
    
    public boolean deleteCustomer(Customer customer) {
        for(Customer each: customerList) 
            if(isMatchedCustomer(each, customer)) {
                customerList.remove(customer);
                return true;
            }
        return false; 
    }
    
    public boolean updateCustomer(Customer oldProfile, Customer newProfile) {
        
        //remove old customer profile from the customer account list
        customerList.remove(oldProfile);
        
        //check updated customer username is existed in list or not
        //if not, add updated customer into list
        if(!isCustomerExist(newProfile)) {
            customerList.add(newProfile);
            return true;
        }
        //otherwise, add old profile back into list
        customerList.add(oldProfile);
        return false;
    }
    
        
    
    //Getters
    public Customer getAnCustomer(Customer customer) {
        for(Customer each: customerList) 
            if(each.getUsername().equals(customer.getUsername()) && each.getPassword().equals(customer.getPassword())) 
                return each;
        return null;
    }
    
    public Customer getLoggedCustomer() {
        for(Customer each: customerList)
            if(each.getIsLogged() == true)
                return each;
        return null;
    }
    
    public int getCustomerAccountNumber() {
        return customerList.size();
    }
    
    //Comparison
    //the method is to check the customer username already exists in the customer list
    public boolean isCustomerExist(Customer customer) {
        for(Customer each: customerList) {
            if(each.getUsername().equals(customer.getUsername())) {
                return true;
            } 
        }
        return false;
    }
    
    //the method is to check username and pssword is matched the current input from user.
    public boolean isMatchedCustomer(Customer customer, Customer match) {
        if(!customer.getUsername().equals(match.getUsername()))
            return false;
        if(!customer.getPassword().equals(match.getUsername()))
            return false;
        return true;
    }
    public Customer getMatchedUsernamePassword(String username, String password) {
        for(Customer each: customerList) {
            if(each.getUsername().equals(username) && each.getPassword().equals(password)){
                each.setIsLogged(true);
                return each;
            }
                
        }
        return null;
    }
    
    public Customer findCustomerLoggedOn() {
        for (Customer each: customerList) {
            if(each.getIsLogged())
               return each;
        }
        return null;
    }
    public void allCustomerLoggedOut() {
        for (Customer each: customerList) {
            each.setIsLogged(false);
        }
    }
    
    public Customer getCustomerByNumber(int index) {
        return customerList.get(index);
    
    }
    
    public boolean setCustomerLogged(String username, String password) {
        for(Customer each: customerList) {
            if(each.getUsername().equals(username) && each.getPassword().equals(password)) {
                each.setIsLogged(true);
                return true;
            }
        }
        return false;
    }
    
    public boolean isCustomerNameExist(String name) {
    
        for(Customer each: customerList) {
            if(each.getUsername().equals(name)) {
                return true;
            }
        }
    
        return false;
    }
}
