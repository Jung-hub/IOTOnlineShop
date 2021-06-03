<%-- 
    Document   : order
    Created on : 10/05/2021, 12:30:01 PM
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
        <link rel="stylesheet" href="CSS/order.css">
        <script src="https://kit.fontawesome.com/a076d05399.js" crossorigin="anonymous"></script>
        <title>My Order</title>
    </head>
    <body>
        <%  
            String productStockErr = (String) session.getAttribute("productStockErr");
            String successInfo = (String) session.getAttribute("successInfo");
            String dateFormErr = (String) session.getAttribute("dateFormErr");
            DBOrderlineManager orderlineManager = (DBOrderlineManager)session.getAttribute("orderlineManager");
            OrderList orderList = (OrderList) session.getAttribute("orderList");
        %>
        <nav class="customer">
            <input type="checkbox" id="check">
            <label for="check" class="checkbtn">
                <i class="fas fa-bars"></i>
            </label>
            <label class="logo">My Order</label>
            <ul>
                <li><a href="WelcomeController">Back</a></li>
            </ul>
        </nav>
        <section>
            <div>
                <form class="keyword" method="post" action="SearchOrderServlet" id="search">
                        <input type="text" class="search" name="orderID" autocomplete="off" placeholder="Order number">
                        <input type="text" class="search" name="month" autocomplete="off" placeholder="Month">
                        <input type="text" class="search" name="days" autocomplete="off" placeholder="Days">
                        <button type="submit" class="submit" form="search">Search</button>
                </form>
                <p class="errorinfo"><%=productStockErr != null? productStockErr: ""%></p>
                <p class="errorinfo"><%=dateFormErr != null? dateFormErr: ""%></p>
                <p class="successinfo"><%=successInfo != null? successInfo: ""%></p>
            </div>
            
            <%if(orderList.listSize() == 0) {%>
                <div>
                    <h1>No Order</h1>
                </div>
            <%}else {%>
                <%for (int i = 0; i < orderList.listSize(); i++) {
                    NumberFormat formatter = new DecimalFormat("00000000");
                    int orderID = orderList.getOrderByIndex(i).getOrderID();
                    int status = orderList.getOrderByIndex(i).getStatus();
                    int amount = orderList.getOrderByIndex(i).getAmount();
                    Date date = orderList.getOrderByIndex(i).getOrderDate();
                    ProductList productList = orderlineManager.getProductsByOrderID(orderID, status);
                    String orderStatus = status == -1? "cancelled": status == 0? "unpaid": "paid";
                %>
                    
                        <div>
                            <div class="<%=orderStatus%>">
                                <h1 class="title_left">Order Number <%=formatter.format(orderID)%></h1>
                                <h1 class="title_right">Order Date: <%=date%></h1>
                            </div>
                            <div class="<%=orderStatus%>">
                                <h1>Product Details</h1>
                            
                                <%for(int j = 0; j < productList.listSize(); j++) { 
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
                                <div>
                                    <h1 class="title_right">$<%=amount%></h1>
                                    <h1 class="title_left">Total: </h1>
                                </div>
                            </div>
                            <%if(orderList.getOrderByIndex(i).getStatus() == 0) {%>
                                <div class="<%=orderStatus%>">
                                    <form method="post" action="UpdateOrderController">
                                        <button class="actived inline" type="submit" name="pay" value="<%=orderID%>">Pay Order</button>
                                        <button class="edit inline" type="submit" name="update" value="<%=orderID%>">Update</button>
                                        <button class="delete inline" type="submit" name="delete" value="<%=orderID%>">Cancel</button>
                                    </form>
                                </div>
                            <%}else if(orderList.getOrderByIndex(i).getStatus() == 1) {%>
                                <div class="<%=orderStatus%>">
                                    <h1 class="title_right">Payment Date: <%=orderList.getOrderByIndex(i).getPaymentDate()%></h1>
                                    <h1>Status: Paid</h1>
                                </div>
                                <div class="<%=orderStatus%>">
                                    <h1 class="title_right">Payment number: <%=orderList.getOrderByIndex(i).getPaymentNumber()%></h1>
                                    <h1>Payment type: <%=orderList.getOrderByIndex(i).getPaymentType().toUpperCase()%></h1>
                                </div>
                            <%}else {%>
                                <div class="<%=orderStatus%>">
                                    <h1>Status: Canceled</h1>
                                </div>
                            <%}%>
                        </div>
                <%}%>
            <%}%>
        </section>
    </body>
</html>
