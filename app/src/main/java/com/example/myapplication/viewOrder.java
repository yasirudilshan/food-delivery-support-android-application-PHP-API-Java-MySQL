package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import java.util.*;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toolbar;

import java.util.ArrayList;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.JsonArrayRequest;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class viewOrder extends AppCompatActivity {
    Button button_previous;
    String result;
    //ArrayList<String> arrayList = new ArrayList<String>();
    ArrayList<HashMap<String,String>> values=new ArrayList<HashMap<String,String>>();
    String name, email, password,apikey;


    private Toolbar toolbarr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order);
        toolbarr=findViewById(R.id.myToolBar);
        ImageView left=findViewById(R.id.left_icon);

        //setSupportActionBar(toolbarr);

        //button_previous=findViewById(R.id.previous);
        String[] mobileArray = {"Android","IPhone"};

        getSupportActionBar().hide();
        getSupportActionBar().setTitle("Orders");

        String url ="http://192.168.1.101/FoodOrder/orders.php";
        RequestQueue queue = Volley.newRequestQueue(viewOrder.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {

                    try {
                        // we are getting each json object.
                        JSONObject responseObj = response.getJSONObject(i);
                        String orderName = responseObj.getString("id");
                        String address = responseObj.getString("adress");
                        String orderStatus = responseObj.getString("order_status");
                        String note = responseObj.getString("note");

                        System.out.println(orderName+" "+address+" "+orderStatus+" "+note);
                        HashMap<String,String> m=new HashMap<String,String>();
                        m.put("id",orderName);

                        values.add(m);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    getData();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(viewOrder.this, "Fail to get the data..", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jsonArrayRequest);


    left.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();
        }
    });

    }
    private void getData(){
        ListView listView = (ListView) findViewById(R.id.mobile_list);
        SimpleAdapter adapter=new SimpleAdapter(viewOrder.this,values,R.layout.activity_listview,new String[]{"id"},new int[]{R.id.label});
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> l, View v, int position, long id){
                String name = l.getItemAtPosition(position).toString();
                String s=listView.getItemAtPosition(position).toString();

                String selected = ((TextView) v.findViewById(R.id.label)).getText().toString();
                Log.i("menuItems", name);
                System.out.println(selected);

                Intent intent = new Intent(getBaseContext(), viewOrderDetails.class);
                intent.putExtra("EXTRA_SESSION_ID", selected);
                startActivity(intent);
            }
        });
    }

}
