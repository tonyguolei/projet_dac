<div class="row">
  <div class="col-md-4 col-md-offset-4">
    <div class="jumbotron">
      <h2 class="form-signin-heading">Log in</h2>
      <form class="form-signin" role="form" method="POST" action="ControllerUser?action=login">
          <input type="email" name="mail" class="form-control" placeholder="Email" required autofocus value="${requestScope.mail}">
        <input type="password" name="password" class="form-control" placeholder="Password" required>
        <br/>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Log in</button>
      </form>
      <a href="index.jsp?nav=signup"> No account yet ?</a>
    </div>
  </div>
</div>