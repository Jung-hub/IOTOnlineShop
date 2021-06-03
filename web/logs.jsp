<%-- 
    Document   : logs
    Created on : 27/04/2021, 6:47:45 PM
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="uts.isd.model.*"%>
<%@page import="uts.isd.model.dao.*"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>LOGS Page</title>
        <link rel="stylesheet" href="CSS/logs.css">
        <meta name="viewport" content="width=device-width, initial-scale=1">
    </head>
    <body>
        <%
            User user = (User) session.getAttribute("user");
            String userType = user.getUsertype().equals("0")? "root":  user.getUsertype().equals("1")? "staff": "customer";
            LogList logList = (LogList) session.getAttribute("logList");
        %>
        <nav class="<%=userType%>">
                <input type="checkbox" id="check">
                <label for="check" class="checkbtn">
                    <i class="fas fa-bars"></i>
                </label>
                <label class="logo">User Logs</label>
                <ul>
                    <li><a href="WelcomeController">BACK</a></li>
                </ul>
        </nav>
            
        <center>
            <div>
                <form class="keyword" method="post" action="SearchLogServlet" id="search">
                        <input type="text" class="search" name="month" autocomplete="off" placeholder="Month">
                        <input type="text" class="search" name="days" autocomplete="off" placeholder="Days">
                        <button type="submit" class="submit" form="search">Search</button>
                </form>
                <table class="table-style">
                        <thead class="<%=userType%>">
                            <tr>
                                <th>No</th>
                                <th>Username</th>
                                <th>Status</th>
                                <th>Date</th>
                                <th>Time</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% for(int i = 0; i < logList.listSize(); i++) {%>
                                <tr>
                                    <td><%= i + 1%></td>
                                    <td><%= logList.getLogByIndex(i).getUsername()%></td>
                                    <td><%= logList.getLogByIndex(i).getType()%></td>
                                    <td><%= logList.getLogByIndex(i).getDate()%></td>
                                    <td><%= logList.getLogByIndex(i).getTime()%></td>
                                </tr>
                            <%}%>
                        </tbody>
                </table>
            </div>
        </center>
    </body>
</html>
