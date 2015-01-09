<%-- 
    Document   : createproject
    Author     : Thibaut Coutelou
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="mybeans.Project"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="alerts.AlertType"%>
<%@page import="alerts.Alert"%>
<%/* NOTE: This is an included page from index.jsp (just as the others in this 
   folder, DO NOT use <html> tags or stuff as they're already in */ %>

<%
    if (session.getAttribute("user") == null) {
        String ERROR_LOGIN = "Please log in to edit a project.";
        Alert.addAlert(session, AlertType.DANGER, ERROR_LOGIN);
        response.sendRedirect("index.jsp?nav=login");
        return;
    }

    boolean isEdit = request.getParameter("nav").equals("editproject");

    String endDate="";
    String title="";
    String description="";
    String tags="";
    String goal="";
    if (isEdit) {
        if (request.getAttribute("project") == null) {
            request.getRequestDispatcher("ControllerProject?action=edit").forward(request, response);
            return;
        }
        Project project = (Project) request.getAttribute("project");
        endDate = (new SimpleDateFormat("yyyy-MM-dd")).format(project.getEndDate());
    } else {
    /*this is used to pre-fill form during creation in case of error*/
        title = request.getParameter("title") == null ? "" : request.getParameter("title");
        description = request.getParameter("description") == null ? "" : request.getParameter("description");
        tags = request.getParameter("tags") == null ? "" : request.getParameter("tags");
        goal = request.getParameter("goal") == null ? "" : request.getParameter("goal");
        endDate = request.getParameter("endDate") == null ? "" : request.getParameter("endDate");
    }
%>

<div class="jumbotron">

    <c:choose>
        <c:when test="<%=isEdit%>">
            <h1>Modify an existing project</h1>
        </c:when>
        <c:otherwise>
            <h1>Create a project</h1>
        </c:otherwise>
    </c:choose>
    <form role="form" method="POST" action="ControllerProject?action=
        <c:choose>
            <c:when test="<%=isEdit%>">
                edit
            </c:when>
            <c:otherwise>
                create
            </c:otherwise>
        </c:choose>
    ">
        <input type="hidden" name="id" value="${project.idProject}">
        <div class="form-group">
            <input type="text" class="form-control" name="title" placeholder="Title" required
                <c:choose>
                    <c:when test="<%=isEdit%>">
                        value="${project.title}"
                    </c:when>
                    <c:otherwise>
                        value="<%=title%>"
                    </c:otherwise>
                </c:choose>
            />
        </div>
        <div class="form-group">
            <div class="editor-wrapper">
                <textarea id="editor" data-maxlen="20000" placeholder="Content here ...." name="description"><c:choose><c:when test="<%=isEdit%>">${project.description}</c:when><c:otherwise><%=description%></c:otherwise></c:choose></textarea>
            </div>
        </div>
        <div class="row">
            <div class="form-group col-sm-7">
                <input type="text" class="form-control" name="tags" placeholder="Tags (eg 'soccer,phone,pizza')" required
                    <c:choose>
                        <c:when test="<%=isEdit%>">
                            value="${project.tags}"
                        </c:when>
                        <c:otherwise>
                            value="<%=tags%>"
                        </c:otherwise>
                    </c:choose>
                    />
            </div>
            <div class="form-group col-sm-4 col-sm-offset-1">
                <input type="text" class="form-control datepicker" name="endDate" placeholder="Deadline" value="<%=endDate%>" required/>
            </div>
        </div>
        <div class="form-group">
            <label class="sr-only" for="goal">Goal (in dollars)</label>
            <div class="input-group input-group-lg">
                <div class="input-group-addon">$</div>
                <input type="number" min="0" step="any" class="form-control" name="goal" placeholder="Goal" required
                    <c:choose>
                        <c:when test="<%=isEdit%>">
                            value="${project.goal}"
                        </c:when>
                        <c:otherwise>
                            value="<%=goal%>"
                        </c:otherwise>
                    </c:choose>
                    />
            </div>
        </div>
        <button class="btn btn-lg btn-primary btn-block" type="submit">
            <c:choose>
                <c:when test="<%=isEdit%>">
                    Update
                </c:when>
                <c:otherwise>
                    Create
                </c:otherwise>
            </c:choose>
        </button>
    </form>
</div>
