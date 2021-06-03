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
public class ChangePasswordServlet extends HttpServlet {
    @Override   
    protected void doPost(HttpServletRequest request, 
            HttpServletResponse response) throws ServletException, IOException {       
        //1- retrieve the current session
        HttpSession session = request.getSession();
        
        //2- create an instance of the Validator class    
        Validator validator = new Validator();
        
        //3- Get user info from session attribute
        User user = (User) session.getAttribute("user");
        
        //4- Get user update password from the form.
        String username = user.getUsername();
        String password = user.getPassword();
        String pPassword = request.getParameter("ppassword");
        String nPassword = request.getParameter("npassword"); 
        String cPassword = request.getParameter("cpassword");
        
        //5- retrieve the manager instance from session
        DBManager manager = (DBManager) session.getAttribute("manager");
                
        //7 initialise the error message
        validator.clean(session);
       
        if (!validator.validatePassword(pPassword) ||
                !validator.validatePassword(nPassword) ||
                !validator.validatePassword(cPassword)) {                  
            //set incorrect password error to the session
            session.setAttribute("passErr", "Error: 4 to 15 character(A-Z,a-z,0-9) for password format");
            //redirect user back to the register.jsp
            request.getRequestDispatcher("changepassword.jsp").include(request, response);
        } else if(!validator.isValidatePassword(nPassword, cPassword)) {
            //set incorrect password different error to the session
            session.setAttribute("passDiffErr", "Error: Password and confirm password different");
            //redirect user back to the register.jsp
            request.getRequestDispatcher("changepassword.jsp").include(request, response);
        }else if(!validator.isValidatePassword(password, pPassword)) {
            //set incorrect password different error to the session
            session.setAttribute("passDiffErr", "Error: Current password is incorrect");
            //redirect user back to the register.jsp
            request.getRequestDispatcher("changepassword.jsp").include(request, response);
        }else {
            try {
                //update user profile in database
                manager.updateUserPassword(username, nPassword);
                session.setAttribute("profileUpdate", "Successfully update");
                //update user profile in session attribute
                boolean isPasswordUpdated = user.setPassword(nPassword);
                if(isPasswordUpdated) session.setAttribute("user", user);
                request.getRequestDispatcher("changepassword.jsp").include(request, response);
                
            } catch (SQLException ex) {           
                Logger.getLogger(ChangePasswordServlet.class.getName()).log(Level.SEVERE, null, ex);       
            }
        }
    } 
}
