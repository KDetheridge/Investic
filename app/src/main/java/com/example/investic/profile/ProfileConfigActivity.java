package com.example.investic.profile;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.investic.MainActivity;
import com.example.investic.R;
import com.example.investic.data.LoginRepository;
import com.example.investic.databinding.ActivityLoginBinding;
import com.example.investic.databinding.ActivityProfileConfigBinding;

import com.example.investic.profile.ProfileConfigModel;
import com.example.investic.ui.login.LoginViewModelFactory;
import com.example.investic.ui.login.RegisterActivity;

import db.DBHelper;

public class ProfileConfigActivity extends AppCompatActivity {
    private ActivityProfileConfigBinding binding;
    private ProfileConfigModel profileConfigModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_config);
        binding = ActivityProfileConfigBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //get existing or create new LoginViewModel object
        profileConfigModel = new ProfileConfigModel(getApplicationContext());

        final EditText amtEditText = binding.investmentAmount;
        final EditText durationEditText = binding.investmentDuration;
        final EditText riskEditText = binding.investmentRiskLevel;
        final EditText scrutinyEditText = binding.investmentScrutiny;
        final ProgressBar loadingProgressBar = binding.loading;
        final Button submitButton = binding.submit;
        submitButton.setEnabled(false);
        profileConfigModel.getProfileFormState().observe(this, new Observer<ProfileFormState>() {
            @Override
            public void onChanged(@Nullable ProfileFormState profileFormState) {
                 /*If either of the login form elements are invalid, grey out the login button
                 each time the text fields are updated, LoginViewModel.loginDataChanged is called.
                 (See "afterTextChanged" below.) This updates the LoginFormState errors if data is
                 invalid, or sets "isValid" to true if all is OK.
                 The values can then be accessed below directly from the loginFormState.
                */

                if (profileFormState == null) {
                    return;
                }

                submitButton.setEnabled(profileFormState.isDataValid());
                if (profileFormState.getInvestmentAmountError() != null){
                    amtEditText.setError(getString(profileFormState.getInvestmentAmountError()));
                }
                if (profileFormState.getInvestmentDurationError() != null) {
                    durationEditText.setError(getString(profileFormState.getInvestmentDurationError()));
                }
                if (profileFormState.getInvestmentRiskLevelError() != null) {
                    riskEditText.setError(getString(profileFormState.getInvestmentRiskLevelError()));
                }
                if (profileFormState.getInvestmentScrutinyLevelError() != null) {
                    scrutinyEditText.setError(getString(profileFormState.getInvestmentScrutinyLevelError()));
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
             * Called each time either of the text fields are updated.*/
            @Override
            public void afterTextChanged(Editable s) {
                profileConfigModel.profileDataChanged(amtEditText.getText().toString(),
                        durationEditText.getText().toString(),riskEditText.getText().toString(),scrutinyEditText.getText().toString());
            }
        };
        amtEditText.addTextChangedListener(afterTextChangedListener);
        durationEditText.addTextChangedListener(afterTextChangedListener);
        riskEditText.addTextChangedListener(afterTextChangedListener);
        scrutinyEditText.addTextChangedListener(afterTextChangedListener);

        submitButton.setOnClickListener((new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String amt = amtEditText.getText().toString();
                String dur = durationEditText.getText().toString();
                String ris = riskEditText.getText().toString();
                String scr = scrutinyEditText.getText().toString();

                Integer userID = LoginRepository.getLoggedInUser().getUserId();
                boolean success = profileConfigModel.submitProfile(userID, amt,dur,ris,scr);
                if(success){
                    Toast.makeText(getApplicationContext(), R.string.profile_update_success,Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), R.string.profile_update_fail,Toast.LENGTH_LONG).show();

                }
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finish();
            }
        }));
    }



}