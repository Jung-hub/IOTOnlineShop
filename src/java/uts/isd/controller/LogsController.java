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
public class LogsController extends HttpServlet{
    @Override   
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {       
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String redirectURL = "http://localhost:8080/IOTBay/unauthorised.jsp";
        if(user == null) {
            response.sendRedirect(redirectURL);
        }
        String usertype = user.getUsertype();
        LogList logList = new LogList();
        LOGManager logManager = (LOGManager) session.getAttribute("logManager");
        try {
            if(usertype.equals("0")) {
                logList = logManager.getAllLogs();
            }else {
                logList = logManager.getLogsByUsername(user.getUsername());
            }
            session.setAttribute("logList", logList);
            request.getRequestDispatcher("logs.jsp").include(request, response);
        } catch (SQLException ex) {
             Logger.getLogger(LogsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
