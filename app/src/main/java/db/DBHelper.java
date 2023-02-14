package db;

import static android.database.sqlite.SQLiteDatabase.*;

import static java.util.Objects.isNull;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.investic.data.model.Company;
import com.example.investic.data.model.CompanyArticle;
import com.example.investic.data.model.User;

import java.text.DecimalFormat;
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
        db.execSQL("CREATE TABLE Company(TickerSymbol String NOT NULL PRIMARY KEY , CompanyName TEXT, Sector TEXT, CompanyDescription TEXT, CompanyEthicalScore INTEGER, CompanyMaxScrutiny INTEGER, CompanyRiskLevel INTEGER )");
        db.execSQL("CREATE TABLE User(UserID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, Email TEXT UNIQUE, Username TEXT, Password TEXT, FirstName TEXT, LastName TEXT, IsConfigured INT)");
        db.execSQL("CREATE TABLE Portfolio(PairID INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT, CompanyTicker INTEGER NOT NULL , UserID INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE Profile(UserID INTEGER  NOT NULL PRIMARY KEY, InvestmentAmount REAL, InvestmentDurationMonths INT, InvestmentRiskLevel INT, InvestmentScrutinyLevel INT)");

        ContentValues contentValuesUser = new ContentValues();
        //PROFILE NOT CONFIGURED
        contentValuesUser.put("Email","kieran@gmail.com");
        contentValuesUser.put("Username", "Kieran123");
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
        contentValuesCompany1.put("Sector","Fossil Fuel Provider");
        contentValuesCompany1.put("CompanyEthicalScore",8);
        contentValuesCompany1.put("CompanyMaxScrutiny",2);
        contentValuesCompany1.put("CompanyRiskLevel",2);

        contentValuesCompany2.put("CompanyName","Capgemini LLC.");
        contentValuesCompany2.put("Sector","Technology Consultancy");
        contentValuesCompany2.put("CompanyEthicalScore",34);
        contentValuesCompany2.put("CompanyMaxScrutiny",4);
        contentValuesCompany2.put("CompanyRiskLevel",2);

        contentValuesCompany3.put("CompanyName","NVIDIA Corporation");
        contentValuesCompany3.put("Sector","Technology Hardware Manufacturer");
        contentValuesCompany3.put("CompanyEthicalScore",19);
        contentValuesCompany3.put("CompanyMaxScrutiny",3);
        contentValuesCompany3.put("CompanyRiskLevel",2);

        contentValuesCompany4.put("CompanyName","Microsoft");
        contentValuesCompany4.put("Sector","Technology Services");
        contentValuesCompany4.put("CompanyEthicalScore",26);
        contentValuesCompany4.put("CompanyMaxScrutiny",4);
        contentValuesCompany4.put("CompanyRiskLevel",2);

        contentValuesCompany5.put("CompanyName","Logitech International SA");
        contentValuesCompany5.put("Sector","Consumer Technology");
        contentValuesCompany5.put("CompanyDescription","Logitech products are distributed in more than 100 countries worldwide via various retail channels as well as through the company's strategic partnerships with top-tier PC manufacturers. Logitech has many brands, including: Logitech, Ultimate Ears, Jaybird, Blue Microphones, ASTRO Gaming and Logitech G. Logitech Int. has more than 7000 employees worldwide.");
        contentValuesCompany5.put("CompanyEthicalScore",20);
        contentValuesCompany5.put("CompanyMaxScrutiny",3);
        contentValuesCompany5.put("CompanyRiskLevel",3);

        contentValuesCompany6.put("CompanyName","Corsair Gaming Inc.");
        contentValuesCompany6.put("Sector","Consumer Technology");
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
        contentValuesUser.put("IsConfigured", 0);
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
    public Boolean addToPortfolio(String tickerSymbol, Integer userID){
        ContentValues cv = new ContentValues();
        cv.put("UserID", userID);
        cv.put("CompanyTicker",tickerSymbol);
        //Returns the row index of the newly added row.
        long res = db.insert("Portfolio",null,cv);
        //If error then false, else true.
        if (res == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public void removeFromPortfolio(String ticker, int userID){
        String userIDStr = String.valueOf(userID);
        db.delete("Portfolio","CompanyTicker = ? AND userID = ?", new String[]{ticker, userIDStr});
    }

/*
* Get the Company details from a cursor object on the Company table.
* @return a Company object with all details from the cursor record.*/
    public Company getCompanyDetails(Cursor c){
        Company companyDetails = new Company();
        companyDetails.setTickerSymbol(getDbValue(c,"TickerSymbol"));

        companyDetails.setName(getDbValue(c,"CompanyName"));

        companyDetails.setSector(getDbValue(c,"Sector"));

        if( getDbValue(c,"CompanyEthicalScore") == null){
            companyDetails.setEthicalScore(0);
        }
        else{
            companyDetails.setEthicalScore(Integer.valueOf(getDbValue(c,"CompanyEthicalScore")));
        }

        if(getDbValue(c, "LastSaleUSD") == null){
            companyDetails.setCurrentValue(0);
        }
        else {
            companyDetails.setCurrentValue(Float.valueOf(getDbValue(c, "LastSaleUSD")));
        }

        if(getDbValue(c,"NetChange") == null){
            companyDetails.setNetChange(0);
        }
        else{
            companyDetails.setNetChange(Float.valueOf(getDbValue(c,"NetChange")));
        }

        String inPortStr = getDbValue(c,"InPortfolio");
        if (inPortStr != null && inPortStr.equals("1")){
            companyDetails.setInPortfolio(true);
        }
        else{
            companyDetails.setInPortfolio(false);
        }
        return companyDetails;
    }
    public ArrayList<Company> getPortfolio(Integer userID){
        if(userID < 0){
            return null;
        }
        SQLiteDatabase db = this.getWritableDatabase();
        Log.i("User ID", String.valueOf(userID));

        Cursor c = db.rawQuery("SELECT * FROM PORTFOLIO INNER JOIN COMPANY ON PORTFOLIO.CompanyTicker = COMPANY.TickerSymbol WHERE userid = ?  ORDER BY TickerSymbol, AmountInvested, AmountProfit DESC", new String[]{String.valueOf(userID)});
        Log.i("Num records", String.valueOf(c.getCount()));
        if (c.getCount() > 0) {
            //Intialise the cursor position for reading data.
            c.moveToPosition(0);

            //All company details, with pseudoID as the key
            ArrayList<Company> companyList = new ArrayList<Company>();

            //while there are records left in the cursor
            while (c.getPosition() < c.getCount()){
                //Single company details
                Company companyDetails = getCompanyDetails(c);

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
    /*
    Return a value from a cursor when given a column name.
    @param c a cursor object
    @param columnName the database table column name you are accessing.*/
    public String getDbValue(Cursor c, String columnName){

        Integer labelIdx = c.getColumnIndex(columnName);
        if (labelIdx >= 0){
            //As per https://developer.android.com/reference/android/database/Cursor#FIELD_TYPE_FLOAT
            switch (c.getType(labelIdx)) {
                //Int
                case 1:
                    return String.valueOf(c.getInt(labelIdx));
                //Float
                case 2:
                    DecimalFormat df = new DecimalFormat("#");
                    df.setMaximumFractionDigits(2);
                    return String.format("%.2f",c.getFloat(labelIdx));
                //String
                case 3:
                    return c.getString(labelIdx);
                //Blob
                case 4:
                    return String.valueOf(c.getBlob(labelIdx));

            }
        }

        //No valid column found
        return null;
    }

    public boolean isProfileConfigured(int userID){
        SQLiteDatabase db = this.getWritableDatabase();
        String userIDStr = String.valueOf(userID);
        Cursor c = db.rawQuery("SELECT IsConfigured FROM USER WHERE UserID = ?  ", new String[]{String.valueOf(userIDStr)});
        c.moveToPosition(0);
        if (c.getInt(0) == 0 || isNull(c.getInt(0))){
            return false;
        }
        else if (c.getInt(0) == 1){
            return true;
        }
        //catch erroneous records where IsConfigured is not represented by a boolean (1 or 0)

        return false;

    }
    public HashMap<String, Double> getProfile(Integer userID){
        HashMap<String, Double> profile = new HashMap<>();
        String userIDStr = String.valueOf(userID);
        Cursor c = db.rawQuery("SELECT * FROM profile WHERE UserID = ? ", new String[]{userIDStr});
        c.moveToPosition(0);
        Double scrutiny = Double.valueOf(getDbValue(c, "InvestmentScrutinyLevel"));
        Double risk = Double.valueOf(getDbValue(c, "InvestmentRiskLevel"));
        Double amount = Double.valueOf(getDbValue(c, "InvestmentAmount"));
        Double duration = Double.valueOf(getDbValue(c, "InvestmentDurationMonths"));

        profile.put("InvestmentScrutinyLevel",scrutiny);
        profile.put("InvestmentRiskLevel",risk);
        profile.put("InvestmentAmount",amount);
        profile.put("InvestmentDuration",duration);

        return profile;




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
        Cursor c = db.rawQuery("SELECT TickerSymbol,CompanyName,Sector,Industry, CompanyEthicalScore FROM COMPANY WHERE TickerSymbol NOT IN (SELECT CompanyTicker FROM PORTFOLIO WHERE UserID = ?)" +
                " AND CompanyMaxScrutiny >= (SELECT InvestmentScrutintyLevel FROM Portfolio WHERE UserID = ?)", new String[]{userIDStr});


        return new ArrayList<Company>();
    }


    private ArrayList<Company> compileCompanyList(Cursor c){
        ArrayList<Company> companyList = new ArrayList<Company>();

        c.moveToPosition(0);
        while (c.getPosition() < c.getCount()){
            Company companyDetails = getCompanyDetails(c);

            companyList.add(companyDetails);
            c.moveToNext();
        }
        return companyList;
    }
/*
* Get a list of all the companies available in the database.
* */
    public ArrayList<Company> getAllCompanies(int userID){
        String userIDStr = String.valueOf(userID);
        //Get All companies and mark the relevant ones as InPortfolio for the current user ?
        Cursor c = db.rawQuery("SELECT c.*, ifnull((SELECT 1 FROM Portfolio p WHERE p.UserID = ? AND p.CompanyTicker = c.TickerSymbol) ,0) AS InPortfolio FROM COMPANY c;", new String[]{userIDStr});

        ArrayList<Company> companyList = compileCompanyList(c);
        return companyList;
    }

    public ArrayList<Company> getCompanyByTicker(String tickerSymbol, int userID){
        String userIDStr = String.valueOf(userID);
        //Get All companies and mark the relevant ones as InPortfolio for the current user ?
        Cursor c = db.rawQuery("SELECT c.*, ifnull((SELECT 1 FROM Portfolio p WHERE p.UserID = ? AND p.CompanyTicker = c.TickerSymbol) ,0) AS InPortfolio FROM COMPANY c WHERE c.TickerSymbol = ?;", new String[]{userIDStr, tickerSymbol});

        ArrayList<Company> companyList = compileCompanyList(c);

        return companyList;

    }

    public ArrayList<Company> getCompaniesByPartialMatch(String searchTerm, int userID){
        String userIDStr = String.valueOf(userID);
        searchTerm = searchTerm + "%";
        Cursor c = db.rawQuery("SELECT c.*, ifnull((SELECT 1 FROM Portfolio p WHERE p.UserID = ? AND p.CompanyTicker = c.TickerSymbol) ,0) AS InPortfolio FROM COMPANY c WHERE TickerSymbol LIKE ? OR CompanyName LIKE ?", new String[]{userIDStr, searchTerm,searchTerm});
        ArrayList<Company> companyList = compileCompanyList(c);

        return companyList;
    }
    public ArrayList<CompanyArticle>getCompanyArticles(String tickerSymbol){
        ArrayList<CompanyArticle> articles = new ArrayList<CompanyArticle>();
        Cursor c = db.rawQuery("SELECT Title, PageURL, CountEnvironmentallyFriendlyPractices, CountFairWorkforceTreatment, CountReinvestmentOfProfitsInLocalCommunities FROM CompanyScrapedData WHERE CompanyTicker = ?", new  String[]{tickerSymbol});
        c.moveToPosition(0);
        while (c.getPosition() < c.getCount()) {
            String title = getDbValue(c, "Title");
            String pageURL = getDbValue(c, "");
            int countEnvWords = Integer.valueOf(getDbValue(c, "CountEnvironmentallyFriendlyPractices"));
            int countFairWords = Integer.valueOf(getDbValue(c, "CountFairWorkforceTreatment"));
            int countReinvestmentWords = Integer.valueOf(getDbValue(c, "CountReinvestmentOfProfitsInLocalCommunities"));

            CompanyArticle a = new CompanyArticle(title, pageURL, countEnvWords, countFairWords, countReinvestmentWords);
            articles.add(a);
            c.moveToNext();
        };
        if (!articles.isEmpty()){
            return articles;
        }

        return null;
    }

    public String constructEthicalScoreQuery(String tickerSymbol, int depth){
        String query = "SELECT";
        String calc = "(c1.CompanyEthicalScore";
        String fromClause = "FROM CompanyRelationships ";
        String whereClause = "WHERE TickerSymbol = " + tickerSymbol;
        for(int i=0; i<=depth;i++){
            String columnName = "Company" + String.valueOf(i);
            String tableAlias = "c" + String.valueOf(i);
            query = calc + "," + tableAlias + ".CompanyEthicalScore/" + String.valueOf(i);

            fromClause += "INNER JOIN Company " + tableAlias +
                    " ON CompanyRelationships.Company" + String.valueOf(i) + " = " + tableAlias + ".TickerSymbol";
        }
        calc +=")/depth EthicalScoreDerived";

        query = query + calc + fromClause;
        System.out.println(query);
        return query;
    }

}




















