<%-- 
    Document   : login
    Created on : 
    Author     : 
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="uts.isd.model.*"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="CSS/login.css">
        <title>Login Page</title>
    </head>
    <body>
        <%
            CustomerAccount customerList = (CustomerAccount) session.getAttribute("customerList");
            Customer customer = customerList.getLoggedCustomer();
            String login = request.getParameter("login");
            String redirectURL = "http://localhost:8080/IOTBay/welcome.jsp";
            
            boolean isCustomerEmpty = customer == null? true : false;
            boolean isLoginButtonClicked = login != null? true : false;
            
            boolean loginSuccessful = false;
            
            if(!isCustomerEmpty) {
                response.sendRedirect(redirectURL);
            }else {
                if(login != null) {
                    String name = request.getParameter("uname");
                    String password = request.getParameter("upassword");
                    loginSuccessful = customerList.setCustomerLogged(name, password);
                    if(loginSuccessful) {
                        session.setAttribute("customerList", customerList);
                        response.sendRedirect(redirectURL);
                    }
                }
            }
            
        %>
       
        
        <form class="box" action="login.jsp" method="get" id="login">
            <h1>Login</h1>
            <input type="text" id="uname" name="uname" autocomplete="off" placeholder="Username" required>
            <input type="password" id="upassword" name="upassword" autocomplete="off" placeholder="Password" required>
            <input type="submit" form="login" name="login" value="Login">
            <input type="button" value="Back" onclick="location.href='http://localhost:8080/IOTBay/'">
            <p> Not a Customer? <a href="register.jsp">Register</a></p>
            <p class="errorinfo"><%= isLoginButtonClicked? loginSuccessful? "": "Invalid password. Please try again" :"" %></p>
        </form>
        
    </body>
</html>
