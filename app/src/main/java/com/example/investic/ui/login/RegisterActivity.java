package com.example.investic.ui.login;

import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.investic.MainActivity;
import com.example.investic.R;
import com.example.investic.databinding.ActivityRegisterBinding;
import com.example.investic.ui.login.LoginViewModel;
import com.example.investic.ui.login.LoginViewModelFactory;

import db.DBHelper;

public class RegisterActivity extends AppCompatActivity {
    DBHelper db;
    private LoginViewModel loginViewModel;
    private ActivityRegisterBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DBHelper db = new DBHelper(this);

        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //get existing or create new LoginViewModel object
        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory(this))
                .get(LoginViewModel.class);

        final EditText emailEditText = binding.email;
        final EditText usernameEditText = binding.username;
        final EditText passwordEditText = binding.password;
        final EditText passwordConfirmEditText = binding.passwordConfirm;
        final EditText firstNameEditText = binding.firstName;
        final EditText lastNameEditText = binding.lastName;

        final Button loginButton = binding.login;
        final Button registerButton = binding.register;
        final ProgressBar loadingProgressBar = binding.loading;

        loginViewModel.getRegisterFormState().observe(this, new Observer<RegisterFormState>() {
            @Override
            public void onChanged(@Nullable RegisterFormState registerFormState) {
                 /*If either of the login form elements are invalid, grey out the login button
                 each time the text fields are updated, LoginViewModel.loginDataChanged is called.
                 (See "afterTextChanged" below.) This updates the LoginFormState errors if data is
                 invalid, or sets "isValid" to true if all is OK.
                 The values can then be accessed below directly from the loginFormState.
                */

                if (registerFormState == null) {
                    return;
                }

                registerButton.setEnabled(registerFormState.isDataValid());

                if (registerFormState.getEmailError() != null) {
                    emailEditText.setError(getString(registerFormState.getEmailError()));
                }

                if (registerFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(registerFormState.getUsernameError()));
                }
                if (registerFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(registerFormState.getPasswordError()));
                }
                if (registerFormState.getPasswordConfirmError() != null){
                    passwordConfirmEditText.setError(getString(registerFormState.getPasswordConfirmError()));
                }
                if (registerFormState.getFirstNameError() != null) {
                    firstNameEditText.setError(getString(registerFormState.getFirstNameError()));
                }
                if (registerFormState.getLastNameError() != null) {
                    lastNameEditText.setError(getString(registerFormState.getLastNameError()));
                }

            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            /*
             * When the LoginResult changes, check if there are errors.
             * If not, get the LoggedInUserView created by the LoginViewModel (inside loginResult())*/
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    setResult(Activity.RESULT_OK);
                    updateUiWithUser(loginResult.getSuccess());
                    //Complete and destroy login activity once successful
                    finish();
                }



            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }
            /*
             * Called each time any of the text fields are updated.*/
            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.registerDataChanged(emailEditText.getText().toString(),usernameEditText.getText().toString(),
                                                    passwordEditText.getText().toString(),passwordConfirmEditText.getText().toString(), firstNameEditText.getText().toString(),
                                                    lastNameEditText.getText().toString());
            }
        };
        emailEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordConfirmEditText.addTextChangedListener(afterTextChangedListener);

        usernameEditText.addTextChangedListener(afterTextChangedListener);
        firstNameEditText.addTextChangedListener(afterTextChangedListener);
        lastNameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    Boolean loginSuccess = loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                    if(loginSuccess == true){
                        return true;
                    }

                }
                return false;
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                Boolean res = loginViewModel.register(emailEditText.getText().toString(),
                        usernameEditText.getText().toString(),
                        passwordEditText.getText().toString(),
                        firstNameEditText.getText().toString(),
                        lastNameEditText.getText().toString());
                if(res){
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }


            }
        });

        loginButton.setOnClickListener((new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(i);
                finish();
            }
        }));
    }

    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}