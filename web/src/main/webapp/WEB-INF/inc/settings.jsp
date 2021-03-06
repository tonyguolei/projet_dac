<div class="row">
    <div class="col-md-3">
        <div class="list-group">
            <a class="list-group-item active no-padding">Settings</a>
            <a class="list-group-item no-padding" href="#">Account</a>
        </div>
    </div>
    <div class="col-md-9">
        <div class="list-group">
            <div class="list-group-item active no-padding">Change your password</div>
            <div class="list-group-item">
                <form method="post" action="ControllerUser">
                    <div class="form-group">
                        <label for="pass">New password</label>
                        <input type="password" class="form-control" id="pass" name="pass" placeholder="Password">
                    </div>
                    <div class="form-group">
                        <label for="confpass">Confirm new password</label>
                        <input type="password" class="form-control" id="confpass" name="confpass" placeholder="Password">
                    </div>
                    <input type="hidden" id="action" name="action" value="changePass"/>
                    <input type="submit" class="btn btn-default" role="button" href="#" value="Update password" />
                </form>
            </div> 
        </div>
        
        <div class="list-group">
            <div class="list-group-item-danger active no-padding">Delete your account</div>
            <div class="list-group-item">
                <p>Once you delete your account, there is no going back. Please be certain.</p>
                <form method="post" action="ControllerUser">
                    <div class="form-group">
                        <label for="pass">Type your password</label>
                        <input type="password" class="form-control" id="pass" name="pass" placeholder="Password" />
                    </div>
                    <input type="hidden" id="action" name="action" value="deleteAccount" />
                    <input type="submit" class="btn btn-danger" role="button" href="#" value="Delete" />
                </form>
            </div> 
            
        </div>
    </div>
</div>
