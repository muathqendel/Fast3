package com.qendel.fast;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

public class Become extends AppCompatActivity {

    CheckBox checkBox_sign;

    EditText editText_info;
    TextView textView_choose,textView_choose2;
    String choose,choose1,choose3,choose4;
    String sure= "0";
    DB_sqlite db;
    private ProgressDialog progressDialog;
    RequestQueue requestQueue;
    String username,phone,email,location,info,img,id,passs,genderr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_become);

        editText_info = findViewById(R.id.editText_info);
        textView_choose = findViewById(R.id.textView_choose);
        textView_choose2 = findViewById(R.id.textView_choose2);
        checkBox_sign = (CheckBox) findViewById(R.id.checkBox_sign);

        db = new DB_sqlite(this);
        progressDialog = new ProgressDialog(this);


        checkBox_sign.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    sure= "1";
                } else {
                    sure= "0";
                }
            }
        });

        if(db.get_alldata().equals("0")){
            get_data_user();
        }
    }

    public void Become(View view){

        final String info = editText_info.getText().toString().trim();



        if(choose.equals("")){

            Toast.makeText(Become.this, "please Write your Work", Toast.LENGTH_LONG).show();

        } else if(choose3.equals("")){

            Toast.makeText(Become.this, "please Write your Work", Toast.LENGTH_LONG).show();

        } else if(info.equals("")){

            Toast.makeText(Become.this, "please Write your description", Toast.LENGTH_LONG).show();

        } else if(sure.equals("0")) {

            Toast.makeText(Become.this, "please check the Terms and Conditions", Toast.LENGTH_LONG).show();
        }else {


            Response.Listener <String> responseLisener = new Response.Listener <String>() {

                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");
                        if (success) {
                            Toast.makeText(Become.this, "successfully registered", Toast.LENGTH_LONG).show();
                            Intent Opennovel = new Intent(Become.this, sure.class);
                            startActivity(Opennovel);
                            finish();
                        } else {
                            Toast.makeText(Become.this, "Excuse me! There is an error or username already exists", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };

            Send_Data_Registration send_Data = new Send_Data_Registration(username, email, passs, phone, img, location, genderr,choose3,info,  responseLisener);
            RequestQueue queue = Volley.newRequestQueue(Become.this);
            queue.add(send_Data);
        }
    }

    public void choose(View view) {
        PopupMenu popupMenu = new PopupMenu(Become.this,textView_choose);
        popupMenu.getMenuInflater().inflate(R.menu.menu_choose,popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                int id = item.getItemId();



                if(id == R.id.handel) {
                    choose = "handel";
                    choose1 = "الاعمال اليدوية";
                }else if (id == R.id.transform){
                    choose = "transform";
                    choose1 = "النقل";
                }else if (id == R.id.clean){
                    choose = "clean";
                    choose1 = "التنظيف";
                }else if (id == R.id.home_develope){
                    choose = "home_develope";
                    choose1 = "تحسينات";
                }else if (id == R.id.home_services){
                    choose = "home_services";
                    choose1 = "خدمات منزلبة";
                }else if (id == R.id.destrial){
                    choose = "destrial";
                    choose1 = "المهن الصناعية";
                }

                textView_choose2.setVisibility(View.VISIBLE);
                textView_choose.setText(choose1);

                return true;
            }
        });
        popupMenu.show();
    }

    public void choose2(View view) {
        switch (choose) {
            case "handel": {
                PopupMenu popupMenu = new PopupMenu(Become.this, textView_choose);
                popupMenu.getMenuInflater().inflate(R.menu.popupmenu_handel, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        int id = item.getItemId();

                        if (id == R.id.Home_Kitchen) {
                            choose3 = "Home_Kitchen";
                            choose4 = "مطبخ منزلي";
                        } else if (id == R.id.embroidery) {
                            choose3 = "embroidery";
                            choose4 = "تطريز";
                        } else if (id == R.id.draw) {
                            choose3 = "draw";
                            choose4 = "رسم";
                        } else if (id == R.id.Hannah) {
                            choose3 = "Hannah";
                            choose4 = "حنة";
                        } else if (id == R.id.sewing) {
                            choose3 = "sewing";
                            choose4 = "خياطة";
                        } else if (id == R.id.Gifts) {
                            choose3 = "Gifts";
                            choose4 = "هدايا";
                        } else if (id == R.id.other_h) {
                            choose3 = "other_h";
                            choose4 = "أخرى";
                        }


                        return true;
                    }
                });
                popupMenu.show();
                break;
            }
            case "transform": {
                PopupMenu popupMenu = new PopupMenu(Become.this, textView_choose);
                popupMenu.getMenuInflater().inflate(R.menu.popupmenu_transform, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        int id = item.getItemId();

                        if (id == R.id.Transportation_passengers) {
                            choose3 = "Transportation_passengers";
                            choose4 = "نقل ركاب";
                        } else if (id == R.id.Transportation_parcels) {
                            choose3 = "Transportation_parcels";
                            choose4 = "نقل طرود";
                        }

                        return true;
                    }
                });
                popupMenu.show();
                break;
            }
            case "clean": {
                PopupMenu popupMenu = new PopupMenu(Become.this, textView_choose);
                popupMenu.getMenuInflater().inflate(R.menu.popupmenu_clean, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        int id = item.getItemId();

                        if (id == R.id.Cleaning_houses) {
                            choose3 = "Cleaning_houses";
                            choose4 = "تنظيف منازل";
                        } else if (id == R.id.Car_Cleaning) {
                            choose3 = "Car_Cleaning";
                            choose4 = "تنظيف سيارات";
                        } else if (id == R.id.Cleaning_garden) {
                            choose3 = "Cleaning_garden";
                            choose4 = "تنظيف حدائق";
                        } else if (id == R.id.Cleaning_of_residential_communities) {
                            choose3 = "Cleaning_of_residential_communities";
                            choose4 = "تنظيف تجمعات سكنية";
                        } else if (id == R.id.other_c) {
                            choose3 = "other_c";
                            choose4 = "أخرى";
                        }


                        return true;
                    }
                });
                popupMenu.show();
                break;
            }
            case "home_develope": {
                PopupMenu popupMenu = new PopupMenu(Become.this, textView_choose);
                popupMenu.getMenuInflater().inflate(R.menu.popupmenu_home_develope, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        int id = item.getItemId();

                        if (id == R.id.Car_Services) {
                            choose3 = "Car_Services";
                            choose4 = "خدمات سيارات";
                        } else if (id == R.id.Home_Improvement) {
                            choose3 = "Home_Improvement";
                            choose4 = "تحسين منازل";
                        } else if (id == R.id.Care) {
                            choose3 = "Care";
                            choose4 = "العناية والرعاية";
                        } else if (id == R.id.educational_services) {
                            choose3 = "educational_services";
                            choose4 = "خدمات تعلمية";
                        }


                        return true;
                    }
                });
                popupMenu.show();
                break;
            }
            case "home_services": {
                PopupMenu popupMenu = new PopupMenu(Become.this, textView_choose);
                popupMenu.getMenuInflater().inflate(R.menu.popupmenu_home_services, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        int id = item.getItemId();

                        if (id == R.id.Moving_furniture) {
                            choose3 = "Moving_furniture";
                            choose4 = "نقل اثاث";
                        } else if (id == R.id.Decorations) {
                            choose3 = "Decorations";
                            choose4 = "ديكورات";
                        } else if (id == R.id.Care_elderly) {
                            choose3 = "Care_elderly";
                            choose4 = "رعاية مسنين";
                        }


                        return true;
                    }
                });
                popupMenu.show();
                break;
            }
            case "destrial":
                PopupMenu popupMenu = new PopupMenu(Become.this,textView_choose);
                popupMenu.getMenuInflater().inflate(R.menu.popupmenu_home_destrial,popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        int id = item.getItemId();

                        if(id == R.id.Carpentry) {
                            choose3 = "Carpentry";
                            choose4 = "نجاره";
                        }else if (id == R.id.paint){
                            choose3 = "paint";
                            choose4 = "دهان";
                        }else if (id == R.id.Electricity){
                            choose3 = "Electricity";
                            choose4 = "فني كهرباء";
                        }else if (id == R.id.Sanitary_extensions){
                            choose3 = "Sanitary_extensions";
                            choose4 = "فني تمديدات صحية";
                        }else if (id == R.id.other_d){
                            choose3 = "other_d";
                            choose4 = "اخرى";
                        }


                        return true;
                    }
                });
                popupMenu.show();
                break;
        }


        editText_info.setVisibility(View.VISIBLE);
        textView_choose2.setText(choose4);

    }

    public void get_data_user(){
        requestQueue = Volley.newRequestQueue(this);

        CheckInternetConnection cic = new CheckInternetConnection(getApplicationContext());
        Boolean Ch = cic.isConnectingToInternet();
        if (!Ch) {
            Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        } else {
            progressDialog.setMessage(getString(R.string.Waite));
            progressDialog.setCancelable(true);
            progressDialog.show();
// عدلها ل user2 من السيرفر
            String url = "https://fast540.000webhostapp.com/app/get_data_user.php?username="+db.get_username();
            requestQueue = Volley.newRequestQueue(this);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,
                    new Response.Listener<JSONObject>(){
                        @Override
                        public void  onResponse (JSONObject response){
                            try {
                                JSONArray jsonArray = response.getJSONArray("alldata");

                                JSONObject respons = jsonArray.getJSONObject(0);
                                String id = respons.getString("id");
                                String username = respons.getString("username");
                                String password = respons.getString("password");
                                String email = respons.getString("email");
                                String phone = respons.getString("phone");
                                String location = respons.getString("location");
                                String img = respons.getString("img");
                                String info = respons.getString("info");
                                String type = respons.getString("type");
                                String gender = respons.getString("gender");

                                db.update_alldata("1");
                                db.save_all_data(id,username,password,email,location,phone,img,info,type,gender);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            progressDialog.dismiss();
                            listData2();
                        }
                    },new Response.ErrorListener(){
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", "ERROR");
                }
            }
            );

            requestQueue.add(jsonObjectRequest);


        }

    }

    public void listData2(){
        username = db.get_username();
        phone = db.get_phone();
        email = db.get_email();
        location = db.get_adress();
        info = db.get_info();
        img = db.get_img();
        id = db.get_idd();
        passs = db.get_Password();
        genderr = db.get_gender();



    }
}
