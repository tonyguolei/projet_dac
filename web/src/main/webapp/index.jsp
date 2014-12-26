<%-- 
    Document   : index.jsp
    Created on : Dec 21, 2014, 1:04:20 PM
    Author     : bemug
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <%@include file="fragments/header.jsp" %>
    </head>
  <body>
    <%@include file="fragments/navbar.jsp" %>
    <div class="container">
        <!-- Include from GET parameter mechanism goes here -->
        <% String nav = request.getParameter("nav"); %>
        <% if (nav == null || nav == "") { %>
            <%@include file="inc/welcome.jsp" %>
        <% } else { /*Page not found, go to 404*/ %>
            <%@include file="err/404.jsp" %>
        <% } %>
    </div>
    <%@include file="fragments/footer.jsp" %>
    <%@include file="fragments/jsfiles.jsp" %>
  </body>
</html>
