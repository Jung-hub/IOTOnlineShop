package uts.isd.controller;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpSession;


public class Validator implements Serializable{ 

 
    private final String emailPattern = "([a-zA-Z0-9]+)(([._-])([a-zA-Z0-9]+))*(@)([a-z]+)(.)([a-z]{3})((([.])[a-z]{0,2})*)";
    private final String namePattern = "([A-Z][a-z]+[\\s])+[A-Z][a-z]*";
    private final String firstLastNamePattern = "[A-Za-z\\s]{1,20}";
    private final String passwordPattern = "[A-za-z0-9]{4,15}";
    private final String usernamePattern = "[A-Za-z0-9]{4,15}";
    private final String phonePattern = "[0-9]{8,13}";
    private final String productNamePattern = "[A-Za-z0-9\\s]{3,20}";
    private final String productTypePattern = "[A-Za-z0-9\\s]{3,20}";
    private final String productNumberPattern = "[0-9]{1,}";
    private final String paymentTypePattern = "[A-Za-z\\s]{4,20}";
    private final String paymentNumberPattern = "[0-9]{6,20}";
    public Validator(){}       


    public boolean validate(String pattern, String input){       
        Pattern regEx = Pattern.compile(pattern);       
        Matcher match = regEx.matcher(input);       
        return match.matches(); 
    }       

    public boolean checkEmpty(String email, String password){       
        return  email.isEmpty() || password.isEmpty();   
    }

   
    public boolean validateEmail(String email){                       
       return validate(emailPattern,email);   
    }

    public boolean validateFirstOrLastName(String name){
       return validate(firstLastNamePattern,name); 
    }  
       
    public boolean validateName(String name){
       return validate(namePattern,name); 
    }       
   
    public boolean validateUsername(String username){
       return validate(usernamePattern,username); 
    }
    
    public boolean validatePassword(String password){
       return validate(passwordPattern,password); 
    }
    
    public boolean isValidatePassword(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }
    
    public boolean isAccountLocked(String status) {
        return status.equals("0");
    }
    
    public boolean validateProductName(String name){
       return validate(productNamePattern,name); 
    } 
    
    public boolean validateProductType(String type){
       return validate(productTypePattern,type); 
    }
    
    public boolean validateProductNumber(String number){
       return validate(productNumberPattern,number); 
    }
    
    public boolean validatePaymentType(String type){
       return validate(paymentTypePattern,type); 
    }
    
    public boolean validatePaymentNumber(String number){
       return validate(paymentNumberPattern,number); 
    }
    
    public boolean validatePhoneNumber(String number){
       return validate(phonePattern,number); 
    }
    
    
    public boolean validateDate(String month, String days) {
        if(validateProductNumber(month) && validateProductNumber(days)){
            int intMonth = Integer.parseInt(month);
            int intDays = Integer.parseInt(days);
            if((intMonth <= 12 && intMonth >= 0) && (intDays > 0 && intDays <= getDaysByMonth(intMonth)))
                return true;
        }
        return false;
    }
    
    public int getDaysByMonth(int month) {
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
                return -1;
        
        }
    }
    public void clean(HttpSession session) {
        session.setAttribute("emailErr", "");
        session.setAttribute("passErr", "");
        session.setAttribute("nameErr", "");
        session.setAttribute("firstnameErr", "");
        session.setAttribute("lastnameErr", "");
        session.setAttribute("existErr", "");
        session.setAttribute("usernameErr", "");
        session.setAttribute("passDiffErr", "");
        session.setAttribute("phoneNumberErr", "");
        session.setAttribute("profileUpdate", "");
        session.setAttribute("userLock", "");
        session.setAttribute("productNameErr","");
        session.setAttribute("productTypeErr","");
        session.setAttribute("productPriceErr","");
        session.setAttribute("productStockErr","");
        session.setAttribute("dateFormErr", "");
        session.setAttribute("successInfo", "");
        session.setAttribute("paymentTypeErr","");
        session.setAttribute("paymentNumberErr","");
    }
}