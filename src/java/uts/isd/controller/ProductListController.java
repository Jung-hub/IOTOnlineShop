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
import uts.isd.model.ProductList;
import uts.isd.model.User;
import uts.isd.model.dao.DBProductManager;


/**
 *
 * @author Administrator
 */
public class ProductListController extends HttpServlet{
    @Override   
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //retrieve the current session
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String redirectURL = "http://localhost:8080/IOTBay/unauthorised.jsp";
        if(user == null || !user.getUsertype().equals("1")) {
            response.sendRedirect(redirectURL);
        }
        DBProductManager productManager = (DBProductManager) session.getAttribute("productManager");
        
        try {
            ProductList productList = productManager.getAllProducts();
            session.setAttribute("productList", productList);
            request.getRequestDispatcher("productlist.jsp").include(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ProductListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
