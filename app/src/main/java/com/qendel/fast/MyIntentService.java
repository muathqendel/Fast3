package com.qendel.fast;



import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.Context;
import android.os.Build;
import android.util.Log;
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
import java.util.Iterator;

/**
 * Created by hussienalrubaye on 3/6/16.
 * this services send  broadcast messages every 50000ms
 */
public class MyIntentService extends IntentService {
    public static boolean ServiceIsRun = true;
    public static int CommnetID=0;
    DB_sqlite db;
    boolean not = false ;


    ArrayList <listitme_notification> listnovel = new ArrayList<listitme_notification>();
    RequestQueue requestQueue;
    String title;


    public MyIntentService() {
        super("MyWebRequestService");
    }
    protected void onHandleIntent(Intent workIntent) {

        db = new DB_sqlite(this);


        while ( ServiceIsRun) {

            notification();

            try{
                Thread.sleep(20000);
            }catch (Exception ex){}


        }
    }



    public void notification(){
        try {
            Toast.makeText(MyIntentService.this, "ksk"+ db.get_numb(), Toast.LENGTH_SHORT).show();

            String url = "https://fast540.000webhostapp.com/app/notification.php?notification_id=" + db.get_numb();
            requestQueue = Volley.newRequestQueue(this);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener <JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONArray jsonArray = response.getJSONArray("allnotifications");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject respons = jsonArray.getJSONObject(i);
                                    String id = respons.getString("id");
                                    String Title = respons.getString("Title");
                                    String txt = respons.getString("txt");
                                    String date_time = respons.getString("date_time");

                                    CommnetID = Integer.parseInt(respons.getString("id"));
                                    if (Integer.parseInt( id ) > db.get_numb()) {

                                        db.updateData_notfiy_numb(Integer.parseInt( id ));
                                        //CommnetID_B = Integer.parseInt(respons.getString("id"));
                                        title = Title ;

                                        not = true ;

                                    }
                                    listnovel.add(new listitme_notification(id, Title,txt, date_time));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            if (not) {
                                not = false ;
                                listDataBook();
                            }
                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", "ERROR");
                }
            }
            );

            requestQueue.add(jsonObjectRequest);



        }catch (Exception ex){}
    }

    public void listDataBook(){
        // creat new intent
        Intent intent = new Intent();
        //set the action that will receive our broadcast
        intent.setAction("com.example.Broadcast.fast");
        // add data to the bundle
        intent.putExtra("title", title);
        // send the data to broadcast
        sendBroadcast(intent);
        //delay for 50000ms
    }


    ////////api o
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "channel_name";
            String description = "channel_description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("CHANNEL_ID", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
    ////////// api o
}
