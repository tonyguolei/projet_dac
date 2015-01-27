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
<div class="container-fluid">
  <a href="index.jsp?nav=newmessage" class="btn btn-primary btn-block">New message</a>
  <hr>
  <div id="discussion">
    <div class="panel panel-default">
      <c:forEach  items="${requestScope.listAllPrivateMessage}" var="pm">
          <div class="panel-heading">
            <c:choose>
                <c:when test="${sessionScope.user.idUser == pm.dest.idUser}">
                    <a class="media-left" href="index.jsp?nav=conversation&dest=${pm.exp.mail}">
                      <img data-src="img/profile-placeholder.jpg" src="img/profile-placeholder.jpg" class="profile-img" alt="${pm.exp.mail}">
                    </a>
                    <div class="media-body">
                      <a href="index.jsp?nav=conversation&dest=${pm.exp.mail}">
                        <h6 class="media-heading">${pm.exp.mail}</h6>
                      </a>
                    </div>
                </c:when>
                <c:otherwise>
                    <a href="index.jsp?nav=conversation&dest=${pm.dest.mail}">
                      <a class="media-left" href="index.jsp?nav=conversation&dest=${pm.dest.mail}">
                        <img data-src="img/profile-placeholder.jpg" src="img/profile-placeholder.jpg" class="profile-img" alt="${pm.dest.mail}">
                      </a>
                      <div class="media-body">
                        <a href="index.jsp?nav=conversation&dest=${pm.dest.mail}">
                          <h6 class="media-heading">${pm.dest.mail}</h6>
                        </a>
                      </div>
                    </c:otherwise>
                </c:choose>
          </div>
          <div class="panel-body markdown">${pm.message}</div>
          <div class="fadeout"></div>
      </c:forEach>
    </div>
  </div>
</div>
