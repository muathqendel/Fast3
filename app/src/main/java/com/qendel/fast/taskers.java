package com.qendel.fast;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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

public class taskers extends AppCompatActivity {
    String type;
    ListView listView;
    ArrayList <listitme_tasker> listnovel = new ArrayList<listitme_tasker>();
    private ProgressDialog progressDialog;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taskers);

        Intent data = getIntent();
        type = data.getExtras().getString("type") ;

        listView = findViewById(R.id.listView);
        progressDialog = new ProgressDialog(this);

        get_taskers();
    }

    public void get_taskers(){
        requestQueue = Volley.newRequestQueue(this);

        CheckInternetConnection cic = new CheckInternetConnection(getApplicationContext());
        Boolean Ch = cic.isConnectingToInternet();
        if (!Ch) {
            Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        } else {
            progressDialog.setMessage(getString(R.string.Waite));
            progressDialog.setCancelable(true);
            progressDialog.show();

            String url = "https://fast540.000webhostapp.com/app/show_tasker.php?type="+type;
            requestQueue = Volley.newRequestQueue(this);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,
                    new Response.Listener<JSONObject>(){
                        @Override
                        public void  onResponse (JSONObject response){
                            try {
                                JSONArray jsonArray = response.getJSONArray("alltaskers");
                                for(int i = 0 ; i < jsonArray.length();i++){
                                    JSONObject respons = jsonArray.getJSONObject(i);
                                    String id = respons.getString("id");
                                    String username = respons.getString("username");
                                    String phone = respons.getString("phone");
                                    String email = respons.getString("email");
                                    String location = respons.getString("location");
                                    String img = respons.getString("img");
                                    String gender = respons.getString("gender");
                                    String info = respons.getString("info");
                                    String type2 = respons.getString("type");


                                    listnovel.add(new listitme_tasker(id,username,email,phone,location,img,gender,info,type2));

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
        listView.setAdapter(ad);
    }

    class ListAdapter extends BaseAdapter {
        ArrayList <listitme_tasker> listitem = new ArrayList<listitme_tasker>();

        ListAdapter(ArrayList <listitme_tasker> listitme) {
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
            View view = layoutInflater.inflate(R.layout.row_item_tasker, null);

            ImageView img_tasker =  view.findViewById(R.id.img_tasker);
            TextView textView_tasker = (TextView) view.findViewById(R.id.textView_tasker);
            TextView textView_address = (TextView) view.findViewById(R.id.textView_address);
            TextView textView_info = (TextView) view.findViewById(R.id.textView_info);
            LinearLayout ly_tasker =  view.findViewById(R.id.ly_tasker);

            textView_tasker.setText(listitem.get(i).username);
            textView_address.setText(listitem.get(i).location);
            textView_info.setText(listitem.get(i).info);

            Picasso.with(getApplicationContext()).load(listitem.get(i).img).into(img_tasker);

            ly_tasker.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent Opennovel = new Intent(taskers.this, Tasker.class);
                    Opennovel.putExtra("username",listitem.get(i).username);
                    Opennovel.putExtra("phone",listitem.get(i).phone);
                    Opennovel.putExtra("email",listitem.get(i).email);
                    Opennovel.putExtra("location",listitem.get(i).location);
                    Opennovel.putExtra("info",listitem.get(i).info);
                    Opennovel.putExtra("img",listitem.get(i).img);
                    Opennovel.putExtra("id",listitem.get(i).id);
                    startActivity(Opennovel);
                }
            });



            return view;
        }
    }
}
