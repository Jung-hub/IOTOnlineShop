/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uts.isd.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import uts.isd.model.LogList;
import uts.isd.model.User;
import uts.isd.model.dao.LOGManager;

/**
 *
 * @author Administrator
 */
public class SearchLogServlet extends HttpServlet {
    @Override   
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //retrieve the current session
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String usertype = user.getUsertype();
        
        //Get data from the form
        String month = request.getParameter("month");
        String day = request.getParameter("days");
        
        //Get LOGManager from the current session
        LogList logList = new LogList();
        LOGManager logManager = (LOGManager) session.getAttribute("logManager");
        
        try {
            if(usertype.equals("0")) {
                logList = logManager.getAllLogs().getListByDate(month, day);
            }else {
                logList = logManager.getLogsByNameAndDate(user.getUsername(), month, day);
            }
            session.setAttribute("logList", logList);
            request.getRequestDispatcher("logs.jsp").include(request, response);
        } catch (SQLException ex) {
             Logger.getLogger(SearchLogServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }       
    
}
