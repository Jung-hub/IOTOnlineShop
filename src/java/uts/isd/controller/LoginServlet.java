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
import uts.isd.model.dao.DBCartManager;
import uts.isd.model.dao.DBManager;
import uts.isd.model.dao.DBOrderManager;
import uts.isd.model.dao.DBPaymentManager;
import uts.isd.model.dao.DBProductManager;
import uts.isd.model.dao.LOGManager;
 
/*
The loginServlet controler captures the posted data from the form of login jsp
and validate the user logining-credentials by using validate class in uts.isd.controller
that contains validation patterns
*/ 
public class LoginServlet extends HttpServlet {
    @Override   
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {       
        //retrieve the current session
        HttpSession session = request.getSession();
        //create an instance of the Validator class    
        Validator validator = new Validator();
        //capture the posted email
        String username = request.getParameter("uname");
        //capture the posted password
        String password = request.getParameter("upassword");    
        //retrieve the manager instance from session
        DBManager manager = (DBManager) session.getAttribute("manager");
        DBProductManager productManager = (DBProductManager) session.getAttribute("productManager");
        DBCartManager cartManager = (DBCartManager) session.getAttribute("cartManager");
        LOGManager logManager = (LOGManager) session.getAttribute("logManager");
        DBPaymentManager paymentManager = (DBPaymentManager) session.getAttribute("paymentManager");
        //initialise the error message
        validator.clean(session);
        
        if (!validator.validateUsername(username)) {           
            //set incorrect email error to the session
            session.setAttribute("usernameErr", "Error: 4 to 15 character(A-Z,a-z,0-9) for username format");
            //redirect user back to the login.jsp
            request.getRequestDispatcher("login.jsp").include(request, response);
        } else if (!validator.validatePassword(password)) {                  
            //set incorrect password error to the session
            session.setAttribute("passErr", "Error: 4 to 15 character(A-Z,a-z,0-9) for password format");

            //redirect user back to the login.jsp
            request.getRequestDispatcher("login.jsp").include(request, response);
        } else {
            try {
                //find user by email and password
                User user = manager.findUser(username, password);
                if(user != null) {
                    if(user.getStatus().equals("1")) {
                        //save the logged in user object to the session 
                        session.setAttribute("user", user);
                        session.setAttribute("availableProductList", productManager.getAllProducts());
                        session.setAttribute("cartProductList", cartManager.getCartProductByUsername(username));
                        session.setAttribute("paymentList", paymentManager.findPaymentByUsername(username));
                        DBOrderManager orderManager = (DBOrderManager) session.getAttribute("orderManager");
                        OrderList orderList = null;
                        if(user.getUsertype().equals("2")) {
                            orderList = orderManager.getOrdersByUsername(username);
                        }else if(user.getUsertype().equals("1")) {
                            orderList = orderManager.getAllOrders();
                        }
                           
                        if(orderList == null) session.setAttribute("orderList", new OrderList());
                        session.setAttribute("orderList", orderList);
                        //store log into database
                        logManager.addLog(username,"login");
                    
                        //redirect user to the welcom.jsp
                        request.getRequestDispatcher("welcome.jsp").include(request, response);
                    }else {
                        //set user does not exist error to the session 
                        session.setAttribute("userLock", "Account is locked");
                        
                        //redirect user back to the login.jsp  
                        request.getRequestDispatcher("login.jsp").include(request, response);
                    }
                    
                }else {
                    //set user does not exist error to the session 
                    session.setAttribute("existErr", "Username or password is incorrect");
                    
                    //redirect user back to the login.jsp  
                    request.getRequestDispatcher("login.jsp").include(request, response);
                }
            } catch (SQLException ex) {           
                Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);       
            }
        }
    } 
}