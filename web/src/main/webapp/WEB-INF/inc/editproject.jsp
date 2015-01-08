<%-- 
    Document   : createproject
    Author     : Thibaut Coutelou
--%>

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

    if (request.getAttribute("project") == null) {
        request.getRequestDispatcher("ControllerProject?action=edit").forward(request, response);
        return;
    }
    
    Project project = (Project) request.getAttribute("project");
    String endDate = (new SimpleDateFormat("yyyy-MM-dd")).format(project.getEndDate());
%>

<div class="jumbotron">
    <h1>Modify an existing project</h1>
    <form role="form" method="POST" action="ControllerProject?action=edit">
        <input type="hidden" name="id" value="${project.idProject}">
        <div class="form-group">
            <input type="text" class="form-control" name="title" placeholder="Title" value="${project.title}"/>
        </div>
        <div class="form-group">
            <div class="editor-wrapper">
                <textarea id="editor" data-maxlen="20000" placeholder="Content here ...." name="description">${project.description}</textarea>
            </div>
        </div>
        <div class="row">
            <div class="form-group col-sm-7">
                <input type="text" class="form-control" name="tags" placeholder="Tags (eg 'soccer,phone,pizza')" value="${project.tags}"/>
            </div>
            <div class="form-group col-sm-4 col-sm-offset-1">
                <input type="text" class="form-control datepicker" name="endDate" placeholder="Deadline" value="<%=endDate%>"/>
            </div>
        </div>
        <div class="form-group">
            <label class="sr-only" for="goal">Goal (in dollars)</label>
            <div class="input-group input-group-lg">
                <div class="input-group-addon">$</div>
                <input type="text" class="form-control" name="goal" placeholder="Goal" value="${project.goal}"/>
            </div>
        </div>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Modify</button>
    </form>
</div>
