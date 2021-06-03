<%-- 
    Document   : logout
    Created on : 
    Author     : 
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="uts.isd.model.*"%>
<%@page import="uts.isd.model.dao.*"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="CSS/logout.css">
        <title>Logout Page</title>
    </head>
    <body>
        <h1>Logout Page</h1>
        <h2>Your account has been logged out. </h2>
        <button onclick="location.href='http://localhost:8080/IOTBay/'" class="button">Back to homepage</button>
    </body>
</html>
