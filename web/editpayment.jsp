<%-- 
    Document   : editpayment.jsp
    Created on : 11/05/2021, 3:00:37 PM
    Author     : Administrator
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
        <link rel="stylesheet" href="CSS/editpayment.css">
        <title>Add User Payment Page</title>
    </head>
    <body>
        
        <%
            
            String existErr = (String)session.getAttribute("existErr");
            String TypeErr = (String)session.getAttribute("paymentTypeErr");
            String NumberErr = (String)session.getAttribute("paymentNumberErr");
            String successInfo = (String)session.getAttribute("successInfo");
            Payment payment = (Payment) session.getAttribute("payment");
            String type = payment.getPaymentType();
            int paymentNo = payment.getPaymentNo();
            String number = payment.getPaymentNumber();
        %>
        
        <form class="box" action="EditPaymentServlet" method="post" id="edituserpayment">
            <h1>Edit Payment</h1>
            <input type="text" name="type" autocomplete="off" value="<%=type%>" placeholder="Payment Type" required>
            <input type="text" name="number" autocomplete="off" value="<%=number%>" placeholder="Payment Number" required>
            <input type="hidden" name="paymentNo" value="<%=paymentNo%>">
            <input type="submit" form="edituserpayment" name="add" value="update">
            <input type="button" value="Back" onclick="location.href='http://localhost:8080/IOTBay/UserPaymentController'">
            <p class="errorinfo"><%=TypeErr != null? TypeErr: ""%></p>
            <p class="errorinfo"><%=NumberErr != null? NumberErr: ""%></p>
            <p class="errorinfo"><%=existErr != null? existErr: ""%></p>
            <p class="successinfo"><%=successInfo != null? successInfo: ""%></p>
        </form>
    </body>
</html>
