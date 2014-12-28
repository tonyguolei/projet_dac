<%
    if (request.getAttribute("projects") == null) {
        response.sendRedirect("ControllerProject?action=list");
        return;
    }
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="jumbotron">
    <h1>Projects list</h1>
    <table class="table table-hover table-striped">
        <thead>
            <tr>
                <th>Project title</th>
                <th>Owner</th>
                <th>Goal</th>
                <th>Deadline</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${projects}" var="project">
                <tr>
                    <th scope="row"><a href="login.jsp?nav=project&id=${project.idProject}"><c:out value="${project.title}" /></a></th>
                    <td><a href="login.jsp?nav=user&id=${project.idOwner.idUser}"><c:out value="${project.idOwner.mail}" /></a></td>
                    <td><c:out value="${project.goal}" /></td>
                    <td><c:out value="${project.endDate}" /></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>