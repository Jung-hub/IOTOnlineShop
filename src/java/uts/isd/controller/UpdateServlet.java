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
public class UpdateServlet extends HttpServlet{
    @Override   
    protected void doPost(HttpServletRequest request, 
            HttpServletResponse response) throws ServletException, IOException {
        //retrieve the current session
        HttpSession session = request.getSession();
        //Get user from session
        User user = (User) session.getAttribute("user");
        
        String redirectURL = "http://localhost:8080/IOTBay/unauthorised.jsp";
        if(user == null || !user.getUsertype().equals("0")) {
            response.sendRedirect(redirectURL);
        }
        
        //create an instance of the Validator class    
        Validator validator = new Validator();
        validator.clean(session);
        
        //4- Get user update info from the form.
        String username = request.getParameter("keyuser");
        String password = request.getParameter("keypassword");
        String firstname = request.getParameter("fname");
        String lastname = request.getParameter("lname");
        String email = request.getParameter("email");
        String birthday = request.getParameter("birthday");
        String phone = request.getParameter("phone");
        
        //5- retrieve the manager instance from session
        DBManager manager = (DBManager) session.getAttribute("manager");
        
        if(!validator.validateFirstOrLastName(firstname)) {
            session.setAttribute("firstnameErr", "Error: 1 - 20 characters(A-Z,a-z,space) for first name format");
            //redirect user back to the register.jsp
            request.getRequestDispatcher("update.jsp").include(request, response);
        }else if(!validator.validateFirstOrLastName(lastname)) {
            session.setAttribute("lastnameErr", "Error: 1 - 20 characters(A-Z,a-z,space) for last name format");
            //redirect user back to the register.jsp
            request.getRequestDispatcher("update.jsp").include(request, response);
        }else if(!validator.validateEmail(email)) {
            //set incorrect email error to the session
            session.setAttribute("emailErr", "Error: Email format xxx.xxx@xxx.xxx.xxx");
            //redirect user back to the register.jsp
            request.getRequestDispatcher("update.jsp").include(request, response);
        }else if(!validator.validatePhoneNumber(phone)) {
            session.setAttribute("phoneNumberErr", "Error: 8 - 13 digital characters(0-9) for phone format");
            //redirect user back to the register.jsp
            request.getRequestDispatcher("update.jsp").include(request, response);
        }else {
            try {
                manager.updateUserProfile(username, password, firstname, lastname, email, birthday, phone);
                User specificUser = manager.findUserByUserName(username);
                session.setAttribute("specificUser", specificUser);
                session.setAttribute("accountList", manager.getAllUsers());
                session.setAttribute("profileUpdate", "Successfully update");
                request.getRequestDispatcher("update.jsp").include(request, response);
            } catch (SQLException ex) {           
                Logger.getLogger(UpdateServlet.class.getName()).log(Level.SEVERE, null, ex);       
            }
        }
    }
}    
