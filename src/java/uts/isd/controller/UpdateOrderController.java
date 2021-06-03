/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uts.isd.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import uts.isd.model.Order;
import uts.isd.model.Product;
import uts.isd.model.ProductList;
import uts.isd.model.User;
import uts.isd.model.dao.DBOrderManager;
import uts.isd.model.dao.DBOrderlineManager;
import uts.isd.model.dao.DBPaymentManager;
import uts.isd.model.dao.DBProductManager;

/**
 *
 * @author Administrator
 */
public class UpdateOrderController extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        int orderID;
        String update = request.getParameter("update");
        String delete = request.getParameter("delete");
        String payment = request.getParameter("pay");
        boolean isUpdateButtonClicked = update != null;
        boolean isDeleteButtonClicked = delete != null;
        //boolean isPaymentButtonClicked = payment != null? true: false;
        //Get orderline manager from the session
        DBOrderlineManager orderlineManager = (DBOrderlineManager) session.getAttribute("orderlineManager");
        DBProductManager productManager = (DBProductManager) session.getAttribute("productManager");
        DBOrderManager orderManager = (DBOrderManager) session.getAttribute("orderManager");
        DBPaymentManager paymentManager = (DBPaymentManager) session.getAttribute("paymentManager");
        ProductList orderProductList = new ProductList();
        if(isUpdateButtonClicked) {
            //Get orderID
            orderID = Integer.parseInt(update);
            
            //Get products from the orderline database
            try {
                Order order = orderManager.getOrderByOrderID(orderID);
                orderProductList = orderlineManager.getProductsByOrderID(orderID, order.getStatus());
                session.setAttribute("orderProductList", orderProductList);
                session.setAttribute("orderID", update);
            } catch (SQLException ex) {
                Logger.getLogger(UpdateOrderController.class.getName()).log(Level.SEVERE, null, ex);
            }
            //redirect user to the page of orderline.jsp
            request.getRequestDispatcher("orderline.jsp").include(request, response);
        }else if(isDeleteButtonClicked) {
            orderID = Integer.parseInt(delete);
            
            try {
                orderManager.updateOrderStatus(orderID, -1);
                orderlineManager.updateAllProductStatus(orderID, -1);
                orderProductList = orderlineManager.getProductsByOrderID(orderID, -1);
                int productID;
                for(int i = 0; i < orderProductList.listSize(); i++) {
                    Product orderProduct = orderProductList.getProductByIndex(i);
                    productID = orderProduct.getProductNo();
                    Product stockProduct = productManager.getProductByNumber(productID);
                    int returnStock = orderProduct.getStock();
                    int stock = stockProduct.getStock();
                    productManager.updateProductByNumber(productID, returnStock + stock);
                }
                session.setAttribute("availableProductList", productManager.getAllProducts());
                session.setAttribute("orderList", orderManager.getOrdersByUsername(user.getUsername()));
            } catch (SQLException ex) {
                Logger.getLogger(UpdateOrderController.class.getName()).log(Level.SEVERE, null, ex);
            }
            request.getRequestDispatcher("order.jsp").include(request, response);
        }else {
            try {
                orderID = Integer.parseInt(payment);
                Order order = orderManager.getOrderByOrderID(orderID);
                session.setAttribute("order", order);
                session.setAttribute("paymentList", paymentManager.getPaymentByUsername(user.getUsername()));
                request.getRequestDispatcher("submitorder.jsp").include(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(UpdateOrderController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
        }
        
        
        
        
        
        
        
    }
    
}
