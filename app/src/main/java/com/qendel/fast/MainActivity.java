package com.qendel.fast;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




    }

    public void employer(View view) {
        Intent Opennovel = new Intent(MainActivity.this, login.class);
        Opennovel.putExtra("type","1");
        startActivity(Opennovel);
    }

    public void user(View view) {
        Intent Opennovel = new Intent(MainActivity.this, login.class);
        Opennovel.putExtra("type","0");
        startActivity(Opennovel);
    }
}
