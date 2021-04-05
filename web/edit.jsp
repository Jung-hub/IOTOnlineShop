<%-- 
    Document   : edit
    Created on : 31/03/2021, 6:51:57 PM
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="uts.isd.model.*"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit Profile Page</title>
        <link rel="stylesheet" href="CSS/edit.css">
    </head>
    <body>
        <%
            //Get data from session
            CustomerAccount customerList= (CustomerAccount) session.getAttribute("customerList");
            
            //Get data from the object
            Customer customer = customerList.getLoggedCustomer();
            
            //Store user info into variables
            String pName = customer.getUsername();
            String pEmail = customer.getEmail();
            String pBirthday = customer.getBirthday();
            String pPhone = customer.getPhone();
            
            
            //The variable is to check user presses update button or not 
            String updated = request.getParameter("updated");
            
            //The variables is to check to disply the update result
            boolean isCustomerEmpty = customer == null? true: false;
            boolean isUpdateSuccessful = false;
            boolean isUpdateOn = updated != null? true : false;
            
            //The variables is store the inforamtion of success or failure update
            String successInfo = "Successfully update";
            String failureInfo = "Update Failure! Check the code";
            
            if(isUpdateOn) {
                    String firstName = request.getParameter("fname");
                    String lastName = request.getParameter("lname");
                    String email = request.getParameter("email");
                    String birthday = request.getParameter("birthday");
                    String phone = request.getParameter("phone");
                    
                    //create an object to initialise customer fields
                    Customer updatedCustomer = new Customer(customer.getUsername(), customer.getPassword(), customer.getEmail());
                    
                    //enable the customer logging condition
                    updatedCustomer.setIsLogged(true);
                    
                    //update customer profile
                    updatedCustomer.setProfile(firstName, lastName , email, birthday, phone);
                    
                    //update customer to list and check the functionality is successful or not
                    isUpdateSuccessful = customerList.updateCustomer(customer, updatedCustomer);
                    
                    //update session
                    session.setAttribute("customerList", customerList);
                    
            }
        %>
        <%if(isCustomerEmpty) {%>
            <h1>Unauthorized action</h1>
            <button onclick="location.href='http://localhost:8080/IOTBay/'" class="button">Back to index page</button>
        <%}else {%>
            <form class="box" action="edit.jsp" method="post" id="update">
                <h1>Edit Profile</h1>
                <input type="text" id="fname" name="fname" autocomplete="off" placeholder="First Name">
                <input type="text" id="lname" name="lname" autocomplete="off" placeholder="Last Name">
                <input type="mail" id="email" name="email" autocomplete="off" placeholder="xxx@xxx.xxx" required>
                <input type="date" id="birthday" name="birthday" autocomplete="off" placeholder="DD/MM/YYYY">
                <input type="tel" id="phone" name="phone" autocomplete="off" placeholder="1234567890">
                <input type="submit" form="update" name="updated" value="Update">
                <input type="button" value="Back" onclick="location.href='http://localhost:8080/IOTBay/profile.jsp'">
                <%
                if(isUpdateOn) {
                    if(isUpdateSuccessful) {%>
                        <p><%=successInfo%></p>
                    <%}else {%>
                        <p class="errorinfo"><%=failureInfo%></p>
                    <%}%>
                <%}%>
            </form>
        <%}%>
            
    </body>
</html>
