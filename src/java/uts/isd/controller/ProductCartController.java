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
import uts.isd.model.Product;
import uts.isd.model.ProductList;
import uts.isd.model.User;

/**
 *
 * @author Administrator
 */
public class ProductCartController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //retrieve the current session
        HttpSession session = request.getSession();
        
        User user = (User)session.getAttribute("user");
        String redirectURL = "http://localhost:8080/IOTBay/unauthorised.jsp";
        if(user == null || !user.getUsertype().equals("2")) {
            response.sendRedirect(redirectURL);
        }
        //create an instance of the Validator class    
        Validator validator = new Validator();
        
        //initialise the error message
        validator.clean(session);
         
        //redirect user to the welcom.jsp
        request.getRequestDispatcher("cart.jsp").include(request, response);
        
        
    }
    
}
