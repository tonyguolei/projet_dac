<%
    if (request.getAttribute("projects") == null) {
        request.getRequestDispatcher("ControllerProject?action=list").forward(request, response);
        return;
    }
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="jumbotron">
    <h1>Projects list</h1>
    <table class="sortable table table-hover table-striped">
        <thead>
            <tr>
                <th>Project title</th>
                <th>Owner</th>
                <th>Goal</th>
                <th>Deadline</th>
                <c:if test="${sessionScope.user.isAdmin}">
                <th>Reported</th>
                </c:if>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${projects}" var="project">
                <tr>
                    <td scope="row"><strong><a href="index.jsp?nav=project&id=${project.idProject}"><c:out value="${project.title}" /></a></strong></td>
                    <td><a href="index.jsp?nav=user&id=${project.idOwner.idUser}"><c:out value="${project.idOwner.mail}" /></a></td>
                    <td>$ <c:out value="${project.goal}" /></td>
                    <td><span class="time-relative" data-format="YYYY-MM-DD HH:mm"><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${project.endDate}" /></span></td>
                    <c:if test="${sessionScope.user.isAdmin}">
                    <td>
                        <c:if test="${project.flagged}">
                        <font color="red"> Yes </font>
                        </c:if>
                        <c:if test="${!project.flagged}">
                        <font color="green"> No </font>
                        </c:if>
                    </td>
                    </c:if>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>