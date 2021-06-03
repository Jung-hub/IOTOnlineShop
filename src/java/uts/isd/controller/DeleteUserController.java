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
import uts.isd.model.Order;
import uts.isd.model.OrderList;
import uts.isd.model.Product;
import uts.isd.model.ProductList;
import uts.isd.model.User;
import uts.isd.model.dao.DBCartManager;
import uts.isd.model.dao.DBManager;
import uts.isd.model.dao.DBOrderManager;
import uts.isd.model.dao.DBOrderlineManager;
import uts.isd.model.dao.DBProductManager;

/**
 *
 * @author Administrator
 */
public class DeleteUserController extends HttpServlet{
    @Override   
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String redirectURL = "http://localhost:8080/IOTBay/unauthorised.jsp";
        if(user == null || !user.getUsertype().equals("2")) {
            response.sendRedirect(redirectURL);
        }
        DBManager manager = (DBManager) session.getAttribute("manager");
        DBOrderManager orderManager = (DBOrderManager) session.getAttribute("orderManager");
        DBOrderlineManager orderlineManager = (DBOrderlineManager) session.getAttribute("orderlineManager");
        DBProductManager productManager = (DBProductManager) session.getAttribute("productManager");
        DBCartManager cartManager = (DBCartManager) session.getAttribute("cartManager");
        OrderList unPaidOrderList = new OrderList();
        ProductList allReturnProducts = new ProductList();
        //Get all available products in database
        ProductList availableProductList = new ProductList();
        
        
        try {
            availableProductList = productManager.getAllProducts();
        } catch (SQLException ex) {
            Logger.getLogger(DeleteUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
                
        try {
            unPaidOrderList = orderManager.getOrdersByUsernameAndStatus(user.getUsername(), 0);
            
            
            for(int i = 0; i < unPaidOrderList.listSize(); i++) {
                //Get each order
                Order getOrder = unPaidOrderList.getOrderByIndex(i);
                int orderID = getOrder.getOrderID();
                //1 update unpaid order to cancelled order
                orderManager.updateOrderStatus(orderID, -1);
                //2 get return
                ProductList returnProducts = orderlineManager.getProductsByOrderID(orderID, 0);
                for(int j = 0; j < returnProducts.listSize(); j++) {
                    allReturnProducts.addProduct(returnProducts.getProductByIndex(j));
                }
                    //3 update orderline return product status to -1
                orderlineManager.updateAllProductStatus(orderID, -1);
            }
                
            for(int i = 0; i < availableProductList.listSize(); i++) {
                Product eachStockProduct = availableProductList.getProductByIndex(i);
                for(int j = 0; j < allReturnProducts.listSize(); j++) {
                    Product eachReturnProduct = allReturnProducts.getProductByIndex(j);
                    if(eachStockProduct.getProductNo() == eachReturnProduct.getProductNo()) {
                        eachStockProduct.updateStockByReturnNumber(eachReturnProduct.getStock());
                    }

                }
            }
            
            
            //Get cart products in database
            ProductList cartProductList = new ProductList();
            cartProductList = cartManager.getCartProductByUsername(user.getUsername());
            
            if(cartProductList.listSize() != 0) {
                for(int i = 0; i < availableProductList.listSize(); i++) {
                    Product eachProduct = availableProductList.getProductByIndex(i);
                    for(int j = 0; j < cartProductList.listSize(); j++) {
                        Product returnProduct = cartProductList.getProductByIndex(j);
                        if(eachProduct.getProductNo() == returnProduct.getProductNo()) {
                            eachProduct.updateStockByReturnNumber(returnProduct.getStock());
                        }
                    }
                }
            }
            
            cartManager.deleteProduct(user.getUsername());
            
                        
            //update each product stock in the database
            for(int i = 0; i < availableProductList.listSize(); i++) {
                int productNo = availableProductList.getProductByIndex(i).getProductNo();
                int stock = availableProductList.getProductByIndex(i).getStock();
                productManager.updateProductByNumber(productNo, stock);
            }
            //delete the account
            manager.deleteUser(user.getUsername());
            
            //stop the session
            session.invalidate();
            
            request.getRequestDispatcher("delete.jsp").include(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(DeleteUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
        
    }
    
}
