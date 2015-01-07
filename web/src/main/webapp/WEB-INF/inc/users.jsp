<%
    if (request.getAttribute("users") == null) {
        request.getRequestDispatcher("ControllerUser?action=list").forward(request, response);
        return;
    }
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="jumbotron">
    <h1>Users list</h1>
    <table class="table table-hover table-striped">
        <thead>
            <tr>
                <th>User</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${users}" var="user">
                <tr>
                    <th scope="row"><a href="index.jsp?nav=user&id=${user.idUser}"><c:out value="${user.mail}" /></a></th>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>