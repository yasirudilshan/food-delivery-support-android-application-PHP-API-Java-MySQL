package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;

public class Registration extends AppCompatActivity {

    TextInputEditText textInputEditTextName, textInputEditTextEmail, textInputEditTextPassword;
    Button buttonSubmit;
    String name, email, password;
    TextView textViewError,TextView_loginNow;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        textInputEditTextName=findViewById(R.id.name);
        textInputEditTextEmail=findViewById(R.id.email);
        TextView_loginNow=findViewById(R.id.loginNow);
        textInputEditTextPassword=findViewById(R.id.password);
        buttonSubmit=findViewById(R.id.submit);
        textViewError=findViewById(R.id.error);
        progressBar=findViewById(R.id.loading);
        getSupportActionBar().setTitle("Registration");

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textViewError.setVisibility(view.GONE);
                progressBar.setVisibility(view.VISIBLE);
                name= String.valueOf(textInputEditTextName.getText());
                email= String.valueOf(textInputEditTextEmail.getText());
                password= String.valueOf(textInputEditTextPassword.getText());

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url ="http://192.168.1.101/FoodOrder/register.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                progressBar.setVisibility(view.GONE);
                                if(response.equals("success")){
                                    Toast.makeText(getApplicationContext(),"Registration Successfully",Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(getApplicationContext(),Login.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else{
                                    textViewError.setText(response);
                                    textViewError.setVisibility(view.VISIBLE);
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(view.GONE);
                        textViewError.setText(error.getLocalizedMessage());
                        textViewError.setVisibility(view.VISIBLE);
                    }
                }){
                    protected Map<String, String> getParams(){
                        Map<String, String> paramV = new HashMap<>();
                        paramV.put("name", name);
                        paramV.put("email", email);
                        paramV.put("password", password);
                        return paramV;
                    }
                };
                queue.add(stringRequest);
            }
        });
        TextView_loginNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
                finish();
            }
        });
    }
}