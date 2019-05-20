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
        super(context, BDname, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table mytable ( id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT,password TEXT," +
                " savedata TEXT, email TEXT, type TEXT, adress TEXT, phone TEXT, gender TEXT,save TEXT)");


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
        db.insert("mytable", null, contentValues);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS mytable");
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

    public boolean update_type(String type){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("type",type);
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


}
