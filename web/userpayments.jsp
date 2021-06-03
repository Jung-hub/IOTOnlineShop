<%-- 
    Document   : payment
    Created on : 10/05/2021, 12:30:41 PM
    Author     : Administrator
--%>

<%@page import="uts.isd.model.dao.DBPaymentManager"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@page import="uts.isd.model.dao.DBOrderManager"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="uts.isd.model.dao.DBOrderlineManager"%>
<%@page import="java.sql.Date"%>
<%@page import="uts.isd.model.*"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="CSS/userpayments.css">
        <script src="https://kit.fontawesome.com/a076d05399.js" crossorigin="anonymous"></script>
        <title>My Payments</title>
    </head>
    <body>
        <%
            PaymentList paymentList = (PaymentList) session.getAttribute("paymentList");
            String successInfo = (String)session.getAttribute("successInfo");
        %>
        <nav class="customer">
            <input type="checkbox" id="check">
            <label for="check" class="checkbtn">
                <i class="fas fa-bars"></i>
            </label>
            <label class="logo">My Payment</label>
            <ul>
                <li><a href="AddPaymentController">Add PAYMENT</a></li>
                <li><a href="WelcomeController">Back</a></li>
            </ul>
        </nav>
        <section>
            <div>
                <form class="keyword" method="post" action="SearchPaymentServlet">
                    <input type="text" class="search" name="keyword" autocomplete="off" placeholder="Type or Card number">
                    <button type="submit" class="submit" name="filter1" value="search">Search</button>
                </form>
                <p class="successinfo"><%=successInfo != null? successInfo: ""%></p>
            </div>
            <%if(paymentList.listSize() == 0) {%>
                <h1>No payment</h1>
            <%}else {%>
                <%for(int i = 0; i < paymentList.listSize(); i++) {
                    NumberFormat formatter = new DecimalFormat("00000000");
                    int paymentNo = paymentList.getPaymentByIndex(i).getPaymentNo();
                    String type = paymentList.getPaymentByIndex(i).getPaymentType();
                    String number = paymentList.getPaymentByIndex(i).getPaymentNumber();
                %>
                    <div>
                        <div class="customer">
                            <h1>Payment number: <%=formatter.format(paymentNo)%></h1>
                        </div>
                        <div class="customer">
                            <h1>Type: <%=type.toUpperCase()%></h1>
                            <h1>Number: <%=String.format("%20s",number).replace(' ', '0')%></h1>
                        </div>
                        <div class="customer">
                            <form class="inline" method="post" action="PaymentRecordController">
                                <button type="submit" class="actived" name="record" value="<%=paymentNo%>">Payment records</button>
                            </form>
                            <form class="inline" method="post" action="EditPaymentController">
                                <button type="submit" class="edit" name="edit" value="<%=paymentNo%>">Edit</button>
                            </form>
                            <form class="inline" method="post" action="DeletePaymentServlet">
                                <button type="submit" class="delete" name="delete" value="<%=paymentNo%>">Delete</button>
                            </form>
                        </div>
                    </div>
                <%}%>
            <%}%>
            
        </section>
    </body>
</html>
