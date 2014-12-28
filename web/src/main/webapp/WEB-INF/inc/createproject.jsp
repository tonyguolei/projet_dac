<%-- 
    Document   : createproject
    Author     : Thibaut Coutelou
--%>

<%@page import="alerts.AlertType"%>
<%@page import="alerts.Alert"%>
<%/* NOTE: This is an included page from index.jsp (just as the others in this 
   folder, DO NOT use <html> tags or stuff as they're already in */ %>

<%

    if (session.getAttribute("user") == null) {
        String ERROR_LOGIN = "Please log in to create a new project.";
        Alert.addAlert(session, AlertType.DANGER, ERROR_LOGIN);
        response.sendRedirect("index.jsp?nav=login");
        return;
    }
%>
   
<div class="jumbotron">
  <h1>Create a new project</h1>
  <form role="form" method="POST" action="ControllerProject?action=create">
      <div class="form-group">
          <input type="text" class="form-control" name="title" placeholder="Title"/>
      </div>
      <div class="form-group">
          <textarea class="form-control" name="description" rows="15" placeholder="Description"/></textarea>
      </div>
      <div class="form-group">
          <input type="text" class="form-control" name="tags" placeholder="Tags (eg 'soccer,phone,pizza')"/>
      </div>
      <div class="form-group">
          <label class="sr-only" for="goal">Goal (in dollars)</label>
          <div class="input-group">
              <div class="input-group-addon">$</div>
              <input type="text" class="form-control" name="goal" placeholder="Goal"/>
              <div class="input-group-addon">.00</div>
          </div>
      </div>
      <div class="form-group">
          <input type="text" class="form-control" name="endDate" placeholder="Deadline (yyyy-mm-dd)"/>
      </div>
      <button class="btn btn-lg btn-primary btn-block" type="submit">Create</button>
  </form>
</div>
