package com.example.investic;

import android.content.Context;

public class InvesticApplication extends android.app.Application {

    private static InvesticApplication instance;

    public InvesticApplication() {
        instance = this;
    }

    public static Context getContext() {
        return instance;
    }


}
