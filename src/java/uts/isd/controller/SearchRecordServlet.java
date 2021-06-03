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
import uts.isd.model.OrderList;
import uts.isd.model.User;
import uts.isd.model.dao.DBOrderManager;

/**
 *
 * @author Administrator
 */
public class SearchRecordServlet extends HttpServlet {
    @Override   
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //retrieve the current session
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String usertype = user.getUsertype();
        
        //Get data from the form
        String month = request.getParameter("month");
        String day = request.getParameter("days");
        String redirectURL = "http://localhost:8080/IOTBay/unauthorised.jsp";
        if(user == null || !usertype.equals("2")) {
            response.sendRedirect(redirectURL);
        }
        OrderList paymentRecord = (OrderList)session.getAttribute("paymentRecord");
        Validator validator = new Validator();
        validator.clean(session);
        
        if(!validator.validateDate(month, day)) {
            session.setAttribute("dateFormErr", "Error: Date format incorrect");
            request.getRequestDispatcher("paymentrecord.jsp").include(request, response);
        }else {
            
            OrderList filterRecord = paymentRecord.getListByDate(month, day);
            if(filterRecord.listSize() != 0) {
                session.setAttribute("paymentRecord", filterRecord);
            }
            else {
                session.setAttribute("dateFormErr", "No matches, display all records");
            }    
            request.getRequestDispatcher("paymentrecord.jsp").include(request, response);
        }
        
        
    }       
    
}