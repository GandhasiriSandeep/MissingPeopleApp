package com.example.unknownper.missingapp;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by unknown per on 12/20/2016.
 */

public class RegisterRequest extends StringRequest {

    private static final String url = "http://studentmitra.co.in/none/missingapp/register.php";
    private Map<String,String> params;
    public RegisterRequest(String mobile,String password, Response.Listener<String> listener) {
        super(Method.POST, url, listener,null);
        params = new HashMap<>();
        params.put("mobile",mobile);
        params.put("password",password);
    }

    public Map<String,String> getparams(){

        return params;
    }
}
