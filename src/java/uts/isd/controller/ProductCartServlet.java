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
import uts.isd.model.Product;
import uts.isd.model.ProductList;
import uts.isd.model.User;
import uts.isd.model.dao.DBCartManager;
import uts.isd.model.dao.DBProductManager;

/**
 *
 * @author Administrator
 */
public class ProductCartServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //retrieve the current session
        HttpSession session = request.getSession();
        
        User user = (User)session.getAttribute("user");
        String redirectURL = "http://localhost:8080/IOTBay/unauthorised.jsp";
        if(user == null || !user.getUsertype().equals("2")) {
            response.sendRedirect(redirectURL);
        }
        
        Validator validator = new Validator();
        validator.clean(session);
        String number = request.getParameter("quantity");
        int orderNumber = 0;
        int index = Integer.parseInt(request.getParameter("add"));
        
        ProductList availableProductList = (ProductList) session.getAttribute("availableProductList");
        ProductList cartProductList = (ProductList) session.getAttribute("cartProductList");
        if(cartProductList == null) cartProductList = new ProductList();
        
        Product selectedProduct = availableProductList.getProductByIndex(index);
        DBCartManager cartManager = (DBCartManager) session.getAttribute("cartManager");
        DBProductManager productManager = (DBProductManager) session.getAttribute("productManager");
        
        if(!validator.validateProductNumber(number)) {
            //set incorrect password different error to the session
            session.setAttribute("productStockErr", "Error: Order number format incorrect");
        }else {
            orderNumber = Integer.parseInt(number);
            if(orderNumber <= 0 || orderNumber > selectedProduct.getStock()) {
                //set incorrect password different error to the session
                session.setAttribute("productStockErr", "Error: Order number should be " + "1 to " + selectedProduct.getStock());
            }else {
                //System.out.println("index: " + index);
                String username = user.getUsername();
                int productNo = selectedProduct.getProductNo();
                String name = selectedProduct.getName();
                String type = selectedProduct.getType();
                int price = selectedProduct.getPrice();
                int quantity = Integer.parseInt(number);
                int remainStock = selectedProduct.getStock() - quantity;
                               

                if(!cartProductList.isProductInList(productNo)) {
                    try {
                        cartManager.addProductInCart(username, productNo, name, type, price, quantity);
                        productManager.updateProductByNumber(productNo, remainStock);
                    } catch (SQLException ex) {
                        Logger.getLogger(ProductCartServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }else {
                    //get product quantity from cartProductList
                    int alreadyNumber = cartProductList.getQuantityByProductNo(productNo);
                    
                    try {
                        cartManager.updateProduct(username, productNo, alreadyNumber + quantity);
                        productManager.updateProductByNumber(productNo, remainStock);
                    } catch (SQLException ex) {
                        Logger.getLogger(ProductCartServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                //store information into session
                session.setAttribute("successInfo", "Product is added into cart");
                try {
                    ProductList productList = (ProductList) session.getAttribute("availableProductList");
                    productList.updateProductRemainNumber(productNo, quantity);
                    session.setAttribute("availableProductList", productList);
                    //session.setAttribute("availableProductList", productManager.getAllProducts());
                    session.setAttribute("cartProductList", cartManager.getCartProductByUsername(username));
                } catch (SQLException ex) {
                    Logger.getLogger(ProductCartServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        //redirect user to the welcom.jsp
        request.getRequestDispatcher("welcome.jsp").include(request, response);
    }
}