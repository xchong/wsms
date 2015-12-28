package com.example.administrator.jsonlist;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class MainActivity extends Activity {



    private RequestQueue mQueue;
    private  JsonObjectRequest jsonObjectRequest;
    private static final String WEATHER_LINK = "http://www.weather.com.cn/data/sk/101280101.html";
    public  ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
    private ListView lvWeather;
    private Context context;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       setContentView(R.layout.activity_main);
        context = this.getApplicationContext();
        lvWeather = (ListView)findViewById(R.id.listview);
        mQueue = Volley.newRequestQueue(this);

        JsonRequst();



//        SimpleAdapter adapter = new SimpleAdapter(this,list,R.layout.item,
//                new String[]{"title","info"},
//                new int[]{R.id.title,R.id.info});
       // lvWeather.setAdapter(adapter);

    }


    public void JsonRequst(){


        StringRequest jsonObjectRequest = new StringRequest("http://gc.ditu.aliyun.com/geocoding?a=%E8%8B%8F%E5%B7%9E%E5%B8%82",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("TAG", response.toString());

                        try{
                            JSONObject jo = new JSONObject(response);
                            Iterator<String> it =  jo.keys();
                            while (it.hasNext()){

                                String key = it.next();
                                String value = jo.getString(key);
                                Log.d("TAG", "title = " + key + " | content = " + value);
                                HashMap<String, String> map = new HashMap<String, String>();
                                map.put("title", key);
                                map.put("content", value);

                                Log.d("TAG", "title = " + key + " | content = " + value);

                                list.add(map);

                                System.out.println(list.toString());
                                SimpleAdapter adapter = new SimpleAdapter(context,list,R.layout.item,
                                        new String[]{"title","content"},
                                        new int[]{R.id.title,R.id.info});


                                lvWeather.setAdapter(adapter);

                            }

                        Log.d("TAG", list.toString());

                        }catch (Exception e){

                           // Log.d("TAG", e.toString());
                        }

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
