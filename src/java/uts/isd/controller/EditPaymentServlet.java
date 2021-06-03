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
import uts.isd.model.dao.DBOrderManager;
import uts.isd.model.dao.DBPaymentManager;

/**
 *
 * @author Administrator
 */
public class EditPaymentServlet extends HttpServlet {
    @Override   
    protected void doPost(HttpServletRequest request, 
            HttpServletResponse response) throws ServletException, IOException { 
        HttpSession session = request.getSession();
        Validator validator = new Validator();
        validator.clean(session);
        User user = (User) session.getAttribute("user");
        String username = user.getUsername();
        String type = request.getParameter("type");
        String number = request.getParameter("number");
        String paymentNo = request.getParameter("paymentNo");
        int convertedNumber = Integer.parseInt(paymentNo);
        DBPaymentManager paymentManager = (DBPaymentManager)session.getAttribute("paymentManager");
        
        
        if(!validator.validatePaymentType(type)) {
            session.setAttribute("paymentTypeErr", "Error: Payment type must consist of 4 to 20 characters");
        }else if(!validator.validatePaymentNumber(number)) {
            session.setAttribute("paymentNumberErr", "Error: Payment number must consist of 6 to 20 digital characters(0-9)");
        }else {
            try {
                Payment payment = paymentManager.getPaymentByPaymentNo(convertedNumber);
                payment.setPaymentType(type);
                payment.setPaymentNumber(number);
                paymentManager.updatePayment(convertedNumber, type, number);
                session.setAttribute("payment", payment);
                session.setAttribute("paymentList", paymentManager.getPaymentByUsername(username));
                session.setAttribute("successInfo", "The payment updated successfully");
                //update relevant order with previous payment record
                DBOrderManager orderManager = (DBOrderManager) session.getAttribute("orderManager");
                orderManager.updateOrderPayment(username, payment.getPaymentType(), payment.getPaymentNumber(), type, number);
                
            } catch (SQLException ex) {
                Logger.getLogger(EditPaymentServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        }
        request.getRequestDispatcher("editpayment.jsp").include(request, response);
        
        
        
    }
}
