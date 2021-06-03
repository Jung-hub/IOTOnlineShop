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
import uts.isd.model.dao.DBProductManager;

/**
 *
 * @author Administrator
 */
/**
 *
 * @author Administrator
 */
public class UpdateProductController extends HttpServlet{
    @Override   
    protected void doPost(HttpServletRequest request, 
            HttpServletResponse response) throws ServletException, IOException {       
        //retrieve the current session
        HttpSession session = request.getSession();
        //Get user from session
        User user = (User) session.getAttribute("user");
        int number = Integer.parseInt(request.getParameter("edit"));
        String redirectURL = "http://localhost:8080/IOTBay/unauthorised.jsp";
        if(user == null || !user.getUsertype().equals("1")) {
            response.sendRedirect(redirectURL);
        }
        //create an instance of the Validator class    
        Validator validator = new Validator();
        validator.clean(session);
          
        
        DBProductManager productManager = (DBProductManager) session.getAttribute("productManager");
        
        try {
            if(productManager.findProduct(number))
                session.setAttribute("product", productManager.getProductByNumber(number));
            request.getRequestDispatcher("updateproduct.jsp").include(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(UpdateProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    } 
}
