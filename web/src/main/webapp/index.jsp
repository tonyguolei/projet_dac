<%-- 
    Document   : index.jsp
    Created on : Dec 21, 2014, 1:04:20 PM
    Author     : bemug
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <%@include file="WEB-INF/fragments/header.jsp" %>
    </head>
  <body>
    <%@include file="WEB-INF/fragments/navbar.jsp" %>
    <div class="container">
        <%@include file="WEB-INF/fragments/messages.jsp" %>
        <!-- Include from GET parameter mechanism goes here -->
        <% String nav = request.getParameter("nav"); %>
        <% if (nav == null || nav.equals("")) { %>
            <%@include file="WEB-INF/inc/welcome.jsp" %>
        <% } else if (nav.equals("login")) { %>
            <%@include file="WEB-INF/inc/login.jsp" %>
        <% } else if (nav.equals("signup")) { %>
            <%@include file="WEB-INF/inc/signup.jsp" %>
        <% } else if (nav.equals("user")) { %>
            <%@include file="WEB-INF/inc/user.jsp" %>
        <% } else if (nav.equals("users")) { %>
            <%@include file="WEB-INF/inc/users.jsp" %>
        <% } else if (nav.equals("projects")) { %>
            <%@include file="WEB-INF/inc/projects.jsp" %>
        <% } else if (nav.equals("project")) { %>
            <%@include file="WEB-INF/inc/project.jsp" %>
        <% } else if (nav.equals("createproject")) { %>
            <%@include file="WEB-INF/inc/createproject.jsp" %>
        <% } else if (nav.equals("settings")) { %>
            <%@include file="WEB-INF/inc/settings.jsp" %>
        <% } else { /*Page not found, go to 404*/ %>
            <%@include file="WEB-INF/err/404.jsp" %>
        <% } %>
    </div>
    <%@include file="WEB-INF/fragments/footer.jsp" %>
    <%@include file="WEB-INF/fragments/jsfiles.jsp" %>
    <% Alert.cleanAlerts(session); %>
  </body>
</html>
