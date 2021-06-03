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
import uts.isd.model.User;
import uts.isd.model.dao.DBOrderManager;

/**
 *
 * @author Administrator
 */
public class SearchOrderServlet extends HttpServlet {
    @Override   
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //retrieve the current session
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String usertype = user.getUsertype();
        
        //Get data from the form
        String month = request.getParameter("month");
        String day = request.getParameter("days");
        String order = request.getParameter("orderID");
        
        boolean isMonthNull = month.equals("") ;
        boolean isDayNull = day.equals("");
        boolean isOrderNull = order.equals("");
               
        /*orderID*/
        
        String redirectURL = "http://localhost:8080/IOTBay/unauthorised.jsp";
        
        OrderList orderList = new OrderList();
        DBOrderManager orderManager = (DBOrderManager) session.getAttribute("orderManager");
        
        Validator validator = new Validator();
        validator.clean(session);
        
        if(!user.getUsertype().equals("2") && !user.getUsertype().equals("1")) {
            response.sendRedirect(redirectURL);
        }else if(!isOrderNull && !validator.validateProductNumber(order)) {
            session.setAttribute("dateFormErr", "Error: Character numbers(0-9) for order number format ");
            if(usertype.equals("2")) {
                request.getRequestDispatcher("order.jsp").include(request, response);
            }else {
                request.getRequestDispatcher("allorders.jsp").include(request, response);
            }
        }else if((!isMonthNull && !isDayNull) && !validator.validateDate(month, day)) {
            session.setAttribute("dateFormErr", "Error: Character numbers(0-9) for date format ");
            if(usertype.equals("2")) {
                request.getRequestDispatcher("order.jsp").include(request, response);
            }else {
                request.getRequestDispatcher("allorders.jsp").include(request, response);
            }
        }else {
            if(!isOrderNull) {
                try {
                    if(usertype.equals("2")) {
                        orderList = orderManager.getOrdersByUsername(user.getUsername()).getListByOrderID(Integer.parseInt(order));
                        session.setAttribute("orderList", orderList);
                        request.getRequestDispatcher("order.jsp").include(request, response);
                    }else {
                        orderList = orderManager.getAllOrders().getListByOrderID(Integer.parseInt(order));
                        session.setAttribute("orderList", orderList);
                        request.getRequestDispatcher("allorders.jsp").include(request, response);
                    }
                    
                } catch (SQLException ex) {
                    Logger.getLogger(SearchOrderServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            
            }else if(!isMonthNull && !isDayNull){
                try {
                    if(usertype.equals("2")) {
                        orderList = orderManager.getOrdersByUsername(user.getUsername()).getListByDate(month, day);
                        session.setAttribute("orderList", orderList);
                        request.getRequestDispatcher("order.jsp").include(request, response);
                    }else if(usertype.equals("1")) {
                        orderList = orderManager.getAllOrders().getListByDate(month, day);
                        session.setAttribute("orderList", orderList);
                        request.getRequestDispatcher("allorders.jsp").include(request, response);
                    }
                    else {
                        response.sendRedirect(redirectURL);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(SearchOrderServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else {
                try {
                    if(usertype.equals("2")) {
                        orderList = orderManager.getOrdersByUsername(user.getUsername());
                        session.setAttribute("orderList", orderList);
                        request.getRequestDispatcher("order.jsp").include(request, response);
                    }else if(usertype.equals("1")) {
                        orderList = orderManager.getAllOrders();
                        session.setAttribute("orderList", orderList);
                        request.getRequestDispatcher("allorders.jsp").include(request, response);
                    }
                    else {
                        response.sendRedirect(redirectURL);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(SearchOrderServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            
            }
            
        }
        
        
    }       
    
}
