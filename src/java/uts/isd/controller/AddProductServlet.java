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
import uts.isd.model.Product;
import uts.isd.model.User;
import uts.isd.model.dao.DBManager;
import uts.isd.model.dao.DBProductManager;

/**
 *
 * @author Administrator
 */
/**
 *
 * @author Administrator
 */
public class AddProductServlet extends HttpServlet{
    @Override   
    protected void doPost(HttpServletRequest request, 
        HttpServletResponse response) throws ServletException, IOException {
        //1- retrieve the current session
        HttpSession session = request.getSession();
        
        //2- create an instance of the Validator class    
        Validator validator = new Validator();
        
        //3- Get user update password from the form.
        String type = request.getParameter("type");
        String name = request.getParameter("name");
        String price = request.getParameter("price");
        String stock = request.getParameter("stock");
        
        //4 retrieve the manager instance from session
        DBProductManager productManager = (DBProductManager) session.getAttribute("productManager");
                
        //5 initialise the error message
        validator.clean(session);
        
        if (!validator.validateProductName(name)) {           
            //set incorrect email error to the session
            session.setAttribute("productNameErr", "Error: Product name format incorrect");
            //redirect user back to the register.jsp
            request.getRequestDispatcher("addproduct.jsp").include(request, response);
        } else if (!validator.validateProductType(type)) {                  
            //set incorrect password error to the session
            session.setAttribute("productTypeErr", "Error: Product type format incorrect");
            //redirect user back to the register.jsp
            request.getRequestDispatcher("addproduct.jsp").include(request, response);
        } else if(!validator.validateProductNumber(price)) {
            //set incorrect password different error to the session
            session.setAttribute("productPriceErr", "Error: Product price format incorrect");
            //redirect user back to the register.jsp
            request.getRequestDispatcher("addproduct.jsp").include(request, response);
        }else if(!validator.validateProductNumber(stock)) {
            //set incorrect password different error to the session
            session.setAttribute("productStockErr", "Error: Product stock format incorrect");
            //redirect user back to the register.jsp
            request.getRequestDispatcher("addproduct.jsp").include(request, response);
        }else {
            try {
                //find user by email and password
                if(productManager.findProductByName(name)) {
                    //set information of that the user already exists
                    session.setAttribute("existErr", "Error: Product already exists");
                    //redirect user to the welcom.jsp
                    request.getRequestDispatcher("addproduct.jsp").include(request, response);
                }else {
                    
                    //store product into databse
                    productManager.addProduct(name, type, Integer.parseInt(price), Integer.parseInt(stock));
                    
                    //store information into session
                    session.setAttribute("successInfo", "New product is created successfully");
                    
                    //update account list into session
                    session.setAttribute("productList", productManager.getAllProducts());
                    
                    //redirect user to the welcome.jsp  
                    request.getRequestDispatcher("addproduct.jsp").include(request, response);
                }
            } catch (SQLException ex) {           
                Logger.getLogger(AddProductServlet.class.getName()).log(Level.SEVERE, null, ex);       
            }
        }
    }   
}
