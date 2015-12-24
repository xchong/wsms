package com.example.administrator.jsonlist;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class MainActivity extends Activity {



    private RequestQueue mQueue;
    private  JsonObjectRequest jsonObjectRequest;
    private static final String WEATHER_LINK = "http://www.weather.com.cn/data/sk/101280101.html";



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

         mQueue = Volley.newRequestQueue(this.getApplicationContext());
         JsonRequst();


    }


    public void JsonRequst(){


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("http://gc.ditu.aliyun.com/geocoding?a=%E8%8B%8F%E5%B7%9E%E5%B8%82", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("TAG", response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
            }
        });

        mQueue.add(jsonObjectRequest);
    }

}
