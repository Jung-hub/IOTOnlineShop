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
public class EditServlet extends HttpServlet {
    @Override   
    protected void doPost(HttpServletRequest request, 
            HttpServletResponse response) throws ServletException, IOException {       
        //1- retrieve the current session
        HttpSession session = request.getSession();
        //2- create an instance of the Validator class    
        
        Validator validator = new Validator();
        //3- Get user info from session attribute
        User user = (User) session.getAttribute("user");
                       
        //4- Get user update info from the form.
        String username = user.getUsername();
        String password = user.getPassword();
        String firstname = request.getParameter("fname");
        String lastname = request.getParameter("lname");
        String email = request.getParameter("email");
        String birthday = request.getParameter("birthday");
        String phone = request.getParameter("phone");
                
        //5- retrieve the manager instance from session
        DBManager manager = (DBManager) session.getAttribute("manager");
                
        //6 initialise the error message
        validator.clean(session);
        
        if(!validator.validateFirstOrLastName(firstname)) {
            session.setAttribute("firstnameErr", "Error: 1 - 20 characters(A-Z,a-z,space) for first name format");
            //redirect user back to the register.jsp
            request.getRequestDispatcher("edit.jsp").include(request, response);
        }else if(!validator.validateFirstOrLastName(lastname)) {
            session.setAttribute("lastnameErr", "Error: 1 - 20 characters(A-Z,a-z,space) for last name format");
            //redirect user back to the register.jsp
            request.getRequestDispatcher("edit.jsp").include(request, response);
        }else if(!validator.validateEmail(email)) {
            //set incorrect email error to the session
            session.setAttribute("emailErr", "Error: Email format xxx.xxx@xxx.xxx.xxx");
            //redirect user back to the register.jsp
            request.getRequestDispatcher("edit.jsp").include(request, response);
        }else if(!validator.validatePhoneNumber(phone)) {
            session.setAttribute("phoneNumberErr", "Error: 8 - 13 digital characters(0-9) for phone format");
            //redirect user back to the register.jsp
            request.getRequestDispatcher("edit.jsp").include(request, response);
        }else {
            try {
                //update user profile in database
                manager.updateUserProfile(username, password, firstname, lastname, email, birthday, phone);
                session.setAttribute("profileUpdate", "Successfully update");
                //update user profile in session attribute
                boolean isUpdateSuccessful = user.setProfile(firstname, lastname , email, birthday, phone);
                if(isUpdateSuccessful) session.setAttribute("user", user);
                request.getRequestDispatcher("edit.jsp").include(request, response);
                
            } catch (SQLException ex) {           
                Logger.getLogger(EditServlet.class.getName()).log(Level.SEVERE, null, ex);       
            }
        }
    } 
}