package com.qendel.fast;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
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

public class search extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private String Search_Name ="";
    ArrayList <listitme_tasker> listnovel = new ArrayList<listitme_tasker>();
    private ProgressDialog progressDialog;
    private int LimitS = 0;
    private ListView listView;
    private ListAdapter listAdapter;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        listView = (ListView) findViewById(R.id.listView);
        progressDialog = new ProgressDialog(this);



        get_taskers();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_page, menu);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.menu_searchable));
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        if (query.length() > 1) {
            Search_Name = query.trim();
            listnovel.clear();
            get_taskers();
        } else {
            Toast.makeText(this, " يجب ان نكون كلمة البحث حرفين أو أكثر", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
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

            String url = "https://fast540.000webhostapp.com/app/search.php?search="+Search_Name;
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
                    Intent Opennovel = new Intent(search.this, Tasker.class);
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
