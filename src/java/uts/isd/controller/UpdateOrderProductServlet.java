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
import uts.isd.model.Order;
import uts.isd.model.ProductList;
import uts.isd.model.User;
import uts.isd.model.dao.DBOrderManager;
import uts.isd.model.dao.DBOrderlineManager;
import uts.isd.model.dao.DBProductManager;

/**
 *
 * @author Administrator
 */
public class UpdateOrderProductServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //retrieve the current session
        HttpSession session = request.getSession();
        
        User user = (User)session.getAttribute("user");
        String redirectURL = "http://localhost:8080/IOTBay/unauthorised.jsp";
        if(user == null || !user.getUsertype().equals("2")) {
            response.sendRedirect(redirectURL);
        }
        Validator validator = new Validator();
        validator.clean(session);
        String quantity = request.getParameter("quantity");
        String validNumber = request.getParameter("validnumber");
        String orderID = request.getParameter("orderID");
        
        int maxOrderNumber = Integer.parseInt(validNumber);
        String update = request.getParameter("update");
        boolean isUpdateButtonClicked = update != null;
        String delete = request.getParameter("delete");
        boolean isDeleteButtonClicked = delete != null;
        int convertedOrderID = Integer.parseInt(orderID);
        int orderNumber;
        int productNo;
        DBOrderlineManager orderlineManager = (DBOrderlineManager) session.getAttribute("orderlineManager");
        DBOrderManager orderManager = (DBOrderManager) session.getAttribute("orderManager");
        DBProductManager productManager = (DBProductManager) session.getAttribute("productManager");
        
        if(isUpdateButtonClicked) {
            if(!validator.validateProductNumber(quantity)) {
                session.setAttribute("productStockErr", "Error: Order number format incorrect");
            }else {
                orderNumber = Integer.parseInt(quantity);
                if(orderNumber <= 0 || orderNumber > maxOrderNumber) {
                    session.setAttribute("productStockErr", "Error: Order number out of stock");
                }else {
                    productNo = Integer.parseInt(update);
                    int remainNumber = maxOrderNumber - orderNumber;
                    try {
                        productManager.updateProductByNumber(productNo, remainNumber);
                        Order order = orderManager.getOrderByOrderID(convertedOrderID);
                        orderlineManager.updateOrderlineProduct(convertedOrderID, productNo, orderNumber);
                        ProductList orderList = orderlineManager.getProductsByOrderID(convertedOrderID, order.getStatus());
                        orderManager.updateOrderAmount(convertedOrderID, orderList.getAmount());
                        session.setAttribute("availableProductList", productManager.getAllProducts());
                        session.setAttribute("orderProductList", orderList);
                        session.setAttribute("orderList", orderManager.getOrdersByUsername(user.getUsername()));
                        session.setAttribute("successInfo", "Product is updated");
                    } catch (SQLException ex) {
                        Logger.getLogger(UpdateOrderProductServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            
            }
        }else if(isDeleteButtonClicked){
            productNo = Integer.parseInt(delete);
            try {
                productManager.updateProductByNumber(productNo, maxOrderNumber);
                orderlineManager.updateProductStatus(convertedOrderID, productNo);
                Order order = orderManager.getOrderByOrderID(convertedOrderID);
                ProductList orderList = orderlineManager.getProductsByOrderID(convertedOrderID, order.getStatus());
                if(orderList.listSize() == 0) {
                    orderManager.updateOrderStatus(convertedOrderID, -1);
                    ProductList cancelledProductList = orderlineManager.getProductsByOrderID(convertedOrderID, -1);
                    orderManager.updateOrderAmount(convertedOrderID, cancelledProductList.getAmount());
                }else {
                    orderManager.updateOrderAmount(convertedOrderID, orderList.getAmount());
                }
                session.setAttribute("availableProductList", productManager.getAllProducts());
                session.setAttribute("orderProductList", orderList);
                session.setAttribute("successInfo", "Product is deleted");
                session.setAttribute("orderList", orderManager.getOrdersByUsername(user.getUsername()));
            } catch (SQLException ex) {
                Logger.getLogger(UpdateOrderProductServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        request.getRequestDispatcher("orderline.jsp").include(request, response); 
    }
}
