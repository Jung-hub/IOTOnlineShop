<%-- 
    Document   : accountlist
    Created on : 01/04/2021, 5:38:15 PM
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="uts.isd.model.*"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Account List Page</title>
        <link rel="stylesheet" href="CSS/accountlist.css">
        <script src="https://kit.fontawesome.com/a076d05399.js" crossorigin="anonymous"></script>
    </head>
    <body>
        <%
            CustomerAccount customerList = (CustomerAccount)session.getAttribute("customerList");
            int customerListSize = customerList.getCustomerAccountNumber();
        %>
        <nav>
            <input type="checkbox" id="check">
            <label for="check" class="checkbtn">
                <i class="fas fa-bars"></i>
            </label>
            <label class="logo">Customer List</label>
            <ul>
                <li><a href="index.jsp">Back</a></li>
            </ul>
        </nav>
        
        <section>
            
                    <center>
                        <table class="table-style">
                        <thead>
                            <tr>
                                <th>No</th>
                                <th>Username</th>
                                <th>Password</th>
                                <!--
                                <th>First Name</th>
                                <th>Last Name</th>
                                -->
                                <th>Email</th>
                                <!--
                                <th>Birthday</th>
                                <th>Phone</th>
                                -->
                            </tr>
                        </thead>
                        <tbody>
                            <%for (int i = 0; i < customerListSize; i++) {%>
                            <tr>
                                <td><%=i + 1%></td>
                                <td><%=customerList.getCustomerByNumber(i).getUsername()%></td>
                                <td><%=customerList.getCustomerByNumber(i).getPassword()%></td>
                                <!--
                                <td><%=customerList.getCustomerByNumber(i).getUserFirstName()%></td>
                                <td><%=customerList.getCustomerByNumber(i).getUserLastName()%></td>
                                -->
                                <td><%=customerList.getCustomerByNumber(i).getEmail()%></td>
                                <!--
                                <td><%=customerList.getCustomerByNumber(i).getBirthday()%></td>
                                <td><%=customerList.getCustomerByNumber(i).getPhone()%></td>
                                -->
                            </tr>
                            <%}%>
                        </tbody>
                    </table>
                </center>
                
            
        </section>
        
    </body>
</html>
