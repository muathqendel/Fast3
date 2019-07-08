package com.qendel.fast;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class sure extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sure);
    }
    public void onBackPressed() {

        Intent Opennovel = new Intent(sure.this, login.class);
        startActivity(Opennovel);
        finish();
        super.onBackPressed();
    }

}
