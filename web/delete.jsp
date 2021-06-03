<%-- 
    Document   : delete
    Created on : 27/04/2021, 5:57:48 PM
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
        <title>Delete Page</title>
    </head>
    <body>
        <h1>Delete Page</h1>
        <h2>Your account has been deleted. </h2>
        <button onclick="location.href='http://localhost:8080/IOTBay/'" class="button">Back to homepage</button>
    </body>
</html>
