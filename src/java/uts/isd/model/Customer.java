/*
 * The customer class is to store all customer profile from user input
 */
package uts.isd.model;
import java.io.Serializable;



/**
 *
 * @author Administrator
 */
public class Customer implements Serializable {
    private String username;
    private String userFirstName;
    private String userLastName;
    private String password;
    private String email;
    private String birthday;
    private String phone;
    private boolean isLogged;

    public Customer() {
        username = "";
        password = "";
        email = "";
        birthday = "";
        phone = "";
        isLogged = false;
    
    }
    
    //customer constructor is to initialise all fields
    public Customer(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.userFirstName = "";
        this.userLastName = "";
        this.birthday = "";
        this.phone = "";
        this.isLogged = false;
    }
    
    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    //getter and setter in class
    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public void setIsLogged(boolean logCondition) {
        this.isLogged = logCondition;
    }
    
    public boolean getIsLogged() {
        return this.isLogged;
    }
    
    public void setProfile(String firstName, String lastName, String mail, String birth, String phone) {
        this.setUserFirstName(firstName);
        this.setUserLastName(lastName);
        this.setEmail(mail);
        this.setBirthday(birth);
        this.setPhone(phone);
    }
    
    
    @Override
    public String toString() {
        return username + " " + password + " " + userFirstName + " " + userLastName + " " + email + " " + " " + birthday + " " + phone;
    }
}