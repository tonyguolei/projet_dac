<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<nav class="navbar navbar-default">
  <div class="container-fluid">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="index.jsp">SelfStarter</a>
    </div>

    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav">
        <!-- TODO active class  nav is not set wtf-->
        <!--<li class="<c:if test="${request.getParameter('nav') == 'projects'}">active</c:if>"><a href="index.jsp?nav=projects">Browse Projects<c:if test="${request.getParameter('nav') == 'projects'}"><span class="sr-only">(current)</span></c:if></a></li>-->
            <li class="dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">Projects <span class="caret"></span></a>
              <ul class="dropdown-menu" role="menu">
                <li><a href="index.jsp?nav=projects">Browse Projects</a></li>
                <li><a href="index.jsp?nav=createproject">Create a new project</a></li>
                <li><a href="ControllerMemorise?action=list&id=${sessionScope.user.idUser}">Saved projects</a></li>
            </ul>
        </li>
<!--        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">Dropdown <span class="caret"></span></a>
          <ul class="dropdown-menu" role="menu">
            <li><a href="#">Action</a></li>
            <li><a href="#">Another action</a></li>
            <li><a href="#">Something else here</a></li>
            <li class="divider"></li>
            <li><a href="#">Separated link</a></li>
            <li class="divider"></li>
            <li><a href="#">One more separated link</a></li>
          </ul>
        </li>-->
      </ul>
      <form class="navbar-form navbar-left" role="search" action="ControllerProject" method="get">
        <div class="form-group input-group">
          <input type="hidden" id="action" name="action" value="search">
          <input type="text" class="form-control" id="tag" name="tag" placeholder="Search for projects">
          <span class="input-group-addon">
            <button type="submit" class="btn btn-default">
              <span class="glyphicon glyphicon-search" id="logIcon"></span>
            </button>
          </span>
        </div>
      </form>
      <!-- RIGHT NAVBAR -->
        <% if (session.getAttribute("user") == null ) { %>    
        <div class="navbar-right">
            <a class="btn btn-default navbar-btn" role="button" href="index.jsp?nav=login">Log in</a>
            <a class="btn btn-primary navbar-btn" role="button" href="index.jsp?nav=signup">Sign up</a>
        </div>
          <% } else { %>
      <ul class="nav navbar-nav navbar-right">
        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false"><span class="glyphicon glyphicon-user" id="logIcon"></span> <c:out value="${sessionScope.user.mail}"/> <span class="caret"></span></a>
          <ul class="dropdown-menu" role="menu">
            <li><a href="index.jsp?nav=user&id=${sessionScope.user.idUser}">My Profile</a></li>
            <li><a href="index.jsp?nav=settings&id=${sessionScope.user.idUser}">Settings</a></li>
            <li class="divider"></li>
            <li><a href="ControllerUser?action=logout">Logout</a></li>
          </ul>
        </li>
      </ul>
        <% } %>
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>