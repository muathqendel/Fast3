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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Notification extends AppCompatActivity {

    String novel_id;

    private String Search_Name ="";
    ArrayList <listitme_notification> listnovel = new ArrayList<listitme_notification>();
    private ListView listView;
    private ListAdapter listAdapter;
    private ProgressDialog progressDialog;
    RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);


        Intent data = getIntent();
        novel_id = data.getExtras().getString("novel_id") ;

        listView = (ListView) findViewById(R.id.listView);
        progressDialog = new ProgressDialog(this);

        try {
            get_Notification();
        } catch (Exception e) {
            Intent Opennovel = new Intent(Notification.this, no_connection.class);
            startActivity(Opennovel);
        }

    }

    public void get_Notification(){
        requestQueue = Volley.newRequestQueue(this);

        CheckInternetConnection cic = new CheckInternetConnection(getApplicationContext());
        Boolean Ch = cic.isConnectingToInternet();
        if (!Ch) {
            Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        } else {
            progressDialog.setMessage(getString(R.string.Waite));
            progressDialog.setCancelable(true);
            progressDialog.show();

            String url = "https://fast540.000webhostapp.com/app/notification.php?notification_id=0";
            requestQueue = Volley.newRequestQueue(this);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,
                    new Response.Listener<JSONObject>(){
                        @Override
                        public void  onResponse (JSONObject response){
                            try {
                                JSONArray jsonArray = response.getJSONArray("allnotifications");
                                for(int i = 0 ; i < jsonArray.length();i++){
                                    JSONObject respons = jsonArray.getJSONObject(i);
                                    String id = respons.getString("id");
                                    String Title = respons.getString("Title");
                                    String txt = respons.getString("txt");
                                    String date_time = respons.getString("date_time");


                                    listnovel.add(new listitme_notification(id, Title,txt, date_time));
                                    progressDialog.dismiss();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
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
        ArrayList<listitme_notification> listitem_arter = new ArrayList<listitme_notification>();

        ListAdapter(ArrayList <listitme_notification> listitme) {
            this.listitem_arter = listitme;
        }

        @Override
        public int getCount() {
            return listitem_arter.size();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public Object getItem(int position) {
            return listitem_arter.get(position).id;
        }

        @Override
        public View getView(final int i, View convertView, ViewGroup parent) {


            LayoutInflater layoutInflater = getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.row_item_notification, null);



            TextView textView_title = (TextView) view.findViewById(R.id.textView_title);
            TextView textView_txt = (TextView) view.findViewById(R.id.textView_txt);




            textView_title.setText(listitem_arter.get(i).Title);
            textView_txt.setText(listitem_arter.get(i).txt);




            return view;
        }
    }
}
