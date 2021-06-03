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
public class AddPaymentServlet extends HttpServlet{
    @Override   
    protected void doPost(HttpServletRequest request, 
        HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        User user = (User)session.getAttribute("user");
        String redirectURL = "http://localhost:8080/IOTBay/unauthorised.jsp";
        if(user == null || !user.getUsertype().equals("2")) {
           response.sendRedirect(redirectURL);
        }
        Validator validator = new Validator();
        validator.clean(session);
        String username = user.getUsername();
        String type = request.getParameter("type");
        String number = request.getParameter("number");
        DBPaymentManager paymentManager = (DBPaymentManager) session.getAttribute("paymentManager");
        
        
        if (!validator.validatePaymentType(type)) {
            session.setAttribute("paymentTypeErr","Error: Payment type must consist of 4 to 20 characters");
        }else if(!validator.validatePaymentNumber(number)) {
            session.setAttribute("paymentNumberErr","Error: Payment number must consist of 6 to 20 digital characters(0-9)");
        }else {
            try {
                if(paymentManager.findPaymentByTypeAndNumber(username, type, number)){
                    session.setAttribute("existErr","Error: Payment already exists");
                
                }else {
                    paymentManager.addPayment(username, type, number);
                    session.setAttribute("successInfo","New payment is created successfully");
                    session.setAttribute("paymentList", paymentManager.getPaymentByUsername(username));
                }
            } catch (SQLException ex) {
                Logger.getLogger(AddPaymentServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        request.getRequestDispatcher("adduserpayment.jsp").include(request, response);
    }
}
