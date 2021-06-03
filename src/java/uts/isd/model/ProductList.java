/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uts.isd.model;

import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Administrator
 */
public class ProductList {
    ArrayList<Product> productList;
    
    public ProductList() {
        productList = new ArrayList<Product>();
    }
    
    public int listSize() {
        return productList.size();
    }
    
    public void addProduct(Product product) {
        productList.add(product);
    }
        
    public void setRemainStock(int productNo, int number) {
        for(Product each: productList) {
            if(each.getProductNo() == productNo) {
                each.setSoldNumber(number);
                return;
            }
        } 
    
    }
    
    public void updateProductRemainNumber(int productNo, int orderNumber) {
        for(Product each: this.productList) {
            if(each.getProductNo() == productNo) {
                each.updateRemainStock(orderNumber);
            }
        }
        
    }
    
    
    public boolean isProductInList(int productNo) {
        for(int i = 0; i < productList.size(); i++) {
            if(getProductByIndex(i).getProductNo() == productNo) {
                return true;
            }
        }
        return false;
    }
    
    public int getQuantityByProductNo(int productNo) {
        for(int i = 0; i < productList.size(); i++) {
            if(getProductByIndex(i).getProductNo() == productNo) {
                return getProductByIndex(i).getStock();
            }
        }
        return 0;
    
    }
        
    public Product getProductByIndex(int index) {
        return productList.get(index);
    }
    
    public Product getProductByProductNo(int productNo) {
        for(int i = 0; i < productList.size(); i++) {
            if(getProductByIndex(i).getProductNo() == productNo) {
                return getProductByIndex(i);
            }
        }
        return null;
    }
    
    public int getAmount() {
        int amount = 0;
        for(Product each: productList)
            amount = amount + each.getPrice() * each.getStock();
        return amount;
    }
    
    
    public void displayProducts() {
        if(listSize() == 0) {
            System.out.println("No records.");
            return;
        }
        for(int i = 0; i < listSize(); i++) {
            System.out.println(getProductByIndex(i));
        }
    }
}
