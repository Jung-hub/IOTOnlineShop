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
import uts.isd.model.dao.DBCartManager;
import uts.isd.model.dao.DBProductManager;

/**
 *
 * @author Administrator
 */
public class UpdateCartProductServlet extends HttpServlet{
     protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //retrieve the current session
        HttpSession session = request.getSession();
        
        User user = (User)session.getAttribute("user");
        String redirectURL = "http://localhost:8080/IOTBay/unauthorised.jsp";
        if(user == null || !user.getUsertype().equals("2")) {
            response.sendRedirect(redirectURL);
        }
        String username = user.getUsername();
        Validator validator = new Validator();
        validator.clean(session);
        String quantity = request.getParameter("quantity");
        String validNumber = request.getParameter("validnumber");
        int maxOrderNumber = Integer.parseInt(validNumber);
        
        String update = request.getParameter("update");
        boolean isUpdateButtonClicked = update != null;
        String delete = request.getParameter("delete");
        boolean isDeleteButtonClicked = delete != null;
        int orderNumber;
        int productNo;
        DBCartManager cartManager = (DBCartManager) session.getAttribute("cartManager");
        DBProductManager productManager = (DBProductManager) session.getAttribute("productManager");
        
        if(isUpdateButtonClicked) {
            if(!validator.validateProductNumber(quantity)) {
                session.setAttribute("productStockErr", "Error: Order number format incorrect");
            }else {
                orderNumber = Integer.parseInt(quantity);
                if(orderNumber <= 0 || orderNumber > maxOrderNumber) {
                    //set incorrect password different error to the session
                    session.setAttribute("productStockErr", "Error: Order number should be " + "1 to " + maxOrderNumber);
                }else {
                    productNo = Integer.parseInt(update);
                    int remainNumber = maxOrderNumber - orderNumber;
                    
                    try {
                        cartManager.updateProduct(username, productNo, orderNumber);
                        productManager.updateProductByNumber(productNo, remainNumber);
                        //store information into session
                        session.setAttribute("successInfo", "Product order number is updated");
                        
                    } catch (SQLException ex) {
                        Logger.getLogger(UpdateCartProductServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }else if(isDeleteButtonClicked) {
            productNo = Integer.parseInt(delete);
            try {
                productManager.updateProductByNumber(productNo, maxOrderNumber);
                cartManager.deleteProduct(username, productNo);
                //store information into session
                session.setAttribute("successInfo", "Product is deleted");
            } catch (SQLException ex) {
                Logger.getLogger(UpdateCartProductServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
         try {
             session.setAttribute("availableProductList", productManager.getAllProducts());
             session.setAttribute("cartProductList", cartManager.getCartProductByUsername(username));
         } catch (SQLException ex) {
             Logger.getLogger(UpdateCartProductServlet.class.getName()).log(Level.SEVERE, null, ex);
         }
         request.getRequestDispatcher("cart.jsp").include(request, response);   
    }
}
