<%-- 
    Document   : orderline
    Created on : 10/05/2021, 12:30:12 PM
    Author     : Administrator
--%>

<%@page import="uts.isd.model.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="CSS/cart.css">
        <script src="https://kit.fontawesome.com/a076d05399.js" crossorigin="anonymous"></script>
        <title>My Orderline</title>
    </head>
    <body>
        <%  
            ProductList availableProductList = (ProductList) session.getAttribute("availableProductList");
            ProductList orderProductList = (ProductList) session.getAttribute("orderProductList");
            int total = 0;
            String productStockErr = (String) session.getAttribute("productStockErr");
            String successInfo = (String) session.getAttribute("successInfo");
            String orderID = (String) session.getAttribute("orderID");
        %>
        <nav class="customer">
            <input type="checkbox" id="check">
            <label for="check" class="checkbtn">
                <i class="fas fa-bars"></i>
            </label>
            <label class="logo">Order Detail</label>
            <ul>
                <li><a href="OrderController">Back</a></li>
            </ul>
        </nav>
        <section>
            <div>
                <form class="keyword" method="post" action="SearchOrderProductServlet">
                    <input type="text" class="search" name="keyword" autocomplete="off" placeholder="Product name or type">
                    <button type="submit" class="submit" name="filter" value="<%=orderID%>">Search</button>
                </form>
                <p class="errorinfo"><%=productStockErr != null? productStockErr: ""%></p>
                <p class="successinfo"><%=successInfo != null? successInfo: ""%></p>
            </div>
            <%if(orderProductList.listSize() == 0) {%>
                <h1>No matched products</h1>
            <%}else {%>
                <%for (int i = 0; i < orderProductList.listSize(); i++) {
                int productNo = orderProductList.getProductByIndex(i).getProductNo();
                String name = orderProductList.getProductByIndex(i).getName();
                String type = orderProductList.getProductByIndex(i).getType();
                int price = orderProductList.getProductByIndex(i).getPrice();
                int stock = orderProductList.getProductByIndex(i).getStock();
                int availableNumber = stock + availableProductList.getQuantityByProductNo(productNo);
                total = total + price * stock;
                %>
                <div>
                    <div class="<%=type.toLowerCase()%>">
                        <h1 class="title_left"><%=type.toUpperCase()%></h1>
                        <h1 class="title_right">$<%=price * stock%></h1>
                    </div>
                    <div class="<%=type.toLowerCase()%>">
                        <h3>Product Name: <%=name%></h3>
                        <h3>Product Price: <%=price%></h3>
                        <h3>Maximum Available Quantity: <%=availableNumber%></h3>
                    </div>
                    <div class="<%=type.toLowerCase()%>">
                        <form method="post" action="UpdateOrderProductServlet">
                            <label class="inline" for="<%=productNo%>"><h3>Order: </h3></label>
                            <input class="inline" type="text" id="<%=productNo%>" name="quantity" value="<%=stock%>" placeholder="Number">
                            <input type="hidden" name="validnumber" value="<%=availableNumber%>">
                            <input type="hidden" name="orderID" value="<%=orderID%>">
                            <button class="edit inline" type="submit" name="update" value="<%=productNo%>">Update</button>
                            <button class="delete inline" type="submit" name="delete" value="<%=productNo%>">Delete</button>
                        </form>
                    </div>
                </div>
                <%}%>
            <div>
                <h1 class="total_right">Total: $<%=total%></h1>
            </div>
            
            <%}%>
            
                
           
        </section>
    </body>
</html>
