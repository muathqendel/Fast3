package com.qendel.fast;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

import android.support.v7.app.AppCompatActivity;

public class Registeration2 extends AppCompatActivity {

    EditText editText_username_r, editText_email_r, editText_pass_r, editText_pass2_r, editText_ip;
    DB_sqlite db;
    String save_data;
    CheckBox checkBox_save_data_r;
    String Check;
    String type;
    ImageView imageView;
    String imagecode;

    RadioGroup radioGroup;
    RadioButton radioButton;
    String sex = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration2);


        imageView = (ImageView) findViewById(R.id.img_user);
        checkBox_save_data_r = (CheckBox) findViewById(R.id.checkBox_save_data_r);
        editText_username_r = (EditText) findViewById(R.id.editText_username_r);
        editText_email_r = (EditText) findViewById(R.id.editText_email_r);
        editText_pass_r = (EditText) findViewById(R.id.editText_pass_r);
        editText_pass2_r = (EditText) findViewById(R.id.editText_pass2_r);
        editText_ip = (EditText) findViewById(R.id.editText_ip);

        radioGroup = findViewById(R.id.radio_group);



        db = new DB_sqlite(this);
        checkBox_save_data_r.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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

    public void sign_up2(View view) {

        final String Ename = editText_username_r.getText().toString().trim();
        final String Email = editText_email_r.getText().toString().trim();
        final String Pass = editText_pass_r.getText().toString().trim();
        final String ip = editText_ip.getText().toString().trim();
        String Pass2 = editText_pass2_r.getText().toString().trim();



        /// to convert image to string
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream); /// 100 % quality from image
        imagecode = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);



        if (!Pass.equals(Pass2)) {

            Toast.makeText(Registeration2.this, "Please write password correctly", Toast.LENGTH_LONG).show();

        }else if(Ename.equals("")) {

            Toast.makeText(Registeration2.this, "please write your username", Toast.LENGTH_LONG).show();

        }
        else if(Email.equals("")) {

            Toast.makeText(Registeration2.this, "please write your email", Toast.LENGTH_LONG).show();
        }
        else if(!Email.contains("@")) {

            Toast.makeText(Registeration2.this, "please choose correct email", Toast.LENGTH_LONG).show();
        }
        else if(Pass.equals("")) {

            Toast.makeText(Registeration2.this, "please write your password", Toast.LENGTH_LONG).show();
        }else if(ip.length() != 10 && ip.length() != 12 && ip.length() != 14 ){

            Toast.makeText(Registeration2.this, "Please write Phone Number correctly", Toast.LENGTH_LONG).show();

        }else if(editText_pass_r.length() < 8){

            Toast.makeText(Registeration2.this, "the password must have 8 Symbols at least", Toast.LENGTH_LONG).show();

        }else if(sex.equals("")) {

            Toast.makeText(Registeration2.this, "please select your gender", Toast.LENGTH_LONG).show();

        }else {


            Response.Listener <String> responseLisener = new Response.Listener <String>() {

                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");
                        if (success) {
                            Toast.makeText(Registeration2.this, "successfully registered", Toast.LENGTH_LONG).show();
                            db.updateData_R2(Ename, Pass, save_data, Email,ip,sex);
                            db.update_save("1");
                            Intent Opennovel = new Intent(Registeration2.this, login.class);
                            Opennovel.putExtra("type", type);
                            startActivity(Opennovel);
                            finish();
                        } else {
                            Toast.makeText(Registeration2.this, "Excuse me! There is an error or username already exists", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };

            Send_Data_Registration2 send_Data = new Send_Data_Registration2(Ename, Email, Pass, ip, imagecode, sex,  responseLisener);
            RequestQueue queue = Volley.newRequestQueue(Registeration2.this);
            queue.add(send_Data);
        }

    }


    public void Sign_in(View view) {
        Intent Opennovel = new Intent(Registeration2.this, login.class);
        Opennovel.putExtra("type",type);
        startActivity(Opennovel);
    }

    public void isertimg(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,100);
    }

    //// to show this image in imageView
    protected void onActivityResult (int requstcode, int reslutcode , Intent data){
        super.onActivityResult(requstcode,reslutcode,data);

        if(requstcode == 100 && reslutcode == RESULT_OK ){
            Uri uri = data.getData();
            imageView.setImageURI(uri);
        }
    }

    public void checkRadiio(View view) {
        int RadioId = radioGroup.getCheckedRadioButtonId();

        radioButton = findViewById(RadioId);

        String radioText =  radioButton.getText().toString();

        if(radioText.equals("Female")){
            sex = "female";
        }else if (radioText.equals("Male")){
            sex= "male" ;
        }

    }
}
