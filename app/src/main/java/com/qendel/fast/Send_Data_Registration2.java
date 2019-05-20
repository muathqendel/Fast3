package com.qendel.fast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Send_Data_Registration2 extends StringRequest {
    private static final String SEND_DATA_URL = "https://fast540.000webhostapp.com/app/Registeration_user.php";
    private Map<String, String> MapData;

    public Send_Data_Registration2(String name, String email, String password, String ip, String imagecode ,
                                   String sex, Response.Listener <String> listener) {
        super(Request.Method.POST, SEND_DATA_URL, listener, null);
        MapData = new HashMap<>();
        MapData.put("ename", name);
        MapData.put("email", email);
        MapData.put("epassword", password);
        MapData.put("phone",ip);
        MapData.put("sex",sex);
        MapData.put("imagecode",imagecode);
    }

    @Override
    public Map<String, String> getParams() {
        return MapData;
    }
}
