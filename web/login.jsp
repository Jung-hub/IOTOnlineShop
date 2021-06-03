<%-- 
    Document   : login
    Created on : 
    Author     : 
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
        <link rel="stylesheet" href="CSS/login.css">
        <title>Login Page</title>
    </head>
    <body>
        
        <%
            String redirectURL = "http://localhost:8080/IOTBay/WelcomeController";
            String existErr = (String)session.getAttribute("existErr");
            String passErr = (String)session.getAttribute("passErr");
            String usernameErr = (String) session.getAttribute("usernameErr");
            String userLock = (String)session.getAttribute("userLock");
            
            User user = (User)session.getAttribute("user");
            if (user != null) {
                response.sendRedirect(redirectURL);
            }
            
        %>
        
        <form class="box" action="LoginServlet" method="post" id="login">
            <h1>Login</h1>
            <input type="text" id="uname" name="uname" autocomplete="off" placeholder="Username" required>
            <input type="password" id="upassword" name="upassword" autocomplete="off" placeholder="Password" required>
            <input type="submit" form="login" name="login" value="Login">
            <input type="button" value="Back" onclick="location.href='http://localhost:8080/IOTBay/'">
            <p> Not a Customer? <a href="register.jsp">Register</a></p>
            <p class="errorinfo"><%=usernameErr != null? usernameErr: ""%></p>
            <p class="errorinfo"><%=passErr != null? passErr: ""%></p>
            <p class="errorinfo"><%=userLock != null? userLock: ""%></p>
            <p class="errorinfo"><%=existErr != null? existErr: ""%></p>
            
        </form>
        
    </body>
</html>
