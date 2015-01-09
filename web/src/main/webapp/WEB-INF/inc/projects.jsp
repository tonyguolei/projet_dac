<%@page import="java.util.List"%>
<%@page import="mybeans.Project"%>
<%@page import="mybeans.User"%>
<%
    if (request.getAttribute("projects") == null) {
        request.getRequestDispatcher("ControllerProject?action=list").forward(request, response);
        return;
    }

    User currentUser = (User) session.getAttribute("user");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="jumbotron">
    <h1>Projects list</h1>
    <table class="table table-hover table-striped">
        <thead>
            <tr>
                <th>Project title</th>
                <th>Owner</th>
                <th>Goal</th>
                <th>Deadline</th>
                    <% if (currentUser != null) {%>
                    <%     if (currentUser.getIsAdmin()) {%>
                <th>Reported</th>
                    <%     }%>
                    <% }%>
            </tr>
        </thead>
        <tbody>
            <% for (Project project : (List<Project>) request.getAttribute("projects")) {%>
                <tr>
                    <th scope="row"><a href="index.jsp?nav=project&id=<%=project.getIdProject()%>"><c:out value="<%=project.getTitle()%>" /></a></th>
                    <td><a href="index.jsp?nav=user&id=<%=project.getIdOwner().getIdUser()%>"><c:out value="<%=project.getIdOwner().getMail()%>" /></a></td>
                    <td>$ <c:out value="<%=project.getGoal()%>" /></td>
                    <td><span class="time-relative"><fmt:formatDate pattern="yyyy-MM-dd" value="<%=project.getEndDate()%>" /></span></td>
                    <% if (currentUser != null) {%>
                    <%     if (currentUser.getIsAdmin()) {%>
                    <td>
                        <% if (project.getFlagged()) {%>
                        <font color="red"> Yes </font>
                        <% } else {%>
                        <font color="green"> No </font>
                        <% }%>
                    </td>
                    <%     }%>
                    <% }%>
                </tr>
            <% }%>
        </tbody>
    </table>
</div>