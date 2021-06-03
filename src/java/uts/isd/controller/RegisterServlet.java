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
import uts.isd.model.ProductList;
import uts.isd.model.User;
import uts.isd.model.dao.DBCartManager;
import uts.isd.model.dao.DBManager;
import uts.isd.model.dao.DBPaymentManager;
import uts.isd.model.dao.DBProductManager;
import uts.isd.model.dao.LOGManager;

 
/*
The loginServlet controler captures the posted data from the form of login jsp
and validate the user logining-credentials by using validate class in uts.isd.controller
that contains validation patterns
*/ 
public class RegisterServlet extends HttpServlet {
    @Override   
    protected void doPost(HttpServletRequest request, 
            HttpServletResponse response) throws ServletException, IOException {       
        //1- retrieve the current session
        HttpSession session = request.getSession();
        //2- create an instance of the Validator class    
        Validator validator = new Validator();
        //3- capture the posted username and mail
        String username = request.getParameter("uname");
        String email = request.getParameter("email");
        //4- capture the posted password
        String password = request.getParameter("upassword"); 
        String cPassword = request.getParameter("cupassword");
        //5- retrieve the manager instance from session
        DBManager manager = (DBManager) session.getAttribute("manager");
        DBProductManager productManager = (DBProductManager) session.getAttribute("productManager");
        DBCartManager cartManager = (DBCartManager) session.getAttribute("cartManager");
        LOGManager logManager = (LOGManager) session.getAttribute("logManager");
        DBPaymentManager paymentManager = (DBPaymentManager) session.getAttribute("paymentManager");
        //6- Declare a user with value of null
        User user = null;
        
        //7 initialise the error message
        validator.clean(session);
       
        if (!validator.validateUsername(username)) {           
            //set incorrect email error to the session
            session.setAttribute("usernameErr", "Error: 4 to 15 character(A-Z,a-z,0-9) for username format");
            //redirect user back to the register.jsp
            request.getRequestDispatcher("register.jsp").include(request, response);
        } else if (!validator.validatePassword(password) || !validator.validatePassword(cPassword)) {                  
            //set incorrect password error to the session
            session.setAttribute("passErr", "Error: 4 to 15 character(A-Z,a-z,0-9) for password format");
            //redirect user back to the register.jsp
            request.getRequestDispatcher("register.jsp").include(request, response);
        } else if(!validator.isValidatePassword(password, cPassword)) {
            //set incorrect password different error to the session
            session.setAttribute("passDiffErr", "Error: Password and confirm password different");
            //redirect user back to the register.jsp
            request.getRequestDispatcher("register.jsp").include(request, response);
        }else if(!validator.validateEmail(email)) {
            //set incorrect email error to the session
            session.setAttribute("emailErr", "Error: Email format xxx.xxx@xxx.xxx.xxx");
            //redirect user back to the register.jsp
            request.getRequestDispatcher("register.jsp").include(request, response);
        }else {
            try {
                //find user by email and password
                user = manager.findUser(username, password);
                if(user != null) {
                    //set information of that the user already exists
                    session.setAttribute("existErr", "Error: Username already exists");
                    //redirect user to the welcom.jsp
                    request.getRequestDispatcher("register.jsp").include(request, response);
                }else {
                    //store user into databse
                    manager.addUser(username, password, "2", email, "1");
                    //store log into database
                    logManager.addLog(username,"login");
                    //save the logged in user object to the session 
                    session.setAttribute("user", new User(username, password, email));
                    session.setAttribute("availableProductList", productManager.getAllProducts());
                    session.setAttribute("cartProductList", new ProductList());
                    session.setAttribute("orderList", new OrderList());
                    session.setAttribute("paymentList", paymentManager.findPaymentByUsername(username));
                    //redirect user to the welcome.jsp  
                    request.getRequestDispatcher("welcome.jsp").include(request, response);
                }
            } catch (SQLException ex) {           
                Logger.getLogger(RegisterServlet.class.getName()).log(Level.SEVERE, null, ex);       
            }
        }
    } 
}