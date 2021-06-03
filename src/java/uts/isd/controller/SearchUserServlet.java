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
import uts.isd.model.UserAccount;
import uts.isd.model.dao.DBManager;
import uts.isd.model.dao.LOGManager;

/**
 *
 * @author Administrator
 */
public class SearchUserServlet extends HttpServlet {
    @Override   
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //retrieve the current session
        HttpSession session = request.getSession();
        
        String keyword;
        String usertype;
        keyword = request.getParameter("keyword");
        usertype = request.getParameter("usertype");
        
        
        DBManager manager = (DBManager) session.getAttribute("manager");
        
        UserAccount accountList;
             
        try {
            if(usertype.equals("0")) {
                accountList = manager.getAllUsersByKeyWord(keyword);
            }else {
                accountList = manager.getAllUsersByKeyWord(usertype, keyword);
            }
            session.setAttribute("accountList", accountList);
            request.getRequestDispatcher("accountlist.jsp").include(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(SearchUserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
