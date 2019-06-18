package com.qendel.fast;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class services extends AppCompatActivity {

    String type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);
    }

    public void search(View view) {
        type = "search";
        go_services();
    }

    public void home_develope(View view) {
        type = "develope";
        go_services();
    }

    public void home_services(View view) {
        type = "home_services";
        go_services();
    }

    public void destrial(View view) {
        type = "destrial";
        go_services();
    }

    public void handel(View view) {
        type = "handel";
        go_services();
    }

    public void transform(View view) {
        type = "transform";
        go_services();
    }

    public void clean(View view) {
        type = "clean";
        go_services();
    }

    public void go_services(){
        Intent Opennovel = new Intent(services.this, services2.class);
        Opennovel.putExtra("type",type);
        startActivity(Opennovel);
    }
}
