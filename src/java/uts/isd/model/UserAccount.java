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
public class UserAccount implements Serializable {
    private LinkedList<User> userList;

    //Constructor
    public UserAccount() {
        userList = new LinkedList<User>();
    }
    
    //Setters
    public void setAnUser(User newCustomer) {
        userList.add(newCustomer);
    }
    
    public boolean deleteAnUser(User customer) {
        for(User each: userList) 
            if(isMatchedCustomer(each, customer)) {
                userList.remove(customer);
                return true;
            }
        return false; 
    }
    
    public boolean updateUser(User oldProfile, User newProfile) {
        
        //remove old customer profile from the customer account list
        userList.remove(oldProfile);
        
        //check updated customer username is existed in list or not
        //if not, add updated customer into list
        if(!isUserExist(newProfile)) {
            userList.add(newProfile);
            return true;
        }
        //otherwise, add old profile back into list
        userList.add(oldProfile);
        return false;
    }
    
    public void setUserStatus(String username, String status) {
        String reverseStatus = status.equals("0")? "1": "0";
        for (User userList1 : userList) {
            if(userList1.getUsername().equals(username)) {
                userList1.setStatus(reverseStatus);
                return;
            }
        }
    
    }
        
    public void removeUser(String username) {
        for(int i = 0; i < userList.size(); i++) {
            if(userList.get(i).getUsername().equals(username)) {
                userList.remove(i);
                return;
            }
        
        }
    
    }
    
    
    //Getters
    public User getAnUser(User customer) {
        for(User each: userList) 
            if(each.getUsername().equals(customer.getUsername()) && each.getPassword().equals(customer.getPassword())) 
                return each;
        return null;
    }
    
    public User getLoggedUser() {
        for(User each: userList)
            if(each.getIsLogged() == true)
                return each;
        return null;
    }
    
    public int getUserAccountNumber() {
        return userList.size();
    }
    
    //Comparison
    //the method is to check the customer username already exists in the customer list
    public boolean isUserExist(User customer) {
        for(User each: userList) {
            if(each.getUsername().equals(customer.getUsername())) {
                return true;
            } 
        }
        return false;
    }
    
    //the method is to check username and pssword is matched the current input from user.
    public boolean isMatchedCustomer(User customer, User match) {
        if(!customer.getUsername().equals(match.getUsername()))
            return false;
        if(!customer.getPassword().equals(match.getUsername()))
            return false;
        return true;
    }
    public User getMatchedUsernamePassword(String username, String password) {
        for(User each: userList) {
            if(each.getUsername().equals(username) && each.getPassword().equals(password)){
                each.setIsLogged(true);
                return each;
            }
                
        }
        return null;
    }
    
    public User findCustomerLoggedOn() {
        for (User each: userList) {
            if(each.getIsLogged())
               return each;
        }
        return null;
    }
    public void allCustomerLoggedOut() {
        for (User each: userList) {
            each.setIsLogged(false);
        }
    }
    
    public User getUserByNumber(int index) {
        return userList.get(index);
    
    }
    
    public boolean setUserLogged(String username, String password) {
        for(User each: userList) {
            if(each.getUsername().equals(username) && each.getPassword().equals(password)) {
                each.setIsLogged(true);
                return true;
            }
        }
        return false;
    }
    
    public boolean isCustomerNameExist(String name) {
        for(User each: userList) {
            if(each.getUsername().equals(name)) {
                return true;
            }
        }
        
        return false;
    }
    
    public void showAllUsers() {
        System.out.println("User table:");
        userList.forEach((each) -> {
            System.out.println(each);
        });
    }
}
