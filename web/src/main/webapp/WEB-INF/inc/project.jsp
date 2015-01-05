<%
    if (request.getAttribute("project") == null || request.getAttribute("fundLevel") == null) {
        request.getRequestDispatcher("ControllerProject?action=inspect").forward(request, response);
        return;
    }
%>
<div class="jumbotron">
    <h1>${requestScope.project.title}</h1>
    <h2>Owner</h2>
    <p><a href="index.jsp?nav=user&id=${requestScope.project.idOwner.idUser}">${requestScope.project.idOwner.mail}</a></p>
    <h2>Description</h2>
    <div class="markdown">${requestScope.project.description}</div>
    <h2>Goal</h2>
    <p>$ ${requestScope.project.goal}</p>
    <h2>Fund level</h2>
    <p>$ ${requestScope.fundLevel} </p>
    <h2>Save this project</h2>
    <p><a href="ControllerMemorise?action=create&id=${requestScope.project.idProject}">Remember!</a> <a href="ControllerMemorise?action=remove&id=${requestScope.project.idProject}">Forget!</a></p>
    <h2>I want to fund this !</h2>
    <form class="form-inline" role="form" method="POST" action="ControllerFund?action=create&id=${requestScope.project.idProject}">
      <div class="form-group">
          <label class="sr-only" for="value">Amount (in dollars)</label>
          <div class="input-group">
              <div class="input-group-addon">$</div>
              <input type="number" class="form-control" name="value" placeholder="Amount"/>
              <div class="input-group-addon">.00</div>
          </div>
      </div>
      <button class="btn btn-default" type="submit">Fund!</button>
  </form>
    <h2>Creation date</h2>
    <p>${requestScope.project.creationDate}</p>
    <h2>Deadline</h2>
    <p>${requestScope.project.endDate}</p>
    <h2>Tags</h2>
    <p>${requestScope.project.tags}</p>
    <h2>Report</h2>
    <p><a href="ControllerProject?action=report&id=${requestScope.project.idProject}"> Report this project</a></p>
</div>