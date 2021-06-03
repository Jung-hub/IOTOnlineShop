<%-- 
    Document   : changepassword
    Created on : 02/04/2021, 10:11:04 AM
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="uts.isd.model.*"%>
<%@page import="java.sql.*"%>
<%@page import="uts.isd.model.dao.*"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Change Password Page</title>
        <link rel="stylesheet" href="CSS/changepassword.css">
    </head>
    <body>
        
    <%
        String passDiffErr = (String)session.getAttribute("passDiffErr");;
        String passErr = (String)session.getAttribute("passErr");;
        String profileUpdate = (String)session.getAttribute("profileUpdate");
    %>
    <form class="box" action="ChangePasswordServlet" method="post" id="change">
        <h1>Password</h1>
        <input type="password" id="ppassword" name="ppassword" autocomplete="off" placeholder="Current Password" required>
        <input type="password" id="npassword" name="npassword" autocomplete="off" placeholder="New Password" required>
        <input type="password" id="cpassword" name="cpassword" autocomplete="off" placeholder="Confirm New Password" required>
        <input type="submit" form="change" name="change" value="Confirm">
        <input type="button" value="Back" onclick="location.href='http://localhost:8080/IOTBay/ProfileController'">
        <p class="errorinfo"><%=passDiffErr != null? passDiffErr: ""%></p>
        <p class="errorinfo"><%=passErr != null? passErr: ""%></p>
        <p class="successinfo"><%=profileUpdate != null? profileUpdate: ""%></p>
    </form>
    
    
    </body>
</html>
