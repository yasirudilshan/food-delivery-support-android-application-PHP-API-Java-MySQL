package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class viewOrderDetails extends AppCompatActivity {
Button goBack;
TextView showUserId,showAddress,showOrderStatus,showNote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order_details);
        showUserId=findViewById(R.id.textViewUseID);
        showAddress=findViewById(R.id.textViewAddress);
        showOrderStatus=findViewById(R.id.textViewOrderStatus);
        showNote=findViewById(R.id.textViewNote);

        getSupportActionBar().setTitle("Order Details");

        String sessionId = getIntent().getStringExtra("EXTRA_SESSION_ID");
        showUserId.setText(sessionId);
        String item=sessionId;


        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url ="http://192.168.1.101/FoodOrder/orderdetails.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            String orderId=jsonObject.getString("id");
                            String address=jsonObject.getString("address");
                            String orderStatus=jsonObject.getString("order_status");
                            String note=jsonObject.getString("note");

                            System.out.println(orderId);
                            System.out.println(address);
                            System.out.println(orderStatus);
                            System.out.println(note);

                            showUserId.setText(orderId);
                            showAddress.setText(address);
                            if(orderStatus.equals("0")){
                                showOrderStatus.setText("In-Processing");
                            }
                            else{
                                showOrderStatus.setText("Delivering");
                            }
                            showNote.setText(note);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            protected Map<String, String> getParams(){
                Map<String, String> paramV = new HashMap<>();
                paramV.put("item", item);

                return paramV;
            }
        };
        queue.add(stringRequest);

    }
}