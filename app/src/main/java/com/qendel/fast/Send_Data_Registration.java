package com.qendel.fast;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hp on 29/08/2018.
 */

public class Send_Data_Registration extends StringRequest {

    private static final String SEND_DATA_URL = "https://fast540.000webhostapp.com/app/Registeration_employer.php";
    private Map<String, String> MapData;

    public Send_Data_Registration(String name, String email, String password, String ip, String imagecode ,
                                  String adress , String sex,String type,String info,
                                  Response.Listener <String> listener) {
        super(Method.POST, SEND_DATA_URL, listener, null);
        MapData = new HashMap<>();
        MapData.put("ename", name);
        MapData.put("email", email);
        MapData.put("epassword", password);
        MapData.put("phone",ip);
        MapData.put("location",adress);
        MapData.put("sex",sex);
        MapData.put("imagecode",imagecode);
        MapData.put("type",type);
        MapData.put("info",info);
    }

    @Override
    public Map<String, String> getParams() {
        return MapData;
    }
}