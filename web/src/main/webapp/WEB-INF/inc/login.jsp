<div class="jumbotron">
    <form class="form-signin" role="form" method="POST" action="ControllerUser?action=login">
        <h2 class="form-signin-heading">Log in</h2>
        <input type="email" name="mail" class="form-control" placeholder="Email" required autofocus value="<%=request.getParameter("mail")!=null?request.getParameter("mail"):""%>">
        <input type="password" name="password" class="form-control" placeholder="Password" required>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Log in</button>
    </form>
    <a href="index.jsp?nav=signup"> No account yet ?</a>
</div>