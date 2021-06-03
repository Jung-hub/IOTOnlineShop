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
import uts.isd.model.PaymentList;
import uts.isd.model.User;
import uts.isd.model.dao.DBPaymentManager;

/**
 *
 * @author Administrator
 */
public class SearchPaymentServlet extends HttpServlet {
    @Override   
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String username = user.getUsername();
        String keyword;
        keyword = request.getParameter("keyword");
        Validator validator = new Validator();
        validator.clean(session);
        DBPaymentManager paymentManager = (DBPaymentManager) session.getAttribute("paymentManager");
        System.out.println(username + " " + keyword);
        
        try {
            PaymentList paymentList = paymentManager.getPaymentByKeyword(username, keyword);
            if(paymentList.listSize() != 0) {
                session.setAttribute("paymentList", paymentList);
            }else {
                session.setAttribute("paymentList", paymentManager.getPaymentByUsername(username));
            }
                
            
        } catch (SQLException ex) {
            Logger.getLogger(SearchPaymentServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.getRequestDispatcher("userpayments.jsp").include(request, response);
    }
}
