<%-- 
    Document   : paymentrecord
    Created on : 13/05/2021, 6:36:05 PM
    Author     : Administrator
--%>

<%@page import="uts.isd.model.dao.DBOrderManager"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="uts.isd.model.dao.DBOrderlineManager"%>
<%@page import="java.sql.Date"%>
<%@page import="uts.isd.model.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="CSS/submitorder.css">
        <script src="https://kit.fontawesome.com/a076d05399.js" crossorigin="anonymous"></script>
        <title>Submit Order</title>
    </head>
    <body>
        <%  
            String dateFormErr = (String) session.getAttribute("dateFormErr");
            NumberFormat orderFormatter = new DecimalFormat("00000000");
            DBOrderlineManager orderlineManager = (DBOrderlineManager) session.getAttribute("orderlineManager");
            OrderList paymentRecord = (OrderList) session.getAttribute("paymentRecord");
                              
        %>
        <nav class="customer">
            <input type="checkbox" id="check">
            <label for="check" class="checkbtn">
                <i class="fas fa-bars"></i>
            </label>
            <label class="logo">Payment Records</label>
            <ul>
                <li><a href="UserPaymentController">Back</a></li>
            </ul>
        </nav>
        <section>
            <div>
                <form class="keyword" method="post" action="SearchRecordServlet" id="search">
                        <input type="text" class="search" name="month" autocomplete="off" placeholder="Month">
                        <input type="text" class="search" name="days" autocomplete="off" placeholder="Days">
                        <button type="submit" class="submit" form="search">Search</button>
                </form>
                <p class="errorinfo"><%=dateFormErr != null? dateFormErr: ""%></p>
            </div>
            <%if(paymentRecord.listSize() == 0) {%>
                <h1>No payment records</h1>
            <%}else {%>
                <%for(int i = 0; i < paymentRecord.listSize(); i++) {
                    Order getOrder = paymentRecord.getOrderByIndex(i);
                    int orderID = getOrder.getOrderID();
                    int amount = getOrder.getAmount();
                    int status = getOrder.getStatus();
                    Date orderDate = getOrder.getOrderDate();
                    Date paymentDate = getOrder.getPaymentDate();
                
                %>
                    <div>
                        <div class="paid">
                            <h1 class="title_left">Order Number <%=orderFormatter.format(orderID)%></h1>
                            <h1 class="title_right">Total: $<%=amount%></h1>
                        </div>
                        <div class="paid">
                            <h1>Order Date: <%=orderDate%></h1>
                            <h1>Payment Date: <%=paymentDate%></h1>
                        </div>
                        <div class="paid">
                            <h1>Product Details</h1>
                            <%
                            ProductList productList = orderlineManager.getProductsByOrderID(orderID, status);
                            for(int j = 0; j < productList.listSize(); j++) { 
                                String productName = productList.getProductByIndex(j).getName();
                                String productType = productList.getProductByIndex(j).getType().toUpperCase();
                                int quantity = productList.getProductByIndex(j).getStock();
                                int total = productList.getProductByIndex(j).getPrice()*quantity;
                            %>
                                <div class="<%=productType.toLowerCase()%>">
                                    <h2><%= productType%></h2>
                                    <h2 class="title_right"><%="Quantity:     " + quantity + " $" + total%></h2>
                                    <h2><%= productName.toUpperCase()%></h2>
                                </div>
                            <%}%>
                        </div>
                    </div>
                
                <%}%>
            <%}%>
            
            
        </section>
    </body>
</html>
