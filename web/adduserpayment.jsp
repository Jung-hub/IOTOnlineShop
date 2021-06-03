<%-- 
    Document   : paymentline
    Created on : 10/05/2021, 12:30:52 PM
    Author     : Administrator
--%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="uts.isd.model.*"%>
<%@page import="uts.isd.model.dao.*"%>
<%--//STEP 1. Import required packages --%>
<%@page import="java.sql.*"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="CSS/adduserpayment.css">
        <title>Add User Payment Page</title>
    </head>
    <body>
        
        <%
            String redirectURL = "http://localhost:8080/IOTBay/WelcomeController";
            String existErr = (String)session.getAttribute("existErr");
            String TypeErr = (String)session.getAttribute("paymentTypeErr");
            String NumberErr = (String)session.getAttribute("paymentNumberErr");
            String successInfo = (String)session.getAttribute("successInfo");
            User user = (User)session.getAttribute("user");
            if (user != null) {
                response.sendRedirect(redirectURL);
            }
            
        %>
        
        <form class="box" action="AddPaymentServlet" method="post" id="adduserpayment">
            <h1>Add a Payment</h1>
            <input type="text" name="type" autocomplete="off" placeholder="Payment Type" required>
            <input type="text" name="number" autocomplete="off" placeholder="Payment Number" required>
            <input type="submit" form="adduserpayment" name="add" value="Add">
            <input type="button" value="Back" onclick="location.href='http://localhost:8080/IOTBay/UserPaymentController'">
            <p class="errorinfo"><%=TypeErr != null? TypeErr: ""%></p>
            <p class="errorinfo"><%=NumberErr != null? NumberErr: ""%></p>
            <p class="errorinfo"><%=existErr != null? existErr: ""%></p>
            <p class="successinfo"><%=successInfo != null? successInfo: ""%></p>
        </form>
    </body>
</html>
