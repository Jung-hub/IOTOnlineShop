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
import uts.isd.model.OrderList;
import uts.isd.model.Product;
import uts.isd.model.ProductList;
import uts.isd.model.User;
import uts.isd.model.dao.DBCartManager;
import uts.isd.model.dao.DBOrderManager;
import uts.isd.model.dao.DBOrderlineManager;

/**
 *
 * @author Administrator
 */
public class SaveOrderServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //retrieve the current session
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String redirectURL = "http://localhost:8080/IOTBay/unauthorised.jsp";
        if(user == null || !user.getUsertype().equals("2")) {
            response.sendRedirect(redirectURL);
        }
        DBCartManager cartManager = (DBCartManager) session.getAttribute("cartManager");
        DBOrderManager orderManager = (DBOrderManager) session.getAttribute("orderManager");
        DBOrderlineManager orderlineManager = (DBOrderlineManager) session.getAttribute("orderlineManager");
        Validator validator = new Validator();
        validator.clean(session);
        try {
            String username = user.getUsername();
            ProductList cartProductList = cartManager.getCartProductByUsername(username);
            if(cartProductList.listSize() == 0) {
                session.setAttribute("productStockErr","Error: The order you create has no product");
                request.getRequestDispatcher("cart.jsp").include(request, response);
            }else {
                int amount = cartProductList.getAmount();
                session.setAttribute("cartProductList", new ProductList());
                cartManager.deleteProduct(username);
                int orderNumber = orderManager.addOrder(username, amount);
                OrderList orderList = orderManager.getOrdersByUsername(username);
                session.setAttribute("orderList", orderList);
                for (int i = 0; i < cartProductList.listSize(); i++) {
                    int productNo = cartProductList.getProductByIndex(i).getProductNo();
                    String productType = cartProductList.getProductByIndex(i).getType();
                    String productName = cartProductList.getProductByIndex(i).getName();
                    int price = cartProductList.getProductByIndex(i).getPrice();
                    int quantity = cartProductList.getProductByIndex(i).getStock();
                    orderlineManager.addProductOrderline(orderNumber,productNo, productType, productName, price, quantity);
                }
                request.getRequestDispatcher("welcome.jsp").include(request, response);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SaveOrderServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
