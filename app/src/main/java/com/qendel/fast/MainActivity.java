package com.qendel.fast;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    DB_sqlite db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DB_sqlite(this);

         if(!db.get_type().equals("null")){
          Intent Opennovel = new Intent(MainActivity.this, login.class);
          startActivity(Opennovel);
        }

    }

    public void employer(View view) {
        db.update_type("1");
        Intent Opennovel = new Intent(MainActivity.this, login.class);
        //Opennovel.putExtra("type","1");
        startActivity(Opennovel);
    }

    public void user(View view) {
        db.update_type("0");
        Intent Opennovel = new Intent(MainActivity.this, login.class);
        //Opennovel.putExtra("type","0");
        startActivity(Opennovel);
    }
}
