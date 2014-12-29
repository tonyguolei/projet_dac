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
            <ul class="nav navbar-nav navbar-right">
                <% if (session.getAttribute("user") == null ) { %>    
                    <li><a href="index.jsp?nav=login">Login</a></li>
                    <li><a href="index.jsp?nav=signup">Signup</a></li>
                <% } else { %>
                    <li class="navbar-brand navbar-brand-centered"><span class="glyphicon glyphicon-user" id="logIcon"></span></li>
                    <li class="navbar-text"><c:out value="${sessionScope.user.mail}"/></li>
                    <li><a href="ControllerUser?action=logout">Log out</a></li>
                <% } %>
            </ul>
        <%// </div><!-- /.navbar-collapse --> %>
    </div><!-- /.container-fluid -->
</nav>
