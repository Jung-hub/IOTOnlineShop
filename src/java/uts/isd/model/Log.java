/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uts.isd.model;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
/**
 *
 * @author Administrator
 */
public class Log {
    final int number;
    final String username;
    final String type;
    final private Date date;
    final private Time time;
    
    
    public Log(int number, String username, String type, Date date, Time time) {
        this.number = number;
        this.username = username;
        this.type = type;
        this.date = date;
        this.time = time;
        
    }
    
    public int getNumber() {
        return number;
    }
    
    public String getUsername() {
        return username;
    }
    public String getType() {
        return type;
    }
    public Date getDate() {
        return date;
    }
    public Time getTime() {
        return time;
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
            return compared.getTime().compareTo(getDate()) == 0;
        }
        if(numberOfMonth != 0 && numberOfDays == 0) {
            Calendar startCalendar = new GregorianCalendar(year , numberOfMonth - 1 , 1);
            Calendar endCalendar = new GregorianCalendar(year , numberOfMonth - 1, getMonthDays(numberOfMonth));
            return this.date.compareTo(startCalendar.getTime()) >= 0 && this.date.compareTo(endCalendar.getTime()) <= 0;
        }
        
        if(numberOfMonth == 0 && numberOfDays != 0) {
            ArrayList<Date> dateList = new ArrayList<>();
            for(int i = 1; i <= 12; i++) {
                Calendar c = new GregorianCalendar(year , i - 1, numberOfDays, 0, 0, 0);
                dateList.add(new java.sql.Date(c.getTimeInMillis()));
            }
            for(int i = 0; i < 12; i++) {
                if(dateList.get(i).compareTo(this.date) == 0)
                    return true;
            }
            return false;
        }    
        return true;
    }
    
    public String displayLogWithoutUsername() {
        return type + " " + date + " " + time;
    }
    
    
    @Override
    public String toString() {
        return "" + number + " " + username + " " + type + " " + date + " " + time;
    }
}
