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
public class AccountListController extends HttpServlet{
    @Override   
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //retrieve the current session
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String redirectURL = "http://localhost:8080/IOTBay/unauthorised.jsp";
        if(user == null || !user.getUsertype().equals("0")) {
            response.sendRedirect(redirectURL);
        }
        DBManager manager = (DBManager) session.getAttribute("manager");
        try {
            UserAccount accountList = manager.getAllUsersWithoutRoot();
            session.setAttribute("accountList", accountList);
            request.getRequestDispatcher("accountlist.jsp").include(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(AccountListController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
