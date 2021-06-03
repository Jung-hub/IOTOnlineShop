<%-- 
    Document   : adduser
    Created on : 27/04/2021, 7:48:04 PM
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
        <link rel="stylesheet" href="CSS/adduser.css">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>ADD USER</title>
    </head>
    <body>
        <%
            String usernameErr = (String)session.getAttribute("usernameErr");
            String passErr = (String)session.getAttribute("passErr");
            String passDiffErr = (String)session.getAttribute("passDiffErr");
            String existErr = (String)session.getAttribute("existErr");
            String profileUpdate = (String)session.getAttribute("profileUpdate");
        %>
        <form class="box" action="AddUserServlet" method="post" id="register">
            <h1>User Registration</h1>
            <input type="text" name="uname" autocomplete="off" placeholder="Username" required>
            <input type="password" id="upassword" name="upassword" autocomplete="off" placeholder="Password" required>
            <input type="password" id="cupassword" name="cupassword" autocomplete="off" placeholder="Confirm Password" required>
            <label for="staff">
                <input type="radio" id="staff" name="utype" value="1" checked>
                Staff
                <span></span>
            </label>
            <label for="customer">
                <input type="radio" id="customer" name="utype" value="2">
                Customer
                <span></span>
            </label>
            <input type="submit" form="register" name="register" value="Register">
            <input type="button" value="Back" onclick="location.href='http://localhost:8080/IOTBay/AccountListController'">
            <p class="errorinfo"><%=usernameErr != null? usernameErr: ""%></p>
            <p class="errorinfo"><%=passErr != null? passErr: ""%></p>
            <p class="errorinfo"><%=passDiffErr != null? passDiffErr: ""%></p>
            <p class="errorinfo"><%=existErr != null? existErr: ""%></p>
            <p class="successinfo"><%=profileUpdate != null? profileUpdate: ""%></p>
        </form>
        
    </body>
</html>
