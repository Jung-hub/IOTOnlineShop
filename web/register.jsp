<%-- 
    Document   : register
    Created on : 
    Author     : 
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="uts.isd.model.*"%>
<%@page import="uts.isd.model.dao.*"%>
<%--//STEP 1. Import required packages --%>
<%@page import="java.sql.*"%>
<!DOCTYPE html>
<html>
    
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="CSS/register.css">
        <title>Register Page</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
            
    </head>
    <body class="back">
        
       
        <%
            String redirectURL = "http://localhost:8080/IOTBay/welcome.jsp";
            String usernameErr = (String) session.getAttribute("usernameErr");
            String passErr = (String)session.getAttribute("passErr");
            String passDiffErr = (String) session.getAttribute("passDiffErr");
            String emailErr = (String) session.getAttribute("emailErr");
            String existErr = (String)session.getAttribute("existErr");
            
            User user = (User)session.getAttribute("user");
            if (user != null) {
                response.sendRedirect(redirectURL);
            } 
        
        %>
        <form class="box" action="RegisterServlet" method="post" id="register">
            <h1>Register</h1>
            <input type="text" id="uname" name="uname" autocomplete="off" placeholder="Username" required>
            <input type="password" id="upassword" name="upassword" autocomplete="off" placeholder="Password" required>
            <input type="password" id="cupassword" name="cupassword" autocomplete="off" placeholder="Confirm Password" required>
            <input type="mail" id="email" name="email" autocomplete="off" placeholder="xxx.xxx@xxx.xxx.xx" required>
            <input type="submit" form="register" name="register" value="Register">
            <input type="button" value="Back" onclick="location.href='http://localhost:8080/IOTBay/'">
            <p> Already a Customer? <a href="login.jsp">Login</a></p>
            <p class="errorinfo"><%=usernameErr != null? usernameErr: ""%></p>
            <p class="errorinfo"><%=passErr != null? passErr: ""%></p>
            <p class="errorinfo"><%=passDiffErr != null? passDiffErr: ""%></p>
            <p class="errorinfo"><%=emailErr != null? emailErr: ""%></p>
            <p class="errorinfo"><%=existErr != null? existErr: ""%></p>
            
        </form>
        
    </body>
</html>

