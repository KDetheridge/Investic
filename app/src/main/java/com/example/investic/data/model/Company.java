package com.example.investic.data.model;

public class Company {
    private String name;
    private String tickerSymbol;
    private String sector;
    private float currentValue;
    private float netChange;
    private int ethicalScore;
    private boolean inPortfolio;


    public Company(String name, String tickerSymbol, String sector,  float currentValue, float netChange, int ethicalScore){
        this.name = name;
        this.sector = sector;
        this.tickerSymbol = tickerSymbol;
        this.currentValue = currentValue;
        this.netChange = netChange;
        this.ethicalScore = ethicalScore;
    }
    public Company(){

    }


    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }
    public String getTickerSymbol() {
        return this.tickerSymbol;
    }
    public void setTickerSymbol(String tickerSymbol){
        this.tickerSymbol = tickerSymbol;
    }

    public String getSector() {
        return this.sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public float getCurrentValue(){
        return this.currentValue;
    }

    public void setCurrentValue(float currentValue) {
        this.currentValue = currentValue;
    }

    public float getNetChange(){
        return this.netChange;
    }

    public void setNetChange(float netChange) {
        this.netChange = netChange;
    }

    public int getEthicalScore() {
        return this.ethicalScore;
    }

    public void setEthicalScore(int ethicalScore) {
        this.ethicalScore = ethicalScore;
    }

    public boolean isInPortfolio() {
        return this.inPortfolio;
    }

    public void setInPortfolio(boolean inPortfolio) {
        this.inPortfolio = inPortfolio;
    }
}
