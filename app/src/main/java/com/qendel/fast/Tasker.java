package com.qendel.fast;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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

import java.util.ArrayList;

public class Tasker extends AppCompatActivity {

    String username,phone,email,location,info,img,id;
    TextView textView_tasker,textView_address,textView_email,textView_info,textView_phone;
    ImageView img_user;
    GridView gridView;
    ArrayList <listitme_img> listnovel = new ArrayList<listitme_img>();
    private ProgressDialog progressDialog;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasker);

        Intent data = getIntent();
        username = data.getExtras().getString("username") ;
        phone = data.getExtras().getString("phone") ;
        email = data.getExtras().getString("email") ;
        location = data.getExtras().getString("location") ;
        info = data.getExtras().getString("info") ;
        img = data.getExtras().getString("img") ;
        id = data.getExtras().getString("id") ;

        textView_tasker = findViewById(R.id.textView_tasker);
        textView_address = findViewById(R.id.textView_address);
        textView_email = findViewById(R.id.textView_email);
        textView_info = findViewById(R.id.textView_info);
        textView_phone = findViewById(R.id.textView_phone);
        img_user = findViewById(R.id.img_user);
        gridView = findViewById(R.id.gridView);

        textView_tasker.setText(username);
        textView_address.setText(location);
        textView_email.setText(email);
        textView_info.setText(info);
        textView_phone.setText(phone);
        Picasso.with(getApplicationContext()).load(img).into(img_user);


        progressDialog = new ProgressDialog(this);

        get_img();
    }

    public void get_img(){
        requestQueue = Volley.newRequestQueue(this);

        CheckInternetConnection cic = new CheckInternetConnection(getApplicationContext());
        Boolean Ch = cic.isConnectingToInternet();
        if (!Ch) {
            Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        } else {
            progressDialog.setMessage(getString(R.string.Waite));
            progressDialog.setCancelable(true);
            progressDialog.show();

            String url = "https://fast540.000webhostapp.com/app/show_img.php?tasker_id="+id;
            requestQueue = Volley.newRequestQueue(this);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,
                    new Response.Listener<JSONObject>(){
                        @Override
                        public void  onResponse (JSONObject response){
                            try {
                                JSONArray jsonArray = response.getJSONArray("allimg");
                                for(int i = 0 ; i < jsonArray.length();i++){
                                    JSONObject respons = jsonArray.getJSONObject(i);
                                    String id = respons.getString("id");
                                    String image = respons.getString("image");
                                    String tasker_id = respons.getString("tasker_id");
                                    String date_time = respons.getString("date_time");


                                    listnovel.add(new listitme_img(id,image,tasker_id,date_time));

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            progressDialog.dismiss();
                            listData();
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

    public void listData() {
        ListAdapter ad = new ListAdapter(listnovel);
        gridView.setAdapter(ad);
    }

    public void phone(View view) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:"+ phone));
        startActivity(intent);
    }

    public void mail(View view) {
        String[] to = {email};
        Intent sendemail = new Intent(Intent.ACTION_SEND);
        sendemail.putExtra(Intent.EXTRA_EMAIL, to);
        sendemail.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
        sendemail.putExtra(Intent.EXTRA_TEXT, "السلام عليكم ورحمة الله وبركاته :");
        sendemail.setType("message/rfc822");
        if (sendemail.resolveActivity(getPackageManager()) != null) {
            startActivity(Intent.createChooser(sendemail, "Send Email"));
        }
    }

    class ListAdapter extends BaseAdapter {
        ArrayList <listitme_img> listitem = new ArrayList<listitme_img>();

        ListAdapter(ArrayList <listitme_img> listitme) {
            this.listitem = listitme;
        }

        @Override
        public int getCount() {
            return listitem.size();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public Object getItem(int position) {
            return listitem.get(position).id;
        }

        @Override
        public View getView(final int i, View convertView, ViewGroup parent) {


            LayoutInflater layoutInflater = getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.row_item_img, null);

            ImageView img_tasker =  view.findViewById(R.id.imageView);



            Picasso.with(getApplicationContext()).load(listitem.get(i).image).into(img_tasker);




            return view;
        }
    }

}
