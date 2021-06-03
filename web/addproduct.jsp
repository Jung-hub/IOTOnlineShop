<%-- 
    Document   : addproduct
    Created on : 07/05/2021, 4:13:03 PM
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="uts.isd.model.*"%>
<%--//STEP 1. Import required packages --%>
<%@page import="java.sql.*"%>
<%@page import="uts.isd.model.dao.*"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="CSS/addproduct.css">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>ADD Product</title>
    </head>
    <body>
        <%
           
            String productNameErr = (String)session.getAttribute("productNameErr");
            String productTypeErr = (String)session.getAttribute("productTypeErr");
            String productPriceErr = (String)session.getAttribute("productPriceErr");
            String productStockErr = (String)session.getAttribute("productStockErr");
            String existErr = (String)session.getAttribute("existErr");
            String successInfo = (String)session.getAttribute("successInfo");
            
        %>
        <form class="box" action="AddProductServlet" method="post" id="register">
            <h1>Create New Product</h1>
            <input type="text" name="type" autocomplete="off" placeholder="Product Type" required>
            <input type="text" name="name" autocomplete="off" placeholder="Product Name" required>
            <input type="text" name="price" autocomplete="off" placeholder="Product Price" required>
            <input type="text" name="stock" autocomplete="off" placeholder="Product Stock" required>
            <input type="submit" form="register" name="register" value="Add">
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
