<%-- 
    Document   : update
    Created on : 30/04/2021, 12:11:20 AM
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="uts.isd.model.*"%>
<%@page import="java.sql.*"%>
<%@page import="uts.isd.model.dao.*"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Update Page</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="https://kit.fontawesome.com/a076d05399.js" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="CSS/update.css">
    </head>
    <body>
        
        
        <%
            //Get data from the object
            User specificUser = (User) session.getAttribute("specificUser");
            String emailErr = (String) session.getAttribute("emailErr");
            String profileUpdate = (String) session.getAttribute("profileUpdate");
            String firstnameErr = (String) session.getAttribute("firstnameErr");
            String lastnameErr = (String) session.getAttribute("lastnameErr");
            String phoneNumberErr = (String) session.getAttribute("phoneNumberErr");
        %>   
                
        <form class="box" action="UpdateServlet" method="post" id="update">
            <h1><%=specificUser.getUsername()%> Profile</h1>
            <input type="text" id="fname" name="fname" autocomplete="off" placeholder="First Name" value="<%=specificUser.getUserFirstName()%>">
            <input type="text" id="lname" name="lname" autocomplete="off" placeholder="Last Name" value="<%=specificUser.getUserLastName()%>">
            <input type="mail" id="email" name="email" autocomplete="off" placeholder="Email" value="<%=specificUser.getEmail()%>" required>
            <input type="date" id="birthday" name="birthday" autocomplete="off" value="<%=specificUser.getBirthday()%>" required>
            <input type="tel" id="phone" name="phone" autocomplete="off" placeholder="Phone Number" value="<%=specificUser.getPhone()%>">
            <input type="submit" form="update" name="updated" value="Update">
            <input type="button" value="Back" onclick="location.href='http://localhost:8080/IOTBay/accountlist.jsp'">
            <input type="hidden" name="keypassword" value="<%=specificUser.getPassword()%>">
            <input type="hidden" name="keyuser" value="<%=specificUser.getUsername()%>">
            <p class="errorinfo"><%=firstnameErr != null? firstnameErr: ""%></p>
            <p class="errorinfo"><%=lastnameErr != null? lastnameErr: ""%></p>
            <p class="errorinfo"><%=emailErr != null? emailErr: ""%></p>
            <p class="errorinfo"><%=phoneNumberErr != null? phoneNumberErr: ""%></p>
            <p class="successinfo"><%=profileUpdate != null? profileUpdate: ""%></p>
        </form>
    </body>
</html>
