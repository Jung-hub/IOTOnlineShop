<%-- 
    Document   : welcome
    Created on : 
    Author     : Jung
--%>

<%@page import="java.util.Base64"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="uts.isd.model.*"%>
<!DOCTYPE html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="CSS/welcome.css">
        <script src="https://kit.fontawesome.com/a076d05399.js" crossorigin="anonymous"></script>
        <title>Welcome Page</title>
    </head>
    <body>
        <%  
            User user = (User) session.getAttribute("user");
            ProductList availableProductList = (ProductList) session.getAttribute("availableProductList");
            ProductList cartProductList = (ProductList) session.getAttribute("cartProductList");
            OrderList orderList = (OrderList) session.getAttribute("orderList");
            String redirectURL = "http://localhost:8080/IOTBay/unauthorised.jsp";
            boolean isUserNull = user == null;
            if(isUserNull) response.sendRedirect(redirectURL);
            String usertype = user.getUsertype();
            String productStockErr = (String) session.getAttribute("productStockErr");
            String successInfo = (String) session.getAttribute("successInfo");
        %>
        
        <%if(usertype.equals("0")) {%>
            <nav class="root">
                <input type="checkbox" id="check">
                <label for="check" class="checkbtn">
                    <i class="fas fa-bars"></i>
                </label>
                <label class="logo">Hi, <%=user.getUserFirstName().equals("")? user.getUsername(): user.getUserFirstName()%></label>
                <ul>
                    <li><a href="ProfileController">My Profile</a></li>
                    <li><a href="AccountListController">AccountList</a></li>
                    <li><a href="LogsController">LOGS</a></li>
                    <li><a href="LogoutController">Logout</a></li>
                </ul>
            </nav>
        <%}else if(usertype.equals("1")) {%>
            <nav class="staff">
                <input type="checkbox" id="check">
                <label for="check" class="checkbtn">
                    <i class="fas fa-bars"></i>
                </label>
                <label class="logo">Hi, <%=user.getUserFirstName().equals("")? user.getUsername(): user.getUserFirstName()%></label>
                <ul>
                    <li><a href="<%="ProfileController"%>">My Profile</a></li>
                    <li><a href="ProductListController">Product</a></li>
                    <li><a href="OrderController">Order</a></li>
                    <li><a href="LogsController">LOGS</a></li>
                    <li><a href="LogoutController">Logout</a></li>
                </ul>
            </nav>
        <%} else {%>
            <nav class="customer">
                <input type="checkbox" id="check">
                <label for="check" class="checkbtn">
                    <i class="fas fa-bars"></i>
                </label>
                <label class="logo">Hi, <%=user.getUserFirstName().equals("")? user.getUsername(): user.getUserFirstName()%></label>
                <ul>
                    <li><a href="ProfileController">My Profile</a></li>
                    <li><a href="ProductCartController">Cart(<%=cartProductList.listSize()%>)</a></li>
                    <li><a href="OrderController">Order(<%=orderList.listSize()%>)</a></li>
                    <li><a href="UserPaymentController">Payment</a></li>
                    <li><a href="LogsController">LOGS</a></li>
                    <li><a href="LogoutController">Logout</a></li>
                </ul>
            </nav>
            <section>
            <div>
                <form class="keyword" method="post" action="CustomerSearchProductServlet">
                    <input type="text" class="search" name="keyword" autocomplete="off" placeholder="Product name or type">
                    <button type="submit" class="submit" name="filter" value="search">Search</button>
                    <p class="errorinfo"><%=productStockErr != null? productStockErr: ""%></p>
                    <p class="successinfo"><%=successInfo != null? successInfo: ""%></p>
                </form>
            </div>
            <%if(availableProductList.listSize() == 0) {%>
                <h1>No available products</h1>
            <%}else {%>
                <%for (int i = 0; i < availableProductList.listSize(); i++) {
                int productNo = availableProductList.getProductByIndex(i).getProductNo();
                String name = availableProductList.getProductByIndex(i).getName();
                String type = availableProductList.getProductByIndex(i).getType();
                int price = availableProductList.getProductByIndex(i).getPrice();
                int stock = availableProductList.getProductByIndex(i).getStock();
            %>
                <div>
                    <div class="<%=type.toLowerCase()%>">
                        <h1><%=type.toUpperCase()%></h1>
                    </div>
                    <div class="<%=type.toLowerCase()%>">
                        <h3>Product Name: <%=name%></h3>
                        <h3>Product Price: <%=price%></h3>
                        <h3>Available stock: <%=stock%></h3>
                    </div>
                    <div class="<%=type.toLowerCase()%>">
                        <%if(stock != 0) {%>
                            <form method="post" action="ProductCartServlet">
                                <label class="inline" for="<%=productNo%>"><h3>Quantity: </h3></label>
                                <input class="inline" type="text" id="<%=productNo%>" name="quantity" placeholder="Number">
                                <button class="edit inline" type="submit" name="add" value="<%=i%>">Add to cart</button>
                            </form>
                        <%}else {%>
                        <h3>Quantity: <span>Sold out</span></h3>
                        <%}%>
                    </div>
                </div>
                <%}%>
            <%}%>
        <%}%>
        </section>
    </body>
        
        
        
    

