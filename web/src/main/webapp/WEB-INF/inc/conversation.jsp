<%-- 
    Document   : conversation
    Created on : Jan 11, 2015, 3:46:02 PM
    Author     : guillaumeperrin
--%>

<%@page import="mybeans.User"%>
<%@page import="rightsmanager.RightsManager"%>
<%@page import="alerts.Alert"%>
<%@page import="alerts.AlertType"%>

<%
    if (request.getAttribute("listMessageConversation") == null) {
        request.getRequestDispatcher("ControllerPrivateMessage?action=conversation").forward(request, response);
        return;
    }
    String ERROR_LOGIN = "Please log in to send a private message.";
    if (RightsManager.isNotLoggedRedirect(session, response, AlertType.DANGER, ERROR_LOGIN)) {
            return;
        }

        String message = request.getParameter("message") == null ? "" : request.getParameter("message");
%>

<div class="jumbotron">
    <div class="discussion">
      <h3 class="list-group-item-heading">Conversation with ${param['dest']}</h3>
      <hr>
      <c:forEach  items="${requestScope.listMessageConversation}" var="pm">
        <c:choose>
          <c:when test="${sessionScope.user.idUser == pm.exp.idUser}">
            <div class="msg msg-right markdown bg-success<c:if test="${pm.isRead == true}"> msg-read</c:if>">${pm.message}</div>
          </c:when>
          <c:otherwise>
            <div class="msg msg-left markdown <c:if test="${pm.isRead == false}"> msg-unread</c:if>">${pm.message}</div>
          </c:otherwise>
        </c:choose>
      </c:forEach>
    </div>
    <div class="row">
      <form role="form" method="POST" action="ControllerPrivateMessage?action=newmessage">
        <div class="form-group">
          <input type="hidden" class="form-control" id="dest" name="dest" placeholder="Email of the recipient" value="${param["dest"]}" required>
        </div>
        <div class="form-group">
          <div class="editor-wrapper">
            <textarea class="editor" data-maxlen="2000" data-height="220" placeholder="Content here ...." name="message"><%=message%></textarea>
          </div>
        </div>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Send</button>
      </form>
    </div>
</div>