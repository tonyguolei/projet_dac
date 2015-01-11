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

<div class="container-fluid">
  <div class="row">
    <div class="list-group">
      <h5 class="list-group-item-heading">${requestScope.dest}</h5>
      <c:forEach  items="${requestScope.listMessageConversation}" var="pm">
        <p class="list-group-item-text">${pm.message}</p>
      </c:forEach>   
    </div>
  </div>
</div>