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
import uts.isd.model.dao.DBCartManager;
import uts.isd.model.dao.DBProductManager;

/**
 *
 * @author Administrator
 */
public class SearchCartProductServlet extends HttpServlet{
    @Override   
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //retrieve the current session
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String username = user.getUsername();
        String keyword;
        keyword = request.getParameter("keyword");
        DBCartManager cartManager = (DBCartManager) session.getAttribute("cartManager");
        Validator validator = new Validator();
        validator.clean(session);
        
        try {
            session.setAttribute("cartProductList", cartManager.getProductByKeyword(username, keyword));
            request.getRequestDispatcher("cart.jsp").include(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(SearchCartProductServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
