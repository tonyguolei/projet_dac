<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
  <%@include file="fragments/header.jsp" %>
  <body>
    <div class="container">
      <h1>Hello, world!</h1>
      <form method="get" action="helloworld" role="form">
        <input type="submit" class="btn btn-lg btn-primary btn-block" value="Say Hello"/>
      </form>
    </div>
    <%@include file="fragments/footer.jsp" %>
    <%@include file="fragments/jsfiles.jsp" %>
  </body>
</html>