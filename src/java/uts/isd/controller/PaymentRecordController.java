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
import uts.isd.model.Payment;
import uts.isd.model.User;
import uts.isd.model.dao.DBOrderManager;
import uts.isd.model.dao.DBOrderlineManager;
import uts.isd.model.dao.DBPaymentManager;

/**
 *
 * @author Administrator
 */
public class PaymentRecordController extends HttpServlet{
     protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
         //retrieve the current session
        HttpSession session = request.getSession();
        
        User user = (User)session.getAttribute("user");
        String redirectURL = "http://localhost:8080/IOTBay/unauthorised.jsp";
        if(user == null || !user.getUsertype().equals("2")) {
            response.sendRedirect(redirectURL);
        }
        Validator validator = new Validator();
        validator.clean(session);
        String paymentNo = (String) request.getParameter("record");
        int payNumber = Integer.parseInt(paymentNo);
        DBPaymentManager paymentManager = (DBPaymentManager) session.getAttribute("paymentManager");
        DBOrderManager orderManager = (DBOrderManager) session.getAttribute("orderManager");
        
        
         try {
             Payment payment = paymentManager.getPaymentByPaymentNo(payNumber);
             OrderList orderList = orderManager.getOrdersByPaymentDetail(user.getUsername(), payment.getPaymentType(), payment.getPaymentNumber());
             session.setAttribute("paymentRecord", orderList);
             request.getRequestDispatcher("paymentrecord.jsp").include(request, response);
         } catch (SQLException ex) {
             Logger.getLogger(PaymentRecordController.class.getName()).log(Level.SEVERE, null, ex);
         }
        
        
         
         
     }
    
}
