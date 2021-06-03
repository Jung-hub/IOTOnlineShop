<%-- 
    Document   : productlist
    Created on : 07/05/2021, 1:47:14 PM
    Author     : Administrator
--%>

<%@page import="uts.isd.model.ProductList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Product List Page</title>
        <link rel="stylesheet" href="CSS/productlist.css">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <script src="https://kit.fontawesome.com/a076d05399.js" crossorigin="anonymous"></script>
    </head>
    <body>
        <%
            ProductList productList = (ProductList)session.getAttribute("productList");
            String successInfo = (String)session.getAttribute("successInfo");
        %>
        <nav class="staff">
            <input type="checkbox" id="check">
            <label for="check" class="checkbtn">
                <i class="fas fa-bars"></i>
            </label>
            <label class="logo">Product List</label>
            <ul>
                <li><a href="AddProductController">ADD PRODUCT</a></li>
                <li><a href="WelcomeController">BACK</a></li>
            </ul>
        </nav>
        <section>
            <div>
                <form class="keyword" method="post" action="SearchProductServlet">
                    <input type="text" class="search" name="keyword" autocomplete="off" placeholder="Product name or type">
                    <button type="submit" class="submit" name="filter" value="search">Search</button>
                    <p class="successinfo"><%=successInfo != null? successInfo: ""%></p>
                </form>
            </div>
            <%for (int i = 0; i < productList.listSize(); i++) {
                int productNo = productList.getProductByIndex(i).getProductNo();
                String name = productList.getProductByIndex(i).getName();
                String type = productList.getProductByIndex(i).getType();
                int price = productList.getProductByIndex(i).getPrice();
                int stock = productList.getProductByIndex(i).getStock();
            %>
                <div>
                    <div class="<%=type.toLowerCase()%>">
                        <h1><%=type.toUpperCase()%></h1>
                    </div>
                    <div class="<%=type.toLowerCase()%>">
                        <h3>Product Name: <%=name%></h3>
                        <h3>Product Price: <%=price%></h3>
                        <h3>Stock: <%=stock%></h3>
                    </div>
                    <div class="<%=type.toLowerCase()%>">
                        <form class="inline" method="post" action="UpdateProductController">
                            <button type="submit" class="edit" name="edit" value="<%=productNo%>">Edit</button>
                        </form>
                        <form class="inline" method="post" action="DeleteProductServlet">
                            <button type="submit" class="delete" name="delete" value="<%=productNo%>">Delete</button>
                        </form>
                    </div>
                </div>
            <%}%>
        </section>
    </body>
</html>
