<%-- 
    Document   : register
    Created on : 
    Author     : 
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="uts.isd.model.*"%>
<!DOCTYPE html>
<html>
    
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="CSS/register.css">
        <title>Register Page</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    
    </head>
    <body class="back">
        <%
            CustomerAccount customerList= (CustomerAccount) session.getAttribute("customerList");
            Customer customer = customerList.getLoggedCustomer();
            
            String register = request.getParameter("register");
            String redirectURL = "http://localhost:8080/IOTBay/welcome.jsp";
            
            boolean isRegisterButtonClicked = register != null ? true : false;
            
            boolean registerSuccessful = false;
            
            boolean isCustomerNameExist = false;
            String customerNameExistInfo = "Sorry, user name already exists";
            
            boolean passwordsDifferent = false;
            String passwordDiffInfo = "Sorry, passwords are different";
            
            if(customer != null) {
                response.sendRedirect(redirectURL);
            }else {
                if(register != null) {
                    String name = request.getParameter("uname");
                    String uPassword = request.getParameter("upassword");
                    String cPassword = request.getParameter("cupassword");
                    String email = request.getParameter("email");
                    /*
                    String birthday = request.getParameter("birthday");
                    String phone = request.getParameter("phone");
                    */
                    if(uPassword.equals(cPassword)) {
                        if(!customerList.isCustomerNameExist(name)) {
                            //Create a new customer
                            Customer newCustomer = new Customer(name, uPassword, email);
                            
                            //Set the customer is logged.
                            newCustomer.setIsLogged(true);
                            
                            //store the result of the registration into variable
                            registerSuccessful = customerList.setAnCustomer(newCustomer);
                        }
                        else {
                            isCustomerNameExist = true;
                        }
                    }
                    else {
                        passwordsDifferent = true;
                    }
                    
                    if(registerSuccessful) {
                        session.setAttribute("customerList", customerList);
                        response.sendRedirect(redirectURL);
                    }
                }
            }
            
        %>
        <form class="box" action="register.jsp" method="post" id="register">
            <h1>Register</h1>
            <input type="text" id="uname" name="uname" autocomplete="off" placeholder="Username" required>
            <input type="password" id="upassword" name="upassword" autocomplete="off" placeholder="Password" required>
            <input type="password" id="cupassword" name="cupassword" autocomplete="off" placeholder="Confirm Password" required>
            <input type="mail" id="email" name="email" autocomplete="off" placeholder="xxx@xxx.xxx" required>
            <!--
            <input type="date" id="birthday" name="birthday" autocomplete="off" placeholder="DD/MM/YYYY" required>
            <input type="tel" id="phone" name="phone" autocomplete="off" placeholder="1234567890" required>
            -->
            <input type="submit" form="register" name="register" value="Register">
            <input type="button" value="Back" onclick="location.href='http://localhost:8080/IOTBay/'">
            <p> Already a Customer? <a href="login.jsp">Login</a></p>
            <%
            if(isRegisterButtonClicked) {
                if(!registerSuccessful) {
                    if(isCustomerNameExist) {%>
                        <p class="errorinfo"><%= customerNameExistInfo%></p>
                    <%}else if(passwordsDifferent) {%>
                        <p class="errorinfo"><%= passwordDiffInfo%></p>
                    <%}else {%>
                        <p class="errorinfo">Should check the code logic</p>
                    <%}
                }
            }
            %>
        </form>
        
    </body>
</html>

