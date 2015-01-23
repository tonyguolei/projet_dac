<!-- Stack the columns on mobile by making one full-width and the other half-width -->
<div class="row">
  <div class="col-md-4 col-md-offset-4">
    <div class="jumbotron">
      <h2 class="form-signin-heading">Sign up</h2>
      <form class="form-signin" role="form" method="POST" action="ControllerUser?action=signup">
        <input type="email" name="mail" class="form-control" placeholder="Email" required>
        <input type="password" name="password" class="form-control" placeholder="Password" required>
        <br/>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign up</button>
      </form>
    </div>
  </div>
</div>