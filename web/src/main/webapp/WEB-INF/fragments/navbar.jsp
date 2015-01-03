<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<nav class="navbar navbar-default" role="navigation">
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
        <!--
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li class="active"><a href="#">Link <span class="sr-only">(current)</span></a></li>
                <li><a href="#">Link</a></li>
                <li class="dropdown">
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
                </li>
            </ul>
        -->
            <form class="navbar-form navbar-left" role="search" action="ControllerProject" method="get">
                <span class="glyphicon glyphicon-search" id="logIcon"></span>
                <div class="form-group">
                    <input type="hidden" id="action" name="action" value="search">
                    <input type="text" class="form-control" id="tag" name="tag" placeholder="Search projects">
                </div>
            </form>
            <ul class="nav navbar-nav">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">Projects <span class="caret"></span></a>
                    <ul class="dropdown-menu" role="menu">
                        <li><a href="index.jsp?nav=projects">Browse projects</a></li>
                        <li><a href="index.jsp?nav=createproject">Create a new project</a></li>
                    </ul>
                </li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <% if (session.getAttribute("user") == null ) { %>    
                    <li><p class="navbar-btn"><a class="btn btn-default" role="button" href="index.jsp?nav=login">Log in</a></p></li>
                    <li><p class="navbar-btn"><a class="btn btn-primary" role="button" href="index.jsp?nav=signup">Sign up</a></p></li>
                <% } else { %>
                    <li class="navbar-brand navbar-brand-centered"><span class="glyphicon glyphicon-user" id="logIcon"></span></li>
                    <li><a href="index.jsp?nav=user&id=${sessionScope.user.idUser}"><c:out value="${sessionScope.user.mail}"/></a></li>
                    <li><p class="navbar-btn"><a class="btn btn-primary" role="button" href="ControllerUser?action=logout">Log out</a></p></li>
                <% } %>
            </ul>
        <%// </div><!-- /.navbar-collapse --> %>
    </div><!-- /.container-fluid -->
</nav>
