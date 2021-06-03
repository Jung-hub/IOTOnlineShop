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
import uts.isd.model.User;
import uts.isd.model.dao.LOGManager;

/**
 *
 * @author Administrator
 */
public class LogoutController extends HttpServlet {
    @Override   
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
        //retrieve the current session
        HttpSession session = request.getSession();
        
        User user = (User) session.getAttribute("user");
        LOGManager logManager = (LOGManager) session.getAttribute("logManager");
        
        try {
            logManager.addLog(user.getUsername(), "logout");
        } catch (SQLException ex) {
            Logger.getLogger(LogoutController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        session.invalidate();
        request.getRequestDispatcher("logout.jsp").include(request, response);
        
        
    }
    
    
}
