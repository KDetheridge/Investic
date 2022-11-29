package com.example.investic.ui.login;

import androidx.annotation.Nullable;

/**
 * Data validation state of the registration form.
 */
class RegisterFormState {
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
    @Nullable
    private Integer passwordConfirmError;
    private boolean isDataValid;

    RegisterFormState(@Nullable Integer emailError, @Nullable Integer usernameError,
                      @Nullable Integer passwordError, @Nullable Integer passwordConfirmError, @Nullable Integer firstNameError, @Nullable Integer lastNameError) {
        this.usernameError = usernameError;
        this.passwordError = passwordError;
        this.passwordConfirmError = passwordConfirmError;
        this.emailError = emailError;
        this.firstNameError = firstNameError;
        this.lastNameError = lastNameError;
        this.isDataValid = false;
    }

    RegisterFormState(boolean isDataValid) {
        this.usernameError = null;
        this.passwordError = null;
        this.isDataValid = isDataValid;
    }
    @Nullable
    Integer getEmailError(){
        return emailError;
    }

    @Nullable
    Integer getUsernameError() {

        return usernameError;
    }

    @Nullable
    Integer getPasswordError() {

        return passwordError;
    }

    @Nullable
    Integer getPasswordConfirmError(){
        return passwordConfirmError;
    }
    @Nullable
    Integer getFirstNameError(){
        return firstNameError;
    }
    @Nullable
    Integer getLastNameError(){
        return lastNameError;
    }
    boolean isDataValid() {
        return isDataValid;
    }
}