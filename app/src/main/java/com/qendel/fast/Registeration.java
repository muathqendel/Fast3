package com.qendel.fast;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

import android.support.v7.app.AppCompatActivity;

public class Registeration extends AppCompatActivity {


    EditText editText_username_r, editText_email_r, editText_pass_r, editText_pass2_r, editText_ip, editText_adress;
    DB_sqlite db;
    String save_data;
    CheckBox checkBox_save_data_r,checkBox_sign;
    String Check;
    ImageView imageView;
    String imagecode;
    String type;
    RadioGroup radioGroup;
    RadioButton radioButton;
    String sex = "",sure= "0";
    private static final String TAG = "Registration";


    EditText editText_info;
    TextView textView_choose,textView_choose2;
    String choose,choose1,choose3,choose4;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);



        imageView = (ImageView) findViewById(R.id.img_user);
        checkBox_save_data_r = (CheckBox) findViewById(R.id.checkBox_save_data_r);
        editText_username_r = (EditText) findViewById(R.id.editText_username_r);
        editText_email_r = (EditText) findViewById(R.id.editText_email_r);
        editText_pass_r = (EditText) findViewById(R.id.editText_pass_r);
        editText_pass2_r = (EditText) findViewById(R.id.editText_pass2_r);
        editText_ip = (EditText) findViewById(R.id.editText_ip);
        editText_adress = findViewById(R.id.editText_location);



        editText_info = findViewById(R.id.editText_info);
        textView_choose = findViewById(R.id.textView_choose);
        textView_choose2 = findViewById(R.id.textView_choose2);
        checkBox_sign = (CheckBox) findViewById(R.id.checkBox_sign);

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



    }


    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }


    public void sign_up2(View view) {


        final String Ename = editText_username_r.getText().toString().trim();
        final String Email = editText_email_r.getText().toString().trim();
        final String Pass = editText_pass_r.getText().toString().trim();
        final String ip = editText_ip.getText().toString().trim();
        String Pass2 = editText_pass2_r.getText().toString().trim();
        final String adress = editText_adress.getText().toString().trim();

        final String info = editText_info.getText().toString().trim();


        /// to convert image to string
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream); /// 100 % quality from image
        imagecode = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);



        if (!Pass.equals(Pass2)) {

            Toast.makeText(Registeration.this, "Please write password correctly", Toast.LENGTH_LONG).show();

        }else if(Ename.equals("")) {

            Toast.makeText(Registeration.this, "please write your username", Toast.LENGTH_LONG).show();

        }
        else if(Email.equals("")) {

            Toast.makeText(Registeration.this, "please write your email", Toast.LENGTH_LONG).show();
        }
        else if(!Email.contains("@")) {

            Toast.makeText(Registeration.this, "please choose correct email", Toast.LENGTH_LONG).show();
        }
        else if(Pass.equals("")) {

            Toast.makeText(Registeration.this, "please write your password", Toast.LENGTH_LONG).show();
        }else if(ip.length() != 10 && ip.length() != 12 && ip.length() != 14 ){

            Toast.makeText(Registeration.this, "Please write Phone Number correctly", Toast.LENGTH_LONG).show();

        }else if(editText_pass_r.length() < 8){

            Toast.makeText(Registeration.this, "the password must have 8 Symbols at least", Toast.LENGTH_LONG).show();

        }else if(sex.equals("")) {

            Toast.makeText(Registeration.this, "please select your gender", Toast.LENGTH_LONG).show();

        }else if(adress.equals("")){

            Toast.makeText(Registeration.this, "please Write your Address", Toast.LENGTH_LONG).show();

        } else if(choose.equals("")){

            Toast.makeText(Registeration.this, "please Write your Work", Toast.LENGTH_LONG).show();

        } else if(choose3.equals("")){

            Toast.makeText(Registeration.this, "please Write your Work", Toast.LENGTH_LONG).show();

        } else if(info.equals("")){

            Toast.makeText(Registeration.this, "please Write your description", Toast.LENGTH_LONG).show();

        } else if(sure.equals("0")) {

            Toast.makeText(Registeration.this, "please check the Terms and Conditions", Toast.LENGTH_LONG).show();
        }else {


            Response.Listener <String> responseLisener = new Response.Listener <String>() {

                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");
                        if (success) {
                            Toast.makeText(Registeration.this, "successfully registered", Toast.LENGTH_LONG).show();
                            db.updateData_R(Ename, Pass, save_data, Email,adress,ip,sex);
                            db.update_save("1");
                            Intent Opennovel = new Intent(Registeration.this, sure.class);
                            Opennovel.putExtra("type", type);
                            startActivity(Opennovel);
                            finish();
                        } else {
                            Toast.makeText(Registeration.this, "Excuse me! There is an error or username already exists", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };

            Send_Data_Registration send_Data = new Send_Data_Registration(Ename, Email, Pass, ip, imagecode, adress, sex,choose3,info,  responseLisener);
            RequestQueue queue = Volley.newRequestQueue(Registeration.this);
            queue.add(send_Data);
        }

    }


    public void Sign_in(View view) {
        Intent Opennovel = new Intent(Registeration.this, login.class);
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


    public void choose(View view) {
        PopupMenu popupMenu = new PopupMenu(Registeration.this,textView_choose);
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
                PopupMenu popupMenu = new PopupMenu(Registeration.this, textView_choose);
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
                PopupMenu popupMenu = new PopupMenu(Registeration.this, textView_choose);
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
                PopupMenu popupMenu = new PopupMenu(Registeration.this, textView_choose);
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
                PopupMenu popupMenu = new PopupMenu(Registeration.this, textView_choose);
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
                PopupMenu popupMenu = new PopupMenu(Registeration.this, textView_choose);
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
                PopupMenu popupMenu = new PopupMenu(Registeration.this,textView_choose);
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
}
