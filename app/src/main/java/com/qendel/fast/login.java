package com.qendel.fast;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;



public class login extends AppCompatActivity {

    EditText editText_username_log, editText_pass_log;
    DB_sqlite db;
    String save_data;
    CheckBox checkBox_save_data;
    String Check;
    String type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Intent data = getIntent();
        type = data.getExtras().getString("type") ;

        editText_username_log = (EditText) findViewById(R.id.editText_username_log);
        editText_pass_log = (EditText) findViewById(R.id.editText_pass_log);
        checkBox_save_data = (CheckBox) findViewById(R.id.checkBox);

        db = new DB_sqlite(this);

        if (db.get_Check() != null) {
            Check = db.get_Check();
            if (Check.equals("1")) {

                editText_username_log.setText(db.get_username());
                editText_pass_log.setText(db.get_Password());
                checkBox_save_data.setChecked(true);
                save_data = "1";

                if(type.equals("1")) {
                    startActivity(new Intent(login.this, Main2Activity.class));
                }else if (type.equals("0")){
                    startActivity(new Intent(login.this, Main5Activity.class));
                }


            } else {

                checkBox_save_data.setChecked(false);
                save_data = "0";
            }

            checkBox_save_data.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        save_data = "1";
                    } else {
                        save_data = "0";
                    }
                }
            });


        }

    }

    public void onBackPressed() {

        finish();
        super.onBackPressed();
    }

    public void login2(View view) {
        if(type.equals("1")) {
            login_employer();
        }else if (type.equals("0")){
            login_user();
        }
    }

    public void sign_up(View view) {

        if(type.equals("1")) {
            Intent Opennovel = new Intent(login.this, Registeration.class);
            Opennovel.putExtra("type",type);
            startActivity(Opennovel);
        }else if (type.equals("0")){
            Intent Opennovel = new Intent(login.this, Registeration2.class);
            Opennovel.putExtra("type",type);
            startActivity(Opennovel);
        }


    }

    public void login_employer(){
        final String Log_in_name = editText_username_log.getText().toString().trim();
        final String Log_in_Password = editText_pass_log.getText().toString().trim();

        Response.Listener <String> responseLisener = new Response.Listener <String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        Toast.makeText(login.this, "تم  تسجيل الدخول", Toast.LENGTH_SHORT).show();
                        db.updateData_Login(Log_in_name, Log_in_Password, save_data);
                        db.update_save("0");
                        startActivity(new Intent(login.this, Main2Activity.class));
                    } else {
                        Toast.makeText(login.this, "البيانات غير صحيحة", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        Send_Data_Login send_Data = new Send_Data_Login(Log_in_name, Log_in_Password, responseLisener);
        RequestQueue queue = Volley.newRequestQueue(login.this);
        queue.add(send_Data);
    }

    public void login_user(){
        final String Log_in_name = editText_username_log.getText().toString().trim();
        final String Log_in_Password = editText_pass_log.getText().toString().trim();

        Response.Listener <String> responseLisener = new Response.Listener <String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        Toast.makeText(login.this, "تم  تسجيل الدخول", Toast.LENGTH_SHORT).show();
                        db.updateData_Login(Log_in_name, Log_in_Password, save_data);
                        db.update_save("contactf");
                        startActivity(new Intent(login.this, Main5Activity.class));

                    } else {
                        Toast.makeText(login.this, "البيانات غير صحيحة", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        Send_Data_Login2 send_Data = new Send_Data_Login2(Log_in_name, Log_in_Password, responseLisener);
        RequestQueue queue = Volley.newRequestQueue(login.this);
        queue.add(send_Data);
    }
}
