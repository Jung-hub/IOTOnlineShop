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
import uts.isd.model.Payment;
import uts.isd.model.User;
import uts.isd.model.dao.DBPaymentManager;

/**
 *
 * @author Administrator
 */
public class EditPaymentController extends HttpServlet {
    @Override   
    protected void doPost(HttpServletRequest request, 
            HttpServletResponse response) throws ServletException, IOException { 
        HttpSession session = request.getSession();
        
        User user = (User) session.getAttribute("user");
        String redirectURL = "http://localhost:8080/IOTBay/unauthorised.jsp";
        if(user == null || !user.getUsertype().equals("2")) {
            response.sendRedirect(redirectURL);
        }
        String paymentNo = (String) request.getParameter("edit");
        int payNumber = Integer.parseInt(paymentNo);
        DBPaymentManager paymentManager = (DBPaymentManager) session.getAttribute("paymentManager");
        try {
            Payment payment = paymentManager.getPaymentByPaymentNo(payNumber);
            session.setAttribute("payment", payment);
        } catch (SQLException ex) {
            Logger.getLogger(EditPaymentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.getRequestDispatcher("editpayment.jsp").include(request, response);
        
    }
}
