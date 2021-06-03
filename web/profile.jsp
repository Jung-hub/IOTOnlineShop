<%-- 
    Document   : main
    Created on : 31/03/2021, 5:15:23 PM
    Author     : Administrator
--%>
<%@page import="java.util.Base64"%>
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
            //Get a logged customer
            User user = (User) session.getAttribute("user");
            String type = user.getUsertype();
            String className = type.equals("0")? "root": type.equals("1")? "staff":"customer";
        %>
        
        
        <nav class="<%=className%>">
            <input type="checkbox" id="check">
            <label for="check" class="checkbtn">
                <i class="fas fa-bars"></i>
            </label>
            <label class="logo">My Profile</label>
            <ul>
                <li><a href="EditController">Edit Profile</a></li>
                    
                <li><a href="ChangePasswordController">Change Password</a></li>
                <%if(type.equals("2")) {%>
                    <li><a href="DeleteUserController">Delete Account</a></li>
                <%}%>
                <li><a href="WelcomeController">Back</a></li>
            </ul>
        </nav>
            
        <center>
            <section>
                <table class="table-style">
                    <tbody>
                        <tr>
                            <td class="table-header <%=className%>">First Name</td>
                            <td data-label="First Name"><%=user.getUserFirstName()%></td>
                        </tr>
                        <tr>
                            <td class="table-header <%=className%>">Last Name</td>
                            <td data-label="Last Name"><%=user.getUserLastName()%></td>
                        </tr>
                        <tr>
                            <td class="table-header <%=className%>">Password</td>
                            <td data-label="Password"><%=user.getPassword()%></td>
                        </tr>
                        <tr>
                            <td class="table-header <%=className%>">Email</td>
                            <td data-label="Email"><%=user.getEmail()%></td>
                        </tr>
                        <tr>
                            <td class="table-header <%=className%>">Birthday</td>
                            <td data-label="Birthday"><%=user.getBirthday()%></td>
                        </tr>   
                        <tr>   
                            <td class="table-header <%=className%>">Phone</td>
                            <td data-label="Phone"><%=user.getPhone()%></td>
                        </tr>
                    </tbody>
                </table>
            </section>
        </center>
    </body>
</html>
