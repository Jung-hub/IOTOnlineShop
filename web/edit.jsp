<%-- 
    Document   : edit
    Created on : 31/03/2021, 6:51:57 PM
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
        <title>Edit Profile Page</title>
        <link rel="stylesheet" href="CSS/edit.css">
    </head>
    <body>
        <%
            User user = (User) session.getAttribute("user");
            String emailErr = (String) session.getAttribute("emailErr");
            String profileUpdate = (String) session.getAttribute("profileUpdate");
            String firstnameErr = (String) session.getAttribute("firstnameErr");
            String lastnameErr = (String) session.getAttribute("lastnameErr");
            String phoneNumberErr = (String) session.getAttribute("phoneNumberErr");
        %>  
        <form class="box" action="EditServlet" method="post" id="update">
            <h1>Edit Profile</h1>
            <input type="text" id="fname" name="fname" autocomplete="off" placeholder="First Name" value="<%=user.getUserFirstName()%>">
            <input type="text" id="lname" name="lname" autocomplete="off" placeholder="Last Name" value="<%=user.getUserLastName()%>">
            <input type="mail" id="email" name="email" autocomplete="off" placeholder="Email" value="<%=user.getEmail()%>" required>
            <input type="date" id="birthday" name="birthday" autocomplete="off" value="<%=user.getBirthday()%>" required>
            <input type="tel" id="phone" name="phone" autocomplete="off" placeholder="Phone Number" value="<%=user.getPhone()%>">
            <input type="submit" form="update" name="updated" value="Update">
            <input type="button" value="Back" onclick="location.href='http://localhost:8080/IOTBay/ProfileController'">
            <p class="errorinfo"><%=firstnameErr != null? firstnameErr: ""%></p>
            <p class="errorinfo"><%=lastnameErr != null? lastnameErr: ""%></p>
            <p class="errorinfo"><%=emailErr != null? emailErr: ""%></p>
            <p class="errorinfo"><%=phoneNumberErr != null? phoneNumberErr: ""%></p>
            <p class="successinfo"><%=profileUpdate != null? profileUpdate: ""%></p>
        </form>
    </body>
</html>
