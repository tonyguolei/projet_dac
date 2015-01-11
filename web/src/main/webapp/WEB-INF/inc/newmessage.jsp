<%-- 
    Document   : newmessage
    Created on : Jan 11, 2015, 3:04:31 PM
    Author     : guillaumeperrin
--%>

<%@page import="alerts.Alert"%>
<%@page import="alerts.AlertType"%>
<%
    if (session.getAttribute("user") == null) {
        String ERROR_LOGIN = "Please log in to send a private message.";
        Alert.addAlert(session, AlertType.DANGER, ERROR_LOGIN);
        response.sendRedirect("index.jsp?nav=login");
        return;
    }
    
    String dest = request.getAttribute("dest") == null ? "" : (String) request.getAttribute("dest");
    dest = request.getParameter("dest") == null ? dest : request.getParameter("dest");
    String message = request.getParameter("message") == null ? "" : request.getParameter("message");
%>

<div class="container-fluid">
  <form role="form" method="POST" action="ControllerPrivateMessage?action=newmessage">
    <div class="form-group">
      <input type="email" class="form-control" id="dest" name="dest" placeholder="Email of the recipient" value="<%=dest%>" required>
    </div>
    <div class="form-group">
      <div class="editor-wrapper">
        <textarea id="editor" data-maxlen="2000" placeholder="Content here ...." name="message"><%=message%></textarea>
      </div>
    </div>
    <button class="btn btn-lg btn-primary btn-block" type="submit">Send</button>
  </form>
</div>