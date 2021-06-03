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
import uts.isd.model.dao.DBPaymentManager;

/**
 *
 * @author Administrator
 */
public class DeletePaymentServlet extends HttpServlet {
    @Override   
    protected void doPost(HttpServletRequest request, 
            HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String username = user.getUsername();
        
        String paymentNo = request.getParameter("delete");
        int convertedNumber = Integer.parseInt(paymentNo);
        DBPaymentManager paymentManager = (DBPaymentManager)session.getAttribute("paymentManager");
        try {
            paymentManager.deletePayment(convertedNumber);
            session.setAttribute("successInfo","Your payment method is deleted successfully");
            session.setAttribute("paymentList", paymentManager.getPaymentByUsername(username));
        } catch (SQLException ex) {
            Logger.getLogger(DeletePaymentServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.getRequestDispatcher("userpayments.jsp").include(request, response);
    }
}
