<%-- 
    Document   : conversations
    Created on : Jan 11, 2015, 2:29:56 PM
    Author     : guillaumeperrin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%/* NOTE: This is an included page from index.jsp (just as the others in this 
   folder, DO NOT use <html> tags or stuff as they're already in */ %>

<%
    if (request.getAttribute("listAllPrivateMessage") == null) {
        request.getRequestDispatcher("ControllerPrivateMessage?action=conversations").forward(request, response);
        return;
    }
%>
<div class="row">
  <div>
    <a href="index.jsp?nav=newmessage" class="btn btn-primary">New message</a>
  </div>
  <div>
    <h3>Messages</h3>
    <div class="col-xs-8">
      <div class="list-group">
        <c:forEach  items="${requestScope.listAllPrivateMessage}" var="pm">
            <c:choose>
                <c:when test="${sessionScope.user.idUser == pm.dest.idUser}">
                    <a href="index.jsp?nav=conversation&dest=${pm.exp.mail}">
                    </c:when>
                    <c:otherwise>
                        <a href="index.jsp?nav=conversation&dest=${pm.dest.mail}">
                        </c:otherwise>
                    </c:choose>
                    <h6 class="list-group-item-heading active">${pm.exp.mail} -> ${pm.dest.mail}</h6>
                </a>
                <p class="list-group-item-text markdown">${pm.message}</p>
            </c:forEach>   
      </div>
    </div>
  </div>
</div>
