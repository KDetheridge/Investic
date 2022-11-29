package db;

import static android.database.sqlite.SQLiteDatabase.*;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.investic.data.model.Company;
import com.example.investic.data.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DBNAME = "Investic.db";
    SQLiteDatabase db;
    public DBHelper(@Nullable Context context) {

        super(context, DBNAME, null, 1);
        this.db = this.getWritableDatabase();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //db = this.getWritableDatabase();
        db.execSQL("CREATE TABLE COMPANY(CompanyID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, CompanyName TEXT, CompanyCategory TEXT, CompanyDescription TEXT, CompanyEthicalScore INTEGER, CompanyMaxScrutiny INTEGER, CompanyRiskLevel INTEGER )");
        db.execSQL("CREATE TABLE USER(UserID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, Email TEXT UNIQUE, Username TEXT, Password TEXT, FirstName TEXT, LastName TEXT, IsConfigured INT)");
        db.execSQL("CREATE TABLE PORTFOLIO(PairID INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT, CompanyID INTEGER NOT NULL , UserID INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE PROFILE(UserID INTEGER  NOT NULL PRIMARY KEY, InvestmentAmount REAL, InvestmentDurationMonths INT, InvestmentRiskLevel INT, InvestmentScrutinyLevel INT)");

        ContentValues contentValuesUser = new ContentValues();
        //PROFILE NOT CONFIGURED
        contentValuesUser.put("Email","kieran@gmail.com");
        contentValuesUser.put("Username", "Kieran");
        contentValuesUser.put("Firstname", "Kieran");
        contentValuesUser.put("LastName", "Dogman");
        contentValuesUser.put("Password", "Apple123");
        contentValuesUser.put("IsConfigured", 0);
        long result = db.insert("USER", null, contentValuesUser);
        Log.i("Result in DB", String.valueOf(result));

        ContentValues contentValuesProfile = new ContentValues();
        contentValuesProfile.put("UserID", 1);
        contentValuesProfile.put("InvestmentAmount", 500.00);
        contentValuesProfile.put("InvestmentDurationMonths", 18);
        contentValuesProfile.put("InvestmentRiskLevel", 3);
        contentValuesProfile.put("InvestmentScrutinyLevel", 2);

        db.insert("PROFILE", null, contentValuesProfile);


        ContentValues contentValuesCompany1 = new ContentValues();
        ContentValues contentValuesCompany2 = new ContentValues();
        ContentValues contentValuesCompany3 = new ContentValues();
        ContentValues contentValuesCompany4 = new ContentValues();
        ContentValues contentValuesCompany5 = new ContentValues();
        ContentValues contentValuesCompany6 = new ContentValues();

        contentValuesCompany1.put("CompanyName","Shell");
        contentValuesCompany1.put("CompanyCategory","Fossil Fuel Provider");
        contentValuesCompany1.put("CompanyEthicalScore",8);
        contentValuesCompany1.put("CompanyMaxScrutiny",2);
        contentValuesCompany1.put("CompanyRiskLevel",2);

        contentValuesCompany2.put("CompanyName","Capgemini LLC.");
        contentValuesCompany2.put("CompanyCategory","Technology Consultancy");
        contentValuesCompany2.put("CompanyEthicalScore",34);
        contentValuesCompany2.put("CompanyMaxScrutiny",4);
        contentValuesCompany2.put("CompanyRiskLevel",2);

        contentValuesCompany3.put("CompanyName","NVIDIA Corporation");
        contentValuesCompany3.put("CompanyCategory","Technology Hardware Manufacturer");
        contentValuesCompany3.put("CompanyEthicalScore",19);
        contentValuesCompany3.put("CompanyMaxScrutiny",3);
        contentValuesCompany3.put("CompanyRiskLevel",2);

        contentValuesCompany4.put("CompanyName","Microsoft");
        contentValuesCompany4.put("CompanyCategory","Technology Services");
        contentValuesCompany4.put("CompanyEthicalScore",26);
        contentValuesCompany4.put("CompanyMaxScrutiny",4);
        contentValuesCompany4.put("CompanyRiskLevel",2);

        contentValuesCompany5.put("CompanyName","Logitech International SA");
        contentValuesCompany5.put("CompanyCategory","Consumer Technology");
        contentValuesCompany5.put("CompanyDescription","Logitech products are distributed in more than 100 countries worldwide via various retail channels as well as through the company's strategic partnerships with top-tier PC manufacturers. Logitech has many brands, including: Logitech, Ultimate Ears, Jaybird, Blue Microphones, ASTRO Gaming and Logitech G. Logitech Int. has more than 7000 employees worldwide.");
        contentValuesCompany5.put("CompanyEthicalScore",20);
        contentValuesCompany5.put("CompanyMaxScrutiny",3);
        contentValuesCompany5.put("CompanyRiskLevel",3);

        contentValuesCompany6.put("CompanyName","Corsair Gaming Inc.");
        contentValuesCompany6.put("CompanyCategory","Consumer Technology");
        contentValuesCompany6.put("CompanyDescription","Corsair Gaming Inc. is a technology company engaged in the development and manufacture of various products aimed at gamers, content creators, and PC users.");
        contentValuesCompany6.put("CompanyEthicalScore",16);
        contentValuesCompany6.put("CompanyMaxScrutiny",3);
        contentValuesCompany6.put("CompanyRiskLevel",3);

        db.insert("COMPANY", null, contentValuesCompany1);
        db.insert("COMPANY", null, contentValuesCompany2);
        db.insert("COMPANY", null, contentValuesCompany3);
        db.insert("COMPANY", null, contentValuesCompany4);
        db.insert("COMPANY", null, contentValuesCompany5);
        db.insert("COMPANY", null, contentValuesCompany6);


        ContentValues contentValuesPortfolio1 = new ContentValues();
        ContentValues contentValuesPortfolio2 = new ContentValues();
        contentValuesPortfolio1.put("CompanyID",1);
        contentValuesPortfolio1.put("UserID",1);

        contentValuesPortfolio2.put("CompanyID",3);
        contentValuesPortfolio2.put("UserID",1);
        db.insert("PORTFOLIO", null, contentValuesPortfolio1);
        db.insert("PORTFOLIO", null, contentValuesPortfolio2);




    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS USER");
    }
    public User createUser(String email, String username, String password, String firstName, String lastName){
        email = email.toLowerCase();
        SQLiteDatabase db = this.getWritableDatabase();
        //User already exists
        if(checkEmail(email.toLowerCase())){
            return null;
        }
        ContentValues contentValuesUser = new ContentValues();
        contentValuesUser.put("Email",email);
        contentValuesUser.put("Username", username);
        contentValuesUser.put("Password", password);
        contentValuesUser.put("FirstName", firstName);
        contentValuesUser.put("LastName", lastName);

        long result = db.insert("User", null, contentValuesUser);

        if (result == -1){
            return null;
        }
        else{
            Integer uID = getUserID(email);

                //Intitialise the user profile config.
                ContentValues contentValuesProfile = new ContentValues();
                contentValuesProfile.put("UserID", uID);
                contentValuesProfile.put("InvestmentAmount", 0);
                contentValuesProfile.put("InvestmentDurationMonths", 0);
                contentValuesProfile.put("InvestmentRiskLevel", 0);
                contentValuesProfile.put("InvestmentScrutinyLevel", 0);

                db.insert("Profile", null, contentValuesProfile);
            return getUser(email, password);
        }
    }
    public Integer getUserID(String email){
        Cursor c = db.rawQuery("SELECT UserID FROM USER WHERE email = ?", new String[] {email});
        Integer userID = -1;
        if (c.getCount() > 0) {
            //Intialise the cursor position for reading data.
            c.moveToPosition(0);

            int userIdIdx = c.getColumnIndex(("UserID"));
            if (userIdIdx >= 0) {
                userID = c.getInt(userIdIdx);
            }
        }
        return userID;
    }
    public Boolean checkUsername(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM USER WHERE USERNAME = ?", new String[] {username});
        if (c.getCount() > 0){
            return true;
        }
        else{
            return false;
        }
    }
    public Boolean checkEmail(String email){
        //SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM USER WHERE email = ?", new String[] {email.toLowerCase()});
        if (c.getCount() > 0){
            return true;
        }
        else{
            return false;
        }
    }

    public Boolean checkUserID(Integer id){
        Cursor c = db.rawQuery("SELECT UserID FROM USER WHERE UserID = ? ", new String[]{String.valueOf(id)});
        if (c.getCount() > 0){
            return true;
        }
        else{
            return false;
        }

    }
    public Boolean checkEmailPassword(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.i("Login Credentials",email.toLowerCase() + " : " + password);
        Cursor c = db.rawQuery("SELECT * FROM USER WHERE email = ? AND PASSWORD = ?", new String[] {email.toLowerCase(), password});
        //Cursor c = db.rawQuery("SELECT * FROM USER", new String[]{});
        Log.i("Num records", String.valueOf(c.getCount()));
        if (c.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public User getUser(String email, String password){
        email = email.toLowerCase();
        SQLiteDatabase db = this.getWritableDatabase();
        User user = null;
        Log.i("Login Credentials",email + " : " + password);
        Cursor c = db.rawQuery("SELECT * FROM USER WHERE email = ? AND password = ?", new String[] {email, password});
        //Cursor c = db.rawQuery("SELECT * FROM USER", new String[]{});
        Log.i("Num records", String.valueOf(c.getCount()));
        if (c.getCount() > 0) {
            String username = "";
            Integer userID = -1;

            //Intialise the cursor position for reading data.
            c.moveToPosition(0);

            int userIdIdx = c.getColumnIndex(("UserID"));
            if (userIdIdx >= 0){
                userID = c.getInt(userIdIdx);
            }

            int x = c.getColumnIndex("Username");

            if (x >= 0){

                username = c.getString(x);
                user = new User(userID,email,username);
            }
        }
        if (user != null){
            Log.i("User details", user.toString());

        }
        else {
            Log.i("User details", "USER IS NULL IN GETUSER OF DBHELPER.");

        }
        return user;
    }

    public ArrayList<HashMap<String,String>> getPortfolio(Integer userID){
        if(userID < 0){
            return null;
        }
        SQLiteDatabase db = this.getWritableDatabase();
        Log.i("User ID", String.valueOf(userID));

        Cursor c = db.rawQuery("SELECT * FROM PORTFOLIO INNER JOIN COMPANY ON PORTFOLIO.COMPANYID = COMPANY.COMPANYID WHERE userid = ?  ", new String[]{String.valueOf(userID)});
        Log.i("Num records", String.valueOf(c.getCount()));
        if (c.getCount() > 0) {
            //Intialise the cursor position for reading data.
            c.moveToPosition(0);

            //All company details, with pseudoID as the key
            ArrayList<HashMap<String, String>> companyList = new ArrayList<HashMap<String, String>>();

            //while there are records left in the cursor
            while (c.getPosition() < c.getCount()){
                //Single company details
                HashMap<String, String> companyDetails = new HashMap<String, String>();


                Integer companyIdIdx = c.getColumnIndex("CompanyID");
                if (companyIdIdx >= 0){
                    companyDetails.put("CompanyID", c.getString(companyIdIdx));
                }
                else{
                    companyDetails.put("CompanyID","NA");
                }

                Integer companyNameIdx = c.getColumnIndex("CompanyName");
                if (companyNameIdx >= 0){
                    companyDetails.put("CompanyName", c.getString(companyNameIdx));
                }
                else{
                    companyDetails.put("CompanyName","NA");
                }

                Integer companyCatIdx = c.getColumnIndex("CompanyCategory");
                if (companyCatIdx >= 0){
                    companyDetails.put("CompanyCategory", c.getString(companyCatIdx));
                }
                else{
                    companyDetails.put("CompanyCategory","NA");
                }

                //Add company details to list
                companyList.add(companyDetails);
                //increment cursor position
                c.moveToNext();
            }

            return companyList;

        } else {

            return null;
        }

    }

    public boolean isProfileConfigured(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        email = email.toLowerCase();
        Cursor c = db.rawQuery("SELECT IsConfigured FROM USER WHERE Email = ?  ", new String[]{String.valueOf(email)});
        c.moveToPosition(0);
        if (c.getInt(0) == 0){
            return false;
        }
        else if (c.getInt(0) == 1){
            return true;
        }
        //catch erroneous records where IsConfigured is not represented by a boolean (1 or 0)

        return false;

    }
    public boolean updateProfile(Integer userID, Double amt, Integer dur, Integer risk, Integer scrutiny){
    SQLiteDatabase db = this.getWritableDatabase();
    Integer res = 0;
    if (checkUserID(userID)){
        ContentValues cv = new ContentValues();
        cv.put("InvestmentAmount", amt);
        cv.put("InvestmentDurationMonths", dur);
        cv.put("InvestmentRiskLevel", risk);
        cv.put("InvestmentScrutinyLevel", scrutiny);


        res = db.update("profile", cv,"UserID = ?", new String[]{String.valueOf(userID)} );
        if(res > 0){
            ContentValues cvUser = new ContentValues();
            cvUser.put("IsConfigured",1);
            db.update("User",cvUser, "UserID  = ?", new String[]{String.valueOf(userID)});
        }
    }
    return (res > 0);
    }

    public ArrayList<Company> find10Matches(int userID){
        String userIDStr = String.valueOf(userID);
        //No or invalid userID provided to function, return nothing.
        if (userIDStr == null || userIDStr == "") {
            return null;

        }
        //Select companies which are not already in the users portfolio and meet the user defined criteria.
        Cursor c = db.rawQuery("SELECT CompanyID,CompanyName,CompanyCategory,CompanyDescription, CompanyEthicalScore FROM COMPANY WHERE CompanyID NOT IN (SELECT CompanyID FROM PORTFOLIO WHERE UserID = ?)" +
                " AND CompanyMaxScrutiny >= (SELECT InvestmentScrutintyLevel FROM Portfolio WHERE UserID = ?)", new String[]{userIDStr});


        return new ArrayList<Company>();
    }
}




















