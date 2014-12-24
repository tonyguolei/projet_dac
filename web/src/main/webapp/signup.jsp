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
        <form class="form-signin" role="form" method="POST" action="ControllerUser?action=signup">
            <h2 class="form-signin-heading">Sign up</h2>
            <input type="email" name="mail" class="form-control" placeholder="Email" required autofocus>
            <input type="password" name="password" class="form-control" placeholder="Password" required>
            <button class="btn btn-lg btn-primary btn-block" type="submit">Sign up</button>
        </form>
        <a href="login.jsp"> Already have an account ?</a>
    </div>
    <%@include file="fragments/footer.jsp" %>
    <%@include file="fragments/jsfiles.jsp" %>
  </body>
</html>
