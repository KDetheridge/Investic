package com.example.investic.profile;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.util.Patterns;

import com.example.investic.MainActivity;
import com.example.investic.data.LoginRepository;
import com.example.investic.data.Result;
import com.example.investic.data.model.LoggedInUser;
import com.example.investic.R;

import com.example.investic.profile.ProfileFormState;

import java.text.NumberFormat;

import db.DBHelper;

public class ProfileConfigModel extends ViewModel{
    Context context;
    private MutableLiveData<ProfileFormState> profileFormState = new MutableLiveData<>();

    ProfileConfigModel( Context context) {
        this.context = context;
    }

    public void profileDataChanged(String investmentAmount, String investmentDuration, String investmentRiskLevel, String investmentScrutinyLevel){

        if (investmentAmount == "" || !isPositiveNumeric(investmentAmount)){
            profileFormState.setValue(new ProfileFormState(R.string.invalid_number,null,null,null));
        }
        else if (investmentDuration == "" || !isPositiveNumeric(investmentDuration)){
            profileFormState.setValue(new ProfileFormState(null,R.string.invalid_number,null,null));

        }
        else if ( investmentRiskLevel == "" || !validateNumericBetween(investmentRiskLevel,1,5)){
            profileFormState.setValue(new ProfileFormState(null, null,R.string.invalid_number_between_1_5,null));

        }
        else if (investmentScrutinyLevel == "" || !validateNumericBetween(investmentScrutinyLevel,1,5)){
            profileFormState.setValue(new ProfileFormState(null, null,null,R.string.invalid_number_between_1_5));

        }
        else{
            profileFormState.setValue(new ProfileFormState(true));
        }
    }

    private boolean validateNumericBetween(String val, Integer lower, Integer upper){
        Integer num = 0;
        if (isPositiveNumeric(val)){
            num = Integer.parseInt(val);
        }
        if (isPositiveNumeric(val) && num <= upper && num >= lower) {
            return true;
        }
        else{
            return false;
        }
    }
    private boolean isPositiveNumeric(String investmentAmount){
        if(investmentAmount == null){
            return false;
        };
        Double x = 0.0;
        //try to cast to double
        try{
            x = Double.parseDouble(investmentAmount);
        }
        //if it fails to convert because the string does not only contain a number, return false
        catch (NumberFormatException e){
            return false;
        }
        //if it is negative, return false.
        if (x < 0){
            return false;
        }
        //positive, return true.
        else {
            return true;
        }
    }

    LiveData<ProfileFormState> getProfileFormState() {
        return profileFormState;
    }


    public Boolean submitProfile(Integer userID, String amt, String dur, String risk, String scrutiny){
        DBHelper db = new DBHelper(context);
        Double amtInt = Double.parseDouble(amt);
        Integer durInt = Integer.parseInt(dur);
        Integer riskInt = Integer.parseInt(risk);
        Integer scrutinyInt = Integer.parseInt(scrutiny);

        boolean res = db.updateProfile(userID, amtInt, durInt, riskInt, scrutinyInt);
        return res;
    }
}
