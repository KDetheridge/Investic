package com.example.investic.data.model;

public class CompanyArticle {
    private String title;
    private String url;
    private int environmentalWordCount;
    private int fairTreatmentWordCount;
    private int reinvestmentWordCount;

    public CompanyArticle(String t, String u, int e, int f, int r){
        this.title = t;
        this.url = u;
        this.environmentalWordCount = e;
        this.fairTreatmentWordCount = f;
        this.reinvestmentWordCount = r;
    }
}
