package com.qendel.fast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Send_Data_img extends StringRequest {
    private static final String SEND_DATA_URL = "https://fast540.000webhostapp.com/app/add_img.php";
    private Map <String, String> MapData;

    public Send_Data_img(String idd, String imagecode, Response.Listener<String> listener) {
        super(Request.Method.POST, SEND_DATA_URL, listener, null);
        MapData = new HashMap <>();
        MapData.put("ename", idd);
        MapData.put("imagecode", imagecode);
    }

    @Override
    public Map<String, String> getParams() {
        return MapData;
    }
}
