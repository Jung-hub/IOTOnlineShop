/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uts.isd.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
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
import uts.isd.model.dao.DBOrderlineManager;
import uts.isd.model.dao.DBPaymentManager;

/**
 *
 * @author Administrator
 */
public class SubmitOrderServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //retrieve the current session
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String redirectURL = "http://localhost:8080/IOTBay/unauthorised.jsp";
        if(user == null || !user.getUsertype().equals("2")) {
            response.sendRedirect(redirectURL);
        }
        Validator validator = new Validator();
        validator.clean(session);
        NumberFormat formatter = new DecimalFormat("00000000000000000000");
        DBOrderManager orderManager = (DBOrderManager) session.getAttribute("orderManager");
        DBOrderlineManager orderlineManager = (DBOrderlineManager) session.getAttribute("orderlineManager");
        DBPaymentManager paymentManager = (DBPaymentManager) session.getAttribute("paymentManager");
        
       
        String payment = request.getParameter("payment");
        boolean isInputExistUserPaymentList = false;
        boolean isOtherPaymentSelected = payment.equals("other")? true: false;
        
        String type;
        String number;
        
        if(isOtherPaymentSelected) {
            type = request.getParameter("type");
            number = request.getParameter("number");
                      
            try {
                isInputExistUserPaymentList = paymentManager.findPaymentByTypeAndNumber(user.getUsername(), type, number);
            } catch (SQLException ex) {
                Logger.getLogger(SubmitOrderServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(!validator.validatePaymentType(type)) {
                session.setAttribute("paymentTypeErr","Error: At least 4 characters for payment type input");
                request.getRequestDispatcher("submitorder.jsp").include(request, response);
            }else if(!validator.validatePaymentNumber(number)) {
                session.setAttribute("paymentNumberErr","Error: All digit characters for payment number");
                request.getRequestDispatcher("submitorder.jsp").include(request, response);
            }else if(isInputExistUserPaymentList) {
                session.setAttribute("existErr", "Error: Input payment already exists in your payment list");
                request.getRequestDispatcher("submitorder.jsp").include(request, response);
            }else {
                try {
                    //add new payment to the user payment list
                    paymentManager.addPayment(user.getUsername(), type, number);
                    //update order payment
                    int orderID = Integer.parseInt(request.getParameter("pay"));
                    orderManager.updateOrder(orderID, 1, type, number);
                    orderlineManager.updateAllProductStatus(orderID, 1);
                    //update user order list
                    session.setAttribute("orderList", orderManager.getOrdersByUsername(user.getUsername()));
                    //push page to order.jsp
                    request.getRequestDispatcher("order.jsp").include(request, response);
                } catch (SQLException ex) {
                    Logger.getLogger(SubmitOrderServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }else {
            try {
                int paymentNo = Integer.parseInt(payment);
                Payment paymentDetail = paymentManager.getPaymentByPaymentNo(paymentNo);
                int orderID = Integer.parseInt(request.getParameter("pay"));
                orderManager.updateOrder(orderID, 1, paymentDetail.getPaymentType(), paymentDetail.getPaymentNumber());
                orderlineManager.updateAllProductStatus(orderID, 1);
                session.setAttribute("orderList", orderManager.getOrdersByUsername(user.getUsername()));
                request.getRequestDispatcher("order.jsp").include(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(SubmitOrderServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NumberFormatException ex1) {
                System.out.println("Convert Error:");
            }
        }
        
        
        
        
        
    }
    
}
