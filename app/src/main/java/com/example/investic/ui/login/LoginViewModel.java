package com.example.investic.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.content.Context;
import android.content.Intent;
import android.util.Patterns;

import com.example.investic.MainActivity;
import com.example.investic.data.LoginRepository;
import com.example.investic.data.Result;
import com.example.investic.data.model.LoggedInUser;
import com.example.investic.R;

import db.DBHelper;

public class LoginViewModel extends ViewModel {
    Context context;
    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<RegisterFormState> registerFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private LoginRepository loginRepository;

    LoginViewModel(LoginRepository loginRepository, Context context) {
        this.loginRepository = loginRepository;
        this.context = context;
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<RegisterFormState> getRegisterFormState() {
        return registerFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public Boolean login(String email, String password) {
        // can be launched in a separate asynchronous job
        Result<LoggedInUser> result = loginRepository.login(email, password);

        if (result instanceof Result.Success) {
            LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
            loginResult.setValue(new LoginResult(new LoggedInUserView(data.getDisplayName())));
            //return success to LoginActivity
            return true;

        } else {
            loginResult.setValue(new LoginResult(R.string.login_failed));
            //return fail to LoginActivity
            return false;
        }
    }

    public Boolean register(String email, String password, String username, String firstName, String lastName){
        Result<LoggedInUser> result = loginRepository.register(email, password, username, firstName, lastName);
        if (result instanceof Result.Success) {
            LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
            loginResult.setValue(new LoginResult(new LoggedInUserView(data.getDisplayName())));
            //return success to LoginActivity
            return true;

        } else {
            loginResult.setValue(new LoginResult(R.string.register_failed));
            //return fail to LoginActivity
            return false;
        }

    }

    public void registerDataChanged(String email, String username, String password, String confirmPassword, String firstName, String lastName) {

        if(!isEmailValid(email)){
            registerFormState.setValue(new RegisterFormState(R.string.invalid_email, null,null, null, null, null));
        }
        else if(!isEmailUnique(email)){
            registerFormState.setValue(new RegisterFormState(R.string.existing_email, null,null, null, null, null));
        }
        else if (!isUserNameValid(username)) {
            registerFormState.setValue(new RegisterFormState(null, R.string.invalid_username,null, null,null,null));
        }
        else if (!isPasswordValid(password)) {
            registerFormState.setValue(new RegisterFormState(null,null, R.string.invalid_password, null,null, null));
        }
        else if (!isConfirmPasswordValid(password, confirmPassword)) {
            registerFormState.setValue(new RegisterFormState(null,null, null, R.string.invalid_password_confirm, null, null));
        }
        else if(!isNameValid(firstName)){
            registerFormState.setValue(new RegisterFormState(null,null, null, null, R.string.invalid_name, null));

        }
        else if (!isNameValid(lastName)){
            registerFormState.setValue(new RegisterFormState(null,null, null, null, null, R.string.invalid_name));
        }
        else {
            registerFormState.setValue(new RegisterFormState(true));
        }
    }

    public void loginDataChanged(String email, String password) {
        if (!isEmailValid(email)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_email, null));
        }
        else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null,R.string.invalid_password));
        }
        else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }
    private boolean isConfirmPasswordValid(String password, String confirmPassword){
         return password.equals(confirmPassword);
    }
    private boolean isEmailValid(String email){
        if (email.contains("@")) {

            return Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
        return false;
    }

    public boolean isProfileConfigured(String email){
        DBHelper db = new DBHelper(context);
        return db.isProfileConfigured(email);
    }

    private boolean isEmailUnique(String email){
        DBHelper db = new DBHelper(context);
        boolean emailExists = db.checkEmail(email);
        if (emailExists){
            return false;
        }
        return true;
    }

    private boolean isNameValid(String name){
        return name != null && !name.trim().isEmpty() && name.matches("[a-zA-Z]+");
    }
}