package com.qendel.fast;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

public class add_img extends AppCompatActivity {




    ImageView imageView;
    String imagecode,idd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_img);

        Intent data = getIntent();
        idd = data.getExtras().getString("id") ;


        imageView = findViewById(R.id.imageView);

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

    public void cansel(View view) {
        Intent Opennovel = new Intent(add_img.this, Profile.class);
        startActivity(Opennovel);
    }

    public void add(View view) {


        /// to convert image to string
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream); /// 100 % quality from image
        imagecode = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);


        Response.Listener <String> responseLisener = new Response.Listener <String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        Toast.makeText(add_img.this, "تم اضافة الصورة", Toast.LENGTH_LONG).show();
                        Intent Opennovel = new Intent(add_img.this, Profile.class);
                        startActivity(Opennovel);
                        finish();
                    } else {
                        Toast.makeText(add_img.this, "لم يتم اضافة الصورة حاول مرة اخري ...", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        Send_Data_img send_Data = new Send_Data_img(idd , imagecode,  responseLisener);
        RequestQueue queue = Volley.newRequestQueue(add_img.this);
        queue.add(send_Data);
    }

    public void change(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,100);
    }
}
