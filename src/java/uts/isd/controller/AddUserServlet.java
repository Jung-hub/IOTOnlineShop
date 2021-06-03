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
import uts.isd.model.dao.DBManager;

/**
 *
 * @author Administrator
 */
public class AddUserServlet extends HttpServlet{
    @Override   
    protected void doPost(HttpServletRequest request, 
        HttpServletResponse response) throws ServletException, IOException {
        //1- retrieve the current session
        HttpSession session = request.getSession();
        
        //2- create an instance of the Validator class    
        Validator validator = new Validator();
        
        //3- Get user update password from the form.
        String username = request.getParameter("uname");
        String uPassword = request.getParameter("upassword");
        String cPassword = request.getParameter("cupassword");
        String usertype = request.getParameter("utype");       
        
        //4 retrieve the manager instance from session
        DBManager manager = (DBManager) session.getAttribute("manager");
                
        //5 initialise the error message
        validator.clean(session);
        
        User user = null;
        
        if (!validator.validateUsername(username)) {           
            //set incorrect email error to the session
            session.setAttribute("usernameErr", "Error: 4 to 15 character(A-Z,a-z,0-9) for username format");
            //redirect user back to the register.jsp
            request.getRequestDispatcher("adduser.jsp").include(request, response);
        } else if (!validator.validatePassword(uPassword) || !validator.validatePassword(cPassword)) {                  
            //set incorrect password error to the session
            session.setAttribute("passErr", "Error: 4 to 15 character(A-Z,a-z,0-9) for password format");
            //redirect user back to the register.jsp
            request.getRequestDispatcher("adduser.jsp").include(request, response);
        } else if(!validator.isValidatePassword(uPassword, cPassword)) {
            //set incorrect password different error to the session
            session.setAttribute("passDiffErr", "Error: Password and confirm password different");
            //redirect user back to the register.jsp
            request.getRequestDispatcher("adduser.jsp").include(request, response);
        }else {
            try {
                //find user by email and password
                user = manager.findUserByUserName(username);
                if(user != null) {
                    //set information of that the user already exists
                    session.setAttribute("existErr", "Error: Username already exists");
                    //redirect user to the welcom.jsp
                    request.getRequestDispatcher("adduser.jsp").include(request, response);
                }else {
                    
                    //store user into databse
                    manager.addUser(username, uPassword, usertype, "", "1");
                    
                    //store information into session
                    session.setAttribute("profileUpdate", "Account is created successfully");
                    
                    //update account list into session
                    session.setAttribute("accountList", manager.getAllUsersWithoutRoot());
                    
                    //redirect user to the welcome.jsp  
                    request.getRequestDispatcher("adduser.jsp").include(request, response);
                }
            } catch (SQLException ex) {           
                Logger.getLogger(AddUserServlet.class.getName()).log(Level.SEVERE, null, ex);       
            }
        }
    }   
}
