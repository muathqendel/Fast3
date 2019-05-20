package com.qendel.fast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Send_Data_Login2 extends StringRequest {

    private static final String SEND_DATA_URL = "https://fast540.000webhostapp.com/app/login_user.php";
    private Map<String, String> MapData;

    public Send_Data_Login2(String Login_name, String Login_password, Response.Listener<String> listener) {
        super(Request.Method.POST, SEND_DATA_URL, listener, null);
        MapData = new HashMap<>();
        MapData.put("Login_name", Login_name);
        MapData.put("Login_password", Login_password);
    }

    @Override
    public Map<String, String> getParams() {
        return MapData;
    }
}
