<%-- 
    Document   : index
    Created on : 
    Author     : 
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="uts.isd.model.*"%>
<!DOCTYPE html>
<html>
    
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="CSS/index.css">
    <title>Home Page</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script src="https://kit.fontawesome.com/a076d05399.js" crossorigin="anonymous"></script>
    </head>
    <body>
        <%
            CustomerAccount customerList = (CustomerAccount)session.getAttribute("customerList");
            if(customerList == null) {
                customerList = new CustomerAccount();
                session.setAttribute("customerList", customerList);
            }
        %>
        <nav>
            <input type="checkbox" id="check">
            <label for="check" class="checkbtn">
                <i class="fas fa-bars"></i>
            </label>
            <label class="logo">IoTBay Shop</label>
            <ul>
                <li><a href="login.jsp">Sign in</a></li>
                <li><a href="register.jsp">Sign up</a></li>
                <li><a href="accountlist.jsp">Account list</a></li>
            </ul>
        </nav>
        <section></section>
    </body>
</html>
