/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uts.isd.controller;

 

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import uts.isd.model.dao.*;

 

public class ConnServlet extends HttpServlet {

    private DBConnector db;
    private DBManager manager;
    private LOGManager logManager;
    private Connection conn;
    private DBProductManager productManager;
    private DBCartManager cartManager;
    private DBOrderManager orderManager;
    private DBOrderlineManager orderlineManager;
    private DBPaymentManager paymentManager;
        

    @Override //Create and instance of DBConnector for the deployment session
    public void init() {
        try {
            db = new DBConnector();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ConnServlet.class.getName()).log(Level.SEVERE, null, ex);
        }      
    }

    @Override //Add the DBConnector, DBManager, Connection instances to the session
    //The doGet() capture the current session from request and opens connection with userdb
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //The steps of what doGet() do
        //1 set page content text type
        //2 get the current session from parameter of HttpServletRequest request
        //3 create a database connection 
        //4 create an instance of DBManager
        //5 add the instance into current session
        //1 set page content text type
        response.setContentType("text/html;charset=UTF-8");
                       
        //2 declare a session variable to store request session
        HttpSession session = request.getSession();
        
        //3 create a database connection when application starts
        conn = db.getConnection();
        
        //4 create an instance of Validator to initialise the error message
        Validator validator = new Validator(); 
        validator.clean(session);
        
        try {
            //4 An instance to connect to DBManager database of users table
            manager = new DBManager(conn);
            logManager = new LOGManager(conn);
            productManager = new DBProductManager(conn);
            cartManager = new DBCartManager(conn);
            orderManager = new DBOrderManager(conn);
            orderlineManager = new DBOrderlineManager(conn);
            paymentManager = new DBPaymentManager(conn);
        } catch (SQLException ex) {
            Logger.getLogger(ConnServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        //export the DB manager to the view-session (JSPs)
        //5 add the instance into current session
        session.setAttribute("manager", manager);
        session.setAttribute("logManager", logManager);
        session.setAttribute("productManager", productManager);
        session.setAttribute("cartManager", cartManager);
        session.setAttribute("orderManager", orderManager);
        session.setAttribute("orderlineManager", orderlineManager);
        session.setAttribute("paymentManager", paymentManager);
    }   

    @Override //Destroy the servlet and release the resources of the application (terminate also the db connection)
    public void destroy() {
        try {
            db.closeConnection();
        } catch (SQLException ex) {
            Logger.getLogger(ConnServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
