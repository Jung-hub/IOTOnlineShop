/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uts.isd.model;

/**
 *
 * @author Administrator
 */
public class Product {
    private int productNo;
    private String name;
    private String type;
    private int price;
    private int stock;
    
    public Product() {
        productNo = -1;
        name = "";
        type = "";
        price = -1;
        stock = 0;
    }
    
    public Product(int number, String name, String type, int price, int stock) {
        this.productNo = number;
        this.name = name;
        this.type = type;
        this.price = price;
        this.stock = stock;
    }
    public int getProductNo() {
        return productNo;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public void setProductNo(int productNo) {
        this.productNo = productNo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
    
    public void setSoldNumber(int number) {
        this.stock = this.stock - number;
    }
    
    public int updateOrderNumber(int number) {
        int addition = number - this.stock;
        setSoldNumber(addition);
        return addition;
    }
    
    public void updateRemainStock(int number) {
        this.stock = this.stock - number;
    }
    
    public void updateStockByReturnNumber(int number) {
        this.stock = this.stock + number;
    }
    
    
    public boolean isOutOfStock() {
        return this.stock == 0;
    }
    
    public boolean isPriceValid() {
        return this.price > 0;
    }
    
    @Override
    public String toString() {
        return "" + productNo + " " + name + " " + type + " " + price + " " + stock;
    }
}
