<%
    if (request.getAttribute("userProjects") == null
        || request.getAttribute("inspectedUser") == null
        || request.getAttribute("fundedProjects") == null) {
        request.getRequestDispatcher("ControllerUser?action=inspect").forward(request, response);
        return;
    }
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="jumbotron">
  <div class="row">
    <div class="col-xs-2">
      <img data-src="img/profile-placeholder.jpg" src="img/profile-placeholder.jpg" class="profile-img" alt="${comment.idUser.mail}">
    </div>
    <div class="col-xs-10">
      <h1><strong>${requestScope.inspectedUser.mail}</strong><c:if test="${requestScope.inspectedUser.banned}"><span class="danger">(banned)</span></c:if></h1>
      <p>Balance : $ ${requestScope.inspectedUser.balance}</p>

    </div>
    <div id="profile-nav" class="col-xs-12">
      <ul class="nav nav-tabs nav-justified" role="tablist">
        <li role="presentation" class="active"><a href="#created" aria-controls="created" role="tab" data-toggle="tab">Created projects<span class="badge">${userProjects.size()}</span></a></li>
        <li role="presentation"><a href="#funded" aria-controls="funded" role="tab" data-toggle="tab">Funded projects<span class="badge">${fundedProjects.size()}</span></a></li>
        <li role="presentation"><a href="#saved" aria-controls="saved" role="tab" data-toggle="tab">Saved projects<span class="badge">${savedProjects.size()}</span></a></li>
            <c:if test="${sessionScope.user.isAdmin && !requestScope.inspectedUser.isAdmin}">
          <li role="presentation"><a href="#admin" aria-controls="admin" role="tab" data-toggle="tab">Admin</a></li>
          </c:if>
      </ul>
    </div>
    <div class="tab-content col-xs-12">
      <div role="tabpanel" class="tab-pane active" id="created">
        <table class="table table-hover table-striped">
          <thead>
            <tr>
              <th>Project title</th>
              <th>Goal</th>
              <th>Deadline</th>
            </tr>
          </thead>
          <tbody>
            <c:forEach items="${userProjects}" var="project">
                <tr>
                  <td scope="row"><a href="index.jsp?nav=project&id=${project.idProject}"><c:out value="${project.title}" /></a></td>
                  <td>${project.goal}</td>
                  <td><span class="time-relative" data-format="YYYY-MM-DD HH:mm"><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${project.endDate}" /></span></td>
                </tr>
            </c:forEach>
          </tbody>
        </table>
      </div>
      <div role="tabpanel" class="tab-pane" id="funded">
        <table class="table table-hover table-striped">
          <thead>
            <tr>
              <th>Project title</th>
              <th>Fund</th>
              <th>Goal</th>
              <th>Deadline</th>
            </tr>
          </thead>
          <tbody>
            <c:forEach items="${fundedProjects}" var="fund">
                <tr>
                  <td scope="row"><a href="index.jsp?nav=project&id=${fund.idProject.idProject}"><c:out value="${fund.idProject.title}" /></a></td>
                  <td><c:out value="${fund.value}" /></td>
                  <td>${fund.idProject.goal}</td>
                  <td><span class="time-relative" data-format="YYYY-MM-DD HH:mm"><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${fund.idProject.endDate}" /></span></td>
                </tr>
            </c:forEach>
          </tbody>
        </table>

      </div>
      <div role="tabpanel" class="tab-pane" id="saved">
        <p><a href="ControllerMemorise?action=list&id=${requestScope.inspectedUser.idUser}">Here</a></p>
      </div>
      <c:if test="${sessionScope.user.isAdmin && !requestScope.inspectedUser.isAdmin}">
          <div role="tabpanel" class="tab-pane" id="admin">
            <form role="form" method="POST" action="ControllerUser">
              <input type="hidden" name="id" value="${requestScope.inspectedUser.idUser}">
              <c:if test="${!requestScope.inspectedUser.banned}">
                  <input type="hidden" name="action" value="ban"/>
                  <button class="btn btn-lg btn-danger btn-block" type="submit">Ban this user</button>
              </c:if>
              <c:if test="${requestScope.inspectedUser.banned}">
                  <input type="hidden" name="action" value="unban"/>
                  <button class="btn btn-lg btn-danger btn-block" type="submit">Unban this user</button>
              </c:if>
            </form>
          </div>
      </c:if>
    </div>
  </div>
</div>
