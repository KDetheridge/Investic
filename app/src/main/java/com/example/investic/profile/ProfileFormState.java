package com.example.investic.profile;

import androidx.annotation.Nullable;
/**
 * Data validation state of the profile configuration form.
 */
public class ProfileFormState {

    @Nullable
    private Integer investmentAmountError;
    @Nullable
    private Integer investmentDurationError;
    @Nullable
    private Integer investmentRiskLevelError;
    @Nullable
    private Integer investmentScrutinyLevelError;

    private boolean isDataValid;

    ProfileFormState(@Nullable Integer investmentAmountError,@Nullable Integer investmentDurationError, @Nullable Integer investmentRiskLevelError, @Nullable Integer investmentScrutinyLevelError){
        this.investmentAmountError = investmentAmountError;
        this.investmentDurationError = investmentDurationError;
        this.investmentRiskLevelError = investmentRiskLevelError;
        this.investmentScrutinyLevelError = investmentScrutinyLevelError;
        this.isDataValid = false;
    }
    ProfileFormState(Boolean isValid){
        this.isDataValid = isValid;
    }


    public Integer getInvestmentAmountError(){
        return this.investmentAmountError;
    }
    public Integer getInvestmentDurationError(){
        return this.investmentDurationError;
    }
    public Integer getInvestmentRiskLevelError(){
        return this.investmentRiskLevelError;
    }
    public Integer getInvestmentScrutinyLevelError(){
        return this.investmentScrutinyLevelError;
    }


    public boolean isDataValid(){
        return this.isDataValid;
    }
}
