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
import uts.isd.model.dao.DBCartManager;
import uts.isd.model.dao.DBOrderManager;
import uts.isd.model.dao.DBOrderlineManager;
import uts.isd.model.dao.DBProductManager;

/**
 *
 * @author Administrator
 */
public class SearchOrderProductServlet extends HttpServlet{
    @Override   
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //retrieve the current session
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String username = user.getUsername();
        String keyword;
        keyword = request.getParameter("keyword");
        int orderID = Integer.parseInt(request.getParameter("filter"));
        DBOrderManager orderManager = (DBOrderManager) session.getAttribute("orderManager");
        DBOrderlineManager orderlineManager = (DBOrderlineManager) session.getAttribute("orderlineManager");
        Validator validator = new Validator();
        validator.clean(session);
        //orderlineManager.getProductByKeyword(0, keyword)
        try {
            //ProductList orderProductList = orderlineManager.getProductByKeyword(orderID, keyword);
            Order order = orderManager.getOrderByOrderID(orderID);
            session.setAttribute("orderProductList", orderlineManager.getProductByKeyword(orderID, order.getStatus(), keyword));
            request.getRequestDispatcher("orderline.jsp").include(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(SearchCartProductServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
