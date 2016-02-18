<%-- 
    Document   : destroySession
    Created on : 17/01/2016, 21:23:21
    Author     : Principal
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ page language="java" %>
<%
   request.getSession().invalidate();
   response.sendRedirect("login.jsp");
   
%><html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
    </body>
</html>
