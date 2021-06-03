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
import uts.isd.model.UserAccount;
import uts.isd.model.dao.DBManager;

/**
 *
 * @author Administrator
 */
public class StatusServlet extends HttpServlet {
    @Override   
    protected void doPost(HttpServletRequest request, 
            HttpServletResponse response) throws ServletException, IOException {
        //retrieve the current session
        HttpSession session = request.getSession();
        
        //Get user from session
        User user = (User) session.getAttribute("user");
        UserAccount accountList = (UserAccount) session.getAttribute("accountList");
        String username = request.getParameter("username");
        String status = request.getParameter("status");
                
        String redirectURL = "http://localhost:8080/IOTBay/unauthorised.jsp";
        if(user == null || !user.getUsertype().equals("0")) {
            response.sendRedirect(redirectURL);
        }
        
        //retrieve the manager instance from session
        DBManager manager = (DBManager) session.getAttribute("manager");
        
        try {
            manager.updateUserStatus(username, status);
            accountList.setUserStatus(username, status);
            //session.setAttribute("accountList", getAllUsersWithoutRoot());
            session.setAttribute("accountList", accountList);
            request.getRequestDispatcher("accountlist.jsp").include(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(StatusServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
