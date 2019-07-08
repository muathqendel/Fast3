package com.qendel.fast;

/**
 * Created by hp on 28/08/2018.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DB_sqlite extends SQLiteOpenHelper {
    public static final String BDname = "mdata.db";

    public DB_sqlite(Context context) {
        super(context, BDname, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table mytable ( id INTEGER PRIMARY KEY AUTOINCREMENT,idd  TEXT,username TEXT,password TEXT," +
                " savedata TEXT, email TEXT, type TEXT, adress TEXT, phone TEXT, gender TEXT,info TEXT,img TEXT,save TEXT ,alldata TEXT )");
        db.execSQL("create table notfiy (id INTEGER PRIMARY KEY AUTOINCREMENT, numb INTEGER,enableb INTEGER,numn INTEGER,enablen INTEGER)");



        ContentValues contentValues = new ContentValues();
        contentValues.put("username", "null");
        contentValues.put("password", "null");
        contentValues.put("savedata", "0");
        contentValues.put("email", "null");
        contentValues.put("type", "null");
        contentValues.put("adress", "null");
        contentValues.put("phone", "null");
        contentValues.put("gender", "null");
        contentValues.put("save", "0");

        contentValues.put("info", "null");
        contentValues.put("alldata", "0");
        contentValues.put("img", "null");
        contentValues.put("idd", "null");
        db.insert("mytable", null, contentValues);

        ContentValues contentValues1 = new ContentValues();
        contentValues1.put("numb",0);
        contentValues1.put("enableb",0);
        contentValues1.put("numn",0);
        contentValues1.put("enablen",0);
        db.insert("notfiy",null ,contentValues1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS mytable");
        db.execSQL("DROP TABLE IF EXISTS notfiy");
        onCreate(db);

    }


    public String get_Check() {
        String Check;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select savedata from mytable ", null);
        res.moveToFirst();
        Check = res.getString(res.getColumnIndex("savedata"));
        return Check;
    }

    public String get_username() {
        String username;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select username from mytable ", null);
        res.moveToFirst();
        username = res.getString(res.getColumnIndex("username"));
        return username;
    }

    public String get_Password() {
        String Password;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select password from mytable ", null);
        res.moveToFirst();
        Password = res.getString(res.getColumnIndex("password"));
        return Password;
    }

    public String get_email() {
        String email;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select email from mytable ", null);
        res.moveToFirst();
        email = res.getString(res.getColumnIndex("email"));
        return email;
    }


    public String get_type() {
        String type;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select type from mytable ", null);
        res.moveToFirst();
        type = res.getString(res.getColumnIndex("type"));
        return type;
    }

    public String get_adress() {
        String adress;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select adress from mytable ", null);
        res.moveToFirst();
        adress = res.getString(res.getColumnIndex("adress"));
        return adress;
    }

    public String get_gender() {
        String gender;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select gender from mytable ", null);
        res.moveToFirst();
        gender = res.getString(res.getColumnIndex("gender"));
        return gender;
    }

    public String get_alldata() {
        String alldata;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select alldata from mytable ", null);
        res.moveToFirst();
        alldata = res.getString(res.getColumnIndex("alldata"));
        return alldata;
    }

    public String get_info() {
        String info;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select info from mytable ", null);
        res.moveToFirst();
        info = res.getString(res.getColumnIndex("info"));
        return info;
    }

    public String get_img() {
        String img;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select img from mytable ", null);
        res.moveToFirst();
        img = res.getString(res.getColumnIndex("img"));
        return img;
    }

    public String get_idd() {
        String idd;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select idd from mytable ", null);
        res.moveToFirst();
        idd = res.getString(res.getColumnIndex("idd"));
        return idd;
    }


    public String get_phone() {
        String phone;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select phone from mytable ", null);
        res.moveToFirst();
        phone = res.getString(res.getColumnIndex("phone"));
        return phone;
    }



    public String get_save() {
        String save;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select save from mytable ", null);
        res.moveToFirst();
        save = res.getString(res.getColumnIndex("save"));
        return save;
    }




    public boolean updateData_R(String name, String password, String check_save_data, String email,
                                String adress, String phone, String gender) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", name);
        contentValues.put("password", password);
        contentValues.put("savedata", check_save_data);
        contentValues.put("email", email);
        contentValues.put("adress", adress);
        contentValues.put("phone", phone);
        contentValues.put("gender", gender);
        db.update("mytable", contentValues, "id= ?", new String[]{"1"});

        return true;
    }

    public boolean updateData_R2(String name, String password, String check_save_data, String email,
                                 String phone, String gender) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", name);
        contentValues.put("password", password);
        contentValues.put("savedata", check_save_data);
        contentValues.put("email", email);
        contentValues.put("phone", phone);
        contentValues.put("gender", gender);
        db.update("mytable", contentValues, "id= ?", new String[]{"1"});

        return true;
    }

    public boolean updateData_Login(String name, String password, String check_save_data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", name);
        contentValues.put("password", password);
        contentValues.put("savedata", check_save_data);
        db.update("mytable", contentValues, "id= ?", new String[]{"1"});

        return true;
    }

    public boolean logout () {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", "null");
        contentValues.put("password", "null");
        contentValues.put("savedata", "0");
        contentValues.put("type", "null");
        contentValues.put("save", "0");
        contentValues.put("alldata", "0");
        db.update("mytable", contentValues, "id= ?", new String[]{"1"});

        return true;
    }

    public boolean save_all_data (String idd,String name,String password, String email, String adress, String phone, String img,
                                  String info,String type,String gender) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("idd", idd);
        contentValues.put("username", name);
        contentValues.put("password", password);
        contentValues.put("info", info);
        contentValues.put("type", type);
        contentValues.put("email", email);
        contentValues.put("adress", adress);
        contentValues.put("phone", phone);
        contentValues.put("img", img);
        contentValues.put("gender", gender);
        db.update("mytable", contentValues, "id= ?", new String[]{"1"});

        return true;
    }

    public boolean update_type(String type){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("type",type);
        db.update("mytable",contentValues,"id= ?",new String[]{"1"});

        return  true ;
    }

    public boolean update_alldata(String alldata){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("alldata",alldata);
        db.update("mytable",contentValues,"id= ?",new String[]{"1"});

        return  true ;
    }


    public boolean update_save(String save){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("save",save);
        db.update("mytable",contentValues,"id= ?",new String[]{"1"});

        return  true ;
    }





    public int get_numb() {
        int num ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select numb from notfiy ", null);
        res.moveToFirst();
        num = res.getInt(res.getColumnIndex("numb"));
        return num;
    }


    public boolean updateData_notfiy_numb(int num ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues1 = new ContentValues();
        contentValues1.put("numb", num);


        db.update("notfiy", contentValues1, "id= ?", new String[]{"1"});

        return true;
    }



}
