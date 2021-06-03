<%-- 
    Document   : updateproduct
    Created on : 07/05/2021, 1:48:46 PM
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="uts.isd.model.*"%>
<%@page import="java.sql.*"%>
<%@page import="uts.isd.model.dao.*"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Update Product Page</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="https://kit.fontawesome.com/a076d05399.js" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="CSS/update.css">
    </head>
    <body>
        
        
        <%
            //Get data from the object
            Product product = (Product) session.getAttribute("product");
            String productNameErr = (String)session.getAttribute("productNameErr");
            String productTypeErr = (String)session.getAttribute("productTypeErr");
            String productPriceErr = (String)session.getAttribute("productPriceErr");
            String productStockErr = (String)session.getAttribute("productStockErr");
            String existErr = (String)session.getAttribute("existErr");
            String successInfo = (String)session.getAttribute("successInfo");
        %>   
        
        <form class="box" action="UpdateProductServlet" method="post">
            <h1>Update Product</h1>
            <input type="text" name="type" autocomplete="off" placeholder="Product Type" value="<%=product.getType()%>" required>
            <input type="text" name="name" autocomplete="off" placeholder="Product Name" value="<%=product.getName()%>" required>
            <input type="text" name="price" autocomplete="off" placeholder="Product Price" value="<%=product.getPrice()%>" required>
            <input type="text" name="stock" autocomplete="off" placeholder="Product Stock" value="<%=product.getStock()%>" required>
            <input type="hidden" name="number" value="<%=product.getProductNo()%>" required>
            <input type="submit" name="register" value="Update">
            <input type="button" value="Back" onclick="location.href='http://localhost:8080/IOTBay/ProductListController'">
            <p class="errorinfo"><%=productNameErr != null? productNameErr: ""%></p>
            <p class="errorinfo"><%=productTypeErr != null? productTypeErr: ""%></p>
            <p class="errorinfo"><%=productPriceErr != null? productPriceErr: ""%></p>
            <p class="errorinfo"><%=productStockErr != null? productStockErr: ""%></p>
            <p class="errorinfo"><%=existErr != null? existErr: ""%></p>
            <p class="successinfo"><%=successInfo != null? successInfo: ""%></p>
        </form>
        
    </body>
</html>
