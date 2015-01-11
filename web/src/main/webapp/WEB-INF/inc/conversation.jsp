<%-- 
    Document   : conversation
    Created on : Jan 11, 2015, 3:46:02 PM
    Author     : guillaumeperrin
--%>

<%
    if (request.getAttribute("listMessageConversation") == null) {
        request.getRequestDispatcher("ControllerPrivateMessage?action=conversation").forward(request, response);
        return;
    }
%>

<div class="row">
  <div>
    <a href="index.jsp?nav=newmessage&dest=${param['dest']}" class="btn btn-primary">Answer</a>
  </div>
  <div class="list-group">
    <h5 class="list-group-item-heading">${param['dest']}</h5>
    <c:forEach  items="${requestScope.listMessageConversation}" var="pm">
      <p class="list-group-item-text markdown">${pm.message}</p>
    </c:forEach>   
  </div>
</div>