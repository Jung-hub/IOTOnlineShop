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
public class LogList {
    ArrayList<Log> list;
    
    public LogList() {
        list = new ArrayList<>();
    }
    
    public void addLog(Log log) {
        list.add(log);
    }
    
    public int listSize() {
        return list.size();
    }
    
    public Log getLogByIndex(int index) {
        return list.get(index);
    }
    
    public LogList getListByDate(String month, String day) {
        LogList filterList = new LogList();
        
        for(int i = 0; i < listSize(); i++) {
            if(getLogByIndex(i).isDateMatchByDate(month, day)) {
                filterList.addLog(getLogByIndex(i));
            }
        }
        return filterList;
    }
    
    public void displayLogs() {
        if(listSize() == 0) {
            System.out.println("No records.");
            return;
        }
        for(int i = 0; i < listSize(); i++) {
            System.out.println(getLogByIndex(i));
        }
    
    }
}
