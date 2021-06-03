<%-- 
    Document   : accountlist
    Created on : 01/04/2021, 5:38:15 PM
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="uts.isd.model.*"%>
<%--//STEP 1. Import required packages --%>
<%@page import="java.sql.*"%>
<%@page import="uts.isd.model.dao.*"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Account List Page</title>
        <link rel="stylesheet" href="CSS/accountlist.css">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <script src="https://kit.fontawesome.com/a076d05399.js" crossorigin="anonymous"></script>
    </head>
    <body>
        <%
            UserAccount userList = (UserAccount) session.getAttribute("accountList");
        %>
        
            <nav class="root">
                <input type="checkbox" id="check">
                <label for="check" class="checkbtn">
                    <i class="fas fa-bars"></i>
                </label>
                <label class="logo">User List</label>
                <ul>
                    <li><a href="AddUserController">ADD USER</a></li>
                    <li><a href="WelcomeController">BACK</a></li>
                </ul>
            </nav>
            <section>
                <div>
                    <form class="keyword" method="post" action="SearchUserServlet">
                        
                        <label for="all">
                            <input type="radio" id="all" name="usertype" value="0" checked>
                            All
                            <span></span>
                        </label>
                        <label for="staff">
                            <input type="radio" id="staff" name="usertype" value="1">
                            Staff
                            <span></span>
                        </label>
                        <label for="customer">
                            <input type="radio" id="customer" name="usertype" value="2">
                            Customer
                            <span></span>
                        </label>
                        <input type="text" class="search" name="keyword" autocomplete="off" placeholder="Username, full name, phone">
                        <button type="submit" class="submit" name="filter1" value="search">Search</button>
                                              
                       
                    </form>
                </div>
                            
                
            <%for (int i = 0; i < userList.getUserAccountNumber(); i++) {
                String eachUsername = userList.getUserByNumber(i).getUsername();
                String eachUsertype = userList.getUserByNumber(i).getUsertype().equals("1")? "staff":"customer";
                String eachUserstatus = userList.getUserByNumber(i).getStatus();
                String eachFirstname = userList.getUserByNumber(i).getUserFirstName();
                String eachLastname = userList.getUserByNumber(i).getUserLastName();
                String eachUserPassword = userList.getUserByNumber(i).getPassword();
                String eachUserphone = userList.getUserByNumber(i).getPhone();
                String eachUserbirth = userList.getUserByNumber(i).getBirthday();
                String eachUsermail = userList.getUserByNumber(i).getEmail();
            %>
                <div>
                    <div class="<%=eachUsertype%>">
                        <h1><%=eachUsertype.toUpperCase()%></h1>
                    </div>
                    <div class="<%=eachUsertype%>">
                        <h3>Account status: <span class="<%=eachUserstatus.equals("1")? "unlocked": "locked"%>"><%=eachUserstatus.equals("1")? "Active": "Locked"%></span></h3>
                        <h3>User name: <%=eachUsername%> Password: <%=eachUserPassword%></h3>
                        <h3>First name: <%=eachFirstname%> Last name: <%=eachLastname%></h3>
                        <h3>Mail: <%=eachUsermail%></h3>
                        <h3>Phone: <%=eachUserphone%></h3>
                        <h3>Birth: <%=eachUserbirth%></h3>
                    </div>
                    <div class="<%=eachUsertype%>">
                        <form class="inline" method="post" action="UpdateController">
                            <button type="submit" class="edit" name="username" value="<%=eachUsername%>">Edit</button>
                        </form>
                        <form class="inline" method="post" action="DeleteServlet">
                            <button type="submit" class="delete" name="delete" value="<%=eachUsername%>">Delete</button>
                        </form>
                        <form class="inline" method="post" action="StatusServlet">
                            <input type="hidden"  name ="username" value="<%=eachUsername%>">
                            <button type="submit" class="<%=eachUserstatus.equals("1")? "lock": "actived"%>" name="status" value="<%=eachUserstatus%>" onclick="location.href='http://localhost:8080/IOTBay/accountlist.jsp'"><%=eachUserstatus.equals("1")? "Lock": "Active"%></button>
                        </form>
                    </div>
                </div>
            <%}%>
       </section>
       
    </body>
</html>
