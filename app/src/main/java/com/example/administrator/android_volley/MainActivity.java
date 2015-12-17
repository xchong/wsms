package com.example.administrator.android_volley;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity {

//    private Double homeLat=26.0673834d; //宿舍纬度
//    private Double homeLon=119.3119936d; //宿舍经度
    private EditText editText = null;
    private MyReceiver receiver=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        editText=(EditText)findViewById(R.id.editText);
        //判断GPS是否可用
        Log.i("gpsble", UtilTool.isGpsEnabled((LocationManager)getSystemService(Context.LOCATION_SERVICE))+"");
        if(!UtilTool.isGpsEnabled((LocationManager)getSystemService(Context.LOCATION_SERVICE))){
            Toast.makeText(this, "GSP当前已禁用，请在您的系统设置屏幕启动。", Toast.LENGTH_LONG).show();
            Intent callGPSSettingIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(callGPSSettingIntent);
            return;
        }

        //启动服务
        startService(new Intent(this, GpsService.class));

        //注册广播
        receiver=new MyReceiver();
        IntentFilter filter=new IntentFilter();
        filter.addAction("com.ljq.activity.GpsService");
        registerReceiver(receiver, filter);

       // volleyPost();



    }

    //获取广播数据
    private class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle=intent.getExtras();
            String lon=bundle.getString("lon");
            String lat=bundle.getString("lat");

                editText.setText("目前经纬度\n经度："+lon+"\n纬度："+lat);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void volleyPost(){

        RequestQueue mQueue = Volley.newRequestQueue(this.getApplication());

        StringRequest stringRequest = new StringRequest(Request.Method.POST,"http://172.31.14.56/zhiban/submit.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("baidu", response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("er", error.getMessage(), error);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("username", "jxair");
                map.put("password", "jxair");
                return map;
            }
        };

        mQueue.add(stringRequest);
    }
}
