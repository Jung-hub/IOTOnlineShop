/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uts.isd.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import uts.isd.model.User;

/**
 *
 * @author Administrator
 */
public class OrderController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //retrieve the current session
        HttpSession session = request.getSession();
        
        User user = (User)session.getAttribute("user");
        String redirectURL = "http://localhost:8080/IOTBay/unauthorised.jsp";
        //create an instance of the Validator class    
        Validator validator = new Validator();
        
        //initialise the error message
        validator.clean(session);
        
        if(user.getUsertype().equals("2")) {
            request.getRequestDispatcher("order.jsp").include(request, response);
        }else if(user.getUsertype().equals("1")) {
            request.getRequestDispatcher("allorders.jsp").include(request, response);
        }else {
            response.sendRedirect(redirectURL);
        }
        
        //redirect user to the welcom.jsp
    }
    
}
