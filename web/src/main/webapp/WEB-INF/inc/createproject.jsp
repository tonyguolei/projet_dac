<%-- 
    Document   : createproject
    Author     : Thibaut Coutelou
--%>

<%@page import="alerts.AlertType"%>
<%@page import="alerts.Alert"%>
<%/* NOTE: This is an included page from index.jsp (just as the others in this 
   folder, DO NOT use <html> tags or stuff as they're already in */ %>

<%
    if (session.getAttribute("user") == null) {
        String ERROR_LOGIN = "Please log in to create a new project.";
        Alert.addAlert(session, AlertType.DANGER, ERROR_LOGIN);
        response.sendRedirect("index.jsp?nav=login");
        return;
    }
%>

<%--this is used to pre-fill form during creation in case of error--%>
<%
    String title = request.getParameter("title") == null ? "" : request.getParameter("title");
    String description = request.getParameter("description") == null ? "" : request.getParameter("description");
    String tags = request.getParameter("tags") == null ? "" : request.getParameter("tags");
    String goal = request.getParameter("goal") == null ? "" : request.getParameter("goal");
    String endDate = request.getParameter("endDate") == null ? "" : request.getParameter("endDate");
%>
<div class="jumbotron">
    <h1>Create a new project</h1>
    <form role="form" method="POST" action="ControllerProject?action=create">
        <div class="form-group">
            <input type="text" class="form-control" name="title" placeholder="Title" value="<%=title%>"/>
        </div>
        <div class="form-group">
            <div class="editor-wrapper">
                <textarea id="editor" data-maxlen="20000" placeholder="Content here ...." name="description" value="<%=description%>"></textarea>
            </div>
        </div>
        <div class="row">
            <div class="form-group col-sm-7">
                <input type="text" class="form-control" name="tags" placeholder="Tags (eg 'soccer,phone,pizza')" value="<%=tags%>"/>
            </div>
            <div class="form-group col-sm-4 col-sm-offset-1">
                <input type="text" class="form-control datepicker" name="endDate" placeholder="Deadline" value="<%=endDate%>"/>
            </div>
        </div>
        <div class="form-group">
            <label class="sr-only" for="goal">Goal (in dollars)</label>
            <div class="input-group input-group-lg">
                <div class="input-group-addon">$</div>
                <input type="text" class="form-control" name="goal" placeholder="Goal" value="<%=goal%>"/>
            </div>
        </div>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Create</button>
    </form>
</div>
