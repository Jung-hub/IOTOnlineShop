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
public class DeleteProductServlet extends HttpServlet {
    @Override   
    protected void doPost(HttpServletRequest request, 
            HttpServletResponse response) throws ServletException, IOException {
        //retrieve the current session
        HttpSession session = request.getSession();
        
        //Get user from session
        User user = (User) session.getAttribute("user");
        int number = Integer.parseInt(request.getParameter("delete"));
                
        String redirectURL = "http://localhost:8080/IOTBay/unauthorised.jsp";
        if(user == null || !user.getUsertype().equals("1")) {
            response.sendRedirect(redirectURL);
        }
        
        //retrieve the manager instance from session
        DBProductManager productManager = (DBProductManager) session.getAttribute("productManager");
        
        try {
                if(productManager.findProduct(number)) productManager.deleteProduct(number);
                session.setAttribute("productList", productManager.getAllProducts());
                session.setAttribute("successInfo", "The product is deleted successfully");
                request.getRequestDispatcher("productlist.jsp").include(request, response);
        } catch (SQLException ex) {           
                Logger.getLogger(DeleteProductServlet.class.getName()).log(Level.SEVERE, null, ex);       
        }
        
    }
}
