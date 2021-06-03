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
import uts.isd.model.dao.DBProductManager;

/**
 *
 * @author Administrator
 */
public class UpdateProductServlet extends HttpServlet{
    @Override   
    protected void doPost(HttpServletRequest request, 
        HttpServletResponse response) throws ServletException, IOException {
        //1- retrieve the current session
        HttpSession session = request.getSession();
        //2- create an instance of the Validator class    
        Validator validator = new Validator();
        
        //3- Get user update password from the form.
        String number = request.getParameter("number");
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
            request.getRequestDispatcher("updateproduct.jsp").include(request, response);
        } else if (!validator.validateProductType(type)) {                  
            //set incorrect password error to the session
            session.setAttribute("productTypeErr", "Error: Product type format incorrect");
            //redirect user back to the register.jsp
            request.getRequestDispatcher("updateproduct.jsp").include(request, response);
        } else if(!validator.validateProductNumber(price)) {
            //set incorrect password different error to the session
            session.setAttribute("productPriceErr", "Error: Product price format incorrect");
            //redirect user back to the register.jsp
            request.getRequestDispatcher("updateproduct.jsp").include(request, response);
        }else if(!validator.validateProductNumber(stock)) {
            //set incorrect password different error to the session
            session.setAttribute("productStockErr", "Error: Product stock format incorrect");
            //redirect user back to the register.jsp
            request.getRequestDispatcher("updateproduct.jsp").include(request, response);
        }else {
            try {
                //store product into databse
                productManager.updateProduct(Integer.parseInt(number), name, type, Integer.parseInt(price), Integer.parseInt(stock));
                    
                //store information into session
                session.setAttribute("successInfo", "Product is updated successfully");
                    
                int productNum = Integer.parseInt(number);
                int productPrice = Integer.parseInt(price);
                int productStock = Integer.parseInt(stock);
                
                session.setAttribute("product", new Product(productNum, name, type, productPrice, productStock));
                session.setAttribute("productList", productManager.getAllProducts());
                    
                //redirect user to the welcome.jsp  
                request.getRequestDispatcher("updateproduct.jsp").include(request, response);
                
            } catch (SQLException ex) {           
                Logger.getLogger(UpdateProductServlet.class.getName()).log(Level.SEVERE, null, ex);       
            }
        }
    }   
}
