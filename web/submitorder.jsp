<%-- 
    Document   : submitorder
    Created on : 10/05/2021, 12:31:44 PM
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
            
            NumberFormat formatter = new DecimalFormat("00000000");
            PaymentList paymentList = (PaymentList) session.getAttribute("paymentList");
            Order order = (Order) session.getAttribute("order");
            DBOrderlineManager orderlineManager = (DBOrderlineManager) session.getAttribute("orderlineManager");
            ProductList productList = orderlineManager.getProductsByOrderID(order.getOrderID(), order.getStatus());
            String TypeErr = (String)session.getAttribute("paymentTypeErr");
            String NumberErr = (String)session.getAttribute("paymentNumberErr");
            String existErr = (String)session.getAttribute("existErr");
            
            
        %>
        <nav class="customer">
            <input type="checkbox" id="check">
            <label for="check" class="checkbtn">
                <i class="fas fa-bars"></i>
            </label>
            <label class="logo">Submit Order</label>
            <ul>
                <li><a href="OrderController">Back</a></li>
            </ul>
        </nav>
        <section>
            <div>
                <div class="paid">
                    <h1 class="title_left font_size">Order Number <%=formatter.format(order.getOrderID())%></h1>
                    <h1 class="title_right font_size">Order Date: <%=order.getOrderDate()%></h1>
                </div>
            </div>
            
            <div>
                
            
            <div class="paid">
                <div class="paid font_size_smaller"><h1>Product Details</h1></div>
                <%for(int j = 0; j < productList.listSize(); j++) { 
                    String productName = productList.getProductByIndex(j).getName();
                    String productType = productList.getProductByIndex(j).getType().toUpperCase();
                    int quantity = productList.getProductByIndex(j).getStock();
                    int total = productList.getProductByIndex(j).getPrice()*quantity;
                %>
                <div class="<%=productType.toLowerCase()%>">
                    <h1><%= productType%></h1>
                    <h1 class="title_right"><%="Quantity:     " + quantity + " $" + total%></h1>
                    <h1><%= productName.toUpperCase()%></h1>
                </div>
                <%}%>
                <div>
                    <h1 class="title_right">$<%=order.getAmount()%></h1>
                    <h1>Total: </h1>
                </div>
            </div>
            </div>
            <div>
                <div class="paid font_size_smaller"><h1>Payment Selection</h1></div>
                <form method="post" action="SubmitOrderServlet" id="submitorder">
                    <%for(int i = 0; i < paymentList.listSize(); i++) {
                        Payment each = paymentList.getPaymentByIndex(i);
                        int paymentNo = each.getPaymentNo();
                        String type = each.getPaymentType().toUpperCase();
                        String number = each.getPaymentNumber();
                    %>
                    <div class="paid">
                        <input class="inline_block big" type="radio" id="<%=paymentNo%>" name="payment" value="<%=paymentNo%>">
                        <label class="inline_block" for="<%=paymentNo%>">
                            <h3>Type: <%=type%></h3>
                            <h3>Number: <%=number%></h3>
                        </label>
                    </div>
                    <%}%>
                    <div class="paid">
                        <input class="inline_block big" type="radio" name="payment" value="other" checked>
                        <input class="inline_block text_input" name="type" placeholder="Your payment type">
                        <input class="inline_block text_input" type="text" id="other" name="number" placeholder="Your payment number">
                    </div>
                    <div class="paid">
                        <button class="actived inline" form="submitorder" type="submit" name="pay" value="<%=order.getOrderID()%>">Submit Order</button>
                        <p class="errorinfo"><%=TypeErr != null? TypeErr: ""%></p>
                        <p class="errorinfo"><%=NumberErr != null? NumberErr: ""%></p>
                        <p class="errorinfo"><%=existErr != null? existErr: ""%></p>
                    </div>
                </form>
            </div>
        </section>
    </body>
</html>
