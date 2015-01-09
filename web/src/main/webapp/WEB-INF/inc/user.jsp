<%
    if (request.getAttribute("userProjects") == null 
          || request.getAttribute("inspectedUser") == null 
          || request.getAttribute("fundedProjects") == null) {
        request.getRequestDispatcher("ControllerUser?action=inspect").forward(request, response);
        return;
    }
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="jumbotron">
    <h1>${requestScope.inspectedUser.mail}
        <c:if test="${requestScope.inspectedUser.banned}">
            (banned)
        </c:if>
    </h1>
    <p>Balance : $${requestScope.inspectedUser.balance}</p>
    <h2>Projects created</h2>
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
                    <th scope="row"><a href="index.jsp?nav=project&id=${project.idProject}"><c:out value="${project.title}" /></a></th>
                    <td><c:out value="${project.goal}" /></td>
                    <td><c:out value="${project.endDate}" /></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <h2>Projects funded</h2>
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
                    <th scope="row"><a href="index.jsp?nav=project&id=${fund.idProject.idProject}"><c:out value="${fund.idProject.title}" /></a></th>
                    <td><c:out value="${fund.value}" /></th>
                    <td><c:out value="${fund.idProject.goal}" /></td>
                    <td><c:out value="${fund.idProject.endDate}" /></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <h2>Projects saved</h2>
    <p><a href="ControllerMemorise?action=list&id=${requestScope.inspectedUser.idUser}">Here</a></p>
    <c:if test="${sessionScope.user.isAdmin && !requestScope.inspectedUser.banned && !requestScope.inspectedUser.isAdmin}">
    <br/>
    <form role="form" method="POST" action="ControllerUser">
        <input type="hidden" name="action" value="ban"/>
        <input type="hidden" name="id" value="${requestScope.inspectedUser.idUser}">
        <button class="btn btn-lg btn-danger btn-block" type="submit">Ban this user</button>
    </form>
    </c:if>
</div>
