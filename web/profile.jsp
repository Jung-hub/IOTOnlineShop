<%-- 
    Document   : main
    Created on : 31/03/2021, 5:15:23 PM
    Author     : Administrator
--%>
<%@page import="uts.isd.model.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="CSS/profile.css">
        <script src="https://kit.fontawesome.com/a076d05399.js" crossorigin="anonymous"></script>
        <title>Profile Page</title>
    </head>
    <body>
        <%
            //Get a customer list from the customer list
            CustomerAccount customerList = (CustomerAccount)session.getAttribute("customerList");
            
            //Get a logged customer
            Customer customer = customerList.getLoggedCustomer();
            
            //Check a logged customer is received or not
            boolean isCustomerEmpty = customer == null? true : false;
        %>
        
        <%if(isCustomerEmpty) {%>
            <h1>Unauthorized action</h1>
            <button onclick="location.href='http://localhost:8080/IOTBay/'" class="button">Back to index page</button>
        <%}else{%>
            <nav>
                <input type="checkbox" id="check">
                <label for="check" class="checkbtn">
                    <i class="fas fa-bars"></i>
                </label>
                <label class="logo">My Profile</label>
                <ul>
                    <li><a href="edit.jsp">Edit Profile</a></li>
                    <li><a href="changepassword.jsp">Change Password</a></li>
                    <li><a href="welcome.jsp">Back</a></li>
                </ul>
            </nav>
            
            <center>
                <section>
                    <table class="table-style">
                        <!--
                        <thead>
                            <tr>
                                <th>First Name</th>
                                <th>Last Name</th>
                                <th>Password</th>
                                <th>Email</th>
                                <th>Birthday</th>
                                <th>Phone</th>
                            </tr>
                        </thead>
                        -->
                        <tbody>
                            <tr>
                                <td class="table-header">First Name</td>
                                <td data-label="First Name"><%=customer.getUserFirstName()%></td>
                            </tr>
                            <tr>
                                <td class="table-header">Last Name</td>
                                <td data-label="Last Name"><%=customer.getUserLastName()%></td>
                            </tr>
                            <tr>
                                <td class="table-header">Password</td>
                                <td data-label="Password"><%=customer.getPassword()%></td>
                            </tr>
                            <tr>
                                <td class="table-header">Email</td>
                                <td data-label="Email"><%=customer.getEmail()%></td>
                            </tr>
                            <tr>
                                <td class="table-header">Birthday</td>
                                <td data-label="Birthday"><%=customer.getBirthday()%></td>
                            </tr>   
                            <tr>   
                                <td class="table-header">Phone</td>
                                <td data-label="Phone"><%=customer.getPhone()%></td>
                            </tr>
                        </tbody>
                    </table>
                </section>
            </center>
            <!--
            <header>
                <h1>Welcome, <%= customer%></h1>
                <div class="container">
                    <nav>
                        <ul>
                            <li><button onclick="location.href='http://localhost:8080/assignment1/edit.jsp'" class="button">Edit Profile</button></li>
                            <li><button onclick="location.href='http://localhost:8080/assignment1/changepassword.jsp'" class="button">Change Password</button></li>
                            <li><button onclick="location.href='http://localhost:8080/assignment1/welcome.jsp'" class="button">Back</button></li>
                        </ul>
                    </nav>
                </div>
            </header>
            -->
        <%}%>
        
        
       
    </body>
</html>
