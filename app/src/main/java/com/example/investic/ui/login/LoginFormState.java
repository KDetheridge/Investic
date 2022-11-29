package com.example.investic.ui.login;

import androidx.annotation.Nullable;

/**
 * Data validation state of the login form.
 */
class LoginFormState {
    @Nullable
    private Integer emailError;
    @Nullable
    private Integer usernameError;
    @Nullable
    private Integer passwordError;
    @Nullable
    private Integer firstNameError;
    @Nullable
    private Integer lastNameError;
    private boolean isDataValid;

    LoginFormState(@Nullable Integer emailError,
                   @Nullable Integer passwordError) {
        this.emailError = emailError;
        this.passwordError = passwordError;

        this.isDataValid = false;
    }

    LoginFormState(boolean isDataValid) {
        this.usernameError = null;
        this.passwordError = null;
        this.isDataValid = isDataValid;
    }

    @Nullable
    Integer getUsernameError() {
        return usernameError;
    }

    @Nullable
    Integer getPasswordError() {
        return passwordError;
    }


    Integer getEmailError(){ return emailError;}
    boolean isDataValid() {
        return isDataValid;
    }
}