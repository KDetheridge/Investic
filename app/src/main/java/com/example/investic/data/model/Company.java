package com.example.investic.data.model;

public class Company {
    private int id;
    private String name;
    private String shortDesc;
    private String longDesc;
    private float currentValue;

    public Company(int id, String name, String shortDesc, String longDesc, float currentValue){
        this.id = id;
        this.name = name;
        this.shortDesc = shortDesc;
        this.longDesc = longDesc;
        this.currentValue = currentValue;
    }

    public int getId() {
        return this.id;
    }

    public String getName(){
        return this.name;
    }
    public String getShortDesc(){
        return this.shortDesc;
    }
    public String getLongDesc(){
        return this.longDesc;
    }

    public float getCurrentValue(){
        return this.currentValue;
    }
}
