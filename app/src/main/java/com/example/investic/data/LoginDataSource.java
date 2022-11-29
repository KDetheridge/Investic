package com.example.investic.data;

import android.content.Context;

import com.example.investic.data.model.LoggedInUser;
import com.example.investic.data.model.User;
import com.example.investic.ui.login.LoginActivity;

import java.io.IOException;
import db.DBHelper;
/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {
    Context context;
    public LoginDataSource(Context context){
        this.context = context;
    }
    public Result<LoggedInUser> login(String email, String password) {

        try {
            DBHelper db = new DBHelper(context);
            User u = db.getUser(email,password);
            if(u != null){
                LoggedInUser user = new LoggedInUser(u.getID(), u.getUsername());
                return new Result.Success<>(user);
            }
            else{
                return new Result.Error(new IOException("Username or Password is incorrect."));
            }


            //return new Result.Success<>(fakeUser);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public Result<LoggedInUser> register(String email, String password, String username, String firstName, String lastName){
        DBHelper db = new DBHelper(context);
        User u = db.createUser(email, password, username, firstName, lastName);
        if(u != null){
            LoggedInUser user = new LoggedInUser(u.getID(), u.getUsername());
            return new Result.Success<>(user);
        }
        else{
            return new Result.Error(new IOException("User already exists with this email address."));
        }

    }

    public void logout() {
        // TODO: revoke authentication
    }
}