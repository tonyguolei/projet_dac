<%
    if (request.getAttribute("userProjects") == null || request.getAttribute("inspectedUser") == null) {
        request.getRequestDispatcher("ControllerUser?action=inspect").forward(request, response);
        return;
    }
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="jumbotron">
    <h1>${requestScope.inspectedUser.mail}</h1>
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
    <h2>Projects saved</h2>
    <p><a href="ControllerMemorise?action=list&id=${requestScope.inspectedUser.idUser}">Here</a></p>
</div>
