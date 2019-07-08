package com.qendel.fast;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String type,selectt;
    DB_sqlite db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        db = new DB_sqlite(this);
        selectt = db.get_type();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();



        if (id == R.id.nav_Tasker ) {
            startActivity(new Intent(Main2Activity.this, Profile.class));
        } else if (id == R.id.nav_Setting) {

        } else if (id == R.id.nav_Notification) {
            startActivity(new Intent(Main2Activity.this, Notification.class));
        } else if (id == R.id.nav_Call_Center) {

        } else if (id == R.id.nav_help) {

        } else if (id == R.id.nav_share) {
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("text/plain");
            share.putExtra(Intent.EXTRA_TEXT, getString(R.string.app_name) + "\n" + "https://play.google.com/store/apps/details?id=com.qendel.fast");
            if (share.resolveActivity(getPackageManager()) != null) {
                startActivity(Intent.createChooser(share, "مشاركة التطبيق"));
            }
        } else if (id == R.id.nav_send) {
            String[] to = {"qendelfast@gmail.com"};
            Intent sendemail = new Intent(Intent.ACTION_SEND);
            sendemail.putExtra(Intent.EXTRA_EMAIL, to);
            sendemail.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
            sendemail.putExtra(Intent.EXTRA_TEXT, "السلام عليكم ورحمة الله وبركاته :");
            sendemail.setType("message/rfc822");
            if (sendemail.resolveActivity(getPackageManager()) != null) {
                startActivity(Intent.createChooser(sendemail, "Send Email"));
            }
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void search(View view) {
        Intent Opennovel = new Intent(Main2Activity.this, search.class);
        startActivity(Opennovel);
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
        Intent Opennovel = new Intent(Main2Activity.this, services2.class);
        Opennovel.putExtra("type",type);
        startActivity(Opennovel);
    }
}
