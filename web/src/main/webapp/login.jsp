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
        <!-- Include from GET parameter mechanism goes here -->
        <form class="form-signin" role="form" method="POST" action="ControllerUser?action=login">
            <h2 class="form-signin-heading">Log in</h2>
            <input type="email" name="mail" class="form-control" placeholder="Email" required autofocus>
            <input type="password" name="password" class="form-control" placeholder="Password" required>
            <button class="btn btn-lg btn-primary btn-block" type="submit">Log in</button>
        </form>
        <a href="signup.jsp"> No account yet ?</a>
    </div>
    <%@include file="WEB-INF/fragments/footer.jsp" %>
    <%@include file="WEB-INF/fragments/jsfiles.jsp" %>
  </body>
</html>
